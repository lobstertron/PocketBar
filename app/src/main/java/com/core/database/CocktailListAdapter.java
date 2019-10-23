package com.core.database;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.core.android.pocketbar.R;
import com.core.android.pocketbar.RecipeActivity;

import java.util.List;

public class CocktailListAdapter extends RecyclerView.Adapter<CocktailListAdapter.CocktailViewHolder> {

    // key for intent extra
    public static final String NAME_MESSAGE = "com.core.android.pocketbar.NAME";
    public static final String DIRECTIONS_MESSAGE = "com.core.android.pocketbar.DIRECTIONS";
    //Builds a cocktail view from a xml specification
    private final LayoutInflater mInflater;
    private List<Cocktail> mCocktails; //Cocktails the adapter is managing
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
            String cocktailName = mCocktails.get(mPosition).getName();
            String mixingDirections = mCocktails.get(mPosition).getDirections();
            // add text to the 'Intent' as an extra
            intent.putExtra(NAME_MESSAGE, cocktailName);
            intent.putExtra(DIRECTIONS_MESSAGE, mixingDirections);

            context.startActivity(intent);
        }
    }
}
