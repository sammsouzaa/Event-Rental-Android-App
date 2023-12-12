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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.FragmentMylocationsAddLocationBasicdetails1Binding;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;

public class mylocations_add_location_basicdetails1 extends Fragment {

    public mylocations_add_location_basicdetails1() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Context context;

    FragmentMylocationsAddLocationBasicdetails1Binding binding;

    SharedViewModel sharedViewModel;
    String title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMylocationsAddLocationBasicdetails1Binding.inflate(inflater, container, false);

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

        setOnClickListeners(dialog, view);

    }

    private void setOnClickListeners(Dialog dialog, View view) {

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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails1_to_basicdetails);

            }
        });

        binding.propertytitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                binding.idcontador1.setText(editable.length() + " de 25");

            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = binding.propertytitle.getText().toString().trim();

                if(!title.trim().isEmpty()){

                    sharedViewModel.setTitle(title);
                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails1_to_basicdetails2);

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_basicdetails1_to_reviewAllData);
                    }
                }else{
                    binding.propertytitle.setError("please, complete the title");
                }

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

                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        Navigation.findNavController(view).navigate(R.id.action_basicdetails1_to_basicdetails);

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_basicdetails1_to_reviewAllData);
                    }
                    return true;
                }

                return false;
            }
        });
    }

    private void setDataIfNotNull() {

        title = sharedViewModel.getTitle().getValue();

        if (title != null && !title.isEmpty()) {
            loadTitle();
        } else {
            return;
        }

    }

    private void loadTitle() {
        binding.propertytitle.setText(title);
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
                Navigation.findNavController(view).navigate(R.id.action_basicdetails1_to_viewMyLocations);

            }
        });
    }
}