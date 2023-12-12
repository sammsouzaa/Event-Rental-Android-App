package com.example.lazerrenttest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.Database.Information_getImovel;
import com.example.lazerrenttest.databinding.ActyDetailsBinding;
import com.example.lazerrenttest.model.IdImovelModel;
import com.example.lazerrenttest.model.Inf_getImovelModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailsActivity extends AppCompatActivity {

    //declarando variaveis;
    private ActyDetailsBinding binding;

    private String IDimovel, telaantiga;
    private Information_getImovel information_getImovel;
    private ProgressDialog dialog;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private IdImovelModel idImovelModel = new IdImovelModel();

//    LinearLayout.LayoutParams params;
//    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActyDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //fazendo o instanciamento com banco de dados;
        mAuth = mAuth.getInstance();
        firebaseFirestore = firebaseFirestore.getInstance();

        //Log.d para debug
        Log.d("Adapter", "entrou na activity");

        //configurando a dialog de progresso;
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.show();

        //recebendo o bandle de informações enviadas pelo intent antigo;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            IDimovel = bundle.getString("ID_do_imovel");
            telaantiga = bundle.getString("telaAntiga");
            if ("mylocations".equals(telaantiga)){
                binding.button1000.setText("edit property");
            }
        }

        //chamando a classe que recebe as informações do banco de dados (firebase);
        idImovelModel.setId_imovel(IDimovel);
        information_getImovel = new Information_getImovel(this, IDimovel);

        information_getImovel.fetchImovelInfo(new Information_getImovel.ImovelInfoCallback() {
            @Override
            public void onImovelInfoLoaded() {
                //carrega os dados nos elementos da tela
                loadData();
            }
            @Override
            public void onImovelInfoFailed(Exception e) {

                //fechando a dialog de progresso e mostrando erro;
                dialog.dismiss();
                Toast.makeText(DetailsActivity.this, "Erro ao carregar informações do imóvel", Toast.LENGTH_SHORT).show();
            }
        });

        binding.button1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //a ultima tela gerada antes dessa, foi enviado um intent com um extra (string) que é recebido no começo do codigo, nesse momento
                //fazemos uma verificação de qual foi a ultima tela, se foi a tela mylocations, ele vai para a tela de editar imoveis,
                if ("mylocations".equals(telaantiga)){

                    //gerando novo intent
                    Intent intent = new Intent(DetailsActivity.this, edit_data_my_location.class);

                    //logd para debug e recebendo as informações do banco de dados novamente;
                    Log.d("editdata", "Criou intent");
                    Inf_getImovelModel model = getModelData(information_getImovel);
                    Log.d("editdata", model.getTitle());

                    //inserindo o objeto ao intent e inicializando a nova activity com o intent;
                    intent.putExtra("objeto", model);
                    Log.d("editdata", "putou o model");
                    startActivity(intent);

                }else{
                    //caso a ultima tela tenha sido qualquer outra, ele vai para a tela de reserva dos imoveis;
                    Intent intent = new Intent(DetailsActivity.this, LocationActy.class);
                    Inf_getImovelModel model = getModelData(information_getImovel);
                    intent.putExtra("objeto", model);

                    startActivity(intent);
                }
            }
        });

