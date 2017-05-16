package com.example.antonio.progetto.supermercato;

import android.content.Intent;
import android.database.Cursor;
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

import com.example.antonio.progetto.GestioneDB;
import com.example.antonio.progetto.MainActivity;
import com.example.antonio.progetto.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 * Created by Antonio on 13/05/2017.
 */

public class MainSupermercato extends AppCompatActivity {

    Button aggiungi, mod_info;
    ListView lista;
    private GestioneDB db;
    private String sup;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_supermercato);
        db=new GestioneDB(this);
        aggiungi= (Button) findViewById(R.id.b_aggiungi_prod_sup);
        mod_info=(Button) findViewById(R.id.b_info_sup);
        lista=(ListView) findViewById(R.id.Lista_prodotti);
        //Creo lista con adapter per aggiungere elemento si usa prodotti_list.add(numero, elemento);
        String[] prodotti= {};
        final List<String> prodotti_list= new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prodotti_list);
        lista.setAdapter(adapter);
        Intent k=getIntent();
        sup=k.getExtras().getString("Supermercato");
        Cursor cursor=db.listaInseritiSupermercato(sup);

        /*HashMap<Integer,Vector<String>> mappa= new HashMap<Integer, Vector<String>>();
        int i=0;

        Vector<String> vett= new Vector<String>();
        if (cursor.moveToFirst())
        {
            do {
                for(int j=0;j<cursor.getColumnCount();j++){
                    vett.add(j,cursor.getString(j));
                }
                mappa.put(i,vett);
                i++;

            } while (cursor.moveToNext());
        }
*/
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
        prodotti_list.add(0,"Passata di Pomodori Marca: Mutti Quantià: 3 Data di scadenza: 20/07/2017");
        prodotti_list.add(1,"Tonno Marca: Rio Mare Quantià: 300 Data di scadenza: 20/08/2018  ");
        Vector<String> prova= new Vector<String>();
       // prova= mappa.get(0);
        //prodotti_list.add(2,prova.firstElement()+prova.lastElement()+prova.get(5));

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
        i.putExtra("Supermercato",sup);
        startActivity(i);
    }
}
