package com.example.lazerrenttest.fragmentsMyLocation;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.adapter.ImoveisAdapter;
import com.example.lazerrenttest.adapter.MyLocationsAdapter;
import com.example.lazerrenttest.databinding.FragmentMylocationsViewMyLocationsBinding;
import com.example.lazerrenttest.model.RecyclerImovelModel;
import com.example.lazerrenttest.model.RecyclerMyLocations;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import android.os.Handler;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ViewMyLocationsFragment extends Fragment {


    public ViewMyLocationsFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    FragmentMylocationsViewMyLocationsBinding binding;
    Context context;
    SharedViewModel sharedViewModel;
    ProgressDialog dialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMylocationsViewMyLocationsBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dialog = new ProgressDialog(getContext());

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        dialog.show();

        setBackButton(view);
        context = getContext();

        binding.myLocationRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        binding.btnNewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler = new Handler();
                handler.postDelayed(() -> Navigation.findNavController(view).navigate(R.id.action_viewMyLocations_to_photos), 200);
            }
        });

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db.collection("user").document(userId).collection("imoveis")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<String> imovelIds = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                        String imovelId = documentSnapshot.getId();
                        imovelIds.add(imovelId);
                    }

                    if(imovelIds.size() == 0){
                        dialog.dismiss();
                    }

                    List<RecyclerMyLocations> imovelList = new ArrayList<>();


                    for (String imovelId : imovelIds) {
                        db.collection("imovel").document(imovelId)
                                .get()
                                .addOnSuccessListener(documentSnapshot -> {
                                    if (documentSnapshot.exists()) {
                                        String typesofproperties = documentSnapshot.getString("typesofproperties");
                                        String title = documentSnapshot.getString("title");
                                        String ID_imoveis = documentSnapshot.getString("id_imoveis");
                                        String status = documentSnapshot.getString("status");

                                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                        StorageReference imageRef = storageRef.child("images/imovel/" + ID_imoveis + "/Photo Principal");

                                        imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                                            RecyclerMyLocations imovel = new RecyclerMyLocations(title, status, typesofproperties, ID_imoveis, uri);
                                            imovelList.add(imovel);

                                            if (imovelList.size() == imovelIds.size()) { // Correção aqui
                                                MyLocationsAdapter adapter = new MyLocationsAdapter(context, imovelList);
                                                binding.myLocationRecycler.setAdapter(adapter);
                                                dialog.dismiss();
                                            }
                                        }).addOnFailureListener(e -> {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            dialog.dismiss();
                                        });
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dialog.dismiss();
                        Toast.makeText(context, "no properties", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setBackButton(View view) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    Intent intent = new Intent(requireContext(), MainActivity.class);
                    startActivity(intent);

                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}