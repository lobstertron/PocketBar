package com.core.android.pocketbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.database.Cocktail;
import com.core.database.CocktailListAdapter;
import com.core.database.PocketBarRepository;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchCocktailsEntry;
    private CocktailListAdapter mCocktailListAdapter;
    private RecyclerView mRecyclerView;
    private PocketBarRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerview);
        mCocktailListAdapter = new CocktailListAdapter(this);
        mRecyclerView.setAdapter(mCocktailListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchCocktailsEntry = findViewById(R.id.search_cocktails);
        mRepository = new PocketBarRepository(getApplication());
    }

    /**
     * Searches for cocktails that can be created with a list of entered ingredients
     */
    public void searchCocktails(View view) {
        String searchString = mSearchCocktailsEntry.getText().toString();
        String[] ingredientList = searchString.split(",");
        new searchAsyncTask(mRepository, mCocktailListAdapter).execute(ingredientList);
    }

    /**
     * Runs the cocktail search asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private static class searchAsyncTask extends AsyncTask<String, Void, List<Cocktail>> {

        private PocketBarRepository cocktailRepository;
        private CocktailListAdapter cocktailListAdapter;

        searchAsyncTask(PocketBarRepository cr, CocktailListAdapter adapter) {
            cocktailRepository = cr;
            cocktailListAdapter = adapter;
        }

        @Override
        protected List<Cocktail> doInBackground(String... ingredients) {
            return cocktailRepository.searchCocktails(ingredients);
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<Cocktail> cocktails) {
            cocktailListAdapter.setCocktails(cocktails);
        }
    }
}
