/*
Samantha Durr
CS-360
Project 3
 */
package com.example.samanthadurrinventoryproject;

import static java.lang.Math.max;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginScreen extends AppCompatActivity {

    //The variables that allow a user to login via company ID, username, and password.
    Activity activity;
    Button loginButton, newUserButton, forgotPassword;
    EditText companyID, enterUsername, enterPassword;
    String usernameHolder, companyIdHolder, passwordHolder;
    Boolean holderEmpty;
    PopupWindow popUpWindow;
    SQLiteDatabase database;
    UsersSQLiteHandler handler;
    String tempPass = "X0q5mM~f5u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);
        //Sets the activity to itself.
        activity = this;

        //Sets the various elements to their appropriate counterparts in the design.
        loginButton = findViewById(R.id.loginButton);
        newUserButton = findViewById(R.id.newUserButton);
        forgotPassword = findViewById(R.id.forgotPassword);

        companyID = findViewById(R.id.companyID);
        enterUsername = findViewById(R.id.enterUsername);
        enterPassword = findViewById(R.id.enterPassword);

        //Sets a listener for when the login button is clicked to check that the information
        //supplied is correct.
        loginButton.setOnClickListener(view-> {

            LoginSuccessful();
            Intent intent = new Intent(loginScreen.this, inventoryScreen.class);
            startActivity(intent);
        });

        //Sets a listener for taking a user to the new user window.
        newUserButton.setOnClickListener(view-> {

            Intent intent = new Intent(loginScreen.this, newUserScreen.class);
            startActivity(intent);
        });

        //Sets a listener that takes the user to the forgot their password window.
        forgotPassword.setOnClickListener(view-> {

            usernameHolder = enterUsername.getText().toString().trim();

            //If the username is not empty, then popup the window.
            if(!usernameHolder.isEmpty()) {
                forgetPassPopup();
            }
            else {
                Toast.makeText(loginScreen.this, "User email is Empty",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    //Check that the information is correct to login successfully.
    public void LoginSuccessful() {

        String message = CheckEditTextNotEmpty();

        //If the holder isn't empty, then go into the database and find out if the user is there.
        //Check the information against hte tables to know that the user is in the table and log
        //them in.
        if(!holderEmpty) {
            if ( handler == null ) {
                handler = new UsersSQLiteHandler(this);
            }
            database = handler.getWritableDatabase();

            Cursor cursor = database.query(UsersSQLiteHandler.USERS_TABLE, null, " " +
                    UsersSQLiteHandler.COLUMN_2_USERNAME + "=?",
                    new String[] {usernameHolder}, null, null, null);

            while(cursor.moveToNext()) {
                if(cursor.isFirst()) {
                    cursor.moveToFirst();

                    tempPass = cursor.getString(max((int)0,(int)cursor.getColumnIndex(
                            UsersSQLiteHandler.COLUMN_3_PASSWORD)));
                    usernameHolder = cursor.getString(max((int)0,(int)
                            cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_2_USERNAME)));
                    companyIdHolder = cursor.getString(max((int)0,(int)
                            cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_1_COMPANYID)));

                    cursor.close();
                }
            }

            handler.close();
        }
        else {

            Toast.makeText(loginScreen.this, message, Toast.LENGTH_LONG).show();
        }
    }

    //Check that the user did not leave any empty values. If they did, let them know and request
    //that they input the information.
    public String CheckEditTextNotEmpty() {

        String message = "";

        usernameHolder = enterUsername.getText().toString().trim();
        passwordHolder = enterPassword.getText().toString().trim();

        if(usernameHolder.isEmpty()) {
            enterUsername.requestFocus();
            holderEmpty = true;

            message = "Username is Empty";
        }
        else if(passwordHolder.isEmpty()){
            enterPassword.requestFocus();
            holderEmpty = true;
            message = "User Password is empty";
        }
        else {
            holderEmpty = false;
        }
        return message;
    }

    //Check that hte information is valid and log them in, otherwise let them know that something
    //does not match.
    public void FinalResultCheck(){
        if(tempPass.equalsIgnoreCase(passwordHolder)) {
            Toast.makeText(loginScreen.this, "Login Successful", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("company ID", String.valueOf(companyID));
            bundle.putString("username", String.valueOf(enterUsername));
            bundle.putString("password", String.valueOf(enterPassword));

            Intent intent = new Intent(loginScreen.this, inventoryScreen.class);
            intent.putExtras(bundle);
            startActivity(intent);

            EmptyEditTextAfterDataInsert();
        }
        else {
            Toast.makeText(loginScreen.this, "Incorrect username, password, or company ID." +
                    "User may not be registered.", Toast.LENGTH_LONG).show();
        }

        tempPass = "X0q5mM~f5u8";
    }

    //Empty the fields after the user has logged in.
    public void EmptyEditTextAfterDataInsert() {

        companyID.getText().clear();
        enterUsername.getText().clear();
        enterPassword.getText().clear();
    }

    //If the user clicks the forgot password button, then pull up the window and walk them through
    //the forgot password information to get more information.
    public void forgetPassPopup() {

        LayoutInflater inflater = activity.getLayoutInflater();
        View layout = inflater.inflate(R.layout.fragment_popup_password, activity.findViewById(R.id.forgotPassword));

        popUpWindow = new PopupWindow(layout, 800, 800, true);
        popUpWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        EditText userName = layout.findViewById(R.id.userName);
        EditText companyId = layout.findViewById(R.id.companyIDEnter);

        TextView passwordView = layout.findViewById(R.id.passwordView);

        database = handler.getWritableDatabase();

        Cursor cursor = database.query(UsersSQLiteHandler.USERS_TABLE, null, " " +
                UsersSQLiteHandler.COLUMN_1_COMPANYID + "=?", new String[]{companyIdHolder},
                null, null, null);

        while(cursor.moveToNext()) {
            cursor.moveToFirst();

            usernameHolder = cursor.getString(max((int)0,(int)cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_2_USERNAME)));
            tempPass = cursor.getString(max((int)0,(int)cursor.getColumnIndex(UsersSQLiteHandler.COLUMN_3_PASSWORD)));

            cursor.close();
        }
        handler.close();

        Button get = layout.findViewById(R.id.submitButton);
        Button cancel = layout.findViewById(R.id.cancelButton);

        get.setOnClickListener(view-> {
            String verifyCompanyId = companyId.getText().toString();
            String verifyUsername = userName.getText().toString();

            if(verifyCompanyId.equals(companyIdHolder) && verifyUsername.equals(usernameHolder)) {
                passwordView.setText(tempPass);

                new Handler().postDelayed(()-> popUpWindow.dismiss(), 2000);
            }
            else {
                Toast.makeText(activity, "Company ID and Username do not match.",
                        Toast.LENGTH_LONG);
            }
        });

        cancel.setOnClickListener(view-> {
            Toast.makeText(activity, "Cancelled.", Toast.LENGTH_SHORT).show();
            popUpWindow.dismiss();
        });
    }
}