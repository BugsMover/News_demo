package com.example.new_demo;

public class Meizi {

    private String imageUrl;
    private String title;
    private String name;
    private String favorites;
    private String comments;

    private String links;

    Meizi(String imageUrl, String title, String name, String favorites, String comments,String links) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.name = name;
        this.favorites = favorites;
        this.comments = comments;
        this.links = links;
    }

    public String getImageUrl() { return imageUrl; }
    public String getTitle() { return title; }
    public String getName() { return name; }
    public String getFavorites() { return favorites; }
    public String getComments() { return comments; }
    public String getLinks() { return links; }
}
