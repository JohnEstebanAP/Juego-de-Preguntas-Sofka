package com.johnestebanap.juegodepreguntassofka.fragments;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.johnestebanap.juegodepreguntassofka.R;

public class HomeFragment extends Fragment {


    Button clearBtn;

    //Constructor de la clase HomeFragment
    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragmen
        View view = inflater.inflate(R.layout.fragment_home, container, false);


        Button play = view.findViewById(R.id.btn_play);

        //boton para iniciar el juego y pasar al fragment del juego
        play.setOnClickListener(v -> {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container_fragment, new GameFragment());
            fragmentTransaction.commit();
        });

        clearBtn = view.findViewById(R.id.btn_clear);
        //metodo para limpiar o formatiar a 0 los valores
        clearBtn.setOnClickListener(v ->
        {
            //se asigna a prefe el documento llamado data de sharedPreferences
            SharedPreferences prefe = getActivity().getSharedPreferences("datos", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = prefe.edit();
            editor.putInt("cont", 1);
            editor.putInt("respuestaCorrecta", 0);
            editor.putInt("respuestaIncorrecta", 0);
            editor.putInt("puntaje", 0);
            editor.putInt("contPreguntas", 1);
            editor.apply();

            Toast.makeText(getContext(), "Datos Limpiados", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}