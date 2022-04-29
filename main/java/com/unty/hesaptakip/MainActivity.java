package com.unty.hesaptakip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static int musteriSayisi;

    public void musterileriYukle(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        musteriSayisi = musteriler.getInt("musteriSayisi",0);

        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        musterileriYukle();
    }

    public void kisilerAc(View view) {
        Toast.makeText(MainActivity.this,"Müşteriler yükleniyor...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, Kisiler_Ekrani.class));
        finish();
    }
}