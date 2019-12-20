package com.example.prueba;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.prueba.dal.EquipoDAL;
import com.example.prueba.dto.Equipo;

import java.io.NotSerializableException;
import java.util.ArrayList;

public class MainFrame extends AppCompatActivity {
    private EquipoDAL equipoDAL;
    private ListView listEquipo;
    private ArrayAdapter<Equipo> adapter;
    private ArrayList<Equipo> listaEquipos;
    private Button btnAgregar;

    private int codPosicion = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_framee);



        this.btnAgregar = (Button) findViewById(R.id.btnAgregar);
        this.equipoDAL = new EquipoDAL(getApplicationContext(), new Equipo());
        this.listaEquipos = new EquipoDAL(getBaseContext()).seleccionar();

        this.listEquipo = (ListView) findViewById(R.id.listEquipo);
        this.adapter = new ArrayAdapter<Equipo>(
                getApplicationContext(), android.R.layout.simple_list_item_1, this.listaEquipos

        );
        this.listEquipo.setAdapter(adapter);



        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmación");
        builder.setMessage("¿Desea borrar La Direccion?");
        builder.setPositiveButton("Si, borrar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int id = ((Equipo) listaEquipos.get(codPosicion)).getSerie();
                boolean r = equipoDAL.eliminar(id);
                if(r){
                    Toast.makeText(getApplicationContext(), "Se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    actualizarLista();
                } else {
                    Toast.makeText(getApplicationContext(), "No se ha podido eliminar la serie", Toast.LENGTH_LONG).show();
                }
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        final AlertDialog dialog = builder.create();

        listEquipo.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                codPosicion = posicion;
                dialog.show();
                return true;
            }
        });


        listEquipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicion, long l) {
                codPosicion = posicion;
                abrirEditarEquipoActivity();

            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intento = new Intent(MainFrame.this, AgregarEquipo.class);
                startActivity(intento);
            }
        });



    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarLista();
    }


    private void actualizarLista() {
        adapter.clear();
        adapter.addAll(equipoDAL.seleccionar());
        adapter.notifyDataSetChanged();
    }

    private void abrirEditarEquipoActivity() {
        Intent intento = new Intent(MainFrame.this, EditarEquipo.class);
        Equipo e = (Equipo) listaEquipos.get(codPosicion);

        intento.putExtra("equipo", e);
        startActivityForResult(intento, 100);
    }
}
