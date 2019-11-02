package com.core.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "shopping_ingredient", primaryKeys = {"ingredient"})
public class ShoppingIngredient {


    @NonNull
    @ColumnInfo(name = "ingredient")
    private String ingredient;


    public ShoppingIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @NonNull
    public String getIngredient() {
        return ingredient;
    }

























}
