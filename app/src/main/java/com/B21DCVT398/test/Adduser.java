package com.B21DCVT398.test;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.B21DCVT398.test.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Calendar;

public class Adduser extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText fullnameEditText;
    private EditText emailEditText;
    private EditText phoneEditText;
    private EditText dobEditText;
    private RadioGroup genderGroup;
    private Button resetButton;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        // Initialize the EditText and Button fields
        usernameEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);
        fullnameEditText = findViewById(R.id.fullname_input);
        emailEditText = findViewById(R.id.email_input);
        phoneEditText = findViewById(R.id.phone_input);
        dobEditText = findViewById(R.id.dob_input);
        genderGroup = findViewById(R.id.gender_group);
        addButton = findViewById(R.id.add_button);
        resetButton = findViewById(R.id.reset_button);

        // Set up DatePickerDialog for dobEditText
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Create a DatePickerDialog
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Adduser.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                // Set the selected date in dobEditText (month+1 because it's 0-indexed)
                                dobEditText.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                            }
                        },
                        year, month, day);

                // Show the date picker dialog
                datePickerDialog.show();
            }
        });

        // Set onClickListener for Reset Button
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Clear all input fields
                usernameEditText.setText("");
                passwordEditText.setText("");
                fullnameEditText.setText("");
                emailEditText.setText("");
                phoneEditText.setText("");
                dobEditText.setText("");
                genderGroup.clearCheck();  // Clear selected gender
//                Intent intent = new Intent(Adduser.this, WelcomeActivity.class);
//                startActivity(intent);
            }
        });

        // Set onClickListener for Add Button
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Retrieve the values from input fields
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String fullname = fullnameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String phone = phoneEditText.getText().toString();
                String dobString = dobEditText.getText().toString();
                int selectedGenderId = genderGroup.getCheckedRadioButtonId();

                // Validate mandatory fields
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(Adduser.this, "Username and Password are required", Toast.LENGTH_LONG).show();
                    return;
                }

                // Retrieve selected gender
                String gender = "";
                if (selectedGenderId == R.id.gender_male) {
                    gender = "nam";
                } else if (selectedGenderId == R.id.gender_female) {
                    gender = "nữ";
                }

                // Create a new User object
                User user = new User(username, password, fullname, email, phone, dobString, gender);

                // Add the user to the user list (assume MainActivity has a static userList)
                MainActivity.userList.add(user);

                // Redirect to WelcomeActivity
                Intent intent = new Intent(Adduser.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }
}