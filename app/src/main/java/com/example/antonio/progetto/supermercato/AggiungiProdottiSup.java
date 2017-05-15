package com.example.antonio.progetto.supermercato;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.antonio.progetto.R;


import java.util.Vector;

/**
 * Created by Antonio on 15/05/2017.
 */

public class AggiungiProdottiSup extends AppCompatActivity{

    EditText NomeProdotto,GiornoScadenza,MeseScadenza,AnnoScadenza;
    Button BCarica;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_prodotto_supermercato);
        NomeProdotto = (EditText) findViewById(R.id.nome_nuovo_prod);
        GiornoScadenza= (EditText) findViewById(R.id.giorno_nuovo_prod);
        MeseScadenza= (EditText) findViewById(R.id.mese_nuovo_prod);
        AnnoScadenza=(EditText) findViewById(R.id.anno_nuovo_prod);
        BCarica= (Button) findViewById(R.id.b_aggiungi_nuovo_prod_activity);
        NomeProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NomeProdotto.setText("");
            }
        });
        GiornoScadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GiornoScadenza.setText("");
            }
        });
        MeseScadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeseScadenza.setText("");
            }
        });
        AnnoScadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnnoScadenza.setText("");
            }
        });

    }

    public  void onClickAggiungi(View v) {
        String nome = NomeProdotto.getText().toString();

    }

}
