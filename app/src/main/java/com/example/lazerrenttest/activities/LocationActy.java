package com.example.lazerrenttest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lazerrenttest.Database.LocationsData;
import com.example.lazerrenttest.R;
import com.example.lazerrenttest.customDialog.CustomDatePickerDialog;
import com.example.lazerrenttest.databinding.ActivityLocationActyBinding;
import com.example.lazerrenttest.model.Inf_getImovelModel;
import com.example.lazerrenttest.model.LocationModel;
import com.example.lazerrenttest.registers.RegisterLocation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LocationActy extends AppCompatActivity {

    //variaveis locais
    private ActivityLocationActyBinding binding;
    private String IDimovel, IDdono;
    private int btn = 0;
    private List<Date> unavailableDates = new ArrayList<>();
    private Inf_getImovelModel inf_getImovelModel;
    private String dateEntry = "";
    private String dateExit = "";
    private Calendar calendarDateExit = null;
    private Calendar calendarDateEntry = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationActyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //logd apenas para debug
        Log.d("locationacty", "onCreate: Bundle received");

        //recebendo o bundle enviado pelo intent da tela anterior
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Log.d("locationacty", "Bundle is not null");
            inf_getImovelModel = (Inf_getImovelModel) bundle.getSerializable("objeto");
            if (inf_getImovelModel != null) {
                Log.d("locationacty", "inf_getImovelModel is not null");
                // Proceed with processing inf_getImovelModel
                IDimovel = inf_getImovelModel.getID_imoveis();
                IDdono = inf_getImovelModel.getIdProprietario();
            } else {
                Log.e("locationacty", "inf_getImovelModel is null");
            }
        } else {
            Log.e("locationacty", "Bundle is null");
        }

        LocationsData locationsData = new LocationsData(this, IDimovel, new LocationsData.OnDataFetchComplete() {
            @Override
            public void onDataFetchComplete(List<Date> datas) {
                unavailableDates = datas;
            }
        });

        binding.btnEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //variavel btn responsavel para verificar qual button foi clicado, o da entrada ou da saida, pois o mesmo metodo é chamado posteriormente;
                btn = 1;
                createDateDialog();
            }
        });
        binding.btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn = 2;
                createDateDialog();
            }
        });

        binding.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //se ele não selecionou as datas de entrada e saida, apresenta um toast notificando;
                if(dateEntry.equals("") || dateExit.equals("")){
                    Toast.makeText(LocationActy.this, "Please select all dates!!", Toast.LENGTH_SHORT).show();
                }else{
                    LocationModel locationModel = new LocationModel(calendarDateEntry, calendarDateExit);
                    RegisterLocation registerLocation = new RegisterLocation(locationModel, LocationActy.this, IDimovel, IDdono);
                }
            }
        });

        binding.btnBackprevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //button voltar;
                Intent intent=new Intent(LocationActy.this, DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("ID_do_imovel",IDimovel);
                bundle.putString("telaAntiga", "recentes");

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        binding.btnCloseToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //cancelar tudo;
                startActivity(new Intent(LocationActy.this, MainActivity.class));
            }
        });
    }

    //metodo para criar a dialog de calendario;
    private void createDateDialog() {

        Calendar currentDate = Calendar.getInstance();

        CustomDatePickerDialog datePickerDialog = new CustomDatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        if (btn == 1) {
                            handleEntryDateSelection(year, month, dayOfMonth);
                        } else {
                            handleExitDateSelection(year, month, dayOfMonth);
                        }
                    }
                },
                currentDate.get(Calendar.YEAR), // Define o ano mínimo como o ano atual
                currentDate.get(Calendar.MONTH), // Define o mês mínimo como o mês atual
                currentDate.get(Calendar.DAY_OF_MONTH), // Define o dia mínimo como o dia atual
                unavailableDates); // lista de datas desabilitadas

        datePickerDialog.show();
    }

    //verificando se a data de saida ja foi seleciona e logo depois, se a data de entrada é anterior a data de saida
    private void handleEntryDateSelection(int year, int month, int dayOfMonth) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);

        if (calendarDateExit != null && selectedDate.after(calendarDateExit)) {
            showToast("The entry date must be before the exit date");
        } else {
            calendarDateEntry = selectedDate;
            dateEntry = android.text.format.DateFormat.format("dd/MM/yyyy", selectedDate).toString();
            updateDateText(binding.textEntrydate, calendarDateEntry);
        }
    }

    //faz a mesma vericiação anterior com a data de saida;
    private void handleExitDateSelection(int year, int month, int dayOfMonth) {
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);

        if (calendarDateEntry != null && selectedDate.before(calendarDateEntry)) {
            showToast("The exit date must be after the entry date");
        } else {
            calendarDateExit = selectedDate;
            dateExit = android.text.format.DateFormat.format("dd/MM/yyyy", selectedDate).toString();
            updateDateText(binding.textExitdate, calendarDateExit);
        }
    }

    //metodo apenas para notificar um toast
    private void showToast(String message) {
        Toast.makeText(LocationActy.this, message, Toast.LENGTH_SHORT).show();
    }


    //atualiza o textview com a data selecionada;
    private void updateDateText(TextView textView, Calendar calendar) {
        String formattedDate = android.text.format.DateFormat.format("dd/MM/yyyy", calendar).toString();
        textView.setText(formattedDate);
    }
}