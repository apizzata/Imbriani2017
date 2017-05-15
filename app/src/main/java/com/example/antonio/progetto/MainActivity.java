package com.example.antonio.progetto;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.antonio.progetto.ente.MainEnte;
import com.example.antonio.progetto.supermercato.MainSupermercato;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    Button BLogin, BRegistrazione;
    EditText TUser, TPass;
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


        TUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TUser.getText().toString().equals("Email")) {
                    TUser.setText("");
                }
            }
        });
        db.open();
        long x = db.inserisciEnti(null, null, null, null, "deco2", "cacca");
      //  Cursor ca=db.ottieniEnticonUtente("deco2");
      //  Toast.makeText(getApplicationContext(),ca.getString(5),Toast.LENGTH_LONG).show();
    }
    public void Registrazione(View v){
        Intent i= new Intent(this, Registrazione.class);
        startActivity(i);
    }

    public void Accesso (View v){
        String user= TUser.getText().toString();
        String pass= TPass.getText().toString();
        /*
        Controllo con base di dati  //TODO aggiungere radiobutton Ente, Supermercato
        if(isOk){
        Intent i = new Intent(this, Classe);
        startActivity(i);
        }
        else{
           Toast.makeText(getApplicationContext(),"Password o Email errate", Toast.LENGTH_LONG);
           }
         */

        Cursor c= db.ottieniEnticonUtente(user);

        if(c.moveToFirst()&&pass.equals(c.getString(5))){
            Intent i = new Intent(this, MainSupermercato.class);
            startActivity(i);
        } else {
            Toast.makeText(getApplicationContext(),"Password o Utente errato",Toast.LENGTH_LONG).show();
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

}
