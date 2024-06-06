package com.example.musclepal;



public class Muscle {//holds each muscles id - to link with muscle and musclename
    private String id;
    private String muscleName;

    public Muscle(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMuscleName() {
        return muscleName;
    }

    public void setMuscleName(String muscleName) {
        this.muscleName = muscleName;
    }

    public Muscle(String id, String muscleName){
        this.id = id;
        this.muscleName = muscleName;
    }
}
