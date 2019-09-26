package com.example.antonio.progetto.supermercato;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.R;


import java.util.Vector;

/**
 * Created by Antonio on 15/05/2017.
 */

public class AggiungiProdottiSup extends AppCompatActivity{

    EditText NomeProdotto,GiornoScadenza,MeseScadenza,AnnoScadenza,LottoProdotto, QuantitaProdotto,MarcaProdotto;
    Button BCarica;
    private GestioneDB db;
    private  String sup;
    private String path;
    private static final int IMAGE_PICK=100;
    Uri imageUri;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aggiungi_prodotto_supermercato);
        db= new GestioneDB(this);
        path=null;

        NomeProdotto = (EditText) findViewById(R.id.nome_nuovo_prod);
        GiornoScadenza= (EditText) findViewById(R.id.giorno_nuovo_prod);
        MeseScadenza= (EditText) findViewById(R.id.mese_nuovo_prod);
        AnnoScadenza=(EditText) findViewById(R.id.anno_nuovo_prod);
        BCarica= (Button) findViewById(R.id.b_aggiungi_nuovo_prod_activity);


        LottoProdotto=(EditText) findViewById(R.id.lotto_nuovo_prod);
        QuantitaProdotto= (EditText) findViewById(R.id.quantita_nuovo_prod);
        MarcaProdotto=(EditText) findViewById(R.id.marca_nuovo_prod);
        NomeProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NomeProdotto.getText().toString().equals("Nome"))
                    NomeProdotto.setText("");
            }
        });
        GiornoScadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (GiornoScadenza.getText().toString().equals("gg"))
                    GiornoScadenza.setText("");
            }
        });
        MeseScadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MeseScadenza.getText().toString().equals("mm"))
                    MeseScadenza.setText("");
            }
        });
        AnnoScadenza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AnnoScadenza.getText().toString().equals("aaaa"))
                    AnnoScadenza.setText("");
            }
        });
        LottoProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LottoProdotto.getText().toString().equals("Lotto"))
                    LottoProdotto.setText("");
            }
        });
        QuantitaProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(QuantitaProdotto.getText().toString().equals("Quantità"))
                    QuantitaProdotto.setText("");
            }
        });
        MarcaProdotto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MarcaProdotto.getText().toString().equals("Marca"))
                    MarcaProdotto.setText("");
            }
        });
        Intent k= getIntent();
        sup=k.getExtras().getString("Supermercato");
    }

    public  void onClickAggiungi(View v) {


        String nome = NomeProdotto.getText().toString().toLowerCase();
       String m= db.correggiData(MeseScadenza.getText().toString());
        String g= db.correggiData(GiornoScadenza.getText().toString());
        Integer quantita = Integer.parseInt(QuantitaProdotto.getText().toString());
        String lotto = LottoProdotto.getText().toString().toLowerCase();
        String marca = MarcaProdotto.getText().toString().toLowerCase();
        Integer Check= db.checkData(g,m,AnnoScadenza.getText().toString());
        String data = g + "-" +m + "-" + AnnoScadenza.getText().toString();

        if(!nome.equals(null)&&!nome.equals("\\s+")&&!marca.equals(null)&&!marca.equals("\\s+")&&!data.equals(null)&&!data.equals("\\s+")&&quantita!=0&&Check==1){
            db.open();
            db.insertProdotto(nome,marca,sup,quantita,lotto,null,data,null);
            Toast.makeText(this,"Inserimento riuscito ",Toast.LENGTH_SHORT).show();
            NomeProdotto.setText("Nome");
            GiornoScadenza.setText("gg");
            MeseScadenza.setText("mm");
            AnnoScadenza.setText("aaaa");
            QuantitaProdotto.setText("Quantità");
            LottoProdotto.setText("Lotto");
            MarcaProdotto.setText("Marca");
            db.close();
        }else {
            Toast.makeText(this,"Inserire tutti i dati correttamente",Toast.LENGTH_LONG).show();
            Log.d("datisba",nome+marca+data+quantita+Check.toString());
        }
        //db.insertProdotto(nome,marca,sup,quantita,lotto,null,data,null);
      // Cursor c =db.ottieniProdottiByNome(nome,marca);
        //Toast.makeText(getApplicationContext(),c.getString(1).toString()+ " "+ c.getString(2).toString() , Toast.LENGTH_SHORT).show();

    }



}
