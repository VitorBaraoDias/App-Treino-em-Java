package com.example.treino_casa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class ActivityProfile extends AppCompatActivity {
    public Dado dado = new Dado(); //declara classe de dados do utilizador
    ImageView imgGenero,imgAlterar,imgVoltar,imgDelete;
    EditText txtnome,txtidade;
    Spinner spinPeso,spinAltura;
    Button btnalterar;
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        estanciarObjeto(); //   //estancia objetos

        Bundle extras = getIntent().getExtras();
        dado.setId(extras.getInt("id"));

        //DAO
        PersonalTrainerDao personalTrainerDao = new PersonalTrainerDao(this);
        DadosDao dadosDao = new DadosDao(this); //declara classe dao da base de de dados sobre caracteristicas do utilizador
        UserDao userDao = new UserDao(this);
        //DAO
        setImgGenero(dadosDao,dado);    //faz set da imagem sobre o genero
        setboxs(dado,dadosDao);         //set das box
        setenabled();

        imgAlterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnalterar.setVisibility(View.VISIBLE);
                setenabledTrue();
            }
        });
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnalterar.setVisibility(View.INVISIBLE);
                setenabled();
            }
        });
        btnalterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtnome.getText().toString().equals("")){
                    txtnome.setError("Preencha a caixa!");
                }
                else if(txtidade.getText().toString().equals("")){
                    txtidade.setError("Preencha a caixa!");
                }
                else{
                     setclasse(dadosDao);
                    setenabled();
                }
            }
        });
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.homeId){
                    Intent intent = new Intent(ActivityProfile.this,ActivityMenu .class);
                    intent.putExtra("id",dado.getId());
                    startActivity(intent);
                    finish();
                }
                else if(item.getItemId()==R.id.recylerTrainerId){
                    Intent intent = new Intent(ActivityProfile.this,FormListaPersonalTrainer.class);
                    intent.putExtra("id",dado.getId());
                    startActivity(intent);
                    finish();
                }
                else if(item.getItemId()==R.id.equipaId){
                    if(personalTrainerDao.validarConta(dado,false)==false){
                        Intent intent = new Intent(ActivityProfile.this,FormTrainer.class);
                        intent.putExtra("id",dado.getId());
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Você já faz parte da equipa", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(ActivityProfile.this)
                        .setTitle("Excluir")
                        .setMessage("Deseja excluir o registro?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                //excluir o registro
                                if(personalTrainerDao.validarConta(dado,false)==false){
                                    dadosDao.eliminaTarefa(dado);
                                    userDao.eliminaTarefa(dado);
                                    Toast.makeText(getApplicationContext(), "Conta eliminada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityProfile.this,MainActivity.class);
                                    startActivity(intent);
                                }
                                else{
                                    personalTrainerDao.eliminaTarefa(dado);
                                    dadosDao.eliminaTarefa(dado);
                                    userDao.eliminaTarefa(dado);
                                    Toast.makeText(getApplicationContext(), "Conta eliminada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityProfile.this,MainActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //não exclui, apenas fecha a mensagem
                                dialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }
    private void estanciarObjeto(){
        imgGenero = findViewById(R.id.imgGenero);
        imgAlterar = findViewById(R.id.imgAlterar);
        imgVoltar = findViewById(R.id.imgVoltarProfile);
        imgDelete = findViewById(R.id.imgDelete);
        txtnome = findViewById(R.id.txtNome);
        txtidade = findViewById(R.id.txtIdade);
        spinPeso = findViewById(R.id.spinPeso);
        spinAltura = findViewById(R.id.spin_Altura);
        btnalterar=findViewById(R.id.btnalterar);
        navigationView=findViewById(R.id.navBar);

        List<String> peso = new ArrayList<String>();

        for(int i=35;i<=150;i++){{
            peso.add(i+" Kg");

        }}
        List<String> alt = new ArrayList<String>();
        for(int i=100;i<=250;i++){{
            alt.add(i+" cm");

        }}
        //ADPTER PESO
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (java.util.List<String>) peso);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinPeso.setAdapter(dataAdapter);
        //ADPTER ALTURA
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, (java.util.List<String>) alt);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinAltura.setAdapter(dataAdapter2);
    }
    private void setImgGenero(DadosDao dadosDao,Dado dado){
        String genero=dadosDao.verificarGenero(dado,"");
        if(genero.equals("Masculino")){
           imgGenero.setImageDrawable(getResources().getDrawable(R.drawable.profilemen));
         }
       else{
           imgGenero.setImageDrawable(getResources().getDrawable(R.drawable.profilewomen));
        }
    }
    private void setboxs(Dado dado,DadosDao dadosDao)
    {
        dado=dadosDao.getCaracteristicasUser(dado);
        String nome=dado.getNome();
        String idade= String.valueOf(dado.getIdade());
        int peso=0;
        int altura=0;
        txtnome.setHint(nome);
        txtidade.setHint(idade);
        for(int i=35;i<=dado.getPeso();i++){
            peso=i;
        }
        peso=peso-35;
        for(int i=100;i<=dado.getAltura();i++){
            altura=i;
        }
        altura=altura-100;
        spinPeso.setSelection(peso);
        spinAltura.setSelection(altura);
    }
    private void setenabled(){
        txtnome.setInputType(InputType.TYPE_NULL);
        txtidade.setInputType(InputType.TYPE_NULL);
        spinPeso.setEnabled(false);
        spinAltura.setEnabled(false);
        btnalterar.setVisibility(View.INVISIBLE);
    }
    private void setenabledTrue(){
        txtnome.setInputType(InputType.TYPE_CLASS_TEXT);
        txtidade.setInputType(InputType.TYPE_CLASS_NUMBER);
        spinPeso.setEnabled(true);
        spinAltura.setEnabled(true);
    }
    private void setclasse(DadosDao dadosDao){
        String tamanhoPeso=String.valueOf(spinPeso.getSelectedItem().toString());
        String tamanhoAltura=String.valueOf(spinAltura.getSelectedItem().toString());

        if(tamanhoPeso.length()==5){
            tamanhoPeso = tamanhoPeso.substring(0, tamanhoPeso.length()-3);
        }
        else{
            tamanhoPeso = tamanhoPeso.substring(0, tamanhoPeso.length()-4);
        }
        tamanhoAltura = tamanhoAltura.substring(0, tamanhoAltura.length()-3);
        dado.setNome(txtnome.getText().toString());
        dado.setIdade(Integer.parseInt(txtidade.getText().toString()));
        dado.setPeso(Integer.parseInt(tamanhoPeso));
        dado.setAltura(Integer.parseInt(tamanhoAltura));
        dadosDao.alterarPerfil(dado);
        Toast.makeText(getApplicationContext(),"Utilizador atualizado", Toast.LENGTH_SHORT).show();
    }
}