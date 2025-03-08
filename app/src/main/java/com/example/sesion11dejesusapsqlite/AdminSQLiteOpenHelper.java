package com.example.sesion11dejesusapsqlite;

//librerias de conexion a BD SQLite

import  android.database.sqlite.SQLiteDatabase;
import  android.database.sqlite.SQLiteDatabase.CursorFactory;
import  android.database.sqlite.SQLiteOpenHelper;
import  android.content.Context;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Crea tabla en base de datos administracion
        db.execSQL("create table articulo (cod integer primary key, descripcion text, ubicacion text, existencia integer )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists articulo");
        db.execSQL("create table articulo (cod integer primary key, descripcion text, ubicacion text, existencia integer )");
    }//inicia clase

}