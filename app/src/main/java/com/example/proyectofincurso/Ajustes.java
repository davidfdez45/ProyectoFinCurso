package com.example.proyectofincurso;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Ajustes extends AppCompatActivity {

    Spinner Provincias;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        Provincias = findViewById(R.id.provincias);


                SharedPreferences prefs = getSharedPreferences("Provincia", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("provinciaBusqueda",Provincias.getSelectedItem().toString());
                editor.commit();

    }
}
