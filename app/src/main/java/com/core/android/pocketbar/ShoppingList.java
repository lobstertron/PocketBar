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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.database.BarIngredient;
import com.core.database.BarIngredientListAdapter;
import com.core.database.PocketBarRepository;
import com.core.database.ShoppingIngredient;
import com.core.database.ShoppingIngredientListAdapter;

import java.util.LinkedList;
import java.util.List;


public class ShoppingList extends AppCompatActivity {


    private LinkedList<String> shoppingList = new LinkedList<>();




    //The view containing the list of ingredients in the bar
    private EditText sAddIngredientEntry;

    //The view containing the list of ingredients in the bar
    private RecyclerView sRecyclerView;
    //The adapter holding the data backing the recyclerview
    private ShoppingIngredientListAdapter sShoppingIngredientListAdapter;
    //The data access object providing access to the data stored in the application database
    private PocketBarRepository sIngredientRepository;


    // Not exactly sure what this is for yet but it's was part of the tutorial.
    private LayoutInflater sShopListInflater;








/*
    public ShoppingList(Context context, LinkedList<String> itemList) {
        mShopListInflater = LayoutInflater.from(context);
        this.shoppingList = itemList;
    }
*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sRecyclerView = findViewById(R.id.recyclerviewShop);
        sShoppingIngredientListAdapter = new ShoppingIngredientListAdapter(this);
        //Associate the adapter with the recyclerview - when the adapter's data changes it
        //will inform the view that it needs to update
        sRecyclerView.setAdapter(sShoppingIngredientListAdapter);
        sRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        sAddIngredientEntry = findViewById(R.id.editText_shoplistadd);


        sIngredientRepository = new PocketBarRepository(getApplication());
        //Grab the contents of the main bar
        getShoppingIngredients();


    }


    /**
     * Add an item to the shopping list and update view.
     */
    public void addIngredient(View view) {

        String ingredientToAdd = sAddIngredientEntry.getText().toString();
        Log.i("AddItem Method", "The Following Was Added: " + ingredientToAdd);


        new insertShoppingIngredientAsyncTask(sIngredientRepository, sShoppingIngredientListAdapter).execute(new ShoppingIngredient(ingredientToAdd));
    }




    /**
     * Remove an ingredient to the shopping list and update view.
     */
    public void removeIngredient(View view) {
//        String ingredientName = view.getParent().toString()
        TextView ingredientTextView = ((View) view.getParent()).findViewById(R.id.shopping_ingredient_name);
        String ingredientName = ingredientTextView.getText().toString();
        new deleteShoppingIngredientAsyncTask(sIngredientRepository, sShoppingIngredientListAdapter).execute(ingredientName);
    }





    public void getShoppingIngredients() {
        new ShoppingList.shoppingAsyncTask(sIngredientRepository, sShoppingIngredientListAdapter).execute();
    }







    /**
     * Inserts a new ingredient into the default bar. Once inserted, updates the adapter
     */
    private static class insertShoppingIngredientAsyncTask extends AsyncTask<ShoppingIngredient, Void, List<ShoppingIngredient>> {

        private PocketBarRepository ingredientShoppingRepository;
        private ShoppingIngredientListAdapter ingredientShoppingListAdapter;

        insertShoppingIngredientAsyncTask(PocketBarRepository cr, ShoppingIngredientListAdapter adapter) {
            ingredientShoppingRepository = cr;
            ingredientShoppingListAdapter = adapter;
        }

        @Override
        protected List<ShoppingIngredient> doInBackground(ShoppingIngredient... shoppingIngredients) {
            // Return a String result.
            ingredientShoppingRepository.insertShoppingIngredient(shoppingIngredients[0]);
            return ingredientShoppingRepository.getShoppingIngredients();
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<ShoppingIngredient> cocktails) {
            new ShoppingList.shoppingAsyncTask(ingredientShoppingRepository, ingredientShoppingListAdapter).execute();
        }
    }







    /**
     * Inserts a new ingredient into the default bar. Once inserted, updates the adapter
     */
    private static class deleteShoppingIngredientAsyncTask extends AsyncTask<String, Void, Void> {

        private PocketBarRepository ingredientRepository;
        private ShoppingIngredientListAdapter ingredientListAdapter;

        deleteShoppingIngredientAsyncTask(PocketBarRepository cr, ShoppingIngredientListAdapter adapter) {
            ingredientRepository = cr;
            ingredientListAdapter = adapter;
        }

        @Override
        protected Void doInBackground(String... ShoppingIngredients) {
            // Return a String result.
            ingredientRepository.deleteShoppingIngredient(ShoppingIngredients[0]);
            //return ingredientRepository.getMyBarIngredients();
            return null;
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(Void diov) {
            new ShoppingList.shoppingAsyncTask(ingredientRepository, ingredientListAdapter).execute();
        }
    }



    /**
     * Gets the ingredients in the default bar and updates the view with those ingredients
     */
    private static class shoppingAsyncTask extends AsyncTask<Void, Void, List<ShoppingIngredient>> {

        private PocketBarRepository ingredientRepository;
        private ShoppingIngredientListAdapter ingredientShoppingListAdapter;

        shoppingAsyncTask(PocketBarRepository cr, ShoppingIngredientListAdapter adapter) {
            ingredientRepository = cr;
            ingredientShoppingListAdapter = adapter;
        }

        @Override
        protected List<ShoppingIngredient> doInBackground(Void... voids) {
            // Return a String result.
            return ingredientRepository.getShoppingIngredients();
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<ShoppingIngredient> cocktails) {
            ingredientShoppingListAdapter.setIngredients(cocktails);
        }
    }





}
