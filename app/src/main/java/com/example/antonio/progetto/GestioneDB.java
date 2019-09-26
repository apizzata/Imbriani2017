package com.example.antonio.progetto;

/**
 * Created by Domenico on 15/05/2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class GestioneDB {

    static final String DATABASE_NOME = "WeFood5";
    static final int DATABASE_VERSIONE =1; //12;

    /*
    Creo una costante contenente la query per la creazione del database
    */
    static final String DATABASE_ENTI = "CREATE TABLE ENTI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL))";
    static final String DATABASE_SUPERMERCATI = "CREATE TABLE SUPERMERCATI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL))";
    static final String DATABASE_PRODOTTI = "CREATE TABLE PRODOTTI (ID INTEGER , NOME VARCHAR(50), MARCA VARCHAR(50), SUPERMERCATO_E VARCHAR(50), QUANTITA INTEGER, LOTTO VARCHAR(30), TIPO VARCHAR(30), DATA_SCADENZA VARCHAR(10), FOTO VARCHAR(100), PRIMARY KEY(ID))";
    static final String DATABASE_MESSAGGI = "create table if not exists MESSAGGI (SUPERMERCATO VARCHAR(30), ENTE VARCHAR(50), MESSAGGIO TEXT)";
    static final String DATABASE_PRESI = "CREATE TABLE PRELEVATI (ENTE VARCHAR(30), PRODOTTO INTEGER, QUANTITA INTEGER, DATA VARCHAR(10))";
    //static final String[] dati={"CREATE TABLE ENTI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL))","CREATE TABLE SUPERMERCATI (NOME VARCHAR(30), INDIRIZZO VARCHAR(50), COMUNE VARCHAR(50), PROVINCIA VARCHAR(30), EMAIL VARCHAR(50), PASSWORD VARCHAR(50), PRIMARY KEY(EMAIL))","CREATE TABLE PRELEVATI (ENTE VARCHAR(30), PRODOTTO INTEGER, QUANTITA INTEGER, DATA VARCHAR(10))","CREATE TABLE PRODOTTI (ID INTEGER AUTO_INCREMENT, NOME VARCHAR(50), MARCA VARCHAR(50), SUPERMERCATO_E VARCHAR(50), QUANTITA INTEGER, LOTTO VARCHAR(30), TIPO VARCHAR(30), DATA_SCADENZA VARCHAR(10), FOTO VARCHAR(100), PRIMARY KEY(ID))"};
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
        DatabaseHelper(Context context){
            // invoco il costruttore della classe base
            super(context, DATABASE_NOME, null, DATABASE_VERSIONE);
        }
        @Override
        public void onCreate(SQLiteDatabase db){
            try {

                db.execSQL(DATABASE_ENTI);

                db.execSQL(DATABASE_SUPERMERCATI);
                db.execSQL(DATABASE_PRESI);
                db.execSQL(DATABASE_PRODOTTI);
            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            //db.execSQL(DATABASE_PRESI);
        }
    }

    public GestioneDB open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        DBHelper.close();
    }
    //TESTED
    public long inserisciEnti(String nome, String indirizzo, String comune, String provincia, String email,String pass){
        ContentValues values = new ContentValues();
        values.put( "NOME", nome );
        values.put( "INDIRIZZO", indirizzo );
        values.put( "COMUNE", comune );
        values.put( "PROVINCIA", provincia );
        values.put( "EMAIL", email);
        values.put( "PASSWORD", pass);
        return db.insert("ENTI", null, values);
    }
    //TESTED
    public long insertSupermercato(String nome, String indirizzo, String comune, String provincia, String email,String pass) {
        ContentValues values = new ContentValues();
        values.put( "NOME", nome );
        values.put( "INDIRIZZO", indirizzo );
        values.put( "COMUNE", comune );
        values.put( "PROVINCIA", provincia );
        values.put( "EMAIL", email);
        values.put( "PASSWORD", pass);
        return db.insert("SUPERMERCATI", null, values);
    }
    //HALF TESTED
    public long insertProdotto(String nome, String marca, String supermercato, int quantita, String lotto, String tipo, String scadenza, String foto ) {
        Integer scad= checkScadenza(scadenza);
        if(scad==1) {
            Cursor c = ottieniProdottiByNome(nome, marca);
            if (c.moveToFirst()) {
                do {
                    if (c.getString(7).equals(scadenza) && c.getString(3).equals(supermercato)) {
                        int q = c.getInt(4) + quantita;
                        int id = c.getInt(0);
                        ContentValues args = new ContentValues();
                        args.put("QUANTITA", q);
                        db.update("prodotti", args, "ID=" + id, null);
                        return 1;
                    }
                } while (c.moveToNext());
            } else {
                int i=0;
                Cursor cursor=listaInseriti();
                while(cursor.moveToNext()){
                    i++;
                }
                Integer ID=i;
                ContentValues values = new ContentValues();
                values.put("ID",ID);
                values.put("NOME", nome);
                values.put("MARCA", marca);
                values.put("SUPERMERCATO_E", supermercato);
                values.put("QUANTITA", quantita);
                values.put("LOTTO", lotto);
                values.put("TIPO", tipo);
                values.put("DATA_SCADENZA", scadenza);
                values.put("FOTO", foto);
                db.insertOrThrow("PRODOTTI", null, values);
                Log.d("PRODD","1");
                return 1;

            }
            Log.d("PRODD","-1");
            return -1;
        }
        Log.d("PRODD","-2");
        return -2;
    }
    //TESTED
    public void insertPrelevati(int ID, String ente, int quantita){
        ContentValues values=new ContentValues();
        values.put("ENTE", ente);
        values.put("PRODOTTO", ID);
        values.put("QUANTITA", quantita);
        Calendar today=Calendar.getInstance();
        Integer y=today.get(Calendar.YEAR);
        Integer m=today.get(Calendar.MONTH);
        Integer d=today.get(Calendar.DATE);
        String mo;
        String da;
        if(m<10){
            mo="0"+m.toString();
        }
        else{
            mo=m.toString();
        }
        if(d<10){
            da="0"+d.toString();
        }
        else{
            da=d.toString();
        }
        String s=da+"-"+mo+"-"+y.toString();
        values.put("DATA", s);
        //values.put("STATO",0);
        db.insert("PRELEVATI", null, values);
    }
    //TESTED
    public void deleteProdotti(){
        db.delete("prodotti", null, null);
    }
    public void deleteProdotto(int ID){
        db.delete("prodotti", "ID="+ID, null);
    }
    //TESTED
    public void deleteEnti(){
        db.delete("enti", null, null);
    }
    public void deleteEnte(String Ente){
        db.delete("ente", "EMAIL='"+Ente+"'", null);
    }
    //TESTED
    public void deleteSupermercati(){
        db.delete("supermercati", null, null);
    }
    public void deleteSupermercato(String supermercato){
        db.delete("supermercato", "EMAIL='"+supermercato+"'", null);
    }
    //TESTED
    public void deletePrelevati(){
        db.delete("PRELEVATI", null, null);
    }
    //TESTED
    public Cursor ottieniPrelevati(){
        Cursor c=db.rawQuery("SELECT * FROM PRELEVATI", null);
        return c;
    }
    //TESTED
    public Cursor ottieniEnti(){
        return db.query("ENTI",new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, null, null, null, null, null);
    }
    //TESTED
    public Cursor ottieniEnticonUtente(String utente){
        //Cursor mCursore = db.query(true, DATABASE_TABELLA, new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, "EMAIL ="+utente, null, null, null, null, null);
        Cursor mCursore= db.rawQuery("SELECT * FROM ENTI WHERE EMAIL = '"+utente+"'", null);
        if (mCursore != null) {
            mCursore.moveToFirst();
        }
        return mCursore;
    }
    //TESTED
    public Cursor ottieniSupermercati(){
        return db.rawQuery("SELECT * FROM SUPERMERCATI", null);
    }
    public Cursor ottieniSupermercaticonUtente(String utente)    {
        //return db.query("SUPERMERCATI",new String[] {"NOME", "INDIRIZZO", "COMUNE", "PROVINCIA", "EMAIL", "PASSWORD"}, "EMAIL ='"+utente+"'", null, null, null, null);
        return  db.rawQuery("SELECT * FROM SUPERMERCATI WHERE EMAIL='"+utente+"'",null);
    }
    //TESTED
    public Cursor ottieniProdottiByNome(String nome, String marca){
        return db.rawQuery("SELECT * FROM PRODOTTI WHERE NOME='"+nome+"' AND MARCA='"+marca+"'", null);
    }
    public Cursor ottieniProdottiById(Integer id){
        return  db.rawQuery("SELECT * FROM PRODOTTI WHERE ID="+id,null);
    }
    //TESTED
    public Cursor listaInseriti(){
        return db.rawQuery("SELECT * FROM PRODOTTI", null);
    }
    //TESTED
    public Cursor listaInseritiSupermercato(String supermercato){
        return db.rawQuery("SELECT * FROM PRODOTTI WHERE SUPERMERCATO_E='"+supermercato+"'", null);
    }
    //TESTED
    public Cursor listaPrelevati(String mail){
        return db.rawQuery("SELECT E.NOME,P.ID, P.NOME, P.MARCA, P.DATA_SCADENZA, R.QUANTITA FROM ENTI E JOIN PRELEVATI R ON E.EMAIL=R.ENTE JOIN PRODOTTI P ON R.PRODOTTO=P.ID WHERE E.EMAIL='"+mail+"'", null);
    }
    //TESTED
    public int decrementa(int ID, int quantita){
        Cursor c=db.rawQuery("SELECT * FROM PRODOTTI WHERE ID="+ID, null);
        if(c.moveToFirst()){
            int q=c.getInt(4)-quantita; //Controllare la funzione getInteger()
            if(q<0)
                return -1;
            ContentValues args=new ContentValues();
            args.put("QUANTITA", q);
            db.update("PRODOTTI", args, "ID="+ID, null);
            return 1;
        }
        return 0;
    }
    //TESTED
    public int checkScadenza(String Scadenza){
      //  Cursor c=db.rawQuery("SELECT * FROM PRODOTTI WHERE ID="+ID, null);
       // String s;
       // c.moveToFirst();
        //s = c.getString(7);
        String d[]=Scadenza.split("-");
        Calendar scadenza=Calendar.getInstance();
        scadenza.set(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0])); // controllare Integer
        Calendar today=Calendar.getInstance();
        if(scadenza.equals(today))
            return 0;
        else if(scadenza.after(today))
            return 1;
        else if(scadenza.before(today)){
            today.add(Calendar.DATE, -7);
            if(scadenza.before(today)){
                today.add(Calendar.DATE, -7);
                if(scadenza.before(today)) {
                    return -3;
                }
                return -2;
            }
            return -1;
        }
        return 0;
    }
    //TESTED
    public void pulisciPrelevati(){
        Calendar today=Calendar.getInstance();
        Cursor c=db.rawQuery("SELECT * FROM PRELEVATI", null);
        if(c.moveToFirst()){
            do{
                String s=c.getString(3);
                String d[]=s.split("-");
                Calendar prelievo=Calendar.getInstance();
                prelievo.set(Integer.parseInt(d[2]), Integer.parseInt(d[1]), Integer.parseInt(d[0]));
                prelievo.add(Calendar.DATE, 30);
                if(prelievo.before(today)){
                    db.delete("PRELEVATI", "DATA = '"+s+"'", null);
                }
            }while(c.moveToNext());
        }
    }
    //TESTED
    public void preleva(int ID, int quantita, String email){
        int k=decrementa(ID, quantita);
        if(k<0)
            Toast.makeText(context.getApplicationContext(), "Risorse non disponibili \n", Toast.LENGTH_LONG).show();
        else if(k==0)
            Toast.makeText(context.getApplicationContext(), "Prodotto non presente del nostro database \n", Toast.LENGTH_LONG).show();
        else if(k>0) {
            insertPrelevati(ID, email, quantita);
            Toast.makeText(context.getApplicationContext(), "Acquisto effettuato \n", Toast.LENGTH_LONG).show();
        }
    }


    public Cursor ottieniProdottiByNome(String nome){

        return db.rawQuery("SELECT * FROM PRODOTTI WHERE NOME LIKE'%"+nome+"%'", null);
    }

    public void modificaSupermercato(String email, String nome, String indirizzo, String comune, String provincia){
        Log.d("UPDATEE","ciao1");
        Cursor ca= db.rawQuery("SELECT * FROM ENTI WHERE EMAIL='"+email+"'", null);
        Log.d("UPDATEE","ciao2");
        ContentValues values = new ContentValues();
        Log.d("UPDATEE","ciao3");
        if(nome.equals(null))
            values.put( "NOME", ca.getString(0));
        else
            values.put("NOME", nome);

        if(indirizzo.equals(null))
            values.put( "INDIRIZZO", ca.getString(1));
        else
            values.put( "INDIRIZZO", indirizzo);

        if(comune.equals(null))
            values.put( "COMUNE", ca.getString(2));
        else
            values.put( "COMUNE", comune );

        if(provincia.equals(null))
            values.put( "PROVINCIA", ca.getString(3));
        else
            values.put( "PROVINCIA", provincia );
        values.put("EMAIL", email);

        Log.d("UPDATEE","ciao4");

        db.update("SUPERMERCATI", values, "EMAIL='"+email+"'", null);
    }
    public void modificaEnte(String email, String nome, String indirizzo, String comune, String provincia){
        Log.d("UPDATEE","ciao1");
        Cursor ca= db.rawQuery("SELECT * FROM ENTI WHERE EMAIL='"+email+"'", null);
        Log.d("UPDATEE","ciao2");
        ContentValues values = new ContentValues();
        Log.d("UPDATEE","ciao3");
        if(nome.equals(null))
            values.put( "NOME", ca.getString(0));
        else
            values.put("NOME", nome);

        if(indirizzo.equals(null))
            values.put( "INDIRIZZO", ca.getString(1));
        else
            values.put( "INDIRIZZO", indirizzo);

        if(comune.equals(null))
            values.put( "COMUNE", ca.getString(2));
        else
            values.put( "COMUNE", comune );

        if(provincia.equals(null))
            values.put( "PROVINCIA", ca.getString(3));
        else
            values.put( "PROVINCIA", provincia );
        values.put("EMAIL", email);

        Log.d("UPDATEE","ciao4");
        db.update("ENTI", values, "EMAIL='"+email+"'", null);
    }
    public int checkData(String gg, String mm, String yy){
        int g=Integer.parseInt(gg);
        int m=Integer.parseInt(mm);
        int y=Integer.parseInt(yy);
        if(g<1||m<1||m>12)
            return -1;
        else if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10 || m==12){
            if(g>31)
                return -2;
        }
        else if(m==4 || m==6 || m==9 || m==11){
            if (g>30)
                return -3;
        }
        else if(m==2 && y%4==0){
            if(g>29)
                return -4;
        }
        else if(m==2 && g>28)
            return -5;
        if(y<2016)
            return -6;

        return 1;
    }
    public String correggiData(String gg) {
        if(gg.length()<2){
            String k="0"+gg;
            return k;
        }
        else{
            return gg;
        }
    }
     public void AggiungiProdottoDaEnte(Integer ID){
        Cursor c=db.rawQuery("SELECT P.SUPERMERCATO_E, P.QUANTITA, R.QUANTITA FROM PRELEVATI R JOIN PRODOTTI P ON R.PRODOTTO=P.ID WHERE R.PRODOTTO="+ID+"",null);
         String sup=null;
         Integer qS=0;
         Integer qE=0;
         if(c.moveToFirst()) {
             sup = c.getString(0);
             qS = c.getInt(1);//quantita nel supermercato
             qE = c.getInt(2); //quantita presa dall'ente
             ContentValues values= new ContentValues();
             Integer q= qS+qE;
             values.put("QUANTITA",q);
             db.update("PRODOTTI", values, "ID="+ID+"", null);
             db.delete("PRELEVATI","PRODOTTO="+ID+"",null);
         }else{
             Log.d("AGGIUNGI","fail");
         }
    }

    public void EliminaProdSupermercato(Integer ID){
        ContentValues values= new ContentValues();
        values.put("QUANTITA",0);
        db.update("PRODOTTI",values, "ID="+ID+"",null);
    }
    public void ModificaQuantitaProdotto(Integer ID,Integer qnt){
        ContentValues values=new ContentValues();
        values.put("QUANTITA",qnt);
        db.update("PRODOTTI",values, "ID="+ID+"",null);
    }
}