//        binding.favoriteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                DocumentReference documentReference = firebaseFirestore.collection("user").document(usuarioID);
//                documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot documentSnapshot = task.getResult();
//                            if (documentSnapshot != null && documentSnapshot.exists()) {
//                                if(Integer.parseInt(documentSnapshot.getString("favoritos")) != 0){
//
//                                    int favoritos = Integer.parseInt(documentSnapshot.getString("favoritos")) + 1;
//
//                                    firebaseFirestore.collection("user").document(mAuth.getUid()).collection("imoveis")
//                                            .document(idImovelModel.getId_imovel()).set(idImovelModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                                @Override
//                                                public void onSuccess(Void unused) {
//
//                                                    documentReference.update("favoritos", Integer.toString(favoritos));
//                                                    binding.favoriteBtn.setBackgroundResource(R.color.blue);
//
//                                                }
//                                            }).addOnFailureListener(new OnFailureListener() {
//                                                @Override
//                                                public void onFailure(@NonNull Exception e) {
//                                                    Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                                }
//                                            });
//                                }
//                            }
//                        }
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//
//                        Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }

    //apenas recebendo e atribuindo as informações em variaveis locais
    //metodo desnecessario, quando necessario, pode somente usar a classe get diretamente, ao invez de utilizar todo esse processo apenas para variaveis locais.
    private Inf_getImovelModel getModelData(Information_getImovel information_getImovel) {

        String status, typesofproperties, title, description, propertysize, price, capacity, beds, baths, street, streetnumber, neighborhood, city, ID_imoveis, idProprietario, nFavoritos, nLocations, ID;
        Uri photoPrincipal, photo1, photo2, photo3, photo4;

        status = information_getImovel.getStatus();
        typesofproperties = information_getImovel.getTypesofproperties();
        title = information_getImovel.getTitle();
        description = information_getImovel.getDescription();
        propertysize = information_getImovel.getPropertysize();
        price = information_getImovel.getPrice();
        capacity = information_getImovel.getCapacity();
        beds = information_getImovel.getBeds();
        baths = information_getImovel.getBaths();
        street = information_getImovel.getStreet();
        streetnumber = information_getImovel.getStreetnumber();
        neighborhood = information_getImovel.getNeighborhood();
        city = information_getImovel.getCity();
        ID_imoveis = information_getImovel.getID_imoveis();
        idProprietario = information_getImovel.getIdProprietario();
        nFavoritos = information_getImovel.getnFavoritos();
        nLocations = information_getImovel.getnLocations();

        photoPrincipal = information_getImovel.getPhotoPrincipal();
        photo1 = information_getImovel.getPhoto1();
        photo2 = information_getImovel.getPhoto2();
        photo3 = information_getImovel.getPhoto3();
        photo4 = information_getImovel.getPhoto4();

        //cria um novo modelo e retorna o modelo;
        Inf_getImovelModel current = new Inf_getImovelModel(status, typesofproperties, title, description, propertysize, price, capacity, beds, baths, street, streetnumber,
                neighborhood, city, ID_imoveis, idProprietario, nFavoritos, nLocations, photoPrincipal, photo1, photo2, photo3, photo4);

        return current;
    }

    //metodo para carregar as informações no elemento da tela
    private void loadData() {

        binding.viewTitle.setText(information_getImovel.getTitle());
        binding.viewDescription.setText(information_getImovel.getDescription());
        binding.viewPropertysize.setText(information_getImovel.getPropertysize() + "m2");
        binding.viewPrice.setText("r$"+information_getImovel.getPrice() + " /day");
        binding.viewCapacity.setText(information_getImovel.getCapacity());
        binding.viewNBeds.setText(information_getImovel.getBeds() + " beds");
        binding.viewNBaths.setText(information_getImovel.getBaths() + " baths");
        binding.viewAdress.setText(information_getImovel.getStreet() + ", " + information_getImovel.getStreetnumber() );

        binding.viewNFavorites.setText(information_getImovel.getnFavoritos());
        binding.viewNLocations.setText(information_getImovel.getnLocations());

        Glide.with(this).load(information_getImovel.getPhotoPrincipal()).into(binding.viewPhotoPrincipal);
        Glide.with(this).load(information_getImovel.getPhoto1()).into(binding.viewPhoto1);
        Glide.with(this).load(information_getImovel.getPhoto2()).into(binding.viewPhoto2);
        Glide.with(this).load(information_getImovel.getPhoto3()).into(binding.viewPhoto3);
        Glide.with(this).load(information_getImovel.getPhoto4()).into(binding.viewPhoto4);

        dialog.dismiss();

        //ATIVAR QUANDO O SISTEMA DE LOCAÇÃO ESTIVER FUNCIONANDO NORMALMENTE

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user.getUid().equals(information_getImovel.getIdProprietario())){
//            binding.button1000.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //ao apertar voltar...
        startActivity(new Intent(DetailsActivity.this, MainActivity.class));
    }
}