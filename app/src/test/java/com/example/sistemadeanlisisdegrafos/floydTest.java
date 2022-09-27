package com.example.sistemadeanlisisdegrafos;

import junit.framework.TestCase;

import org.junit.Test;

public class floydTest extends TestCase {


    @Test
    public void testCrearMatrices() {
        int matrizPrueba1 [][] = new int[5][5];
        int matrizPrueba2[][] = new int[5][5];
        int matrizPrueba3[][] = new int[5][5];
        int ver = 5;

        ///assertTrue("True", floyd.crearMatrices(matrizPrueba1, matrizPrueba2, matrizPrueba3, ver));
    }


    public void testCopiarMatriz() {
    }

    public void testFloyd() {
        int vertice = 4;
        int infinito = 999999999;
        int matFloyd[][]={{0,5,infinito,infinito},{50,0,15,5},{30,infinito,0,15},{15,infinito,5,0}};
        int matRec [][] = {{-1,2,3,4},{1,-1,3,4},{1,2,-1,4},{1,2,3,-1}};

       /// assertTrue("True", floyd.Floyd(matFloyd, vertice,matRec));
    }

    public void testImprimirmatriz() {
    }

    public void testBuscar() {
        int matRec[][]={{-1,2,4,2},
                        {4,-1,4,4},
                        {1,1,-1,4},
                        {1,1,3,-1}};
        int origen = 1;
        int destino = 4;
        String ruta = "";
        //assertTrue("true", floyd.buscar(origen,destino, matRec, ruta));
    }
}