package com.example.lazerrenttest.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.customDialog.Dialog_closeapp;
import com.example.lazerrenttest.databinding.ActyMainBinding;
import com.example.lazerrenttest.fragmentsMenu.MapsFragment;
import com.example.lazerrenttest.fragmentsMenu.SettingsFragment;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    //declarando variaveis;
    private boolean DoublePressToExit;
    private Toast toast;
    private ActyMainBinding binding;

    private FirebaseFirestore mFire = FirebaseFirestore.getInstance();

    private NavHostFragment navHostFragment;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActyMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //metodo de navegação
        initNavigation();

    }
        private void initNavigation(){
            navHostFragment
                    = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHostFragment);
            navController = navHostFragment.getNavController();

            NavigationUI.setupWithNavController(binding.buttonNavBar, navController);
        }

    @Override
    public void onBackPressed() {

        if(DoublePressToExit){

            Dialog_closeapp dialog_closeapp = new Dialog_closeapp(MainActivity.this, MainActivity.this);
            dialog_closeapp.setCancelable(false);
            dialog_closeapp.show();

//            AlertDialog.Builder alertDialogClose = new AlertDialog.Builder(MainActivity.this);
//            alertDialogClose.setTitle("Exit App");
//            alertDialogClose.setMessage("Would you close app?");
//            alertDialogClose.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    finishAffinity();
//                }
//            });
//            alertDialogClose.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    dialog.dismiss();
//                }
//            });
//            alertDialogClose.show();
//            toast.cancel();
        }
        else {
            DoublePressToExit = true;

            toast = Toast.makeText(this, "Click Again", Toast.LENGTH_SHORT);
            toast.show();

            android.os.Handler handler = new android.os.Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    DoublePressToExit = false;
                }
            }, 1500);

        }
    }

}
