package com.core.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Provides access to database information without requiring a data source that is set in stone
 */
public class PocketBarRepository {
    //The DAO interface providing the methods to query the Room DB
    private PocketBarDao mPocketBarDao;

    public PocketBarRepository(Application application) {
        PocketBarRoomDatabase db = PocketBarRoomDatabase.getDatabase(application);
        mPocketBarDao = db.pocketbarDao();
    }

    /************************** Cocktail methods and Objects **************************************/
    public void insertCocktail(Cocktail cocktail) {
        new PocketBarRepository.insertCocktailAsyncTask(mPocketBarDao).execute(cocktail);
    }

    private static class insertCocktailAsyncTask extends AsyncTask<Cocktail, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        insertCocktailAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cocktail... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public List<Cocktail> getAllCocktails() {
        return mPocketBarDao.getAllCocktails();
    }

    public List<Cocktail> searchCocktails(String... ingredients) {
        //Look at every cocktail in the database
        List<Cocktail> allCocktails = getAllCocktails();
        //Save off those that satisfy criteria
        List<Cocktail> cocktailResult = new LinkedList<>();
        for (int i = 0; i < allCocktails.size(); i++) {
            boolean validCocktail = true;
            List<CocktailLine> allCocktailLines = mPocketBarDao.searchCocktailLinesByCocktail(allCocktails.get(i).getName());
            for (int j = 0; j < allCocktailLines.size(); j++) {
                String ingredient = allCocktailLines.get(j).getIngredient();
                //If a cocktail uses an ingredient not in the list of ingredients, do not add to result
                boolean ingredientInSearch = Arrays.asList(ingredients).contains(ingredient);
                if (!ingredientInSearch) {
                    validCocktail = false;
                    break;
                }
            }
            if (validCocktail) {
                cocktailResult.add(allCocktails.get(i));
            }
        }
        return cocktailResult;
    }

    /************************** Bar ingredient methods and objects ********************************/
    public List<BarIngredient> getMyBarIngredients() {
        return mPocketBarDao.getMyBarIngredients();
    }

    public void insertBarIngredient(BarIngredient barIngredient) {
        new PocketBarRepository.insertBarIngredientAsyncTask(mPocketBarDao).execute(barIngredient);
    }

    private static class insertBarIngredientAsyncTask extends AsyncTask<BarIngredient, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        insertBarIngredientAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final BarIngredient... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
