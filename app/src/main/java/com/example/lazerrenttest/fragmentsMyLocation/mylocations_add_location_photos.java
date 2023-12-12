package com.example.lazerrenttest.fragmentsMyLocation;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.ClipData;
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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.databinding.FragmentMylocationsAddLocationPhotosBinding;
import com.example.lazerrenttest.sharedviewmodel.SharedViewModel;
import com.squareup.picasso.Picasso;

public class mylocations_add_location_photos extends Fragment {

    public mylocations_add_location_photos() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Context context;
    Uri imagemPrincipal, imagem1, imagem2, imagem3, imagem4;
    int cont = 0;

    FragmentMylocationsAddLocationPhotosBinding binding;
    SharedViewModel sharedViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMylocationsAddLocationPhotosBinding.inflate(inflater, container, false);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
        return binding.getRoot();

    }

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        if (result.getData() != null) {
                            ClipData clipData = result.getData().getClipData();
                            if (clipData != null) {
                                int itemCount = clipData.getItemCount();
                                int maxImages = Math.min(itemCount, 5); // Limitar a 5 imagens

                                if (itemCount > 5) {
                                    Toast.makeText(context, "please select up to 5 images", Toast.LENGTH_SHORT).show();
                                    return; // Não processa mais imagens se exceder o limite
                                }else if (itemCount < 5) {
                                    Toast.makeText(context, "please select up to 5 images", Toast.LENGTH_SHORT).show();
                                    return; // Não processa mais imagens se exceder o limite
                                }

                                for (int i = 0; i < maxImages; i++) {
                                    Uri imageUri = clipData.getItemAt(i).getUri();

                                    if (i == 0) {
                                        imagemPrincipal = imageUri;
                                    } else if (i == 1) {
                                        imagem1 = imageUri;
                                    } else if (i == 2) {
                                        imagem2 = imageUri;
                                    } else if (i == 3) {
                                        imagem3 = imageUri;
                                    } else if (i == 4) {
                                        imagem4 = imageUri;
                                    }
                                    loadImages();
                                }
                            } else {

                                if (cont == 10){
                                    imagemPrincipal = result.getData().getData();
                                    Glide.with(getContext()).load(imagemPrincipal).into(binding.photoPrincipal);}

                                if (cont == 1){
                                    imagem1 = result.getData().getData();
                                    Glide.with(getContext()).load(imagem1).into(binding.photo1);}

                                if (cont == 2){
                                    imagem2 = result.getData().getData();
                                    Glide.with(getContext()).load(imagem2).into(binding.photo2);}

                                if (cont == 3){
                                    imagem3 = result.getData().getData();
                                    Glide.with(getContext()).load(imagem3).into(binding.photo3);}

                                if (cont == 4){
                                    imagem4 = result.getData().getData();
                                    Glide.with(getContext()).load(imagem4).into(binding.photo4);}

                                // Aqui você pode processar a única imagem selecionada
                            }
                        }
                    } else {
                        Toast.makeText(context, "please select an image", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void loadImages() {
        Glide.with(getContext()).load(imagemPrincipal).into(binding.photoPrincipal);
        Glide.with(getContext()).load(imagem1).into(binding.photo1);
        Glide.with(getContext()).load(imagem2).into(binding.photo2);
        Glide.with(getContext()).load(imagem3).into(binding.photo3);
        Glide.with(getContext()).load(imagem4).into(binding.photo4);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setDataIfNotNull();

        context = getContext();
        Dialog dialog = new Dialog(context);

        showDialog(dialog, view);
        backButton(dialog, view);

        onClickListeners(dialog, view);

    }

    private void setDataIfNotNull() {

            imagemPrincipal = sharedViewModel.getPhotoprincipal().getValue();
            imagem1 = sharedViewModel.getPhoto1().getValue();
            imagem2 = sharedViewModel.getPhoto2().getValue();
            imagem3 = sharedViewModel.getPhoto3().getValue();
            imagem4 = sharedViewModel.getPhoto4().getValue();

            if(imagemPrincipal != null && imagem1 != null && imagem2 != null && imagem3 != null && imagem4 != null){loadImages();}

            else{return;}

    }

    private void onClickListeners(Dialog dialog, View view) {

        binding.btnCloseToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();
            }
        });

        binding.btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(imagemPrincipal != null && imagem1 != null && imagem2 != null && imagem3 != null && imagem4 != null){

                    Log.d("TESTE", "nenhuma variavel vazia");

                    sharedViewModel.setPhotoprincipal(imagemPrincipal);
                    Log.d("TESTE", "setPhotoprincipal successfully");
                    sharedViewModel.setPhoto1(imagem1);
                    Log.d("TESTE", "setPhoto1 successfully");
                    sharedViewModel.setPhoto2(imagem2);
                    Log.d("TESTE", "setPhoto2 successfully");
                    sharedViewModel.setPhoto3(imagem3);
                    Log.d("TESTE", "setPhoto3 successfully");
                    sharedViewModel.setPhoto4(imagem4);
                    Log.d("TESTE", "setPhoto4 successfully");

                    Log.d("TESTE", "após setar todas as imagens");
                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        Navigation.findNavController(view).navigate(R.id.action_photos_to_basicdetails);

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_photos_to_reviewAllData);
                    }
                }else{
                    Toast.makeText(context, "please select all images", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.photoPrincipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont = 10;
                getIntentAddPhoto();
            }
        });
        binding.photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont = 1;
                getIntentAddPhoto();
            }
        });
        binding.photo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont = 2;
                getIntentAddPhoto();
            }
        });
        binding.photo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont = 3;
                getIntentAddPhoto();
            }
        });
        binding.photo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cont = 4;
                getIntentAddPhoto();
            }
        });
    }

    private void getIntentAddPhoto() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        activityResultLauncher.launch(intent);
    }

    private void backButton(Dialog dialog, View view) {

        view.setFocusableInTouchMode(true);
        view.requestFocus();

        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {

                    String review = sharedViewModel.getReview().getValue();

                    if (review == null) {

                        dialog.show();

                    } else {
                        Navigation.findNavController(view).navigate(R.id.action_photos_to_reviewAllData);
                    }

                    return true;
                }

                return false;
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
                Navigation.findNavController(view).navigate(R.id.action_photos_to_viewMyLocations);

            }
        });
    }

}