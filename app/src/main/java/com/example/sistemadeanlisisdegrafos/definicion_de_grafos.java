package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class definicion_de_grafos extends AppCompatActivity implements View.OnClickListener {

    int c=0, s;
    int sucesion[];
    int sucesion2[];
    int bandera = 0;
    int Si = 0;
    Button V, G, anali, Otro, exportar1, Grafo1;
    Drawable estilo_boton1,estilo_boton2;
    EditText textV, textG;
    TextView Resul, mensa;
    String res;
    File rut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_definicion_de_grafos);

        estilo_boton1 = getDrawable(R.drawable.estilo_boton2);
        estilo_boton2 = getDrawable(R.drawable.estilo_boton4);

        V = (Button) findViewById(R.id.btnGuardar);
        G = (Button) findViewById(R.id.btnInformacion);
        anali = (Button) findViewById(R.id.btnAnalizar);
        textV = (EditText) findViewById(R.id.editTextV);
        //textG = (EditText) findViewById(R.id.editTextG);
        Resul = (TextView) findViewById(R.id.textR);
        Otro = (Button) findViewById(R.id.btnLimpiar);
        mensa = (TextView) findViewById(R.id.textMensaje);
        Grafo1 = (Button) findViewById(R.id.btng1);
        exportar1 = (Button) findViewById(R.id.btnex1);

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
       // G.setEnabled(false);
    }
    // Redirigir a la actividad informacion

   /* public void I (View view){
        Intent I = new Intent(this, informacion.class);
        startActivity(I);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar:
                String ver;
                String gra;
                int n;

                if(bandera == 0) {
                    bandera = 1;
                    ver = textV.getText().toString();
                    textV.setText("");
                    if (ver.equals("")) {
                        Toast.makeText(this, "ERROR Campo vacios", Toast.LENGTH_SHORT).show();
                    } else {
                        s = Integer.parseInt(ver);
                        if (s <= 0) {
                            Toast.makeText(this, "ERROR El valor no puede ser 0", Toast.LENGTH_SHORT).show();
                        } else {
                            //G.setEnabled(true);
                            //V.setEnabled(false);
                            mensa.setText("Ingrese grado del vértice ("+c+")");
                            CrearS(s);
                            textV.setHint("NUM. GRADO");
                        }
                    }
                }else{
                    gra = textV.getText().toString();
                    textV.setText("");

                    if(gra.equals("")){
                        Toast.makeText(this,"ERROR Campo vacio", Toast.LENGTH_SHORT).show();
                    }else{
                        if(c<=s){
                            n = Integer.parseInt(gra);
                            Ingresar(n, sucesion, c);
                            Ingresar(n, sucesion2, c);
                            Toast.makeText(this,"Valor del vertice "+c+" ingresado", Toast.LENGTH_SHORT).show();
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

           case R.id.btnInformacion:
                Intent I = new Intent(this, informacion.class);
                I.putExtra("indice", 1);
                startActivity(I);
                break;

            case R.id.btnAnalizar:
                String suce = "Sucesión: ";
                Ordenar(s, sucesion);
                for(int i=0; i<s; i++){
                    suce += String.valueOf(sucesion[i]) + " ";
                }

                if(analisis(s, sucesion)){

                    Resul.setText(suce + "\nLa sucesión grados si es graficable");
                    res = (suce + "\nLa sucesión grados si es graficable");
                    Si=1;
                }else{
                    Si=0;
                    Resul.setText(suce + "\nLa sucesión de grados no es graficable");
                    res = (suce + "\nLa sucesión de grados no es graficable");
                }
                anali.setEnabled(false);
                anali.setTextColor(0xFF9c9c9c);
                Grafo1.setVisibility(View.VISIBLE);
                exportar1.setVisibility(View.VISIBLE);
                break;

            case R.id.btnLimpiar:
                mensa.setText("Ingrese el número de vértices");
                V.setEnabled(true);
                bandera = 0;
                Si=0;
                V.setTextColor(0xFFFFFFFF);
                textV.setHint("NUM. VÉRTICES");
                //anali.setTextColor(0xFF9c9c9c);
                c = 0;
                anali.setBackground(estilo_boton2);
                Resul.setText("");
                Grafo1.setVisibility(View.GONE);
                exportar1.setVisibility(View.GONE);
                res = " ";
                break;

            case R.id.btng1:
                if(Si==1){
                    Intent G = new Intent(this, grafos.class);
                    G.putExtra("Vertice", s);
                    G.putExtra("Sucesion",sucesion);
                    G.putExtra("Sucesion2",sucesion2);
                    G.putExtra("Pantalla", 1);
                    startActivity(G);
                }else{
                    Toast.makeText(this,"La sucesion no es graficable",Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.btnex1:

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
        sucesion2 = new int[s];
    }

    public static boolean Ingresar(int s, int suc[], int pos){
        suc[pos] = s;
        return true;
    }

    public static boolean Ordenar(int s, int[] suc){
        int i, j, min, aux;

       for(i=0; i<s; i++){
            System.out.print("\t"+suc[i]);
        }
        System.out.println();
        for(i=0; i<s; i++){
            min = i;
            for(j=i+1; j<s; j++){
                if(suc[j] > suc[min]){
                    min = j;
                }

            }

            aux = suc[i];
            suc[i] = suc[min];
            suc[min] = aux;
        }
        for(i=0; i<s; i++){
            System.out.print("\t"+suc[i]);
        }
        return true;
    }

    public static boolean analisis(int s, int suc[]){
        int n, aux, t;
        int caux[] = new int[s];
        Ordenar(s, suc);
        System.arraycopy(suc, 0, caux, 0, s);
        t = s;
        do{
            for(int i=0; i<t; i++){
                System.out.print(caux[i]+" ");
            }
            System.out.print("\n ");
            n = caux[0];
            if(n == 0){
                break;
            }
            if(n > t){
                return false;
            }
            for(int v =0; v<t-1; v++ ){
                if(v<n){
                    aux = caux[v+1];
                    caux[v] = aux-1;
                }else{
                    aux = caux[v+1];
                    caux[v] = aux;
                }
            }
            t = t-1;
            for(int p = 0; p<t; p++){
                if(caux[p]<0){
                    return false;
                }
            }
            Ordenar(t, caux);
        }while(n != 0);
        return true;
    }

    public void ArchivoR(String res1){
        try{
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("Resultados.txt", Activity.MODE_APPEND));
            archivo.write(res1);
            archivo.flush();
            archivo.close();
        }catch (IOException e){

        }
        Toast.makeText(this, "Resultados Guardados", Toast.LENGTH_SHORT).show();

        /*FileOutputStream fileOutputStream = null;
        try{
            fileOutputStream = openFileOutput("Resultados.txt", MODE_APPEND);
            fileOutputStream.write(res.getBytes());
            Log.d("TAG1","Fichero Salvadp en: " + getFilesDir() + "/" + "Rsultados.txt");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(fileOutputStream != null){
                try{
                    fileOutputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }*/

    }

    public void Ruta(){
        /*String archivo = "miarchivo";
        String carpeta = "/archivo/";
        File  file;
        String file_path ="";
        String name="";
        file_path = (Environment.getExternalStorageDirectory() + carpeta);
        File localFile = new File(file_path);
        Toast.makeText(this,""+file_path,Toast.LENGTH_SHORT).show();
        if(!localFile.exists()){
            localFile.mkdirs();
            Toast.makeText(this,"Creado",Toast.LENGTH_SHORT).show();
        }*/
        try {
            File nuevaCarpeta = new File(Environment.getExternalStorageDirectory(), "CarpetaDePrueba");
            if (!nuevaCarpeta.exists()) {
                nuevaCarpeta.mkdir();
            }
            try {
                File file = new File(nuevaCarpeta, "Archivo" + ".txt");
                file.createNewFile();
            } catch (Exception ex) {
                Log.e("Error", "ex: " + ex);
            }
        } catch (Exception e) {
            Log.e("Error", "e: " + e);
        }
    }

  /*  public File crearCarpetaAlmInterno(String nombreDirectorio){
        //Crear directorio en la memoria interna.
        File directorio = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), nombreDirectorio);
        //Muestro un mensaje en el logcat si no se creo la carpeta por algun motivo
        if(!directorio.mkdirs()){
            Log.e("TAG", "Error: No se creo el directorio privado");
        }
        return directorio;
    }*/

}