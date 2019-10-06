package com.core.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.android.pocketbar.R;

import java.util.List;

public class CocktailListAdapter extends RecyclerView.Adapter<CocktailListAdapter.CocktailViewHolder> {

    //Builds a cocktail view from a xml specification
    private final LayoutInflater mInflater;
    private List<Cocktail> mCocktails; //Cocktails the adapter is managing

    public CocktailListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    /**
     *
     */
    public void setCocktails(List<Cocktail> cocktails) {
        mCocktails = cocktails;
        notifyDataSetChanged();
    }

    @Override
    public CocktailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CocktailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CocktailViewHolder holder, int position) {
        if (mCocktails != null) {
            Cocktail current = mCocktails.get(position);
            holder.cocktailItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.cocktailItemView.setText("No Cocktail");
        }
    }

    @Override
    public int getItemCount() {
        if (mCocktails != null)
            return mCocktails.size();
        else return 0;
    }

    class CocktailViewHolder extends RecyclerView.ViewHolder {
        private final TextView cocktailItemView;

        private CocktailViewHolder(View itemView) {
            super(itemView);
            cocktailItemView = itemView.findViewById(R.id.textView);
        }
    }
}
