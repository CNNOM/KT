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

        // Проверяем, залогинен ли пользователь
        if (username.isEmpty() || email.isEmpty()) {
            // Устанавливаем дефолтное оповещение
            textViewUsername.setText("Username: Not logged in");
            textViewEmail.setText("Email: Not logged in");

            // Блокируем кнопки "Change Password" и "Logout"
            buttonChangePassword.setEnabled(false);
            buttonLogout.setEnabled(false);

            // Устанавливаем прозрачность для неактивных кнопок
            buttonChangePassword.setAlpha(0.5f);
            buttonLogout.setAlpha(0.5f);
        } else {
            // Устанавливаем данные в TextView
            textViewUsername.setText("Username: " + username);
            textViewEmail.setText("Email: " + email);

            // Разблокируем кнопки "Change Password" и "Logout"
            buttonChangePassword.setEnabled(true);
            buttonLogout.setEnabled(true);

            // Устанавливаем полную прозрачность для активных кнопок
            buttonChangePassword.setAlpha(1.0f);
            buttonLogout.setAlpha(1.0f);
        }

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
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                finish(); // Закрываем текущую активность
            }
        });
    }
}