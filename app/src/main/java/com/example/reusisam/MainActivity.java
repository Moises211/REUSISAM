package com.example.reusisam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private EditText etAMUser;
    private EditText etAMPassword;
    private TextView tvAMStatus;
    private Button btAMLogin;
    //private Button btAMRegister;
    private Button btAMExit;
    private CheckBox cbAMPassword;
    private Toolbar toolbar;
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
        //btAMRegister = findViewById(R.id.bt_AMRegister);
        btAMExit = findViewById(R.id.bt_AMExit);
        cbAMPassword = findViewById(R.id.cb_AMPassword);

        toolbar = findViewById(R.id.toolbarAR);
        toolbar.setTitle("Login de Usuario");
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);

        preferences = getSharedPreferences("users", Context.MODE_PRIVATE);

        btAMLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidarAcceso();
            }
        });

        /*btAMRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AbrirRegistro();
            }
        });*/

        btAMExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Salir();
            }
        });

        cbAMPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etAMPassword.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                }else{
                    etAMPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                }
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
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int idmenu = item.getItemId();
        if(idmenu == R.id.btnRegistrar_menu){
            AbrirRegistro();
            return true;
        }
        if(idmenu == R.id.btnSalir_menu ) return Salir();

        return super.onOptionsItemSelected(item);
    }

    private void AbrirRegistro() {
        Intent intent = new Intent(this, RegistarActivity.class);
        startActivity(intent);
    }

    public boolean Salir() {
        finishAffinity();
        return true;
    }
}


