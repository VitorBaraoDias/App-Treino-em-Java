package com.example.treino_casa;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class UserDao {


    private SQLiteDatabase db;
    private DbHelperUser dbHelperUser; // instância da classe que criamos

    private List<Dado> dados;
    private Dado Dado;

    public UserDao(Context context){
        dbHelperUser = new DbHelperUser(context);
        db = dbHelperUser.getWritableDatabase();
    }
    public void inserirUser(Dado dado){
        ContentValues contentValues=new ContentValues();
        contentValues.put("email",dado.getEmail());
        contentValues.put("pass",dado.getPass());
        db.insert("user", null, contentValues);
    }
    @SuppressLint("Range")
    public boolean validarConta(Dado dado,boolean login) {

        String strSql = "select * from user";
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();
        String email = dado.getEmail();
        String pass = dado.getPass();
        boolean teste=false;
        Cursor c = db.rawQuery(strSql, null);
        if(login==false){
            if (c.moveToFirst()) {
                do {
                    if (email.equals(c.getString(c.getColumnIndex("email")))) {
                        return teste=true;
                    }
                } while (c.moveToNext());
            }
        }
        else {
            if (c.moveToFirst()) {
                do {
                    if (email.equals(c.getString(c.getColumnIndex("email")))) {
                        if(pass.equals(c.getString(c.getColumnIndex("pass")))){
                            int i=c.getInt(c.getColumnIndex("id_user"));

                            return teste=true;
                        }

                    }
                } while (c.moveToNext());
            }
        }
        return teste=false;
    }
    @SuppressLint("Range")
    public int idUser(Dado dado, int id) {

        String strSql = "select * from user";
        SQLiteDatabase db = dbHelperUser.getReadableDatabase();
        String email = dado.getEmail();
        String pass = dado.getPass();
        boolean teste=false;
        Cursor c = db.rawQuery(strSql, null);

            if (c.moveToFirst()) {
                do {
                    if (email.equals(c.getString(c.getColumnIndex("email")))) {
                        if(pass.equals(c.getString(c.getColumnIndex("pass")))){
                            id=c.getInt(c.getColumnIndex("id_user"));
                            return id;

                        }

                    }
                } while (c.moveToNext());
            }

        return id;
    }
    public void eliminaTarefa(Dado dado){
        db.delete("user","id_user=" + dado.getId(),null);
    }

}
