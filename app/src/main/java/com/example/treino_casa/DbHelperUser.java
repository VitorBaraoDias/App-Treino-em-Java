package com.example.treino_casa;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelperUser extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tabela_user";
    private static final int DATABASE_VERSION = 1;
    // String para criação da tabela tarefa

    public static final String tableUser= "create table user"
            + "(id_user integer primary key autoincrement,"
            + " email text not null,"
            + " pass text not null)";

    public static final String tableCarc= "create table dadosUser"
            + "(id_dados integer primary key autoincrement,"
            + " id_user integer not null,"
            + " nome text not null,"
            + " idade integer not null,"
            + " peso integer not null,"
            + " altura integer not null,"
            + " genero text not null,"
            + " FOREIGN KEY (id_user)"
            + "REFERENCES user(id_user))";
    public static final String tabletTrainer= "create table personalTrainer"
            + "(id_trainer integer primary key autoincrement,"
            + " id_user integer not null,"
            + " nome text not null,"
            + " localidade text not null,"
            + " certificado text not null,"
            + " FOREIGN KEY (id_user)"
            + "REFERENCES user(id_user))";
    public DbHelperUser(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tableUser);
        db.execSQL(tableCarc);
        db.execSQL(tabletTrainer);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
