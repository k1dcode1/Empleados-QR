package com.kidcode1.ccalpamarca;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DBRecycler1 extends AppCompatActivity {
    ArrayList<Usuario> listaUsuario;
    RecyclerView RecycleriewUsuarios;
    DatabaseHelper conn;
    private EditText etsearch;
    private Button btnBuscar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_b_recycler1);

        conn=new DatabaseHelper(getApplicationContext());
        listaUsuario = new ArrayList<>();
        RecycleriewUsuarios= findViewById(R.id.rv_recyclerPersonas);
        RecycleriewUsuarios.setLayoutManager(new LinearLayoutManager(this));
        etsearch = (EditText) findViewById(R.id.etBuscar1);
        btnBuscar1 = (Button) findViewById(R.id.btnBuscar1);


        consultarListaPersonas();
        ListaPersonasAdapter adapter = new ListaPersonasAdapter(listaUsuario);
        //adapter.notifyDataSetChanged();
        RecycleriewUsuarios.setAdapter(adapter);

        btnBuscar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db =conn.getReadableDatabase();
                Usuario usuario = null;
                listaUsuario.clear();

                String id = etsearch.getText().toString();
                Toast.makeText(getApplicationContext(),"se limpio el list y id es "+id,Toast.LENGTH_LONG).show();
                //String prueba ="rojas";
                Cursor cursor = db.rawQuery("SELECT ID,NAME,EMAIL FROM IDU WHERE NAME LIKE '%"+id+"%'",null);
                for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                    usuario = new Usuario();
                    usuario.setId(cursor.getString(0));
                    usuario.setName(cursor.getString(1));
                    usuario.setEmail(cursor.getString(2));

                    listaUsuario.add(usuario);
                }
                ListaPersonasAdapter adapter = new ListaPersonasAdapter(listaUsuario);
                //adapter.notifyDataSetChanged();
                RecycleriewUsuarios.setAdapter(adapter);
            }
        });

    }
    public String [] buscarData(String id){
        String[] datos =new String[4];
        SQLiteDatabase db =conn.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT ID,NAME,EMAIL FROM IDU WHERE NAME LIKE '%"+id+"%'",null);
        if (cursor.moveToFirst()){
            for (int i=0;i<3;i++){
                datos[i]=cursor.getString(i);
            }
            datos[3]="Encontrado";
        }
        else
        {
            datos[3]="No se pudo encntrar el numero "+id;
        }
        return datos;
    }

    private void consultarListaPersonas() {
        SQLiteDatabase db =conn.getReadableDatabase();
        Usuario usuario= null;
        String id = etsearch.getText().toString();
        //String prueba ="rojas";
        Cursor cursor = db.rawQuery("SELECT ID,NAME,EMAIL FROM IDU WHERE NAME LIKE '%"+id+"%'",null);
        while(cursor.moveToNext()){
            usuario = new Usuario();
            usuario.setId(cursor.getString(0));
            usuario.setName(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));

            listaUsuario.add(usuario);
        }
    }
}