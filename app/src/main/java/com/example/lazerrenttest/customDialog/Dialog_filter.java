package com.example.lazerrenttest.customDialog;

import static android.app.PendingIntent.getActivity;


import static androidx.core.app.ActivityCompat.finishAffinity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import com.example.lazerrenttest.R;

public class Dialog_filter extends Dialog {

    Activity activity;

    public Dialog_filter(@NonNull Context context, Activity activity) {
        super(context);

        this.activity = activity;
    }

    String filter = "title";
    Button btn_OK;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(getContext().getResources().getColor(android.R.color.transparent)));
        setContentView(R.layout.dialog_filter);

        Spinner spinnerFiltro = findViewById(R.id.spinnerFiltro);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.opcoes_filtro, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapter);

        btn_OK = findViewById(R.id.btn_pronto);

        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filter = spinnerFiltro.getSelectedItem().toString();

                EditText edit = activity.findViewById(R.id.search_filter);
                edit.setText("");
                dismiss();
            }
        });

    }

    public String getFilter(){
        return filter;
    }

}
