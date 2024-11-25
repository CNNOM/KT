package com.example.group_project_vstu.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.group_project_vstu.R;
import com.example.group_project_vstu.User;

import java.util.List;

public class TableRowBuilder {

    private Context context;

    public TableRowBuilder(Context context) {
        this.context = context;
    }

    public TableRow build(User student, List<Integer> saturdays) {
        TableRow row = new TableRow(context);

        TextView usernameTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_textview, row, false);
        usernameTextView.setText(student.getUsername());
        usernameTextView.setTextSize(18);
        usernameTextView.setGravity(android.view.Gravity.CENTER); // Выравнивание по центру
        row.addView(usernameTextView);

        for (int day : saturdays) {
            CheckBox checkBox = (CheckBox) LayoutInflater.from(context).inflate(R.layout.item_checkbox, row, false);
            row.addView(checkBox);
        }

        return row;
    }
}