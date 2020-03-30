package com.academy.shoplist.singleton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.academy.shoplist.bean.Prodotto;
import com.academy.shoplist.constants.DbConstants;
import com.academy.shoplist.database.DatabaseManager;

import java.util.ArrayList;

public class ShoplistDatabaseManager extends DatabaseManager {
    //Instance
    private static ShoplistDatabaseManager instance;

    private ShoplistDatabaseManager(Context context){
        super(context);
    }

    public static synchronized ShoplistDatabaseManager getInstance(Context context){

        if(instance==null) {
            synchronized (ShoplistDatabaseManager.class){
                if (instance==null) {
                    instance=new ShoplistDatabaseManager(context);
                    instance.open();
                }
            }
        }
        return instance;
    }

    public void addProdotto (Prodotto prodotto){
        database.beginTransaction();
        try{
            ContentValues values= new ContentValues();
            values.put(DbConstants.PRODOTTI_TABLE_NOME,prodotto.getNome());
            values.put(DbConstants.PRODOTTI_TABLE_DESCRIZIONE,prodotto.getDescrizione());
            database.insert(DbConstants.PRODOTTI_TABLE,null,values);
            Log.d("Elemento inserito ", "Prodotto con nome : " + prodotto.getNome());
            database.setTransactionSuccessful();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            database.endTransaction();
        }
    }
    public ArrayList<Prodotto> getProdottiByCursor(Cursor cursore){
        ArrayList<Prodotto> listaProdotti = new ArrayList<>();
        if (cursore != null && cursore.getCount() != 0) {
            while (cursore.moveToNext()) {
                Prodotto prodotto = new Prodotto();
                prodotto.setNome(cursore.getString(cursore.getColumnIndex(DbConstants.PRODOTTI_TABLE_NOME)));
                prodotto.setDescrizione(cursore.getString(cursore.getColumnIndex(DbConstants.PRODOTTI_TABLE_DESCRIZIONE)));
                prodotto.setImmagine(cursore.getInt(cursore.getColumnIndex(DbConstants.PRODOTTI_TABLE_IMG)));
                listaProdotti.add(prodotto);
            }
            cursore.close();
        } else if (cursore != null) {
            cursore.close();
        }
        return listaProdotti;
    }

    public Cursor getProdotti() {
        return database.query(DbConstants.PRODOTTI_TABLE,null,null,null,null,null,null);
    }
    public void deleteProdotto(String nome)
    {
        Log.d("Prodotti eliminati",": " + database.delete(DbConstants.PRODOTTI_TABLE,DbConstants.PRODOTTI_TABLE_NOME + " = ?",new String[]{nome}));
    }
}
