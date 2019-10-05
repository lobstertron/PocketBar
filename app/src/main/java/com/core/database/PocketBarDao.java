package com.core.database;

import androidx.lifecycle.LiveData;
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

    @Query("DELETE FROM ingredient")
    void deleteAllIngredients();

    @Query("DELETE FROM cocktail")
    void deleteAllCocktails();

    @Query("SELECT * from ingredient ORDER BY name ASC")
    LiveData<List<Ingredient>> getAllIngredients();

    @Query("SELECT * from cocktail ORDER BY name ASC")
    List<Cocktail> getAllCocktails();

    @Query("SELECT * FROM ingredient WHERE name IN(:names)")
    LiveData<List<Ingredient>> searchIngredients(String[] names);

    @Query("SELECT * FROM cocktail WHERE name IN(:names)")
    List<Cocktail> searchCocktails(String[] names);

    @Query("SELECT * FROM cocktail_line WHERE cocktail = :name")
    List<CocktailLine> searchCocktailLinesByCocktail(String name);


}
