package com.johnestebanap.juegodepreguntassofka.db;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_ANSWER;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_CATEGORY;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION1;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION2;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION3;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_OPTION4;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.COLUMN_QUESTION;
import static com.johnestebanap.juegodepreguntassofka.db.Config.TBQuestions.TABLE_NAME;
import static com.johnestebanap.juegodepreguntassofka.db.Config.SQL_CREATE_QUESTIONS_TABLE;
import static com.johnestebanap.juegodepreguntassofka.db.Config.SQL_DELETE_USER;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "juegodepreguntassofka.db";

    SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db)
    {

        this.db = db;
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        //Metodo para agregar las preguntas a la tabla de la base de datos
        agregarPreguntasTb();

    }

      @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1)
    {
        // se elimina la tabla si esta esiste
        db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }



    private void addQuestions(Questions questions) // se agregan las preguntas a la tabla
    {

        ContentValues values = new ContentValues();
        values.put(COLUMN_QUESTION, questions.getQuestion());
        values.put(COLUMN_OPTION1, questions.getOption1());
        values.put( COLUMN_OPTION2, questions.getOption2());
        values.put( COLUMN_OPTION3, questions.getOption3());
        values.put( COLUMN_OPTION4, questions.getOption4());
        values.put( COLUMN_ANSWER, questions.getAnswer());
        values.put( COLUMN_CATEGORY, questions.getCategory());
        db.insert(TABLE_NAME,null,values);






    }

    //se insertan las preguntas a la base de datos
    public  void agregarPreguntasTb()
    {
        //general
        Questions q1= new Questions("¿Cuál no es un color primario?","Rojo","Amarillo","Azul","Café",4,"General");
        addQuestions(q1);

        Questions q2= new Questions("Un pepinillo es un:","Zucchini","Pepino","Quimbombó","Rábano",2,"General");
        addQuestions(q2);

        Questions q3= new Questions("Wakanda es un país ficticio ¿en qué continente?","Asia","Africa","Norte América","Sudamérica",2,"General");
        addQuestions(q3);

        Questions q4= new Questions("¿Dón está Tasmania?","Madagascar","Australia","Groenlandia","JaPón",2,"General");
        addQuestions(q4);

        Questions q5= new Questions("¿Quién gana en la fábula? ¿La liebre o la tortuga?","Ninguno","La tortuga","la liebre","Empatan",2,"General");
        addQuestions(q5);


        //matematicas
        Questions q6= new Questions("¿Cuál es el primer dígito de pi?","1","2","3","4",3,"Matematicas");
        addQuestions(q6);

        Questions q7= new Questions("¿A cuánto equivale π?","3,141592","3,159215","3,144423","3,144123",1,"Matematicas");
        addQuestions(q7);

        Questions q8= new Questions("Cual es la Formula para encontrar la área del cuadrado","A=L^²","A=(LXL)/2","A=LxL","A=Pi(R^2)",3,"Matematicas");
        addQuestions(q8);

        Questions q9= new Questions("¿Qué expresa esta formula? e=mc^2","equivalencia entre masa y energía","la teoría de la probabilidad","Circunferencia de un círculo","Volumen de un cubo",1,"Matematicas");
        addQuestions(q9);

        Questions q10= new Questions("¿Cuáles de los siguientes triángulos, según sus medidas son rectángulos?","3 cm, 4 cm, 7 cm","3 cm, 4 cm, 5cm","5 cm, 12 cm, 13 cm"," 1 + 7 = 8",4,"Matematicas");
        addQuestions(q10);


        //Programacion

        Questions q11= new Questions("¿Cual es una sentencia de contro que permite que el código se ejecute repetidamente (Blucle) en java?","far()","if()","while()","try()",3,"Programacion");
        addQuestions(q11);

        Questions q12= new Questions("¿La estructura condicional más simple en java es?","if()","switch()","for()","foreach()",1,"Programacion");
        addQuestions(q12);

        Questions q13= new Questions("¿Proceso de diseñar, codificar, depurar y mantener el código fuente de programas computacionales?","Compilación","Programación","Hardware","Software",2,"Programacion");
        addQuestions(q13);

        Questions q14= new Questions("Lenguaje formal diseñado para expresar procesos que pueden ser llevados a cabo por máquinas como las computadoras.","Variables","Storyboard","Lenguaje de programación","Diseño de Flujo",3,"Programacion");
        addQuestions(q14);

        Questions q15= new Questions("Son descripciones gráficas de algoritmos; usan símbolos conectados con flechas para indicar la secuencia de instrucciones.","Diagramas de flujo","Storyboard","Pseudocódigo","Lenguaje de programación",1,"Programacion");
        addQuestions(q15);



        //Logica
        Questions q16= new Questions("¿Cual es una sentencia de contro que permite que el código se ejecute repetidamente (Blucle) en java?","far()","if()","while()","try()",3,"Logica");
        addQuestions(q16);

        Questions q17= new Questions("¿La estructura condicional más simple en java es?","if()","switch()","for()","foreach()",1,"Logica");
        addQuestions(q17);

        Questions q18= new Questions("¿Proceso de diseñar, codificar, depurar y mantener el código fuente de programas computacionales?","Compilación","Programación","Hardware","Software",2,"Logica");
        addQuestions(q18);

        Questions q19= new Questions("Lenguaje formal diseñado para expresar procesos que pueden ser llevados a cabo por máquinas como las computadoras.","Variables","Storyboard","Lenguaje de programación","Diseño de Flujo",3,"Logica");
        addQuestions(q19);

        Questions q20= new Questions("Son descripciones gráficas de algoritmos; usan símbolos conectados con flechas para indicar la secuencia de instrucciones.","Diagramas de flujo","Storyboard","Pseudocódigo","Lenguaje de programación",1,"Logica");
        addQuestions(q20);


        //Historia
        Questions q21= new Questions("¿Cual es una sentencia de contro que permite que el código se ejecute repetidamente (Blucle) en java?","far()","if()","while()","try()",3,"Historia");
        addQuestions(q21);

        Questions q22= new Questions("¿La estructura condicional más simple en java es?","if()","switch()","for()","foreach()",1,"Historia");
        addQuestions(q22);

        Questions q23= new Questions("¿Proceso de diseñar, codificar, depurar y mantener el código fuente de programas computacionales?","Compilación","Programación","Hardware","Software",2,"Historia");
        addQuestions(q23);

        Questions q24= new Questions("Lenguaje formal diseñado para expresar procesos que pueden ser llevados a cabo por máquinas como las computadoras.","Variables","Storyboard","Lenguaje de programación","Diseño de Flujo",3,"Historia");
        addQuestions(q24);

        Questions q25= new Questions("Son descripciones gráficas de algoritmos; usan símbolos conectados con flechas para indicar la secuencia de instrucciones.","Diagramas de flujo","Storyboard","Pseudocódigo","Lenguaje de programación",1,"Historia");
        addQuestions(q25);

    }

    //Metodo para consultar todas las preguntas por su respectiva categoria
    @SuppressLint("Range")
    public ArrayList<Questions> getAllQuestionsWithCategory(String category)
    {
        ArrayList<Questions> questionsList = new ArrayList<>();

        db = getReadableDatabase();
        String[] Projection =
                {
                        Config.TBQuestions._ID,
                        COLUMN_QUESTION,
                        COLUMN_OPTION1,
                        COLUMN_OPTION2,
                        COLUMN_OPTION3,
                        COLUMN_OPTION4,
                        COLUMN_ANSWER,
                        COLUMN_CATEGORY,
                };

        String selection = COLUMN_CATEGORY + " = ? ";
        String[] categorySelection = {category} ;


        // se ejecuta la consulta para octener los datos de las preguntas y se le pasa su respectiva categoria
        @SuppressLint("Recycle")
        Cursor cursor = db.query(TABLE_NAME,Projection,selection,categorySelection,null,null,null);

        //se recore el cursor para estraer los datos y guardarlos en la lista questionsList
        if (cursor.moveToFirst())
        {
            do
            {
                Questions questions = new Questions();
                questions.setQuestion(cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION)));
                questions.setOption1(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION1)));
                questions.setOption2(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION2)));
                questions.setOption3(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION3)));
                questions.setOption4(cursor.getString(cursor.getColumnIndex(COLUMN_OPTION4)));
                questions.setAnswer(cursor.getInt(cursor.getColumnIndex(COLUMN_ANSWER)));
                questions.setCategory(cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY)));

                questionsList.add(questions);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return  questionsList;
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // actulisa la vercion de la vase de datos eliminado la vercion vieja y atulizandola por la vercion nueva
        onUpgrade(db, oldVersion, newVersion);
    }
}
