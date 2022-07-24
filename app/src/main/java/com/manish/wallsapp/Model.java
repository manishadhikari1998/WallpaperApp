package com.manish.wallsapp;

public class Model {
    private  String imageUrl;
    private  int likes, views, downloads;

    public Model(String imageUrl, int likes, int views, int downloads) {
        this.imageUrl = imageUrl;
        this.likes = likes;
        this.views = views;
        this.downloads = downloads;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getLikes() {
        return likes;
    }

    public int getViews() {
        return views;
    }

    public int getDownloads() {
        return downloads;
    }
}
