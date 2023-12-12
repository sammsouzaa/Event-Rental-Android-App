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
import com.example.lazerrenttest.databinding.FragmentMylocationsAddLocationBasicdetails2Binding;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;

public class mylocations_add_location_basicdetails2 extends Fragment {

    public mylocations_add_location_basicdetails2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Context context;

    FragmentMylocationsAddLocationBasicdetails2Binding binding;

    SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMylocationsAddLocationBasicdetails2Binding.inflate(inflater, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return binding.getRoot();

    }
    String description;

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

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails2_to_basicdetails1);

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_basicdetails2_to_reviewAllData);
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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails2_to_basicdetails1);
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                description = binding.propertydescription.getText().toString();

                if(!description.trim().isEmpty()){
                    sharedViewModel.setDescription(description);
                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails2_to_basicdetails3);

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_basicdetails2_to_reviewAllData);
                    }
                }else{
                    binding.propertydescription.setError("please, complete the description");
                }
            }
        });

        binding.propertydescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.idcontador2.setText(editable.length() + " de 350");

            }
        });
    }

    private void loadDescription() {
        binding.propertydescription.setText(description);
    }

    private void setDataIfNotNull() {

        description = sharedViewModel.getDescription().getValue();

        if(description != null && !description.isEmpty()){loadDescription();}

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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails2_to_viewMyLocations);

            }
        });
    }
}