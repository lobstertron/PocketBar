package com.core.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.android.pocketbar.R;

import java.util.List;

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private final LayoutInflater mInflater;
    private List<Ingredient> mIngredients; // Cached copy of words

    public IngredientListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        if (mIngredients != null) {
            Ingredient current = mIngredients.get(position);
            holder.wordItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.wordItemView.setText("No Ingredient");
        }
    }

    public void setIngredients(List<Ingredient> words){
        mIngredients = words;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mIngredients has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();
        else return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }
}
