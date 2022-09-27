package com.example.sistemadeanlisisdegrafos;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class euleriana_conexaTest extends TestCase {

    private euleriana_conexa Prueba2;

    @Before
    public void setUp() throws Exception {
        Prueba2 = new euleriana_conexa();
    }
    @Test
    public void testConexo() {
        int mat[][]={{0,0,0,0,0},{0,0,0,1,1},{0,0,0,1,1},{0,1,1,0,1},{0,1,1,1,0}};
        assertEquals(true,euleriana_conexa.conexo(mat,5));
    }
}