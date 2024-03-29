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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Favorite favorite);

    @Query("DELETE FROM bar_ingredient where ingredient = :name")
    void deleteBarIngredient(String name);

    @Query("DELETE FROM shopping_ingredient where ingredient = :name")
    void deleteShoppingIngredient(String name);

    @Query("DELETE FROM favorite where id = :id")
    void deleteFavorite(Integer id);

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

    @Query("SELECT * from ingredient WHERE name IN(:names)")
    List<Ingredient> getIngredientsWithNames(String[] names);

    @Query("SELECT * from cocktail WHERE name IN(:names)")
    List<Cocktail> getCocktailsWithNames(String[] names);

    //The following two inverse methods are used in the cocktail search
    @Query("SELECT cocktailId from cocktail_line WHERE ingredientId NOT IN(:ingredientIds) group by cocktailId")
    List<Integer> getCocktailLinesWithIngredientIdsInverse(int[] ingredientIds);

    @Query("SELECT * from cocktail WHERE id NOT IN(:cocktailIds)")
    List<Cocktail> getCocktailsNotWithCocktailIds(Integer[] cocktailIds);

    @Query("SELECT * from cocktail WHERE id IN(:cocktailIds)")
    List<Cocktail> getCocktailsWithCocktailIds(Integer[] cocktailIds);

    @Query("SELECT id from favorite")
    List<Integer> getAllFavoriteIds();

    @Query("SELECT * from bar_ingredient")
    List<BarIngredient> getAllBarIngredients();

    @Query("SELECT * from bar_ingredient where bar_name = 'main_bar' ORDER BY ingredient ASC")
    List<BarIngredient> getMyBarIngredients();

    @Query("SELECT ingredient from bar_ingredient where bar_name = 'main_bar'")
    List<String> getStringMyBarIngredients();

    @Query("SELECT * from cocktail ORDER BY name ASC")
    List<Cocktail> getAllCocktails();

    @Query("SELECT * FROM cocktail WHERE name IN(:names) order by name ASC")
    List<Cocktail> searchCocktails(String[] names);

    @Query("SELECT * from cocktail_line WHERE cocktailId = :ID")
    List<CocktailLine> generateCocktailLines(int ID);

    @Query("SELECT name from ingredient WHERE id = :ID")
    String getIngredient(int ID);







    /////////////////////////////// Arick Additions ///////////////////////////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ShoppingIngredient shoppingIngredient);

    /*@Query("DELETE FROM shopping_ingredient where ingredient = :name")
    void delete(String name);*/

    @Query("SELECT * from shopping_ingredient")
    List<ShoppingIngredient> getAllShoppingIngredients();

    @Query("SELECT * from shopping_ingredient where bar_name = 'main_bar' ORDER BY ingredient ASC")
    List<ShoppingIngredient> getShoppingIngredients();

    @Query("SELECT ingredient from shopping_ingredient where bar_name = 'main_bar'")
    List<String> getStringShoppingListIngredients();












    ///////////////////////////////////////////////////////////////////////////////


}
