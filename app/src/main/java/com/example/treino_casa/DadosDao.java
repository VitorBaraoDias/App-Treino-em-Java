package com.example.treino_casa;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class DadosDao {
    private SQLiteDatabase db;
    private DbHelperUser dbHelperUser; // instância da classe que criamos
    private Dado dado;
    private List<Dado> dadoList;

    public DadosDao(Context context){
        dbHelperUser = new DbHelperUser(context);
        db = dbHelperUser.getWritableDatabase();
    }
    public void inserirdados(Dado dado){
        ContentValues contentValues=new ContentValues();
        contentValues.put("id_user",dado.getId());
        contentValues.put("nome",dado.getNome());
        contentValues.put("idade",dado.getIdade());
        contentValues.put("peso",dado.getPeso());
        contentValues.put("altura",dado.getAltura());
        contentValues.put("genero",dado.getGenero());
        db.insert("dadosUser", null, contentValues);
    }
    @SuppressLint("Range")
    public boolean verificarDadosinseridos(Dado dado) {

        String strSql = "select * from dadosUser";
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();
        int i = dado.getId();
        Cursor c = db.rawQuery(strSql, null);
        if (c.moveToFirst()) {
            do {
                if (i==(c.getInt(c.getColumnIndex("id_user")))) {
                  return true;

                }
            } while (c.moveToNext());
        }
        return false;
    }
    @SuppressLint("Range")
    public String verificarGenero(Dado dado, String gen) {

        String strSql = "select genero from dadosUser where id_dados = ?";
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();
        int i = dado.getId();
        Cursor c = db.rawQuery(strSql, new String[]{String.valueOf(dado.getId())});
        if (c.moveToFirst()) {
            gen=c.getString(c.getColumnIndex("genero"));
              return gen;
        }
        return gen;
    }
    @SuppressLint("Range")
    public Dado getCaracteristicasUser(Dado dado) {
        String strSql = "select nome,idade,peso,altura from dadosUser where id_dados = ?";
        dadoList = new ArrayList<Dado>();
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();
        Cursor c = db.rawQuery(strSql,  new String[]{String.valueOf(dado.getId())});
        if (c.moveToFirst()) {
            do {
                String nome  = c.getString(c.getColumnIndex("nome"));
                int idade =c.getInt(c.getColumnIndex("idade"));
                int peso  = c.getInt(c.getColumnIndex("peso"));
                int altura =c.getInt(c.getColumnIndex("altura"));
                dado.setNome(nome);
                dado.setIdade(idade);
                dado.setPeso(peso);
                dado.setAltura(altura);
                return dado;
            } while (c.moveToNext());
        }
      return dado;
    }
    @SuppressLint("Range")
    public void alterarPerfil(Dado dado){
        ContentValues contentValues=new ContentValues();
        contentValues.put("nome",dado.getNome());
        contentValues.put("idade",dado.getIdade());
        contentValues.put("peso",dado.getPeso());
        contentValues.put("altura",dado.getAltura());
        db.update("dadosUser",contentValues,"id_user="+ dado.getId(),null);
    }
    public void eliminaTarefa(Dado dado){
        db.delete("dadosUser","id_user=" + dado.getId(),null);
    }
}
