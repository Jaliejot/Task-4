package com.example.reza.task4ngulangyangkesekian;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by REZA on 09/05/2017.
 */

public class Transaksi extends Fragment {
    String pengeluaran;
    String Jpengeluran;
    String pemasukan;
    EditText InpTujuanPngeluaran, InpBiayaPengeluaran;// input tujuan dan jumlah pengeluaran
    EditText InpSumberPemasukan, InpBiayaPemasukan;//input tujuan dan jumlah pemasukan
    Button SubmitPengeluaran, SubmitPemasukan;
    SimpanTransaksi dataHelper;


    public Transaksi() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.f_transaksi,container,false);

       dataHelper = new SimpanTransaksi(getContext()) ;
        //Inisiasi tujuan dan jumlah pengeluaran
        InpTujuanPngeluaran = (EditText) view.findViewById(R.id.DeskripsiPengeluaran);
        InpBiayaPengeluaran = (EditText) view.findViewById(R.id.hargaPengeluaran);
        //Inisiasi sumber dan jumlah pemasukan
        InpSumberPemasukan = (EditText) view.findViewById(R.id.DeskripsiPemasukan);
        InpBiayaPemasukan = (EditText) view.findViewById(R.id.hargaPemasukan);

        SubmitPengeluaran = (Button) view.findViewById(R.id.submitPengeluaran);
        SubmitPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dataHelper.getWritableDatabase();





               /* bundle.putString("Tpengeluaran", InpTujuanPngeluaran.getText().toString());
                bundle.putString("JPengeluaran", InpBiayaPengeluaran.getText().toString());*/


                db.execSQL("insert into Pengeluaran(tujuanpengeluaran, biayapengeluaran) values('" +
                        InpTujuanPngeluaran.getText().toString()+ "','" +
                        InpBiayaPengeluaran.getText().toString()+ "')");
                Toast.makeText(getContext(), "Berhasil", Toast.LENGTH_SHORT).show();
               /* Log.e("Tujuan Pengeluaran =", pengeluaran);
                Log.i("Jumlah Pengeluaran =", Jpengeluran);*/


                //fragment.setArguments(bundle);
                //ft.replace(R.id.content_frame, fragment);
                //ft.commit();

                //ft.replace(R.id.content_dashboard,fragment);


            }
        });

        SubmitPemasukan = (Button) view.findViewById(R.id.submitPemasukan);
        SubmitPemasukan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dataHelper.getWritableDatabase();
                db.execSQL("insert into Pemasukan(sumberpemasukan,jumlahpemasukan) values('"+
                InpSumberPemasukan.getText().toString()+"','"+
                InpBiayaPemasukan.getText().toString()+"')");
                Toast.makeText(getContext(),"Berhasil",Toast.LENGTH_LONG).show();
                InpBiayaPemasukan.setText("");
                InpSumberPemasukan.setText("");
            }
        });
        return view;
    }
}
