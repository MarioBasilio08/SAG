package com.example.sistemadeanlisisdegrafos;

import android.app.Activity;
import android.content.Context;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import static android.os.Environment.getExternalStorageDirectory;

public class Archivos {
    public final String NomArch = "Datos.txt";
    public static boolean Grabar(String dato, File directorio, String Nombre){
        try {
            File file = new File(directorio, Nombre + ".txt");
            BufferedWriter archivo = new BufferedWriter(new FileWriter(file));
            //OutputStreamWriter archivo = new OutputStreamWriter(context.openFileOutput(NomArch, Activity.MODE_PRIVATE));
            archivo.write(dato);
            archivo.flush();
            archivo.close();
        }catch (Exception ex){
            return false;
        }
        return true;
    }
    public void copiarMat(ArrayList Mat, int[][]Matriz, int ver){
        int i, j, k;
        k=0;
        for(i=0; i<ver; i++){
            for(j=0; j<ver; j++){
                Mat.add(k,Matriz[i][j]);
                k++;
            }
        }
    }

}
