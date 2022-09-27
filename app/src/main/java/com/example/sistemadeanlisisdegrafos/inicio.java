package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class inicio extends AppCompatActivity implements View.OnClickListener{

    Button Def, EuC, Dijs, Krus, Prim,  Floyd, DivVen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        Def = (Button) findViewById(R.id.btn1);
        EuC = (Button) findViewById(R.id.btn2);
        Krus = (Button) findViewById(R.id.btn3);
        Dijs = (Button) findViewById(R.id.btn4);
        Prim = (Button) findViewById(R.id.btn5);
        Floyd = (Button) findViewById(R.id.btn6);
        DivVen = (Button) findViewById(R.id.btn7);

        Def.setOnClickListener(this);
        EuC.setOnClickListener(this);
        Dijs.setOnClickListener(this);
        Krus.setOnClickListener(this);
        Prim.setOnClickListener(this);
        Floyd.setOnClickListener(this);
        DivVen.setOnClickListener(this);

    }

    // Redirigir a la actividad definicion_de_grafos

  /*  public void DG (View view){
        Intent DG = new Intent(this, definicion_de_grafos.class);
        startActivity(DG);
    }

    // Redirigir a la actividad euleriana_conexa

    public void EC (View view){
        Intent EC = new Intent(this, euleriana_conexa.class);
        startActivity(EC);
    }

    // Redirigir a la actividad kruskal

    public void K (View view){
        Intent K = new Intent(this, kruskal.class);
        startActivity(K);
    }

    // Redirigir a la actividad dijkstra

    public void D (View view){
        Intent D = new Intent(this, dijkstra.class);
        startActivity(D);
    }

    // Redirigir a la actividad prim

    public void P (View view){
        Intent P = new Intent(this, prim.class);
        startActivity(P);
    }

    // Redirigir a la actividad informacion

    public void I (View view){
        Intent I = new Intent(this, informacion.class);
        startActivity(I);
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.btn1:
                Intent DG = new Intent(this, definicion_de_grafos.class);
                startActivity(DG);
                break;

            case R.id.btn2:
                Intent EC = new Intent(this, euleriana_conexa.class);
                startActivity(EC);
                break;

            case R.id.btn3:
                Intent K = new Intent(this, kruskal.class);
                startActivity(K);
                break;

            case R.id.btn4:
                Intent D = new Intent(this, dijkstra.class);
                startActivity(D);
                break;

            case R.id.btn5:
                Intent P = new Intent(this, prim.class);
                startActivity(P);
                break;

            case R.id.btn6:
                Intent F = new Intent(this, floyd.class);
                startActivity(F);
                break;
            case R.id.btn7:
                Intent DV = new Intent(this, divide_y_venceras.class);
                startActivity(DV);
                break;
        }
    }

    @Override public boolean onCreateOptionsMenu(Menu mimenu){
        getMenuInflater().inflate(R.menu.menu_inicio, mimenu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem op_menu){
        int id=op_menu.getItemId();

        if(id == R.id.Dic){
            ejecu_dic(null);
            return true;
        }

        if (id == R.id.info){
            ejecu_info(null);

            return true;
        }
        if (id == R.id.Tu){
            Intent i = new Intent (Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCQezBBpLS48ZAyryKrXlrTw"));
            startActivity(i);
        }

        return super.onOptionsItemSelected(op_menu);
    }

    public void ejecu_info(View view){
        Intent i = new Intent(this, Info.class);
        startActivity(i);
    }

    public void ejecu_dic(View view){
        Intent I = new Intent(this, informacion.class);
        I.putExtra("indice", 0);
        startActivity(I);
    }
}
