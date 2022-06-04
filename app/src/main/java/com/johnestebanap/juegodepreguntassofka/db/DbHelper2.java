package com.johnestebanap.juegodepreguntassofka.db;

import static com.johnestebanap.juegodepreguntassofka.db.Config.SQL_CREATE_USER_TABLE;
import static com.johnestebanap.juegodepreguntassofka.db.Config.SQL_DELETE_USER;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_ANSWER;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_CATEGORY;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION1;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION2;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION3;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION4;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_QUESTION;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.TABLE_NAME;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBUser.COLUMN_SCORE;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBUser.COLUMN_USER;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.johnestebanap.juegodepreguntassofka.LoginActivity;
import com.johnestebanap.juegodepreguntassofka.PuntajeFinalDialog;

import java.util.ArrayList;

public class DbHelper2 extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "history.db";
    SQLiteDatabase historyDB;

    //Constructor de la baso de datos
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


    @SuppressLint("Range")
    public ArrayList<HistoryUser> getUsersAndScore() {
        ArrayList<HistoryUser> historyUserList = new ArrayList<>();

        Cursor cursor = historyDB.rawQuery("select * FROM HistoryUser",null);

        //se recore el cursor para estraer los datos y guardarlos en la lista questionsList
        if (cursor.moveToFirst()) {
            do {
                HistoryUser historyUser = new HistoryUser();
                historyUser.setNameUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER)));
                historyUser.setScore(cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE)));

                historyUserList.add(historyUser);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return historyUserList;
    }
}
