package com.example.treino_casa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class FormListaPersonalTrainer extends AppCompatActivity {
    
    EditText txtPesquisar;
    RecyclerView recyclerTrainer;
    BottomNavigationView navigationView;

    //criação da listaTreinadores da classe dado
    private ArrayList<DadosTreinador> listaTreinadores = new ArrayList<DadosTreinador>();
    public List<DadosTreinador>listTreinador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_lista_personal_trainer);

        //instanciar a recycler
        recyclerTrainer=findViewById(R.id.recyclerTrainer);
        navigationView=findViewById(R.id.navBar);
        txtPesquisar=findViewById(R.id.txtPesquisar);

        Bundle extras = getIntent().getExtras();
        PersonalTrainerDao personalTrainerDao = new PersonalTrainerDao(this);
        DadosDao dadosDao = new DadosDao(this);
        Dado dado = new Dado();
        dado.setId(extras.getInt("id"));

        setListaTreinadores(personalTrainerDao);  //Guardar valores na listaJogadores
        setAdapter(listaTreinadores);  //mandar para a AdapterRecycler

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.homeId){
                    Intent intent = new Intent(FormListaPersonalTrainer.this,ActivityMenu.class);
                    intent.putExtra("id",dado.getId());
                    startActivity(intent);
                    finish();
                }
                else if(item.getItemId()==R.id.equipaId){
                    if(personalTrainerDao.validarConta(dado,false)==false){
                        Intent intent = new Intent(FormListaPersonalTrainer.this,FormTrainer.class);
                        intent.putExtra("id",dado.getId());
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Você já faz parte da equipa", Toast.LENGTH_SHORT).show();
                    }
                }
                else if(item.getItemId()==R.id.loginId){
                    Intent intent = new Intent(FormListaPersonalTrainer.this,ActivityProfile.class);
                    intent.putExtra("id",dado.getId());
                    startActivity(intent);
                    finish();
                }
                return false;
            }
        });
        txtPesquisar.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
              listaTreinadores.clear();
              if(txtPesquisar.equals(""))
              {

                  setListaTreinadores(personalTrainerDao);
                  setAdapter(listaTreinadores);
              }
              else{

                  String nome = txtPesquisar.getText().toString();
                  listTreinador=personalTrainerDao.pesquisarTreinador(nome);
                  for(int i=0;i<listTreinador.size();i++){
                      DadosTreinador dadosTreinador= new DadosTreinador(listTreinador.get(i).nome,listTreinador.get(i).localidade);
                      listaTreinadores.add(dadosTreinador);
                  }
                  setAdapter(listaTreinadores);

              }
          }

          @Override
          public void afterTextChanged(Editable s) {

          }
        });
    }
        private void setListaTreinadores(PersonalTrainerDao personalTrainerDao) {
            listTreinador=personalTrainerDao.getTreinadores();
            for(int i=0;i<listTreinador.size();i++){
                DadosTreinador dadosTreinador= new DadosTreinador(listTreinador.get(i).nome,listTreinador.get(i).localidade);
                listaTreinadores.add(dadosTreinador);

            }
        }
        private void setAdapter(ArrayList<DadosTreinador> listaTreinadores) {
            AdapterRecycler adapterRecycler = new AdapterRecycler(listaTreinadores);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerTrainer.setLayoutManager(layoutManager);
            recyclerTrainer.setItemAnimator(new DefaultItemAnimator());
            //VAI FAZER COM QUE APAREÇA OS DADOS DA LISTA
            recyclerTrainer.setAdapter(adapterRecycler);
        }
        private void setPesquisar(PersonalTrainerDao personalTrainerDao){
            if(txtPesquisar.equals(""))
            {
                setListaTreinadores(personalTrainerDao);
                setAdapter(listaTreinadores);
            }
            else{

                String nome = txtPesquisar.getText().toString();
                listTreinador=personalTrainerDao.pesquisarTreinador(nome);
                for(int i=0;i<listTreinador.size();i++){
                    DadosTreinador dadosTreinador= new DadosTreinador(listTreinador.get(i).nome,listTreinador.get(i).localidade);
                    listaTreinadores.add(dadosTreinador);
                }
                setAdapter(listaTreinadores);
                Toast.makeText(getApplicationContext(),"Hello Javatpoint",Toast.LENGTH_SHORT).show();
            }

        }
}