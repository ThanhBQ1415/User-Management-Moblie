package com.B21DCVT398.test.dbConnect;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.B21DCVT398.test.model.User;

public class MyDatabaseManager {
    private SQLiteDatabase database;
    private MyDatabaseHelper dbHelper;

    public MyDatabaseManager(Context context) {
        dbHelper = new MyDatabaseHelper(context);
    }

    // Open database for reading/writing
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Close database
    public void close() {
        dbHelper.close();
    }

    // Insert data into the user table
    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("name", user.getFullname());
        values.put("dob", user.getDob());
        values.put("gender", user.getGender());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        return database.insert("user", null, values);
    }

    // Get user by username
    public User getUser(String username) {
        Cursor cursor = database.query(
                "user",
                new String[]{"username", "password", "name", "dob", "gender", "email", "phone"},
                "username = ?",
                new String[]{username},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex("dob"));
            @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));
            cursor.close();
            return new User(username, password, name, dob, gender, email, phone); // Ensure the User constructor can handle these fields
        }

        return null;
    }

    // Delete user by username
    public void deleteUser(String username) {
        database.delete("user", "username = ?", new String[]{username});
    }

    // Print all users
    public void printAllUsers() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                "user",
                new String[]{"username", "password", "name", "dob", "gender", "email", "phone"},
                null,
                null,
                null,
                null,
                null
        );

        if (cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex("username"));
                @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex("dob"));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("gender"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("phone"));

                // Log user details
                Log.d("DatabaseContent", "Username: " + username + ", Password: " + password +
                        ", Name: " + name + ", DOB: " + dob + ", Gender: " + gender +
                        ", Email: " + email + ", Phone: " + phone);
            }
            cursor.close();
        }
    }
}
