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

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private static int cocktailId;
    private static TextView recipe;
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
        // get reference to the TextView that you want to display the message to
        recipe = findViewById(R.id.textView2);
        // set the text in that TextView
        recipe.setText(cocktailName + "\n\nDirections:\n" + mixingDirections);

        new generateRecipeAsyncTask(mRepository).execute();
    }

    /**
     * Runs the cocktail recipe generator asynchronously in a background thread, updating the contents of
     * the cocktail adapter once complete
     */
    private static class generateRecipeAsyncTask extends AsyncTask<Void, Void, List<CocktailLine>> {

        private PocketBarRepository cocktailRepository;

        generateRecipeAsyncTask(PocketBarRepository cr) {
            cocktailRepository = cr;
        }

        @Override
        protected List<CocktailLine> doInBackground(Void... voids) {
            return cocktailRepository.generateRecipe(cocktailId);
        }

        /**
         * Update the cocktails in the adapter, which will update the view
         */
        protected void onPostExecute(List<CocktailLine> cocktailLines) {
            for(int i = 0; i < cocktailLines.size(); i++){
                recipe.append(cocktailLines.get(i).toString() + " ");
                recipe.append(cocktailLines.get(i).getAmount() + " ");
                recipe.append(cocktailLines.get(i).getAmount_literal() + "\n");
            }
        }
    }

}