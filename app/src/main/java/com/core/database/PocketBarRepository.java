package com.core.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.ArrayList;
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

    public List<Cocktail> getAllFavoriteCocktails() {
        List<Integer> favoriteIds = mPocketBarDao.getAllFavoriteIds();
        Integer[] favArray = favoriteIds.toArray(new Integer[favoriteIds.size()]);
        return mPocketBarDao.getCocktailsWithCocktailIds(favArray);
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
        List<Cocktail> cocktails = mPocketBarDao.getCocktailsNotWithCocktailIds(cocktailIds);
        return cocktails;
    }

    public void deleteFavorite(String favoritename) {
        new PocketBarRepository.deleteFavoriteAsyncTask(mPocketBarDao).execute(favoritename);
    }


    /************************** Bar ingredient methods and objects ********************************/
    public List<BarIngredient> getMyBarIngredients() {
        return mPocketBarDao.getMyBarIngredients();
    }
    public List<String> getStringMyBarIngredients() { return mPocketBarDao.getStringMyBarIngredients();}

    public void insertBarIngredient(BarIngredient barIngredient) {
        new PocketBarRepository.insertBarIngredientAsyncTask(mPocketBarDao).execute(barIngredient);
    }

    public void deleteBarIngredient(String ingredientName) {
        new PocketBarRepository.deleteBarIngredientAsyncTask(mPocketBarDao).execute(ingredientName);
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

    private static class deleteBarIngredientAsyncTask extends AsyncTask<String, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        deleteBarIngredientAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.deleteBarIngredient(params[0]);
            return null;
        }
    }

    /******************* Recipe Page methods ******************/
    public List<CocktailLine> generateCocktailLines(int cocktailId){

        // TODO ** maybe this could handle everything that I need to do. Basically just need to get Strings anyway.
        List<CocktailLine> cocktailLines = mPocketBarDao.generateCocktailLines(cocktailId);
        List<String> ingredients = new ArrayList<>();
        for(int i = 0; i < cocktailLines.size(); i++){
            ingredients.add(mPocketBarDao.getIngredient(cocktailLines.get(i).getIngredientId()));
        }



        return mPocketBarDao.generateCocktailLines(cocktailId);
    }

    // for now commenting this out
    public List<String> generateRecipeIngredients(List<CocktailLine> linesForIngredientIds){
        List<String> ingredients = new ArrayList<>();
        for(int i = 0; i < linesForIngredientIds.size(); i++){
            ingredients.add(mPocketBarDao.getIngredient(linesForIngredientIds.get(i).getIngredientId()));
        }

        return ingredients;
    }

    public void insertFavorite(int favoriteId) {
        Favorite favorite = new Favorite(favoriteId);
        new PocketBarRepository.insertFavoriteAsyncTask(mPocketBarDao).execute(favorite);
    }

    private static class insertFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        insertFavoriteAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Favorite... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }





    /************************** Shopping List ingredient methods and objects ********************************/
    public List<ShoppingIngredient> getShoppingIngredients() {
        return mPocketBarDao.getShoppingIngredients();
    }
    public List<String> getStringShoppingListIngredients() { return mPocketBarDao.getStringShoppingListIngredients();}

    public void insertShoppingIngredient(ShoppingIngredient shoppingIngredient) {
        new PocketBarRepository.insertShoppingIngredientAsyncTask(mPocketBarDao).execute(shoppingIngredient);
    }

    public void deleteShoppingIngredient(String ingredientName) {
        new PocketBarRepository.deleteShoppingIngredientAsyncTask(mPocketBarDao).execute(ingredientName);
    }

    private static class insertShoppingIngredientAsyncTask extends AsyncTask<ShoppingIngredient, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        insertShoppingIngredientAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ShoppingIngredient... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteShoppingIngredientAsyncTask extends AsyncTask<String, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        deleteShoppingIngredientAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            mAsyncTaskDao.deleteShoppingIngredient(params[0]);
            return null;
        }
    }

    private static class deleteFavoriteAsyncTask extends AsyncTask<String, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        deleteFavoriteAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            List<Cocktail> cocktailList = mAsyncTaskDao.getCocktailsWithNames(params);
            Integer cocktailId = cocktailList.get(0).getId();
            mAsyncTaskDao.deleteFavorite(cocktailId);
            return null;
        }
    }










}
