package com.example.group_project_vstu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.group_project_vstu.MainActivity;
import com.example.group_project_vstu.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3 секунды

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Найдите TextView
        TextView welcomeText = findViewById(R.id.welcomeText);

        // Загрузите анимацию
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Примените анимацию к TextView
        welcomeText.startAnimation(fadeInAnimation);

        // Задержка перед переходом в MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Создаем Intent для перехода в MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Закрываем SplashActivity
            }
        }, SPLASH_DELAY);
    }
}