package com.unty.hesaptakip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

public class Kisiler_Ekrani extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisiler_ekrani);
        musterileriYukle();
    }

    int[] musteriIsimleri;
    public void musterileriYukle(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        ScrollView sc = (ScrollView) findViewById(R.id.kisilerScrollView);
        LinearLayout linear = (LinearLayout) findViewById(R.id.scrollViewLayout);
        Button musteriBtn;


        for(int i=1;i <= MainActivity.musteriSayisi;i++){
            String name = musteriler.getString("musteri_" + i,"");
            musteriBtn = new Button(this);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 10, 10, 20);

            musteriBtn.setLeft(20);
            musteriBtn.setText(i +"- "+ name);
            musteriBtn.setTextSize(20);
            musteriBtn.setTypeface(musteriBtn.getTypeface(), Typeface.BOLD);
            musteriBtn.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            musteriBtn.setBackgroundResource(R.drawable.image_musteribg);
            String musteriAdSoyadCode = musteriBtn.getText().toString();
            musteriBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] adSoyadSplit = musteriAdSoyadCode.split("-");
                    String adSoyadCode = adSoyadSplit[0];
                    openUserPanel(adSoyadCode);
                }
            });
            linear.addView(musteriBtn,params);
        }

        editor.commit();
    }
    public void openUserPanel(String code) {
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        HareketAddActivity.activeCode = Integer.parseInt(code);
        HareketAddActivity.islemSayisi = musteriler.getInt("musteri_" + Integer.parseInt(code) + "_islemSayisi_",0);

        startActivity(new Intent(Kisiler_Ekrani.this, UserPanelActivity.class));
        finish();
    }

    public void kisiEkle(View view) {
        startActivity(new Intent(Kisiler_Ekrani.this, MusteriEkle.class));
        finish();
    }

    public void geriGit(View view) {
        startActivity(new Intent(Kisiler_Ekrani.this, MainActivity.class));
        finish();
    }
}

/*int enYuksekPuan = ayarlar.getInt("enYuksek", 0);*/
