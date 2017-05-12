package com.example.reza.task4ngulangyangkesekian;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by REZA on 09/05/2017.
 */

public class Home extends Fragment {

        ArrayList<HashMap<String, String>> ListOutcome;
        ArrayList<HashMap<String, String>> ListIncome;
        String []Tujuan_Pengeluaran;//mengeluarkan list tujuan pengeluran
        String []NilaiPengeluaran;//mendisplay list jumlah yang dikeluarkan




        String []SumberIncome;//mengeluarkan list sumber pemasukan
        String [] NilaiIncome;



        int [] totalPengeluaran;
        int [] totalPemasukan;

        SQLiteDatabase db;
        SimpanTransaksi transaksi;

        SimpleAdapter adapter ;
        ListView ListPemasukan, ListPengeluran;
        protected Cursor cursor;
        TextView lblPengeluaran, lblTOtalO, JumlahPengeluaran;//TextView Pengeluarang
        TextView lblPemasukan, lblTotalI, jumlahPemasukan;//TextView Pemasukan
        TextView lblTotalSado, jumlahSaldo;//TextView Saldo
        int Hasilpengeluaran, Hasilpemasukan, Hasilsaldo;

        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
            View view= inflater.inflate(R.layout.f_home,container,false);

            lblPengeluaran      = (TextView) view.findViewById(R.id.Pengeluaran);
            lblTOtalO           = (TextView) view.findViewById(R.id.TextTotalPemasukan);
            JumlahPengeluaran   = (TextView) view.findViewById(R.id.JumlahPengeluaran);
            //findViewbyId buat pemasukan
            lblPemasukan        = (TextView) view.findViewById(R.id.Pengeluaran);
            lblTotalI           = (TextView) view.findViewById(R.id.TextTotalPemasukan);
            jumlahPemasukan     = (TextView) view.findViewById(R.id.JumlahPemasukan);
            //findView by id Saldo
            lblTotalSado        = (TextView) view.findViewById(R.id.TextSaldo);
            jumlahSaldo         = (TextView) view.findViewById(R.id.JumlahSaldo);

            ListPengeluran = (ListView) view.findViewById(R.id.ListPengeluaran);
            ListPemasukan = (ListView) view.findViewById(R.id.ListPenmasukan);

            ListOutcome = new ArrayList<HashMap<String, String>>();
            ListIncome = new ArrayList<HashMap<String, String>>();
            transaksi = new SimpanTransaksi(getContext());

            ambilDataPengeluaran();
            ambilDataPemasukan();
            dataSaldo();
            return view;

        }

        public void ambilDataPengeluaran(){

                db = transaksi.getReadableDatabase();
                Cursor cursor = db.rawQuery("SELECT * FROM Pengeluaran",null);

                Tujuan_Pengeluaran = new String[cursor.getCount()];
                NilaiPengeluaran = new String[cursor.getCount()];
                totalPengeluaran = new int[cursor.getCount()];

                cursor.moveToFirst();
          /*
    */
                for (int cc = 0 ; cc <cursor.getCount(); cc++){
                    cursor.moveToPosition(cc);
                    Tujuan_Pengeluaran[cc] = cursor.getString(1).toString();
                    NilaiPengeluaran[cc] = cursor.getString(2).toString();
                    totalPengeluaran [cc] = cursor.getInt(2);

                    HashMap<String, String> map = new HashMap<String, String>();
                    map.put("pname", Tujuan_Pengeluaran[cc]);
                    map.put("pprice",NilaiPengeluaran[cc]);
                    ListOutcome.add(map);
                    Hasilpengeluaran = Hasilpengeluaran + totalPengeluaran[cc];
                    Log.e("Pengeluaran", String.valueOf(ListOutcome));
                    Log.i("Jumlah",cursor.getString(2));


                    }

                adapter = new SimpleAdapter(getContext(), ListOutcome, R.layout.daftar_transaksi
                        , new String[]{"pname","pprice"},
                        new int []{R.id.pname,R.id.pprice});
                ListPengeluran.setAdapter(adapter);

              //  }


            }

        public void ambilDataPemasukan(){
            db = transaksi.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT * FROM Pemasukan",null);
            SumberIncome = new String[cursor.getCount()];
            NilaiIncome = new String [cursor.getCount()];
            totalPemasukan =new int[cursor.getCount()];

            cursor.moveToFirst();
            for(int ii = 0 ; ii<cursor.getCount();ii++){
                cursor.moveToPosition(ii);
                SumberIncome[ii] = cursor.getString(1).toString();
                NilaiIncome[ii] = cursor.getString(2).toString();
                totalPemasukan[ii] = cursor.getInt(2);

                HashMap<String,String> map1 = new HashMap<String, String>();
                map1.put("pname1", SumberIncome[ii]);
                map1.put("pprice1", NilaiIncome[ii]);
                ListIncome.add(map1);

                Hasilpemasukan = Hasilpemasukan + totalPemasukan[ii];
            }
            adapter = new SimpleAdapter(getContext(),ListIncome,R.layout.daftar_transaksi
                                            ,new String[]{"pname1","pprice1"}
                                            ,new int[]{R.id.pname,R.id.pprice});
            ListPemasukan.setAdapter(adapter);
         }

        public void dataSaldo(){
            JumlahPengeluaran.setText(String.valueOf(Hasilpengeluaran));
            jumlahPemasukan.setText(String.valueOf(Hasilpemasukan));

            Hasilsaldo = Hasilpemasukan - Hasilpengeluaran;

            jumlahSaldo.setText(String.valueOf(Hasilsaldo));

        }
    }



