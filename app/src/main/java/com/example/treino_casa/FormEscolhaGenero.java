package com.example.treino_casa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

public class FormEscolhaGenero extends AppCompatActivity {
public Dado dado;
public String genero="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_escolha_genero);
        ImageView mulher,homem;
        RadioButton radioMulher,radioHomem;
        mulher=findViewById(R.id.imgMulher);
        homem=findViewById(R.id.imgHomem);
        Button btnAvancar;
        radioMulher=findViewById(R.id.radioMulher);
        radioHomem=findViewById(R.id.radioHomem);
        btnAvancar=findViewById(R.id.btnjanela);
        Bundle extras = getIntent().getExtras();

        mulher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioMulher.setChecked(true);
                radioHomem.setChecked(false);
                genero="Feminino";
                dado = new Dado();
                dado.setGenero("Feminino");
            }
        });
        homem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioHomem.setChecked(true);
                radioMulher.setChecked(false);
                dado = new Dado();
                genero="Masculino";
                dado.setGenero("Masculino");
            }
        });
        btnAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioHomem.isChecked()==false && radioMulher.isChecked()==false){
                    Toast.makeText(getApplicationContext(), "Por favor escolha o seu generoo", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(FormEscolhaGenero.this,Caracteristicas.class);
                    int id = extras.getInt("id");
                    intent.putExtra("genero",genero);
                    intent.putExtra("id",id);
                    Toast.makeText(getApplicationContext(), dado.getGenero()+String.valueOf(id), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });
    }
}