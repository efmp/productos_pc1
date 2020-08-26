package com.emaza.productsmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.emaza.productsmanagement.entidades.DAOProducto;
import com.emaza.productsmanagement.entidades.Producto;

public class agregarProducto extends AppCompatActivity {


    DAOProducto daoProducto = new DAOProducto(this);

    ConstraintLayout layout;
    LinearLayout lyContainer;
    String data[] = {"Gama Alta","Gama Media", "Gama Baja"};

    EditText txtNombre, txtPrecio, txtStock;
    Spinner spCategoria;
    Button btnAgregar;

    String nombre, categoria;
    double precio;
    int stock;
    Producto producto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_producto);
        daoProducto.openDB();
        asignarReferemcias();

    }

    private void asignarReferemcias() {
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
        txtStock = findViewById(R.id.txtStock);
        spCategoria = findViewById(R.id.spCategoria);
        btnAgregar = findViewById(R.id.btnAgregar);
        spCategoria = findViewById(R.id.spCategoria);
        layout = findViewById(R.id.bgFondo);
        lyContainer = findViewById(R.id.lyContainerData);
        capturarEvento();

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,data);
        spCategoria.setAdapter(adapter);
        //
    }

    private void capturarEvento(){
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarNuevoProducto();
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

    private void capturarDatos() {
        nombre = txtNombre.getText().toString();
        categoria = spCategoria.getSelectedItem().toString();
        precio = Double.parseDouble(txtPrecio.getText().toString());
        stock = Integer.parseInt(txtStock.getText().toString());
        producto = new Producto(nombre, categoria, precio, stock);

    }
    private void showToasSuccess() {
        Toast.makeText(this,"se añadio un nuevo proucto",Toast.LENGTH_SHORT).show();
    }
    private void cerrarteclado() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
    private void agregarNuevoProducto(){
        if(txtNombre.getText().toString().equals(""))Toast.makeText(this,"ingrese un nombre",Toast.LENGTH_SHORT).show();
        else if(txtPrecio.getText().toString().equals(""))Toast.makeText(this,"ingrese un precio",Toast.LENGTH_SHORT).show();
        else if(txtStock.getText().toString().equals(""))Toast.makeText(this,"ingrese un stock",Toast.LENGTH_SHORT).show();
        else {
            capturarDatos();
            daoProducto.registrarProducto(producto);
            showToasSuccess();
            finish();
        }
    }

}