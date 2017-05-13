package com.example.antonio.progetto;



import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * Created by Antonio on 13/05/2017.
 */

public class Registrazione extends AppCompatActivity {
    EditText user, pass;
    RadioButton sup,ente;
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user= (EditText) findViewById(R.id.registration_user);
        pass= (EditText) findViewById(R.id.registration_password);
        sup= (RadioButton) findViewById(R.id.radio_button_sup);
        ente =(RadioButton) findViewById(R.id.radio_button_ente);
    }

    public void Registra(View v){
        String User= user.getText().toString();
        String Pass= pass.getText().toString();
        if(sup.isChecked()&&!ente.isChecked()){
            //registra tra i supp //TODO aggiungere insert
        }else if (ente.isChecked() && !sup.isChecked()){
            //registra tra gli enti //TODO aggiungere insert
        }else if(sup.isChecked() && ente.isChecked()){
            Toast.makeText(getApplicationContext(),"Devi selezionare solo uno tra i due",Toast.LENGTH_LONG);
        }else{
            Toast.makeText(getApplicationContext(),"Seleziona una tra le due scelte", Toast.LENGTH_LONG);
        }
    }
}
