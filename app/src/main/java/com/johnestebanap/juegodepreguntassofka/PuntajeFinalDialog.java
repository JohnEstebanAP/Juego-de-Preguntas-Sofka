package com.johnestebanap.juegodepreguntassofka;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.johnestebanap.juegodepreguntassofka.db.Config;
import com.johnestebanap.juegodepreguntassofka.db.DbHelper;
import com.johnestebanap.juegodepreguntassofka.db.DbHelper2;
import com.johnestebanap.juegodepreguntassofka.db.HistoryUser;

public class PuntajeFinalDialog {
    private final Context mContext;
    private Dialog finalScoreDialog;
    TextView txtvwPuntajeFinal, txtvwPorcentajeFinal, txtvwCorrectas, txtvwIncorrectas;
    private final Handler handler = new Handler();

    public PuntajeFinalDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void puntajeFianal(int respuestasCoreectas, int respuestasIncorrectas, int totalPreguntas) {
        finalScoreDialog = new Dialog(mContext);
        finalScoreDialog.setContentView(R.layout.final_score);

        final Button btn_finalScore = finalScoreDialog.findViewById(R.id.btn_finalScore);
        finalScoreValidations(respuestasCoreectas, respuestasIncorrectas, totalPreguntas);
        /*
        btn_finalScore.setOnClickListener(view -> //al darle click al botón "ok", al finalizar la ronda, se cerrará el alert dialog y se llevará de nuevo a la explaining Activity
        {
            finalizar();
        });*/
        handler.postDelayed(() -> finalizar(), 10000);
        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);//se pone falso ya que no se desea cancelar al apretar por ejemplo, el botón de atrás
        finalScoreDialog.setCanceledOnTouchOutside(false);//esto indica que el cuadro de diálogo no se cerrará si se clickea en otra parte
    }

    private void addDataHistoriUser(String username, int score) {
        DbHelper2 dbHelper2 = new DbHelper2(mContext);
        SQLiteDatabase db = dbHelper2.getWritableDatabase();
        if (db != null) {
            Toast.makeText(mContext, "se creo la base de datos", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }

        try {
            HistoryUser historyUser = new HistoryUser(username, score);

            //ContentValues values = new ContentValues();
            //values.put(COLUMN_USER, historyUser.getNameUser());
            //values.put(COLUMN_SCORE, historyUser.getScore());
            //historyDB.insert(Config.TBUser.TABLE_NAME, null, values);
            db.execSQL("INSERT INTO HistoryUser  (User, Score) VALUES('" + historyUser.getNameUser() + "', " + historyUser.getScore() + ");");
            Toast.makeText(mContext, "se lleno la tabla", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(mContext, "ERROR AL LLENAR LA BASE DE DATOS", Toast.LENGTH_SHORT).show();
        }
    }

    private void finalizar() {
        finalScoreDialog.dismiss();
        //Formateo los datos
        //se asigna a prefe el documento llamado data de sharedPreferences
        SharedPreferences prefe = mContext.getSharedPreferences("datos", Context.MODE_PRIVATE);
        String user = prefe.getString("User", "null");

        SharedPreferences.Editor editor = prefe.edit();
        editor.putInt("cont", 1);
        editor.putInt("respuestaCorrecta", 0);
        editor.putInt("respuestaIncorrecta", 0);
        editor.putInt("puntaje", 0);
        editor.putInt("contPreguntas", 1);
        editor.apply();

        //se Crea al intent del Home
        Intent intent = new Intent(mContext, HomeActivity.class);
        //al intent se le pone un estra que es el email para pasarselo a la activity del Home
        intent.putExtra("email", user);
        mContext.startActivity(intent);
    }

    @SuppressLint("SetTextI18n")
    private void finalScoreValidations(int respuestasCorrectas, int respuestasIncorrectas, int totalRespuestas) // método em el cuál se hace la fórmula del score
    {
        SharedPreferences prefe = mContext.getSharedPreferences("datos", Context.MODE_PRIVATE);
        String user = prefe.getString("User", "null");

        int puntos, porcentaje;
        txtvwPuntajeFinal = finalScoreDialog.findViewById(R.id.txtvw_puntaje_final); // se le asigna los datos al textview creado en el layout final_score
        txtvwPorcentajeFinal = finalScoreDialog.findViewById(R.id.txtvw_porcentaje_final);
        txtvwCorrectas = finalScoreDialog.findViewById(R.id.txtvw_correctas);
        txtvwIncorrectas = finalScoreDialog.findViewById(R.id.txtvw_incorrectas);

        puntos = (respuestasCorrectas * 10) - (respuestasIncorrectas * 10);
        txtvwPuntajeFinal.setText(mContext.getString(R.string.puntaje_final) + puntos);

        porcentaje = (respuestasCorrectas * 100) / 5;
        txtvwPorcentajeFinal.setText(mContext.getString(R.string.porcentaje_final) + porcentaje + " %");

        txtvwCorrectas.setText(mContext.getString(R.string.correctas_dialog) + respuestasCorrectas);
        txtvwIncorrectas.setText(mContext.getString(R.string.incorrectas_dialog) + respuestasIncorrectas);

        addDataHistoriUser(user, porcentaje);
    }
}

