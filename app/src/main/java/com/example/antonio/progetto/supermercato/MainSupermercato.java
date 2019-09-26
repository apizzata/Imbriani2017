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
    private  Vector<Integer> Id;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_supermercato);
        db=new GestioneDB(this);
        db.open();
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
       // Toast.makeText(getApplicationContext(),cursor.getColumnCount(),Toast.LENGTH_SHORT).show();
      /*  if (cursor.moveToFirst())
        {
            do {
                Toast.makeText(this,
                        "id: " + cursor.getString(0) + "\n" +
                                "Nome: " + cursor.getString(1) + "\n" +
                                "Indirizzo: " + cursor.getString(2)+ "\n" +
                                "Indirizzo: " + cursor.getString(3)+ "\n" +
                                "Indirizzo: " + cursor.getString(4)+ "\n" +
                                "Indirizzo: " + cursor.getString(5)+ "\n" +
                                "Indirizzo: " + cursor.getString(6)+ "\n" +
                                "Indirizzo: " + cursor.getString(7)+ "\n" +
                                "Indirizzo: " + cursor.getString(8)+ "\n",
                        Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
        }
        */

      HashMap<Integer,String> mappa= new HashMap<Integer, String>();
        int i=0;
      //  Vector<String>vett=new Vector<>();
        Id= new Vector<Integer>();

        if (cursor.moveToFirst())
        {
            do {
                    //Toast.makeText(getApplicationContext(),vett.get(j),Toast.LENGTH_SHORT).show();

              //mappa.put(i,cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4)+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getString(7)+" "+cursor.getString(8));
                mappa.put(i,cursor.getString(1)+" "+cursor.getString(2));

                //Toast.makeText(getApplicationContext(),mappa.get(i).get(0)+mappa.get(i).get(1)+mappa.get(i).get(2),Toast.LENGTH_SHORT).show();
               Id.addElement(cursor.getInt(0));
                i++;
            } while (cursor.moveToNext());
        }
       // Toast.makeText(this,mappa.get(0).get(0) + " " + mappa.get(0).get(1) + " " + mappa.get(0).get(2) + " " + mappa.get(0).get(3) + " " + mappa.get(0).get(4) + " " + mappa.get(0).get(5) + " " + mappa.get(0).get(6) + " " + mappa.get(0).get(7) + " " + mappa.get(0).get(8),Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,mappa.get(1).get(0) + " " + mappa.get(1).get(1) + " " + mappa.get(1).get(2) + " " + mappa.get(1).get(3) + " " + mappa.get(1).get(4) + " " + mappa.get(1).get(5) + " " + mappa.get(1).get(6) + " " + mappa.get(1).get(7) + " " + mappa.get(1).get(8),Toast.LENGTH_SHORT).show();


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  lista.getItemAtPosition(position);
                String obj= o.toString();
                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainSupermercato.this,DescrizioneProdotto.class);
               // i.putExtra("Nome",obj);
                i.putExtra("Id",Id.get(position));
                i.putExtra("Ente",sup);
                startActivity(i);
            }
        });
      //  prodotti_list.add(0,"Passata di Pomodori Marca: Mutti Quantià: 3 Data di scadenza: 20/07/2017");
       // prodotti_list.add(1,"Tonno Marca: Rio Mare Quantià: 300 Data di scadenza: 20/08/2018  ");
     //   for(int j=0;j<mappa.size();j++){
       //     Toast.makeText(this,mappa.get(j).get(0) + " " + mappa.get(j).get(1) + " " + mappa.get(j).get(2) + " " + mappa.get(j).get(3) + " " + mappa.get(j).get(4) + " " + mappa.get(j).get(5) + " " + mappa.get(j).get(6) + " " + mappa.get(j).get(7) + " " + mappa.get(j).get(8),Toast.LENGTH_SHORT).show();
        //}
        for(int ka=0;ka<mappa.size();ka++) {
            prodotti_list.add(ka, mappa.get(ka)+Id.get(ka));

        }
        db.close();

        // prodotti_list.add(0,mappa.get(0).get(0) + " " + mappa.get(0).get(1) + " " + mappa.get(0).get(2) + " " + mappa.get(0).get(3) + " " + mappa.get(0).get(4) + " " + mappa.get(0).get(5) + " " + mappa.get(0).get(6) + " " + mappa.get(0).get(7) + " " + mappa.get(0).get(8));
        //prodotti_list.add(1,mappa.get(1).get(0) + " " + mappa.get(1).get(1) + " " + mappa.get(1).get(2) + " " + mappa.get(1).get(3) + " " + mappa.get(1).get(4) + " " + mappa.get(1).get(5) + " " + mappa.get(1).get(6) + " " + mappa.get(1).get(7) + " " + mappa.get(1).get(8));
        //Toast.makeText(this,mappa.get(0).get(0) + " " + mappa.get(0).get(1) + " " + mappa.get(0).get(2) + " " + mappa.get(0).get(3) + " " + mappa.get(0).get(4) + " " + mappa.get(0).get(5) + " " + mappa.get(0).get(6) + " " + mappa.get(0).get(7) + " " + mappa.get(0).get(8),Toast.LENGTH_SHORT).show();

    }

    public void onResume(){
        super.onResume();

        db.open();

        //Creo lista con adapter per aggiungere elemento si usa prodotti_list.add(numero, elemento);

        String[] prodotti= {};
        final List<String> prodotti_list= new ArrayList<String>(Arrays.asList(prodotti));
        final ArrayAdapter<String> adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,prodotti_list);
        lista.setAdapter(adapter);
        Intent k=getIntent();
        sup=k.getExtras().getString("Supermercato");
        Cursor cursor=db.listaInseritiSupermercato(sup);
        // Toast.makeText(getApplicationContext(),cursor.getColumnCount(),Toast.LENGTH_SHORT).show();
       /* if (cursor.moveToFirst())
        {
            do {
                Toast.makeText(this,
                        "id: " + cursor.getString(0) + "\n" +
                                "Nome: " + cursor.getString(1) + "\n" +
                                "Indirizzo: " + cursor.getString(2)+ "\n" +
                                "Indirizzo: " + cursor.getString(3)+ "\n" +
                                "Indirizzo: " + cursor.getString(4)+ "\n" +
                                "Indirizzo: " + cursor.getString(5)+ "\n" +
                                "Indirizzo: " + cursor.getString(6)+ "\n" +
                                "Indirizzo: " + cursor.getString(7)+ "\n" +
                                "Indirizzo: " + cursor.getString(8)+ "\n",
                        Toast.LENGTH_LONG).show();
            } while (cursor.moveToNext());
        }
        */
        HashMap<Integer,String> mappa= new HashMap<Integer, String>();
        int i=0;
        //  Vector<String>vett=new Vector<>();
        Id= new Vector<Integer>();

        if (cursor.moveToFirst())
        {
            do {
                //Toast.makeText(getApplicationContext(),vett.get(j),Toast.LENGTH_SHORT).show();

                //mappa.put(i,cursor.getString(0)+" "+cursor.getString(1)+" "+cursor.getString(2)+" "+cursor.getString(3)+" "+cursor.getString(4)+" "+cursor.getString(5)+" "+cursor.getString(6)+" "+cursor.getString(7)+" "+cursor.getString(8));
                if(cursor.getInt(4)!=0) {
                    mappa.put(i, cursor.getString(1) + " " + cursor.getString(2));

                    //Toast.makeText(getApplicationContext(),mappa.get(i).get(0)+mappa.get(i).get(1)+mappa.get(i).get(2),Toast.LENGTH_SHORT).show();
                    Id.addElement(cursor.getInt(0));
                    i++;
                }
            } while (cursor.moveToNext());
        }
        // Toast.makeText(this,mappa.get(0).get(0) + " " + mappa.get(0).get(1) + " " + mappa.get(0).get(2) + " " + mappa.get(0).get(3) + " " + mappa.get(0).get(4) + " " + mappa.get(0).get(5) + " " + mappa.get(0).get(6) + " " + mappa.get(0).get(7) + " " + mappa.get(0).get(8),Toast.LENGTH_SHORT).show();
        //Toast.makeText(this,mappa.get(1).get(0) + " " + mappa.get(1).get(1) + " " + mappa.get(1).get(2) + " " + mappa.get(1).get(3) + " " + mappa.get(1).get(4) + " " + mappa.get(1).get(5) + " " + mappa.get(1).get(6) + " " + mappa.get(1).get(7) + " " + mappa.get(1).get(8),Toast.LENGTH_SHORT).show();


        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o=  lista.getItemAtPosition(position);
                String obj= o.toString();
                //Toast.makeText(getApplicationContext(),obj,Toast.LENGTH_LONG).show();
                Intent i=new Intent(MainSupermercato.this,DescrizioneProdotto.class);
                // i.putExtra("Nome",obj);
                i.putExtra("Invoking","Supermercato");
                i.putExtra("Id",Id.get(position));
                i.putExtra("Ente",sup);
                startActivity(i);
            }
        });
        //  prodotti_list.add(0,"Passata di Pomodori Marca: Mutti Quantià: 3 Data di scadenza: 20/07/2017");
        // prodotti_list.add(1,"Tonno Marca: Rio Mare Quantià: 300 Data di scadenza: 20/08/2018  ");
        //   for(int j=0;j<mappa.size();j++){
        //     Toast.makeText(this,mappa.get(j).get(0) + " " + mappa.get(j).get(1) + " " + mappa.get(j).get(2) + " " + mappa.get(j).get(3) + " " + mappa.get(j).get(4) + " " + mappa.get(j).get(5) + " " + mappa.get(j).get(6) + " " + mappa.get(j).get(7) + " " + mappa.get(j).get(8),Toast.LENGTH_SHORT).show();
        //}
        for(int ka=0;ka<mappa.size();ka++) {
            prodotti_list.add(ka, mappa.get(ka));

        }

        db.close();
        // prodotti_list.add(0,mappa.get(0).get(0) + " " + mappa.get(0).get(1) + " " + mappa.get(0).get(2) + " " + mappa.get(0).get(3) + " " + mappa.get(0).get(4) + " " + mappa.get(0).get(5) + " " + mappa.get(0).get(6) + " " + mappa.get(0).get(7) + " " + mappa.get(0).get(8));
        //prodotti_list.add(1,mappa.get(1).get(0) + " " + mappa.get(1).get(1) + " " + mappa.get(1).get(2) + " " + mappa.get(1).get(3) + " " + mappa.get(1).get(4) + " " + mappa.get(1).get(5) + " " + mappa.get(1).get(6) + " " + mappa.get(1).get(7) + " " + mappa.get(1).get(8));
        //Toast.makeText(this,mappa.get(0).get(0) + " " + mappa.get(0).get(1) + " " + mappa.get(0).get(2) + " " + mappa.get(0).get(3) + " " + mappa.get(0).get(4) + " " + mappa.get(0).get(5) + " " + mappa.get(0).get(6) + " " + mappa.get(0).get(7) + " " + mappa.get(0).get(8),Toast.LENGTH_SHORT).show();

    }

    public void onClickInfo(View v){
        Intent i= new Intent(this,ModificaInfo.class);
        i.putExtra("Supermercato",sup);
        i.putExtra("Invoking","Supermercato");
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
