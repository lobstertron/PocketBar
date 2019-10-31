package com.core.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cocktail_line")
public class CocktailLine {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "cocktailId")
    private int cocktailId;

    @ColumnInfo(name = "ingredientId")
    private int ingredientId;

    @ColumnInfo(name = "amount_literal")
    private String amount_literal;

    @ColumnInfo(name = "amount")
    private double amount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCocktailId() {
        return cocktailId;
    }

    public void setCocktailId(int cocktailId) {
        this.cocktailId = cocktailId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getAmount_literal() {
        return amount_literal;
    }

    public void setAmount_literal(String amount_literal) {
        this.amount_literal = amount_literal;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }




}
