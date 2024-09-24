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
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.B21DCVT398.test.model.User;

import java.util.ArrayList;
import java.util.Calendar;
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
        home = findViewById(R.id.welcome_activity);

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

    // Display all users
    private void displayAllUsers() {
        displayResults(MainActivity.userList);
    }

    // Search for users by username
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

    // Confirm edit or deletion of a user
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

        dialogBuilder.setTitle("Edit User");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Get the edited information
                String newUsername = usernameEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();

                String newFullname = fullnameEditText.getText().toString();
                String newEmail = emailEditText.getText().toString();
                String newPhone = phoneEditText.getText().toString();
                String newDob = dobInput.getText().toString();
                String newGender = genderSpinner.getSelectedItem().toString();

                // Update user information
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                user.setFullname(newFullname);
                user.setEmail(newEmail);
                user.setPhone(newPhone);
                user.setDob(newDob);
                user.setGender(newGender);

                Toast.makeText(Deleteuser.this, "User information updated", Toast.LENGTH_SHORT).show();
                displayAllUsers();  // Refresh the user list
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    // Delete user
    private void deleteUser(User user) {
        MainActivity.userList.remove(user); // Remove the user from the list
        Toast.makeText(this, "User " + user.getUsername() + " deleted", Toast.LENGTH_SHORT).show();
        displayAllUsers(); // Refresh the user list
    }
}
