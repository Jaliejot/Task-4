package com.example.reza.task4ngulangyangkesekian;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
   SQLiteDatabase dta;
    SimpanTransaksi getData;
    DatabaseReference Pemasukan;
    DatabaseReference pengeluaran;

   // List<Home> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_Dashboard);
        //getLisExpensed();
         //transaksi = new SimpanTransaksi(this);
        getData = new SimpanTransaksi(this);
        //data = new ArrayList<Home>();
//        FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        Pemasukan = FirebaseDatabase.getInstance().getReference("Incomes");
        pengeluaran= FirebaseDatabase.getInstance().getReference("Expense") ;

        Pemasukan.keepSynced(false);



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void displaySelectedScreen(int itemId){
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_Dashboard:
                fragment = new Home();

                break;
            case R.id.nav_Transaction:
                fragment = new Transaksi();
                break;
            case R.id.nav_Synconisation:
                new FireBaseExecute().execute();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
     displaySelectedScreen(item.getItemId());
        return true;
    }




 /*   public void getLisExpensed() {

        Home fragment = new Home();
        db =  transaksi.getReadableDatabase();
       cursor = db.execSQL("Select * FROM Pengeluaran",null);
        fragment.ambilDataPengeluaran();


    }*/



    class FireBaseExecute extends AsyncTask<Void, Void, Void>{
        Cursor dataPengeluaran;
        Cursor dataPemasukan;
       // HashMap<String,String> map = new HashMap<>();
        ArrayList<HashMap<String,String>> data = new ArrayList<>();
        ArrayList<HashMap<String,String>> data1 = new ArrayList<>();
        String [] dataE;
        String [] dtJumlahE;
        String [] idE;

        String [] dataI;
        String [] dtJumlahI;
        String [] idI;


        @Override
        protected Void doInBackground(Void... params) {
            dta = getData.getReadableDatabase();


            dataPengeluaran = dta.rawQuery("SELECT * FROM Pengeluaran",null);
            dataPemasukan =dta.rawQuery("SELECT * FROM Pemasukan",null);

            dataE = new String[dataPengeluaran.getCount()];
            dtJumlahE = new String[dataPengeluaran.getCount()];
            idE = new String [dataPengeluaran.getCount()];

            dataI = new String[dataPemasukan.getCount()];
            dtJumlahI = new String[dataPemasukan.getCount()];
            idI = new String [dataPemasukan.getCount()];




            try{
                if (dataPengeluaran!=null){
                dataPengeluaran.moveToFirst();
                for (int i = 0 ; i<dataPengeluaran.getCount();i++) {
                    dataPengeluaran.moveToPosition(i);
                    dataE[i] = dataPengeluaran.getString(1).toString();
                    dtJumlahE[i]= dataPengeluaran.getString(2).toString();
                    idE[i] = dataPengeluaran.getString(0).toString();
                    HashMap<String,String> map = new HashMap<>();

                    map.put("pengeluaran",dataE[i]);
                    map.put("biaya",dtJumlahE[i]);
                    map.put("id",idE[i]);
                    data.add(map);

                }

                pengeluaran.push();
                pengeluaran.setValue(data);
                }
                if (dataPemasukan!=null) {
                    dataPemasukan.moveToFirst();


                    for (int c = 0; c < dataPemasukan.getCount(); c++) {
                        dataPemasukan.moveToPosition(c);
                        dataI[c] = dataPemasukan.getString(1).toString();
                        dtJumlahI[c]=dataPemasukan.getString(2).toString();
                        idI[c]=dataPemasukan.getString(0).toString();
                        HashMap<String,String> map1 = new HashMap<>();
                        map1.put("pemasukan",dataI[c]);
                        map1.put("biayaPemasukan",dtJumlahI[c]);
                        map1.put("idPemasukan",idI[c]);
                        data1.add(map1);
                    }
                    Pemasukan.push().setValue(data1);
                }


            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

}

