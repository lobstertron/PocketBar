package com.core.android.pocketbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.core.database.BarIngredient;
import com.core.database.CocktailLine;
import com.core.database.CocktailListAdapter;
import com.core.database.PocketBarRepository;
import com.core.database.ShoppingIngredient;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private int cocktailId;
    private TextView recipeText;
    private TextView directionsText;
    private PocketBarRepository mRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar_recipe);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // insert PocketBar Logo on to Toolbar
        //getSupportActionBar().setLogo(R.drawable.ic_action_name);
        // get the 'Intent that activate this activity
        Intent intent = getIntent();
        mRepository = new PocketBarRepository(getApplication());
        // get the string from the 'Intent's extras
        String cocktailName = intent.getStringExtra(CocktailListAdapter.NAME_MESSAGE);
        getSupportActionBar().setTitle(cocktailName);
        String mixingDirections = intent.getStringExtra(CocktailListAdapter.DIRECTIONS_MESSAGE);
        cocktailId = intent.getIntExtra(CocktailListAdapter.COCKTAIL_ID, 0);
        // get reference to the TextViews that you want to display the message to
        recipeText = findViewById(R.id.recipe);
        directionsText = findViewById(R.id.directions);
        // set the text in that TextView
        directionsText.setText("Directions:\n" + mixingDirections);

        new generateRecipeAsyncTask(mRepository).execute();
    }


    /**
     * Applies the back button back stack behavior when using the
     * toolbar's back button. Done to preserve state on the previous screen
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public TextView getRecipeText(){
        return recipeText;
    }


    public TextView getDirectionsText(){
        return directionsText;
    }


    public int getCocktailId() {
        return cocktailId;
    }

    public void addToShoppingList(View view) {
        new addToShoppingListAsyncTask(mRepository).execute();
    }

    /**
     * Runs the cocktail recipe generator asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private class generateRecipeAsyncTask extends AsyncTask<Void, Void, List<String>> {

        private PocketBarRepository cocktailRepository;
        List<CocktailLine> cocktailLines;
        List<String> recipeIngredients;
        List<String> shoppingIngredients;

        generateRecipeAsyncTask(PocketBarRepository cr) {
            cocktailRepository = cr;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            cocktailLines = cocktailRepository.generateCocktailLines(cocktailId);
            recipeIngredients = cocktailRepository.generateRecipeIngredients(cocktailLines);
            shoppingIngredients = cocktailRepository.getStringShoppingListIngredients();

            return cocktailRepository.getStringMyBarIngredients();
        }

        /**
         * Generate Recipe layout.
         */
        protected void onPostExecute(List<String> barIngredients) {

            recipeText.setText("");

            for(int i = 0; i < cocktailLines.size(); i++){

                // if the ingredient is in MyBar, append green check-mark to beginning of ingredientLine
                if(barIngredients.contains(recipeIngredients.get(i))){
                    String ingredientLine = " \u2713 " + cocktailLines.get(i).getAmount_literal() + " "
                            + recipeIngredients.get(i)+ "\n";

                    Spannable spannable = new SpannableString(ingredientLine);
                    spannable.setSpan(new ForegroundColorSpan(Color.GREEN), 0, 2,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    recipeText.append(spannable);
                // if the ingredient isn't in MyBar, append red X to beginning of ingredientLine
                }else if(shoppingIngredients.contains(recipeIngredients.get(i))){
                    String ingredientLine = " SL " + cocktailLines.get(i).getAmount_literal() + " "
                            + recipeIngredients.get(i)+ "\n";

                    Spannable spannable = new SpannableString(ingredientLine);
                    spannable.setSpan(new ForegroundColorSpan(Color.rgb(240,86,25)), 0, 3,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    recipeText.append(spannable);
                }else{
                    String ingredientLine = " X " + cocktailLines.get(i).getAmount_literal() + " "
                            + recipeIngredients.get(i)+ "\n";

                    Spannable spannable = new SpannableString(ingredientLine);
                    spannable.setSpan(new ForegroundColorSpan(Color.RED), 0, 2,  Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    recipeText.append(spannable);
                }
            }
        }
    }

    /**
     * Adds ingredients from the recipe that aren't in MyBar to the shopping list
     */
    private class addToShoppingListAsyncTask extends AsyncTask<Void, Void, List<String>> {

        private PocketBarRepository cocktailRepository;

        addToShoppingListAsyncTask(PocketBarRepository cr) {
            cocktailRepository = cr;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            List<CocktailLine> cocktailLines = cocktailRepository.generateCocktailLines(cocktailId);
            List<String> barIngredients = cocktailRepository.getStringMyBarIngredients();
            List<String> shoppingIngredients = cocktailRepository.getStringShoppingListIngredients();
            List<String>  recipeIngredients = cocktailRepository.generateRecipeIngredients(cocktailLines);

            for(int i = 0; i < cocktailLines.size(); i++){

                // if the ingredient isn't in MyBar, add it to the ShoppingList
                if(!barIngredients.contains(recipeIngredients.get(i)) && !shoppingIngredients.contains(recipeIngredients.get(i))){

                    cocktailRepository.insertShoppingIngredient(new ShoppingIngredient(recipeIngredients.get(i), "main_bar"));

                }
            }

            return cocktailRepository.getStringShoppingListIngredients();

        }

        /**
         *  re-generate the recipe page to update display
         */
        protected void onPostExecute(List<String> shoppingIngredients) {

            new generateRecipeAsyncTask(mRepository).execute();

        }
    }
}