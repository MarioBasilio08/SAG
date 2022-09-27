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

import java.util.ArrayList;

public class euleriana_conexa extends AppCompatActivity implements View.OnClickListener {
    Button Acep, Ingre, F, o, An, exportar2, Grafo2; //variables del tipo button (botones)
    Drawable estilo_boton1, estilo_boton2;
    TextView Ver;
    EditText editv, editver; //definimos una variable del tipo editText para ingresar datos
    TextView Res; //definimos una variable del tipo Textview para mostrar mensajes (label)
    String aux; //cadena para guarar la frase que se enviara al textview de mensajes
    int n, i=0, j=0; //variablesque ayudaran a los ciclos
    Button Si, NO;  //variabels de los botones si y no
    int Matriz[][]; //matriz de adyacencia del grafo
    boolean ady[];  //matriz tipo boleana para el algoritmo de conexion del grafo
    View t, b, S, N; //variables tipo view para aparecer y desaparecer los botones y cajas de texto
    String res, resG;
    ArrayList<Integer> Mat = new ArrayList<Integer>();
    ArrayList<String> ResG = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_euleriana_conexa);

        estilo_boton1 = getDrawable(R.drawable.estilo_boton2);
        estilo_boton2 = getDrawable(R.drawable.estilo_boton4);

        Acep = (Button) findViewById(R.id.btnGuardar); //enlazamos la variable boton con su identificador correspondiente, boton de guardar

        Ver = (TextView) findViewById(R.id.textMensaje); //enlazamos el texview para los mensajes con su identificador
        editv = (EditText) findViewById(R.id.editTextV); //enlazamos el edittex para ingresar los datos con su identificador

        F = (Button) findViewById(R.id.btnInformacion); //boton de informacion
        o = (Button) findViewById(R.id.btnLimpiar); //boton de limpiar
        An = (Button) findViewById(R.id.Analisis);//boton de analizar
        Res = (TextView) findViewById(R.id.textR); // textview para los resultados del analisis
        NO =(Button) findViewById(R.id.btnNo); //boton de no
        Si = (Button) findViewById(R.id.btnSi); //boton de si
        Grafo2 = (Button) findViewById(R.id.btng2);
        exportar2 = (Button) findViewById(R.id.btnex2);

        NO.setOnClickListener(this);  //colocamos su escuchador al boton no
        NO.setEnabled(false); //con esete metodo podemos habilitar o deshabilitar el boton correspondiente a la variable cambiano la propiedad true o false
        Si.setOnClickListener(this); //escuchador al boton false
        Si.setEnabled(false); //deshabilitar el boton si

        F.setOnClickListener(this); //escuchador del boton informacion
        An.setOnClickListener(this); //escuchador del boton analizar
        o.setOnClickListener(this); //escuchador del boton limpiar
        Grafo2.setOnClickListener(this);
        exportar2.setOnClickListener(this);

        //o.setEnabled(false);
        An.setEnabled(false); //deshabilitar el boton analisis
        Acep.setOnClickListener(this); //escuchador del boton guardar
        //An.setTextColor(0xFF9c9c9c);

        b = findViewById(R.id.btnGuardar); //enlazamos las viarables view con su componente correspondiente para poder esconderlo o aparecerlo, guardar
        t = findViewById(R.id.editTextV); //view caja de texto
        S = findViewById(R.id.btnSi); //view boton si
        N = findViewById(R.id.btnNo); //view boton no

        S.setVisibility(View.GONE); //metodo para esconder el boton si, se usa la variable view aignada, (gone esconder visible aparecer)
        N.setVisibility(View.GONE); //esconder boton no
        An.setBackground(estilo_boton2);
        Grafo2.setVisibility(View.GONE);
        exportar2.setVisibility(View.GONE);
    }

    // Redirigir a la actividad informacion

    public void I (View view){
        Intent I = new Intent(this, informacion.class);
        startActivity(I);
    }


    @Override
    public void onClick(View view) { //metodo para escuchar los botones y realizar sus acciones
        switch (view.getId()){  //obtiene el id del boton pulsado
            case R.id.btnGuardar:  //caso para el boton guardar

                String ver; //variable string para el datp
                ver = editv.getText().toString(); //obtenemos el dato de la caja de texto enlazada a la viarable editv
                editv.setText(""); //eliminamos lo escrito por el usuario en la caja de texto mandandole una cadena sin nada
                if(ver.equals("")){ //vrificamos si lo obtenido por la caja de texto es algo vacio
                    Toast.makeText(this,"ERROR Campo vacio", Toast.LENGTH_LONG).show(); //so lo es mandamos un mensaje con toast de error
                }else{ //si no lo es continuamos con nuestro algoritmo
                    n = Integer.parseInt(ver); //convertimos la cadena string que ontuvimos a un numer entero
                    if(n<=0){ //verificamos si el numero es menor o igual a cero
                        Toast.makeText(this,"ERROR El valor no puede ser 0", Toast.LENGTH_LONG).show(); //si lo es igual mandams un mensaje de error
                    }else{ //si no lo es continuamos
                        Acep.setEnabled(false); //si el numero es mayor a cero inhabilitamos el boton guardar
                        // btnSi.setEnabled(true);
                        //  btnNO.setEnabled(true);
                        Euleriana(n); //pasamos al metodo la variable donde esta el numero de verices de nuestro grafo, para crear su matriz de adyacencia
                        Ver.setText("¿Hay arista x(" + (1) + "), x("+ (1) +")?"); //le mandamos el mensaje al textview para que el usuario sepa la siguiente accion
                        b.setVisibility(View.GONE); //escondemos el boton guaradar
                        t.setVisibility(View.GONE); //escondemos la caja de texto
                        S.setVisibility(View.VISIBLE); //hacemos visible el boton si
                        N.setVisibility(View.VISIBLE); //hacemos visible el boton no
                        Si.setEnabled(true); //habilitamos el boton si
                        NO.setEnabled(true); //habilitamos el boton no
                    }
                }
                break;

            case R.id.btnNo: //al presionar el boton no indicamos que no hay arista ente estos vertices que nos hace mension el mensaje en el textview
                int d=0; //usamos esta variable como bandera
                if(j>i){ //se crea un ciclo imaginario ya que compartira datos con el boton si y deben estar "enlazados" para recorrer na misma matriz
                    Matriz[i][j] = 0; //cuando se presiona no se agrega un 0 a la posisicon que se indico en el mensaje con ayda de los indices i y j
                    Matriz[j][i] = 0; //como en un grafo su matriz es
                }else{
                    if(i == j){
                        Matriz[i][j] = 0; //cuando i e j son iguales solo se pone en una posicion el dato que es la diagonal principal
                    }
                }

                j++; //se aumenta el indice que recorre las columas
                if(j == n){ //si j es igual a n es que este vertice ya fueron ingresados todos sus aristas
                    i++; //se aumenta al indice de las filas para pasar al siguiente vertice
                    j = i; //el indice de las columnas se inicia en el valor de la fila nueva para comenzar desde su posicion en la diagoanal principal
                    if(i==n){ //si i es igual a n quiere decir que toda la matriz esta completa
                        //traz.setEnabled(true);
                        An.setBackground(estilo_boton1);
                        An.setEnabled(true); //se habilita el boton analisis
                        An.setTextColor(0xFFFFFFFF);
                        NO.setEnabled(false); //se deshabilita el boton no
                        Si.setEnabled(false); //se deshabilita el boton nsi
                        S.setVisibility(View.GONE); //desaparecemos el boton si
                        N.setVisibility(View.GONE); //desaparecemos el boton no
                        Ver.setText("Matriz de adyacencia del grafo no dirigido"); //madamos un mensaje para hacer mencion que la matriz esa llena

                        trazar(); //invocamos el metodo trzar para diibujar la matriz de adyacencia en el textview de resultados
                        d = 1; //activamos nuestra bandera que el proceso esta terminado
                    }
                }

                if(d==0){//si la bandera sigue siendo 0
                    Ver.setText("¿Hay arista x" + (i+1) + ", x"+ (j+1)+"?"); // vamos actualizando nuestro mensaje actualizando los indices para que el ususario
                    // seopa en que vertices estaria la arista
                }
                break;

            case R.id.btnSi: //el boton si realiza las misma accione que el boton no  solo que aqui la matriz de adyacencia recibe 1
                int f=0;
                if(j>i){ //comparten los indices i y j que son variables declaradas en el inicio de esta clase y asi crear un ciclo compartido
                    Matriz[i][j] = 1;
                    Matriz[j][i] = 1;
                }else{
                    if(i == j){

                        Matriz[i][j] = 1;
                    }
                }

                j++;
                if(j == n){
                    i++;
                    j = i;
                    if(i==n){

                        //traz.setEnabled(true);
                        An.setBackground(estilo_boton1);
                        An.setEnabled(true);
                        An.setTextColor(0xFFFFFFFF);
                        NO.setEnabled(false);
                        Si.setEnabled(false);
                        S.setVisibility(View.GONE);
                        N.setVisibility(View.GONE);
                        Ver.setText("Matriz de adyacencia del grafo no dirigido");
                        trazar();
                        f = 1;
                    }
                }

                if(f==0){
                    Ver.setText("¿Hay arista x" + (i+1) + ", x"+ (j+1)+"?");
                }
                break;


            case R.id.Analisis: //al presionar el boton analisis comienza el algoritmo
                ResG.clear();
                int l = 0;
                int re[] = new int[3]; //matriz para saber que posibilidades de resultado tenemos
                re[0]=0; //iniciamos todo en 0
                re[1]=0;
                re[2]=0;
                //  conexa(Matriz, ady, n, l);
                if(conexo(Matriz,n)){ //llamamos el metodo del algoritmo conexa
                    aux += "\nResultados:"; //dependiendo del resultado del if se guardara el texto en el string aux para despues mandarlo al mensaje de resultado
                    aux += "\nLa gráfica si es conexa";
                    resG = "\nResultados:";
                    ResG.add(resG);
                    resG = "\nLa gráfica si es conexa";
                    ResG.add(resG);
                }else{
                    aux += "\nResultados:";
                    aux += "\nLa gráfica no es conexa";
                    resG = "\nResultados:";
                    ResG.add(resG);
                    resG = "\nLa gráfica no es conexa";
                    ResG.add(resG);

                }
                euleria(Matriz, n, re); //llamamos al metodo de euleriana

                //en los siguientes if evaluamos cual posicion de nuestro arreglo re recibio un 1 para concatenar el mensaje correspondiente del resultado
                if(re[0]==1){
                    aux += ("\nLa gráfica es euleriana abierta");
                    resG = ("\nLa gráfica es euleriana abierta");
                    ResG.add(resG);
                }

                if(re[1]==1){
                    aux += ("\nLa gráfica es euleriana cerrada");
                    resG = ("\nLa gráfica es euleriana cerrada");
                    ResG.add(resG);
                }

                if(re[2]==1){
                    aux += ("\nLa gráfica no es euleriana");
                    resG = ("\nLa gráfica no es euleriana");
                    ResG.add(resG);
                }
                //teniendo los resultados en aux
                Res.setText(aux); //mandamos el mensaje de aux al cuador donde se veran los resultados
                Ver.setText("Análisis Completo :)"); //mostramos un mensaje general de analisis completo en la parte superior
                o.setEnabled(true);
                An.setEnabled(false); //deshabilitamos  el boton analisis
                Grafo2.setVisibility(View.VISIBLE);
                exportar2.setVisibility(View.VISIBLE);
                res = aux;
                break;

            case R.id.btnLimpiar:  //cuando presionamos el boton de limpiar
                //tenemos que restablecer todas nuestras variables como al inicio de la clase
                aux =""; //borramos la cadena de resultados
                n =0; //restablecemos el tamaño de la matriz
                i=0; //restablecemos los indice i y j
                j=0;
                res="";
                resG="";
                //Ver.setText("Vertices");
                Res.setText(aux); //lipiamos el mesaje en el textvie de resultados
                An.setEnabled(false);
                //An.setTextColor(0xFF9c9c9c);
                Acep.setEnabled(true); //el boton guardar lo habilitamos de nuevo
                Ver.setText("Ingrese el número de vértices"); //mandamos nuestro mensaje de inicio al texview de los mensajes
                b.setVisibility(View.VISIBLE); //hacemos visible el boton guardar
                t.setVisibility(View.VISIBLE); //hacemos visible la caja de texto para ingresar datos
                S.setVisibility(View.GONE); //desaparecemos el boton si
                N.setVisibility(View.GONE); //desaparecemnos el boton no
                An.setBackground(estilo_boton2);
                Grafo2.setVisibility(View.GONE);
                exportar2.setVisibility(View.GONE);
                break;

            case R.id.btnInformacion: //al presionar el boton info
                Intent I = new Intent(this, informacion.class); //aqui se declara una variable de tipo intent con un metodo para establecer
                // a que actividad sera dirigida en eeste caso a informacion.class
                I.putExtra("indice", 2);
                startActivity(I); //iniciamos la actividad pasandoles la variable de tipo intent
                break;
            case R.id.btng2:
                Archivos conexion = new Archivos();
                conexion.copiarMat(Mat, Matriz, n);
                Intent G = new Intent(this, grafos.class);
                G.putExtra("Vertice", n);
                G.putExtra("Pantalla", 2);
                G.putExtra("Matriz", Mat);
                G.putExtra("Res", ResG);
                startActivity(G);
                break;

            case R.id.btnex2:
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

    public void Euleriana(int n){
        Matriz = new int[n][n];
        ady = new boolean[n];
    }

    public void trazar(){
        int x, y;
        aux ="\nMatriz\n";
        Res.setText(aux);
        for(x=0; x<n; x++){
            aux += "X" + (x+1) + " | \t";
            for(y=0; y<n; y++){
                aux += String.valueOf(Matriz[x][y] + " \t");
            }
            aux += "|\n";
        }
        Res.setText(aux);
        //traz.setEnabled(false);
    }

    public static boolean conexo(int[][] rsult, int n) {
        // Object[][] rsult = generaMatrizAdyacencia();
        int x = 0;
        if (n > 0) {
            for (int i = 0; i < n ; i++) {
                for (int j = 0; j < n ; j++) {
                    if (i != j) {
                        if ((Integer) rsult[i][j] == 0 && (Integer)
                                rsult[j][i] == 0) {
                            x += 1;
                        }
                    }
                }
                if (x == rsult.length - 1) {
                    return false;
                }
                x = 0;
            }
            return true;
        }
        return false;
    }

    public void elevacion(int mat[][], int mat2[][], int x){
        int i, j, y;
        int suma = 0;
        for(i=0; i<x; i++){
            for(j=0; j<x; j++){
                for(y=0; y<x; y++){
                    suma += (mat[i][y] * mat[y][j]);
                }
                mat2[i][j] = suma;
                suma = 0;
            }
        }
    }

    public void elevacion2(int mat[][], int mat2[][], int x, int t){
        int i, j, y, m, c;
        int suma = 0;
        int matC[][] = new int[x][x];

        for(i=0; i<x; i++){
            for(j=0; j<x; j++){
                matC[i][j] = mat[i][j];
            }
        }

        for(m=1; m<t; m++){
            for(i=0; i<x; i++){
                for(j=0; j<x; j++){
                    for(y=0; y<x; y++){
                        //suma = suma + (mat[i][y] * mat[y][j]);
                        suma += (mat[i][y] * matC[y][j]);
                    }
                    mat2[i][j] = suma;
                    suma = 0;
                }
            }
            for(i=0; i<x; i++){
                for(j=0; j<x; j++){
                    matC[i][j] = mat2[i][j];
                }
            }
        }
    }

    public void conexa(int mat[][], boolean ady[], int x, int v){
        int i, j, c, t=0, y;

        ady[v]= true;
        for(i=v; i<x; i++){
            for(j=v+1; j<x; j++){
                //y=mat[i][j];
                if(mat[i][j]>0){
                    if(ady[j] != true){
                        conexa(mat, ady, x, j);
                    }else{
                        ady[j] = false;
                    }
                }
            }
        }
    }



    public void euleria(int mat[][], int x, int r[]){
        int mat2[][] = new int[x][x];
        int i, p, impar=0;
        int c=0;
        boolean ad[] = new boolean[x];


        for(i=0; i<x; i++){
            for(p=0; p<x; p++){
                if(i != p){
                    if(mat[i][p]==1){
                        c++;
                    }
                }
            }
            if(c%2 != 0){
                impar++;
            }
            c=0;
        }

        if((impar == 2) && (conexo(mat, x))){
            r[0]=1;

        }else{
            if((impar == 0) && conexo(mat, x)){
                r[1]=1;
            }else{
                r[2]=1;
            }
        }
    }

}