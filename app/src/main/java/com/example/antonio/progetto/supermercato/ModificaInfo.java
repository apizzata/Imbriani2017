package com.example.antonio.progetto.supermercato;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modifica_info);
        db= new GestioneDB(this);
        sup=getIntent().getExtras().getString("Supermercato");
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

        Toast.makeText(getApplicationContext(),"Modifica riuscita", Toast.LENGTH_SHORT);
    }
}