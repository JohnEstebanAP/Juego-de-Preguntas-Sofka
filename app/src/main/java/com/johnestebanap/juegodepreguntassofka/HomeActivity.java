package com.johnestebanap.juegodepreguntassofka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.dynamic.SupportFragmentWrapper;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.johnestebanap.juegodepreguntassofka.fragments.GameFragment;
import com.johnestebanap.juegodepreguntassofka.fragments.HistoryFragment;
import com.johnestebanap.juegodepreguntassofka.fragments.HomeFragment;

/**
 * [Actividad para mostrar la pantalla de heme activity]
 * Clase que extiende de AppCompatActivity
 * En esta clase se crea el menú lateral y el menú de navegación,
 * además del fragmento donde se mostrará la información.
 *
 * @author Santiago Ospino Osorio - santiago.m200@outlook.es
 * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
 * @version 1.0.0
 * @since Esta presente desde la version 1.0.0
 */
public class HomeActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigation;

    private FirebaseAuth mAuth;

    /**
     * [Crea la actividad, método de inicio]
     *
     * @param savedInstanceState Elemento de tipo Bundle.
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.topAppBar);
        drawerLayout = findViewById(R.id.drawer_layaout);
        navigationView = findViewById(R.id.navigation_view);
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // El Fragment que se muestra por defecto
        showHome();

        // consigo los extras enviados con el Intent y verifico que no se un valor nulo o este vasido
        if (getIntent().getExtras().getString(getString(R.string.email)) != null && !TextUtils.isEmpty(getIntent().getExtras().getString(String.valueOf(R.string.email)))) {
            String email = getIntent().getExtras().getString(String.valueOf(R.string.email));

            //Es nesesario buscar toda la ruta para encontrar el elemento qeu queremos modificar
            TextView correoUser = navigationView.getHeaderView(0).findViewById(R.id.nav_header_tvcorreo);
            correoUser.setText(email);
        }

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.open_nav,
                R.string.close_nav);//leyendo el nav drawer
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(item ->
        {
            switch (item.getItemId()) {
                case R.id.mn_home:
                    showHome();
                    return true;
                case R.id.mn_settings:
                    return true;
                case R.id.mn_info:
                    return true;
                case R.id.mn_salir:
                    showAlerSalir();
                    return true;
                default:
                    return false;
            }
        });

        bottomNavigation.setSelectedItemId(R.id.bnv_game);
        bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bnv_menu:
                    showHome();
                    break;
                case R.id.bnv_game:
                    showGame();
                    break;
                case R.id.bnv_historial:
                    showRecord();
                    break;
                default:
                    break;
            }
            return true;
        });

        mAuth = FirebaseAuth.getInstance();
        String email = getIntent().getExtras().getString("email");
        //Guardar datos
        SharedPreferences prefs = (SharedPreferences) getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.commit();
    }


    /**
     * [Método para cerrar la app y desligar al usuario.]
     *
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    public void closeAndDelogearse() {
        Toast.makeText(this, "sesión cerrada", Toast.LENGTH_SHORT).show();
        //borrar datos de secion
        mAuth.signOut();
        //eliminamos los datos del SharedPreferences
        getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit().clear().apply();
    }

    /**
     * [Realiza el cambio de estado del Fragmento padre reemplazandolo por el fragmento del HomeFragment]
     *
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    public void showHome() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HomeFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    /**
     * [Realiza el cambio de estado del Fragmento padre reemplazandolo por el fragmento del GameFragment]
     *
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    public void showGame() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new GameFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    /**
     * [Realiza el cambio de estado del Fragmento padre reemplazandolo por el fragmento del HistoryFragment]
     *
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    public void showRecord() {
        getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HistoryFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
    }


    /**
     * [Metodo para mostrar alerta para salir de la app]
     *
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    private void showAlerSalir() {
        String title = "¿Esta seguro de querer salir?";
        String message = "Si sale de la aplicacíon perderá todo el progreso y se cancelara la sesion";
        String btnNegative = "Canselar";
        String btnPositive = "Salir";

        new MaterialAlertDialogBuilder(this)
                .setTitle(title)
                .setMessage(message)
                .setNegativeButton(btnNegative, (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton(btnPositive, (dialogInterface, i) -> {
                    closeAndDelogearse();
                    super.finishAndRemoveTask();
                })
                .show();
        /*
        super.finish(); // para finalisar mi activadad
        super.finishAffinity();//Serar la aplicacion completa
        super.finishAndRemoveTask();//finalisar la actividad y remover en la vasurera para que no quede abierta en segundo plano
        */
    }

    //Contador pra medir los milesegundos
    long count1 = 0;

    /**
     * [Método para controlar las acciones del botón de asia atras del dispositivo.]
     * Sobreescritura del método
     *
     * @author Santiago Ospino Osorio - santiago.m200@outlook.es
     * John Esteban Alvarez Piedrahita - esteban.ea145@gmail.com
     * @since [1.0.0]
     */
    @Override
    public void onBackPressed() {

        //condifison qeu verifica si el el valor 2000 es mayor a los milisegundos del sistema
        if (count1 + 2000 > System.currentTimeMillis()) {
            //close app
            super.finishAffinity();
        } else {

            Toast.makeText(this, getString(R.string.onbackpressed_exit), Toast.LENGTH_SHORT).show();
            // se formatea el contador
            count1 = System.currentTimeMillis();
        }
    }
}