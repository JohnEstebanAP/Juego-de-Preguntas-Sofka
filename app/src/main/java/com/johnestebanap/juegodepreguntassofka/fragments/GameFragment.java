package com.johnestebanap.juegodepreguntassofka.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.johnestebanap.juegodepreguntassofka.PuntajeFianalDialog;
import com.johnestebanap.juegodepreguntassofka.R;
import com.johnestebanap.juegodepreguntassofka.db.DbHelper;
import com.johnestebanap.juegodepreguntassofka.db.Questions;

import java.util.ArrayList;
import java.util.Collections;

public class GameFragment extends Fragment {

    View view;

    RadioGroup radioGroup;
    TextView txtvwPreguntas, txtvwContPreguntas, txtvwCorrecto, txtvwPuntaje, txtvwIncorrecto, txtvwCountDown;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    RadioButton rb4;
    Button buttonNext;

    public ArrayList<Questions> questionList;
    private int contPreguntas;
    private int questionTotalCount;
    private Questions currentQuestion;
    private Boolean respuesta;

    private Handler handler = new Handler();

    private ColorStateList btnLabelColor;

    private int respuestaCorrecta = 0, respuestaIncorrecta = 0;

    private PuntajeFianalDialog puntajeFianalDialog;

    private int totalSizeofQuestions = 0;

    int puntaje = 0;
    int cont = 0;

