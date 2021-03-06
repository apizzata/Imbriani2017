package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.R;

/**
 * Created by Antonio on 13/05/2017.
 */

public class ModificaInfo extends AppCompatActivity {

    EditText nome, indirizzo, provincia, comune;
    Button BAggiorna;
    GestioneDB db;
    String sup;
    String utente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifica_info);
        db= new GestioneDB(this);
        sup=getIntent().getExtras().getString("Supermercato");
        utente=getIntent().getExtras().getString("Invoking");
        nome= (EditText) findViewById(R.id.info_nome);
        indirizzo=(EditText) findViewById(R.id.info_indirizzo);
        provincia= (EditText) findViewById(R.id.info_Provincia);
        comune= (EditText) findViewById(R.id.info_Comune);
        BAggiorna= (Button) findViewById(R.id.B_aggiorna_info);

        nome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nome.getText().toString().equals("Nome"))
                    nome.setText("");
            }
        });
        indirizzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (indirizzo.getText().toString().equals("Indirizzo"))
                    indirizzo.setText("");
            }
        });
        provincia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (provincia.getText().toString().equals("Provincia"))
                    provincia.setText("");
            }
        });
        comune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (comune.getText().toString().equals("Comune"))
                    comune.setText("");
            }
        });
    }

    public void AggiornaInfo(View v){
        //Db.insert(nome.getText().toString(),....);//TODO aggiungere insert
        db.open();
        String nome_=nome.getText().toString();
        String indirizzo_=indirizzo.getText().toString();
        String provincia_=provincia.getText().toString();
        String comune_=comune.getText().toString();
        Log.d("AGGIORNAA","ciao1");
        if(utente.equals("Ente")){
            Log.d("AGGIORNAA","ciao2");
            db.modificaEnte(sup,nome_,indirizzo_,comune_,provincia_);
            Toast.makeText(getApplicationContext(),"Modifica riuscita", Toast.LENGTH_SHORT).show();
            Log.d("AGGIORNAA","ciao3");
        }else{
            Log.d("AGGIORNAA","ciao4");
            db.modificaSupermercato(sup,nome_,indirizzo_,comune_,provincia_);
            Toast.makeText(getApplicationContext(),"Modifica riuscita", Toast.LENGTH_SHORT).show();
            Log.d("AGGIORNAA","ciao5");
        }
        db.close();
    }
}