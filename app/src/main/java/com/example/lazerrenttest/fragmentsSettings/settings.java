package com.example.lazerrenttest.fragmentsSettings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.activities.MainActivity;
import com.example.lazerrenttest.databinding.ActySettingsSettingsBinding;

import java.util.Locale;

public class settings extends AppCompatActivity {

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_LANGUAGE = "language";

    private ActySettingsSettingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActySettingsSettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.languages, com.facebook.R.layout.support_simple_spinner_dropdown_item);
        binding.sppinnerlinguagens.setAdapter(arrayAdapter);

        binding.closebutao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Defina a lógica para o clique no botão btnOk
        binding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedLanguageIndex = binding.sppinnerlinguagens.getSelectedItemPosition();
                if (selectedLanguageIndex == 0) {
                    Toast.makeText(settings.this, "Please select a language!!", Toast.LENGTH_SHORT).show();
                }
                else if (selectedLanguageIndex == 1) {
                    SelectLanguage("pt");
                }
                else if (selectedLanguageIndex == 2) {
                    SelectLanguage("en");
                }
                else if (selectedLanguageIndex == 3) {
                    SelectLanguage("es");
                }
                startActivity(new Intent(settings.this, MainActivity.class));
            }
        });

        // Verifique se o idioma já foi definido
        if (!isLanguageSet()) {
            // Carregar a preferência salva
            SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            String savedLanguage = prefs.getString(PREF_LANGUAGE, "");
            if (!savedLanguage.isEmpty()) {
                SelectLanguage(savedLanguage);
            }
        }
    }
    private void SelectLanguage(String language) {
        // Salvar a preferência
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(PREF_LANGUAGE, language);
        editor.apply();

        Locale localidadde = new Locale(language);
        Locale.setDefault(localidadde);

        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = localidadde;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    // Método para verificar se o idioma já foi definido
    private boolean isLanguageSet() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String savedLanguage = prefs.getString(PREF_LANGUAGE, "");
        return !savedLanguage.isEmpty();
    }
}