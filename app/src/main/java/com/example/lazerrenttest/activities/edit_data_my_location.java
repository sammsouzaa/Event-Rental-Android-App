package com.example.lazerrenttest.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.lazerrenttest.R;
import com.example.lazerrenttest.Updates.UpdateImovelData;
import com.example.lazerrenttest.databinding.ActivityEditDataMyLocationBinding;
import com.example.lazerrenttest.model.ImovelModel;
import com.example.lazerrenttest.model.Inf_getImovelModel;

public class edit_data_my_location extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    //variaveis locais
    private ActivityEditDataMyLocationBinding binding;
    private Inf_getImovelModel inf_getImovelModel;
    private Uri imagemPrincipal, imagem1, imagem2, imagem3, imagem4;
    private int cont = 0;

    private String typeproperty,title, description, size, price, capacity, beds, baths, street, number, neighborhood, city, idImovel, usuarioID, nFavoritos, nLocations;

    private String typeAntigo;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditDataMyLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //inicializando um dialog de processo
        dialog = new ProgressDialog(this);

        dialog.setMessage("Loading...");
        dialog.setCancelable(false);

        //carregando o spinner
        setSpinner();

        //logd utilizado apenas para debug;
        Log.d("editdata", "onCreate: Bundle received");
        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            Log.d("editdata", "Bundle is not null");
            inf_getImovelModel = (Inf_getImovelModel) bundle.getSerializable("objeto");
            if (inf_getImovelModel != null) {
                Log.d("editdata", "inf_getImovelModel is not null");
                // Proceed with processing inf_getImovelModel
                loadAllData();
                setOnClickListeners();
            } else {
                Log.e("editdata", "inf_getImovelModel is null");
            }
        } else {
            Log.e("editdata", "Bundle is null");
        }
    }

    private void setOnClickListeners() {

        binding.editdataBtnCloseAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button close
                startActivity(new Intent(edit_data_my_location.this, MainActivity.class));
            }
        });

        binding.editdataBtnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //button continuar;
                getEditedData();
            }
        });
    }

    //recebendo os dados e atribuindo-os a variaveis locais;
    private void getEditedData() {

        typeproperty = binding.editdataTypeofproperty.getSelectedItem().toString();
        title = binding.editdataPropertytitle.getText().toString();
        description = binding.editdataPropertydescription.getText().toString();
        size = binding.editdataPropertySize.getText().toString();
        price = binding.editdataPrice.getText().toString();
        capacity = binding.editdataCapacity.getText().toString();
        beds = binding.editdataBeds.getText().toString();
        baths = binding.editdataBaths.getText().toString();
        street = binding.editdataStreet.getText().toString();
        number = binding.editdataNumber.getText().toString();
        neighborhood = binding.editdataNeighborhood.getText().toString();
        city = binding.editdataCity.getText().toString();

        idImovel = inf_getImovelModel.getID_imoveis();
        usuarioID = inf_getImovelModel.getIdProprietario();
        nFavoritos = inf_getImovelModel.getnFavoritos();
        nLocations = inf_getImovelModel.getnLocations();

        dialog.show();

        ImovelModel imovelModel = new ImovelModel("pendente", typeproperty,title, description, size, price, capacity, beds, baths,
                street, number, neighborhood, city, idImovel, usuarioID, nFavoritos, nLocations);

        UpdateImovelData updateImovelData = new UpdateImovelData(edit_data_my_location.this, imovelModel);

        dialog.dismiss();
    }

    //recebendo e atribuindo as informações recebidas aos elementos da interface
    private void loadAllData() {

        binding.editdataPropertytitle.setText(inf_getImovelModel.getTitle());
        binding.editdataPropertydescription.setText(inf_getImovelModel.getDescription());
        binding.editdataPropertySize.setText(inf_getImovelModel.getPropertysize());
        binding.editdataPrice.setText(inf_getImovelModel.getPrice());
        binding.editdataCapacity.setText(inf_getImovelModel.getCapacity());
        binding.editdataBeds.setText(inf_getImovelModel.getBeds());
        binding.editdataBaths.setText(inf_getImovelModel.getBaths());
        binding.editdataStreet.setText(inf_getImovelModel.getStreet());
        binding.editdataNumber.setText(inf_getImovelModel.getStreetnumber());
        binding.editdataNeighborhood.setText(inf_getImovelModel.getNeighborhood());
        binding.editdataCity.setText(inf_getImovelModel.getCity());

        typeAntigo = inf_getImovelModel.getTypesofproperties();

        //spinner
        if(typeAntigo.equals("Farm")){
            binding.editdataTypeofproperty.setSelection(4);
        }
        else if(typeAntigo.equals("House")){
            binding.editdataTypeofproperty.setSelection(0);
        }
        else if(typeAntigo.equals("House w/ pool")){
            binding.editdataTypeofproperty.setSelection(1);
        }
        else if(typeAntigo.equals("Mansion")){
            binding.editdataTypeofproperty.setSelection(2);
        }
        else if(typeAntigo.equals("Party Room")){
            binding.editdataTypeofproperty.setSelection(3);
        }
    }

    //setando spinner
    private void setSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types_of_properties, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.editdataTypeofproperty.setAdapter(adapter);
        binding.editdataTypeofproperty.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    //ao apertar voltar;
    @Override
    public void onBackPressed() {

        startActivity(new Intent(edit_data_my_location.this, DetailsActivity.class));

        Intent intent = new Intent(edit_data_my_location.this, DetailsActivity.class);

        Bundle bundle = new Bundle();

        bundle.putString("ID_do_imovel", inf_getImovelModel.getID_imoveis());
        bundle.putString("telaAntiga", "recentes");

        intent.putExtras(bundle);
        startActivity(intent);

    }
}