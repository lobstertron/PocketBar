package com.core.cocktail;

import com.core.database.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Bar {
    public Bar() {
        inventory = new ArrayList<>();
    }

    public List<Ingredient> getInventory() {
        return inventory;
    }

    private List<Ingredient> inventory;

    public boolean addIngredient(Ingredient ingredient) {
        if (inventory.contains(ingredient)) {
            return false;
        }
        else {
            return inventory.add(ingredient);
        }
    }

    public boolean removeIngredient(Ingredient ingredient) {
        if (inventory.contains(ingredient)) {
            return inventory.remove(ingredient);
        }
        else {
            return false;
        }
    }
}
