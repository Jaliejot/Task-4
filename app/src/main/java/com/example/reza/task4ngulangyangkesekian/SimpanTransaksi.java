package com.example.reza.task4ngulangyangkesekian;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by REZA on 09/05/2017.
 */

public class SimpanTransaksi extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Task4.db";
    private static final int DATABASE_VER = 1;

    public SimpanTransaksi(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VER);

    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String pengeluaran ="create table Pengeluaran(id integer  primary key AUTOINCREMENT , tujuanpengeluaran text, biayapengeluaran integer);";
        Log.d("table", "ON CREATE : "+pengeluaran);
        db.execSQL(pengeluaran);
        String pemasukan ="create table Pemasukan(id  integer  primary key AUTOINCREMENT, sumberpemasukan text, jumlahpemasukan integer );";
        Log.e("Tabel","ON CREATE ="+pemasukan);
        db.execSQL(pemasukan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}