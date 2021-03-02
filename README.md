# Android-SQLite-Database
Aplicacion android que almacena, consulta, elimina y modifica registros medio de SQLite


1. Contenido de la clase AdminSQLiteOpenHelper: 

package com.example.database;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper{

    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table articulos(codigo int primary key, descripcion text, precio real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}


2. Metodo de Altas en la calse principal: 

public void Altas(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Tienda", null, 1);//nombre de bd y version
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();//habilitar la db en modo lectura y escritura
        String Scodigo = codigo.getText().toString();
        String Sdesc = descr.getText().toString();
        String Sprecio = precio.getText().toString();

        if (!Scodigo.isEmpty() && !Sprecio.isEmpty() && !Sdesc.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", Scodigo);
            registro.put("descripcion", Sdesc);
            registro.put("precio", Sprecio);

            BaseDeDatos.insert("articulos", null, registro);
            BaseDeDatos.close();
            codigo.setText("");
            descr.setText("");
            precio.setText("");

            Toast.makeText(this, "Registro Existoso", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_LONG).show();
        }

    }


3. Metodo de Consultas: 

 public void Consultas(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Tienda", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String Scodigo = codigo.getText().toString();
        if (!Scodigo.isEmpty()) {
            Cursor fila = BaseDeDatos.rawQuery("select descripcion, precio from articulos where codigo =" + Scodigo, null);
            if (fila.moveToFirst()) {
                descr.setText(fila.getString(0));
                precio.setText(fila.getString(1));
                BaseDeDatos.close();
            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_LONG).show();
                BaseDeDatos.close();
            }

        } else {
            Toast.makeText(this, "Introduce el codigo del articulo", Toast.LENGTH_LONG).show();
        }
    }
    
4. Metodo de Bajas: 

public void Bajas(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Tienda", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();
        String Scodigo = codigo.getText().toString();
        if (!Scodigo.isEmpty()) {
            int cantidad = BaseDeDatos.delete("articulos", "codigo=" + Scodigo, null);
            BaseDeDatos.close();
            codigo.setText("");
            descr.setText("");
            precio.setText("");

            if (cantidad == 1) {
                Toast.makeText(this, "Articulo eliminado exitosamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Introduce el codigo del articulo", Toast.LENGTH_LONG).show();
        }

    }
    
    
5. Metodo de modificaciones: 

 public void Modificaciones(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "Tienda", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String Scodigo = codigo.getText().toString();
        String Sdesc = descr.getText().toString();
        String Sprecio = precio.getText().toString();

        if (!Scodigo.isEmpty() && !Sprecio.isEmpty() && !Sdesc.isEmpty()) {
            ContentValues registro = new ContentValues();
            registro.put("codigo", Scodigo);
            registro.put("descripcion", Sdesc);
            registro.put("precio", Sprecio);

            int cantidad = BaseDeDatos.update("articulos", registro, "codigo=" + Scodigo, null);
            BaseDeDatos.close();

            if (cantidad == 1) {
                Toast.makeText(this, "Articulo modificado correctamente", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No existe el articulo", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "Llenar todos los campos", Toast.LENGTH_LONG).show();
        }
    }


