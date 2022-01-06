package com.johnestebanap.juegodepreguntassofka;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


public class RegistroActivity extends AppCompatActivity {
    //private FirebaseFirestore db;
    //private String userId;
    private FirebaseAuth mAuth;
    private EditText editTextNombreUsuario, editContrasenia, editTextNombre, editTextApellido;
    private CheckBox checkBox;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        mAuth = FirebaseAuth.getInstance();
        //  db = FirebaseFirestore.getInstance();

        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        editContrasenia = findViewById(R.id.editContraseña);
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        checkBox = findViewById(R.id.checkBox);
        Button btnfinalizar = findViewById(R.id.finalizar);

        btnfinalizar.setOnClickListener(view -> createUser());
    }

    public void createUser() {
        String emailUser = editTextNombreUsuario.getText().toString();
        String password = editContrasenia.getText().toString();
        String name = editTextNombre.getText().toString();
        String apellido = editTextApellido.getText().toString();

        if (TextUtils.isEmpty(emailUser)) {
            Toast.makeText(this, R.string.completar_correo, Toast.LENGTH_SHORT).show();
            editTextNombreUsuario.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, R.string.completar_contasenia, Toast.LENGTH_SHORT).show();
            editContrasenia.requestFocus();
        } else if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, R.string.completar_nombre, Toast.LENGTH_SHORT).show();
            editTextNombre.requestFocus();
        } else if (TextUtils.isEmpty(apellido)) {
            Toast.makeText(this, R.string.completar_apellido, Toast.LENGTH_SHORT).show();
            editTextApellido.requestFocus();
        } else if (checkBox.isChecked()) {
            Toast.makeText(this, R.string.checkboc_registro, Toast.LENGTH_SHORT).show();
            editTextApellido.requestFocus();
        } else {
            mAuth.createUserWithEmailAndPassword(emailUser, password).addOnCompleteListener(
                    task -> {
                        if (task.isSuccessful()) {
                         /*   userId = mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = db.collection("users").document(userId);

                            Map<String, Object> user = new HashMap<>();
                            user.put("Email", emailUser);
                            user.put("Nombre", name);
                            user.put("Apellido", apellido);
                            // Registro los datos nuevos con el metodo del set.(user)
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(@NonNull Void unused) {
                                    Toast.makeText(RegistroActivity.this, R.string.datos_registrados, Toast.LENGTH_SHORT).show();
                                }
                            });*/
                            showAlertExito();
                            showHome(emailUser);
                        } else {
                            showAlertError();
                        }
                    });
        }
    }

    private void showHome(String email) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    private void showAlertExito() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistroActivity.this);
        alertDialog.setTitle(R.string.felicidades);
        alertDialog.setMessage(R.string.regsitro_exitoso);
        alertDialog.setPositiveButton(R.string.aceptar, null);
        alertDialog.create().show();
    }

    private void showAlertError() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegistroActivity.this);
        alertDialog.setTitle(R.string.error);
        alertDialog.setMessage(R.string.alert_error);
        alertDialog.setPositiveButton(R.string.aceptar, null);
        alertDialog.create().show();
    }

}