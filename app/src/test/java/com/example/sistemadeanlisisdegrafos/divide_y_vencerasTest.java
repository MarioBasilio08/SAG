package com.example.sistemadeanlisisdegrafos;

import junit.framework.TestCase;

public class divide_y_vencerasTest extends TestCase {

    public void testCrearS() {
    }

    public void testIngresar() {
    }

    public void testQuicksort() {
        int ver = 9;
        int Matriz[] = {10, 4, 8,16,32,0,88, 22,1,3};

        assertTrue(String.valueOf(true),divide_y_venceras.quicksort(Matriz, 0, ver));
    }
}