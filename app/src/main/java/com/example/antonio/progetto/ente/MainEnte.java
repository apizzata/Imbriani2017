package com.example.antonio.progetto.ente;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.R;
import com.example.antonio.progetto.supermercato.DescrizioneProdotto;
import com.example.antonio.progetto.supermercato.MainSupermercato;
import com.example.antonio.progetto.supermercato.ModificaInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Antonio on 14/05/2017.
 */

public class MainEnte extends AppCompatActivity {

    Button BModifica_info, BRicerca_Prodotti, BLog_out;
    EditText Ricerca_Prodotti;
    ListView Lista_prodotti;
    private String ente;
    private HashMap<Integer,Integer[]> Id;
    private GestioneDB db;
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_enti);
        db=new GestioneDB(this);
        BModifica_info= (Button) findViewById(R.id.b_modifica_info_ente);
        BRicerca_Prodotti= (Button) findViewById(R.id.b_ricerca_ente);
        BLog_out= (Button) findViewById(R.id.b_log_out_ente);
        Ricerca_Prodotti= (EditText) findViewById(R.id.ricerca_prodotti);
        Lista_prodotti=(ListView) findViewById(R.id.lista_ordini_ente);
        Intent k=getIntent();
        ente=k.getExtras().getString("Ente");
        db.open();
        Cursor cursor=db.listaPrelevati(ente);
        //TODO la lista si riempe degli ordini dell'utente
        Ricerca_Prodotti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Ricerca_Prodotti.getText().toString().equals("Ricerca Prodotti"))
                     Ricerca_Prodotti.setText("");
            }
        });
        HashMap<Integer,String> mappa= new HashMap<Integer, String>();
        int i=0;

        Id= new HashMap<>();

        if (cursor.moveToFirst())
        {//return db.rawQuery("SELECT E.NOME,P.ID, P.NOME, P.MARCA, P.DATA_SCADENZA, R.STATO FROM ENTI E JOIN PRELEVATI R ON E.EMAIL=R.ENTE JOIN PRODOTTI P ON R.PRODOTTO=P.ID WHERE E.EMAIL='"+mail+"'", null);

            do {
                mappa.put(i,cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4));
                //Toast.makeText(this,cursor.getString(2),Toast.LENGTH_SHORT).show();
                Integer[] temp=new  Integer[] {cursor.getInt(1),cursor.getInt(5),cursor.getInt(6)};
                Id.put(i,temp);
                //Toast.makeText(this,temp[0],Toast.LENGTH_SHORT).show();
                i++;
            } while (cursor.moveToNext());
        }
        String[] prodotti={};
        final List<String> prodotti_list= new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prodotti_list);
        Lista_prodotti.setAdapter(adapter);
        Lista_prodotti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  Lista_prodotti.getItemAtPosition(position);

                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainEnte.this,com.example.antonio.progetto.supermercato.DescrizioneProdotto.class);
                i.putExtra("Invoking","Ente");
                i.putExtra("Id",Id.get(position));
                i.putExtra("Ente",ente);
                startActivity(i);
            }
        });
        for(int ka=0;ka<mappa.size();ka++) {
            prodotti_list.add(ka, mappa.get(ka));

        }
        db.close();

    }

    public void onResume(){
        super.onResume();
        db.open();
        Cursor cursor=db.listaPrelevati(ente);
        //TODO la lista si riempe degli ordini dell'utente
        Ricerca_Prodotti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Ricerca_Prodotti.getText().toString().equals("Ricerca Prodotti"))
                    Ricerca_Prodotti.setText("");
            }
        });
        HashMap<Integer,String> mappa= new HashMap<Integer, String>();
        int i=0;

        Id= new HashMap<>();

        if (cursor.moveToFirst())
        {//return db.rawQuery("SELECT E.NOME,P.ID, P.NOME, P.MARCA, P.DATA_SCADENZA, R.STATO FROM ENTI E JOIN PRELEVATI R ON E.EMAIL=R.ENTE JOIN PRODOTTI P ON R.PRODOTTO=P.ID WHERE E.EMAIL='"+mail+"'", null);

            do {
                mappa.put(i,cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4));
                //Toast.makeText(this,cursor.getString(2),Toast.LENGTH_SHORT).show();
                Integer[] temp=new  Integer[] {cursor.getInt(1),cursor.getInt(5),cursor.getInt(6)};
                Id.put(i,temp);
                //Toast.makeText(this,temp[0],Toast.LENGTH_SHORT).show();
                i++;
            } while (cursor.moveToNext());
        }
        String[] prodotti={};
        final List<String> prodotti_list= new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prodotti_list);
        Lista_prodotti.setAdapter(adapter);
        Lista_prodotti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  Lista_prodotti.getItemAtPosition(position);

                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainEnte.this,com.example.antonio.progetto.supermercato.DescrizioneProdotto.class);
                i.putExtra("Invoking","Ente");
                i.putExtra("Id",Id.get(position));
                i.putExtra("Ente",ente);
                startActivity(i);
            }
        });
        for(int ka=0;ka<mappa.size();ka++) {
            prodotti_list.add(ka, mappa.get(ka));

        }
        db.close();

    }
    public void ricerca_prodotti(View v){
        String ricerca= Ricerca_Prodotti.getText().toString().toLowerCase();
        //TODO query di ricerca e i risultati vanno nella lista
        Intent i= new Intent(this, Ricerca_ente.class);
        i.putExtra("Ricerca",ricerca);
        i.putExtra("Ente",ente);
        startActivity(i);
    }

    public void onClickCambiaInfo(View v){
        Intent i= new Intent(this,com.example.antonio.progetto.supermercato.ModificaInfo.class);
        i.putExtra("Supermercato",ente);
        i.putExtra("Invoking","Ente");
        startActivity(i);
    }

}

