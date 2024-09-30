package com.B21DCVT398.test.dbConnect;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.B21DCVT398.test.model.User;
import java.util.ArrayList;
import java.util.List;

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
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        values.put("dob", user.getDob());
        values.put("gender", user.getGender());

        return database.insert("user", null, values);
    }

    // Get user by username from the database
    public User getUser(String username) {
        Cursor cursor = database.query(
                "user",
                new String[]{"username", "password", "name", "email", "phone", "dob", "gender"},
                "username = ?",
                new String[]{username},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex("password"));
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
            @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex("email"));
            @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("phone"));
            @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("dob"));
            @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("gender"));
            cursor.close();  // Close the cursor after reading

            // Return the user object
            return new User(username, password, name, email, phone, dob, gender);
        }

        return null; // Return null if no user is found
    }

    // Get all users from the database
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Cursor cursor = database.query(
                "user",
                new String[]{"username", "password", "name", "email", "phone", "dob", "gender"},
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
                @SuppressLint("Range") String dob = cursor.getString(cursor.getColumnIndex("email"));
                @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex("phone"));
                @SuppressLint("Range") String email = cursor.getString(cursor.getColumnIndex("dob"));
                @SuppressLint("Range") String phone = cursor.getString(cursor.getColumnIndex("gender"));

                // Add each user to the userList
                userList.add(new User(username, password, name, email, phone, dob, gender));
            }
            cursor.close();  // Close the cursor after reading
        }

        return userList;
    }

    // Update user information in the database
    public int updateUser(User user) {
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        values.put("name", user.getFullname());
        values.put("email", user.getDob());
        values.put("phone", user.getGender());
        values.put("dob", user.getEmail());
        values.put("gender", user.getPhone());

        // Update the user with the matching username
        return database.update("user", values, "username = ?", new String[]{user.getUsername()});
    }

    // Delete user by username from the database
    public void deleteUser(String username) {
        database.delete("user", "username = ?", new String[]{username});
    }
}
