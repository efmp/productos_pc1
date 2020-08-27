package com.emaza.productsmanagement.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ProductosBD extends SQLiteOpenHelper {

    public ProductosBD(Context context) {
        super(context, Constantes.NOMBREBD, null, Constantes.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String commandSql = "CREATE TABLE "+Constantes.NOMBRETABLA+
                " (id integer Primary Key autoincrement," +
                "nombreProd text not null,"+
                "categoriaProd text not null,"+
                "precioProd real not null,"+
                "stockProd text not null,"+
                "descuentoProd real not null,"+
                "estadoProd integer not null);";
        sqLiteDatabase.execSQL(commandSql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
