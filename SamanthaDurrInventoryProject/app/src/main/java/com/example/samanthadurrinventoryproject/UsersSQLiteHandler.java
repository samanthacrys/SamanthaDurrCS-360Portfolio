/*
Samantha Durr
CS-260
Project 3, Option 1: Inventory App
 */
package com.example.samanthadurrinventoryproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

import java.util.Objects;

public class UsersSQLiteHandler extends SQLiteOpenHelper {

    //This is the final version of the database.
    private static final int DBVersion = 1;

    //This is the database, which is being called usersDatabase.
    public static final String DATABASE_DB = "usersDatabase.DB";
    public static final String USERS_TABLE = "UsersTable";

    //These are the different columns in the database.
    public static final String COLUMN_1_COMPANYID = "companyID";
    public static final String COLUMN_2_USERNAME = "Username";
    public static final String COLUMN_3_PASSWORD = "Password";

    //Creates the base users table with the appropriate information.
    private static final String CreateUsersTable =
            "CREATE TABLE " + USERS_TABLE + "(" +
                    COLUMN_1_COMPANYID + " TEXT, " +
                    COLUMN_2_USERNAME + " TEXT, " +
                    COLUMN_3_PASSWORD + " TEXT" +
                    ")";

    //This sets the company ID as a private string.
    private String companyId;

    //This sets the users database.
    public UsersSQLiteHandler(Context context) {

        super(context, DATABASE_DB, null, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        //Tries to create the users database, but can catch an exception and send a message as long
        //as it is not null.
        try
        {
            database.execSQL(CreateUsersTable);
        }
        catch(Exception exception)
        {
            String msg = exception.getMessage();
            if ( msg != null )
            {
                Log.i("Error", msg);
            }
        }
        finally {

        }
    }

    //Checks for the database as being old and new and creates a new users table as needed.
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        database.execSQL(USERS_TABLE);
        onCreate(database);
    }

    //Creates a user from the new users window and sets their information to the appropriate
    //values from the get and set commands found in the User class. Closes the database after
    //these have been added in.
    public void createUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues vals = new ContentValues();
        vals.put(COLUMN_1_COMPANYID, user.getCompanyId());
        vals.put(COLUMN_2_USERNAME, user.getUsername());
        vals.put(COLUMN_3_PASSWORD, user.getPassword());

        database.insert(USERS_TABLE, null, vals);
        database.close();
    }

    //Can read a user from the database and query it to ensure that the values provided from the
    //login screen are valid.
    public User readUser(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(USERS_TABLE, new String[]{COLUMN_1_COMPANYID, COLUMN_2_USERNAME,
                        COLUMN_3_PASSWORD}, COLUMN_1_COMPANYID + "=?", new String[]{String.valueOf(id)}
                , null, null, null, null);

        User user = null;
        //If the cursor is not null, then go to the beginning and add in the new user to the
        //database and then close the cursor.
        if (cursor != null) {
            cursor.moveToFirst();

            user = new User(Integer.parseInt(Objects.requireNonNull(cursor).getString(1)),
                    cursor.getString(2), cursor.getString(3));
        }
        cursor.close();

        return user;
    }

    //If the user updates their information, such as changing their password, then update the
    //information with the new values in the correct field.
    public int updateUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues vals = new ContentValues();
        vals.put(COLUMN_1_COMPANYID, user.getCompanyId());
        vals.put(COLUMN_2_USERNAME, user.getUsername());
        vals.put(COLUMN_3_PASSWORD, user.getPassword());

        return database.update(USERS_TABLE, vals, COLUMN_1_COMPANYID + "=?", new String[] {
                String.valueOf(user.getCompanyId())
        });
    }

    //Removes a user from the database.
    public void deleteUser(User user) {
        SQLiteDatabase database = this.getWritableDatabase();

        database.delete(USERS_TABLE, COLUMN_1_COMPANYID + " = ?",
                new String[] { String.valueOf(user.getCompanyId()) });
        database.close();
    }

    //Creates a list of users that is able to be queried.
    public List<User> getAllUsers(String username, String pass) {
        List<User> userList = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + USERS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //If the cursor is on the first, then get that user at least once and then move to the
        //next.
        if (cursor.moveToFirst()) {
            do {
                User user = new User(companyId, username, pass);
                user.setCompanyId(Integer.parseInt(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(4));

                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return userList;
    }

    //Deletes all users from the table.
    public void deleteAllUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(USERS_TABLE,null,null);
        db.close();
    }

    //Gets a count of all users in the table.
    public int getUsersCount() {
        String countQuery = "SELECT * FROM " + USERS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int usersTotal = cursor.getCount();
        cursor.close();

        return usersTotal;
    }

}
