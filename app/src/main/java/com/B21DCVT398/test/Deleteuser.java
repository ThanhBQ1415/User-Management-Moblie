package com.B21DCVT398.test;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.B21DCVT398.test.dbConnect.MyDatabaseManager;
import com.B21DCVT398.test.model.User;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Deleteuser extends AppCompatActivity {
    private EditText usernameInput;
    private Button searchButton;
    private LinearLayout resultContainer;
    private Button home;
    private MyDatabaseManager dbManager;  // Declare MyDatabaseManager instance

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete);

        usernameInput = findViewById(R.id.input_username);
        searchButton = findViewById(R.id.button_search);
        resultContainer = findViewById(R.id.resultContainer);
        home = findViewById(R.id.welcome_activity);

        // Initialize the MyDatabaseManager
        dbManager = new MyDatabaseManager(this);
        dbManager.open();  // Open the database

        displayAllUsers();  // Display all users when the activity starts

        // Set up home button click event
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Deleteuser.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });

        // Set up search button click event
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameInput.getText().toString().trim();
                if (!username.isEmpty()) {
                    searchUser(username);  // Search for users and display results
                } else {
                    Toast.makeText(Deleteuser.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Display all users from SQLite
    private void displayAllUsers() {
        List<User> allUsers = dbManager.getAllUsers();  // Fetch all users from the database
        displayResults(allUsers);
    }

    // Search for users by username from SQLite
    private void searchUser(String username) {
        User matchedUser = dbManager.getUser(username);  // Fetch the user by username
        List<User> matchedUsers = new ArrayList<>();

        if (matchedUser != null) {
            matchedUsers.add(matchedUser);  // If a user is found, add to the list
        }

        if (!matchedUsers.isEmpty()) {
            displayResults(matchedUsers);  // Display found users
        } else {
            displayNoResults();  // Display no result message
        }
    }

    // Display the search results
    private void displayResults(List<User> users) {
        resultContainer.removeAllViews(); // Clear previous results

        for (User user : users) {
            LinearLayout userLayout = new LinearLayout(this);
            userLayout.setOrientation(LinearLayout.HORIZONTAL);
            userLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // TextView to display user details
            TextView userTextView = new TextView(this);
            String userInfo = "Username: " + user.getUsername() + "\n" +
                    "Password: " + user.getPassword() + "\n" +
                    "Fullname: " + user.getFullname() + "\n" +
                    "Email: " + user.getEmail() + "\n" +
                    "Phone: " + user.getPhone() + "\n" +
                    "Date of Birth: " + user.getDob() + "\n" +
                    "Gender: " + user.getGender();

            userTextView.setText(userInfo);
            userTextView.setTextSize(16);
            userTextView.setPadding(16, 16, 16, 16);
            userTextView.setLayoutParams(new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1f // Use weight to fill available space
            ));

            // Create a TextView for the ellipses
            TextView ellipsisTextView = new TextView(this);
            ellipsisTextView.setText("...");
            ellipsisTextView.setTextSize(20);
            ellipsisTextView.setPadding(16, 16, 16, 16);

            // Set click listener on ellipsis to confirm edit or delete
            ellipsisTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmDeleteOrEditUser(user);
                }
            });

            userLayout.addView(userTextView);
            userLayout.addView(ellipsisTextView); // Add the ellipses TextView
            resultContainer.addView(userLayout);
        }
    }

    // Handle case when no users are found
    private void displayNoResults() {
        resultContainer.removeAllViews();

        TextView noResultTextView = new TextView(this);
        noResultTextView.setText("No users found.");
        noResultTextView.setTextSize(18);
        noResultTextView.setPadding(16, 16, 16, 16);

        resultContainer.addView(noResultTextView);
    }

    // Confirm delete or edit user dialog
    private void confirmDeleteOrEditUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("User Options")
                .setMessage("What would you like to do with user " + user.getUsername() + "?")
                .setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showEditDialog(user);  // Open edit dialog
                    }
                })
                .setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        confirmDeleteUser(user);  // Confirm deletion
                    }
                })
                .setNeutralButton(android.R.string.cancel, null)
                .show();
    }

    // Confirm deletion of a user
    private void confirmDeleteUser(User user) {
        new AlertDialog.Builder(this)
                .setTitle("Delete User")
                .setMessage("Are you sure you want to delete user " + user.getUsername() + "?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteUser(user);  // Delete the user after confirmation
                    }
                })
                .setNegativeButton(android.R.string.cancel, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    // Show edit dialog to modify user details
    private void showEditDialog(User user) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        dialogBuilder.setView(dialogView);

        EditText usernameEditText = dialogView.findViewById(R.id.username_input);
        EditText passwordEditText = dialogView.findViewById(R.id.password_input);
        EditText fullnameEditText = dialogView.findViewById(R.id.fullname_input);
        EditText emailEditText = dialogView.findViewById(R.id.email_input);
        EditText phoneEditText = dialogView.findViewById(R.id.phone_input);
        EditText dobInput = dialogView.findViewById(R.id.dob_input);
        Spinner genderSpinner = dialogView.findViewById(R.id.gender_spinner);

        // Fill current user information into fields
        usernameEditText.setText(user.getUsername());
        passwordEditText.setText(user.getPassword());
        fullnameEditText.setText(user.getFullname());
        emailEditText.setText(user.getEmail());
        phoneEditText.setText(user.getPhone());
        dobInput.setText(user.getDob());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(adapter);

        // Set the current gender selection
        if (user.getGender().equals("Male")) {
            genderSpinner.setSelection(0);
        } else {
            genderSpinner.setSelection(1);
        }

        // Set up DatePickerDialog for date of birth
        Calendar calendar = Calendar.getInstance();
        dobInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Deleteuser.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                                dobInput.setText(selectedYear + "-" + (selectedMonth + 1) + "-" + selectedDay);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        // Set up save button
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Save edited user data
                user.setUsername(usernameEditText.getText().toString().trim());
                user.setPassword(passwordEditText.getText().toString().trim());
                user.setFullname(fullnameEditText.getText().toString().trim());
                user.setEmail(emailEditText.getText().toString().trim());
                user.setPhone(phoneEditText.getText().toString().trim());
                user.setDob(dobInput.getText().toString().trim());
                user.setGender(genderSpinner.getSelectedItem().toString());

                // Update user in database
                dbManager.updateUser(user);
                displayAllUsers();  // Refresh user list after update
                Toast.makeText(Deleteuser.this, "User updated successfully", Toast.LENGTH_SHORT).show();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Cancel the dialog
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();  // Close the database when activity is destroyed
    }
//    // Delete a user from the database
    private void deleteUser(User user) {
        dbManager.deleteUser(user.getUsername());  // Delete the user by username
        displayAllUsers();  // Refresh the list after deletion
        Toast.makeText(this, "User deleted successfully", Toast.LENGTH_SHORT).show();
    }
}
