package com.core.android.pocketbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void launchBarActivity(View view) {
        Intent intent = new Intent(this, BarActivity.class);
        startActivity(intent);
    }

    public void launchSearchActivity(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void launchShoppingList(View view) {

        Intent intentShopList = new Intent(this, ShoppingList.class);
        startActivity(intentShopList);

    }
}
