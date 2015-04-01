package com.cuca.example;

/**
 * Created by marko on 31.03.15..
 */
public class ImageItem {

    private String imageURL;
    private String text;

    private double longitude;
    private double latitude;

    public void setImageURL(String imageURL){
        this.imageURL = imageURL;
    }

    public void setText(String text){
        this.text = text;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void  setLatitude(double latitude){
        this.latitude = latitude;
    }

    public String getImageURL(){
        return imageURL;
    }

    public String getText(){
        return text;
    }

    public double getLongitude(){
        return longitude;
    }

    public double getLatitude(){
        return latitude;
    }

}
