package com.johnestebanap.juegodepreguntassofka;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PuntajeFianalDialog {
    private Context mContext;
    private Dialog finalScoreDialog;
    TextView txtvwFinalScore;
    private Handler handler = new Handler();

    public PuntajeFianalDialog(Context mContext) {
        this.mContext = mContext;
    }

    public void puntajeFianal(int correctAns,int wrongAns, int totalSize)
    {
        finalScoreDialog = new Dialog(mContext);
        finalScoreDialog.setContentView(R.layout.final_score);

        final Button btn_finalScore = (Button) finalScoreDialog.findViewById(R.id.btn_finalScore);

        finalScoreValidations( correctAns, wrongAns, totalSize);

        /*
        btn_finalScore.setOnClickListener(view -> //al darle click al botón "ok", al finalizar la ronda, se cerrará el alert dialog y se llevará de nuevo a la explaining Activity
        {
            finalizar();
        });*/

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finalizar();
            }
        }, 4500);

        finalScoreDialog.show();
        finalScoreDialog.setCancelable(false);//se pone falso ya que no se desea cancelar al apretar por ejemplo, el botón de atrás
        finalScoreDialog.setCanceledOnTouchOutside(false);//esto indica que el cuadro de diálogo no se cerrará si se clickea en otra parte

    }


    private void finalizar(){
        finalScoreDialog.dismiss();


        //Formateo los datos
        //se asigna a prefe el documento llamado data de sharedPreferences
        SharedPreferences prefe = mContext.getSharedPreferences("datos", Context.MODE_PRIVATE);

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
    private void finalScoreValidations(int correctAns, int wrongAns, int totalSize) // método em el cuál se hace la fórmula del score

    {
        int tempScore;
        txtvwFinalScore = finalScoreDialog.findViewById(R.id.txtvwScore); // se le asigna los datos al textview creado en el layout final_score

        if (correctAns == totalSize) //cuando se finaliza el juego
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }

        else if (wrongAns == totalSize) // cuando no se completa  el juego  o las preguntas son incorrectas
        {
            tempScore = 0;
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }

        else  if (correctAns>wrongAns) // cuando hay más respuestas correctas que incorrectas
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else  if (wrongAns>correctAns) // cuando hay más respuestas incorrectas que correctas
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
        else  if (correctAns==wrongAns) // cuando hay igualdad en respuestsa correctas e incorrectas
        {
            tempScore = (correctAns * 20) - (wrongAns * 5);
            txtvwFinalScore.setText("Final Score: " + String.valueOf(tempScore));
        }
    }
}

