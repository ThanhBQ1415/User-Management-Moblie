package com.B21DCVT398.test.dbConnect;

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

    // Mở cơ sở dữ liệu để đọc/ghi
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Đóng cơ sở dữ liệu
    public void close() {
        dbHelper.close();
    }

    // Chèn dữ liệu vào bảng
    public long insertUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        return database.insert("user", null, values);
    }

    // Lấy dữ liệu người dùng theo username
//    public User getUser(String username) {
//        Cursor cursor = database.query(
//                "user",
//                new String[]{"username", "password"},
//                "username = ?",
//                new String[]{username},
//                null,
//                null,
//                null
//        );
//
//        if (cursor != null && cursor.moveToFirst()) {
//            String password = cursor.getString(cursor.getColumnIndex("password"));
//            cursor.close();
//            return new User(username, password);
//        }
//
//        return null;
//    }

//    // Xóa dữ liệu người dùng theo username
//    public void deleteUser(String username) {
//        database.delete("user", "username = ?", new String[]{username});
//    }
//    public void printAllUsers() {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        Cursor cursor = db.query(
//                "user",
//                new String[]{"username", "password"},
//                null,
//                null,
//                null,
//                null,
//                null
//        );
//
//        if (cursor != null) {
//            while (cursor.moveToNext()) {
//                String username = cursor.getString(cursor.getColumnIndex("username"));
//                String password = cursor.getString(cursor.getColumnIndex("password"));
//                Log.d("DatabaseContent", "Username: " + username + ", Password: " + password);
//            }
//            cursor.close();
//        }
//    }


}
