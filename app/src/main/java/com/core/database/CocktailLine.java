package com.core.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cocktail_line", primaryKeys = {"ingredient","cocktail"})
public class CocktailLine {

    @NonNull
    @ColumnInfo(name = "ingredient")
    private String ingredient;

    @NonNull
    @ColumnInfo(name = "cocktail")
    private String cocktail;

    public CocktailLine(@NonNull String ingredient, @NonNull String cocktail, int amount, String amountLiteral) {
        this.ingredient = ingredient;
        this.cocktail = cocktail;
        this.amount = amount;
        this.amountLiteral = amountLiteral;
    }

    private int amount;
    private String amountLiteral;


    @NonNull
    public String getIngredient() {
        return ingredient;
    }

    @NonNull
    public String getCocktail() {
        return cocktail;
    }

    public int getAmount() {
        return amount;
    }

    public String getAmountLiteral() {
        return amountLiteral;
    }

}
