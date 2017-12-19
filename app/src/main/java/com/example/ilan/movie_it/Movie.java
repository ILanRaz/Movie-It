package com.example.ilan.movie_it;

public class Movie {

    private int resourceID;
    private int ID;
    private String movieName;
    private String imdbRatio;
    private String releaseDate;
    private String budget;
    private String about;
    private String imagepath;

    public Movie(int resourceID, String movieName, String imdbRatio, String releaseDate, String budget, String about) {
        this.resourceID = resourceID;
        this.movieName = movieName;
        this.imdbRatio = imdbRatio;
        this.releaseDate = releaseDate;
        this.budget = budget;
        this.about = about;
    }

    public Movie(int ID, String movieName, String imdbRatio, String releaseDate, String budget, String about, String imagepath) {
        this.ID = ID;
        this.movieName = movieName;
        this.imdbRatio = imdbRatio;
        this.releaseDate = releaseDate;
        this.budget = budget;
        this.about = about;
        this.imagepath = imagepath;
    }

    public int getResourceID() {
        return resourceID;
    }

    public void setResourceID(int resourceID) {
        this.resourceID = resourceID;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getImdbRatio() {
        return imdbRatio;
    }

    public void setImdbRatio(String imdbRatio) {
        this.imdbRatio = imdbRatio;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public int getID() {
        return ID;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }
}

