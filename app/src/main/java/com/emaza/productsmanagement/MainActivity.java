package com.emaza.productsmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.emaza.productsmanagement.entidades.DAOProducto;
import com.emaza.productsmanagement.entidades.Producto;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ListView lstProductos;
    String[] from = new String[]{"Nombre","Categoria","Precio"};
    int[] to = new int[]{R.id.lblNombre, R.id.lblCategoria, R.id.lblPrecio};
    Button btnAgregar;
    DAOProducto daoProducto= new DAOProducto(this);
    ArrayList<Producto> listaProductos;
    Button btnBuscar;
    TextView txtBuscar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        daoProducto.openDB();
        asignarReferencias();
    }
    @Override
    protected void onResume(){
        super.onResume();
        daoProducto.openDB();
        asignarReferencias();
    }


    private void asignarReferencias() {
        mostrarProductos();
        btnAgregar = findViewById(R.id.btnAgregar);
        btnBuscar = findViewById(R.id.btnBuscar);
        txtBuscar = findViewById(R.id.txtBusqueda);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoAddView();
            }
        });

        lstProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Producto prod = listaProductos.get(i);
                goToProductDetail(prod);
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,txtBuscar.getText().toString(),Toast.LENGTH_SHORT).show();
               // mostrarProductosByName(txtBuscar.getText().toString());
            }
        });
        txtBuscar.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                return false;
            }
        });
        txtBuscar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mostrarProductosByName(txtBuscar.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    public void mostrarProductos(){
        listaProductos = daoProducto.cargarProductos();
        ArrayList<HashMap<String,String>> productos = new ArrayList<>();
        for(Producto producto:listaProductos){
            HashMap<String,String> dataProducto = new HashMap<>();
            dataProducto.put("Nombre", producto.getNombre());
            dataProducto.put("Categoria", String.valueOf(producto.getCategoria()));
            dataProducto.put("Precio", "S/. "+producto.getPrecio());
            dataProducto.put("Stock", String.valueOf(producto.getStock()));
            dataProducto.put("id", String.valueOf(producto.getId()));
            productos.add(dataProducto);
        }

        lstProductos = findViewById(R.id.lstProductos);
        SimpleAdapter adapter = new SimpleAdapter(this, productos, R.layout.producto_fila, from, to);
        lstProductos.setAdapter(adapter);
    }


    public void mostrarProductosByName(String name){
        listaProductos = daoProducto.cargarProductosByName(name);
        ArrayList<HashMap<String,String>> productos = new ArrayList<>();
        for(Producto producto:listaProductos){
            HashMap<String,String> dataProducto = new HashMap<>();
            dataProducto.put("Nombre", producto.getNombre());
            dataProducto.put("Categoria", String.valueOf(producto.getCategoria()));
            dataProducto.put("Precio", "S/. "+producto.getPrecio());
            dataProducto.put("Stock", String.valueOf(producto.getStock()));
            dataProducto.put("id", String.valueOf(producto.getId()));
            productos.add(dataProducto);
        }

        lstProductos = findViewById(R.id.lstProductos);
        SimpleAdapter adapter = new SimpleAdapter(this, productos, R.layout.producto_fila, from, to);
        lstProductos.setAdapter(adapter);
    }



    public void gotoAddView(){
        Intent agregarProducto = new Intent(this, agregarProducto.class);
        startActivity(agregarProducto);
    }
    public void goToProductDetail(Producto prod){
        Intent detalle = new Intent(this, detalleProducto.class);
        Bundle b = new Bundle();
        ArrayList<String> p = new ArrayList<>();
        p.add(String.valueOf(prod.getId()));
        p.add(prod.getNombre());
        p.add(prod.getCategoria());
        p.add(String.valueOf(prod.getPrecio()));
        p.add(String.valueOf(prod.getStock()));
        p.add(String.valueOf(prod.getDescuento()));
        p.add(String.valueOf(prod.getEstado()));
        b.putStringArrayList("data",p);
        detalle.putExtras(b);
        setResult(Activity.RESULT_OK,detalle);
        startActivity(detalle);
    }
}