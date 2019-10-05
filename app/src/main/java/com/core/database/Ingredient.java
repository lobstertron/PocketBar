package com.core.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredient")
public class Ingredient {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    public Ingredient(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