    //Constructor de la Clase GameFragment
    public GameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);

        //cada vez que nos salimos de la app se guardar los datos del contador de las cateogrías, preguntas buenas, malas y el puntaje
        SharedPreferences prefs = getActivity().getSharedPreferences("datos", MODE_PRIVATE);
        cont = prefs.getInt("cont", 0);
        respuestaCorrecta = prefs.getInt("respuestaCorrecta", 0);
        respuestaIncorrecta = prefs.getInt("respuestaIncorrecta", 0);
        puntaje = prefs.getInt("puntaje", 0);
        //se le resta 1 para no hacer una sovrecarga o ronpimiento del contador y genera una inconsistencia
        contPreguntas = prefs.getInt("contPreguntas", 1) - 1;

        //metodo que inicializa las varaibles del layout
        idInit();
        //Se inicializa la base de datos de sqlite
        dbInit();
        //se Inicializa el Metodo del Juego
        startGame();

        //se les pasan los valores recuperados con el SaredPreferences
        txtvwCorrecto.setText("Correctas: " + String.valueOf(respuestaCorrecta));
        txtvwIncorrecto.setText("Incorrectas: " + String.valueOf(respuestaIncorrecta));
        txtvwPuntaje.setText("Puntaje: " + String.valueOf(puntaje));

        btnLabelColor = rb1.getTextColors();

        puntajeFianalDialog = new PuntajeFianalDialog(getContext());

        return view;
    }


    //se crea un metodo que inicializa las varaibles del layout
    private void idInit() {
        txtvwPreguntas = view.findViewById(R.id.preguntas_game);
        txtvwContPreguntas = view.findViewById(R.id.Contador_preguntas);
        txtvwPuntaje = view.findViewById(R.id.puntaje_game);
        txtvwCorrecto = view.findViewById(R.id.respuesta_correcta_game);
        txtvwIncorrecto = view.findViewById(R.id.respuestas_incorrectas_game);

        radioGroup = view.findViewById(R.id.radioGroup);
        rb1 = view.findViewById(R.id.rdbtn_res1);
        rb2 = view.findViewById(R.id.rdbtn_res2);
        rb3 = view.findViewById(R.id.rdbtn_res3);
        rb4 = view.findViewById(R.id.rdbtn_res4);

        buttonNext = view.findViewById(R.id.btn_confir);
    }


    // incializa la base de datos
    private void dbInit() {
        DbHelper dbHelper = new DbHelper(getContext());
        // se realiza en un switch para poder obtener las categorías del modelado de la clase Questions.
        switch (cont) {
            case 1:
                questionList = dbHelper.getAllQuestionsWithCategory("General");
                break;
            case 2:
                questionList = dbHelper.getAllQuestionsWithCategory("Matematicas");
                break;
            case 3:
                questionList = dbHelper.getAllQuestionsWithCategory("Programacion");
                break;
            case 4:
                questionList = dbHelper.getAllQuestionsWithCategory("Logica");
                break;
            case 5:
                questionList = dbHelper.getAllQuestionsWithCategory("Historia");
                break;
        }
    }


    private void startGame() {
        questionTotalCount = questionList.size();
        //metodo para convertir la lista de preguntas aleatoria
        Collections.shuffle(questionList);

        //Se Muestran las Preguntas
        showQuestions();

        // se escucha cundo se ase click en algun radio buton
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) // al momento de seleccionar una respuesta correcta, se pondrá de los colores que son
            // ya que tuve un error en el que al pasar de pregunta, igual se quedaba el color de la opción
            {
                switch (id) {
                    case R.id.rdbtn_res1:
                        rb1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.option_selected));
                        rb2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        break;

                    case R.id.rdbtn_res2:
                        rb1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.option_selected));
                        rb3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        break;

                    case R.id.rdbtn_res3:
                        rb1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.option_selected));
                        rb4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        break;

                    case R.id.rdbtn_res4:
                        rb1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.botton_background));
                        rb4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.option_selected));
                        break;
                }

            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!respuesta) {
                    //se verifica si algun radio buton fue selecionado
                    if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked() || rb4.isChecked()) {
                        //se verifica si las preguntas estan buenas

                        gameOperations();
                    } else {
                        //se muestra un toas sino se seleciona ninguna respuesta
                        Toast.makeText(getContext(), "Por favor selecione una opcion", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    // Se muestran y actualiza las preguntas con sus opciones
    public void showQuestions() {
        radioGroup.clearCheck(); //limpia lo seleccionado

        // se le agrega el drawable, el cual le aplica transparancia a el resto de radio buttons
        rb1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.opcion_botton_background));
        rb2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.opcion_botton_background));
        rb3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.opcion_botton_background));
        rb4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.opcion_botton_background));

        totalSizeofQuestions = questionList.size();
        // virifica si ya se llego a la ultima pregunta para mostrar o no la siguiente pregunta
        if (contPreguntas < questionTotalCount) {
            //obtiene el nuero total de preguntas
            currentQuestion = questionList.get(contPreguntas);

            //Bloque donde se le asigna los valores de la BD a las opciones que se van a mostrar
            txtvwPreguntas.setText(currentQuestion.getCategory() + ": " + currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());


            contPreguntas++;
            //se necesita que por defecto esté en false
            respuesta = false;


            txtvwContPreguntas.setText(getString(R.string.pregunta) + contPreguntas + "/" + questionTotalCount);
            //Se guarda el historial del las pregunta actual, puntaje, preguntas correctas e incorrectas.
            guardarHistorial(contPreguntas);

            //Cundo yegamos a la ultima pregunta canviar el texto del boton y decir (confirmar y Finalizar)
            if (contPreguntas == questionTotalCount) {
                if (cont == totalSizeofQuestions) {
                    buttonNext.setText(R.string.confirmar_finalizar_game);
                }
            } else {
                buttonNext.setText(R.string.confirmar);
            }

        } else
        //si no, se devuelve de nuevo a la actividad
        {
            if (cont == totalSizeofQuestions) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // cuando se llega al final del juego, se lanzara lo escrito en la clase
                        //FinalScoreDialog, la cual muestra un alertDialog con el puntaje total obtenido
                        puntajeFianalDialog.puntajeFianal(respuestaCorrecta, respuestaIncorrecta, 25);

                        //si la categoria ya supera los 5 entonces se deve serrar el activity
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), "cerrada y recargada", Toast.LENGTH_SHORT).show();
                                getActivity().finish();
                            }
                        }, 5000);
                    }
                }, 500);
            }


            // se verifica que la categoria no sea la ultima para mandarle los datos  de las preguntas buenas, malas y puntaje a la siguiente categoría
            if (cont < 5) {
                //se incrementa la categoría
                cont++;
                //se guarda el historial
                guardarHistorial(1);

                //Recargamos nuevamente el Fragment del GameFragment y seguimos con la sigiente pregunta o pasa a la siguiente categoría
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.container_fragment, new GameFragment());
                fragmentTransaction.commit();
            }
        }
    }

    private void gameOperations() {
        respuesta = true;
        RadioButton rbSelected = view.findViewById(radioGroup.getCheckedRadioButtonId());
        // la clase indexofchild nos permite obtener el radiobutton seleccionado, se le suma +1 para que siempre inicie con el valor de 1, para que al entrar
        int answerNr = radioGroup.indexOfChild(rbSelected) + 1;

        // Metodo pra verifcar si al respuesta fue corecta
        // al método checkSolution, entre de una por el case 1
        checkSolution(answerNr, rbSelected);
    }

    // Se analiza las respuestas malas o buenas para cada pregunta
    private void checkSolution(int answerNr, RadioButton rbSelected) {
        switch (currentQuestion.getAnswer()) // se va a validar que la pregunte esté buena o no
        {
            case 1: // en cada case se evaluará si las preguntas están buenas o no. se le agregan los contadores de respuestas malas y buenas, un delay de 1 segundo al momento de pasar de pregunta
                if (currentQuestion.getAnswer() == answerNr) {
                    rb1.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.correct_answer));
                    respuestaCorrecta++;//cuenta el numero de preguntas buenas y se lo agrega , en la línea de abajo, al textview
                    txtvwCorrecto.setText(getString(R.string.correctas) + String.valueOf(respuestaCorrecta));
                } else {
                    // la pregunta es incorecto cabiamos el background del radiobuton
                    rbSelected.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.wrong_answer));
                    respuestaIncorrecta++;// cuenta el numero de preguntas malas y se lo agrega , en la línea de abajo, al textview
                    txtvwIncorrecto.setText(getString(R.string.incorrectas)  + String.valueOf(respuestaIncorrecta));
                }
                break;
            case 2:
                if (currentQuestion.getAnswer() == answerNr) {
                    rb2.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.correct_answer));
                    respuestaCorrecta++;
                    txtvwCorrecto.setText(getString(R.string.correctas)  + String.valueOf(respuestaCorrecta));
                } else {
                    // la pregunta es incorecto cabiamos el background del radiobuton
                    rbSelected.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.wrong_answer));
                    respuestaIncorrecta++;
                    txtvwIncorrecto.setText(getString(R.string.incorrectas)  + String.valueOf(respuestaIncorrecta));
                }
                break;
            case 3:
                if (currentQuestion.getAnswer() == answerNr) {
                    rb3.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.correct_answer));
                    respuestaCorrecta++;
                    txtvwCorrecto.setText(getString(R.string.correctas)   + String.valueOf(respuestaCorrecta));
                } else {
                    // la pregunta es incorecto cabiamos el background del radiobuton
                    rbSelected.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.wrong_answer));
                    respuestaIncorrecta++;
                    txtvwIncorrecto.setText(getString(R.string.incorrectas)  + String.valueOf(respuestaIncorrecta));
                }
                break;
            case 4:
                if (currentQuestion.getAnswer() == answerNr) {
                    rb4.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.correct_answer));
                    respuestaCorrecta++;
                    txtvwCorrecto.setText(getString(R.string.correctas)   + String.valueOf(respuestaCorrecta));

                } else {
                    // la pregunta es incorecto cabiamos el background del radiobuton
                    rbSelected.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.wrong_answer));
                    respuestaIncorrecta++;
                    txtvwIncorrecto.setText(getString(R.string.incorrectas) + String.valueOf(respuestaIncorrecta));
                }
                break;
        }
        puntaje = (respuestaCorrecta * 10) - (respuestaIncorrecta * 10);
        txtvwPuntaje.setText(getString(R.string.puntos) + String.valueOf(puntaje));

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showQuestions();
            }
        }, 1000);
    }

    // se guarda el historial o estado de la variables como categoria, respuestas, puntaje cundo el usario dese salir para recuperas su estado cundo inicie secion
    public void guardarHistorial(int contPreguntas) {
        SharedPreferences.Editor editor = getActivity().getSharedPreferences("datos", MODE_PRIVATE).edit();
        editor.putInt("cont", cont);
        editor.putInt("respuestaCorrecta", respuestaCorrecta);
        editor.putInt("respuestaIncorrecta", respuestaIncorrecta);
        editor.putInt("puntaje", puntaje);
        editor.putInt("contPreguntas", contPreguntas);
        editor.apply();
    }
}