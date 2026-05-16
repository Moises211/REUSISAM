package com.example.reusisam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Console;

public class RegistarActivity extends AppCompatActivity {
    private Toolbar ARtoolbar;
    private EditText etARConfirmPassword, tv_ARPassword,et_ARName, etAREmail;
    private Button btARSave, btARReturn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registar);

        ARtoolbar = findViewById(R.id.toolbarAR);
        ARtoolbar.setTitle("Nuevo Usuario");
        ARtoolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(ARtoolbar);

        btARReturn = findViewById(R.id.bt_ARReturn);
        btARSave = findViewById(R.id.bt_ARSave);

        btARReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Regresar();
            }
        });

        btARSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Guardar();
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public boolean Regresar(){
        try {
            MainActivity mainActivity = new MainActivity();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            mainActivity.Salir();
            return true;
        }catch (Exception ex){
            System.out.println("Error:" + ex);
            throw new RuntimeException(ex);
        }
    }

    public boolean Guardar(){
        try {
            if (true){
                return Regresar();
            }else {
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}