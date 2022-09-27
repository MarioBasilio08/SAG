package com.example.sistemadeanlisisdegrafos;

import junit.framework.TestCase;

import org.junit.Before;

public class definicion_de_grafosTest extends TestCase {


    public void testOrdenar() {
        int mat[]={8,10,5,42,9,1,4,12,6,7,21,89,10,47,85};
        assertEquals(true,definicion_de_grafos.Ordenar(15, mat));
    }

    public void testAnalisis() {
        int mat2[]={6,5,5,4,3,2,1};
        assertEquals(false,definicion_de_grafos.analisis(7,mat2));
    }

    public void testIngresar(){
        int mat[] ={0,0,0,0,0,0,0};

        assertEquals(true, definicion_de_grafos.Ingresar(8,mat,2));
        System.out.println(mat[2]);
    }
}