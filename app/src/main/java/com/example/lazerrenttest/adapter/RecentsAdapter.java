package com.example.lazerrenttest.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.ADM.information.information_imovel;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.DetailsActivity;
import com.example.lazerrenttest.model.RecentsData;

import java.util.ArrayList;
import java.util.List;

public class RecentsAdapter extends RecyclerView.Adapter<RecentsAdapter.RecentsViewHolder> {

    Activity context;
    List<RecentsData> recentsDataList;

    public RecentsAdapter(Activity context, List<RecentsData> recentsDataList) {
        this.context = context;
        this.recentsDataList = recentsDataList;
    }

    @NonNull
    @Override
    public RecentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itemview_recents_bg, parent, false);

        // here we create a recyclerview row item layout file
        return new RecentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(recentsDataList.get(position).getTitle());
        holder.address.setText(recentsDataList.get(position).getAddress());
        holder.price.setText("r$"+recentsDataList.get(position).getPrice());
//        holder.placeImage.setImageResource(recentsDataList.get(position).getImageUrl());

        if(recentsDataList.get(position).getPhoto() != null){
            Glide.with(context)
                    .load(recentsDataList.get(position).getPhoto())
                    .error(R.drawable.baseline_house_24)
                    .into(holder.placeImage);
        }

//        int drawableResouceID=holder.itemView.getResources().getIdentifier(recentsDataList.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());
//        Glide.with(holder.itemView.getContext())
//                .load(drawableResouceID)
//                .into(holder.placeImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(context, DetailsActivity.class);
                Log.d("Adapter", "criou intent");
                Bundle bundle = new Bundle();
                Log.d("Adapter", "inicializou bundle");
                bundle.putString("ID_do_imovel",recentsDataList.get(position).getID());
                bundle.putString("telaAntiga", "recentes");
                Log.d("Adapter", "putou as strings");

                intent.putExtras(bundle);
                context.startActivity(intent);
                Log.d("Adapter", "startou activity");
            }
        });

    }

    @Override
    public int getItemCount() {
        return recentsDataList.size();
    }

    public static final class RecentsViewHolder extends RecyclerView.ViewHolder{

        ImageView placeImage;
        TextView title, address, price;

        public RecentsViewHolder(@NonNull View itemView) {
            super(itemView);

            placeImage = itemView.findViewById(R.id.idPhoto);
            title = itemView.findViewById(R.id.titleTxt);
            address = itemView.findViewById(R.id.addressTxt);
            price = itemView.findViewById(R.id.priceTxtt);

        }
    }

    public void filterList(List<RecentsData> filter){
        recentsDataList = filter;
        notifyDataSetChanged();
    }
}