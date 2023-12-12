package com.example.lazerrenttest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.DetailsActivity;
import com.example.lazerrenttest.model.RecyclerMyLocations;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyLocationsAdapter extends RecyclerView.Adapter<MyLocationsAdapter.MyLocationsViewHolder> {

    Context context;
    List<RecyclerMyLocations> imoveis;

    public MyLocationsAdapter(Context context, List<RecyclerMyLocations> imoveis) {
        this.context = context;
        this.imoveis = imoveis;
    }

    @NonNull
    @Override
    public MyLocationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.itemview_mylocations, parent, false);
        MyLocationsAdapter.MyLocationsViewHolder vhclass = new MyLocationsAdapter.MyLocationsViewHolder(view);
        return vhclass;
    }

    @Override
    public void onBindViewHolder(@NonNull MyLocationsViewHolder holder, int position) {

        MyLocationsAdapter.MyLocationsViewHolder vhclass = (MyLocationsAdapter.MyLocationsViewHolder) holder;
        RecyclerMyLocations imovel = imoveis.get(position);

        vhclass.title.setText(imovel.getTitle());
        vhclass.type.setText(imovel.getType());
        vhclass.status.setText(imovel.getStatus());

        if(imovel.getImageUrl() != null){
            Glide.with(context)
                    .load(imovel.getImageUrl())
                    .error(R.drawable.baseline_house_24)
                    .into(vhclass.pic);
        }

        vhclass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, DetailsActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("ID_do_imovel",imovel.getID());
                bundle.putString("telaAntiga", "mylocations");

                intent.putExtras(bundle);
                vhclass.itemView.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return imoveis.size();
    }

    public static final class MyLocationsViewHolder extends RecyclerView.ViewHolder{

        ImageView pic;
        TextView title, type, status;

        public MyLocationsViewHolder(@NonNull View itemView) {
            super(itemView);

            pic = itemView.findViewById(R.id.ID_property_pic);
            title = itemView.findViewById(R.id.ID_property_title);
            type = itemView.findViewById(R.id.ID_property_type);
            status = itemView.findViewById(R.id.ID_property_status);

        }
    }
}