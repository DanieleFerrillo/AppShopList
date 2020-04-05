package com.academy.shoplist.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import com.academy.shoplist.constants.DbConstants;

import java.io.File;
import java.io.IOException;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DB_NAME = "shoplist.db";
    private final static int CURRENT_DB_VERSION = 1;
    protected final Context myContext;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, CURRENT_DB_VERSION);
        this.myContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("createDatabase", "UPGRADE DB FROM" + oldVersion + "TO" + newVersion);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (Build.VERSION.SDK_INT >= 28) {
            db.disableWriteAheadLogging();
        }
    }

    public SQLiteDatabase openDatabase() throws SQLException {
        String myPath = myContext.getDatabasePath(DB_NAME).getPath();
        return SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    //gestisce la creazione di un nuovo database se non ne esiste uno
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        SQLiteDatabase db = this.getReadableDatabase();
        if (!dbExist) {
            createShopListDB(db);
        }
    }

    private boolean checkDataBase() {
        //verifica esistenza database
        File database = myContext.getDatabasePath(DB_NAME);
        return database.exists();
    }

    private void createShopListDB(SQLiteDatabase database) {
        Log.d("createDataBase", "create datase " + DB_NAME);
        database.execSQL(DatabaseTables.SQL_CREATE_PRODOTTO);
    }

    // elimina le tabella richiamando un altro metodo "droptable"
    private void dropAllTable(SQLiteDatabase database) {
        Log.d("createDataBase", "DROP ALL TABLES ");
        dropTable(database, DbConstants.PRODOTTI_TABLE);
    }

    public void dropTable(SQLiteDatabase database, String table) {
        try {
            String dropTable = "DROP TABLE IF EXISTS " + table + ";";
            database.execSQL(dropTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
