package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class grafos extends AppCompatActivity {
    int width;
    int height;
    TextView pruueba;
    int ver, pantalla;
    int [] sucesion;
    int [] sucesion2;
    float [][] CoorCirculo;
    ArrayList<Integer> Lista = new ArrayList<Integer>();
    ArrayList<Integer> Lista2 = new ArrayList<Integer>();
    ArrayList<Integer> Copia = new ArrayList<Integer>();
    int [][] Matriz;
    int [][] Matriz2;
    String res;
    ArrayList<String> ResG = new ArrayList<String>();
    //Bundle extras = getIntent().getExtras();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafos);
        GraficoView grafico = new GraficoView(this);
        setContentView(grafico);

        ver = getIntent().getIntExtra("Vertice",0);
        pantalla = getIntent().getIntExtra("Pantalla", 0);
        if(pantalla == 1){
            sucesion = new int[ver];
            sucesion2 = new int[ver];
            sucesion = getIntent().getIntArrayExtra("Sucesion");
            sucesion2 = getIntent().getIntArrayExtra("Sucesion2");
        }
        if(pantalla == 2){
            ResG = getIntent().getStringArrayListExtra("Res");
            Lista = getIntent().getIntegerArrayListExtra("Matriz");
            Matriz = new int[ver][ver];
            regresarMat(Lista, Matriz, ver);
        }

        if((pantalla == 3) || (pantalla == 5)){
            Lista = getIntent().getIntegerArrayListExtra("Matriz1");
            Matriz = new int[ver+1][ver+1];
            regresarMat(Lista, Matriz, ver+1);
            Lista2 = getIntent().getIntegerArrayListExtra("Matriz2");
            Matriz2 = new int[ver][ver];
            regresarMat(Lista2, Matriz2, ver);
        }

        if(pantalla == 4){
            Lista = getIntent().getIntegerArrayListExtra("Matriz1");
            Matriz = new int[ver][ver];
            regresarMat(Lista, Matriz, ver);
            Lista2 = getIntent().getIntegerArrayListExtra("Camino");
        }

        if(pantalla == 6){
            Lista = getIntent().getIntegerArrayListExtra("Matriz1");
            Matriz = new int[ver][ver];
            regresarMat(Lista, Matriz, ver);
            Copia = getIntent().getIntegerArrayListExtra("Camino");
            int s = Copia.size();
            for(int h= (s-1); h>=0; h--){
                Lista2.add(Copia.get(h));
            }
        }

        if(pantalla==7){
            sucesion = new int[ver];
            sucesion = getIntent().getIntArrayExtra("Sucesion");
            res = getIntent().getStringExtra("SinOrden");
        }
        CoorCirculo = new float[ver][2];
        /*pruueba = (TextView)findViewById(R.id.textView);
        pruueba.setText("Ancho: "+ width +"\n"+"Largo: "+height);*/
    }

    private class GraficoView extends View {
        public GraficoView(Context context) {
            super(context);
        }

        @Override
        protected void onDraw(Canvas canvas){
            super.onDraw(canvas);
            Paint pincel = new Paint();
            pincel.setColor(Color.rgb(248,238,231));
            canvas.drawPaint(pincel);
            width = canvas.getWidth();
            height= canvas.getHeight();
            pincel.setColor(Color.WHITE);
            pincel.setTextSize(80);
            pincel.setAntiAlias(true);

            CirculoCo(ver, CoorCirculo);

            switch (pantalla){
                case 1:
                    GrafoDefinicion(pincel, canvas, ver,  CoorCirculo, sucesion2);
                    GrafoCompleto(pincel, canvas, ver);
                    break;
                case 2:
                    Euleriana(pincel,  canvas,  ver, Matriz, CoorCirculo, ResG);
                    GrafoCompleto(pincel, canvas, ver);
                    break;
                case 3:
                    PesosK(pincel, canvas, ver, Matriz, CoorCirculo);
                    PesosKM(pincel, canvas, ver, Matriz2, CoorCirculo);
                    NumPesos(pincel, canvas, ver, Matriz, CoorCirculo);
                    GrafoCompleto(pincel, canvas, ver);
                    break;
                case 4:
                    PesosDij(pincel, canvas, ver, Matriz, CoorCirculo);
                    CaminoDij(pincel, canvas, ver, Matriz, CoorCirculo, Lista2);
                    NumDij(pincel, canvas, ver, Matriz, CoorCirculo);
                    GrafoCompletoDij(pincel, canvas, ver);
                    break;
                case 5:
                    PesosK(pincel, canvas, ver, Matriz, CoorCirculo);
                    PesosKM(pincel, canvas, ver, Matriz2, CoorCirculo);
                    NumPesos(pincel, canvas, ver, Matriz, CoorCirculo);
                    GrafoCompleto(pincel, canvas, ver);
                    break;
                case 6:
                    PesosFloyd(pincel, canvas, ver, Matriz, CoorCirculo);
                    CaminoFloyd(pincel, canvas, ver, Matriz, CoorCirculo, Lista2);
                    NumFloyd(pincel, canvas, ver, Matriz, CoorCirculo);
                    GrafoCompleto(pincel, canvas, ver);
                    break;
                case 7:
                    GrafoDivVen(pincel, canvas, ver, sucesion,res);
                    break;
            }
        }
    }

    public void GrafoCompleto(Paint pincel, Canvas canvas, int ver){
        for(int i =1; i<=ver; i++){
            pincel.setColor(Color.rgb(161,6,132));
            canvas.drawCircle(CoorCirculo[i-1][0], CoorCirculo[i-1][1], (float) (((width/2.0)*0.3)/2.0), pincel);
            pincel.setColor(Color.WHITE);
            pincel.setTextSize(30);
            pincel.setAntiAlias(true);
            canvas.drawText("X"+i, CoorCirculo[i-1][0]-20, (CoorCirculo[i-1][1])+10, pincel);
        }
    }

    public void CrearGrafo(int k, int mat3[][], int mat2[]){
        int i,j;
        for(i=0; i<k; i++){
            for(j=0; j<k; j++){
                mat3[i][j]=0;
            }
        }

        for(i=0; i<k; i++){
            for(j=0; j<k; j++){
                if((i!=j) && (j>i)){
                    if(mat2[i]!=0){
                        mat3[i][j]=1;
                        mat3[j][i]=1;
                        mat2[i]=mat2[i]-1;
                        mat2[j]=mat2[j]-1;
                    }
                }
            }
        }

    }

    public void CirculoCo(int ver, float CoorCirculo[][]){
        double coseno, seno;
        float x, y;
        float cx = (float) (width/2.0);
        float espacio = (float) (cx*0.2);
        double angulo=0;
        float auxAn= 360/ver;
        for(int i =1; i<=ver; i++){
            angulo = auxAn * i;
            coseno = Math.cos(Math.toRadians(angulo));
            seno = Math.sin(Math.toRadians(angulo));
            x= (float) (width/2+(cx-espacio)*coseno);
            y = (float) (-(height/2)+(cx-espacio)*seno);
            CoorCirculo[i-1][0]=x;
            CoorCirculo[i-1][1]=-1*y;
        }
    }

    public void GrafoDefinicion(Paint pincel, Canvas canvas, int ver, float CoorCirculo [][], int[]sucesion){
        String tex="Sucesion: ";
        String mat="";
        int mat3[][] = new int [ver][ver];
        pincel.setColor(Color.BLACK);
        for (int i=0; i<ver; i++){
            tex += sucesion[i]+" ";
        }
        canvas.drawText(tex,20,70,pincel);

        CrearGrafo(ver, mat3, sucesion);

        for(int i=0; i<ver; i++){
            for(int j=0; j<ver; j++){
                if((i!=j) && (j>i)){
                    if(mat3[i][j] != 0){
                        pincel.setStrokeWidth(8);
                        canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1], CoorCirculo[j][0],CoorCirculo[j][1], pincel);
                    }
                }
            }
        }
    }

    public void  Euleriana(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][], ArrayList res){
        String arre = "";
        float x, y, x1, y1;
        int n;
        Paint pin = new Paint();
        pin.setAntiAlias(true);// Unalias
        //pin.setStyle(Paint.Style.FILL);
        pin.setStyle(Paint.Style.STROKE);
        pin.setStrokeWidth(8);
        pin.setColor(Color.BLACK);

        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        n = res.size();
        for(int i=0; i<n; i++){
            arre = (String) res.get(i);
            canvas.drawText(arre,20,70+(i*50),pincel);
        }


        for(int i = 0; i< ver; i++){
            for(int j=0; j< ver; j++){
                //arre += Matriz[i][j]+"\t";
                if((Matriz[i][j] == 1) && ((j>=i))){
                    if(i==j){
                        if(CoorCirculo[i][1] <= (height/2.0)){
                            x = (float) (CoorCirculo[i][0] - ((float) (((width/2.0)*0.3)/2.0)) + 10.0);
                            y = (float) (CoorCirculo[i][1] - ((float) (((width/2.0)*0.3)/2.0)) - 20.0);
                            x1 = (float) (CoorCirculo[i][0] + ((float) (((width/2.0)*0.3)/2.0)) - 10.0);
                            y1 = CoorCirculo[i][1] + ((float) (((width/2.0)*0.3)/2.0));
                            RectF rect = new RectF(x,y,x1,y1);
                            canvas.drawArc(rect, 0, -180, false, pin);
                        }else {
                            x = (float) (CoorCirculo[i][0] - ((float) (((width/2.0)*0.3)/2.0)) + 10.0);
                            y = CoorCirculo[i][1] - ((float) (((width/2.0)*0.3)/2.0));
                            x1 = (float) (CoorCirculo[i][0] + ((float) (((width/2.0)*0.3)/2.0)) - 10.0);
                            y1 = (float) (CoorCirculo[i][1] + ((float) (((width/2.0)*0.3)/2.0)) + 20.0);
                            RectF rect = new RectF(x,y,x1,y1);

                            canvas.drawArc(rect, 0, 180, false, pin);
                        }

                    }else{
                        canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1], CoorCirculo[j][0],CoorCirculo[j][1], pincel);
                    }
                }
            }
            //arre +="\n";
        }
        //canvas.drawText(arre,20,70,pincel);
    }

    public void PesosK(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][]){
        String peso="";
        float xm, ym;
        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        for(int i = 1; i<= ver; i++){
            for(int j=1; j<=ver; j++){
                if((i!=j) && (j>i)){
                    if(Matriz[i][j] != 999999999){
                        pincel.setColor(Color.BLACK);
                        canvas.drawLine(CoorCirculo[i-1][0],CoorCirculo[i-1][1], CoorCirculo[j-1][0],CoorCirculo[j-1][1], pincel);

                     /*   peso = String.valueOf(Matriz[i][j]);
                        xm = (float) ((CoorCirculo[i-1][0] + CoorCirculo[j-1][0])/2.0);
                        ym = (float) ((CoorCirculo[i-1][1] + CoorCirculo[j-1][1])/2.0);

                        xm = (float) ((CoorCirculo[i-1][0] + xm)/2.0);
                        ym = (float) ((CoorCirculo[i-1][1] + ym)/2.0);

                        pincel.setColor(Color.BLUE);
                        canvas.drawText(peso,xm,ym,pincel);*/
                    }
                }
            }
        }
    }

    public void PesosKM(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][]){
        String peso="";
        float xm, ym;
        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        for(int i = 0; i< ver; i++){
            for(int j=0; j<ver; j++){
                if((i!=j) && (j>i)){
                    if(Matriz[i][j] != 0){
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1], CoorCirculo[j][0],CoorCirculo[j][1], pincel);

                       /* peso = String.valueOf(Matriz[i][j]);
                        xm = (float) ((CoorCirculo[i][0] + CoorCirculo[j][0])/2.0);
                        ym = (float) ((CoorCirculo[i][1] + CoorCirculo[j][1])/2.0);

                        xm = (float) ((CoorCirculo[i][0] + xm)/2.0);
                        ym = (float) ((CoorCirculo[i][1] + ym)/2.0);

                        pincel.setColor(Color.BLUE);
                        canvas.drawText(peso,xm,ym,pincel);*/
                    }
                }
            }
        }
    }

    public void NumPesos(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][]){
        String peso="";
        float xm, ym;
        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        for(int i = 1; i<= ver; i++){
            for(int j=1; j<=ver; j++){
                if((i!=j) && (j>i)){
                    if(Matriz[i][j] != 999999999){

                        peso = String.valueOf(Matriz[i][j]);
                        xm = (float) ((CoorCirculo[i-1][0] + (3.0*CoorCirculo[j-1][0]))/4.0);
                        ym = (float) ((CoorCirculo[i-1][1] + (3.0*CoorCirculo[j-1][1]))/4.0);
                        /*xm = (float) ((CoorCirculo[i-1][0] + CoorCirculo[j-1][0])/2.0);
                        ym = (float) ((CoorCirculo[i-1][1] + CoorCirculo[j-1][1])/2.0);

                        xm = (float) ((CoorCirculo[i-1][0] + xm)/2.0);
                        ym = (float) ((CoorCirculo[i-1][1] + ym)/2.0);*/

                        pincel.setColor(Color.BLUE);
                        canvas.drawText(peso,xm,ym,pincel);
                    }
                }
            }
        }
    }

    public void PesosDij(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][]){
        float x, y, x1, y1;

        Paint pin = new Paint();
        pin.setAntiAlias(true);// Unalias
        //pin.setStyle(Paint.Style.FILL);
        pin.setStyle(Paint.Style.STROKE);
        pin.setStrokeWidth(8);
        pin.setColor(Color.argb(0xD9,112, 128, 144));
       // pin.setColor(Color.BLACK);

        //pincel.setColor(Color.BLACK);
        pincel.setColor(Color.argb(0xD9,112, 128, 144));
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        for(int i = 0; i< ver; i++){
            for(int j=0; j<ver; j++){
                if(Matriz[i][j] != 0xFFFF){
                    if(j>i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){
                            //pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0]+20,CoorCirculo[i][1], CoorCirculo[j][0]+20,CoorCirculo[j][1], pincel);
                        }else{
                          //  pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1]-20, CoorCirculo[j][0],CoorCirculo[j][1]-20, pincel);
                        }
                    }

                    if(j<i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){
                          //  pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0]-20,CoorCirculo[i][1], CoorCirculo[j][0]-20,CoorCirculo[j][1], pincel);
                        }else{
                           // pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1]+20, CoorCirculo[j][0],CoorCirculo[j][1]+20, pincel);
                        }
                    }

                    if(i==j) {
                        if (CoorCirculo[i][1] <= (height / 2.0)) {
                            x = (float) (CoorCirculo[i][0] - ((float) (((width / 2.0) * 0.3) / 2.0)) + 10.0);
                            y = (float) (CoorCirculo[i][1] - ((float) (((width / 2.0) * 0.3) / 2.0)) - 20.0);
                            x1 = (float) (CoorCirculo[i][0] + ((float) (((width / 2.0) * 0.3) / 2.0)) - 10.0);
                            y1 = CoorCirculo[i][1] + ((float) (((width / 2.0) * 0.3) / 2.0));
                            RectF rect = new RectF(x, y, x1, y1);
                            canvas.drawArc(rect, 0, -180, false, pin);
                        } else {
                            x = (float) (CoorCirculo[i][0] - ((float) (((width / 2.0) * 0.3) / 2.0)) + 10.0);
                            y = CoorCirculo[i][1] - ((float) (((width / 2.0) * 0.3) / 2.0));
                            x1 = (float) (CoorCirculo[i][0] + ((float) (((width / 2.0) * 0.3) / 2.0)) - 10.0);
                            y1 = (float) (CoorCirculo[i][1] + ((float) (((width / 2.0) * 0.3) / 2.0)) + 20.0);
                            RectF rect = new RectF(x, y, x1, y1);

                            canvas.drawArc(rect, 0, 180, false, pin);
                        }
                    }
                }
            }
        }
    }

    public void CaminoDij(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][], ArrayList Camino){
        String peso="", aux;
        int n;
        int ori, des;

        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        n = Camino.size();
        //peso = String.valueOf(n);
        //canvas.drawText(peso,20,70,pincel);
        //peso = String.valueOf(Camino.get(0));
        //canvas.drawText(peso,20,120,pincel);

        for(int i=0; i<n; i++){
            aux = String.valueOf(Camino.get(i));
            if(i != (n-1)){
                peso +="V"+aux+" --> ";
            }else{
                peso +="V"+aux;
            }
        }
        canvas.drawText("Camino:",20,70,pincel);
        canvas.drawText(peso,20,120,pincel);
        for(int i = 0; i <(n-1); i++){
            aux = String.valueOf(Camino.get(i));
            ori = Integer.parseInt(aux);
            aux = String.valueOf(Camino.get((i+1)));
            des = Integer.parseInt(aux);
           if((ori >=0 && ori < ver) && (des >=0 && des < ver)){
                if(ori < des){
                    if(CoorCirculo[ori][1] != CoorCirculo[des][1]){
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0]+20,CoorCirculo[ori][1], CoorCirculo[des][0]+20,CoorCirculo[des][1], pincel);
                    }else{
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0],CoorCirculo[ori][1]-20, CoorCirculo[des][0],CoorCirculo[des][1]-20, pincel);
                    }
                }

                if(ori > des){
                    if(CoorCirculo[ori][1] != CoorCirculo[des][1]){
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0]-20,CoorCirculo[ori][1], CoorCirculo[des][0]-20,CoorCirculo[des][1], pincel);
                    }else{
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0],CoorCirculo[ori][1]+20, CoorCirculo[des][0],CoorCirculo[des][1]+20, pincel);
                    }
                }

            }
        }

    }

    public void NumDij(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][]){
        String peso="";
        float xm, ym;
        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        for(int i = 0; i< ver; i++){
            for(int j=0; j<ver; j++){
                if(Matriz[i][j] != 0xFFFF){
                    if(j>i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){
                            peso = String.valueOf(Matriz[i][j]);
                            xm = (float) (((CoorCirculo[i][0]+20) +(3.0*(CoorCirculo[j][0])+20)) /4.0);
                            ym = (float) ((CoorCirculo[i][1] + (3.0*CoorCirculo[j][1]))/4.0);
                            /*xm = (float) (((CoorCirculo[i][0]+20) + (CoorCirculo[j][0])+20)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + CoorCirculo[j][1])/2.0);

                            xm = (float) (((CoorCirculo[i][0]+20) + xm)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[i][0]+20,CoorCirculo[i][1],CoorCirculo[j][0]+20,CoorCirculo[j][1],true);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[j][0]+20,CoorCirculo[j][1],true);

                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm+20,ym,pincel);
                        }else{
                            peso = String.valueOf(Matriz[i][j]);

                            xm = (float) ((CoorCirculo[i][0] +(3.0*CoorCirculo[j][0]))/4.0);
                            ym = (float) (((CoorCirculo[i][1]-20) + (3.0*(CoorCirculo[j][1]-20)))/4.0);

                           /* xm = (float) ((CoorCirculo[i][0] + CoorCirculo[j][0])/2.0);
                            ym = (float) (((CoorCirculo[i][1]-20) + (CoorCirculo[j][1])-20)/2.0);

                            xm = (float) ((CoorCirculo[i][0] + xm)/2.0);
                            ym = (float) (((CoorCirculo[i][1]-20) + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[i][0],CoorCirculo[i][1]-20,CoorCirculo[j][0],CoorCirculo[j][1]-20,true);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[j][0],CoorCirculo[j][1]-20,true);

                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm+20,ym,pincel);
                        }
                    }

                    if(j<i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){

                            peso = String.valueOf(Matriz[i][j]);

                            xm = (float) (((CoorCirculo[i][0]-20)+(3.0*(CoorCirculo[j][0]-20)))/4.0);
                            ym = (float) ((CoorCirculo[i][1] + (3.0*CoorCirculo[j][1]))/4.0);

                            /*xm = (float) (((CoorCirculo[i][0]-20) + (CoorCirculo[j][0])-20)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + CoorCirculo[j][1])/2.0);

                            xm = (float) (((CoorCirculo[i][0]-20) + xm)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[j][0]-20,CoorCirculo[j][1],CoorCirculo[i][0]-20,CoorCirculo[i][1],false);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[i][0]-20,CoorCirculo[i][1],false);

                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm-40,ym,pincel);
                        }else{
                            peso = String.valueOf(Matriz[i][j]);

                            xm = (float) ((CoorCirculo[i][0] + (3.0*CoorCirculo[j][0]))/4.0);
                            ym = (float) (((CoorCirculo[i][1]+20)+(3.0*(CoorCirculo[j][1]+20)))/4.0);

                           /* xm = (float) ((CoorCirculo[i][0] + CoorCirculo[j][0])/2.0);
                            ym = (float) (((CoorCirculo[i][1]+20) + (CoorCirculo[j][1])+20)/2.0);

                            xm = (float) ((CoorCirculo[i][0] + xm)/2.0);
                            ym = (float) (((CoorCirculo[i][1]+20) + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[j][0],CoorCirculo[j][1]+20,CoorCirculo[i][0],CoorCirculo[i][1]+20,false);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[i][0],CoorCirculo[i][1]+20,false);
                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm-40,ym,pincel);
                        }
                    }

                    if(i==j) {
                        if (CoorCirculo[i][1] <= (height / 2.0)) {
                            peso = String.valueOf(Matriz[i][j]);
                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,CoorCirculo[i][0],CoorCirculo[i][1]-80,pincel);
                        } else {
                            peso = String.valueOf(Matriz[i][j]);
                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,CoorCirculo[i][0],CoorCirculo[i][1]+120,pincel);
                        }
                    }

                }
            }
        }
    }

    public void arrow(Paint pincel, Canvas canvas,float xm, float ym, float x1, float y1, float x2, float y2, boolean sen){
        float xp, yp;
        xp = (x1+x2)/2;
        yp = (y1+y2)/2;
        float m, aux;

        //xp = xm;
        //yp = ym;

        aux=(x2-x1);
        if(aux!=0){
            m = (y2-y1)/aux;
        }else{
            m = 0xFFFF;
        }

        pincel.setColor(Color.BLUE);
        if(sen){
           xp = (x2+xp)/2;
           yp = (y2+yp)/2;

            if(m == 0xFFFF){
                canvas.drawLine(xp ,yp,xp-10, yp-15, pincel);
                canvas.drawLine(xp,yp,xp+10, yp-15, pincel);
                //canvas.drawLine(xp+10,yp+10,xp-10, yp+10, pincel);
            }else{
                if(m == 0){
                    canvas.drawLine(xp ,yp,xp-10, yp-10, pincel);
                    canvas.drawLine(xp,yp,xp-10, yp+10, pincel);
                    //canvas.drawLine(xp+10,yp+10,xp-10, yp+10, pincel);
                }else{
                    if(m<0){
                        if(y1<y2){
                            canvas.drawLine(xp ,yp,xp, yp-15, pincel);
                            canvas.drawLine(xp,yp,xp+15, yp, pincel);
                        }else{
                            canvas.drawLine(xp ,yp,xp, yp+15, pincel);
                            canvas.drawLine(xp,yp,xp-15, yp, pincel);
                        }
                    }else{
                        if(m>0){
                            canvas.drawLine(xp ,yp,xp-15, yp, pincel);
                            canvas.drawLine(xp,yp,xp, yp-15, pincel);
                            /*if(y1>y2){
                                canvas.drawLine(xp ,yp,xp-15, yp, pincel);
                                canvas.drawLine(xp,yp,xp, yp-15, pincel);
                            }else{
                                canvas.drawLine(xp ,yp,xp, yp+15, pincel);
                                canvas.drawLine(xp,yp,xp+15, yp, pincel);
                            }*/

                        }
                    }
                }
            }

        }else{
            xp = (x1+xp)/2;
            yp = (y1+yp)/2;

            if(m == 0xFFFF){
                canvas.drawLine(xp ,yp,xp-10, yp+15, pincel);
                canvas.drawLine(xp,yp,xp+10, yp+15, pincel);
                //canvas.drawLine(xp+10,yp+10,xp-10, yp+10, pincel);
            }else{
                if(m == 0){
                    canvas.drawLine(xp ,yp,xp+10, yp-10, pincel);
                    canvas.drawLine(xp,yp,xp+10, yp+10, pincel);
                    //canvas.drawLine(xp+10,yp+10,xp-10, yp+10, pincel);
                }else{
                    if(m<0){
                        if(y1<y2){
                            canvas.drawLine(xp ,yp,xp, yp+15, pincel);
                            canvas.drawLine(xp,yp,xp-15, yp, pincel);
                        }else{
                            canvas.drawLine(xp ,yp,xp, yp-15, pincel);
                            canvas.drawLine(xp,yp,xp+15, yp, pincel);
                        }
                    }else{
                        if(m>0){
                            canvas.drawLine(xp ,yp,xp, yp+15, pincel);
                            canvas.drawLine(xp,yp,xp+15, yp, pincel);
                            /*if(y1>y2){
                                canvas.drawLine(xp ,yp,xp, yp+15, pincel);
                                canvas.drawLine(xp,yp,xp+15, yp, pincel);
                            }else{
                                canvas.drawLine(xp ,yp,xp-15, yp, pincel);
                                canvas.drawLine(xp,yp,xp, yp-15, pincel);
                            }*/
                        }
                    }
                }
            }
        }

    }

    public void GrafoCompletoDij(Paint pincel, Canvas canvas, int ver){
        for(int i =1; i<=ver; i++){
            pincel.setColor(Color.rgb(161,6,132));
            canvas.drawCircle(CoorCirculo[i-1][0], CoorCirculo[i-1][1], (float) (((width/2.0)*0.3)/2.0), pincel);
            pincel.setColor(Color.WHITE);
            pincel.setTextSize(30);
            pincel.setAntiAlias(true);
            canvas.drawText("X"+(i-1), CoorCirculo[i-1][0]-20, (CoorCirculo[i-1][1])+10, pincel);
        }
    }

    public void PesosFloyd(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][]){
        float x, y, x1, y1;

        Paint pin = new Paint();
        pin.setAntiAlias(true);// Unalias
        //pin.setStyle(Paint.Style.FILL);
        pin.setStyle(Paint.Style.STROKE);
        pin.setStrokeWidth(8);
        pin.setColor(Color.argb(0xD9,112, 128, 144));
        // pin.setColor(Color.BLACK);

        //pincel.setColor(Color.BLACK);
        pincel.setColor(Color.argb(0xD9,112, 128, 144));
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        for(int i = 0; i< ver; i++){
            for(int j=0; j<ver; j++){
                if(Matriz[i][j] != 999999999){
                    if(j>i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){
                            //pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0]+20,CoorCirculo[i][1], CoorCirculo[j][0]+20,CoorCirculo[j][1], pincel);
                        }else{
                            //  pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1]-20, CoorCirculo[j][0],CoorCirculo[j][1]-20, pincel);
                        }
                    }

                    if(j<i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){
                            //  pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0]-20,CoorCirculo[i][1], CoorCirculo[j][0]-20,CoorCirculo[j][1], pincel);
                        }else{
                            // pincel.setColor(Color.BLACK);
                            canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1]+20, CoorCirculo[j][0],CoorCirculo[j][1]+20, pincel);
                        }
                    }
                }
            }
        }
    }

    public void NumFloyd(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][]){
        String peso="";
        float xm, ym;
        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        for(int i = 0; i< ver; i++){
            for(int j=0; j<ver; j++){
                if(Matriz[i][j] != 999999999){
                    if(j>i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){
                            peso = String.valueOf(Matriz[i][j]);
                            xm = (float) (((CoorCirculo[i][0]+20) +(3.0*(CoorCirculo[j][0])+20)) /4.0);
                            ym = (float) ((CoorCirculo[i][1] + (3.0*CoorCirculo[j][1]))/4.0);
                            /*xm = (float) (((CoorCirculo[i][0]+20) + (CoorCirculo[j][0])+20)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + CoorCirculo[j][1])/2.0);

                            xm = (float) (((CoorCirculo[i][0]+20) + xm)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[i][0]+20,CoorCirculo[i][1],CoorCirculo[j][0]+20,CoorCirculo[j][1],true);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[j][0]+20,CoorCirculo[j][1],true);

                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm+20,ym,pincel);
                        }else{
                            peso = String.valueOf(Matriz[i][j]);

                            xm = (float) ((CoorCirculo[i][0] +(3.0*CoorCirculo[j][0]))/4.0);
                            ym = (float) (((CoorCirculo[i][1]-20) + (3.0*(CoorCirculo[j][1]-20)))/4.0);

                           /* xm = (float) ((CoorCirculo[i][0] + CoorCirculo[j][0])/2.0);
                            ym = (float) (((CoorCirculo[i][1]-20) + (CoorCirculo[j][1])-20)/2.0);

                            xm = (float) ((CoorCirculo[i][0] + xm)/2.0);
                            ym = (float) (((CoorCirculo[i][1]-20) + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[i][0],CoorCirculo[i][1]-20,CoorCirculo[j][0],CoorCirculo[j][1]-20,true);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[j][0],CoorCirculo[j][1]-20,true);

                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm+20,ym,pincel);
                        }
                    }

                    if(j<i){
                        if(CoorCirculo[i][1] != CoorCirculo[j][1]){

                            peso = String.valueOf(Matriz[i][j]);

                            xm = (float) (((CoorCirculo[i][0]-20)+(3.0*(CoorCirculo[j][0]-20)))/4.0);
                            ym = (float) ((CoorCirculo[i][1] + (3.0*CoorCirculo[j][1]))/4.0);

                            /*xm = (float) (((CoorCirculo[i][0]-20) + (CoorCirculo[j][0])-20)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + CoorCirculo[j][1])/2.0);

                            xm = (float) (((CoorCirculo[i][0]-20) + xm)/2.0);
                            ym = (float) ((CoorCirculo[i][1] + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[j][0]-20,CoorCirculo[j][1],CoorCirculo[i][0]-20,CoorCirculo[i][1],false);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[i][0]-20,CoorCirculo[i][1],false);

                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm-40,ym,pincel);
                        }else{
                            peso = String.valueOf(Matriz[i][j]);

                            xm = (float) ((CoorCirculo[i][0] + (3.0*CoorCirculo[j][0]))/4.0);
                            ym = (float) (((CoorCirculo[i][1]+20)+(3.0*(CoorCirculo[j][1]+20)))/4.0);

                           /* xm = (float) ((CoorCirculo[i][0] + CoorCirculo[j][0])/2.0);
                            ym = (float) (((CoorCirculo[i][1]+20) + (CoorCirculo[j][1])+20)/2.0);

                            xm = (float) ((CoorCirculo[i][0] + xm)/2.0);
                            ym = (float) (((CoorCirculo[i][1]+20) + ym)/2.0);*/

                            arrow(pincel,canvas,xm,ym,CoorCirculo[j][0],CoorCirculo[j][1]+20,CoorCirculo[i][0],CoorCirculo[i][1]+20,false);
                            //arrow(pincel,canvas,xm,ym,CoorCirculo[i][0],CoorCirculo[i][1]+20,false);
                            pincel.setColor(Color.rgb(0,114,46));
                            canvas.drawText(peso,xm-40,ym,pincel);
                        }
                    }

                }
            }
        }
    }

    public void CaminoFloyd(Paint pincel, Canvas canvas, int ver, int[][] Matriz,float CoorCirculo [][], ArrayList Camino){
        String peso="", aux;
        int n;
        int ori, des;

        pincel.setColor(Color.BLACK);
        pincel.setStrokeWidth(8);
        pincel.setTextSize(50);
        n = Camino.size();

        for(int i=0; i<n; i++){
            aux = String.valueOf(Camino.get(i));
            if(i != (n-1)){
                peso +="V"+aux+" --> ";
            }else{
                peso +="V"+aux;
            }
        }
        canvas.drawText("Camino:",20,70,pincel);
        canvas.drawText(peso,20,120,pincel);
        for(int i = 0; i <(n-1); i++){
            aux = String.valueOf(Camino.get(i));
            ori = Integer.parseInt(aux);
            aux = String.valueOf(Camino.get((i+1)));
            des = Integer.parseInt(aux);
            ori = ori-1;
            des = des -1;
            if((ori >=0 && ori < ver) && (des >=0 && des < ver)){
                if(ori < des){
                    if(CoorCirculo[ori][1] != CoorCirculo[des][1]){
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0]+20,CoorCirculo[ori][1], CoorCirculo[des][0]+20,CoorCirculo[des][1], pincel);
                    }else{
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0],CoorCirculo[ori][1]-20, CoorCirculo[des][0],CoorCirculo[des][1]-20, pincel);
                    }
                }

                if(ori > des){
                    if(CoorCirculo[ori][1] != CoorCirculo[des][1]){
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0]-20,CoorCirculo[ori][1], CoorCirculo[des][0]-20,CoorCirculo[des][1], pincel);
                    }else{
                        pincel.setColor(Color.RED);
                        canvas.drawLine(CoorCirculo[ori][0],CoorCirculo[ori][1]+20, CoorCirculo[des][0],CoorCirculo[des][1]+20, pincel);
                    }
                }

            }
        }

    }

    public void GrafoDivVen(Paint pincel, Canvas canvas, int ver, int[]suce, String text){

        pincel.setColor(Color.BLACK);
        pincel.setTextSize(50);
        canvas.drawText(text,20,70,pincel);
        
        for (int i=0; i<(ver-1);i++){
            pincel.setStrokeWidth(8);
            canvas.drawLine(CoorCirculo[i][0],CoorCirculo[i][1], CoorCirculo[i+1][0],CoorCirculo[i+1][1], pincel);
        }

        for(int i =1; i<=ver; i++){
            pincel.setColor(Color.rgb(161,6,132));
            canvas.drawCircle(CoorCirculo[i-1][0], CoorCirculo[i-1][1], (float) (((width/2.0)*0.3)/2.0), pincel);
            pincel.setColor(Color.WHITE);
            pincel.setTextSize(30);
            pincel.setAntiAlias(true);
            canvas.drawText(String.valueOf(suce[i-1]), CoorCirculo[i-1][0]-20, (CoorCirculo[i-1][1])+10, pincel);
        }
    }


    public void regresarMat(ArrayList Mat, int[][]Matriz, int ver){
        int i, j, k;
        k=0;
        for(i=0; i<ver; i++){
            for(j=0; j<ver; j++){
                Matriz[i][j] = (int) Mat.get(k);
                k++;
            }
        }
    }
}