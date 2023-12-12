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

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.FragmentMylocationsAddLocationBasicdetailsBinding;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;

public class mylocations_add_location_basicdetails extends Fragment {

    public mylocations_add_location_basicdetails() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Context context;


    FragmentMylocationsAddLocationBasicdetailsBinding binding;

    SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMylocationsAddLocationBasicdetailsBinding.inflate(inflater, container, false);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_photos);

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_reviewAllData);
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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_photos);
            }
        });

        binding.typeFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedViewModel.setTypeproperty("Farm");
                String review = sharedViewModel.getReview().getValue();

                if (review == null) {

                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_basicdetails1);

                } else {
                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_reviewAllData);
                }
            }
        });
        binding.typeHouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedViewModel.setTypeproperty("House");
                String review = sharedViewModel.getReview().getValue();

                if (review == null) {

                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_basicdetails1);

                } else {
                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_reviewAllData);
                }
            }
        });
        binding.typeHousewpool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedViewModel.setTypeproperty("House w/ pool");
                String review = sharedViewModel.getReview().getValue();

                if (review == null) {

                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_basicdetails1);

                } else {
                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_reviewAllData);
                }
            }
        });
        binding.typeMansion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedViewModel.setTypeproperty("Mansion");
                String review = sharedViewModel.getReview().getValue();

                if (review == null) {

                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_basicdetails1);

                } else {
                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_reviewAllData);
                }
            }
        });

        binding.typePartyroom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedViewModel.setTypeproperty("Party Room");
                String review = sharedViewModel.getReview().getValue();

                if (review == null) {
                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_basicdetails1);
                } else {
                    Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_reviewAllData);
                }
            }
        });
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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails_to_viewMyLocations);

            }
        });
    }
}