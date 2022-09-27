package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class dijkstra extends AppCompatActivity implements View.OnClickListener {
    Button btnVet, btnPes, Sbtn, Nbtn, btnDi, btnR, O, In,exportar4, Grafo4;
    Drawable estilo_boton1,estilo_boton2;
    EditText eVet, ePes, Origen, Destino;
    TextView texVet, texPes, texDij, texDis,verti;
    ConstraintLayout li,l2;
    int n, i= 0, j=0, cont = 0;
    char aux;
    String aux2 = "";
    String res;
    int op, op2, peso=-1, origen=0;
    int org, rt;
    GrafMatPeso Grafica;
    CaminoMinimo Dijs;
    View t, b, g, pe, s;
    ArrayList<Integer> Mat1 = new ArrayList<Integer>();
    ArrayList<Integer> Camino = new ArrayList<Integer>();

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
        setContentView(R.layout.activity_dijkstra);

        estilo_boton1 = getDrawable(R.drawable.estilo_boton2);
        estilo_boton2 = getDrawable(R.drawable.estilo_boton4);

        b = findViewById(R.id.btnGuardar);
        t = findViewById(R.id.editTextV);
        g = findViewById(R.id.btnAceptar);
        pe = findViewById(R.id.editPeso);
        s = findViewById(R.id.btnSiguiente);

        btnVet = (Button) findViewById(R.id.btnGuardar);
        O=(Button) findViewById(R.id.btnO);
        In=(Button) findViewById(R.id.btnInf);
        btnPes = (Button) findViewById(R.id.btnAceptar);
        Nbtn = (Button) findViewById(R.id.btnSiguiente);
        //  Nbtn = (Button) findViewById(R.id.btnNO);
        btnDi = (Button) findViewById(R.id.Analisis);
        //btnR = (Button) findViewById(R.id.NRuta);
        Grafo4 = (Button) findViewById(R.id.btng4);
        exportar4 = (Button) findViewById(R.id.btnex4);

        eVet = (EditText) findViewById(R.id.editTextV);
        ePes = (EditText) findViewById(R.id.editPeso);
        Origen = (EditText) findViewById(R.id.editVO);
        Destino = (EditText) findViewById(R.id.editVD);

        li = (ConstraintLayout) findViewById(R.id.btnOyD);
        l2 = (ConstraintLayout) findViewById(R.id.vista2);
        texVet = (TextView) findViewById(R.id.textMensaje);
        // texPes = (TextView) findViewById(R.id.textVertP);
        //  texDij = (TextView) findViewById(R.id.textDi);
        texDis = (TextView) findViewById(R.id.textR);
        verti = (TextView) findViewById(R.id.textView4);


        tableLayout = (TableLayout) findViewById(R.id.table1);

        btnVet.setOnClickListener(this);
        O.setOnClickListener(this);
        In.setOnClickListener(this);
        btnPes.setOnClickListener(this);
        // btnPes.setOnClickListener(this);
        //Sbtn.setOnClickListener(this);
        Nbtn.setOnClickListener(this);
        btnDi.setOnClickListener(this);
        //   btnR.setOnClickListener(this);
        Grafo4.setOnClickListener(this);
        exportar4.setOnClickListener(this);

        btnVet.setEnabled(true);
        btnPes.setEnabled(false);
        // Sbtn.setEnabled(false);
        Nbtn.setEnabled(false);
        btnDi.setEnabled(false);
        //btnDi.setTextColor(0xFF9c9c9c);
        // btnR.setEnabled(false);
        g.setVisibility(View.GONE);
        pe.setVisibility(View.GONE);
        s.setVisibility(View.GONE);
        btnDi.setBackground(estilo_boton2);

        Grafo4.setVisibility(View.GONE);
        exportar4.setVisibility(View.GONE);

    }

    public void crearMatrizDinamica(){
        tableDynamic = new TableDynamic(tableLayout,getApplicationContext());
        header = new String[n+1];
        header[0]="V";
        for (int k=1; k<=n; k++){
            header[k]="V"+(k-1);
        }
        tableDynamic.addHeadler(header);
        tableDynamic.addData(getVertices());
        tableDynamic.backgroundHeader(getDrawable(R.drawable.estilo_boton5));
        tableDynamic.backgroundData(getDrawable(R.drawable.estilo_boton5), getDrawable(R.drawable.estilo_caja_de_texto2));
    }

    public void save(int G[][]){
        crearMatrizDinamica();
        for(int k=0; k<n; k++){
            String[]item=new String[n+1];
            item[0] = "V"+k;
            for (int l=0; l<n; l++){
                if(G[k][l]==Grafica.INFINITO){
                    item[l+1]="---";
                }else {
                    item[l+1]=""+G[k][l];
                }
            }
            tableDynamic.addItems(item);
        }
    }

    private ArrayList<String[]>getVertices(){
        return rows;
    }

    // Redirigir a la actividad informacion

    public void I (View view){
        Intent I = new Intent(this, informacion.class);
        startActivity(I);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGuardar:
                String ver;
                ver = eVet.getText().toString();
                eVet.setText("");
                if(ver.equals("")){
                    Toast.makeText(this,"ERROR Campo vacio", Toast.LENGTH_LONG).show();
                }else{
                    n = Integer.parseInt(ver);
                    if(n<=0){
                        Toast.makeText(this,"ERROR El valor no puede ser 0", Toast.LENGTH_LONG).show();
                    }else{
                        btnVet.setEnabled(false);
                        Nbtn.setEnabled(true);
                        btnPes.setEnabled(true);
                        // Sbtn.setEnabled(true);
                        b.setVisibility(View.GONE);
                        t.setVisibility(View.GONE);
                        g.setVisibility(View.VISIBLE);
                        pe.setVisibility(View.VISIBLE);
                        s.setVisibility(View.VISIBLE);
                        l2.setVisibility(View.VISIBLE);

                        Grafica = new GrafMatPeso(n);
                        for(int i = 0; i<n; i++){
                            aux = (char) i;
                            aux2 += aux;
                            Grafica.nuevoVertice(aux2);
                            aux2 = "";
                        }
                        texVet.setText("¿Hay arista x(" + (0) + "), x("+ (0) +")? Si lo hay ingrese su peso");
                    }
                }

                break;

            case R.id.btnSiguiente:
                int u = 0;
                j++;
                if(j==n){
                    i++;
                    j=0;
                    if(i==n){
                        u =1;
                        save(Grafica.matPeso);
                        btnDi.setEnabled(true);
                        btnDi.setBackground(estilo_boton1);
                        verti.setVisibility(view.VISIBLE);
                        li.setVisibility(view.VISIBLE);
                        btnDi.setTextColor(0xFFFFFFFF);
                        texVet.setText("Aristas completas");
                        g.setVisibility(View.GONE);
                        pe.setVisibility(View.GONE);
                        s.setVisibility(View.GONE);

                    }

                }
                if(u==0)
                    texVet.setText("¿Hay arista x" + (i) + ", x"+ (j) +"? Si lo hay ingrese su peso");

                break;

            case R.id.btnAceptar:

                int p = 0, l=0;
                String pes;
                pes = ePes.getText().toString();
                ePes.setText("");
                if(pes.equals("")){
                    Toast.makeText(this,"ERROR Campo vacio", Toast.LENGTH_LONG).show();
                }else{
                    p = Integer.parseInt(pes);
                    if(p<0){
                        Toast.makeText(this,"", Toast.LENGTH_LONG).show();
                    }else{

                        Grafica.nuevoArco(i, j, p);
                        j++;
                        if(j == n){
                            i++;
                            j=0;
                            if(i==n){
                                l=1;
                                texVet.setText("Aristas completas");
                                // texPes.setText("Pesos de las aristas existentes completos");
                                save(Grafica.matPeso);
                                btnDi.setEnabled(true);
                                btnDi.setBackground(estilo_boton1);
                                btnDi.setTextColor(0xFFFFFFFF);
                                verti.setVisibility(view.VISIBLE);
                                li.setVisibility(view.VISIBLE);
                                g.setVisibility(View.GONE);
                                pe.setVisibility(View.GONE);
                                s.setVisibility(View.GONE);
                            }
                        }
                        if(l==0){
                            texVet.setText("¿Hay arista x" + (i) + ", x"+ (j) +"? Si lo hay ingrese su peso");
                        }

                    }
                }
                break;

            case R.id.Analisis:
                String graf ="";
                String or, de;
                int ori, des;

                or = Origen.getText().toString();
                Origen.setText("");
                de = Destino.getText().toString();
                Destino.setText("");


                if(or.equals("") || de.equals("")){
                    Toast.makeText(this,"ERROR Algún campo esta vacio", Toast.LENGTH_LONG).show();
                }else{
                    ori = Integer.parseInt(or);
                    des = Integer.parseInt(de);

                    if(ori<0 || ori >= n){
                        Toast.makeText(this,"ERROR El valor de origen no esta entre el rango de sus vértices"+ 0 + "-"+ (n-1), Toast.LENGTH_LONG).show();
                    }else{
                        if(des<0 || des >= n){
                            Toast.makeText(this,"ERROR El valor de destino no esta entre el rango de sus vértices"+ 0 + "-"+ (n-1), Toast.LENGTH_LONG).show();
                        }else{
                            if(ori == des){
                                Toast.makeText(this,"ERROR El valor de origen y destino son iguales", Toast.LENGTH_LONG).show();
                            }else{
                                Dijs = new CaminoMinimo(Grafica, ori);
                                Dijs.caminoMinimos();
                                org = ori;

                                rt = des;
                                Dijs.recuperaCamino(des);
                                //btnDi.setEnabled(false);
                                graf+=("Matriz de Pesos\n");
                                graf+=(" \t");
                                for(int f=0;f<n;f++)
                                    graf+=("V"+f+"\t");
                                graf+=("\n");
                                for(int t=0;t<n; t++){
                                    graf+=("V"+t+" |\t");
                                    for(int s=0; s<n; s++){
                                        if(Grafica.matPeso[t][s] != Grafica.INFINITO){
                                            graf+=(Grafica.matPeso[t][s]+"\t");
                                        }else{
                                            graf+=("_\t");
                                        }

                                    }
                                    graf+=("|");
                                    graf+=("\n");
                                }
                                int x = n * n;
                                int vet[] = new int[x] ;
                                Dijs.recuperaCamino(des);


                                int q = Dijs.a;
                                if(Dijs.a > 2){
                                    q = Dijs.a/2;

                                }

                           /* for(int z=0; z < Dijs.ver.length; z++){
                                graf += Dijs.ver[z]+ " ";
                            }*/
                                // graf+=("\n");
                                /*for(int b = q-1; b >= 0; b--){
                                    if(b==q-1){
                                        graf += ("V" + Dijs.ver[b]);
                                        Camino.add(Dijs.ver[b]);
                                    }else{
                                        graf += (" --> V" + Dijs.ver[b]);
                                        Camino.add(Dijs.ver[b]);
                                    }
                                }*/
                                int num = Dijs.in;
                                num--;
                                int num2=num;
                                graf = "camino:\n"+Dijs.cam;
                                graf+=("\n Pesos: ");


                                for(int k=1; k<Dijs.in/2;k++){
                                    graf+=("  "+Grafica.matPeso[Dijs.ver[num]][Dijs.ver[num-1]]+"\t");
                                    num--;
                                }
                                graf += "\nOrigen: "+org+ " Destino: "+rt;

                                texDis.setText(graf);
                                res = graf;
                                //btnR.setEnabled(true);
                                graf="";
                                Grafo4.setVisibility(View.VISIBLE);
                                exportar4.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }
                break;

            case R.id.btnO:
                //btnDi.setEnabled(true);
                // Dijs.in=0;
                //Dijs.a=0;
                i = 0;
                j=0;
                graf = "";
                res = "";
                texDis.setText(graf);
                btnDi.setEnabled(false);
                btnDi.setBackground(estilo_boton2);
                //btnDi.setTextColor(0xFF9c9c9c);
                btnVet.setEnabled(true);
                texVet.setText("Ingrese el número de vértices");
                b.setVisibility(View.VISIBLE);
                t.setVisibility(View.VISIBLE);
                g.setVisibility(View.GONE);
                pe.setVisibility(View.GONE);
                s.setVisibility(View.GONE);
                verti.setVisibility(view.GONE);
                li.setVisibility(view.GONE);
                l2.setVisibility(View.GONE);
                Grafo4.setVisibility(View.GONE);
                exportar4.setVisibility(View.GONE);
                break;

            case R.id.btnInf:
                Intent I = new Intent(this, informacion.class);
                I.putExtra("indice", 4);
                startActivity(I);
                break;

            case R.id.btng4:
                Archivos conexion = new Archivos();
                conexion.copiarMat(Mat1, Grafica.matPeso, n);
                Camino = (ArrayList<Integer>) Dijs.Ca.clone();
                Intent G = new Intent(this, grafos.class);
                G.putExtra("Vertice", n);
                G.putExtra("Pantalla", 4);
                G.putExtra("Matriz1", Mat1);
                G.putExtra("Camino", Camino);
                startActivity(G);
                break;
            case R.id.btnex4:
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

}