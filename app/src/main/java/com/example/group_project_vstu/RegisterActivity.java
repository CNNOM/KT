package com.example.group_project_vstu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPassword;
    private Button buttonRegister;
    private Spinner spinnerRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonRegister = findViewById(R.id.buttonRegister);
        spinnerRole = findViewById(R.id.spinnerRole);

        // Настройка Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.roles_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(adapter);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                String role = spinnerRole.getSelectedItem().toString();

                // Проверка валидности данных
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                } else if (username.length() <= 3) {
                    Toast.makeText(RegisterActivity.this, "Username must be longer than 3 characters", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(RegisterActivity.this, "Invalid email format", Toast.LENGTH_SHORT).show();
                } else if (password.length() <= 4) {
                    Toast.makeText(RegisterActivity.this, "Password must be longer than 4 characters", Toast.LENGTH_SHORT).show();
                } else {
                    // Хэширование пароля
                    String hashedPassword = hashPassword(password);

                    // Сохранение данных пользователя
                    saveUserData(username, email, hashedPassword, role);
                    Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Проверка на валидность email
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private String hashPassword(String password) {
        try {
            // Создаем экземпляр MessageDigest для алгоритма SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            // Хэшируем пароль
            byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            // Преобразуем хэш в строку в шестнадцатеричном формате
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void saveUserData(String username, String email, String hashedPassword, String role) {
        User user;
        switch (role) {
            case "Teacher":
                user = new Teacher(username, email, hashedPassword);
                break;
            case "Parent":
                user = new Parent(username, email, hashedPassword);
                break;
            case "Student":
                user = new Student(username, email, hashedPassword);
                break;
            default:
                user = new User(username, email, hashedPassword, "user");
                break;
        }

        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        UserDao userDao = db.userDao();

        // Используем ExecutorService для выполнения вставки в фоновом потоке
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                userDao.insert(user);
            }
        });
    }
}