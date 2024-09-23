package com.B21DCVT398.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.B21DCVT398.test.model.User;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Deleteuser extends AppCompatActivity {
    private EditText usernameInput;
    private Button searchButton;
    private LinearLayout resultContainer;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);


        usernameInput = findViewById(R.id.input_username);
        searchButton = findViewById(R.id.button_search);
        resultContainer = findViewById(R.id.resultContainer);
        home=findViewById(R.id.welcome_activity);

        displayAllUsers();

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deleteuser.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                if (!username.isEmpty()) {
                    searchUser(username); // Tìm kiếm user và hiển thị kết quả
                } else {
                    Toast.makeText(Deleteuser.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }


    private void displayAllUsers() {
        displayResults(MainActivity.userList);
    }


    private void searchUser(String username) {
        List<User> matchedUsers = new ArrayList<>();


        for (User user : MainActivity.userList) {
            if (user.getUsername().contains(username)) {
                matchedUsers.add(user);
            }
        }

        if (!matchedUsers.isEmpty()) {
            displayResults(matchedUsers);
        } else {
            displayNoResults();
        }
    }

    private void displayResults(List<User> users) {
        resultContainer.removeAllViews(); // Xóa các kết quả cũ

        for (User user : users) {
            LinearLayout userLayout = new LinearLayout(this);
            userLayout.setOrientation(LinearLayout.VERTICAL);
            TextView userTextView = new TextView(this);
            String userInfo = "Username: " + user.getUsername() + "\n" +
                    "Password: " +user.getPassword() +"\n"+
                    "Fullname: " + user.getFullname() + "\n" +
                    "Email: " + user.getEmail();
            userTextView.setText(userInfo);
            userTextView.setTextSize(16);
            userTextView.setPadding(16, 16, 16, 16);


            userTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDeleteUser(user);
                }
            });
            userLayout.addView(userTextView);
            resultContainer.addView(userLayout);
        }
    }


    private void displayNoResults() {
        resultContainer.removeAllViews();

        TextView noResultTextView = new TextView(this);
        noResultTextView.setText("No users found.");
        noResultTextView.setTextSize(18);
        noResultTextView.setPadding(16, 16, 16, 16);

        resultContainer.addView(noResultTextView);
    }

    // Hàm xác nhận xóa user
    private void confirmDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete user " + user.getUsername() + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(user); // Xóa user sau khi xác nhận
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Hàm xóa user và cập nhật danh sách
    private void deleteUser(User user) {
        MainActivity.userList.remove(user);
        Toast.makeText(this, "User " + user.getUsername() + " deleted", Toast.LENGTH_SHORT).show();
        displayAllUsers();
    }
}
