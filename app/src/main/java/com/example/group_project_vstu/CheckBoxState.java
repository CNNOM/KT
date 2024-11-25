package com.example.group_project_vstu;

public class CheckBoxState {
    private int userId;
    private int day;
    private boolean isChecked;

    public CheckBoxState(int userId, int day, boolean isChecked) {
        this.userId = userId;
        this.day = day;
        this.isChecked = isChecked;
    }

    public int getUserId() {
        return userId;
    }

    public int getDay() {
        return day;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}