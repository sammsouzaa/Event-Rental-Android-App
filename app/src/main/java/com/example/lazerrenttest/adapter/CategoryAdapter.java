package com.example.lazerrenttest.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.ActyHomeSearch;
import com.example.lazerrenttest.model.CategoryModel;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<CategoryModel> items;
    private Context context;

    public CategoryAdapter(ArrayList<CategoryModel> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_categories, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, int position) {
        CategoryModel item = items.get(position);

        holder.titleCat.setText(item.getTitle());
        int drawableResourceId = holder.itemView.getResources().getIdentifier(item.getPicPath(),
                "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.imageCat);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActyHomeSearch.class);
                intent.putExtra("type",item.getType());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleCat;
        private ImageView imageCat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleCat = itemView.findViewById(R.id.titleCat);
            imageCat = itemView.findViewById(R.id.imageCat);


        }
    }
}
