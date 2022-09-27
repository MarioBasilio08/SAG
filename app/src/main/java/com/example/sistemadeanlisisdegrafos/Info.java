package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        DisplayMetrics medidasVentanas = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentanas);

        int ancho = medidasVentanas.widthPixels;
        int alto = medidasVentanas.heightPixels;

        getWindow().setLayout((int) (ancho*0.85), (int) (alto *0.5));
    }
}