package com.core.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cocktail")
public class Cocktail {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "instructions")
    private String instructions;

    public Cocktail(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public String getInstructions() {
        return instructions;
    }
}
