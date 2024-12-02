package com.example.group_project_vstu;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.group_project_vstu.PasswordUtils.hashPasswordUtilit;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText editTextCurrentPassword, editTextNewPassword, editTextConfirmPassword;
    private Button buttonChangePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        editTextCurrentPassword = findViewById(R.id.editTextCurrentPassword);
        editTextNewPassword = findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        buttonChangePassword = findViewById(R.id.buttonChangePassword);

        buttonChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = editTextCurrentPassword.getText().toString();
                String newPassword = editTextNewPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString();

                // Проверка валидности данных
                if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(ChangePasswordActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show();
                } else if (!isStrongPassword(newPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Password must be at least 4 characters", Toast.LENGTH_LONG).show();
                } else {
                    // Хэширование нового пароля
                    String hashedNewPassword = hashPassword(newPassword);

                    // Проверка текущего пароля
                    if (checkCurrentPassword(currentPassword)) {
                        // Обновление пароля в базе данных
                        updatePassword(hashedNewPassword);
                        Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(ChangePasswordActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isStrongPassword(String password) {
        // Проверка на длину пароля
        return password.length() > 4;
    }

    private String hashPassword(String password) {
        // Хэширование пароля (используйте тот же метод, что и в RegisterActivity)
        return hashPasswordUtilit(password);
    }

    private boolean checkCurrentPassword(String currentPassword) {
        // Получаем текущего пользователя из SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Хэшируем текущий пароль для проверки
        String hashedCurrentPassword = hashPasswordUtilit(currentPassword);

        // Получаем доступ к базе данных
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();

        // Используем ExecutorService для выполнения запроса в фоновом потоке
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Проверяем, совпадает ли текущий пароль с паролем в базе данных
                User user = userDao.getUser(username, hashedCurrentPassword);
                if (user != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePasswordActivity.this, "Current password is correct", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ChangePasswordActivity.this, "Current password is incorrect", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return false;
    }

    private void updatePassword(String hashedNewPassword) {
        // Получаем текущего пользователя из SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        // Получаем доступ к базе данных
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();

        // Используем ExecutorService для выполнения запроса в фоновом потоке
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                // Обновляем пароль пользователя в базе данных
                userDao.updatePassword(username, hashedNewPassword);
            }
        });
    }
}