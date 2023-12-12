package com.example.lazerrenttest.adapter;

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
import com.example.lazerrenttest.model.RecyclerImovelModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class ImoveisAdapter extends RecyclerView.Adapter{

    List<RecyclerImovelModel> imoveis;
    Context context;
    FirebaseFirestore mFire = FirebaseFirestore.getInstance();

    public ImoveisAdapter(List<RecyclerImovelModel> imoveis, Context context) {
        this.imoveis = imoveis;
        this.context = context;
        Log.d("pendentes", "entrou no adapter: "+imoveis.size() );
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemview_properties, parent, false);
        ImoveisAdapter.ViewholderClass vhclass = new ImoveisAdapter.ViewholderClass(view);
        return vhclass;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Log.d("pendentes", "tamanho da lista passada: "+imoveis.size() );

        ImoveisAdapter.ViewholderClass vhclass = (ImoveisAdapter.ViewholderClass) holder;
        RecyclerImovelModel imovel = imoveis.get(position);

        DocumentReference documentReference = mFire.collection("user").document(imovel.getIdProprietario());

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                DocumentSnapshot documentSnapshot = task.getResult();

                Log.d("pendentes", "title "+imovel.getTitle());
                Log.d("pendentes", "type "+imovel.getTypesofproperties());
                Log.d("pendentes", "username "+documentSnapshot.getString("fullname"));

                vhclass.i_tittle.setText(imovel.getTitle());
                vhclass.i_type_property.setText(imovel.getTypesofproperties());
                vhclass.i_username.setText(documentSnapshot.getString("fullname"));

                if(imovel.getPhotoPrincipal() != null){
                    Glide.with(context)
                            .load(imovel.getPhotoPrincipal())
                            .error(R.drawable.baseline_house_24)
                            .into(vhclass.i_photo_principal);
                }
            }
        });
        vhclass.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, information_imovel.class);

                Bundle bundle = new Bundle();
                bundle.putString("ID_do_imovel",imovel.getID_imoveis());

                intent.putExtras(bundle);
                vhclass.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imoveis.size();
    }

    public class ViewholderClass extends RecyclerView.ViewHolder{

        TextView i_tittle;
        TextView i_type_property;
        TextView i_username;
        ImageView i_photo_principal;

        public ViewholderClass(@NonNull View itemView) {
            super(itemView);

            i_tittle = itemView.findViewById(R.id.i_tittle);
            i_type_property = itemView.findViewById(R.id.i_type_property);
            i_username = itemView.findViewById(R.id.i_username);
            i_photo_principal = itemView.findViewById(R.id.i_photo_principal);
        }
    }
}
