package com.core.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "shopping_ingredient", primaryKeys = {"ingredient", "bar_name"})
public class ShoppingIngredient {


    @NonNull
    @ColumnInfo(name = "ingredient")
    private String ingredient;


    @NonNull
    @ColumnInfo(name = "bar_name")
    private String barName;


    public ShoppingIngredient(String ingredientToAdd, String ingredient) {
        this.ingredient = ingredient;
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
