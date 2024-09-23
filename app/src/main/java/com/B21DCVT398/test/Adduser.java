package com.B21DCVT398.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.B21DCVT398.test.model.User;

public class Adduser extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText fullnameEditText;
    private EditText emailEditText;
    private Button ResetButton;
    private Button AddButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        usernameEditText = findViewById(R.id.username_input);
        passwordEditText = findViewById(R.id.password_input);
        fullnameEditText = findViewById(R.id.fullname_input);
        emailEditText = findViewById(R.id.email_input);
        AddButton =  findViewById(R.id.add_button);
        ResetButton= findViewById(R.id.reset_button);

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernameEditText.setText("");
                passwordEditText.setText("");
                fullnameEditText.setText("");
                emailEditText.setText("");
            }
        });

        AddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=usernameEditText.getText().toString();
                String password=passwordEditText.getText().toString();
                String fullname=fullnameEditText.getText().toString();
                String email=emailEditText.getText().toString();
                if(username.isEmpty() || password.isEmpty() ){
                    Toast.makeText(Adduser.this, "Username and Password are required", Toast.LENGTH_LONG).show();
                }else{
                    User user= new User(username,password,fullname,email);
                    MainActivity.userList.add(user);
                Intent intent = new Intent(Adduser.this, WelcomeActivity.class);
                startActivity(intent);}
            }
        });

    }
}
