package com.core.cocktail;

import java.util.List;

public class Cocktail {
    private String name;
    private List<CocktailLine> cocktailLineList;
    private String instructions;

    public Cocktail(String name, List<CocktailLine> cocktailLineList, String instructions) {
        this.name = name;
        this.cocktailLineList = cocktailLineList;
        this.instructions = instructions;
    }
}
