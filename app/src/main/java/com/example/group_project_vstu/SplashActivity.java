package com.example.group_project_vstu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 3000; // 3 секунды

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Найдите ImageView
        ImageView logoImageView = findViewById(R.id.logoImageView);

        // Загрузите анимацию
        Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation_detailed);

        // Примените анимацию к ImageView
        logoImageView.startAnimation(logoAnimation);

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