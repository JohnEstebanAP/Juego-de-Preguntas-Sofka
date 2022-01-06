package com.johnestebanap.juegodepreguntassofka;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
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
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    BottomNavigationView bottomNavigation;

    private FirebaseAuth mAuth;

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

        // consigo los extras enviados con el Intent, i verifico que no se un valor nulo o este sasido
        if (getIntent().getExtras().getString("email") != null && !TextUtils.isEmpty(getIntent().getExtras().getString("email"))) {
            String email = getIntent().getExtras().getString("email");

            //Es nesesario buscar toda la ruta para encontrar el elemento qeu queremos modificar
            TextView correoUser= navigationView.getHeaderView(0).findViewById(R.id.nav_header_tvcorreo);
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
        bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bnv_game:
                        showFragmentGame();
                        break;
                    case R.id.bnv_ocr:
                        openOcr();
                        break;
                    case R.id.bnv_qr:
                        //showQr();
                        break;
                }
                return true;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        String email = getIntent().getExtras().getString("email");
        //Guardar datos
        SharedPreferences prefs = (SharedPreferences) getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("email", email);
        editor.commit();
    }

    private void showFragmentGame() {
    }

    @Override
    public void onBackPressed() {
        String url = "";
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("url",url);
        startActivity(intent);
    }

    public void closeAndDelogearse() {
        Toast.makeText(this, "sesión cerrada", Toast.LENGTH_SHORT).show();
        //borrar datos de secion
        mAuth.signOut();
        //eliminamos los datos del SharedPreferences
        getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit().clear().apply();
    }

    public void showHome() {
       // getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new HomeFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    public void openOcr() {
       // getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new OcrFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    public void showListDocumentos() {
       // getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, new ListDocumentFragment()).setReorderingAllowed(true).addToBackStack(null).commit();
    }

    private void showAler(String title,String ms) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(ms);
        alertDialog.setPositiveButton("Aceptar", null);
        alertDialog.setNegativeButton("Canselar",null);
        alertDialog.create().show();
    }

    //Metodo para mostrar alerta para salir de la app
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
}