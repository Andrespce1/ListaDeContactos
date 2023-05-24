package com.example.agendadecontactos;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Iterator;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText et_nombre, et_telefono, et_direccion, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_nombre = (EditText) findViewById(R.id.txtName);
        et_telefono = (EditText) findViewById(R.id.txtTelefono);
        et_direccion = (EditText) findViewById(R.id.txtDireccion);
        et_email = (EditText) findViewById(R.id.txtEmail);
        mostrarDatosEnTableLayout();
    }

    public void GuardarContacto(View view) {
        String nombre = et_nombre.getText().toString();
        String telefono = et_telefono.getText().toString();
        String direccion = et_direccion.getText().toString();
        String email = et_email.getText().toString();

        SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(nombre + "_telefono", telefono);
        editor.putString(nombre + "_direccion", direccion);
        editor.putString(nombre + "_email", email);
        editor.commit();

        Toast.makeText(this, "El contacto ha sido guardado", Toast.LENGTH_SHORT).show();
    }

    public void BuscarContacto(View view) {
        String nombre = et_nombre.getText().toString();
        SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);

        String telefono = preferencias.getString(nombre + "_telefono", "");
        String direccion = preferencias.getString(nombre + "_direccion", "");
        String email = preferencias.getString(nombre + "_email", "");

        if (telefono.isEmpty() && direccion.isEmpty() && email.isEmpty()) {
            Toast.makeText(this, "No se encontró ningún registro", Toast.LENGTH_SHORT).show();
        } else {
            et_telefono.setText(telefono);
            et_direccion.setText(direccion);
            et_email.setText(email);
        }
    }

    public void EliminarContacto(View view) {
        String nombre = et_nombre.getText().toString();

        SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        SharedPreferences.Editor obk_editor = preferencias.edit();

        if (preferencias.contains(nombre)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Confirmar eliminación");
            builder.setMessage("¿Está seguro de eliminar el contacto?");
            builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    obk_editor.remove(nombre);
                    obk_editor.commit();
                    Toast.makeText(MainActivity.this, "El contacto ha sido eliminado", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // No hacer nada
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Toast.makeText(this, "No se encontró ningún registro con ese nombre", Toast.LENGTH_SHORT).show();
        }
    }

    public void mostrarDatosEnTableLayout() {
        // Obtiene una referencia al TableLayout
        TableLayout tableLayout = findViewById(R.id.table_layout);

        // Obtiene todas las entradas de SharedPreferences
        SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        Map<String, ?> entradas = preferencias.getAll();

        // Recorre todas las entradas y crea una nueva fila por cada una
        for (Map.Entry<String, ?> entrada : entradas.entrySet()) {
            String key = entrada.getKey();
            String[] partes = key.split("_");

            String nombre = partes[0];
            String campo = "";

            // Verifica que haya al menos dos partes
            if (partes.length >= 2) {
                campo = partes[1];
            }

            // Crea una nueva fila solo si es la primera vez que se encuentra el nombre
            if (campo.equals("telefono")) {
                TableRow fila = new TableRow(this);

                TextView tv_nombre = new TextView(this);
                tv_nombre.setText(nombre);
                fila.addView(tv_nombre);

                TextView tv_telefono = new TextView(this);
                tv_telefono.setText(entrada.getValue().toString());
                fila.addView(tv_telefono);

                TextView tv_direccion = new TextView(this);
                tv_direccion.setText(preferencias.getString(nombre + "_direccion", ""));
                fila.addView(tv_direccion);

                TextView tv_email = new TextView(this);
                tv_email.setText(preferencias.getString(nombre + "_email", ""));
                fila.addView(tv_email);

                tableLayout.addView(fila);
            }
        }
    }
    public void actualizarContactos(View view) {
        // Obtiene una referencia al TableLayout
        TableLayout tableLayout = findViewById(R.id.table_layout);

        // Limpia el TableLayout para eliminar todas las filas existentes
        tableLayout.removeAllViews();

        // Obtiene todas las entradas de SharedPreferences
        SharedPreferences preferencias = getSharedPreferences("agenda", Context.MODE_PRIVATE);
        Map<String, ?> entradas = preferencias.getAll();

        // Recorre todas las entradas y crea una nueva fila por cada una
        for (Map.Entry<String, ?> entrada : entradas.entrySet()) {
            String key = entrada.getKey();
            String[] partes = key.split("_");

            String nombre = partes[0];
            String campo = "";

            // Verifica que haya al menos dos partes
            if (partes.length >= 2) {
                campo = partes[1];
            }

            // Crea una nueva fila solo si es la primera vez que se encuentra el nombre
            if (campo.equals("telefono")) {
                TableRow fila = new TableRow(this);

                TextView tv_nombre = new TextView(this);
                tv_nombre.setText(nombre);
                fila.addView(tv_nombre);

                TextView tv_telefono = new TextView(this);
                tv_telefono.setText(entrada.getValue().toString());
                fila.addView(tv_telefono);

                TextView tv_direccion = new TextView(this);
                tv_direccion.setText(preferencias.getString(nombre + "_direccion", ""));
                fila.addView(tv_direccion);

                TextView tv_email = new TextView(this);
                tv_email.setText(preferencias.getString(nombre + "_email", ""));
                fila.addView(tv_email);

                tableLayout.addView(fila);
            }
        }
    }

}