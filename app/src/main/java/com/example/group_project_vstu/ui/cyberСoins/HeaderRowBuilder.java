package com.example.group_project_vstu.ui.cyberСoins;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.group_project_vstu.R;

import java.util.List;

public class HeaderRowBuilder {

    private Context context;

    public HeaderRowBuilder(Context context) {
        this.context = context;
    }

    public TableRow build(List<Integer> saturdays) {
        TableRow headerRow = new TableRow(context);

        String[] headers = {"Username"};
        for (int day : saturdays) {
            headers = addElement(headers, String.valueOf(day));
        }

        for (String header : headers) {
            TextView headerTextView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_textview, headerRow, false);
            headerTextView.setText(header);
            headerTextView.setTextSize(20);
            headerTextView.setTypeface(null, Typeface.BOLD);
            headerTextView.setGravity(android.view.Gravity.CENTER); // Выравнивание по центру
            headerRow.addView(headerTextView);
        }

        return headerRow;
    }

    private String[] addElement(String[] originalArray, String newElement) {
        String[] newArray = new String[originalArray.length + 1];
        System.arraycopy(originalArray, 0, newArray, 0, originalArray.length);
        newArray[originalArray.length] = newElement;
        return newArray;
    }
}