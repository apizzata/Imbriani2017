package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.R;

/**
 * Created by Antonio on 15/05/2017.
 */

public class DescrizioneProdotto extends AppCompatActivity {

    TextView Nome,Scadenza,Marca,Quantita,Supermercato;
    GestioneDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_prodotto);
        db= new GestioneDB(this);
        db.open();
        Nome=(TextView) findViewById(R.id.nome_obj_descr);
        Scadenza=(TextView) findViewById(R.id.scadenza_obj_desscr);
        Marca=(TextView) findViewById(R.id.marca_obj_descr);
        Quantita=(TextView) findViewById(R.id.quantita_obj_descr);
        Supermercato=(TextView) findViewById(R.id.sup2_obj_descr);

        Intent i= getIntent();
        //String[] dati=i.getExtras().getString("Nome").split("\\s+");
        Integer x= i.getExtras().getInt("Id");
      /*  Nome.setText(dati[1]);
        Marca.setText(dati[2]);
        Supermercato.setText(dati[3]);
        Quantita.setText(dati[4]);
        Scadenza.setText(dati[7]);
*/
        Cursor c= db.ottieniProdottiById(x);
        if(c.moveToFirst()) {
            Nome.setText(c.getString(1));
            Marca.setText(c.getString(2));
            Supermercato.setText(c.getString(3));
            Quantita.setText(c.getString(4));
            Scadenza.setText(c.getString(7));
        }



    }
}
