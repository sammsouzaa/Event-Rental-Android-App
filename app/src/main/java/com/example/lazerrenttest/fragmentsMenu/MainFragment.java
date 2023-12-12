package com.example.lazerrenttest.fragmentsMenu;

import static android.app.Activity.RESULT_OK;
import static android.content.Intent.getIntent;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.R;

import com.example.lazerrenttest.activities.ActyHomeSearch;
import com.example.lazerrenttest.adapter.CategoryAdapter;
import com.example.lazerrenttest.adapter.RecentsAdapter;
import com.example.lazerrenttest.databinding.FragmentMainBinding;
import com.example.lazerrenttest.model.CategoryModel;
import com.example.lazerrenttest.model.RecentsData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentMainBinding binding;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

//    RecyclerView recentRecycler, topPlacesRecycler;
//    RecentsAdapter recentsAdapter;
//    MyLocationsAdapter topPlacesAdapter;

    private StorageReference storageReferance;

    private ImageView imageview;

    private Uri image;

    private ProgressDialog progressDialog;

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == RESULT_OK){
                if(result.getData() != null){
                    image = result.getData().getData();
                    Glide.with(getContext()).load(image).into(imageview);
                }
            }else {
                Toast.makeText(context, "please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private FirebaseFirestore mFire = FirebaseFirestore.getInstance();
    private ProgressDialog dialog;
    private String usuarioID;

    private Activity act = getActivity();
    private CategoryAdapter categoryAdapter;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        binding.recentRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        dialog = new ProgressDialog(getContext());

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();

        initRecyclerRecents();
        initRecyclerCategories();

        setProfileImage();

        FirebaseApp.initializeApp(getActivity());
        storageReferance = FirebaseStorage.getInstance().getReference();

        context = getContext();

        Dialog dialog = new Dialog(context);

        declararDialog(dialog);

        verifyPhotoUsername(dialog);

        binding.profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(getContext(), ProfileUser.class));

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();

                final String directoryPath = "imagens/teste2";

                StorageReference directoryRef = storageRef.child(directoryPath);

                directoryRef.listAll()
                        .addOnSuccessListener(new OnSuccessListener<ListResult>() {
                            @Override
                            public void onSuccess(ListResult listResult) {
                                // Iterando sobre os itens no diretório
                                for (StorageReference item : listResult.getItems()) {
                                    // Deletando cada arquivo no diretório
                                    item.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            // Ocorreu um erro ao deletar o arquivo
                                        }
                                    });
                                }

                                // Deletando o diretório vazio após excluir todos os arquivos
                                directoryRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context, "Diretorio deletado", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(context, "Diretorio nao deletado: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "erro ao listar o diretorio " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        binding.textviewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActyHomeSearch.class);
                intent.putExtra("type","nada");
                startActivity(intent);
            }
        });
    }

    private void initRecyclerCategories() {
        binding.categoryRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        ArrayList<CategoryModel> items = new ArrayList<>();

        items.add(new CategoryModel(getString(R.string.categories_house), "caticon_house", "House"));
        items.add(new CategoryModel(getString(R.string.categories_mansion), "caticon_mansion", "Mansion"));
        items.add(new CategoryModel(getString(R.string.categories_farm), "caticon_farm", "Farm"));
        items.add(new CategoryModel(getString(R.string.categories_partyroom), "caticon_partyhouse", "Party Room"));
        items.add(new CategoryModel(getString(R.string.categories_pool), "caticon_poolhouse", "House w pool"));

        categoryAdapter = new CategoryAdapter(items, getContext());
        binding.categoryRecycler.setAdapter(categoryAdapter);
    }

    private void initRecyclerRecents() {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference imovRef = db.collection("imovel");

        imovRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) {
                    dialog.dismiss();
                }
                else {
                    List<RecentsData> imoveis = new ArrayList<>();

                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        String typesofproperties = documentSnapshot.getString("typesofproperties");
                        String title = documentSnapshot.getString("title");
                        String ID_imoveis = documentSnapshot.getString("id_imoveis");
                        String status = documentSnapshot.getString("status");
                        String type = documentSnapshot.getString("typesofproperties");

                        String address = documentSnapshot.getString("street") + ", " + documentSnapshot.getString("streetnumber");
                        String price = documentSnapshot.getString("price");

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference imageRef = storageRef.child("images/imovel/" + ID_imoveis + "/Photo Principal");

                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                            RecentsData imovel = new RecentsData(title, address, price, ID_imoveis, status, type, uri);
                            imoveis.add(imovel);

                            if (imoveis.size() == task.getResult().size()) {
                                List<RecentsData> filteredImoveis = new ArrayList<>();
                                for (RecentsData imovelItem : imoveis) {
                                    if ("approved".equals(imovelItem.getStatus())) {
                                        filteredImoveis.add(imovelItem);
                                    }
                                }
                                RecentsAdapter adapter = new RecentsAdapter(getActivity(), filteredImoveis);
                                binding.recentRecycler.setAdapter(adapter);
                                dialog.dismiss();
                            }
                        }).addOnFailureListener(e -> {
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        });
                    }
                }
            }
        });
    }

    private void setProfileImage() {

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference photoRef = storageRef.child("images/profile/" + usuarioID + "/profilephoto");
        Log.d("TESTE", " teste 1 ");

        photoRef.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        Log.d("TESTE", " teste 2 " + uri);

                        Picasso.get().load(uri).into(binding.profileBtn);

                        Log.d("TESTE", " teste 3 " + uri);
                    }
                });
    }

    private void declararDialog(Dialog dialog) {

        progressDialog = new ProgressDialog(getContext());

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_photo_username);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText edittext_add_username = dialog.findViewById(R.id.edittext_add_username);
        LinearLayout image_add_photo = dialog.findViewById(R.id.image_add_photo);
        Button button = dialog.findViewById(R.id.button);
        imageview = dialog.findViewById(R.id.imageview);

        image_add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                activityResultLauncher.launch(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = edittext_add_username.getText().toString().trim();

                if(!username.isEmpty()){

                    if(image != null) {

                        progressDialog.show();
                        DocumentReference userRef = FirebaseFirestore.getInstance().collection("user").document(usuarioID);

                        userRef.update("username", username)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {

                                            StorageReference reference = storageReferance.child("images/profile/" + usuarioID + "/profilephoto");
                                            reference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                @Override
                                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                    progressDialog.dismiss();
                                                    dialog.dismiss();
                                                    Toast.makeText(context, "image uploaded successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    dialog.dismiss();
                                                }
                                            });
                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                    }else{
                        Toast.makeText(context, "please, select an image", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(context, "please, set a username", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

//    private void setTopPlacesRecycler(List<RecyclerMyLocations> topPlacesDataList) {
//
//        topPlacesRecycler = binding.topRecycler;
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
//        topPlacesRecycler.setLayoutManager(layoutManager);
//        topPlacesAdapter = new TopPlacesAdapter(context, topPlacesDataList);
//        topPlacesRecycler.setAdapter(topPlacesAdapter);
//
//    }
//
//    private void setRecentRecycler(List<RecentsData> recentsDataList) {
//
//        recentRecycler = binding.recentRecycler;
//        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
//        recentRecycler.setLayoutManager(layoutManager);
//        recentsAdapter = new RecentsAdapter(context, recentsDataList);
//        recentRecycler.setAdapter(recentsAdapter);
//
//    }

    private void verifyPhotoUsername(Dialog dialog) {

        Log.d("TESTE", " on function ");

        DocumentReference documentReference = mFire.collection("user").document(usuarioID);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot != null && documentSnapshot.exists()) {

                        String username = documentSnapshot.getString("username").trim();

                        if(username.equals("")){

                            // Criar a referência ao arquivo no Storage
                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference photoRef = storageRef.child("images/profile/" + usuarioID + "/profilephoto");

                            // Verificar a existência da foto

                            photoRef.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            // A foto existe
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.show();
                                        }
                                    });
                        }
                    }
                }
            }
        });
    }
}