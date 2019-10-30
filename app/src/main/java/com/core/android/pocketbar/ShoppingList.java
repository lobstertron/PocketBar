package com.core.android.pocketbar;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

public class ShoppingList extends AppCompatActivity {


    private final LinkedList<String> shoppingList = new LinkedList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_shopping_list);
    }
}
