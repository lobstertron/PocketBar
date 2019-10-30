package com.core.android.pocketbar;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.LinkedList;




public class ShoppingList extends AppCompatActivity {


    private LinkedList<String> shoppingList = new LinkedList<>();
    private EditText mAddItemEntry;

    private LayoutInflater mShopListInflater;




    public ShoppingList(Context context, LinkedList<String> itemList) {
        mShopListInflater = LayoutInflater.from(context);
        this.shoppingList = itemList;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_shopping_list);

        mAddItemEntry = findViewById(R.id.editText_shoplistadd);

    }

    public void addItem(View view) {

        String ingredientToAdd = mAddItemEntry.getText().toString();
        Log.i("AddItem Method", "The Following Was Added: " + ingredientToAdd);
    }
}
