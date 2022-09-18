package com.example.foodstore.categoryList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodstore.R;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder> {

    private ArrayList<Category> categoryArrayList;
    private Context mcontext;
    private static View.OnClickListener mOnItemClickListener;

    public CategoryAdapter(ArrayList<Category> categoryArrayList, Context mcontext) {
        this.categoryArrayList = categoryArrayList;
        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {
        // Set the data to textview and imageview.
        Category category = categoryArrayList.get(position);
        holder.title.setText(category.getTitle());
        holder.img.setImageResource(category.getImagePath());
    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return categoryArrayList.size();
    }

    public void setOnItemClickListener(View.OnClickListener itemClickListener) {
        mOnItemClickListener = itemClickListener;
    }

    // View Holder Class to handle Recycler View.
    public static class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageView img;

        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.ctg_title);
            img = itemView.findViewById(R.id.ctg_img);

            itemView.setTag(this);
            itemView.setOnClickListener(mOnItemClickListener);
        }
    }
}
