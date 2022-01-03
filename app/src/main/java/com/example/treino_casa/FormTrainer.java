package com.example.treino_casa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class FormTrainer extends AppCompatActivity {


    ImageView imgVoltar; //IMAGEM VOLTAR
    TextView txtNome,txtLocalidade;
    CheckBox checkCertificado;
    Button btnInserir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_trainer);

        objetos(); //estancia objetos

        Bundle extras = getIntent().getExtras();
        PersonalTrainerDao personalTrainerDao = new PersonalTrainerDao(this);
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FormTrainer.this,ActivityMenu.class);
                Dado dado = new Dado();
                dado.setId(extras.getInt("id"));
                intent.putExtra("id",dado.getId());
                finish();
                startActivity(intent);
            }
        });
        btnInserir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserir(extras,personalTrainerDao);
            }
        });

    }
    private void objetos(){
        imgVoltar=findViewById(R.id.imgVoltar);
        txtNome=findViewById(R.id.txtNomeTrainer);
        txtLocalidade=findViewById(R.id.txtLocalidade);
        btnInserir=findViewById(R.id.btnInserirTrainer);
        checkCertificado=findViewById(R.id.checkCertificado);
    }
    private void inserir(Bundle extras,PersonalTrainerDao personalTrainerDao){
        Dado dado = new Dado();
        if(txtNome.getText().toString().equals("")){
            txtNome.setError("Preencha a caixa!");
        }
        else if(txtLocalidade.getText().toString().equals("")){

            txtLocalidade.setError("Preencha a caixa!");
        }
        else{
            if(checkCertificado.isChecked()==false){
                dado.setCertificado("Não");
            }
            else{
                dado.setCertificado("Sim");
            }

            dado.setId(extras.getInt("id"));
            dado.setNome(txtNome.getText().toString());
            dado.setLocalidade(txtLocalidade.getText().toString());

           if(personalTrainerDao.validarTreinadorExistente(dado,false)){
               Toast.makeText(getApplicationContext(), "Já existe um personal trainer com esse nome!", Toast.LENGTH_SHORT).show();

           }
            else{
               personalTrainerDao.inserirTrainer(dado); //INSERIR NA TABELA PERSONAL TRAINER
               Toast.makeText(getApplicationContext(), "Personal Trainer inserido", Toast.LENGTH_SHORT).show();

               Intent intent = new Intent(FormTrainer.this,ActivityMenu.class);

               dado.setId(extras.getInt("id"));
               intent.putExtra("id",dado.getId());
               finish();
               startActivity(intent);
           }
            txtNome.requestFocus();
            txtLocalidade.setText("");
            txtNome.setText("");
            checkCertificado.setChecked(false);
        }
    }
}