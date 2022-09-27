package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class floyd extends AppCompatActivity implements View.OnClickListener  {
    Button btnGuardar, btnAceptar, btnSiguiente, btnAnalizar, btnExportar, btnGraficar,btnLimpiar, btnInf;
    EditText Vertice, Peso, Origen, Destino;
    Drawable estilo_boton1,estilo_boton2, estilo_boton3;
    TextView Titulo,Titulo2, Mostrar;
    ConstraintLayout l1,l2,l3;

    int vertice, peso;
    int MatOri[][]; //Matriz original
    int m[][];    //Sera la copia de la matriz original, aquí se haran los calculos
    int Rec[][];    //Matriz de recorridos.
    int i=0,j=0,k=0;
    int INFINITO = 999999999;
    int ban=0;
    ArrayList<Integer> Mat1 = new ArrayList<Integer>();
    ArrayList<Integer> Camino = new ArrayList<Integer>();

    String dato,cadena="", res;

    //para la tabla dinamica
    TableLayout tableLayout,tableLayout2;
    private String[]header;
    private String[]header2;
    private ArrayList<String[]> rows = new ArrayList<>();
    private ArrayList<String[]> rows2 = new ArrayList<>();
    private TableDynamic tableDynamic,tableDynamic2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floyd);

        estilo_boton1 = getDrawable(R.drawable.estilo_boton2);
        estilo_boton2 = getDrawable(R.drawable.estilo_boton4);
        estilo_boton3 = getDrawable(R.drawable.estilo_boton6);

        btnGuardar = (Button) findViewById(R.id.btnGuardar2);
        btnAceptar = (Button) findViewById(R.id.btnAceptar2);
        btnSiguiente = (Button) findViewById(R.id.btnSiguiente2);
        btnAnalizar = (Button) findViewById(R.id.Analisis2);
        btnExportar = (Button) findViewById(R.id.btnExportar2);
        btnGraficar = (Button) findViewById(R.id.btnGrafica2);
        btnLimpiar = (Button) findViewById(R.id.btnO2);
        btnInf = (Button) findViewById(R.id.btnInf2);

        Vertice = (EditText) findViewById(R.id.editTextV2);
        Peso = (EditText) findViewById(R.id.editPeso2);
        Origen = (EditText) findViewById(R.id.editVO2);
        Destino = (EditText) findViewById(R.id.editVD2);

        Titulo = (TextView) findViewById(R.id.textMensaje2);
        Titulo2 = (TextView) findViewById(R.id.textView44);
        Mostrar = (TextView) findViewById(R.id.textView7);

        l1 = (ConstraintLayout) findViewById(R.id.contenedor1);
        l2 = (ConstraintLayout) findViewById(R.id.contenedor2);
        l3 = (ConstraintLayout) findViewById(R.id.contenedor3);

        tableLayout = (TableLayout) findViewById(R.id.table1);
        tableLayout2 = (TableLayout) findViewById(R.id.table2);

        btnGuardar.setOnClickListener(this);
        btnAceptar.setOnClickListener(this);
        btnSiguiente.setOnClickListener(this);
        btnAnalizar.setOnClickListener(this);
        btnExportar.setOnClickListener(this);
        btnGraficar.setOnClickListener(this);
        btnLimpiar.setOnClickListener(this);
        btnInf.setOnClickListener(this);

        btnGraficar.setVisibility(View.GONE);
        btnExportar.setVisibility(View.GONE);

        btnAnalizar.setBackground(estilo_boton2);

        btnExportar.setBackground(estilo_boton2);
        btnGraficar.setBackground(estilo_boton2);
    }

    public void save(int G[][]){
        for(int k=0; k<vertice; k++){
            String[]item=new String[vertice+1];
            item[0] = "V"+(k+1);
            for (int l=0; l<vertice; l++){
                if(G[k][l]==INFINITO){
                    item[l+1]="Inf";
                }else {
                    item[l+1]=""+G[k][l];
                }
            }
            tableDynamic.addItems(item);
        }
    }

    public void save2(int G[][]){
        for(int k=0; k<vertice; k++){
            String[]item=new String[vertice+1];
            item[0] = "V"+(k+1);
            for (int l=0; l<vertice; l++){
                if(G[k][l]==INFINITO){
                    item[l+1]="Inf";
                }else {
                    item[l+1]=""+G[k][l];
                }
            }
            tableDynamic2.addItems(item);
        }
    }

    public void crearMatrizDinamicaD(){
        tableDynamic = new TableDynamic(tableLayout,getApplicationContext());
        header = new String[vertice+1];
        header[0]="V";
        for (int k=1; k<=vertice; k++){
            header[k]="V"+k;
        }
        tableDynamic.addHeadler(header);
        tableDynamic.addData(getVertices());
        tableDynamic.backgroundHeader(getDrawable(R.drawable.estilo_boton5));
        tableDynamic.backgroundData(getDrawable(R.drawable.estilo_boton5), getDrawable(R.drawable.estilo_caja_de_texto2));
    }

    private ArrayList<String[]>getVertices(){
        return rows;
    }

    private ArrayList<String[]>getVertices2(){
        return rows2;
    }

    public void crearMatrizDinamicaR(){
        tableDynamic2 = new TableDynamic(tableLayout2,getApplicationContext());
        header2 = new String[vertice+1];
        header2[0]="V";
        for (int k=1; k<=vertice; k++){
            header2[k]="V"+k;
        }
        tableDynamic2.addHeadler(header2);
        tableDynamic2.addData(getVertices2());
        tableDynamic2.backgroundHeader(getDrawable(R.drawable.estilo_boton5));
        tableDynamic2.backgroundData(getDrawable(R.drawable.estilo_boton5), getDrawable(R.drawable.estilo_caja_de_texto2));
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnGuardar2:{
                dato = Vertice.getText().toString();
                if(dato.equals("")){
                    Toast.makeText(this,"ERROR Campo vacio", Toast.LENGTH_LONG).show();
                }else {
                    vertice = Integer.parseInt(dato);
                    if (vertice > 2) {
                        crearMatrices();
                        Vertice.setText("");
                        j=i+1;
                        Titulo.setText("Ingrese peso del vertice " + (i+1) + " al " + (j+1));
                        l1.setVisibility(v.GONE);
                        l2.setVisibility(v.VISIBLE);
                    } else {
                        Toast.makeText(this, "Ingrese un vértice mayor a 2", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            }

            case R.id.btnAceptar2:{
                dato = Peso.getText().toString();
                if(dato.equals("")){
                    Toast.makeText(this,"ERROR Campo vacio", Toast.LENGTH_LONG).show();
                }else{
                    peso = Integer.parseInt(dato);
                    if(peso<=0){
                        Toast.makeText(this, "Ingrese un peso mayor a 0", Toast.LENGTH_LONG).show();

                    }else{
                        MatOri[i][j] = peso;
                        j++;
                        if(i==vertice-1 && j==vertice-1){
                            Toast.makeText(this, "Matriz completa", Toast.LENGTH_LONG).show();
                            //cadena += "Matriz original\n";
                            //Imprimirmatriz(MatOri);
                            //Mostrar.setText(cadena);
                            //save(MatOri);
                            l2.setVisibility(v.GONE);
                            Titulo2.setVisibility(v.VISIBLE);
                            l3.setVisibility(v.VISIBLE);

                            btnAnalizar.setBackground(estilo_boton3);
                            //btnAnalizar.setBackgroundColor(Color.rgb(161,6,132));
                        }else{
                            if(j>=vertice){
                                i++;
                                j=0;
                            }
                            if(i==j){
                                j++;
                            }
                        }
                        Titulo.setText("Ingrese peso del vertice " + (i+1) + " al " + (j+1));
                        Peso.setText("");
                    }
                }
                break;
            }

            case R.id.btnSiguiente2:{
                MatOri[i][j] = INFINITO;
                j++;

                if(i==vertice-1 && j==vertice-1){
                    Toast.makeText(this, "Matriz completa", Toast.LENGTH_LONG).show();
                    //cadena += "Matriz original\n";
                    //Imprimirmatriz(MatOri);
                    //Mostrar.setText(cadena);
                    //save(MatOri);
                    l2.setVisibility(v.GONE);
                    Titulo2.setVisibility(v.VISIBLE);
                    l3.setVisibility(v.VISIBLE);
                    btnAnalizar.setBackground(estilo_boton3);
                    //btnAnalizar.setBackgroundColor(Color.rgb(161,6,132));
                }else{
                    if(j>=vertice){
                        i++;
                        j=0;
                    }
                    if(i==j){
                        j++;
                    }
                }
                Titulo.setText("Ingrese peso del vertice " + (i+1) + " al " + (j+1));
                Peso.setText("");
                break;
            }

            case R.id.Analisis2:{
                int Ori,Des;
                String dato2;
                cadena = "";
                dato = Origen.getText().toString();
                dato2 = Destino.getText().toString();
                if(!dato.equals("") && !dato2.equals("")){
                    Ori = Integer.parseInt(dato);
                    Des = Integer.parseInt(dato2);
                    if((Ori>0 && Ori<=vertice) && (Des>0 && Des<=vertice)){
                        if(ban==0){
                            ban=1;
                            CopiarMatriz();
                            Floyd();
                            save(m);
                            save2(Rec);

                            btnExportar.setBackground(estilo_boton1);
                            btnGraficar.setBackground(estilo_boton1);
                            btnGraficar.setVisibility(View.VISIBLE);
                            btnExportar.setVisibility(View.VISIBLE);
                        }
                        cadena += "\nRecorrido:\n";
                        buscar(Ori,Des);
                        Mostrar.setText(cadena);
                    }else{
                        Toast.makeText(this,"ERROR El valor de origen o destino no esta entre el rango de sus vértices"+ 1 + "-"+ (vertice), Toast.LENGTH_SHORT).show();
                    }

                }

                break;
            }

            case R.id.btnO2:{
                i=j=k=ban=0;
                Vertice.setText("");
                Peso.setText("");
                Mostrar.setText("");
                Origen.setText("");
                Destino.setText("");
                cadena="";
                res="";
                Camino.clear();
                Mat1.clear();
                l1.setVisibility(v.VISIBLE);
                Titulo2.setVisibility(v.GONE);
                l2.setVisibility(v.GONE);
                l3.setVisibility(v.GONE);
                Titulo.setText("Ingrese el número de vértices");
                btnAnalizar.setBackground(estilo_boton2);
                btnExportar.setBackground(estilo_boton2);
                btnGraficar.setBackground(estilo_boton2);
                btnGraficar.setVisibility(View.GONE);
                btnExportar.setVisibility(View.GONE);
                break;
            }

            case R.id.btnInf2:{
                Intent I = new Intent(this, informacion.class);
                I.putExtra("indice", 6);
                startActivity(I);
                break;
            }
            case R.id.btnGrafica2:
                Archivos conexion = new Archivos();
                Mat1.clear();
                conexion.copiarMat(Mat1,MatOri, vertice);

                Intent G = new Intent(this, grafos.class);
                G.putExtra("Vertice", vertice);
                G.putExtra("Pantalla", 6);
                G.putExtra("Matriz1", Mat1);
                G.putExtra("Camino", Camino);
                startActivity(G);
                break;

            case R.id.btnExportar2:

                Intent Nom = new Intent(this, Nombre_Fichero.class);
                Nom.putExtra("Resultados", res);
                startActivity(Nom);
                break;
        }
    }

    public void crearMatrices(){
        int l,z;
        MatOri = new int[vertice][vertice];
        m = new int[vertice][vertice];
        Rec = new int[vertice][vertice];
        crearMatrizDinamicaD();
        crearMatrizDinamicaR();

        //Inicializamos la matriz de recorridos
        for(l=0; l<vertice; l++){
            for(z=0; z<vertice; z++){
                if(l != z){
                    Rec[l][z]=z+1;
                }else{
                    Rec[l][z]=-1;
                }
                MatOri[l][z]=0;
            }
        }

    }

    public void CopiarMatriz(){
        int l,z;

        for(l=0; l<vertice; l++) {
            for (z = 0; z < vertice; z++) {
                m[l][z] = MatOri[l][z];
            }
        }
    }

    public void Floyd(){

        for(i=0; i<vertice; i++){
            for(j=0; j<vertice; j++){
                if(i != j){
                    if(m[j][i] != INFINITO){
                        for(k=0; k<vertice; k++){
                            if(j!=k && i!=k){
                                if(m[i][k] != INFINITO){
                                    if(m[j][i] + m[i][k] < m[j][k]){
                                        m[j][k] = m[j][i]+m[i][k];
                                        Rec[j][k] = i+1;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void Imprimirmatriz(int Matriz[][]){
        int l,z;

        for(l=0; l<vertice; l++) {
            for(z=0; z<vertice; z++) {
                if(Matriz[l][z]==INFINITO){
                    cadena+=" oo ";
                }else {
                    cadena += Matriz[l][z] + "\t\t";
                }
            }
            cadena += "\n";
        }
    }

    public void buscar(int O, int D){
        Camino.clear();
        String cad="";
        cad +="\n"+D+"-> ";
        Camino.add(D);
        while(Rec[O-1][D-1] != D){
            D = Rec[O-1][D-1];
            cad += D+"-> ";
            Camino.add(D);
        }
        cad += O+"";
        Camino.add(O);
        invertircadena(cad);
    }

    public void invertircadena(String cad){
        int l;
        cadena +="\n";
        for(l=cad.length()-1; l>=0; l--){
            if(cad.charAt(l) == '-'){
                cadena += '>';
            }
            else if(cad.charAt(l) == '>'){
                cadena += '-';
            }else
                cadena += cad.charAt(l);
        }
    }


}