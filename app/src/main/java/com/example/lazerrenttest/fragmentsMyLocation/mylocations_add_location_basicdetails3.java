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

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.FragmentMylocationsAddLocationBasicdetails3Binding;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;

public class mylocations_add_location_basicdetails3 extends Fragment {

    public mylocations_add_location_basicdetails3() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private Context context;

    private FragmentMylocationsAddLocationBasicdetails3Binding binding;

    private SharedViewModel sharedViewModel;

    private String size, price, capacity, beds, baths;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMylocationsAddLocationBasicdetails3Binding.inflate(inflater, container, false);

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

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails3_to_basicdetails2);

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_basicdetails3_to_reviewAllData);
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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails3_to_basicdetails2);

            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                size = binding.propertySize.getText().toString();
                price = binding.propertyPrice.getText().toString();
                capacity = binding.propertyCapacity.getText().toString();
                beds = binding.propertyBeds.getText().toString();
                baths = binding.propertyBaths.getText().toString();

                if(!size.trim().isEmpty() || !price.trim().isEmpty() || !capacity.trim().isEmpty() || !beds.trim().isEmpty() || !baths.trim().isEmpty() ){

                    sharedViewModel.setPropertysize(size);
                    sharedViewModel.setPrice(price);
                    sharedViewModel.setCapacity(capacity);
                    sharedViewModel.setBeds(beds);
                    sharedViewModel.setBaths(baths);

                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails3_to_addressDetails);

                    } else {

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails3_to_reviewAllData);
                    }
                }
                else if (size.trim().isEmpty()){
                    binding.propertySize.setError("");
                    //Toast.makeText(context, "please, complete all data", Toast.LENGTH_SHORT).show();
                }
                else if (price.trim().isEmpty()){
                    binding.propertyPrice.setError("");
                }
                else if (capacity.trim().isEmpty()){
                    binding.propertyCapacity.setError("");
                }
                else if (beds.trim().isEmpty()){
                    binding.propertyBeds.setError("");
                }
                else if (baths.trim().isEmpty()){
                    binding.propertyBaths.setError("");
                }
            }
        });

        binding.propertySize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.idcontador3.setText(editable.length() + " de 10");

            }
        });
    }

    private void loadData() {
        binding.propertySize.setText(size);
        binding.propertyPrice.setText(price);
        binding.propertyCapacity.setText(capacity);
        binding.propertyBeds.setText(beds);
        binding.propertyBaths.setText(baths);
    }

    private void setDataIfNotNull() {

        size = sharedViewModel.getPropertysize().getValue();
        price = sharedViewModel.getPrice().getValue();
        capacity = sharedViewModel.getCapacity().getValue();
        beds = sharedViewModel.getBeds().getValue();
        baths = sharedViewModel.getBaths().getValue();

        if(size != null && !size.isEmpty() || price != null && !price.isEmpty() || capacity != null &&  !capacity.isEmpty() ||
                beds != null && !beds.isEmpty() || baths != null && !baths.isEmpty() ){loadData();}

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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails3_to_viewMyLocations);

            }
        });
    }

}