package com.core.android.pocketbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.database.BarIngredient;
import com.core.database.Cocktail;
import com.core.database.CocktailListAdapter;
import com.core.database.Ingredient;
import com.core.database.PocketBarRepository;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    //Entry field for entering cocktail search
    private AutoCompleteTextView mSearchCocktailsEntry;
    //The adapter that holds the cocktail data for the view
    private CocktailListAdapter mCocktailListAdapter;
    //The view holding the cocktails
    private RecyclerView mRecyclerView;
    //The repository providing database access
    private PocketBarRepository mRepository;
    //The tray of ingredients involved in the search
    private FlexboxLayout mIngredientTray;
    //Array backing the autocomplete dropdown
    private ArrayAdapter<String> mIngredientAdapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRepository = new PocketBarRepository(getApplication());
        mRecyclerView = findViewById(R.id.recyclerview);
        mCocktailListAdapter = new CocktailListAdapter(this);
        mRecyclerView.setAdapter(mCocktailListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchCocktailsEntry = findViewById(R.id.search_cocktails);
        mIngredientTray = findViewById(R.id.ingredient_search_tray);

        //Create the Adapter to hold the autocomplete items
        mIngredientAdapterList = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        mSearchCocktailsEntry.setAdapter(mIngredientAdapterList);

        //Get all ingredients to populate autocomplete list
        new getAllIngredientsAsyncTask(mRepository, mIngredientAdapterList).execute();
    }

    /**
     * Searches for cocktails that can be created with a list of entered ingredients
     */
    public void addIngredient(View view) {
        String ingredientString = mSearchCocktailsEntry.getText().toString();
        //Clear entry field
        mSearchCocktailsEntry.setText("");
        addIngredientToTray(ingredientString);
    }

    public void addIngredientToTray(String ingredientName) {
        //First check if the ingredient is already in the tray
        int trayIngredientCount = mIngredientTray.getFlexItemCount();
        for (int i = 0; i < trayIngredientCount; i++) {
            TextView textView = mIngredientTray.getFlexItemAt(i).findViewById(R.id.textView);
            if (textView.getText().toString().equals(ingredientName)) {
                return;
            }
        }
        View addedView = getLayoutInflater().inflate(R.layout.ingredient_tray_item, null);
        TextView ingredientTrayName = addedView.findViewById(R.id.textView);
        ingredientTrayName.setText(ingredientName);
        mIngredientTray.addView(addedView);
    }

    /**
     * Searches all cocktails that can be composed from the ingredients in the ingerdient
     * tray
     *
     * @param view
     */
    public void searchCocktails(View view) {
        int ingredientListCount = mIngredientTray.getChildCount();
        String[] ingredientList = new String[ingredientListCount];
        for (int i = 0; i < ingredientListCount; i++) {
            TextView ingredientView = (TextView) mIngredientTray.getChildAt(i).findViewById(R.id.textView);
            String ingredientName = ingredientView.getText().toString();
            ingredientList[i] = ingredientName;
        }
        new searchAsyncTask(mRepository, mCocktailListAdapter).execute(ingredientList);
    }

    public void clearIngredientsTray(View view) {
        mIngredientTray.removeAllViews();
    }

    public void addBarIngredients(View view) {
        new getBarIngredientsAsyncTask(mRepository, this).execute();
    }

    public void removeIngredient(View view) {
        mIngredientTray.removeView(view);
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


    /**
     * Runs the cocktail search asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private static class getAllIngredientsAsyncTask extends AsyncTask<Void, Void, List<Ingredient>> {

        private PocketBarRepository cocktailRepository;
        private ArrayAdapter<String> ingredientAdapter;

        getAllIngredientsAsyncTask(PocketBarRepository cr, ArrayAdapter<String> ia) {
            cocktailRepository = cr;
            ingredientAdapter = ia;
        }

        @Override
        protected List<Ingredient> doInBackground(Void... voids) {
            return cocktailRepository.getAllIngredients();
        }

        /**
         * Update the cocktails in the autocomplete adapter
         */
        protected void onPostExecute(List<Ingredient> ingredients) {
            List<String> ingredientList = new ArrayList<>();
            for (int i = 0; i < ingredients.size(); i++) {
                ingredientList.add(ingredients.get(i).getName());
            }
            ingredientAdapter.addAll(ingredientList);
        }
    }

    /**
     * Runs the cocktail search asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private static class getBarIngredientsAsyncTask extends AsyncTask<Void, Void, List<BarIngredient>> {

        private PocketBarRepository cocktailRepository;
        private SearchActivity searchActivity;

        getBarIngredientsAsyncTask(PocketBarRepository cr, SearchActivity sa) {
            cocktailRepository = cr;
            searchActivity = sa;
        }

        @Override
        protected List<BarIngredient> doInBackground(Void... voids) {
            return cocktailRepository.getMyBarIngredients();
        }

        /**
         * Update the cocktails in the autocomplete adapter
         */
        protected void onPostExecute(List<BarIngredient> ingredients) {
            for (int i = 0; i < ingredients.size(); i++) {
                searchActivity.addIngredientToTray(ingredients.get(i).getIngredient());
            }
        }
    }
}
