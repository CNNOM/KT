package com.example.group_project_vstu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewEmail;
    private Button buttonChangePassword, buttonGoToMain, buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        textViewUsername = findViewById(R.id.textViewUsername);
        textViewEmail = findViewById(R.id.textViewEmail);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);
        buttonGoToMain = findViewById(R.id.buttonGoToMain);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Получаем данные пользователя (логин и почта) из SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        String email = sharedPreferences.getString("email", "");

        // Устанавливаем данные в TextView
        textViewUsername.setText("Username: " + username);
        textViewEmail.setText("Email: " + email);

        // Обработка нажатия на кнопку "Change Password"
        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на активность для изменения пароля
                startActivity(new Intent(ProfileActivity.this, ChangePasswordActivity.class));
            }
        });

        // Обработка нажатия на кнопку "Go to Main"
        buttonGoToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Переход на начальную страницу
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

        // Обработка нажатия на кнопку "Logout"
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Очищаем SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.apply();

                // Переходим на активность входа
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish(); // Закрываем текущую активность
            }
        });
    }
}