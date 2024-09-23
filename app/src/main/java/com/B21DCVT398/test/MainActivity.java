package com.B21DCVT398.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import com.B21DCVT398.test.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class MainActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;
    private Button home;


    public static List<User> userList = new ArrayList<>();
     static {
    userList.add(new User("B21DCVT398", "1", "Bui Quang Thanh", "thanh@gmail.com"));
    userList.add(new User("thanh1", "1", "thanh", "thanh@gmail.com"));
    userList.add(new User("thanh2", "1", "thanh", "thanh@gmail.com"));
    userList.add(new User("thanh3", "1", "thanh", "thanh@gmail.com"));
    userList.add(new User("thanh4", "1", "thanh", "thanh@gmail.com"));
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login_button);
//        MyDatabaseManager mydatabasemanager = new MyDatabaseManager(this);
//        mydatabasemanager.open();

//        User user=new User("B21DCVT398","1","Bui Quang Thanh","buithanh29117@gmail.com");


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if(CheckUser(username, password)){
                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
                    intent.putExtra("USERNAME", username);
                    intent.putExtra("PASSWORD", password);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                }



            }
        });



    }

    public static boolean CheckUser(String username, String password) {
        for (User u : userList) if (u.getUsername().equals(username) && u.getPassword().equals(password)) return true;
        return false;
    }

    public static boolean isExistUser(String username) {
        for (User u : userList) if (u.getUsername().equals(username)) return true;
        return false;
    }

    public static List<User> filter(String key) {
        return userList.stream().filter(u -> u.getUsername().startsWith(key)).sorted().collect(Collectors.toList());
    }

    public static List<User> getAllUser() {
        return new ArrayList<>(userList);
    }

    public static User getUser(String username) {
        for (User u : userList) if (u.getUsername().equals(username)) return u;
        return null;
    }

    public static void updateUser(User user) {
        for (User u : userList) if (u.getUsername().equals(user.getUsername())) u.update(user.getPassword(), user.getFullname(), user.getEmail());
    }
    public static void deleteUserByUsername(String username) {
        Iterator<User> iterator = userList.iterator();

        while (iterator.hasNext()) {
            User user = iterator.next();
            if (user.getUsername().equals(username)) {
                iterator.remove(); // Xóa user
                System.out.println("User với username: " + username + " đã được xóa.");
                return;
            }
        }

        System.out.println("Không tìm thấy user với username: " + username);
    }



}















//                User check = mydatabasemanager.getUser(username);
//                if (check != null) {
//                    Intent intent = new Intent(MainActivity.this, WelcomeActivity.class);
//                    intent.putExtra("USERNAME", username);
//                    intent.putExtra("PASSWORD", password);
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
//                }