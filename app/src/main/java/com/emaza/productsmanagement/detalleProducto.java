package com.emaza.productsmanagement;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.emaza.productsmanagement.entidades.DAOProducto;
import com.emaza.productsmanagement.entidades.Producto;

import java.util.ArrayList;

public class detalleProducto extends AppCompatActivity {
    String data[] = {"Gama Alta", "Gama Media", "Gama Baja"};
    String id, nombre, categoria, precio, stock, descuento, estado;
    EditText txtnombre, txtprecio, txtstock;
    Spinner spCategoria;
    Button btnGuardar, btnEliminar;
    DAOProducto daoProducto = new DAOProducto(this);

    ConstraintLayout layout;
    LinearLayout lyContainer;
    RadioGroup rbGrupo;
    RadioButton rbCinco, rbDiez;
    CheckBox ckEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_producto);
        daoProducto.openDB();
        asignarReferencias();
        capturarEventos();
        //Toast.makeText(this,"onCreate",Toast.LENGTH_LONG).show();
    }


    private void asignarReferencias() {
        Bundle extras = getIntent().getExtras();
        ArrayList<String> lista = extras.getStringArrayList("data");
        id = lista.get(0);
        nombre = lista.get(1);
        categoria = lista.get(2);
        precio = lista.get(3);
        stock = lista.get(4);
        descuento = lista.get(5);
        estado = lista.get(6);

        txtnombre = findViewById(R.id.txtNombre);
        txtprecio = findViewById(R.id.txtPrecio);
        txtstock = findViewById(R.id.txtStock);
        rbGrupo = findViewById(R.id.rbGrupo);
        rbCinco = findViewById(R.id.rbCinco);
        rbDiez = findViewById(R.id.rbDiez);
        ckEstado = findViewById(R.id.ckEstado);

        btnGuardar = findViewById(R.id.btnGuardar);
        btnEliminar = findViewById(R.id.btnEliminar);
        spCategoria = findViewById(R.id.spCategoria);
        layout = findViewById(R.id.bgFondo);
        lyContainer = findViewById(R.id.lyContainerData);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);
        spCategoria.setAdapter(adapter);

        cargarCombo();
        txtnombre.setText(nombre);
        txtprecio.setText(precio);
        txtstock.setText(stock);
        if (Double.parseDouble(descuento) <= 0.05) {
            rbCinco.setChecked(true);
        } else {
            rbDiez.setChecked(true);
        }
        System.out.println("DATABOOLEANO: "+estado);
        if (Boolean.parseBoolean(estado)) {
            ckEstado.setChecked(true);
        } else {
            ckEstado.setChecked(false);
        }
    }

    private void cargarCombo() {

        switch (categoria) {
            case "Gama Alta":
                spCategoria.setSelection(0);
                break;
            case "Gama Media":
                spCategoria.setSelection(1);
                break;
            case "Gama Baja":
                spCategoria.setSelection(2);
                break;
        }
    }

    private void capturarEventos() {
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarProducto();
            }
        });
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminar();
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarteclado();
            }
        });
        lyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cerrarteclado();
            }
        });
    }

    private void guardarProducto() {
        if (txtnombre.getText().toString().equals(""))
            Toast.makeText(this, "ingrese un nombre", Toast.LENGTH_SHORT).show();
        else if (txtprecio.getText().toString().equals(""))
            Toast.makeText(this, "ingrese un precio", Toast.LENGTH_SHORT).show();
        else if (txtstock.getText().toString().equals(""))
            Toast.makeText(this, "ingrese un stock", Toast.LENGTH_SHORT).show();
        else {
            double descuento;
            boolean estado;
            if (rbGrupo.getCheckedRadioButtonId() == R.id.rbCinco) {
                descuento = 0.05;
            } else {
                descuento = 0.10;
            }
            if (ckEstado.isChecked()) {
                estado = true;
            } else {
                estado = false;
            }
            System.out.println("CHECKED ESTADO:"+ckEstado.isChecked());
            Producto p = new Producto(Integer.parseInt(id),
                    txtnombre.getText().toString(),
                    spCategoria.getSelectedItem().toString(),
                    Double.parseDouble(txtprecio.getText().toString()),
                    Integer.parseInt(txtstock.getText().toString()),
                    descuento,
                    estado);
            System.out.println("MYID:" + p.getId());
            daoProducto.modificarProducto(p);
            finish();
        }
    }

    private void eliminar() {
        AlertDialog.Builder alerta = new AlertDialog.Builder(detalleProducto.this);
        alerta.setMessage("Esta seguro que desea eliminar este producto?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        daoProducto.eliminarPersona(Integer.parseInt(id));
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog titulo = alerta.create();
        titulo.setTitle("Eliminar Producto");
        titulo.show();
    }

    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}