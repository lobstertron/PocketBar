package com.core.android.pocketbar;

import android.os.AsyncTask;
import android.os.Bundle;

import com.core.database.Cocktail;
import com.core.database.CocktailRepository;
import com.core.database.CocktailViewModel;
import com.core.database.Cocktail;
import com.core.database.CocktailListAdapter;
import com.core.database.CocktailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.EditText;

import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private EditText mSearchCocktailsEntry;
    private String mCocktailSearch;

    private CocktailViewModel mCocktailViewModel;
    private CocktailListAdapter mCocktailListAdapter;
    private RecyclerView mRecyclerView;

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

        mCocktailViewModel = ViewModelProviders.of(this).get(CocktailViewModel.class);
        mSearchCocktailsEntry = findViewById(R.id.search_cocktails);
    }

    public void searchCocktails(View view) {
        mCocktailSearch = mSearchCocktailsEntry.getText().toString();
        String[] ingredientList = mCocktailSearch.split(",");
        new searchAsyncTask(mCocktailViewModel, mCocktailListAdapter).execute(ingredientList);
    }

    private static class searchAsyncTask extends AsyncTask<String, Void, List<Cocktail>> {

        private CocktailViewModel cocktailViewModel;
        private CocktailListAdapter cocktailListAdapter;

        searchAsyncTask(CocktailViewModel cvm, CocktailListAdapter adapter) {
            cocktailViewModel = cvm;
            cocktailListAdapter = adapter;
        }

        /**
         * Runs on the background thread.
         */
        @Override
        protected List<Cocktail> doInBackground(String... ingredients) {
            // Return a String result.
            return cocktailViewModel.searchCocktails(ingredients);
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<Cocktail> cocktails) {
            cocktailListAdapter.setCocktails(cocktails);
        }
    }
}
