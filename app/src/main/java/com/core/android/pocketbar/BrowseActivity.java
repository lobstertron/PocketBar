package com.core.android.pocketbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class BrowseActivity extends AppCompatActivity {

    //Entry field for entering cocktail search
    private TextView mSearchCocktailsEntry;
    //The adapter that holds the cocktail data for the view
    private CocktailListAdapter mCocktailListAdapter;
    //The view holding the cocktails
    private RecyclerView mRecyclerView;
    //The repository providing database access
    private PocketBarRepository mRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRepository = new PocketBarRepository(getApplication());
        mRecyclerView = findViewById(R.id.recyclerview);
        mCocktailListAdapter = new CocktailListAdapter(this);
        mRecyclerView.setAdapter(mCocktailListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchCocktailsEntry = findViewById(R.id.search_cocktails);

        //Get all ingredients to populate autocomplete list
        //new getAllIngredientsAsyncTask(mRepository, mIngredientAdapterList).execute();
        new getAllCocktailsAsyncTask(mRepository, mCocktailListAdapter).execute();

        mSearchCocktailsEntry.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //No need to implement
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCocktailListAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //No need to implement
            }
        });
    }

    public void openRandomCocktail(View view) {
        int totalCocktailsShown = mRecyclerView.getAdapter().getItemCount();
        int randomCocktailIndex = (int) Math.round(Math.random() * totalCocktailsShown);
        mCocktailListAdapter.openCocktailAtIndex(randomCocktailIndex);

    }


    /**
     * Runs the cocktail search asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private static class getAllCocktailsAsyncTask extends AsyncTask<String, Void, List<Cocktail>> {

        private PocketBarRepository cocktailRepository;
        private CocktailListAdapter cocktailListAdapter;

        getAllCocktailsAsyncTask(PocketBarRepository cr, CocktailListAdapter adapter) {
            cocktailRepository = cr;
            cocktailListAdapter = adapter;
        }

        @Override
        protected List<Cocktail> doInBackground(String... ingredients) {
            return cocktailRepository.getAllCocktails();
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<Cocktail> cocktails) {
            cocktailListAdapter.setCocktails(cocktails);
        }
    }

}
