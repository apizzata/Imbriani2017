package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.antonio.progetto.R;

import org.w3c.dom.Text;

/**
 * Created by Antonio on 15/05/2017.
 */

public class DescrizioneProdotto extends AppCompatActivity {

    TextView Nome,Scadenza,Marca,Quantita,Supermercato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_prodotto);
        Nome=(TextView) findViewById(R.id.nome_obj_descr);
        Scadenza=(TextView) findViewById(R.id.scadenza_obj_desscr);
        Marca=(TextView) findViewById(R.id.marca_obj_descr);
        Quantita=(TextView) findViewById(R.id.quantita_obj_descr);
        Supermercato=(TextView) findViewById(R.id.sup_obj_descr);
        Intent i= getIntent();
        String dato= i.getExtras().getString("Nome");
        Nome.setText(dato);
    }
}
