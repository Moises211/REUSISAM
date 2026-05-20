package com.example.reusisam;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            cargarFragment(new InicioFragment());
        }

        bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragmentSeleccionado = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_inicio) {
                fragmentSeleccionado = new InicioFragment();
            } else if (itemId == R.id.nav_productos) {
                fragmentSeleccionado = new ProductosFragment();
            } else if (itemId == R.id.nav_perfil) {
                fragmentSeleccionado = new PerfilFragment();
            }

            if (fragmentSeleccionado != null) {
                cargarFragment(fragmentSeleccionado);
                return true;
            }

            return false;
        });
    }

    private void cargarFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainerView, fragment)
                .commit();
    }
}