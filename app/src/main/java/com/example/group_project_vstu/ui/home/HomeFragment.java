package com.example.group_project_vstu.ui.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_vstu.NewsAdapter;
import com.example.group_project_vstu.NewsItem;
import com.example.group_project_vstu.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Анимация текста "Добро пожаловать!"
        animateTypingText(binding.textViewGreeting, "Добро пожаловать!");

        // Создаем список новостей с ссылками
        List<NewsItem> newsList = generateNews();

        // Настраиваем RecyclerView для отображения новостей
        RecyclerView recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new NewsAdapter(newsList));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Метод для генерации новостей с ссылками
    private List<NewsItem> generateNews() {
        List<NewsItem> newsList = new ArrayList<>();

        // Добавляем новости с ссылками
        newsList.add(new NewsItem("Кибершкола открывает новый курс!", "Мы рады сообщить, что в нашей кибершколе открылся новый курс по программированию на Python. Присоединяйтесь к нам и учитесь у лучших преподавателей.", "https://stepik.org/course/67/"));
        newsList.add(new NewsItem("Вебинар по кибербезопасности", "20 октября состоится вебинар по основам кибербезопасности. Не пропустите возможность узнать больше о защите своих данных в интернете.", "https://www.infowatch.ru/resursy/vebinary"));
        newsList.add(new NewsItem("Конкурс для учеников", "Мы объявляем конкурс для наших учеников! Примите участие и выиграйте ценные призы. Подробности на нашем сайте.", "https://www.kaggle.com/competitions"));
        newsList.add(new NewsItem("Новые преподаватели", "Мы рады представить вам наших новых преподавателей, которые присоединились к нашей команде. Они принесут новые знания и опыт в нашу школу.", "https://cchgeu.ru/studentu/nayti-kontakty-prepodavatelya/"));
        newsList.add(new NewsItem("Онлайн-лекции", "Все лекции теперь доступны онлайн. Вы можете учиться в любое удобное для вас время.", "https://www.lektorium.tv/"));

        return newsList;
    }

    // Метод для анимации текста "типа набора текста"
    private void animateTypingText(TextView textView, String text) {
        textView.setText(""); // Очищаем текст
        final int[] index = {0};
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (index[0] < text.length()) {
                    textView.append(String.valueOf(text.charAt(index[0])));
                    index[0]++;
                    handler.postDelayed(this, 100); // Задержка между символами (100 мс)
                }
            }
        };
        handler.postDelayed(runnable, 500); // Задержка перед началом анимации (500 мс)
    }
}