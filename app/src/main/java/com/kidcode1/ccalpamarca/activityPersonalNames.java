package com.kidcode1.ccalpamarca;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class activityPersonalNames extends AppCompatActivity {
    ListView lv1;
    private Button btnsearch;
    private EditText etsearch;

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_names);
        TextView t = (TextView) findViewById(R.id.tvtitle);
        String next = "<font color='#EE0000'>Ingrese Primer Apellido de la Persona a Buscar</font>";
        t.setText(Html.fromHtml(next));

        lv1 = (ListView) findViewById(R.id.lvData);
        btnsearch = (Button) findViewById(R.id.btnSearch);
        etsearch = (EditText) findViewById(R.id.etSearch);


        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ArrayList <String> ranking = new ArrayList<>();
                    db = openHelper.getWritableDatabase();
                    String id = etsearch.getText().toString();
                    Cursor cursor = db.rawQuery("SELECT ID,NAME FROM IDU WHERE NAME LIKE '%"+id+"%'",null);
                    if (cursor.moveToFirst()){
                        do{
                            ranking.add(cursor.getString(0) + " - " + cursor.getString(1));
                        }while(cursor.moveToNext());
                    }
                    //db.close();
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(activityPersonalNames.this, simple_list_item_1, ranking);
                    lv1.setAdapter(adapter);
                    //Toast.makeText(getApplicationContext(),ranking.indexOf(0),Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}