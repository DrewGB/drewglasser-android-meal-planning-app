package com.example.drewglasser_brown;

/*
 * Name: Drew Glasser-Brown
 * Purpose: This is the RecipeListAdapter for the RecyclerView
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>
{

    private ArrayList<Recipe> recipes;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    // constructor, gets data and a context
    RecipeListAdapter(Context context, ArrayList<Recipe> recipes)
    {
        this.mInflater = LayoutInflater.from(context); // will inflate from XML
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    void setClickListener(ItemClickListener itemClickListener)
    {
        this.mClickListener = itemClickListener;
    }
    // parent activity will implement this method to respond to click events
    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }

    /**
     * Set the value on the views
     * @param holder The ViewHolder which should be updated to represent the contents of the
     *        item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull RecipeListAdapter.ViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.txtTitle.setText(recipe.getTitle());

    }



    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtTitle;
        ViewHolder(View itemView)
        {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtRecipeTitle);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (mClickListener != null) mClickListener.onItemClick(v,
                    getAdapterPosition());
        }
    }
}
