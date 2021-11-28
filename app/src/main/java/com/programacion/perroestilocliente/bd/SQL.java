package com.programacion.perroestilocliente.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQL extends SQLiteOpenHelper {

    private static final String DATABASE = "perroestilobd";
    private static final int VERSION = 1;
    private final String tabUsuarios = "CREATE TABLE USUARIOS (" +
            "ID_USER INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "NOMBRE_USUARIO TEXT NOT NULL," +
            "APELLIDO_P TEXT NOT NULL," +
            "APELLIDO_M TEXT NOT NULL," +
            "GENERO TEXT NOT NULL," +
            "FECHA_NACIMIENTO TEXT NOT NULL," +
            "CONTRASENIA TEXT NOT NULL," +
            "TIPO_USUARIO TEXT NOT NULL," +
            "TELEFONO TEXT NOT NULL," +
            "EMAIL TEXT NOT NULL," +
            "ESTATUS TEXT NOT NULL);";

    private final String tabDirecciones = "CREATE TABLE DIRECCIONES (" +
            "ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "ID_USER TEXT NOT NULL," +
            "ESTADO TEXT NOT NULL," +
            "MUNICIPIO TEXT NOT NULL," +
            "LOCALIDAD TEXT NOT NULL," +
            "CODIGO_POSTAL TEXT NOT NULL," +
            "CALLE_EXT TEXT NOT NULL," +
            "CALLE_INT TEXT NOT NULL," +
            "REFERENCIAS TEXT NOT NULL," +
            "LATITUD TEXT NOT NULL," +
            "LONGITUD TEXT NOT NULL);";

    private final String tabProductos = "CREATE TABLE PRODUCTOS (" +
            "ID_PROD INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "ID_CATEGORIA TEXT NOT NULL," +
            "ID_DISENIO TEXT NOT NULL," +
            "ID_TALLA TEXT NOT NULL," +
            "DESCUENTO TEXT NOT NULL," +
            "PRECIO TEXT NOT NULL," +
            "RAITING TEXT NOT NULL," +
            "FECHA_CREACION TEXT NOT NULL," +
            "LONGITUD TEXT NOT NULL);";

    public SQL(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tabUsuarios);
        db.execSQL(tabDirecciones);
        db.execSQL(tabProductos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion) {
            db.execSQL("DROP TABLE IF EXISTS USUARIOS");
            db.execSQL("DROP TABLE IF EXISTS DIRECCIONES");
            db.execSQL("DROP TABLE IF EXISTS PRODUCTOS");
            db.execSQL(tabUsuarios);
            db.execSQL(tabProductos);
            db.execSQL(tabDirecciones);
        }
    }
}
