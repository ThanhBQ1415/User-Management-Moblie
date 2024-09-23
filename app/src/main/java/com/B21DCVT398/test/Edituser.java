package com.B21DCVT398.test;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.B21DCVT398.test.model.User;

import java.util.ArrayList;

public class Edituser extends AppCompatActivity {

    private ListView userListView;
    private ArrayAdapter<String> userAdapter;
    private ArrayList<String> usernamesList;
    private Button home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit);

        userListView = findViewById(R.id.user_list_view);
        home = findViewById(R.id.home_button);

        // Tạo danh sách tên người dùng từ danh sách user
        usernamesList = new ArrayList<>();
        for (User user : MainActivity.userList) {
            usernamesList.add(user.getUsername());
        }

        // Hiển thị danh sách người dùng lên ListView
        userAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, usernamesList);
        userListView.setAdapter(userAdapter);

        // Bắt sự kiện khi bấm vào một user trong danh sách
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy thông tin user được chọn
                String selectedUsername = usernamesList.get(position);
                User selectedUser = null;
                for (User user : MainActivity.userList) {
                    if (user.getUsername().equals(selectedUsername)) {
                        selectedUser = user;
                        break;
                    }
                }

                if (selectedUser != null) {
                    // Hiển thị dialog để chỉnh sửa user
                    showEditUserDialog(selectedUser, position);
                }
            }
        });

        // Nút Home để quay lại màn hình chính
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Edituser.this, WelcomeActivity.class);
                startActivity(intent);
            }
        });
    }

    // Hàm hiển thị dialog để chỉnh sửa thông tin user
    private void showEditUserDialog(User user, int position) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_user, null);
        dialogBuilder.setView(dialogView);

        EditText usernameEditText = dialogView.findViewById(R.id.username_input);
        EditText passwordEditText = dialogView.findViewById(R.id.password_input);
        EditText fullnameEditText = dialogView.findViewById(R.id.fullname_input);
        EditText emailEditText = dialogView.findViewById(R.id.email_input);

        // Điền thông tin người dùng hiện tại vào các trường
        usernameEditText.setText(user.getUsername());
        passwordEditText.setText(user.getPassword());
        fullnameEditText.setText(user.getFullname());
        emailEditText.setText(user.getEmail());

        String Username = usernameEditText.getText().toString();
        String Password = passwordEditText.getText().toString();
        String Fullname = fullnameEditText.getText().toString();
        String Email = emailEditText.getText().toString();

        dialogBuilder.setTitle("Edit User");
        dialogBuilder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Lấy thông tin đã chỉnh sửa
                String newUsername = usernameEditText.getText().toString();
                String newPassword = passwordEditText.getText().toString();
                String newFullname = fullnameEditText.getText().toString();
                String newEmail = emailEditText.getText().toString();

                // Cập nhật thông tin người dùng trong danh sách
                user.setUsername(newUsername);
                user.setPassword(newPassword);
                user.setFullname(newFullname);
                user.setEmail(newEmail);

                // Cập nhật tên trong danh sách hiển thị
                usernamesList.set(position, newUsername);
                userAdapter.notifyDataSetChanged();

                Toast.makeText(Edituser.this, "User updated successfully!", Toast.LENGTH_SHORT).show();
            }
        });

        // Thêm nút "Reset" với hành động custom để không đóng dialog
        dialogBuilder.setNegativeButton("Reset", null); // Set null to prevent automatic dialog close

        // Tạo dialog và hiển thị
        AlertDialog dialog = dialogBuilder.create();
        dialog.show();

        // Lấy tham chiếu tới nút "Reset" sau khi dialog hiển thị
        Button resetButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        if (resetButton != null) {
            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Reset các trường EditText mà không đóng dialog
                    usernameEditText.setText(Username);
                    passwordEditText.setText( Password);
                    fullnameEditText.setText( Fullname);
                    emailEditText.setText( Email);
                }
            });
        }
    }
}
