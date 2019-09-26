package com.example.antonio.progetto;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.antonio.progetto.ente.MainEnte;
import com.example.antonio.progetto.supermercato.MainSupermercato;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;


public class MainActivity extends AppCompatActivity {

    Button BLogin, BRegistrazione;
    EditText TUser, TPass;
    RadioButton RSupermercato,REnte;
    RadioGroup RGroup;
    private GestioneDB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //inizializzazione DB
        db = new GestioneDB(this);

        try {
            String destPath = "/data/data/" + getPackageName() + "/databases";
            File f = new File(destPath);
            if (!f.exists()) {
                f.mkdirs();
                f.createNewFile();
                CopyDB(getBaseContext().getAssets().open("progetto.db"), new FileOutputStream(destPath + "/progetto.db"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BLogin = (Button) findViewById(R.id.login);
        BRegistrazione = (Button) findViewById(R.id.Registrazione);
        TUser = (EditText) findViewById(R.id.Email);
        TPass = (EditText) findViewById(R.id.Password);
        REnte=(RadioButton) findViewById(R.id.ente_main_acticity);
        RSupermercato=(RadioButton) findViewById(R.id.sup_main_activity);
        RGroup=(RadioGroup) findViewById(R.id.radioGroup_main);
     /*   TUser.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (TUser.getText().toString().equals("Email")) {
                    TUser.setText("");
                }
            }
        });*/
/*
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(TUser.getRootView().getWindowToken(), 0);
       // imm.hideSoftInputFromWindow(TPass.getWindowToken(),0);
  */      TUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TUser.getText().toString().equals("Email")) {
                    TUser.setText("");

                }
             //   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
               // imm.showSoftInput(TUser, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        RGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(REnte.isChecked()){
                    RSupermercato.setChecked(false);
                }else if(RSupermercato.isChecked()){
                    REnte.setChecked(false);
                }
            }
        });

       // long x = db.inserisciEnti(null, null, null, null, "deco2", "cacca");

       // db.deleteProdotti();
        db.open();
        //Cursor c=db.ottieniEnti();
      //  Toast.makeText(this,c.getCount(),Toast.LENGTH_LONG).show();
        /*Cursor ca=db.listaInseriti();
        if (ca.moveToFirst())
        {
            do {
                Toast.makeText(this,
                        "id: " + ca.getString(0) + "\n" +
                                "Nome: " + ca.getString(1) + "\n" +
                                "Indirizzo: " + ca.getString(2)+ca.getString(3),
                        Toast.LENGTH_LONG).show();
            } while (ca.moveToNext());
        }
    */   // Toast.makeText(getApplicationContext(),ca.getString(5),Toast.LENGTH_LONG).show();
    }

    public void Registrazione(View v){
        Intent i= new Intent(this, Registrazione.class);
        db.close();
        startActivity(i);
    }

    public void Accesso (View v){
        String user= TUser.getText().toString();
        String pass= TPass.getText().toString();
        /*
        Controllo con base di dati
        if(isOk){
        Intent i = new Intent(this, Classe);
        startActivity(i);
        }
        else{
           Toast.makeText(getApplicationContext(),"Password o Email errate", Toast.LENGTH_LONG);
           }
         */
        //TODO Se è un supermercato va a mainsupermercato e passa con extras l'utente, la stessa cosa per ente solo che cambia con mainente
        if(RSupermercato.isChecked()) {
            db.open();
            Cursor c = db.ottieniSupermercaticonUtente(user);

            if (c.moveToFirst() && pass.equals(c.getString(5))) {
                Intent i = new Intent(this, MainSupermercato.class);
                i.putExtra("Supermercato", user);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Password o Utente errato", Toast.LENGTH_LONG).show();
            }
            db.close();
        }else if(REnte.isChecked()){
            db.open();
            Cursor c = db.ottieniEnticonUtente(user);

            if (c.moveToFirst() && pass.equals(c.getString(5))) {
                Intent i = new Intent(this, MainEnte.class);
                i.putExtra("Ente", user);
                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Password o Utente errato", Toast.LENGTH_LONG).show();
            }
            db.close();
        }else{
            Toast.makeText(this,"Seleziona una delle due scelte",Toast.LENGTH_LONG).show();
        }
    }
    public void CopyDB(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }
        inputStream.close();
        outputStream.close();
    }

    public void onBackPressed(){
        super.onBackPressed();
        db.close();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }
}
