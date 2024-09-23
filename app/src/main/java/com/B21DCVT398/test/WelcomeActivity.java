package com.B21DCVT398.test;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {

    private TextView welcomeMessage;
    private Button aduserbutton;
    private Button edituserbutton;
    private Button deleteuserbutton;
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcomeMessage = findViewById(R.id.welcome_message);

        String username = getIntent().getStringExtra("USERNAME");
        welcomeMessage.setText("Welcome " + username);


        aduserbutton = findViewById(R.id.add_user_button);
        logout = findViewById(R.id.logout);
        aduserbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(WelcomeActivity.this, Adduser.class);
                startActivity(intent1);
               }
            });


            edituserbutton = findViewById(R.id.edit_user_button);
            edituserbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent2 = new Intent(WelcomeActivity.this, Edituser.class);
                    intent2.putExtra("OPERATION", "Edit");
                    intent2.putExtra("USERNAME", username);
                    startActivity(intent2);
                  }
                });



                deleteuserbutton = findViewById(R.id.delete_user_button);
                deleteuserbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent3 = new Intent(WelcomeActivity.this, Deleteuser.class);
                        startActivity(intent3);
                    }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
