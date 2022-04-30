package com.unty.hesaptakip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Locale;

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

        DilAyarla();
        musterileriYukle();
    }

    public void kisilerAc(View view) {
        Toast.makeText(MainActivity.this,"Müşteriler yükleniyor...", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this, Kisiler_Ekrani.class));
        finish();
    }
    public void DilAyarla() {
        String languageToLoad  = "tr"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

    public void hareketler(View view) {
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.hareketlerBtn);
        Animation a = AnimationUtils.loadAnimation(this,R.anim.bakiye_acilma);
        rl.startAnimation(a);
    }
}