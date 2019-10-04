package com.core.android.pocketbar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.core.cocktail.Bar;
import com.core.cocktail.Ingredient;

import java.util.List;

public class BarActivity extends AppCompatActivity {

    private LinearLayout mIngredientListLayout;
    private EditText mAddIngredientEntry;
    private String mIngredientToAdd;
    private Bar mBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mIngredientListLayout = findViewById(R.id.ingredient_list);
        mAddIngredientEntry = findViewById(R.id.add_ingredient_entry);
        mBar = new Bar();
    }

    private void updateInventory() {
        mIngredientListLayout.removeAllViews();
        List<Ingredient> inventory = mBar.getInventory();
        //inventory.add(new Ingredient(mIngredientToAdd));
        for (int i = 0; i < inventory.size(); i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View rowView = inflater.inflate(R.layout.bar_ingredient, null);
            TextView barIngredientView = rowView.findViewById(R.id.bar_ingredient_name);
            barIngredientView.setText(inventory.get(i).getName());
            // Add the new row before the add field button.
            mIngredientListLayout.addView(rowView);
        }
    }

    public void addIngredient(View view) {
        mIngredientToAdd = mAddIngredientEntry.getText().toString();
        if (mIngredientToAdd != null && !mIngredientToAdd.trim().equals("")) {
            boolean added = mBar.addIngredient(new Ingredient(mIngredientToAdd));
            if (added) {
                Toast addedToast = Toast.makeText(this, "Added Ingredient: " + mIngredientToAdd, Toast.LENGTH_SHORT);
                addedToast.show();
            } else {
                Toast addedToast = Toast.makeText(this, "Bar already had ingredient: " + mIngredientToAdd, Toast.LENGTH_SHORT);
                addedToast.show();
            }
        } else {
            Toast addedToast = Toast.makeText(this, "Enter an ingredient" + mIngredientToAdd, Toast.LENGTH_SHORT);
            addedToast.show();
        }
        updateInventory();
    }
}
