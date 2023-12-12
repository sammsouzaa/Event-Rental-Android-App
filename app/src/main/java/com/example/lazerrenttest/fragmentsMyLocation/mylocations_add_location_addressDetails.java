package com.example.lazerrenttest.fragmentsMyLocation;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.FragmentMylocationsAddLocationAddressDetailsBinding;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;

public class mylocations_add_location_addressDetails extends Fragment {

    public mylocations_add_location_addressDetails() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Context context;

    private FragmentMylocationsAddLocationAddressDetailsBinding binding;

    private SharedViewModel sharedViewModel;

    private String street, number, neighborhood, city;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMylocationsAddLocationAddressDetailsBinding.inflate(inflater, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDataIfNotNull();

        setBackButton(view);

        context = getContext();
        Dialog dialog = new Dialog(context);

        showDialog(dialog, view);

        onClickListeners(dialog, view);

    }

    private void setBackButton(View view) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        Navigation.findNavController(view).navigate(R.id.action_addressDetails_to_basicdetails3);

                    }else {
                        Navigation.findNavController(view).navigate(R.id.action_addressDetails_to_reviewAllData);
                    }
                    return true;
                }

                return false;
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
                Navigation.findNavController(view).navigate(R.id.action_addressDetails_to_basicdetails3);

            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                street = binding.propertystreet.getText().toString();
                number = binding.propertynumber.getText().toString();
                neighborhood = binding.propertyneighborhood.getText().toString();
                city = binding.propertycity.getText().toString();

                if(!street.trim().isEmpty() || !number.trim().isEmpty() || !neighborhood.trim().isEmpty() || !city.trim().isEmpty()){

                    sharedViewModel.setStreet(street);
                    sharedViewModel.setNumber(number);
                    sharedViewModel.setNeighborhood(neighborhood);
                    sharedViewModel.setCity(city);

                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        Navigation.findNavController(view).navigate(R.id.action_addressDetails_to_reviewAllData);
                    }
                    else {
                        Navigation.findNavController(view).navigate(R.id.action_addressDetails_to_reviewAllData);
                    }
                }else if (street.trim().isEmpty()){
                    binding.propertystreet.setError("");
                    //Toast.makeText(context, "please, complete all data", Toast.LENGTH_SHORT).show();
                }
                else if (number.trim().isEmpty()){
                    binding.propertynumber.setError("");
                }
                else if (neighborhood.trim().isEmpty()){
                    binding.propertyneighborhood.setError("");
                }
                else if (city.trim().isEmpty()){
                    binding.propertycity.setError("");
                }

            }
        });

    }

    private void loadData() {
        binding.propertystreet.setText(street);
        binding.propertynumber.setText(number);
        binding.propertyneighborhood.setText(neighborhood);
        binding.propertycity.setText(city);
    }

    private void setDataIfNotNull() {

        street = sharedViewModel.getStreet().getValue();
        number = sharedViewModel.getNumber().getValue();
        neighborhood = sharedViewModel.getNeighborhood().getValue();
        city = sharedViewModel.getCity().getValue();

        if(street != null && !street.isEmpty() || number != null && !number.isEmpty() ||
                neighborhood != null &&  !neighborhood.isEmpty() || city != null &&  !city.isEmpty()){loadData();}
        else{return;}
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
                Navigation.findNavController(view).navigate(R.id.action_addressDetails_to_viewMyLocations);

            }
        });
    }

}