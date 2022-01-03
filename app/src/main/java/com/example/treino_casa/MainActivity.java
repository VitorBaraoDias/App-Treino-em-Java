package com.example.treino_casa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public boolean bValidar_Caixas=false,iValidarContaNova=false,bTostCiarConta=false;
    public String passe="  ";
    public String e_mail="   ";

    TextView textCriar,textInicio,textLog;
    EditText txtemail,txtpass;
    Button btnSessao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSessao=findViewById(R.id.btnIniciarSessao);
        txtemail=findViewById(R.id.txtEmail);
        txtpass=findViewById(R.id.txtPass);
        textCriar=findViewById(R.id.textCriarConta);
        textLog=findViewById(R.id.textLog);
        textInicio=findViewById(R.id.textInicio);

        UserDao userDao= new UserDao(this);  //credenciais user
        DadosDao dadosDao = new DadosDao(this); //dados user
        PersonalTrainerDao personalTrainerDao = new PersonalTrainerDao(this);
        btnSessao.setOnClickListener(new View.OnClickListener() {
             @Override
         public void onClick(View view) {
                 if( bTostCiarConta==true){
                     criarConta(userDao);
                 }
                 else{
                     login(userDao,dadosDao);
                 }
        }
        });
        textCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bTostCiarConta==false){
                    txtemail.requestFocus();
                    bTostCiarConta=true;
                    textLog.setText("Registrar");
                    btnSessao.setText("Registrar");
                    textCriar.setText("Tem conta? Faça Login aqui");
                    limparCaixa();
                }
                else{
                    txtemail.requestFocus();
                    bTostCiarConta=false;
                    textLog.setText("Login");
                    btnSessao.setText("Login");
                    textCriar.setText("Não tem conta? Crie aqui a sua");
                    limparCaixa();
                }
            }
        });
    }
    private void criarConta(UserDao userDao){

            String email = txtemail.getText().toString().trim();
            String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            if (txtemail.getText().toString().trim().matches(emailPattern))     //valida formato do email
            {
                if(txtpass.getText().length()>5){         //conta criada se entrar
                    Dado dado= new Dado();
                    dado.setEmail(txtemail.getText().toString());
                    dado.setPass(String.valueOf(txtpass.getText()));

                    if(userDao.validarConta(dado,false)){
                        Toast.makeText(getApplicationContext(), "Já existe essa conta", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Conta Criada", Toast.LENGTH_SHORT).show();
                        userDao.inserirUser(dado); //INSERIR BD
                        bTostCiarConta=false; //conta criada
                    }
                }
                limparCaixa();
            }
            else{    //erro
                txtemail.setText("");
                txtemail.setError("Invalido");
                txtemail.requestFocus();
            }
        }

    private void login(UserDao userDao,DadosDao dadosDao){
        if(txtemail.getText().toString().equals("") || txtpass.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Existem espaços vazios ou não existe a conta", Toast.LENGTH_SHORT).show();
        }
       else{
            Dado dado= new Dado();
            dado.setEmail(txtemail.getText().toString());
            dado.setPass(txtpass.getText().toString());

            if(userDao.validarConta(dado,true)){

                dado.setId(userDao.idUser(dado,0));
                if(dadosDao.verificarDadosinseridos(dado)==true){
                    Intent intent = new Intent(MainActivity.this,ActivityMenu.class);
                    Toast.makeText(getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                    intent.putExtra("id",dado.getId());
                    finish();
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this,FormEscolhaGenero.class);
                    intent.putExtra("id",dado.getId());
                    startActivity(intent);
                }
                limparCaixa();
            }
            else{
                Toast.makeText(getApplicationContext(), "E-mail ou palavra-passe errada ", Toast.LENGTH_SHORT).show();
                limparCaixa();
                txtpass.setError("Password invalido");
                txtemail.setError("E-mail invalido");
                txtemail.requestFocus();
            }
        }
    }
    private void limparCaixa(){
        txtemail.setText("");
        txtpass.setText("");
    }
}