package com.core.database;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.android.pocketbar.R;
import com.core.android.pocketbar.RecipeActivity;

import java.util.ArrayList;
import java.util.List;

public class CocktailListAdapter extends RecyclerView.Adapter<CocktailListAdapter.CocktailViewHolder> implements Filterable {

    // key for intent extra
    public static final String NAME_MESSAGE = "com.core.android.pocketbar.NAME";
    public static final String DIRECTIONS_MESSAGE = "com.core.android.pocketbar.DIRECTIONS";
    public static final String COCKTAIL_ID = "com.core.android.pocketbar.COCKTAIL_ID";
    //Builds a cocktail view from a xml specification
    private final LayoutInflater mInflater;
    private List<Cocktail> mCocktails; //Initial master list of cocktails, filtering always filters this list
    private List<Cocktail> mCocktailsFiltered; //Filtered list of cocktails and the list actually backing the recyclerview
    private Context context;    // used to start RecipeActivity

    public CocktailListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    /**
     *
     */
    public void setCocktails(List<Cocktail> cocktails) {
        mCocktails = cocktails;
        mCocktailsFiltered = cocktails;
        notifyDataSetChanged();
    }

    @Override
    public CocktailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new CocktailViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CocktailViewHolder holder, int position) {
        if (mCocktailsFiltered != null) {
            Cocktail current = mCocktailsFiltered.get(position);
            holder.cocktailItemView.setText(current.getName());
        } else {
            // Covers the case of data not being ready yet.
            holder.cocktailItemView.setText("No Cocktail");
        }
    }

    @Override
    public int getItemCount() {
        if (mCocktails != null)
            return mCocktailsFiltered.size();
        else return 0;
    }

    public void openCocktailAtIndex(int index) {
        Intent intent = new Intent(context, RecipeActivity.class);
        String cocktailName = mCocktailsFiltered.get(index).getName();
        String mixingDirections = mCocktailsFiltered.get(index).getDirections();
        int id = mCocktailsFiltered.get(index).getId();
        // add text to the 'Intent' as an extra
        intent.putExtra(NAME_MESSAGE, cocktailName);
        intent.putExtra(DIRECTIONS_MESSAGE, mixingDirections);
        intent.putExtra(COCKTAIL_ID, id);

        context.startActivity(intent);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mCocktailsFiltered = mCocktails;
                } else {
                    List<Cocktail> filteredList = new ArrayList<>();
                    for (Cocktail row : mCocktails) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    mCocktailsFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mCocktailsFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mCocktailsFiltered = (ArrayList<Cocktail>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class CocktailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView cocktailItemView;

        private CocktailViewHolder(View itemView) {
            super(itemView);
            cocktailItemView = itemView.findViewById(R.id.textView);
            itemView.setOnClickListener(this);
        }

        public void onClick(View v){
            Intent intent = new Intent(context, RecipeActivity.class);
            // get position of item that was clicked
            int mPosition = getLayoutPosition();
            // use ^^ that to access the affected item in mWordList (LinkedList holding data)
            String cocktailName = mCocktailsFiltered.get(mPosition).getName();
            String mixingDirections = mCocktailsFiltered.get(mPosition).getDirections();
            int id = mCocktailsFiltered.get(mPosition).getId();
            // add text to the 'Intent' as an extra
            intent.putExtra(NAME_MESSAGE, cocktailName);
            intent.putExtra(DIRECTIONS_MESSAGE, mixingDirections);
            intent.putExtra(COCKTAIL_ID, id);

            context.startActivity(intent);
        }
    }
}
