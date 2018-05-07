package asus.com.example.asus.cardview;

/**
 * Created by ASUS on 27.03.2018.
 */

public class Album {
    private String name;
    private int numOfDays;
    private int thumbnail;

    public Album(String name, String s, String january) {
    }

    public Album(String name, int numOfDays, int thumbnail) {
        this.name = name;
        this.numOfDays = numOfDays;
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumOfSongs() {
        return numOfDays;
    }

    public void setNumOfSongs(int numOfSongs) {
        this.numOfDays = numOfSongs;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}