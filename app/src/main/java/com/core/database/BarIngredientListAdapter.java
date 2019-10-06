package com.core.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.android.pocketbar.R;

import java.util.List;

public class BarIngredientListAdapter extends RecyclerView.Adapter<BarIngredientListAdapter.IngredientViewHolder> {

    private final LayoutInflater mInflater;
    private List<BarIngredient> mIngredients; //Cached copy of ingredients

    public BarIngredientListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new IngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        if (mIngredients != null) {
            BarIngredient current = mIngredients.get(position);
            holder.ingredientItemView.setText(current.getIngredient());
        } else {
            // Covers the case of data not being ready yet.
            holder.ingredientItemView.setText("No Ingredient");
        }
    }

    public void setIngredients(List<BarIngredient> words) {
        mIngredients = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mIngredients != null)
            return mIngredients.size();
        else return 0;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView ingredientItemView;

        private IngredientViewHolder(View itemView) {
            super(itemView);
            ingredientItemView = itemView.findViewById(R.id.textView);
        }
    }
}
