package com.example.treino_casa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class Caracteristicas extends AppCompatActivity {
    public Dado dado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caracteristicas);

        Button btnAvancar;
        EditText txtNome,txtIdade;

        btnAvancar=findViewById(R.id.btnJanela2);
        txtNome=findViewById(R.id.txtNome2);
        txtIdade=findViewById(R.id.txtIdade);
        Spinner spinnerPeso = (Spinner) findViewById(R.id.spinPeso);
        Spinner spinnerAltura = (Spinner) findViewById(R.id.spin_Altura);
        Bundle extras = getIntent().getExtras();
        DadosDao dadosDao = new DadosDao(this);

        List<String> peso = new ArrayList<String>();
        List<String> alt = new ArrayList<String>();
        for(int i=35;i<=150;i++){{
            peso.add(i+" Kg");

        }}

        for(int i=100;i<=250;i++){{
            alt.add(i+" cm");

        }}
        //ADPTER PESO
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (java.util.List<String>) peso);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPeso.setAdapter(dataAdapter);

        //ADPTER ALTURA
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (java.util.List<String>) alt);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAltura.setAdapter(dataAdapter2);

        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(txtNome.getText().toString().equals("")){
                    txtNome.setError("Preencha a caixa!");

                }
                else if(txtIdade.getText().toString().equals("")){

                    txtIdade.setError("Preencha a caixa!");
                }
                else{
                    String tamanhoPeso=String.valueOf(spinnerPeso.getSelectedItem().toString());
                    String tamanhoAltura=String.valueOf(spinnerAltura.getSelectedItem().toString());
                    Toast.makeText(getApplicationContext(), String.valueOf(tamanhoPeso.length()), Toast.LENGTH_SHORT).show();
                    if(tamanhoPeso.length()==5){
                        tamanhoPeso = tamanhoPeso.substring(0, tamanhoPeso.length()-3);
                    }
                    else{
                        tamanhoPeso = tamanhoPeso.substring(0, tamanhoPeso.length()-4);
                    }
                    tamanhoAltura = tamanhoAltura.substring(0, tamanhoAltura.length()-3);
                    dado = new Dado();
                    dado.setNome(txtNome.getText().toString());
                    dado.setIdade(Integer.parseInt(txtIdade.getText().toString()));
                    dado.setPeso(Integer.parseInt(tamanhoPeso));
                    dado.setAltura(Integer.parseInt(tamanhoAltura));
                    dado.setId(extras.getInt("id"));
                    dado.setGenero(extras.getString("genero"));
                    dadosDao.inserirdados(dado);
                    Intent intent = new Intent(Caracteristicas.this,ActivityMenu.class);
                    intent.putExtra("id",dado.getId());
                    startActivity(intent);
                }
            }
        });
    }

}