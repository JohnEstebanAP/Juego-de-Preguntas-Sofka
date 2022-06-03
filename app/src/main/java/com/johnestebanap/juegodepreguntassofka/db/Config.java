package com.johnestebanap.juegodepreguntassofka.db;

import android.provider.BaseColumns;

public final class Config
{
    //Constructor de la Clase Config
    private Config(){ }

    //se Crean los Strind de las tablas y consultas a relisar para tener mas organizado el codigo
    public static class TBQuestions implements BaseColumns
    {
        public final static String TABLE_NAME = "game_quiz";
        public final static String COLUMN_QUESTION = "preguntas";
        public final static String COLUMN_OPTION1 = "opcion1";
        public final static String COLUMN_OPTION2 = "opcion2";
        public final static String COLUMN_OPTION3 = "opcion3";
        public final static String COLUMN_OPTION4 = "opcion4";
        public final static String COLUMN_ANSWER  = "respuesta";
        public final static String COLUMN_CATEGORY = "categoria";
    }

    protected  final  static  String  SQL_CREATE_QUESTIONS_TABLE = //
            "CREATE TABLE " + TBQuestions.TABLE_NAME + " (" +
                    TBQuestions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TBQuestions.COLUMN_QUESTION + " TEXT NOT NULL," +
                    TBQuestions.COLUMN_OPTION1 + " TEXT NOT NULL," +
                    TBQuestions.COLUMN_OPTION2 + " TEXT NOT NULL," +
                    TBQuestions.COLUMN_OPTION3 + " TEXT NOT NULL," +
                    TBQuestions.COLUMN_OPTION4 + " TEXT NOT NULL," +
                    TBQuestions.COLUMN_ANSWER + " INTEGER NOT NULL," +
                    TBQuestions.COLUMN_CATEGORY + " INTEGER NOT NULL)" ;

    //se Crean los Strind de las tablas y consultas a relisar para tener mas organizado el codigo
    public static class TBUser implements BaseColumns
    {
        public final static String TABLE_NAME = "HistoryUser";
        public final static String COLUMN_USER = "User";
        public final static String COLUMN_SCORE = "Score";
    }

    protected  final  static  String  SQL_CREATE_USER_TABLE = //
            "CREATE TABLE " + TBUser.TABLE_NAME + " (" +
                    TBUser._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    TBUser.COLUMN_USER + " TEXT NOT NULL," +
                    TBUser.COLUMN_SCORE + " INTEGER NOT NULL)";

    protected  final  static  String SQL_DELETE_USER =  "DROP TABLE IF EXISTS " + TBQuestions.TABLE_NAME;

}

