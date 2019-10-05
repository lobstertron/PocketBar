package com.core.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class CocktailRepository {
    private PocketBarDao mPocketBarDao;

    CocktailRepository(Application application) {
        PocketBarRoomDatabase db = PocketBarRoomDatabase.getDatabase(application);
        mPocketBarDao = db.pocketbarDao();
    }

    List<Cocktail> getAllCocktails() {
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
                if ( !ingredientInSearch ) {
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

    public void insert (Cocktail word) {
        new insertAsyncTask(mPocketBarDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Cocktail, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        insertAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Cocktail... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


}
