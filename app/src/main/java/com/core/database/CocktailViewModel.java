package com.core.database;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Random;

public class CocktailViewModel extends AndroidViewModel {
    private CocktailRepository mRepository;
    private List<Cocktail> mAllCocktails;

    public CocktailViewModel(Application application) {
        super(application);
        mRepository = new CocktailRepository(application);
    }

    public List<Cocktail> getAllCocktails() {
        return mAllCocktails;
    }

    public void insert(Cocktail ingredient) {
        mRepository.insert(ingredient);
    }

    public List<Cocktail> searchCocktails(String... ingredients) {
        return mRepository.searchCocktails(ingredients);
    }

}
