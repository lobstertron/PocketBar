package com.core.database;

import android.app.Application;
import android.os.AsyncTask;

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

    /*************************Ingredient methods and objects********************************/
    public List<Ingredient> getAllIngredients() {
        return mPocketBarDao.getAllIngredients();
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

    //good sample string
    public List<Cocktail> searchCocktails(String... ingredients) {

        //Get all of the ingredients with the searched names
        List<Ingredient> cocktailIngredients = mPocketBarDao.getIngredientsWithNames(ingredients);
        int[] ingredientIds = new int[cocktailIngredients.size()];
        for (int i = 0; i < ingredientIds.length; i++) {
            int ingredientId = cocktailIngredients.get(i).getId();
            ingredientIds[i] = ingredientId;
        }

        List<Integer> cocktailIdList = mPocketBarDao.getCocktailLinesWithIngredientIdsInverse(ingredientIds);
        Integer[] cocktailIds = cocktailIdList.toArray(new Integer[cocktailIdList.size()]);

        //Get every cocktail line that does not contain the ingredient
        List<Cocktail> cocktails = mPocketBarDao.getCocktailsWithCocktailIds(cocktailIds);
        return cocktails;
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
