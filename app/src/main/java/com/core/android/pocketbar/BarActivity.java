package com.core.android.pocketbar;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.core.database.Ingredient;
import com.core.database.IngredientListAdapter;
import com.core.database.IngredientViewModel;

import java.util.List;

public class BarActivity extends AppCompatActivity {

    private EditText mAddIngredientEntry;
    private String mIngredientToAdd;

    private IngredientViewModel mIngredientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final IngredientListAdapter adapter = new IngredientListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mIngredientViewModel = ViewModelProviders.of(this).get(IngredientViewModel.class);
        mIngredientViewModel.getAllIngredients().observe(this, new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(@Nullable final List<Ingredient> ingredients) {
                // Update the cached copy of the words in the adapter.
                System.out.println("Ingredients changed");
                adapter.setIngredients(ingredients);
            }
        });
        mAddIngredientEntry = findViewById(R.id.add_ingredient_entry);
    }

    public void addIngredient(View view) {
        mIngredientToAdd = mAddIngredientEntry.getText().toString();
        mIngredientViewModel.insert(new Ingredient(mIngredientToAdd));
    }
}
