package com.example.sistemadeanlisisdegrafos;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pantalla_de_carga extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);

        new Handler() .postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(Pantalla_de_carga.this, inicio.class);
                startActivity(intent);
                finish();
            }
        },6000);

    }
}
