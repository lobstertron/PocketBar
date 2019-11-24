package com.core.android.pocketbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.database.Cocktail;
import com.core.database.CocktailListAdapter;
import com.core.database.PocketBarRepository;

import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    //Entry field for entering cocktail search
//    private TextView mSearchCocktailsEntry;
    //The adapter that holds the cocktail data for the view
    private CocktailListAdapter mCocktailListAdapter;
    //The view holding the cocktails
    private RecyclerView mRecyclerView;
    //The repository providing database access
    private PocketBarRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRepository = new PocketBarRepository(getApplication());
        mRecyclerView = findViewById(R.id.recyclerview);
        mCocktailListAdapter = new CocktailListAdapter(this);
        mRecyclerView.setAdapter(mCocktailListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Get all ingredients to populate autocomplete list
        new getFavoritesAsyncTask(mRepository, mCocktailListAdapter).execute();


    }

    /**
     * Delete an ingredient and update the view
     */
    public void removeFavorite(View view) {
        TextView ingredientTextView = ((View) view.getParent()).findViewById(R.id.textView);
        String ingredientName = ingredientTextView.getText().toString();
        new deleteFavoriteAsyncTask(mRepository, mCocktailListAdapter).execute(ingredientName);
    }

//    public void openRandomCocktail(View view) {
//        int totalCocktailsShown = mRecyclerView.getAdapter().getItemCount();
//        int randomCocktailIndex = (int) Math.round(Math.random() * totalCocktailsShown);
//        mCocktailListAdapter.openCocktailAtIndex(randomCocktailIndex);
//    }


    /**
     * Runs the cocktail search asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private static class getFavoritesAsyncTask extends AsyncTask<String, Void, List<Cocktail>> {

        private PocketBarRepository cocktailRepository;
        private CocktailListAdapter cocktailListAdapter;

        getFavoritesAsyncTask(PocketBarRepository cr, CocktailListAdapter adapter) {
            cocktailRepository = cr;
            cocktailListAdapter = adapter;
        }

        @Override
        protected List<Cocktail> doInBackground(String... ingredients) {
            return cocktailRepository.getAllFavoriteCocktails();
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<Cocktail> cocktails) {
            cocktailListAdapter.setCocktails(cocktails);
        }
    }


    /**
     * Inserts a new ingredient into the default bar. Once inserted, updates the adapter
     */
    private static class deleteFavoriteAsyncTask extends AsyncTask<String, Void, Void> {

        private PocketBarRepository ingredientRepository;
        private CocktailListAdapter ingredientListAdapter;

        deleteFavoriteAsyncTask(PocketBarRepository cr, CocktailListAdapter adapter) {
            ingredientRepository = cr;
            ingredientListAdapter = adapter;
        }

        @Override
        protected Void doInBackground(String... favorites) {
            // Return a String result.
            ingredientRepository.deleteFavorite(favorites[0]);
            //return ingredientRepository.getMyFavorites();
            return null;
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(Void diov) {
            new FavoritesActivity.getFavoritesAsyncTask(ingredientRepository, ingredientListAdapter).execute();
        }
    }
}
