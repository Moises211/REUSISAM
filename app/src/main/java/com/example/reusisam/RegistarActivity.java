package com.example.reusisam;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.reusisam.DTOS.RecordDTO;

import java.io.Console;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RegistarActivity extends AppCompatActivity {
    private Toolbar ARtoolbar;
    private EditText etARConfirmPassword ;
    private EditText etARPassword ;
    private EditText etARName ;
    private EditText etAREmail ;
    private CheckBox cbARConfirmPassword, cbARPassword;
    private Button btARSave, btARReturn;
    private SharedPreferences preferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registar);

        etARConfirmPassword = findViewById(R.id.et_ARConfirmPassword);
        etARPassword = findViewById(R.id.et_ARPassword);
        etARName = findViewById(R.id.et_ARName);
        etAREmail = findViewById(R.id.et_AREmail);

        ARtoolbar = findViewById(R.id.toolbarAR);
        ARtoolbar.setTitle("Nuevo Usuario");
        ARtoolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(ARtoolbar);

        btARReturn = findViewById(R.id.bt_ARReturn);
        btARSave = findViewById(R.id.bt_ARSave);

        cbARPassword = findViewById(R.id.cb_ARPassword);
        cbARConfirmPassword = findViewById(R.id.cb_ARConfirmPassword);

        cbARPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etARPassword.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                }else{
                    etARPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                }
            }
        });

        cbARConfirmPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    etARConfirmPassword.setTransformationMethod(android.text.method.HideReturnsTransformationMethod.getInstance());
                }else{
                    etARConfirmPassword.setTransformationMethod(android.text.method.PasswordTransformationMethod.getInstance());
                }
            }
        });



        btARReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regresar();
            }
        });

        btARSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Guardar()){
                    Toast.makeText(RegistarActivity.this, "Usuario guardado con exito",
                            Toast.LENGTH_LONG).show();
                }
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_registrar, menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int idmenu = item.getItemId();

        if(idmenu == R.id.btnSalir_MenuRegistrar) {
            finishAffinity();
        };

        return super.onOptionsItemSelected(item);
    }

    public boolean Regresar() {
        try {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        } catch (Exception ex) {
            System.out.println("Error:" + ex);
            throw new RuntimeException(ex);
        }
    }

    public boolean Guardar() {
        try {

            preferences = getSharedPreferences("users", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            RecordDTO record = Verificar();
            String clave = etARName.getText().toString().trim();

            String email = etAREmail.getText().toString();
            String password = etARPassword.getText().toString();
            String confirmPassword = etARConfirmPassword.getText().toString();
            System.out.println("Click en guardar");

            if (!record.estado) {
                System.out.println("Click en guardar 2: " + record.valores);
                if (record.valores.isEmpty()) {
                    //System.out.println("Click en guardar usuario");
                    etARName.setError(record.clave);
                    return false;
                }
                if (record.valores.get("email") != null && record.clave.equals("email")) {
                    //System.out.println("Click en guardar email");
                    etAREmail.setError(record.valores.get("email"));
                    return false;
                }
                if (record.valores.get("password") != null && record.clave.equals("password")) {
                    //System.out.println("Click en guardar passw");
                    etARPassword.setError(record.valores.get("password"));
                    return false;
                }
                if (record.valores.get("confirmPassword") != null && record.clave.equals("confirmPassword")) {
                    //System.out.println("Click en guardar confirm");
                    etARConfirmPassword.setError(record.valores.get("confirmPassword"));
                    return false;
                }
                //System.out.println("Click en guardar 3");
            } else {
                //System.out.println("Click en guardados ya");
                Set<String> datos = new HashSet<>();
                datos.add("email:" + record.valores.get("email"));
                datos.add("password:" + record.valores.get("password"));
                editor.putStringSet(clave, datos);
                editor.apply();
                LimpiarCampos();
                return true;
            }
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public RecordDTO Verificar() {
        String clave = etARName.getText().toString().trim();

        String email = etAREmail.getText().toString();
        String password = etARPassword.getText().toString();
        String confirmPassword = etARConfirmPassword.getText().toString();
        preferences = getSharedPreferences("users", Context.MODE_PRIVATE);

        Map<String, String> valores = new HashMap<>();
        String key = "";
        //Validar usuario
        if (noVacio(clave)) {
            if (clave.length() > 3) {
                if (!preferences.contains(clave)) {
                    System.out.println(clave);
                    key = clave;
                } else {
                    return new RecordDTO(false, valores, "usuario ya registrado");
                }
            } else {
                return new RecordDTO(false, valores, "usuario muy corto");
            }
        } else {
            return new RecordDTO(false, valores, "ingresar usuario");
        }
        if (noVacio(key)) {
            //validar email
            if (noVacio(email)) {
                /*int coincidencias = 0;
                int i = email.indexOf("@");

                while (i != -1) {
                    coincidencias++;
                    i = email.indexOf("@", i + "@".length());
                }*/

                //if ((coincidencias == 1)) {
                //Patterns.EMAIL_ADDRESS.matcher(email).matches() - funciona como estandar para validar correos no solo secuencia sino tambien validar
                //los sufijos actuales en direcciones*/
                if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    valores.put("email", email);
                } else {
                    valores.put("email", "Email invalido");
                    return new RecordDTO(false, valores, "email");
                }
            } else {
                valores.put("email", "Ingresar Email");
                return new RecordDTO(false, valores, "email");
            }
            String validatePass;
            //validar contraseña
            if (!((validatePass = PasswordValidated(password)).isEmpty())) {
                valores.put("password", validatePass);
                return new RecordDTO(false, valores, "password");
            }
            //validadar confirmacion de contraseña
            if (!((validatePass = PasswordValidated(confirmPassword)).isEmpty())) {
                valores.put("confirmPassword", validatePass);
                return new RecordDTO(false, valores, "confirmPassword");
            }
            //Comprobar que tanto el confirmar como la original sean iguales
            if (password.equals(confirmPassword)) {
                valores.put("password", password);
            } else {
                valores.put("confirmPassword", "Las contraseñas deben ser iguales");
                return new RecordDTO(false, valores, "confirmPassword");
            }
        }
        return new RecordDTO(true, valores, key);
    }

    public boolean noVacio(String valor) {
        if (!(valor.isBlank() || valor.isEmpty())) {
            return true;
        } else {
            return false;
        }
    }

    public String PasswordValidated(String password) {
        if (noVacio(password)) {
            if (password.length() > 5) {
                boolean hvChart = false;
                boolean hvDigit = false;
                for (int i = 0; i < password.length(); i++) {
                    char e = password.charAt(i);
                    if (Character.isLetter(e)) hvChart = true;
                    if (Character.isDigit(e)) hvDigit = true;

                    if (hvChart && hvDigit) {
                        return "";
                    }
                }
                if (!hvChart || !hvDigit) {
                    return "Debe incluir letras y numeros";
                }
            } else {
                return "Contaseña corta";
            }
        } else {
            return "Ingresar contraseña";
        }
        return "";
    }

    public void LimpiarCampos(){
        etARName.getText().clear();
        etAREmail.getText().clear();
        etARPassword.getText().clear();
        etARConfirmPassword.getText().clear();
    }

}