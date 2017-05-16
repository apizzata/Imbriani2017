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
import android.widget.Toast;

import java.util.Vector;

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
    static final String DATABASE_ENTI = "create table ENTI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL)); ";
    static final String DATABASE_SUPERMERCATI = "create table SUPERMERCATI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL)); ";
    static final String DATABASE_PRODOTTI = "create table PRODOTTI (ID INTEGER AUTO_INCREMENT , NOME VARCHAR(50), MARCA VARCHAR(50), SUPERMERCATO_E VARCHAR(50), QUANTITA INTEGER,LOTTO VARCHAR(30), TIPO VARCHAR(30), DATA_SCADENZA VARCHAR(30), FOTO VARCHAR(100),PRIMARY KEY(ID)); ";
    static final String DATABASE_MESSAGGI = "create table MESSAGGI (SUPERMERCATO VARCHAR(30), ENTE VARCHAR(50), MESSAGGIO TEXT); ";
    static final String DATABASE_PRESI = "create table PRELEVATI (ENTE VARCHAR(30), PRODOTTO VARCHAR(30), QUANTITA INTEGER); ";
    static final String DATABASE_INSERITI = "create table INSERITI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL)); ";

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
                db.execSQL(DATABASE_PRODOTTI);
                db.execSQL(DATABASE_ENTI);
                db.execSQL(DATABASE_SUPERMERCATI);

                db.execSQL(DATABASE_PRESI);
                db.execSQL(DATABASE_MESSAGGI);
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
        //Cursor mCursore = db.query(true, DATABASE_TABELLA, new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, "EMAIL ="+utente, null, null, null, null, null);
        Cursor mCursore= db.rawQuery("SELECT * FROM ENTI WHERE EMAIL = '"+utente+"'", null);
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
        return db.insert("SUPERMERCATI", null, values);
    }

    public long insertProdotto(String nome, String marca, String supermercato , Integer quantita, String lotto, String tipo, String scadenza, String foto ) {
/*        Cursor c= ottieniProdottiByNome(nome, marca);
        while(c.moveToFirst()){
            if(c.getString(7).equals(scadenza)){
                int q=c.getInt(4)+quantita;
                db.execSQL("UPDATE PRODOTTI SET QUANTITA="+q+" WHERE NOME=? AND MARCA=? AND DATA_SCADENZA=? AND SUPERMERCATO_E=?", new String[]{nome, marca, scadenza, supermercato});
                return 1;
            }
        }*/
        ContentValues values = new ContentValues();

        values.put( "NOME", nome);
        values.put( "MARCA", marca);
        values.put( "SUPERMERCATO_E", supermercato );
        values.put( "QUANTITA", quantita );
        values.put( "LOTTO", lotto);
        values.put( "TIPO", tipo);
        values.put( "DATA_SCADENZA", scadenza);
        values.put( "FOTO", foto);
        return db.insert("PRODOTTI", null, values);
    }
    public Cursor ottieniSupermercati(){
        return db.query("SUPERMERCATI",new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, null, null, null, null, null);
    }
    public Cursor ottieniSupermercaticonUtente(String utente)    {
        return db.query("SUPERMERCATI",new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, "EMAIL ="+utente, null, null, null, null);
    }
    public Cursor ottieniProdottiByNome(String Nome, String Marca){
        return db.rawQuery("SELECT * FROM PRODOTTI  WHERE NOME= ? AND MARCA=?", new String[]{Nome, Marca});
    }
    public Cursor listaInseriti(){
       return db.rawQuery("SELECT * FROM PRODOTTI",null);

    }

    public Cursor listaInseritiSupermercato(String supermercato){
        return db.rawQuery("SELECT * FROM PRODOTTI WHERE SUPERMERCATO_E=?"+supermercato,null);
    }
    public Cursor listaPrelevati(String mail){
        return db.rawQuery("SELECT E.NOME, P.NOME, P.MARCA, P.DATA_SCADENZA FROM PRODOTTI ENTI E JOIN PRELEVATI R ON P.EMAIL=R.ENTE JOIN PRODOTTI P ON R.PRODOTTO=P.ID WHERE E.EMAIL=?", new String[]{mail});
    }
    public int decrementa(int ID, int quantita){
        Cursor c= db.rawQuery("SELECT * FROM PRODOTTI WHERE ID=?", new String[]{Integer.toString(ID)});
        if(c.moveToFirst()){
            int q=c.getInt(4)-quantita; //Controllare la funzione getInteger()
            if(q<0)
                return -1;
            db.execSQL("UPDATE PRODOTTI SET QUANTITA="+q+" WHERE ID="+ID);

        }
        return 1;
    }

}