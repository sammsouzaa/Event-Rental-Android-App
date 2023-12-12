package com.example.lazerrenttest.fragmentsMyLocation;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.databinding.FragmentMylocationsAddLocationReviewalldataBinding;
import com.example.lazerrenttest.model.IdImovelModel;
import com.example.lazerrenttest.model.ImovelModel;
import com.example.lazerrenttest.model.ImovelPhotoModel;
import com.example.lazerrenttest.registers.RegisterImovel;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.Random;

public class AddLocationReviewAllDataFragment extends Fragment {

    public AddLocationReviewAllDataFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    FragmentMylocationsAddLocationReviewalldataBinding binding;

    SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMylocationsAddLocationReviewalldataBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return binding.getRoot();
    }

    private ProgressDialog progressDialog;

    String typeproperty, title, price, description, size, capacity, beds, baths, street, number, neighborhood, city;
    Uri photoPrincipal, photo1, photo2, photo3, photo4;

    Context context;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sharedViewModel.setReview("sim");

        setBackButton(view);

        setAllInformations();

        context = getContext();
        Dialog dialog = new Dialog(context);

        showDialog(dialog, view);

        onClickListeners(dialog, view);
        onClickListenersEdit(view);

        progressDialog = new ProgressDialog(getContext());

    }
    private void setBackButton(View view) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    sharedViewModel.setReview(null);
                    Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_addressDetails);

                    return true;
                }
                return false;
            }
        });
    }

    private void onClickListenersEdit(View view) {

        binding.editAdditionalInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_basicdetails3);
            }
        });
        binding.editDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_basicdetails2);
            }
        });
        binding.editPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_basicdetails3);
            }
        });
        binding.editAddressInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_addressDetails);
            }
        });
        binding.editTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_basicdetails1);
            }
        });
        binding.editPropertytype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_basicdetails);
            }
        });
        binding.editPhotos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_photos);
            }
        });

    }

    private void onClickListeners(Dialog dialog, View view) {

        binding.btnCloseToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });

        binding.btnBackprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedViewModel.setReview(null);
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_addressDetails);

            }
        });
        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();

                String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                Random gerador = new Random();
                String idImovel = "";

                for (int i = 0; i < 10; i++){
                    idImovel += Integer.toString(gerador.nextInt(9));
                }
                Log.d("Registro", "criou o id do imovel");

                ImovelModel imovelModel = new ImovelModel("pendente", typeproperty,title, description, size, price, capacity, beds, baths,
                        street, number, neighborhood, city, idImovel, usuarioID, "0", "0");

                Log.d("Registro", "criou o imovel model");

                IdImovelModel idImovelModel = new IdImovelModel(idImovel);
                Log.d("Registro", "criou o ID imovel model");

                ImovelPhotoModel imovelPhotoModel = new ImovelPhotoModel(photoPrincipal, photo1, photo2, photo3, photo4);
                Log.d("Registro", "criou o photos model");

                Log.d("Registro", "entrou na class registrar");
                RegisterImovel registerImovel = new RegisterImovel(getActivity(), imovelModel, idImovelModel, imovelPhotoModel);
                Log.d("Registro", "saiu na class registrar");

                Log.d("Registro", "saiu do reset");

                resetAll();
                progressDialog.dismiss();
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_viewMyLocations);
                Toast.makeText(getContext(), "property submitted for approval", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void resetAll() {
        sharedViewModel.setTitle(null);
        sharedViewModel.setTypeproperty(null);
        sharedViewModel.setDescription(null);
        sharedViewModel.setCapacity(null);
        sharedViewModel.setPropertysize(null);
        sharedViewModel.setBaths(null);
        sharedViewModel.setBeds(null);
        sharedViewModel.setPrice(null);
        sharedViewModel.setStreet(null);
        sharedViewModel.setNumber(null);
        sharedViewModel.setNeighborhood(null);
        sharedViewModel.setCity(null);
        sharedViewModel.setReview(null);
        sharedViewModel.setPhotoprincipal(null);
        sharedViewModel.setPhoto1(null);
        sharedViewModel.setPhoto2(null);
        sharedViewModel.setPhoto3(null);
        sharedViewModel.setPhoto4(null);
    }

    private void loadData() {

        binding.showPropertyTypeHouse.setText(typeproperty);
        binding.showPropertyTitle.setText(title);
        binding.showPropertyDescription.setText(description);
        binding.showPropertySize.setText(size + "m2");
        binding.showPropertyPrice.setText("r$ " + price + " /day");
        binding.showPropertyCapacity.setText("Capacity: " + capacity + " people");
        binding.showPropertyBeds.setText(beds + " beds");
        binding.showPropertyBaths.setText(baths + " baths");
        binding.showPropertyStreet.setText(street);
        binding.showPropertyNumber.setText(number);
        binding.showPropertyNeighborhood.setText(neighborhood);
        binding.showPropertyCity.setText(city);

        Glide.with(getContext()).load(photoPrincipal).into(binding.showPropertyPhotoPrincipal);
        Glide.with(getContext()).load(photo1).into(binding.showPropertyPhoto1);
        Glide.with(getContext()).load(photo2).into(binding.showPropertyPhoto2);
        Glide.with(getContext()).load(photo3).into(binding.showPropertyPhoto3);
        Glide.with(getContext()).load(photo4).into(binding.showPropertyPhoto4);

    }

    private void setAllInformations() {

        typeproperty = sharedViewModel.getTypeproperty().getValue();
        title = sharedViewModel.getTitle().getValue();
        price = sharedViewModel.getPrice().getValue();
        description = sharedViewModel.getDescription().getValue();
        size = sharedViewModel.getPropertysize().getValue();
        capacity = sharedViewModel.getCapacity().getValue();
        beds = sharedViewModel.getBeds().getValue();
        baths = sharedViewModel.getBaths().getValue();
        street = sharedViewModel.getStreet().getValue();
        number = sharedViewModel.getNumber().getValue();
        neighborhood = sharedViewModel.getNeighborhood().getValue();
        city = sharedViewModel.getCity().getValue();

        photoPrincipal = sharedViewModel.getPhotoprincipal().getValue();
        photo1 = sharedViewModel.getPhoto1().getValue();
        photo2 = sharedViewModel.getPhoto2().getValue();
        photo3 = sharedViewModel.getPhoto3().getValue();
        photo4 = sharedViewModel.getPhoto4().getValue();

        loadData();
    }

    private void showDialog(Dialog dialog, View view) {

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_sheet_close);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        Button back = dialog.findViewById(R.id.btn_back);
        Button close = dialog.findViewById(R.id.btn_close);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                Navigation.findNavController(view).navigate(R.id.action_reviewAllData_to_viewMyLocations);
            }
        });
    }
}

//                    new Handler().postDelayed(() -> {
//
//                        progressDialog.cancel();
//
////                        Navigation.findNavController(view).navigate(R.id.action_addLocationFragment_to_viewMyLocationsFragment);
//                        Toast.makeText(getContext(), "Register Succesfull", Toast.LENGTH_SHORT).show();
//
//                    }, 500);