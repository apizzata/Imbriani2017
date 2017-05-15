package com.example.antonio.progetto;

/**
 * Created by Domenico on 15/05/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class GestioneDB {

    /*
    Definisco una serie di costanti
    */
    static final String KEY_RIGAID = "_id";
    static final String KEY_NOME = "nome";
    static final String KEY_INDIRIZZO = "indirizzo";
    static final String TAG = "GestioneDB";
    static final String DATABASE_NOME = "Progetto";
    static final String DATABASE_TABELLA = "ENTI";
    static final int DATABASE_VERSIONE = 1;

    /*
    Creo una costante contenente la query per la creazione del database
    */
    static final String DATABASE_CREAZIONE = "create table ENTI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL));";

    final Context context;
    DatabaseHelper DBHelper;
    SQLiteDatabase db;

    /*
    Costruttore
    */
    public GestioneDB(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    /*
    Estendo la classe SQLiteOpenHelper che si occupa
    della gestione delle connessioni e della creazione del DB
    */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            // invoco il costruttore della classe base
            super(context, DATABASE_NOME, null, DATABASE_VERSIONE);
        }
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREAZIONE);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }

    /*
    Apro la connessione al DB
    */
    public GestioneDB open() throws SQLException
    {
        // ottengo accesso al DB anche in scrittura
        db = DBHelper.getWritableDatabase();
        return this;
    }

    /*
    Chiudo la connessione al DB
    */
    public void close()
    {
        // chiudo la connessione al DB
        DBHelper.close();
    }

    /*
    Inserimento di un nuovo cliente nella tabella
    */
    public long inserisciEnti(String nome, String indirizzo, String comune, String provincia, String email,String pass)
    {
        // creo una mappa di valori
        ContentValues values = new ContentValues();
        values.put( "NOME", nome );
        values.put( "INDIRIZZO", indirizzo );
        values.put( "COMUNE", comune );
        values.put( "PROVINCIA", provincia );
        values.put( "EMAIL", email);
        values.put( "PASSWORD", pass);
        // applico il metodo insert
        return db.insert("ENTI", null, values);
    }

    public Cursor ottieniEnti(){
        return db.query(DATABASE_TABELLA,new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, null, null, null, null, null);
    }
    public Cursor ottieniEnticonUtente(String utente){
        Cursor mCursore = db.query(true, DATABASE_TABELLA, new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, "EMAIL ="+utente, null, null, null, null, null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;

    }

    public long insertSupermercato(String nome, String indirizzo, String comune, String provincia,  String email,String pass) {
        ContentValues values = new ContentValues();
        values.put( "NOME", nome );
        values.put( "INDIRIZZO", indirizzo );
        values.put( "COMUNE", comune );
        values.put( "PROVINCIA", provincia );
        values.put( "EMAIL", email);
        values.put( "PASSWORD", pass);
        return db.insert("supermercati", null, values);
    }

    public long insertProdotto(String nome, String quantita, String provenienza, String scadenza , String tipo, String lotto, String foto, String marca  ) {
        ContentValues values = new ContentValues();
        values.put( "nome", nome );
        values.put( "quantita", quantita );
        values.put( "provenienza", provenienza );
        values.put( "scadenza", scadenza);
        values.put( "tipo", tipo);
        values.put( "lotto", lotto);
        values.put( "foto", "ND");
        values.put( "marca", marca);
        return db.insertOrThrow("PRODOTTI", null, values);
    }

    public Cursor ottieniSupermercati()
    {
        return db.query("supermercati",new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, null, null, null, null, null);
    }
    public Cursor ottieniSupermercaticonUtente(String utente)
    {
        return db.query("supermercati",new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, "EMAIL ="+utente, null, null, null, null);
    }

}