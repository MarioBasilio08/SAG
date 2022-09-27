package com.example.sistemadeanlisisdegrafos;

public class GrafMatPeso {
    static int INFINITO = 0xFFFF;
    int [][] matPeso;

    int numVerts;
    static final int maxVerts = 20;
    Vertice[] verts;

    public GrafMatPeso(int mx)
    {
        matPeso = new int [mx][mx];
        verts = new Vertice[mx];
        for (int i = 0; i < mx; i++)
            for (int j = 0; j < mx; j++)
                matPeso[i][j] = INFINITO;
        numVerts = 0;
    }

    public GrafMatPeso()
    {
        this(maxVerts);
    }

    public int pesoArco(int va, int vb) throws Exception
    {
        return matPeso[va][vb];
    }

    public int pesoArco(String a, String b) throws Exception
    {
        int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        return matPeso[va][vb];
    }

    public int numeroDeVertices()
    {
        return numVerts;
    }

    public Vertice[] vertices()
    {
        return verts;
    }

    public void nuevoVertice (String nom)
    {
        boolean esta = numVertice(nom) >= 0;
        if (!esta)
        {
            Vertice v = new Vertice(nom);
            v.asigVert(numVerts);
            verts[numVerts++] = v;
        }
    }

   /* public void nuevoArco(String a, String b, int peso)
            throws Exception
    {
        int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        if (va < 0 || vb < 0)
            throw new Exception ("Vértice no existe");
        matPeso[va][vb] = peso;
    }*/

    public void nuevoArco(int va, int vb, int peso)//throws Exception
    {
       /* if (va < 0 || vb < 0)
            throw new Exception ("Vértice no existe");*/
        matPeso[va][vb] = peso;
    }

    public boolean adyacente(String a, String b)throws Exception
    {
        int va, vb;
        va = numVertice(a);
        vb = numVertice(b);
        if (va < 0 || vb < 0)
            throw new Exception ("Vértice no existe");
        return matPeso[va][vb] != INFINITO;
    }

    public boolean adyacente(int va, int vb)throws Exception
    {
        if (va < 0 || vb < 0)
            throw new Exception ("Vértice no existe");
        return matPeso[va][vb] != INFINITO;
    }

    public int numVertice(String vs)
    {
        Vertice v = new Vertice(vs);
        boolean encontrado = false;
        int i = 0;
        for (; (i < numVerts) && !encontrado;)
        {
            encontrado = verts[i].equals(v);
            if (!encontrado) i++ ;
        }
        return (i < numVerts) ? i : -1 ;
    }

}
