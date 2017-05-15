package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.antonio.progetto.MainActivity;
import com.example.antonio.progetto.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Antonio on 13/05/2017.
 */

public class MainSupermercato extends AppCompatActivity {

    Button aggiungi, mod_info;
    ListView lista;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_supermercato);
        aggiungi= (Button) findViewById(R.id.b_aggiungi_prod_sup);
        mod_info=(Button) findViewById(R.id.b_info_sup);
        lista=(ListView) findViewById(R.id.Lista_prodotti);
        //Creo lista con adapter per aggiungere elemento si usa prodotti_list.add(numero, elemento);
        String[] prodotti= {};
        final List<String> prodotti_list= new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prodotti_list);
        lista.setAdapter(adapter);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  lista.getItemAtPosition(position);
                String obj= o.toString();
                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainSupermercato.this,DescrizioneProdotto.class);
                i.putExtra("Nome",obj);
                startActivity(i);
            }
        });
        prodotti_list.add(0,"ciao");
        prodotti_list.add(1,"hello");

    }


    public void onClickInfo(View v){
        Intent i= new Intent(this,ModificaInfo.class);
        startActivity(i);
    }
    public void log_out_sup(View v){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }

    public void onClickCambiaAggiungi (View v){
        Intent i= new Intent(this,AggiungiProdottiSup.class);
        startActivity(i);
    }
}
