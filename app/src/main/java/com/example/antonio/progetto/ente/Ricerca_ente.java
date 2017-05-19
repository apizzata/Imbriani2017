package com.example.antonio.progetto.ente;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.R;
import com.example.antonio.progetto.supermercato.DescrizioneProdotto;
import com.example.antonio.progetto.supermercato.MainSupermercato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Antonio on 19/05/2017.
 */

public class Ricerca_ente extends AppCompatActivity {
    ListView Lista_prodotti;
    private GestioneDB db;
    private String ente;
    private Vector<Integer> Id;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ricerca_ente);
        Lista_prodotti = (ListView) findViewById(R.id.lista_ricerca);
        db = new GestioneDB(this);
        db.open();
        Id = new Vector<>();
        Intent j = getIntent();
        String ricerca = j.getExtras().getString("Ricerca");
        ente = j.getExtras().getString("Ente");
        HashMap<Integer, String> mappa = new HashMap<Integer, String>();
        int i = 0;
        String[] prodotti = {};
        final List<String> prodotti_list = new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prodotti_list);
        Lista_prodotti.setAdapter(adapter);


        Cursor cursor = db.ottieniProdottiByNome(ricerca);
        if (cursor.moveToFirst()) {
            do {
                mappa.put(i, cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(4));
                //Toast.makeText(this,cursor.getString(2),Toast.LENGTH_SHORT).show();
                if(cursor.getInt(4)!=0) {
                    Id.add(i, cursor.getInt(0));
                    i++;
                }
            } while (cursor.moveToNext());
        }
        for (int ka = 0; ka < mappa.size(); ka++) {
            prodotti_list.add(ka, mappa.get(ka));

        }
        Log.d("ProvaID", Id.get(0).toString());
        Lista_prodotti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  Lista_prodotti.getItemAtPosition(position);
                String obj= o.toString();
                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(Ricerca_ente.this,DescrizioneProdotto.class);
                // i.putExtra("Nome",obj);
                i.putExtra("Id",Id.get(position));
                i.putExtra("Ente",ente);
                i.putExtra("Invoking","Ricerca");
                startActivity(i);
            }
        });

        db.close();
    }

    public void onResume() {
        super.onResume();

        db.open();
        Intent j = getIntent();
        String ricerca = j.getExtras().getString("Ricerca");
        HashMap<Integer, String> mappa = new HashMap<Integer, String>();
        int i = 0;
        String[] prodotti = {};
        final List<String> prodotti_list = new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, prodotti_list);
        Lista_prodotti.setAdapter(adapter);
        Lista_prodotti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  Lista_prodotti.getItemAtPosition(position);
                String obj= o.toString();
                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(Ricerca_ente.this,DescrizioneProdotto.class);
                // i.putExtra("Nome",obj);
                i.putExtra("Id",Id.get(position));
                i.putExtra("Ente",ente);
                i.putExtra("Invoking","Ricerca");
                startActivity(i);
            }
        });

        Cursor cursor = db.ottieniProdottiByNome(ricerca);
        if (cursor.moveToFirst()) {
            do {
                mappa.put(i, cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(4));
                //Toast.makeText(this,cursor.getString(2),Toast.LENGTH_SHORT).show();

                if(cursor.getInt(4)!=0) {
                    Id.add(i, cursor.getInt(0));
                    i++;
                }
            } while (cursor.moveToNext());
        } else {
            Intent k = new Intent(this, MainEnte.class);
            Toast.makeText(this, "La ricerca non ha fornito risultati", Toast.LENGTH_LONG).show();
            startActivity(k);
        }
        for (int ka = 0; ka < mappa.size(); ka++) {
            prodotti_list.add(ka, mappa.get(ka));

        }
        db.close();
    }


}

