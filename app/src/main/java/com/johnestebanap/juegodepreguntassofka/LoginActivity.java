package com.johnestebanap.juegodepreguntassofka;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import android.app.AlertDialog;
import android.content.Intent;
//import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
//import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
//import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    //Se Creo una instancia o un ojeto, de la autenticacion de firevase
    private FirebaseAuth mAuth;

    //Se Crean las variables para el SharedPreferences
    /*SharedPreferences preferences;
    SharedPreferences.Editor editor;*/

    Button btnEntrar, btnRegistro, btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //a la variable mAuth se Le asina os e inicializa asinandole el ocjeto de La utenticacion de firebase
        mAuth = FirebaseAuth.getInstance();

        //se ase una conecion o se instacian los botones creados en el Layout con los botones del codigo
        btnEntrar = findViewById(R.id.btn_entrar);
        btnRegistro = findViewById(R.id.registrarse);
        btnGoogle = findViewById(R.id.btn_google);


        EditText ediTxtPassword = findViewById(R.id.txtPassword);
        ediTxtPassword.setText("12345678");

        //Landa para catura el clik del boton para verificar las credenciales y entrar al juego
        btnEntrar.setOnClickListener(view -> loginUser());
        //catura el clik del boton para registrar un usuario nuevo y entrar al juego
        btnRegistro.setOnClickListener(view -> registroUser());
        //catura el clik del boton para registrar un usuario nuevo con la cuneta de google y entrar al juego
        btnGoogle.setOnClickListener(view -> loginUserGoogle());
    }

    public void loginUserGoogle() {
        //Configuracion
        GoogleSignInOptions googleConf = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        GoogleSignInClient googleClient = GoogleSignIn.getClient(this, googleConf);
        googleClient.signOut();

      //  Metodo deprecado
      //  startActivityForResult(googleClient.getSignInIntent(), GOOGLE_SING_IN);   onActivityResult(result)
        someActivityResultLauncher.launch(googleClient.getSignInIntent());
    }

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            //lambda de new ActivityResultCallback<ActivityResult>()   onActivityResult(result)
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    //En este metodo no tenemos el requestCode por lo que no podemos hacer uso de el
                    //if(requestCode == GOOGLE_SING_IN(100)){

                    Intent data = result.getData();
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    try {
                        GoogleSignInAccount account = task.getResult(ApiException.class);
                        if (account != null) {

                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

                            //se envia la creencial del correo electronico de google realisaxondun un reguitro de este correo
                            //si se puede reguistrar o ya esta reguistrado el task.isSuccessful retorna true y pasa al home, si no se puede registrar o verificar la cuenta
                            // muestra una alerta de error ya que no se pudo autentica al usuario.
                            FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(
                                    //Lambda del metodo OnCompleteListener<Authresult>()
                                    task1 -> {
                                        if (task1.isSuccessful()) {
                                            showHome(account.getEmail());
                                        } else {
                                            showAlertError();
                                            Log.w("TAG", "Error", task1.getException());
                                        }
                                    }
                            );
                        }
                    } catch (ApiException e) {
                        e.printStackTrace();
                        showAlertError();
                    }
                }
            });

    public void loginUser() {
        //Creo las variables para los EditText de Usuario y contraseña
        EditText editTxtUser, ediTxtPassword;
        //se Relisa la intacia o asignacion de los votones con su corespondiente id
        editTxtUser = findViewById(R.id.txtUser);
        ediTxtPassword = findViewById(R.id.txtPassword);

        //A las variables se les asina el texto de el Email o usaurio y las contraseña ingresados en las editText
        String email = editTxtUser.getText().toString();

        String password = ediTxtPassword.getText().toString();

        //Comprobamos que la varible email no este basido o sea nula
        //nota TextUtils.isEmpty es mejor ya que este no jenera Execiones en caso de que la bariable sea nula como lo puede hacer el metodo isNotEmpty()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, R.string.inputCorreo, Toast.LENGTH_SHORT).show();
            editTxtUser.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.input_contraseña, Toast.LENGTH_SHORT).show();
            ediTxtPassword.requestFocus();
        } else {
          /*  if (user1.equals(email) && pass.equals(password)) {
                preferences = getSharedPreferences("guest", MODE_PRIVATE);
                editor = preferences.edit();
                editor.putString("Name", "user");
                editor.putString("Pass", "123456789");
                editor.putString("rol", "1");
                editor.commit();
                showHome(email);
            } */

            //se envia el correo y la contraseña para verificar si el correo o el usuairo esta reguistrado
            //si esta reguistrado el task.isSuccessful retorna true y pasa al home, si no esta reguistrado
            // muestra una alerta de error ya que no se pudo autentica al usuario.

            if(password.equals("12345678")){
                 showHome(email);
            }

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                    //Lambda del metodo OnCompleteListener<AuthResult>()
                    task -> {
                        if (task.isSuccessful()) {
                            showHome(email);
                        } else {
                            showAlertError();
                            Log.w("TAG", "Error", task.getException());
                        }
                    }
            );

        }
    }

    //Metodo para pasar a al activity del Home
    private void showHome(String email) {
        //se Crea al intent del Home
        Intent intent = new Intent(this, HomeActivity.class);
        //al intent se le pone un estra que es el email para pasarselo a la activity del Home
        intent.putExtra("email", email);
        startActivity(intent);
    }

    private void showAlertError() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Error");
        alertDialog.setMessage(R.string.ErrorAuth);
        alertDialog.setPositiveButton(R.string.aceptar, null);
        alertDialog.create().show();
    }

    public void registroUser() {
        startActivity(new Intent(this, RegistroActivity.class));
    }
}