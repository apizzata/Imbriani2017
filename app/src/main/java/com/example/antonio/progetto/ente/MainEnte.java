package com.example.antonio.progetto.ente;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.antonio.progetto.R;

/**
 * Created by Antonio on 14/05/2017.
 */

public class MainEnte extends AppCompatActivity {

    Button BModifica_info, BRicerca_Prodotti, BLog_out;
    EditText Ricerca_Prodotti;
    ListView Lista_prodotti;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BModifica_info= (Button) findViewById(R.id.b_modifica_info_ente);
        BRicerca_Prodotti= (Button) findViewById(R.id.b_ricerca_ente);
        BLog_out= (Button) findViewById(R.id.b_log_out_ente);
        Ricerca_Prodotti= (EditText) findViewById(R.id.ricerca_prodotti);
        Lista_prodotti=(ListView) findViewById(R.id.lista_ordini_ente);
        //TODO la lista si riempe degli ordini dell'utente
    }

    public void ricerca_prodotti(View v){
        String ricerca= Ricerca_Prodotti.getText().toString();
        //TODO query di ricerca e i risultati vanno nella lista
    }

}

