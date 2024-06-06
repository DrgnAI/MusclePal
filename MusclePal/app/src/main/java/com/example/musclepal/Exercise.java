package com.example.musclepal;

public class Exercise {
    private String title;
    private String muscles;
    private String CoverImage;


    private String description;

    public Exercise(){}
//exercise class for getting and setting items for each exercise
    public Exercise(String title, String muscles, String CoverImage, String description){
        this.title = title;
        this.muscles = muscles;
        this.CoverImage = CoverImage;
        this.description = description;



    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getMuscles() {

        return muscles;
    }

    public void setMuscles(String muscles) {

        this.muscles = muscles;
    }

    public String getCoverImage() {

        return CoverImage;
    }

    public void setCoverImage(String coverImage) {

        CoverImage = coverImage;
    }

}
