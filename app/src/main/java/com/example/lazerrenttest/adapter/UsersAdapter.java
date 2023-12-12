package com.example.lazerrenttest.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.ADM.adm_users;
import com.example.lazerrenttest.ADM.information.information_user;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.DetailsActivity;
import com.example.lazerrenttest.model.RecyclerUserModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter {

    List<RecyclerUserModel> usuarios;
    Context context;

    public UsersAdapter(List<RecyclerUserModel> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_users, parent, false);
        ViewholderClass vhclass = new ViewholderClass(view);
        return vhclass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ViewholderClass vhclass = (ViewholderClass) holder;
        RecyclerUserModel usuario = usuarios.get(position);

        String usuarioID = usuario.getUserId();

        vhclass.userFullname.setText(usuario.getFullname());
        vhclass.userEmail.setText(usuario.getEmail());
        vhclass.usernImoveis.setText(usuario.getImoveis() + " properties");

        if(usuario.getPhotoUser() != null){
            Glide.with(context)
                    .load(usuario.getPhotoUser())
                    .error(R.drawable.baseline_person) // Recurso de imagem para exibir em caso de erro
                    .into(vhclass.userPhoto);
        }
        vhclass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, information_user.class);

                Bundle bundle = new Bundle();
                bundle.putString("ID_do_usuario",usuario.getUserId());

                intent.putExtras(bundle);
                vhclass.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public class ViewholderClass extends RecyclerView.ViewHolder{

        TextView userFullname;
        TextView userEmail;
        TextView usernImoveis;
        ImageView userPhoto;

        public ViewholderClass(@NonNull View itemView) {
            super(itemView);

            userPhoto = itemView.findViewById(R.id.userPhoto);
            userFullname = itemView.findViewById(R.id.userName);
            userEmail = itemView.findViewById(R.id.userEmail);
            usernImoveis = itemView.findViewById(R.id.userImoveis);
        }
    }
}
