package com.johnestebanap.juegodepreguntassofka;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

public class PuntajeFinalDialog {
    private final Context mContext;
    private Dialog finalScoreDialog;
    TextView txtvwPuntajeFinal, txtvwPorcentajeFinal, txtvwCorrectas, txtvwIncorrectas ;
    private final Handler handler = new Handler();

    public PuntajeFinalDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void puntajeFianal(int respuestasCoreectas,int respuestasIncorrectas, int totalPreguntas)
    {
        finalScoreDialog = new Dialog(mContext);
        finalScoreDialog.setContentView(R.layout.final_score);

        final Button btn_finalScore = finalScoreDialog.findViewById(R.id.btn_finalScore);

        finalScoreValidations( respuestasCoreectas, respuestasIncorrectas, totalPreguntas);

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


    private void finalizar(){
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
        intent.putExtra("email", "email");
        mContext.startActivity(intent);
    }


    @SuppressLint("SetTextI18n")
    private void finalScoreValidations(int respuestasCorrectas, int respuestasIncorrectas, int totalRespuestas) // método em el cuál se hace la fórmula del score
    {
        int puntos, porcentaje;
        txtvwPuntajeFinal = finalScoreDialog.findViewById(R.id.txtvw_puntaje_final); // se le asigna los datos al textview creado en el layout final_score
        txtvwPorcentajeFinal = finalScoreDialog.findViewById(R.id.txtvw_porcentaje_final);
        txtvwCorrectas = finalScoreDialog.findViewById(R.id.txtvw_correctas);
        txtvwIncorrectas = finalScoreDialog.findViewById(R.id.txtvw_incorrectas);
        
        puntos = (respuestasCorrectas * 10) - (respuestasIncorrectas * 10);
        txtvwPuntajeFinal.setText(mContext.getString(R.string.puntaje_final) + puntos);

        porcentaje = (respuestasCorrectas * 100)/totalRespuestas;
        txtvwPorcentajeFinal.setText(mContext.getString(R.string.porcentaje_final) + porcentaje +" %");
        
        txtvwCorrectas.setText(mContext.getString(R.string.correctas_dialog) + respuestasCorrectas);
        txtvwIncorrectas.setText(mContext.getString(R.string.incorrectas_dialog) + respuestasIncorrectas);
    }
}

