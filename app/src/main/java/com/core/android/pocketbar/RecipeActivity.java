package com.core.android.pocketbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.core.database.Cocktail;
import com.core.database.CocktailLine;
import com.core.database.CocktailListAdapter;
import com.core.database.PocketBarRepository;

import java.util.ArrayList;
import java.util.LinkedList;
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
        // get the 'Intent that activate this activity
        Intent intent = getIntent();
        mRepository = new PocketBarRepository(getApplication());
        // get the string from the 'Intent's extras
        String cocktailName = intent.getStringExtra(CocktailListAdapter.NAME_MESSAGE);
        String mixingDirections = intent.getStringExtra(CocktailListAdapter.DIRECTIONS_MESSAGE);
        cocktailId = intent.getIntExtra(CocktailListAdapter.COCKTAIL_ID, 0);
        // get reference to the TextViews that you want to display the message to
        recipeText = findViewById(R.id.recipe);
        directionsText = findViewById(R.id.directions);
        // set the text in that TextView
        directionsText.setText(cocktailName + "\n\nDirections:\n" + mixingDirections);

        new generateCocktailLinesAsyncTask(mRepository).execute();
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

    /**
     * Runs the cocktail recipe generator asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private class generateCocktailLinesAsyncTask extends AsyncTask<Void, Void, List<String>> {

        private PocketBarRepository cocktailRepository;
        List<CocktailLine> cocktailLines;

        generateCocktailLinesAsyncTask(PocketBarRepository cr) {
            cocktailRepository = cr;
        }

        @Override
        protected List<String> doInBackground(Void... voids) {
            cocktailLines = cocktailRepository.generateCocktailLines(cocktailId);

            return cocktailRepository.generateRecipeIngredients(cocktailLines);
        }

        /**
         * Generate Recipe layout.
         */
        protected void onPostExecute(List<String> ingredients) {

            for(int i = 0; i < cocktailLines.size(); i++){
                recipeText.append(ingredients.get(i)+ " ");
                recipeText.append(cocktailLines.get(i).getAmount() + " ");
                recipeText.append(cocktailLines.get(i).getAmount_literal() + "\n");
            }
        }
    }
}