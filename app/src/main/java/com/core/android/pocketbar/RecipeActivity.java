package com.core.android.pocketbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.core.database.CocktailListAdapter;

public class RecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        // get the 'Intent that activate this activity
        Intent intent = getIntent();
        // get the string from the 'Intent's extras
        String cocktailName = intent.getStringExtra(CocktailListAdapter.NAME_MESSAGE);
        String mixingDirections = intent.getStringExtra(CocktailListAdapter.DIRECTIONS_MESSAGE);
        // get reference to the TextView that you want to display the message to
        TextView textView = findViewById(R.id.textView2);
        // set the text in that TextView
        textView.setText(cocktailName + "\n\nDirections:\n" + mixingDirections);
    }
}
