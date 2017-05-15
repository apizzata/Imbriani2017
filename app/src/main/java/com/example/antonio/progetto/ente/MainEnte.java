package com.example.antonio.progetto.ente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.antonio.progetto.R;
import com.example.antonio.progetto.supermercato.DescrizioneProdotto;
import com.example.antonio.progetto.supermercato.MainSupermercato;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Antonio on 14/05/2017.
 */

public class MainEnte extends AppCompatActivity {

    Button BModifica_info, BRicerca_Prodotti, BLog_out;
    EditText Ricerca_Prodotti;
    ListView Lista_prodotti;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_enti);
        BModifica_info= (Button) findViewById(R.id.b_modifica_info_ente);
        BRicerca_Prodotti= (Button) findViewById(R.id.b_ricerca_ente);
        BLog_out= (Button) findViewById(R.id.b_log_out_ente);
        Ricerca_Prodotti= (EditText) findViewById(R.id.ricerca_prodotti);
        Lista_prodotti=(ListView) findViewById(R.id.lista_ordini_ente);
        //TODO la lista si riempe degli ordini dell'utente
        Ricerca_Prodotti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Ricerca_Prodotti.setText("");
            }
        });
        String[] prodotti={};
        final List<String> prodotti_list= new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prodotti_list);
        Lista_prodotti.setAdapter(adapter);
        Lista_prodotti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  Lista_prodotti.getItemAtPosition(position);
                String obj= o.toString();
                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainEnte.this,DescrizioneOrdine.class);
                i.putExtra("Nome",obj);
                startActivity(i);
            }
        });
    }

    public void ricerca_prodotti(View v){
        String ricerca= Ricerca_Prodotti.getText().toString();
        //TODO query di ricerca e i risultati vanno nella lista
    }

}

