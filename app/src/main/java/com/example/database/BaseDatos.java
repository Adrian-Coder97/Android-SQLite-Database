package com.example.database;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class BaseDatos extends AppCompatActivity {

    private EditText codigo, descr, precio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_datos);

        codigo = findViewById(R.id.txtCodigo);
        descr = findViewById(R.id.txtDescripcion);
        precio = findViewById(R.id.txtPrecio);
    }

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
}