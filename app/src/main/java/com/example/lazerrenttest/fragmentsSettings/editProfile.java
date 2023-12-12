package com.example.lazerrenttest.fragmentsSettings;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.lazerrenttest.Database.UsuarioManager;
import com.example.lazerrenttest.Updates.UpdateUserData;
import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.databinding.ActySettingsEditprofileBinding;
import com.example.lazerrenttest.model.UpdateUserDataModel;
import com.example.lazerrenttest.model.UserModel;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class editProfile extends AppCompatActivity {

    private final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if(result.getResultCode() == RESULT_OK){
                if(result.getData() != null){
                    mImageUri = result.getData().getData();
                    Glide.with(editProfile.this).load(mImageUri).into(binding.updateProfileimage);
                }
            }else {
                Toast.makeText(editProfile.this, "please select an image", Toast.LENGTH_SHORT).show();
            }
        }
    });
    private ActySettingsEditprofileBinding binding;

    private String fullname, cpf, phone, date;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Uri mImageUri;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActySettingsEditprofileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        progressDialog = new ProgressDialog(this);

        CreateMask();

        recoveryData();

        binding.updateProfilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openFileChooser();
            }
        });

        binding.btnUpdateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.setTitle("Updating...");
                progressDialog.show();
                updateProfileData();
            }
        });

        binding.closebutao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void CreateMask() {

        SimpleMaskFormatter smf = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher mtw = new MaskTextWatcher(binding.edittextUpdateNumber, smf);
        binding.edittextUpdateNumber.addTextChangedListener(mtw);

        SimpleMaskFormatter smf4 = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher mtw4 = new MaskTextWatcher(binding.edittextUpdateCpf, smf4);
        binding.edittextUpdateCpf.addTextChangedListener(mtw4);

        SimpleMaskFormatter smf3 = new SimpleMaskFormatter("NN/NN/NN");
        MaskTextWatcher mtw3 = new MaskTextWatcher(binding.edittextUpdateDate, smf3);
        binding.edittextUpdateDate.addTextChangedListener(mtw3);
    }

    private void recoveryData() {

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        UsuarioManager usuarioManager = new UsuarioManager();
        Task<DocumentSnapshot> task = usuarioManager.getUsuario(usuarioID);

        task.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        UserModel user = document.toObject(UserModel.class);

                        fullname = user.getFullname();
                        phone = user.getPhone();
                        cpf = user.getCpf();
                        date = user.getDate();

                        binding.edittextUpdateName.setText(fullname);
                        binding.edittextUpdateNumber.setText(phone);
                        binding.edittextUpdateCpf.setText(cpf);
                        binding.edittextUpdateDate.setText(date);
                    }
                }
            }
        });

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String caminhoImagem = "images/profile/" + usuarioID + "/profilephoto";
        StorageReference imagemRef = storageRef.child(caminhoImagem);

        imagemRef.getDownloadUrl().addOnSuccessListener(uri -> {
            mImageUri = uri;
            Picasso.get().load(uri.toString()).into(binding.updateProfileimage);
        }).addOnFailureListener(exception -> {
            Toast.makeText(this, "error to get the uri photo", Toast.LENGTH_SHORT).show();
        });
    }

    private void updateProfileData() {

        String usuarioID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        fullname = binding.edittextUpdateName.getText().toString().trim();
        cpf = binding.edittextUpdateCpf.getText().toString();
        phone = binding.edittextUpdateNumber.getText().toString();
        date = binding.edittextUpdateDate.getText().toString();

        if(!fullname.trim().isEmpty() && !cpf.trim().isEmpty() && !phone.trim().isEmpty() && !date.trim().isEmpty()){

            UpdateUserDataModel updateUserDataModel = new UpdateUserDataModel(fullname, cpf, date, phone);

            UpdateUserData updateUserData = new UpdateUserData(this, usuarioID, updateUserDataModel);
            progressDialog.cancel();
            startActivity(new Intent(editProfile.this, MainActivity.class));

        }else{
            Toast.makeText(this, "Complete all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
}