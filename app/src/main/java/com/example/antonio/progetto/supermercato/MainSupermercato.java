package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;

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
        aggiungi= (Button) findViewById(R.id.b_aggiungi_prod_sup);
        mod_info=(Button) findViewById(R.id.b_info_sup);
        lista=(ListView) findViewById(R.id.Lista_prodotti);
        CreaLista(lista);
    }

    public void CreaLista(ListView lista){

        //vettore di stringe che ospiterà elementi dalla query come nell'esempio sotto
        String[] prodotti= {};

        /*
        for(int i=0;i<numero di tuple;i++){
            prodotti{i]=Db.select;
        }

         */
        //lista dal vettore e suo adapter che poi si vanno a legare alla Listview
        final List<String> prodotti_list= new ArrayList<String>(Arrays.asList(prodotti));
       final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prodotti_list);
        lista.setAdapter(adapter);

    }

    public  void onListItemClick(ListView parent, View v, int position, long id){
        Intent i= new Intent();//aggiungi roba al costruttore
        //TODO impostare cosa succede quando si clicca su un elelemnto della lista, bisogna passare gli elementi ad un nuovo intent tramite metodo parcelable ( macello)
    }

    public void onClickInfo(View v){
        Intent i= new Intent(this,ModificaInfo.class);
        startActivity(i);
    }
}
