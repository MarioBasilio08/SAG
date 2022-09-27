package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class prim extends AppCompatActivity implements View.OnClickListener {
    TableLayout tableLayout;
    Button Guardar, Insertar,Siguiente,Analizar, Limpiar, Informacion,exportar5, Grafo5;
    Drawable estilo_boton1,estilo_boton2;
    EditText NVertices, Peso;
    TextView Titulo,Mostrar ;
    ConstraintLayout l1,l2;
    String cadena="";
    String res;
    int n, i = 1, j = 2,banLim=0;
    int G[][];
    int t[][];
    int Matriz[][];
    public boolean [][] in;
    public boolean [][] temp;
    static int mincost = 0;
    public int k, l, num_ed=0;
    private String[]header;
    private ArrayList<String[]> rows = new ArrayList<>();
    private TableDynamic tableDynamic;
    ArrayList<Integer> Mat1 = new ArrayList<Integer>();
    ArrayList<Integer> Mat2 = new ArrayList<Integer>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prim);

        estilo_boton1 = getDrawable(R.drawable.estilo_boton2);
        estilo_boton2 = getDrawable(R.drawable.estilo_boton4);

        Guardar = (Button) findViewById(R.id.btnGuardar);
        Insertar = (Button) findViewById(R.id.btnInsertar);
        Siguiente = (Button) findViewById(R.id.btnSiguiente);
        Analizar = (Button) findViewById(R.id.btnAnalizar);
        Limpiar = (Button) findViewById(R.id.btnLimpiar);
        Informacion = (Button) findViewById(R.id.btnInformacion);
        Grafo5 = (Button) findViewById(R.id.btng5);
        exportar5 = (Button) findViewById(R.id.btnex5);

        NVertices = (EditText) findViewById(R.id.editTextV);
        Peso = (EditText) findViewById(R.id.editTextP);
        Titulo = (TextView) findViewById(R.id.textMensaje);

        l1 = (ConstraintLayout) findViewById(R.id.linearLayout2);
        l2 = (ConstraintLayout) findViewById(R.id.linearLayout7);

        tableLayout = (TableLayout) findViewById(R.id.table);

        Guardar.setOnClickListener(this);
        Insertar.setOnClickListener(this);
        Siguiente.setOnClickListener(this);
        Analizar.setOnClickListener(this);
        Limpiar.setOnClickListener(this);
        Informacion.setOnClickListener(this);
        Grafo5.setOnClickListener(this);
        exportar5.setOnClickListener(this);
        Analizar.setEnabled(false);
        //Analizar.setTextColor(0xFF9c9c9c);
        Analizar.setBackground(estilo_boton2);

        Grafo5.setVisibility(View.GONE);
        exportar5.setVisibility(View.GONE);

    }

    public void save(View view){
        for(int k=1; k<=n; k++){
            String[]item=new String[n+1];
            item[0] = "V"+k;
            for (int l=1; l<=n; l++){
                if(G[k][l] == 999999999){
                    item[l]="---";
                }else{
                    item[l]=""+G[k][l];
                }
            }
            tableDynamic.addItems(item);
        }
    }

    public void crearMatrizDinamica(){
        tableDynamic = new TableDynamic(tableLayout,getApplicationContext());
        header = new String[n+1];
        header[0]="V";
        for (int k=1; k<n+1; k++){
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

    // Redirigir a la actividad informacion

    public void I (View view){
        Intent I = new Intent(this, informacion.class);
        I.putExtra("indice", 5);
        startActivity(I);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnGuardar: {
                String dato="";
                dato = NVertices.getText().toString();
                NVertices.setText("");
                if (dato.equals("")) {
                    Toast.makeText(this, "ERROR Campo vacio", Toast.LENGTH_LONG).show();
                } else {
                    n = Integer.parseInt(dato);
                    if (n > 0) {
                        CrearMatriz();
                        crearMatrizDinamica();
                        banLim=1;
                        Titulo.setText("Ingrese peso del vertice " + i + " - " + j);
                        l1.setVisibility(v.GONE);
                        l2.setVisibility(v.VISIBLE);
                    } else
                        Toast.makeText(this, "ERROR El valor no puede ser 0", Toast.LENGTH_LONG).show();
                }
                break;
            }
            case R.id.btnInsertar: {
                String dato;
                int ban=1;
                dato = Peso.getText().toString();
                Peso.setText("");
                if (dato.equals("")) {
                    Toast.makeText(this, "ERROR Campo vacio", Toast.LENGTH_LONG).show();
                } else {
                    if (i != j) {
                        G[i][j] = Integer.parseInt(dato);
                        G[j][i] = G[i][j];
                        j++;
                    }
                    if (i == j)
                        j++;
                    if(j>=n+1) {
                        i++;
                        j=i+1;
                    }
                    if(i>=n){
                        ban=0;
                        Toast.makeText(this, "Proceso terminado, matriz llena", Toast.LENGTH_LONG).show();
                        //Peso.setEnabled(false);
                        save(v);
                        Insertar.setEnabled(false);
                        Siguiente.setEnabled(false);
                        Insertar.setTextColor(0xFF9c9c9c);
                        Siguiente.setTextColor(0xFF9c9c9c);
                        Analizar.setEnabled(true);
                        Analizar.setTextColor(0xFFFFFFFF);
                        Analizar.setBackground(estilo_boton1);
                    }
                    if(ban==1)
                        Titulo.setText("Ingrese peso del vértice " + i + " - " + j);
                }
                break;
            }
            case R.id.btnSiguiente:{
                int ban=1;
                j++;
                if(j>=n+1) {
                    i++;
                    j=i+1;
                }
                if(i>=n){
                    ban=0;
                    Toast.makeText(this, "Proceso terminado, matriz llena", Toast.LENGTH_LONG).show();
                    save(v);
                    Peso.setEnabled(false);
                    Insertar.setEnabled(false);
                    Siguiente.setEnabled(false);
                    Insertar.setTextColor(0xFF9c9c9c);
                    Siguiente.setTextColor(0xFF9c9c9c);
                    Analizar.setEnabled(true);
                    Analizar.setTextColor(0xFFFFFFFF);
                    Analizar.setBackground(estilo_boton1);
                }
                if(ban==1)
                    Titulo.setText("Ingrese peso del vértice " + i + " - " + j);
                break;
            }
            case R.id.btnAnalizar:{
                cadena = "Matriz original de pesos\n";
                ImprimirmatrizG(G);
                cadena +="\n";
                Kruskals();
                cadena += "Matriz de peso minimo\n";
                Imprimirmatriz(Matriz);
                res = cadena;
                Analizar.setEnabled(false);
                Grafo5.setVisibility(View.VISIBLE);
                exportar5.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.btnLimpiar:{
                Mostrar.setText("");
                Peso.setText("");
                NVertices.setText("");
                res="";
                if(banLim==1) {
                    RellenarMatriz();
                    n = 0;
                    i = 0;
                    j = 0;
                    mincost = 0;
                    num_ed = 0;
                    i = 1;
                    j = 2;
                    l1.setVisibility(v.VISIBLE);
                    l2.setVisibility(v.GONE);
                    Peso.setEnabled(true);
                    Insertar.setEnabled(true);
                    Siguiente.setEnabled(true);
                    Insertar.setTextColor(0xFFFFFFFF);
                    Siguiente.setTextColor(0xFFFFFFFF);
                    Analizar.setEnabled(false);
                    //Analizar.setTextColor(0xFF9c9c9c);
                    cadena = "";
                    Titulo.setText("Ingrese el número de vértices");
                    Analizar.setBackground(estilo_boton2);
                    Grafo5.setVisibility(View.VISIBLE);
                    exportar5.setVisibility(View.VISIBLE);
                }
                break;
            }

            case R.id.btnInformacion: {
                I(null);
                break;
            }
            case R.id.btng5:
                Archivos conexion = new Archivos();
                conexion.copiarMat(Mat1, G, n+1);
                conexion.copiarMat(Mat2, Matriz, n);

                Intent G = new Intent(this, grafos.class);
                G.putExtra("Vertice", n);
                G.putExtra("Pantalla", 5);
                G.putExtra("Matriz1", Mat1);
                G.putExtra("Matriz2", Mat2);
                startActivity(G);
                break;
            case R.id.btnex5:
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

    public void CrearMatriz(){
        Matriz = new int[n][n];
        this.G = new int[n+1][n+1];
        this.in = new boolean[n+1][n+1];
        this.t = new int[n+1][3];
        RellenarMatriz();
    }

    public void RellenarMatriz(){
        int a,b;

        for(a=0; a<=n; a++)
            for (b = 0; b <= n; b++) {
                G[a][b] = 999999999;
                in[a][b] = in[a][b] = false;
            }

        for(a=0; a<n; a++){
            for(b=0; b<n; b++){
                Matriz[a][b] = 0;
            }
        }


    }

    public void ImprimirmatrizG(int Mat[][]){
        int k,l;

        for(k=1; k<=n; k++) {
            for(l=1; l<=n; l++) {
                if(Mat[k][l] == 999999999){
                    cadena += "-"+"\t";
                }else{
                    cadena += Mat[k][l]+"\t";
                }

            }
            cadena += "\n";
        }
    }

    public void Imprimirmatriz(int Mat[][]){
        int k,l;

        for(k=0; k<n; k++) {
            for(l=0; l<n; l++) {
                if(Mat[k][l] == 0){
                    cadena += "-"+"\t";
                }else{
                    cadena += Mat[k][l]+"\t";
                }
            }
            cadena += "\n";
        }
    }

    public void Kruskals(){
        for (int i = 1; i<=n; i++)
        {
            getMinKL();
            if(k==l)
                break;
            System.out.print(k + "-" +l);
            Matriz[k-1][l-1] = G[k][l];
            Matriz[l-1][k-1] = G[l][k];
            //cadena += k+" - " +l+"\n";
            System.out.println();

            mincost = mincost + G[k][l];
            num_ed = (isPresent(i, k))?num_ed:num_ed+1;
            num_ed = (isPresent(i, l))?num_ed:num_ed+1;

            t[i][1] = l;
            t[i][2] = k;
            if(num_ed >= n){
                if(allconnect(i))
                    return;
            }

        }
        System.out.println("\nNo hay solucion");
    }

    public boolean allconnect(int i) {
        for(int c=2;c<=n;c++)
        {
            temp = new boolean[n+1][n+1];
            for(int a=1;a<=n;a++)
                for(int b=1;b<=n;b++)
                    temp[a][b] = temp[b][a] = false;

            if(can_reach(1, c, i) == false)
                return false;
        }
        return true;
    }

    public boolean formscycle(int i) {
        if(isPresent(i, k) && isPresent(i, l)){
            temp = new boolean[n+1][n+1];
            for(int a=1;a<=n;a++)
                for(int b=1;b<=n;b++)
                    temp[a][b] = temp[b][a] = false;

            if(can_reach(k, l, i) )
                return true;
        }
        return false;
    }

    public boolean can_reach(int k, int l, int i) {
        temp[k][l] = temp[l][k] = true;
        for(int o=1; o<=i; o++)
        {
            if(((k == t[o][1]) && (l == t[o][2])) || ((l == t[o][1]) && (k == t[o][2])))
                return true;
            if((k == t[o][1]) && !(temp[t[o][2]][l]) )
            {
                if(can_reach(t[o][2], l, i) == true)
                    return true;
            }
            else if((k == t[o][2]) && !(temp[t[o][1]][l]))
            {
                if(can_reach(t[o][1], l, i) == true)
                    return true;
            }
        }
        return false;
    }

    public boolean isPresent(int i, int val) {
        for(int o=1; o<=i; o++)
            if((val == t[o][1]) || ((val == t[o][2]) ))
                return true;
        return false;
    }

    public void getMinKL() {
        int k1 = 1, l1 = 1;
        for(int i=1;i<=n;i++)
            for(int j=1;j<=n;j++)
            {
                if((i!=j)&&(i<j))
                {
                    if((G[i][j] < G[k1][l1]) && G[i][j] !=0 && in[j][i]==false)
                    {
                        k1 = i;
                        l1 = j;
                    }
                }
            }
        if(G[k1][l1] !=0 )
        {
            k =k1; l=l1;
            in[k][l] = in[l][k] = true;
        }
    }
}