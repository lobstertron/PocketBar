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

    private final LayoutInflater mInflater;
    private List<Cocktail> mCocktails; // Cached copy of words

    public CocktailListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public CocktailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CocktailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CocktailViewHolder holder, int position) {
        if (mCocktails != null) {
            Cocktail current = mCocktails.get(position);
            holder.wordItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Cocktail");
        }
    }

    public void setCocktails(List<Cocktail> words){
        mCocktails = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mCocktails has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mCocktails != null)
            return mCocktails.size();
        else return 0;
    }

    class CocktailViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private CocktailViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
