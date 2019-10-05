package com.core.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class IngredientRepository {
    private PocketBarDao mPocketBarDao;
    private LiveData<List<Ingredient>> mAllIngredients;

    IngredientRepository(Application application) {
        PocketBarRoomDatabase db = PocketBarRoomDatabase.getDatabase(application);
        mPocketBarDao = db.pocketbarDao();
        mAllIngredients = mPocketBarDao.getAllIngredients();
    }

    LiveData<List<Ingredient>> getAllIngredients() {
        return mAllIngredients;
    }

    public void insert (Ingredient word) {
        new insertAsyncTask(mPocketBarDao).execute(word);
    }

    private static class insertAsyncTask extends AsyncTask<Ingredient, Void, Void> {

        private PocketBarDao mAsyncTaskDao;

        insertAsyncTask(PocketBarDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Ingredient... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
