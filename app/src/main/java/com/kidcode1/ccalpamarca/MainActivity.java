package com.kidcode1.ccalpamarca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText etFecha = (EditText) findViewById(R.id.etFecha);
        Button btn2 = (Button) findViewById(R.id.btnIngresar);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(),QRactivity.class);
                startActivityForResult(intent,0);
            }
        });

        //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date myDate = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(myDate);
        try {
            String dayOfMonth = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
            String month = String.valueOf(c.get(Calendar.MONTH));
            String year = String.valueOf(c.get(Calendar.YEAR));
            etFecha.setText(dayOfMonth+"-"+month+"-"+year);

        } catch (Exception e) {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}