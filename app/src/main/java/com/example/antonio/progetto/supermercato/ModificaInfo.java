package com.example.antonio.progetto.supermercato;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.antonio.progetto.R;

/**
 * Created by Antonio on 13/05/2017.
 */

public class ModificaInfo extends AppCompatActivity {

    EditText nome, indirizzo, provincia, comune;
    Button BAggiorna;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nome= (EditText) findViewById(R.id.info_nome);
        indirizzo=(EditText) findViewById(R.id.info_indirizzo);
        provincia= (EditText) findViewById(R.id.info_Provincia);
        comune= (EditText) findViewById(R.id.info_Comune);
        BAggiorna= (Button) findViewById(R.id.B_aggiorna_info);
    }

    public void AggiornaInfo(View v){
        //Db.insert(nome.getText().toString(),....);//TODO aggiungere insert
        Toast.makeText(getApplicationContext(),"Modifica riuscita", Toast.LENGTH_SHORT);
    }
}