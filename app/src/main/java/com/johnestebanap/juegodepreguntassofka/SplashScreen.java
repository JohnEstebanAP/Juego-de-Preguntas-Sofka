package com.johnestebanap.juegodepreguntassofka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Logger;

/**
 * [Actividad para mostrar la pantalla de inicio]
 * Clase que extiende de AppCompatActivity
 * @version 1.0.0
 * @author Santiago Ospino Osorio - santiago.m200@outlook.es
 *         John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
 * @since Esta presente desde la version 1.0.0
 */
public class SplashScreen extends AppCompatActivity {

    /**
     * [Crea la actividad, método de inicio]
     *
     * @param savedInstanceState Elemento de tipo Bundle.
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     *         John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        //Este puede generar error por lo que lo adecuado es colocarlo dentro de un try catch
        try{
            //El metodo Thread nos permite utilizar hilos, este nos permite con el metodo sleep detenernos o detener nuestro porgrama en un punto espesifico por milisegundos.
            Thread.sleep(2000);//Para deternerlo durante 2 segundos
        }catch(InterruptedException e){
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

    }


    /**
     * [Parte del ciclo de vida de la actividad inicia después del onCreate.]
     *
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     *         John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    @Override
    protected void onStart() {
        super.onStart();
        //Abrimos connexion con firebase y solicitamos si hay un usuario logeado

        //se inicia el SharedPreferences para verificar si hay un usuario logeado por este medio.
        SharedPreferences prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        String email = prefs.getString("User",null);

        //verificar si ya hay una cuenta guardada con anterioridad
        if (email != null) {
            showHome(email);
        } else {
            startActivity(new Intent(this, LoginActivity.class));
        }
        //para finalizar la actividad y no quede en segundo plano abierta por
        //detrás luego de abrirla otra actividad.
        super.finish();
    }

    /**
     * [Realiza la transición de la actividad actual a la de showHome..]
     *
     * @param email Elemento de tipo String que contiene el nombre o correo del usuario realiza la operación.
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     *         John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    private void showHome(String email) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }
}