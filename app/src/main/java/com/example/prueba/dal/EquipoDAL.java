package com.example.prueba.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.prueba.dto.Equipo;
import com.example.prueba.helpers.DatabaseHelper;

import java.util.ArrayList;

public class EquipoDAL {
    private DatabaseHelper helper;
    private Equipo equipo;

    public EquipoDAL(Context context){
        this.helper = new DatabaseHelper(context);
        this.equipo = new Equipo();
    }

    public EquipoDAL(Context context, Equipo equipo){
        this.helper = new DatabaseHelper(context);
        this.equipo = equipo;
    }

    public boolean insertar() {
        return this.tryInsert();
    }

    public boolean insertar(Equipo equipo){
        this.equipo = equipo;
        return this.tryInsert();
    }

    public ArrayList<Equipo> seleccionar(){
        ArrayList<Equipo> listado = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor consulta = db.rawQuery("SELECT * FROM equipo", null);

        if (consulta.moveToFirst()){
            do {
                int serie = consulta.getInt(0);
                String marca = consulta.getString(1);
                String descripcion = consulta.getString(2);

                Equipo equipo = new Equipo(serie, marca, descripcion);
                listado.add(equipo);
            } while(consulta.moveToNext());
        }
        return listado;
    }

    public boolean actualizar(int serie, Equipo equipo){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues e = new ContentValues();
        e.put("serie", equipo.getSerie());
        e.put("marca", equipo.getMarca());
        e.put("descripcion", equipo.getDescripcion());
        try{
            int filasAfectadas;
            filasAfectadas = db.update(
                    "equipo",e,"serie = ?", new String[] {String.valueOf(serie)});
            return (filasAfectadas > 0);
        } catch (Exception ex){

        }
        return false;
    }

    public boolean actualizar(Equipo equipo){
        SQLiteDatabase db= helper.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put("marca",equipo.getMarca());
        c.put("descripcion", equipo.getDescripcion());

        try {
            int filaAfectada = db.update("equipo", c,"serie = ?",
                    new String[]{ String.valueOf(equipo.getSerie()) });
            return (filaAfectada>0);
        } catch (Exception e) {

        }
        return false;
    }

    public boolean eliminar(int serie) {
        SQLiteDatabase db = helper.getWritableDatabase();
        int filasAfectadas;

        try{
            filasAfectadas = db.delete("equipo", "serie = ?", new String[] {String.valueOf(serie) });
        } catch ( Exception ex) {
            return false;
        }
        return (filasAfectadas == 1);
    }

    public boolean tryInsert(){
        SQLiteDatabase db= helper.getWritableDatabase();

        ContentValues c = new ContentValues();
        c.put("marca", this.equipo.getMarca());
        c.put("descripcion", this.equipo.getDescripcion());

        try {
            db.insert("equipo",null, c);
        }catch (Exception e){
            return false;
        }

        return true;
    }
    public Equipo getEquipo(){ return this.equipo; }
}
