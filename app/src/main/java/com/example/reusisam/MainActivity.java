package com.example.reusisam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText etAMUser;
    private EditText etAMPassword;
    private TextView tvAMStatus;
    private Button btAMLogin;
    private Button btAMRegister;
    private Button btAMExit;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        etAMUser = findViewById(R.id.et_AMUser);
        etAMPassword = findViewById(R.id.et_AMPassword);
        tvAMStatus = findViewById(R.id.tv_AMStatus);
        btAMLogin = findViewById(R.id.bt_AMLogin);
        btAMRegister = findViewById(R.id.bt_AMRegister);
        btAMExit = findViewById(R.id.bt_AMExit);

        preferences = getSharedPreferences("users", Context.MODE_PRIVATE);

        btAMLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarAcceso();
            }
        });

        btAMRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirRegistro();
            }
        });

        btAMExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Salir();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void ValidarAcceso() {
        String usuario = etAMUser.getText().toString().trim();
        String password = etAMPassword.getText().toString();

        if (usuario.isEmpty()) {
            etAMUser.setError("Ingrese el usuario");
            return;
        }

        if (password.isEmpty()) {
            etAMPassword.setError("Ingrese la contraseña");
            return;
        }

        if (!preferences.contains(usuario)) {
            tvAMStatus.setText("El usuario no existe. Debe registrarse primero.");
            Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_LONG).show();
            return;
        }

        Set<String> datos = preferences.getStringSet(usuario, null);

        if (datos == null) {
            tvAMStatus.setText("No fue posible leer los datos guardados.");
            return;
        }

        String passwordGuardada = obtenerValor(datos, "password:");

        if (password.equals(passwordGuardada)) {
            Toast.makeText(this, "Acceso correcto", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            etAMPassword.setError("Contraseña incorrecta");
            tvAMStatus.setText("La contraseña no coincide con el usuario ingresado.");
        }
    }

    private String obtenerValor(Set<String> datos, String prefijo) {
        for (String dato : datos) {
            if (dato.startsWith(prefijo)) {
                return dato.substring(prefijo.length());
            }
        }
        return "";
    }

    private void AbrirRegistro() {
        Intent intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);
    }

    public boolean Salir() {
        finish();
        return true;
    }
}
    public boolean Salir(){
        finish();
        return  true;
    }
}
