package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.R;

import java.io.File;

/**
 * Created by Antonio on 15/05/2017.
 */

public class DescrizioneProdotto extends AppCompatActivity {

    TextView Nome,Scadenza,Marca,Quantita,Supermercato,Quantita2,Quantita1,ModQuantita;
    Button BAcquisto,BDEnte,BDSup,BMod;
    private GestioneDB db;

    final String path = Environment.DIRECTORY_DOWNLOADS;

    private Integer Qnt;
    private String Ente;
    private Integer IdObj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_prodotto);
        db = new GestioneDB(this);


        Integer[] y;
        Integer qnt;
        db.open();
        Nome = (TextView) findViewById(R.id.nome_obj_descr);
        Scadenza = (TextView) findViewById(R.id.scadenza_obj_desscr);
        Marca = (TextView) findViewById(R.id.marca_obj_descr);
        Quantita = (TextView) findViewById(R.id.quantita_obj_descr);
        Supermercato = (TextView) findViewById(R.id.sup2_obj_descr);
        BAcquisto=(Button) findViewById(R.id.ordina_obj_descr);
        Quantita2=(TextView) findViewById(R.id.qnt3_obj_descr);
        Quantita1=(TextView) findViewById(R.id.qnt2_obj_descr);
        BDEnte=(Button) findViewById(R.id.b_delete_ente);
        BDSup= (Button) findViewById(R.id.b_delete_sup);
        BMod=(Button) findViewById(R.id.b_mod_quantita);
        ModQuantita=(TextView) findViewById(R.id.mod_quantita);
        ModQuantita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ModQuantita.getText().toString().equals("Nuova Quantità"))
                    ModQuantita.setText("");
            }
        });
        Intent i = getIntent();
        Ente=i.getExtras().getString("Ente").toString();
        //String[] dati=i.getExtras().getString("Nome").split("\\s+");
        String nome= i.getExtras().getString("Invoking").toString();

        if (nome.equals("Supermercato")) {
            Quantita2.setVisibility(View.INVISIBLE);
            Quantita1.setVisibility(View.INVISIBLE);
            BAcquisto.setVisibility(View.INVISIBLE);
            BDEnte.setVisibility(View.INVISIBLE);
            Log.d("Invoking","sup");
            IdObj = i.getExtras().getInt("Id");
            Log.d("IDDD",IdObj.toString());
            Cursor c = db.ottieniProdottiById(IdObj);
           // Log.d("IDDDD",c.getString(0 ));
            if (c.moveToFirst()) {
                Log.d("query123",c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+" "+c.getString(4)+" "+c.getString(5)+" "+c.getString(6)+" "+c.getString(7)+" "+c.getString(8));
                Nome.setText(c.getString(1));
                Marca.setText(c.getString(2));
                Supermercato.setText(c.getString(3));
                Quantita.setText(c.getString(4));
                Scadenza.setText(c.getString(7));

            }
        } else if(nome.equals("Ente")){
            BDSup.setVisibility(View.INVISIBLE);
            BAcquisto.setVisibility(View.INVISIBLE);
            Quantita1.setVisibility(View.INVISIBLE);
            Quantita2.setVisibility(View.INVISIBLE);
            BMod.setVisibility(View.INVISIBLE);
            ModQuantita.setVisibility(View.INVISIBLE);
            Log.d("Invoking","ente");
            y = (Integer[]) i.getExtras().get("Id");
            //Log.d("PROVA",y[2].toString());
            IdObj = y[0];

            qnt=y[1];
            Cursor c = db.ottieniProdottiById(IdObj);
            if (c.moveToFirst()) {
                Nome.setText(c.getString(1));
                Marca.setText(c.getString(2));
                Supermercato.setText(c.getString(3));
                Quantita.setText(qnt.toString());
                Scadenza.setText(c.getString(7));

            }
        }
        if (nome.equals("Ricerca")){
            BDEnte.setVisibility(View.INVISIBLE);
            BMod.setVisibility(View.INVISIBLE);
            BDSup.setVisibility(View.INVISIBLE);
            ModQuantita.setVisibility(View.INVISIBLE);
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
        try {
            Qnt = Integer.parseInt(Quantita2.getText().toString());

            if (Qnt == 0) {
                Toast.makeText(this, "Inserire una quantità", Toast.LENGTH_SHORT).show();
            } else {
                db.preleva(IdObj, Qnt, Ente);
            }

            db.close();
        }catch (NumberFormatException e){
            Toast.makeText(this,"Inserire un quantità",Toast.LENGTH_SHORT).show();
        }
    }

    public void EliminaSup(View v){
        db.open();
        db.EliminaProdSupermercato(IdObj);
        Toast.makeText(this,"Prodotto eliminato",Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void EliminaEnte(View v){
        db.open();
        db.AggiungiProdottoDaEnte(IdObj);
        Toast.makeText(this,"Ordine annullato",Toast.LENGTH_SHORT).show();
        db.close();
    }
    public void ModificaQuantita(View v){
        db.open();
        try {
            Integer x = Integer.parseInt(ModQuantita.getText().toString());
            db.ModificaQuantitaProdotto(IdObj, x);
            Toast.makeText(this, "Modifica riuscita", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(getIntent());
        }catch (NumberFormatException e){
            Toast.makeText(this,"Inserire un quantità",Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


}
