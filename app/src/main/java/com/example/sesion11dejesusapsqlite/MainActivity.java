package com.example.sesion11dejesusapsqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText codigo, descripccion, ubicacion, existencias;
    private Button btnServicios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        codigo =findViewById(R.id.etCodigo);
        descripccion =findViewById(R.id.etDescripccion);
        ubicacion =findViewById(R.id.etUbicacion);
        existencias =findViewById(R.id.etExistencias);
        btnServicios =findViewById(R.id.btnServicios);
        btnServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent abrirSer = new Intent(getApplicationContext(), Servicios.class);
                startActivity(abrirSer);
            }
        });
    }
    public void altaProductos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos se reescribible

        //para guardar valor de variables del formulario
        String Codigo = codigo.getText().toString();
        String Descripcion = descripccion.getText().toString();
        String Ubicacion = ubicacion.getText().toString();
        String Existencia = existencias.getText().toString();


        //se crea contenedor para almacenar los valores
        ContentValues registro = new ContentValues();
        //se integran variables de java con valores y campos de la tabla articulo
        registro.put("cod", Codigo);
        registro.put("descripcion", Descripcion);
        registro.put("ubicacion", Ubicacion);
        registro.put("existencia", Existencia);
        //se inserta registro en tabla articulo
        bd.insert("articulo", null, registro);
        // Se cierra BD
        bd.close();
        //Se limpian los campos de texto
        codigo.setText(null);
        descripccion.setText(null);
        ubicacion.setText(null);
        existencias.setText(null);

        //Imprimir datos de registro exitoso en ventana emergente tipo TOAST
        Toast.makeText(this, "Exito al ingresar el registro\n\nCodigo:"+Codigo+"\nDescripcion:"+Descripcion+"\nUbicacion:"+Ubicacion+"Existencia:"+Existencia,Toast.LENGTH_LONG).show();
    }
    public void consultaProductos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos se reescribible

        //se asigna una variable para busqueda y consulta por campo distintivo
        String codigoConsulta = codigo.getText().toString();
        //Cursor recorre los campos d euna tabla hasta encontralo por campo distintivo
        Cursor fila = bd.rawQuery("SELECT descripcion,ubicacion,existencia from articulo where cod="+codigoConsulta,null);

        if(fila.moveToFirst()){//si condicion es verdadera, es decir, encontro un campo y sus datos
            descripccion.setText(fila.getString(0));
            ubicacion.setText(fila.getString(1));
            existencias.setText(fila.getString(2));
            Toast.makeText(this,"Registro encontrado de forma EXITOSA",Toast.LENGTH_LONG).show();
        }else{//condicion falsa si no encontro un registro
            Toast.makeText(this,"No existe Articulo con ese Codigo\nVerifica",Toast.LENGTH_LONG).show();
            bd.close();
        }
    }
    public void borrarProductos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se asigna variable para busqueda por campo distitivo caodigo producto
        String codigoBaja = codigo.getText().toString();
        //Se genera instrtuccion SQL para que se elimine el registro de producto
        int c = bd.delete("articulo","cod="+codigoBaja,null);
        if(c==1){
            Toast.makeText(this,"Registro eliminado de BD exitoso\nVerifica Consulta",Toast.LENGTH_LONG).show();
            //Limpia cajas de texto
            this.codigo.setText("");
            this.descripccion.setText("");
            this.ubicacion.setText("");
            this.existencias.setText("");
        }else{
            Toast.makeText(this,"Error\nNo existe Articulo con ese codigo",Toast.LENGTH_LONG).show();
        }
    }
    public void editarProductos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se declaran variables que vienen desde formulario sus datos
        String Codigo = codigo.getText().toString();
        String Descripcion = descripccion.getText().toString();
        String Ubicacion = ubicacion.getText().toString();
        String Existencia = existencias.getText().toString();

        //se genera un contenedor para almacenar los valores anteriores
        ContentValues registro = new ContentValues();
        registro.put("cod",Codigo);
        registro.put("descripcion",Descripcion);
        registro.put("ubicacion",Ubicacion);
        registro.put("existencia",Existencia);

        //Se crea la variable que contine la instruccion SQL encargada de modificar y almacenar valor 1 si edito
        int cant = bd.update("articulo",registro,"cod="+Codigo,null);
        bd.close();
        if(cant==1) {//condicion si realizo modificacion
            Toast.makeText(this,"Registro actualizado de forma correcta",Toast.LENGTH_LONG).show();
        }else {//contrario a no modificacion
            Toast.makeText(this,"Error\nNo se modifico registro",Toast.LENGTH_LONG).show();
        }
    }
}