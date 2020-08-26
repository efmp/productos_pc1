package com.emaza.productsmanagement.entidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.emaza.productsmanagement.util.Constantes;
import com.emaza.productsmanagement.util.ProductosBD;

import java.util.ArrayList;

public class DAOProducto {
    ProductosBD bdProducto;
    SQLiteDatabase database;

    public DAOProducto(Context context){
        bdProducto = new ProductosBD(context);
    }
    public void openDB(){database = bdProducto.getWritableDatabase();}

    public void registrarProducto(Producto prod){
        try{
            ContentValues values = new ContentValues();
            values.put("nombreProd",prod.getNombre());
            values.put("categoriaProd",prod.getCategoria());
            values.put("PrecioProd",prod.getPrecio());
            values.put("StockProd",prod.getStock());
            database.insert(Constantes.NOMBRETABLA,null,values);
        }
        catch (Exception e){
            Log.d("failed to insert row in:",e.getMessage());
        }
    }
    public void modificarProducto(Producto prod){
        try{
            ContentValues values = new ContentValues();
            values.put("nombreProd",prod.getNombre());
            values.put("categoriaProd",prod.getCategoria());
            values.put("PrecioProd",prod.getPrecio());
            values.put("StockProd",prod.getStock());
            System.out.println(values.get("nombreProd"));
            System.out.println(values.get("categoriaProd"));
            System.out.println(values.get("PrecioProd"));
            System.out.println(values.get("StockProd"));
            database.update(Constantes.NOMBRETABLA,values,"id="+prod.getId(),null);
        }
        catch (Exception e){
            Log.d("failed to update row in",e.getMessage());
        }
    }
    public void eliminarPersona(int id){
        try{
            database.delete(Constantes.NOMBRETABLA,"id="+id,null);
        }
        catch (Exception e){
            Log.d("failed to delete row in",e.getMessage());
        }
    }

    public ArrayList<Producto> cargarProductos(){
        ArrayList<Producto> lista = new ArrayList<>();
        try{

            Cursor cursor = database.rawQuery("SELECT * FROM "+Constantes.NOMBRETABLA,null);
            while (cursor.moveToNext()){
                lista.add(new Producto(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getDouble(3),
                        cursor.getInt(4)
                ));
            }
        }
        catch (Exception e){

        }
        return lista;
    }
}
