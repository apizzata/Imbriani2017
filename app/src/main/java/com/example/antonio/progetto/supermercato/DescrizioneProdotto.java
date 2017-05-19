package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.R;

/**
 * Created by Antonio on 15/05/2017.
 */

public class DescrizioneProdotto extends AppCompatActivity {

    TextView Nome,Scadenza,Marca,Quantita,Supermercato,Stato,Stato2,Quantita1,Quantita2;
    Button BAcquisto;
    GestioneDB db;
    private Integer Qnt;
    private String Ente;
    private int IdObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_prodotto);
        db = new GestioneDB(this);

        Integer stato = 0;
        Integer[] y;
        Integer qnt;
        db.open();
        Nome = (TextView) findViewById(R.id.nome_obj_descr);
        Scadenza = (TextView) findViewById(R.id.scadenza_obj_desscr);
        Marca = (TextView) findViewById(R.id.marca_obj_descr);
        Quantita = (TextView) findViewById(R.id.quantita_obj_descr);
        Supermercato = (TextView) findViewById(R.id.sup2_obj_descr);
        Stato = (TextView) findViewById(R.id.stato_obj_descr);
        Stato2 = (TextView) findViewById(R.id.stato2_obj_descr);
        Quantita1=(TextView) findViewById(R.id.qnt2_obj_descr);
        Quantita2=(TextView) findViewById(R.id.qnt3_obj_descr);
        BAcquisto=(Button) findViewById(R.id.ordina_obj_descr);
        Intent i = getIntent();
        Ente=i.getExtras().getString("Ente").toString();
        //String[] dati=i.getExtras().getString("Nome").split("\\s+");
        String nome= i.getExtras().getString("Invoking").toString();

        if (nome.equals("Supermercato")) {
            Log.d("Invoking","sup");
            Stato.setVisibility(View.INVISIBLE);
            Stato2.setVisibility(View.INVISIBLE);
            Quantita1.setVisibility(View.INVISIBLE);
            Quantita2.setVisibility(View.INVISIBLE);
            BAcquisto.setVisibility(View.INVISIBLE);
            IdObj = i.getExtras().getInt("Id");
            Cursor c = db.ottieniProdottiById(IdObj);
            if (c.moveToFirst()) {
                Nome.setText(c.getString(1));
                Marca.setText(c.getString(2));
                Supermercato.setText(c.getString(3));
                Quantita.setText(c.getString(4));
                Scadenza.setText(c.getString(7));
            }
        } else if(nome.equals("Ente")){
            Quantita1.setVisibility(View.INVISIBLE);
            Quantita2.setVisibility(View.INVISIBLE);
            BAcquisto.setVisibility(View.INVISIBLE);
            Log.d("Invoking","ente");
            y = (Integer[]) i.getExtras().get("Id");
            //Log.d("PROVA",y[2].toString());
            IdObj = y[0];
            stato = y[1];
            qnt=y[2];
            Cursor c = db.ottieniProdottiById(IdObj);
            if (c.moveToFirst()) {
                Nome.setText(c.getString(1));
                Marca.setText(c.getString(2));
                Supermercato.setText(c.getString(3));
                Quantita.setText(qnt.toString());
                Scadenza.setText(c.getString(7));
                if (stato != 0) {
                    Stato.setText("Consegnato");
                } else {
                    Stato.setText("Non consegnato");
                }
            }
        }
        if (nome.equals("Ricerca")){
            Stato.setVisibility(View.INVISIBLE);
            Stato2.setVisibility(View.INVISIBLE);
            IdObj=i.getExtras().getInt("Id");
            Cursor c = db.ottieniProdottiById(IdObj);
            if (c.moveToFirst()) {
                Nome.setText(c.getString(1));
                Marca.setText(c.getString(2));
                Supermercato.setText(c.getString(3));
                Quantita.setText(c.getString(4));
                Scadenza.setText(c.getString(7));//TODO FINIRE ORDINE ENTE
            }
            //db.insertPrelevati(x,"",Integer.parseInt(Quantita2.getText().toString()));
           // db.preleva(x,Integer.parseInt(Quantita2.getText().toString()),i.getExtras().getString("Ente"));


        }
        db.close();
    }

    public void Ordina(View v){
        db.open();
        Qnt=Integer.parseInt(Quantita2.getText().toString());
        db.preleva(IdObj,Qnt,Ente);
        db.close();
    }
}
