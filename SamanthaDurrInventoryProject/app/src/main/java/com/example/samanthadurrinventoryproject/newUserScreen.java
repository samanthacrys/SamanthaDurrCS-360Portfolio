/*
Samantha Durr
CS-260
Project 3, Option 1: Inventory App
 */
package com.example.samanthadurrinventoryproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.BreakIterator;

public class newUserScreen extends AppCompatActivity {

    //Variables that help to create a new user and add them to the users database.
    Button createUser;
    EditText companyId, username, password;
    Boolean EmptyHolder;
    SQLiteDatabase database;
    UsersSQLiteHandler handler;
    String F_Result = "Not_Found";

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user_screen);

        //Sets the various variables found in the new user layout to the appropriate fields in
        //the layout.
        companyId = findViewById(R.id.CompanyIDInput);
        password = findViewById(R.id.PasswordInput);
        createUser = findViewById(R.id.CreateUser);
        username = findViewById(R.id.userNameInput);
        handler = new UsersSQLiteHandler(this);

        //Sets a listener that checks for the create user button being pressed.
        createUser.setOnClickListener(view -> {
            String message = CheckEditTextNotEmpty();

            //If the holder is not empty, then check that the user name does not exist.
            //If it does not, then add the data into the table. Otherwise, let the user know
            //that the username is unavailable.
            if (!EmptyHolder) {
                CheckIfUserNameExists();
                EmptyEditTextAfterDataInsert();
            }
            else {
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

    //Takes the information found in the user screen and adds it to the database with the
    //necessary information.
    public void InsertUserIntoDatabase(){
        String name = username.getText().toString().trim();
        BreakIterator companyID = null;
        String companyId = companyID.getText().toString().trim();
        String pass = password.getText().toString().trim();

        User user = new User(companyId, name, pass);
        handler.createUser(user);

        Toast.makeText(newUserScreen.this,"User Registered Successfully", Toast.LENGTH_LONG).show();

        startActivity(new Intent(newUserScreen.this, loginScreen.class));
        this.finish();
    }

    //Checks that none of the fields are empty. If they are, then let the user know so that they
    //may fill in the fields for their user to be created.
    public String CheckEditTextNotEmpty() {
        String message = "";
        String name = username.getText().toString().trim();
        String companyID = companyId.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if (name.isEmpty()) {
            username.requestFocus();
            EmptyHolder = true;
            message = "User Name is Empty";
        }
        else if (companyID.isEmpty()){
            companyId.requestFocus();
            EmptyHolder = true;
            message = "User Phone is Empty";
        }
        else if (pass.isEmpty()){
            password.requestFocus();
            EmptyHolder = true;
            message = "User Email is Empty";
        }
        else {
            EmptyHolder = false;
        }
        return message;
    }

    //Checks that the username exists or not. If it does, then inform the user so that they may
    //select a different username.
    public void CheckIfUserNameExists(){
        String name = username.getText().toString().trim();
        database = handler.getWritableDatabase();

        Cursor cursor = database.query(UsersSQLiteHandler.USERS_TABLE, null,
                " " + UsersSQLiteHandler.COLUMN_2_USERNAME + "=?",
                new String[]{String.valueOf(username)}, null, null, null);

        while (cursor.moveToNext()) {
            if (cursor.isFirst()) {
                cursor.moveToFirst();
                // If email exists then set result variable value as Email Found
                F_Result = "Email Found";
                // Closing cursor.
                cursor.close();
            }
        }
        handler.close();

        CheckFinalCredentials();
    }

    //Gives one last check to ensure that hte user does not match anything that is already in  the
    //system.
    public void CheckFinalCredentials(){
        if(F_Result.equalsIgnoreCase("username Found"))
        {
            Toast.makeText(newUserScreen.this,"username already Exists",
                    Toast.LENGTH_LONG).show();
        }
        else {
            InsertUserIntoDatabase();
        }
        F_Result = "Not_Found";
    }

    //Empties the data from the fields.
    public void EmptyEditTextAfterDataInsert(){
        username.getText().clear();
        companyId.getText().clear();
        password.getText().clear();
    }

}
