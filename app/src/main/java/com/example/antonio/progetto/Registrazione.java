package com.example.antonio.progetto;



import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by Antonio on 13/05/2017.
 */

public class Registrazione extends AppCompatActivity {
    EditText user, pass;
    RadioButton sup,ente;
    RadioGroup group;
    Button reg;
    private GestioneDB db;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_form);
        db= new GestioneDB(this);

        user= (EditText) findViewById(R.id.registration_user);
        pass= (EditText) findViewById(R.id.registration_password);
        sup= (RadioButton) findViewById(R.id.radio_button_sup);
        ente =(RadioButton) findViewById(R.id.radio_button_ente);
        reg=(Button) findViewById(R.id.B_registrazione_form);
        group=(RadioGroup) findViewById(R.id.Radio_group_reg);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if(ente.isChecked()){
                    sup.setChecked(false);
                }else if(sup.isChecked()){
                    ente.setChecked(false);
                }
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString().equals("Username"))
                    user.setText("");
            }
        });


    }
    public void onBackPressed(){
        super.onBackPressed();
        db.close();
    }
    public void Registra(View v){
        String User= user.getText().toString();
        String Pass= pass.getText().toString();
        db.open();
        if(sup.isChecked()){
            //registra tra i supp //TODO aggiungere insert
            db.insertSupermercato(null,null,null,null,User,Pass);
            db.close();
            Toast.makeText(getApplicationContext(),"Registrazione riuscita", Toast.LENGTH_SHORT).show();
        }else if (ente.isChecked() ){
            //registra tra gli enti //TODO aggiungere insert
            db.inserisciEnti(null,null,null,null,User,Pass);
            db.close();
            Toast.makeText(getApplicationContext(),"Registrazione riuscita", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Seleziona una tra le due scelte", Toast.LENGTH_LONG).show();
        }
    }

}
