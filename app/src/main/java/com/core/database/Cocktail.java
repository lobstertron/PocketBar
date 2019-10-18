package com.core.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cocktail")
public class Cocktail {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "directions")
    private String directions;

    @ColumnInfo(name = "glass")
    private String glass;

    @ColumnInfo(name = "video")
    private String video;

    @ColumnInfo(name = "alcoholic")
    private String alcoholic;

    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    @ColumnInfo(name = "type")
    private String type;

    public Cocktail(String name, String directions) {
        this.name = name;
        this.directions = directions;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public void setGlass(String glass) {
        this.glass = glass;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public void setAlcoholic(String alcoholic) {
        this.alcoholic = alcoholic;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getGlass() {
        return glass;
    }

    public String getVideo() {
        return video;
    }

    public String getAlcoholic() {
        return alcoholic;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDirections() {
        return directions;
    }
}
