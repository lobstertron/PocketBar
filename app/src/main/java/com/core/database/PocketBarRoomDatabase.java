package com.core.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Ingredient.class,
        Cocktail.class,
        CocktailLine.class,
        BarIngredient.class,
        Bar.class},
        version = 5,
        exportSchema = false)
public abstract class PocketBarRoomDatabase extends RoomDatabase {
    public abstract PocketBarDao pocketbarDao();

    private static PocketBarRoomDatabase INSTANCE;

    public static PocketBarRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (PocketBarRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            PocketBarRoomDatabase.class, "word_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    /**
     * Populate the database with dummy data
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final PocketBarDao mDao;
        String[] ingredients = {"lime", "gin", "vodka", "rum", "coke", "orange juice", "tequila", "tonic water", "grenadine", "ginger beer"};
        String[] cocktails = {"Rum and Coke", "Gin and Tonic", "Tequila Sunrise", "Screwdriver", "Moscow Mule"};
        CocktailLine[] cocktailLines = {
                new CocktailLine("rum", "Rum and Coke", 30, "1 oz"),
                new CocktailLine("coke", "Rum and Coke", 120, "4 oz"),
                new CocktailLine("lime", "Gin and Tonic", 10, "1 splash"),
                new CocktailLine("gin", "Gin and Tonic", 30, "1 oz"),
                new CocktailLine("tonic water", "Gin and Tonic", 120, "4 oz"),
                new CocktailLine("tequila", "Tequila Sunrise", 30, "1 oz"),
                new CocktailLine("orange juice", "Tequila Sunrise", 120, "4 oz"),
                new CocktailLine("grenadine", "Tequila Sunrise", 10, "1 splash"),
                new CocktailLine("orange juice", "Screwdriver", 120, "4 oz"),
                new CocktailLine("vodka", "Screwdriver", 30, "1 oz"),
                new CocktailLine("vodka", "Moscow Mule", 30, "1 oz"),
                new CocktailLine("ginger beer", "Moscow Mule", 30, "1 oz")
        };
        Bar[] bars = {new Bar("main_bar")};
        BarIngredient[] barIngredients = {new BarIngredient("orange juice", "main_bar"),
                new BarIngredient("ginger beer", "main_bar"),
                new BarIngredient("vodka", "main_bar"),
                new BarIngredient("rum", "main_bar"),
                new BarIngredient("coke", "main_bar")};

        PopulateDbAsync(PocketBarRoomDatabase db) {
            mDao = db.pocketbarDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            //TODO for 1st iteration, start with clean slate each time
            mDao.deleteAllIngredients();
            mDao.deleteAllBarIngredients();
            mDao.deleteAllBars();
            mDao.deleteAllCocktails();

            for (int i = 0; i <= ingredients.length - 1; i++) {
                Ingredient ingredient = new Ingredient(ingredients[i]);
                mDao.insert(ingredient);
            }
            for (int i = 0; i <= cocktails.length - 1; i++) {
                Cocktail cocktail = new Cocktail(cocktails[i], null);
                mDao.insert(cocktail);
            }
            for (int i = 0; i <= cocktailLines.length - 1; i++) {
                mDao.insert(cocktailLines[i]);
            }
            for (int i = 0; i < bars.length; i++) {
                mDao.insert(bars[i]);
            }
            for (int i = 0; i < barIngredients.length; i++) {
                mDao.insert(barIngredients[i]);
            }
            return null;
        }
    }
}
