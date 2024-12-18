package com.example.group_project_vstu;

public class NewsItem {
    private String title;
    private String description;
    private String link; // Ссылка, связанная с новостью

    public NewsItem(String title, String description, String link) {
        this.title = title;
        this.description = description;
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }
}