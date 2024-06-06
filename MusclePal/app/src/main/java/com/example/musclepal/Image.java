package com.example.musclepal;

public class Image {//holds each images id - to link with exercise and url
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    private String id;
    private String imageURL;

    public Image(){}


    public Image(String id, String imageURL){
        this.id = id;
        this.imageURL = imageURL;
    }
}

