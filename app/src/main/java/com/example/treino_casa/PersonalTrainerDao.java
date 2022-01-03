package com.example.treino_casa;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class PersonalTrainerDao {

    private SQLiteDatabase db;
    private DbHelperUser dbHelperUser; // instância da classe que criamos
    //INSERIR TREINADOR
    private List<Dado> dados;
    private Dado Dado;
    //CARREGAR RECYCLER
    private List<DadosTreinador> dadosTreinadors;
    private DadosTreinador dadosTreinador;

    public PersonalTrainerDao(Context context){
        dbHelperUser = new DbHelperUser(context);
        db = dbHelperUser.getWritableDatabase();
    }
    public void inserirTrainer(Dado dado){

        ContentValues contentValues=new ContentValues();
        contentValues.put("id_user",dado.getId());
        contentValues.put("nome",dado.getNome());
        contentValues.put("localidade",dado.getLocalidade());
        contentValues.put("certificado",dado.getCertificado());
        db.insert("personalTrainer", null, contentValues);
    }
    @SuppressLint("Range")
    public boolean validarConta(Dado dado, boolean login) {

        String strSql = "select * from personalTrainer";
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();
        int id = dado.getId();
        String pass = dado.getPass();
        boolean teste=false;
        Cursor c = db.rawQuery(strSql, null);
        if(login==false){
            if (c.moveToFirst()) {
                do {
                    if (id==(c.getInt(c.getColumnIndex("id_user")))) {
                        return teste=true;
                    }
                } while (c.moveToNext());
            }
        }
        return teste=false;
    }
    @SuppressLint("Range")
    public List<DadosTreinador> getTreinadores() {
        String strSql = "select * from personalTrainer";
        dadosTreinadors = new ArrayList<DadosTreinador>();
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();
        Cursor c = db.rawQuery(strSql, null);
        if (c.moveToFirst()) {
            do {
                String nome= c.getString(c.getColumnIndex("nome"));
                String localidade =c.getString(c.getColumnIndex("localidade"));
                DadosTreinador dadosTreinador = new DadosTreinador(nome,localidade);
                dadosTreinadors.add(dadosTreinador);
            } while (c.moveToNext());
        }
        return dadosTreinadors;
    }
    @SuppressLint("Range")
    public boolean validarTreinadorExistente(Dado dado, boolean login) {

        String strSql = "select nome from personalTrainer";
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();

        String nome = dado.getNome();
        boolean teste=false;
        Cursor c = db.rawQuery(strSql, null);
        if(login==false){
            if (c.moveToFirst()) {
                do {
                    if (nome.equals(c.getString(c.getColumnIndex("nome")))) {
                        return teste=true;
                    }
                } while (c.moveToNext());
            }
        }
        return teste=false;
    }
    @SuppressLint("Range")
    public List<DadosTreinador> pesquisarTreinador(String nometxt) {
        String strSql = "select * from personalTrainer";
        dadosTreinadors = new ArrayList<DadosTreinador>();
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();

        Cursor c =   db.rawQuery("select nome,localidade from personalTrainer where nome like '"+nometxt+"%' order by nome", null);
        if (c.moveToFirst()) {
            do {
                String nomeBd= c.getString(c.getColumnIndex("nome"));
                 String localidade =c.getString(c.getColumnIndex("localidade"));
                 DadosTreinador dadosTreinador = new DadosTreinador(nomeBd,localidade);
                 dadosTreinadors.add(dadosTreinador);
            } while (c.moveToNext());
        }
        return dadosTreinadors;
    }
    public void eliminaTarefa(Dado dado){
        db.delete("personalTrainer","id_user=" + dado.getId(),null);
    }
}
