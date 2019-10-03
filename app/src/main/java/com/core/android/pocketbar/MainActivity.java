package com.core.android.pocketbar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void launchBarActivity(View view) {
        Log.d(LOG_TAG, "Launching Bar Activity");
        Intent intent = new Intent(this, BarActivity.class);
        startActivity(intent);
    }

    public void launchSearchActivity(View view) {
        Log.d(LOG_TAG, "Launching Search Activity");
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }
}
