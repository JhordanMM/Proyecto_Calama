package com.example.prueba;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.prueba.dal.EquipoDAL;
import com.example.prueba.dto.Equipo;

public class AgregarEquipo extends AppCompatActivity {


    private EquipoDAL equipoDal;
    private EditText bMar;
    private EditText bDes;
    private Button btnAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_equipo);
        this.bMar = (EditText) findViewById(R.id.bMar);
        this.bDes = (EditText) findViewById(R.id.bDes);
        this.btnAgregar = (Button) findViewById(R.id.btnAgregar);
        this.equipoDal = new EquipoDAL(getApplicationContext());


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String marca = String.valueOf(bMar.getText());
                String descripcion = String.valueOf(bDes.getText());
                Equipo e = new Equipo( marca, descripcion);
                equipoDal = new EquipoDAL(getApplicationContext(), e);

                if(equipoDal.insertar()) {
                    Toast.makeText(getApplicationContext(), "OK! Insertó", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "MAL! NO Insertó", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}
