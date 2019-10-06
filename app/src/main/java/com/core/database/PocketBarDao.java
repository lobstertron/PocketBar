package com.core.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * It might be a good idea to split up the daos for the project later on by each table, object
 * etc. But it seems to me that it makes sense for an application to keep all the query code in
 * one centralized place.
 */
@Dao
public interface PocketBarDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Ingredient ingredient);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cocktail cocktail);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CocktailLine cocktailLine);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Bar bar);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(BarIngredient barIngredient);

    @Query("DELETE FROM ingredient")
    void deleteAllIngredients();

    @Query("DELETE FROM cocktail")
    void deleteAllCocktails();

    @Query("DELETE FROM cocktail_line")
    void deleteAllCocktailLines();

    @Query("DELETE FROM bar")
    void deleteAllBars();

    @Query("DELETE FROM bar_ingredient")
    void deleteAllBarIngredients();

    @Query("SELECT * from ingredient")
    List<Ingredient> getAllIngredients();

    @Query("SELECT * from bar_ingredient")
    List<BarIngredient> getAllBarIngredients();

//    @Query("SELECT * from ingredient inner join (select * from bar_ingredient where bar_name = 'main_bar') as my_bar_ingredients on my_bar_ingredients.ingredient = ingredient.name ORDER BY name ASC")
//    List<Ingredient> getMyBarIngredients();

    @Query("SELECT * from bar_ingredient where bar_name = 'main_bar' ORDER BY ingredient ASC")
    List<BarIngredient> getMyBarIngredients();

    @Query("SELECT * from cocktail ORDER BY name ASC")
    List<Cocktail> getAllCocktails();

    @Query("SELECT * FROM cocktail WHERE name IN(:names) order by name ASC")
    List<Cocktail> searchCocktails(String[] names);

    @Query("SELECT * FROM cocktail_line WHERE cocktail = :name")
    List<CocktailLine> searchCocktailLinesByCocktail(String name);


}
