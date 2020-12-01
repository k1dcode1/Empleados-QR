package com.kidcode1.ccalpamarca;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class activity_searchDNI extends AppCompatActivity {
    Button _btnInsert, _btnDelete, _btnUpdate,btnImportar,btnBuscar;
    EditText _txtID, _txtName, _txtAdd, _txtPhone, _txtEmail;
    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_d_n_i);

        _btnInsert=(Button)findViewById(R.id.btnInsert);
        _btnDelete=(Button)findViewById(R.id.btnDlt);
        _btnUpdate=(Button)findViewById(R.id.btnUpdate);
        _txtID=(EditText)findViewById(R.id.txtId);
        _txtName=(EditText)findViewById(R.id.txtName);
        _txtAdd=(EditText)findViewById(R.id.txtAdd);
        _txtPhone=(EditText)findViewById(R.id.txtPhone);
        _txtEmail=(EditText)findViewById(R.id.txtEmail);
        openHelper=new DatabaseHelper(this);
        btnImportar = (Button) findViewById(R.id.btnImportar) ;
        btnBuscar = (Button) findViewById(R.id.btnBuscar);



        pedirPermisos();

        btnImportar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //inicia mi codigo
                switch (view .getId()){
                    case R.id.btnImportar:
                        alertDialog();
                        break;
                }

                //finaliza mi codigo
            }
        });

        _btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ids = _txtID.getText().toString();
                String name =_txtName.getText().toString();
                String address = _txtAdd.getText().toString();
                String phone = _txtPhone.getText().toString();
                String email= _txtEmail.getText().toString();
                db=openHelper.getWritableDatabase();
                insertData(ids,name, address, phone, email);
                Toast.makeText(getApplicationContext(), "INSERTED SUCCESSFULLY", Toast.LENGTH_LONG).show();
                _txtID.setText("");
                _txtName.setText("");
                _txtAdd.setText("");
                _txtPhone.setText("");
                _txtEmail.setText("");
            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String id = _txtID.getText().toString();
                String[] datos;
                datos=buscarData(id);
                _txtName.setText(datos[1]);
                _txtAdd.setText(datos[2]);
                _txtPhone.setText(datos[3]);
                _txtEmail.setText(datos[4]);
                Toast.makeText(getApplicationContext(),datos[5],Toast.LENGTH_SHORT).show();
            }
        });

        _btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db=openHelper.getWritableDatabase();
                String id = _txtID.getText().toString();
                deleteData(id);
                Toast.makeText(getApplicationContext(), "Deleted successfully", Toast.LENGTH_LONG).show();
            }
        });

        _btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name =_txtName.getText().toString();
                String address = _txtAdd.getText().toString();
                String phone = _txtPhone.getText().toString();
                String email= _txtEmail.getText().toString();
                db=openHelper.getWritableDatabase();
                updateData(name, address, phone, email);
                Toast.makeText(getApplicationContext(), "UPDATED SUCCESSFULLY", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertData(String ID,String name, String address, String phone, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLS_1,ID);
        contentValues.put(DatabaseHelper.COLS_2, name);
        contentValues.put(DatabaseHelper.COLS_3, address);
        contentValues.put(DatabaseHelper.COLS_4, phone);
        contentValues.put(DatabaseHelper.COLS_5, email);
        long id = db.insert(DatabaseHelper.TABLE_NAME, null, contentValues);
    }

    public String [] buscarData(String id){
        String[] datos =new String[6];
        db=openHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM IDU WHERE id ='"+id+"'",null);
        if (cursor.moveToFirst()){
            for (int i=0;i<5;i++){
                datos[i]=cursor.getString(i);
            }
            datos[5]="Encontrado";
        }
        else
        {
            datos[5]="No se pudo encntrar el numero "+id;
        }
        return datos;
    }

    public boolean deleteData(String id){
        return db.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper.COLS_1 + "=?", new String[]{id})>0;
    }
    public boolean updateData(String name, String address, String phone, String email){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.COLS_2, name);
        contentValues.put(DatabaseHelper.COLS_3, address);
        contentValues.put(DatabaseHelper.COLS_4, phone);
        contentValues.put(DatabaseHelper.COLS_5, email);
        String id = _txtID.getText().toString();
        return db.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper.COLS_1 + "=?", new String[]{id})>0;
    }

    public void importarCSV() {
        limpiarTablas("IDU");

        File carpeta = new File(Environment.getExternalStorageDirectory() + "/Datax");

        String archivoAgenda = carpeta.toString() + "/" + "Usuarios.csv";

        boolean isCreate = false;
        if(!carpeta.exists()) {
            Toast.makeText(this, "NO EXISTE LA CARPETA", Toast.LENGTH_SHORT).show();
        } else {
            String cadena;
            String[] arreglo;

            try {
                FileReader fileReader = new FileReader(archivoAgenda);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null) {

                    arreglo = cadena.split(",");

                    DatabaseHelper admin = new DatabaseHelper(this);
                    SQLiteDatabase db = admin.getWritableDatabase();

                    ContentValues registro = new ContentValues();

                    registro.put("Id", arreglo[0]);
                    registro.put("name", arreglo[1]);
                    registro.put("address", arreglo[2]);
                    registro.put("phone", arreglo[3]);
                    registro.put("email", arreglo[4]);

                    //listaUsuarios.add(
                    //        new Usuario(
                    //                arreglo[1],
                    //                arreglo[2]
                    //        )
                    //);

                    // los inserto en la base de datos
                    db.insert("IDU", null, registro);

                    db.close();

                    Toast.makeText(this, "SE IMPORTO EXITOSAMENTE", Toast.LENGTH_SHORT).show();

                    //adaptador = new AdaptadorUsuarios(MainActivity.this, listaUsuarios);
                    //rvUsuarios.setAdapter(adaptador);

                }
            } catch(Exception e) {
                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
    }

    public void pedirPermisos() {
        // PERMISOS PARA ANDROID 6 O SUPERIOR
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    0);

        }
    }

    public void limpiarTablas(String tabla) {
        DatabaseHelper admin = new DatabaseHelper(this);
        SQLiteDatabase db = admin.getWritableDatabase();

        borrarRegistros(tabla, db);

        Toast.makeText(this, "Se limpio los registros de la "+tabla, Toast.LENGTH_SHORT).show();
    }
    public void borrarRegistros(String tabla, SQLiteDatabase db) {
        db.execSQL("DELETE FROM "+tabla);
    }
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Cuidado");
        builder.setMessage("Si haces click en SI se borrara todo, Estas Seguro ?");
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                importarCSV();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Buena Chicho", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}