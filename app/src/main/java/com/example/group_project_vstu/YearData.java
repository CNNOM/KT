package com.example.group_project_vstu;

import org.threeten.bp.DayOfWeek;
import org.threeten.bp.LocalDate;
import org.threeten.bp.Month;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YearData {
    private Map<Integer, Map<Integer, List<Integer>>> years;

    public YearData() {
        years = new HashMap<>();
    }

    public void addYear(int year) {
        if (!years.containsKey(year)) {
            years.put(year, new HashMap<>());
        }
    }

    public void addMonth(int year, int month) {
        if (years.containsKey(year)) {
            if (!years.get(year).containsKey(month)) {
                years.get(year).put(month, new ArrayList<>());
            }
        }
    }

    public void addSaturday(int year, int month, int day) {
        if (years.containsKey(year) && years.get(year).containsKey(month)) {
            years.get(year).get(month).add(day);
        }
    }

    public Map<Integer, Map<Integer, List<Integer>>> getYears() {
        return years;
    }

    public void populateSaturdays(int startYear, int endYear) {
        for (int year = startYear; year <= endYear; year++) {
            addYear(year);
            for (Month month : Month.values()) {
                addMonth(year, month.getValue());
                LocalDate date = LocalDate.of(year, month, 1);
                while (date.getMonth() == month) {
                    if (date.getDayOfWeek() == DayOfWeek.SATURDAY) {
                        addSaturday(year, month.getValue(), date.getDayOfMonth());
                    }
                    date = date.plusDays(1);
                }
            }
        }
    }

    public List<Integer> getSaturdays(int year, int month) {
        if (years.containsKey(year) && years.get(year).containsKey(month)) {
            return years.get(year).get(month);
        }
        return new ArrayList<>();
    }
}