package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.antonio.progetto.R;

/**
 * Created by Antonio on 15/05/2017.
 */

public class DescrizioneProdotto extends AppCompatActivity {

    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descrizione_prodotto);
        txt=(TextView) findViewById(R.id.descr_obj);
        Intent i= getIntent();
        String dato= i.getExtras().getString("Nome");
        txt.setText(dato);
    }
}
