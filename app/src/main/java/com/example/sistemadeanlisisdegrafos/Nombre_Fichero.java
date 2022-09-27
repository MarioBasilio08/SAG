package com.example.sistemadeanlisisdegrafos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class Nombre_Fichero extends AppCompatActivity implements View.OnClickListener{
    Button GuardarN;
    EditText TextNom;
    String Res;
    File rut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nombre__fichero);

        GuardarN = (Button) findViewById(R.id.butNombre);
        TextNom = (EditText) findViewById(R.id.TextNombre);

        GuardarN.setOnClickListener(this);
        Res = getIntent().getStringExtra("Resultados");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butNombre:
                String text;
                text = TextNom.getText().toString();
                TextNom.setText("");

                if (!text.equals("")){
                    rut = crearCarpetaAlmInterno("Resultados");
                    Toast.makeText(this,rut+"", Toast.LENGTH_LONG).show();
                    Archivos conexion = new Archivos();
                    if(conexion.Grabar(Res, rut, text)){
                        Toast.makeText(this,"Se grabo resultado", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this,"Error al grabar resultado", Toast.LENGTH_SHORT).show();
                    }
                    finish();
                }else{
                    Toast.makeText(this,"Ingrese nombre para su archivo", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    public File crearCarpetaAlmInterno(String nombreDirectorio){
        //Crear directorio en la memoria interna.
        File directorio = new File(getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), nombreDirectorio);
        //Muestro un mensaje en el logcat si no se creo la carpeta por algun motivo
        if(!directorio.mkdirs()){
            Log.e("TAG", "Error: No se creo el directorio privado");
        }
        return directorio;
    }
}