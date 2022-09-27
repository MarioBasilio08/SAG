package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class divide_y_venceras extends AppCompatActivity implements View.OnClickListener{
    int c=0, s;
    int sucesion[];
    int bandera = 0;
    Button V, G, anali, Otro, exportar1, Grafo1;
    Drawable estilo_boton1,estilo_boton2;
    EditText textV, textG;
    TextView Resul, mensa;
    String res, auxG;
    private static String suce="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide_y_venceras);
        estilo_boton1 = getDrawable(R.drawable.estilo_boton2);
        estilo_boton2 = getDrawable(R.drawable.estilo_boton4);

        V = (Button) findViewById(R.id.btnGuardar1);
        G = (Button) findViewById(R.id.btnInformacion1);
        anali = (Button) findViewById(R.id.btnAnalizar1);
        textV = (EditText) findViewById(R.id.editTextV1);
        //textG = (EditText) findViewById(R.id.editTextG);
        Resul = (TextView) findViewById(R.id.textR1);
        Otro = (Button) findViewById(R.id.btnLimpiar1);
        mensa = (TextView) findViewById(R.id.textMensaje1);
        Grafo1 = (Button) findViewById(R.id.btng6);
        exportar1 = (Button) findViewById(R.id.btnex6);

        V.setOnClickListener(this);
        G.setOnClickListener(this);
        anali.setOnClickListener(this);
        Otro.setOnClickListener(this);
        Grafo1.setOnClickListener(this);
        exportar1.setOnClickListener(this);

        Otro.setEnabled(true);
        anali.setEnabled(false);
        //anali.setTextColor(0xFF9c9c9c);
        anali.setBackground(estilo_boton2);
        Grafo1.setVisibility(View.GONE);
        exportar1.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar1:
                String ver;
                String gra;
                int n;

                if(bandera == 0) {
                    bandera = 1;
                    ver = textV.getText().toString();
                    textV.setText("");
                    if (ver.equals("")) {
                        Toast.makeText(this, "ERROR Campo vacios", Toast.LENGTH_LONG).show();
                    } else {
                        s = Integer.parseInt(ver);
                        if (s <= 0) {
                            Toast.makeText(this, "ERROR El valor no puede ser 0", Toast.LENGTH_LONG).show();
                        } else {
                            mensa.setText("Ingrese grado del vértice ("+c+")");
                            CrearS(s);
                            textV.setHint("NUM. GRADO");
                        }
                    }
                }else{
                    gra = textV.getText().toString();
                    textV.setText("");

                    if(gra.equals("")){
                        Toast.makeText(this,"ERROR Campo vacio", Toast.LENGTH_LONG).show();
                    }else{
                        if(c<=s){
                            n = Integer.parseInt(gra);
                            Ingresar(n, sucesion, c);
                            Toast.makeText(this,"Valor del vertice "+c+" ingresado", Toast.LENGTH_LONG).show();
                            c++;
                            mensa.setText("Ingrese grado del vértice ("+c+")");
                        }

                        if(c == s){
                            mensa.setText("Grados completos :)");
                            V.setEnabled(false);
                            V.setTextColor(0xFF9c9c9c);
                            anali.setEnabled(true);
                            anali.setBackground(estilo_boton1);
                            anali.setTextColor(0xFFFFFFFF);
                        }
                    }
                }
                break;

            case R.id.btnAnalizar1:
                auxG = "";
                suce = "Matriz sin ordenar: ";

                for(int i=0; i<s; i++){
                    suce += String.valueOf(sucesion[i]) + " ";
                }
                auxG = suce;
                suce += "\n";
                quicksort(sucesion, 0, (s-1));
                suce = suce + "\nMatriz ordenada: ";

                for(int i=0; i<s; i++){
                    suce += String.valueOf(sucesion[i]) + " ";
                }
                res = suce;
                Resul.setText(suce);

                anali.setEnabled(false);
                //anali.setTextColor(0xFF9c9c9c);
                Grafo1.setVisibility(View.VISIBLE);
                exportar1.setVisibility(View.VISIBLE);
                break;
            case R.id.btnInformacion1:
                Intent I = new Intent(this, informacion.class);
                I.putExtra("indice", 7);
                startActivity(I);
                break;
            case R.id.btnLimpiar1:
                mensa.setText("Ingrese el número de vértices");
                V.setEnabled(true);
                res="";
                bandera = 0;
                V.setTextColor(0xFFFFFFFF);
                textV.setHint("NUM. VÉRTICES");
                //anali.setTextColor(0xFF9c9c9c);
                c = 0;
                anali.setBackground(estilo_boton2);
                Resul.setText("");
                Grafo1.setVisibility(View.GONE);
                exportar1.setVisibility(View.GONE);
                break;

            case R.id.btng6:
                Intent G = new Intent(this, grafos.class);
                G.putExtra("Vertice", s);
                G.putExtra("Pantalla", 7);
                G.putExtra("Sucesion",sucesion);
                G.putExtra("SinOrden",auxG);
                startActivity(G);
                break;
            case R.id.btnex6:
                Intent Nom = new Intent(this, Nombre_Fichero.class);
                Nom.putExtra("Resultados", res);
                startActivity(Nom);
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

    public void CrearS(int s){
        sucesion = new int[s];
    }

    public static boolean Ingresar(int s, int suc[], int pos){
        suc[pos] = s;
        return true;
    }

    public static boolean quicksort(int A[], int izq, int der) {

        int aux;
        int pivote=A[izq]; // tomamos primer elemento como pivote
        int i=izq;         // i realiza la búsqueda de izquierda a derecha
        int j=der;         // j realiza la búsqueda de derecha a izquierda


        while(i < j){                          // mientras no se crucen las búsquedas
            while(A[i] <= pivote && i < j) i++; // busca elemento mayor que pivote
            while(A[j] > pivote) j--;           // busca elemento menor que pivote
            if (i < j) {                        // si no se han cruzado
                aux= A[i];                      // los intercambia
                A[i]=A[j];
                A[j]=aux;
            }
        }

        A[izq]=A[j];      // se coloca el pivote en su lugar de forma que tendremos
        A[j]=pivote;      // los menores a su izquierda y los mayores a su derecha

        if(izq < j-1)
            quicksort(A,izq,j-1);          // ordenamos subarray izquierdo
        if(j+1 < der)
            quicksort(A,j+1,der);          // ordenamos subarray derecho

        imprimir(A, der);
        return true;
    }

    public static void imprimir(int A[], int der){
        int i;
        String cadena="";
        suce = suce + "\nordenando: ";
        for(i=0; i<der;i++){
            cadena += " " + A[i]+ " ";
            suce += " " + A[i]+ " ";
        }
        suce+="\n";
        System.out.println(cadena);
    }
}