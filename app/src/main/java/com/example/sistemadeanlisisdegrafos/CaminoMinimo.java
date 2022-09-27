package com.example.sistemadeanlisisdegrafos;

import java.util.ArrayList;

public class CaminoMinimo {
    private int [][] Pesos;
    private int [] ultimo;
    private int [] D;
    private boolean [] F;
    private int s, n; // vértice origen y número de vértices
    int[] ver=new int[10];
    int in=0;
    int a = 0;
    String cam = "";
    ArrayList<Integer> Ca =new ArrayList<Integer>();
    public CaminoMinimo(GrafMatPeso gp, int origen)
    {
        n = gp.numeroDeVertices();
        s = origen;
        Pesos = gp.matPeso;
        ultimo = new int [n];
        D = new int [n];
        F = new boolean [n];
    }
    public void caminoMinimos()
    {
        // valores iniciales
        for (int i = 0; i < n; i++)
        {
            F[i] = false;
            D[i] = Pesos[s][i];
            ultimo[i] = s;
        }
        F[s] = true; D[s] = 0;
        // Pasos para marcar los n-1 vértices
        for (int i = 1; i < n; i++)
        {
            int v = minimo();  /* selecciona vértice no marcado
                            de menor distancia */
            F[v] = true;   // actualiza distancia de vértices no marcados
            for (int w = 1; w < n; w++)
                if (!F[w])
                    if ((D[v] + Pesos[v][w]) < D[w])
                    {
                        D[w] = D[v] + Pesos[v][w];
                        ultimo[w] = v;
                    }
        }
    }
    private int minimo()
    {
        int mx = GrafMatPeso.INFINITO;
        int v = 1;
        for (int j = 1; j < n; j++)
            if (!F[j] && (D[j]<= mx))
            {
                mx = D[j];
                v = j;
            }
        return   v;
    }

    public void recuperaCamino(int v)
    {
        cam = "";
        Ca.clear();
        int anterior = ultimo[v];
        if (v != s){

            ver[in]=v;
            in++;
            a++;
            recuperaCamino(anterior); // vuelve al último del último
            Ca.add(v);
            cam +=" --> V" + v;
        } else{
            Ca.add(s);
            cam += "V" + s;
            a++;
            ver[in]=s;
            in++;
        }

    }
}
