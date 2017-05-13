package com.example.antonio.progetto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    Button BLogin, BRegistrazione;
    EditText TUser, TPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BLogin = (Button) findViewById(R.id.login);
        BRegistrazione= (Button) findViewById(R.id.Registrazione);
        TUser =(EditText) findViewById(R.id.Email);
        TPass = ( EditText) findViewById(R.id.Password);
    }

    public void Registrazione(View v){
        Intent i= new Intent(this, Registrazione.class);
        startActivity(i);
    }

    public void Accesso (View v){
        String user= TUser.getText().toString();
        String pass= TPass.getText().toString();
        /*
        Controllo con base di dati  //TODO Aggiungere controllo base dati
        if(isOk){
        Intent i = new Intent(this, Classe);
        startActivity(i);
        }
        else{
           Toast.makeText(getApplicationContext(),"Password o Email errate", Toast.LENGTH_LONG);
           }
         */

    }

}
