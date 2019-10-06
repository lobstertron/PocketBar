package com.core.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "bar_ingredient", primaryKeys = {"ingredient", "bar_name"})
public class BarIngredient {


    @NonNull
    @ColumnInfo(name = "ingredient")
    private String ingredient;

    @NonNull
    @ColumnInfo(name = "bar_name")
    private String barName;

    public BarIngredient(String ingredient, String barName) {
        this.ingredient = ingredient;
        this.barName = barName;
    }

    @NonNull
    public String getIngredient() {
        return ingredient;
    }

    @NonNull
    public String getBarName() {
        return barName;
    }

}
