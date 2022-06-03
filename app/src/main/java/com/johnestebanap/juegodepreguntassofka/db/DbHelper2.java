package com.johnestebanap.juegodepreguntassofka.db;

import static com.johnestebanap.juegodepreguntassofka.db.Config.SQL_CREATE_USER_TABLE;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBUser.COLUMN_SCORE;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBUser.COLUMN_USER;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.johnestebanap.juegodepreguntassofka.LoginActivity;
import com.johnestebanap.juegodepreguntassofka.PuntajeFinalDialog;

public class DbHelper2 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "history.db";
    SQLiteDatabase historyDB;

    public DbHelper2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase historyDB) {
        this.historyDB = historyDB;
        historyDB.execSQL(SQL_CREATE_USER_TABLE);
        // addHistoryUserTb();
    }

    @Override
    public void onUpgrade(SQLiteDatabase historyDB, int oldVersion, int newVersion) {
        historyDB.execSQL(SQL_DELETE_USER);
        onCreate(historyDB);
    }

    public void addHistoryUserTb(String nameUser, int score){
        HistoryUser historyUser = new HistoryUser(nameUser, score);
        addHistoryUser(historyUser);
    }

    private void addHistoryUser(HistoryUser historyUser)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER, historyUser.getNameUser());
        values.put(COLUMN_SCORE, historyUser.getScore());
        historyDB.insert(Config.TBUser.TABLE_NAME, null, values);
    }

    protected  final  static  String SQL_DELETE_USER =  "DROP TABLE IF EXISTS " + Config.TBUser.TABLE_NAME;
}
