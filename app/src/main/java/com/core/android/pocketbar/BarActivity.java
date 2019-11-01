package com.core.android.pocketbar;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.database.BarIngredient;
import com.core.database.BarIngredientListAdapter;
import com.core.database.PocketBarRepository;

import java.util.List;

public class BarActivity extends AppCompatActivity {
    //Entry field for new ingredients being added to bar
    private EditText mAddIngredientEntry;

    //The view containing the list of ingredients in the bar
    private RecyclerView mRecyclerView;
    //The adapter holding the data backing the recyclerview
    private BarIngredientListAdapter mBarIngredientListAdapter;
    //The data access object providing access to the data stored in the application database
    private PocketBarRepository mIngredientRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView = findViewById(R.id.recyclerview);
        mBarIngredientListAdapter = new BarIngredientListAdapter(this);
        //Associate the adapter with the recyclerview - when the adapter's data changes it
        //will inform the view that it needs to update
        mRecyclerView.setAdapter(mBarIngredientListAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAddIngredientEntry = findViewById(R.id.add_ingredient_entry);
        mIngredientRepository = new PocketBarRepository(getApplication());
        //Grab the contents of the main bar
        getBarIngredients();
    }

    /**
     * Add an ingredient to the default bar and update view.
     */
    public void addIngredient(View view) {
        String ingredientToAdd = mAddIngredientEntry.getText().toString();
        new insertBarIngredientAsyncTask(mIngredientRepository, mBarIngredientListAdapter).execute(new BarIngredient(ingredientToAdd, "main_bar"));
    }

    /**
     * Add an ingredient to the default bar and update view.
     */
    public void removeIngredient(View view) {
//        String ingredientName = view.getParent().toString()
        TextView ingredientTextView = ((View) view.getParent()).findViewById(R.id.bar_ingredient_name);
        String ingredientName = ingredientTextView.getText().toString();
        new deleteBarIngredientAsyncTask(mIngredientRepository, mBarIngredientListAdapter).execute(ingredientName);
    }

    /**
     * Get all ingredients in the default bar and update view.
     */
    public void getBarIngredients() {
        new barAsyncTask(mIngredientRepository, mBarIngredientListAdapter).execute();
    }

    /**
     * Inserts a new ingredient into the default bar. Once inserted, updates the adapter
     */
    private static class insertBarIngredientAsyncTask extends AsyncTask<BarIngredient, Void, List<BarIngredient>> {

        private PocketBarRepository ingredientRepository;
        private BarIngredientListAdapter ingredientListAdapter;

        insertBarIngredientAsyncTask(PocketBarRepository cr, BarIngredientListAdapter adapter) {
            ingredientRepository = cr;
            ingredientListAdapter = adapter;
        }

        @Override
        protected List<BarIngredient> doInBackground(BarIngredient... barIngredients) {
            // Return a String result.
            ingredientRepository.insertBarIngredient(barIngredients[0]);
            return ingredientRepository.getMyBarIngredients();
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<BarIngredient> cocktails) {
            new barAsyncTask(ingredientRepository, ingredientListAdapter).execute();
        }
    }

    /**
     * Inserts a new ingredient into the default bar. Once inserted, updates the adapter
     */
    private static class deleteBarIngredientAsyncTask extends AsyncTask<String, Void, List<BarIngredient>> {

        private PocketBarRepository ingredientRepository;
        private BarIngredientListAdapter ingredientListAdapter;

        deleteBarIngredientAsyncTask(PocketBarRepository cr, BarIngredientListAdapter adapter) {
            ingredientRepository = cr;
            ingredientListAdapter = adapter;
        }

        @Override
        protected List<BarIngredient> doInBackground(String... barIngredients) {
            // Return a String result.
            ingredientRepository.deleteBarIngredient(barIngredients[0]);
            return ingredientRepository.getMyBarIngredients();
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<BarIngredient> cocktails) {
            new barAsyncTask(ingredientRepository, ingredientListAdapter).execute();
        }
    }

    /**
     * Gets the ingredients in the default bar and updates the view with those ingredients
     */
    private static class barAsyncTask extends AsyncTask<Void, Void, List<BarIngredient>> {

        private PocketBarRepository ingredientRepository;
        private BarIngredientListAdapter ingredientListAdapter;

        barAsyncTask(PocketBarRepository cr, BarIngredientListAdapter adapter) {
            ingredientRepository = cr;
            ingredientListAdapter = adapter;
        }

        @Override
        protected List<BarIngredient> doInBackground(Void... voids) {
            // Return a String result.
            return ingredientRepository.getMyBarIngredients();
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<BarIngredient> cocktails) {
            ingredientListAdapter.setIngredients(cocktails);
        }
    }
}
