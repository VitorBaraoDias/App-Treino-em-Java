package com.example.treino_casa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ActivityMenu extends AppCompatActivity {
    TextView txtData;
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        navigationView=findViewById(R.id.navBar);
        Dado dado = new Dado();
        Bundle extras = getIntent().getExtras();
        dado.setId(extras.getInt("id"));
        DadosDao dadosDao = new DadosDao(this);
        PersonalTrainerDao personalTrainerDao = new PersonalTrainerDao(this);
        String genero=dadosDao.verificarGenero(dado,"");

        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                escolherActivity(item,personalTrainerDao,dado);
                return false;
            }
        });
    }
    private void escolherActivity(MenuItem item,PersonalTrainerDao personalTrainerDao,Dado dado){
        if(item.getItemId()==R.id.homeId){
            Intent intent = new Intent(ActivityMenu.this,MainActivity.class);
            startActivity(intent);
            finish();
        }
        else if(item.getItemId()==R.id.equipaId){
            if(personalTrainerDao.validarConta(dado,false)==false){
                Intent intent = new Intent(ActivityMenu.this,FormTrainer.class);
                intent.putExtra("id",dado.getId());
                startActivity(intent);
            }
            else{
                Toast.makeText(getApplicationContext(), "Você já faz parte da equipa", Toast.LENGTH_SHORT).show();
            }
        }
        else if(item.getItemId()==R.id.recylerTrainerId){
            Intent intent = new Intent(ActivityMenu.this,FormListaPersonalTrainer.class);
            intent.putExtra("id",dado.getId());
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.loginId){
            Intent intent = new Intent(ActivityMenu.this,ActivityProfile.class);
            intent.putExtra("id",dado.getId());
            startActivity(intent);
        }

    }
}