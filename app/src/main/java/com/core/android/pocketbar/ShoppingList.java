package com.core.android.pocketbar;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.core.database.BarIngredient;
import com.core.database.BarIngredientListAdapter;
import com.core.database.PocketBarRepository;

import java.util.LinkedList;
import java.util.List;


public class ShoppingList extends AppCompatActivity {


    private LinkedList<String> shoppingList = new LinkedList<>();




    //The view containing the list of ingredients in the bar
    private EditText mAddItemEntry;
    // Not exactly sure what this is for yet but it's was part of the tutorial.
    private LayoutInflater mShopListInflater;



/*
    public ShoppingList(Context context, LinkedList<String> itemList) {
        mShopListInflater = LayoutInflater.from(context);
        this.shoppingList = itemList;
    }
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_shopping_list);

        mAddItemEntry = findViewById(R.id.editText_shoplistadd);

    }


    /**
     * Add an item to the shopping list and update view.
     */
    public void addItem(View view) {

        String ingredientToAdd = mAddItemEntry.getText().toString();
        Log.i("AddItem Method", "The Following Was Added: " + ingredientToAdd);
        new BarActivity.insertBarIngredientAsyncTask(mIngredientRepository, mBarIngredientListAdapter).execute(new BarIngredient(ingredientToAdd, "main_bar"));
    }




    /**
     * Remove an ingredient to the shopping list and update view.
     */
    public void removeIngredient(View view) {
//        String ingredientName = view.getParent().toString()
        TextView ingredientTextView = ((View) view.getParent()).findViewById(R.id.bar_ingredient_name);
        String ingredientName = ingredientTextView.getText().toString();
        new BarActivity.deleteBarIngredientAsyncTask(mIngredientRepository, mBarIngredientListAdapter).execute(ingredientName);
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
            new BarActivity.barAsyncTask(ingredientRepository, ingredientListAdapter).execute();
        }
    }







    /**
     * Inserts a new ingredient into the default bar. Once inserted, updates the adapter
     */
    private static class deleteBarIngredientAsyncTask extends AsyncTask<String, Void, Void> {

        private PocketBarRepository ingredientRepository;
        private BarIngredientListAdapter ingredientListAdapter;

        deleteBarIngredientAsyncTask(PocketBarRepository cr, BarIngredientListAdapter adapter) {
            ingredientRepository = cr;
            ingredientListAdapter = adapter;
        }

        @Override
        protected Void doInBackground(String... barIngredients) {
            // Return a String result.
            ingredientRepository.deleteBarIngredient(barIngredients[0]);
            //return ingredientRepository.getMyBarIngredients();
            return null;
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(Void diov) {
            new BarActivity.barAsyncTask(ingredientRepository, ingredientListAdapter).execute();
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
