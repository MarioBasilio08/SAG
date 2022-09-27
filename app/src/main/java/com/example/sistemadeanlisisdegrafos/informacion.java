package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class informacion extends AppCompatActivity {
    View dic, inf1, inf2, inf3, inf4, inf5, inf6, inf7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        int in = getIntent().getIntExtra("indice",-1);

        dic=findViewById(R.id.diccionario);
        inf1 = findViewById(R.id.info1);
        inf2 = findViewById(R.id.info2);
        inf3 = findViewById(R.id.info3);
        inf4 = findViewById(R.id.info4);
        inf5 = findViewById(R.id.info5);
        inf6 = findViewById(R.id.info6);
        inf7 = findViewById(R.id.info7);

        dic.setVisibility(View.GONE);
        inf1.setVisibility(View.GONE);
        inf2.setVisibility(View.GONE);
        inf3.setVisibility(View.GONE);
        inf4.setVisibility(View.GONE);
        inf5.setVisibility(View.GONE);
        inf6.setVisibility(View.GONE);
        inf7.setVisibility(View.GONE);

        textoelec(in);
    }

    public void textoelec(int n){
        switch (n){
            case 0:
                dic.setVisibility(View.VISIBLE);
                break;

            case 1:
                inf1.setVisibility(View.VISIBLE);
                break;

            case 2:
                inf2.setVisibility(View.VISIBLE);
                break;

            case 3:
                inf3.setVisibility(View.VISIBLE);
                break;

            case 4:
                inf4.setVisibility(View.VISIBLE);
                break;

            case 5:
                inf5.setVisibility(View.VISIBLE);
                break;
            case 6:
                inf6.setVisibility(View.VISIBLE);
                break;
            case 7:
                inf7.setVisibility(View.VISIBLE);
                break;
        }
    }
}