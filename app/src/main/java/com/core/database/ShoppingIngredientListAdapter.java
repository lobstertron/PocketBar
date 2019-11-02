package com.core.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.android.pocketbar.R;

import java.util.List;

public class ShoppingIngredientListAdapter extends RecyclerView.Adapter<ShoppingIngredientListAdapter.ShoppingIngredientViewHolder> {

    private final LayoutInflater sInflater;
    private List<ShoppingIngredient> sIngredients; //Cached copy of ingredients

    public ShoppingIngredientListAdapter(Context context) {
        sInflater = LayoutInflater.from(context);
    }

    @Override
    public ShoppingIngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = sInflater.inflate(R.layout.shopping_ingredient, parent, false);
        return new ShoppingIngredientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ShoppingIngredientViewHolder holder, int position) {
        if (sIngredients != null) {
            ShoppingIngredient current = sIngredients.get(position);
            holder.shoppingIngredientItemView.setText(current.getIngredient());
        } else {
            // Covers the case of data not being ready yet.
            holder.shoppingIngredientItemView.setText("No Ingredient");
        }
    }

    public void setIngredients(List<ShoppingIngredient> words) {
        sIngredients = words;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (sIngredients != null)
            return sIngredients.size();
        else return 0;
    }

    class ShoppingIngredientViewHolder extends RecyclerView.ViewHolder {
        private final TextView shoppingIngredientItemView;

        private ShoppingIngredientViewHolder(View itemView) {
            super(itemView);
            shoppingIngredientItemView = itemView.findViewById(R.id.shopping_ingredient_name);
        }
    }
}
