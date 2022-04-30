package com.unty.hesaptakip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.fonts.FontFamily;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Kisiler_Ekrani extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    private List<RelativeLayout> itemsListLayout = new ArrayList<>();
    private List<TextView> itemsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kisiler_ekrani);
        musterileriYukle();

        EditText searchEditText = (EditText) findViewById(R.id.kisilerSearchBar);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    public void filter(String s){
        ArrayList<TextView> filteredList=new ArrayList<>();
        ArrayList<RelativeLayout> filteredListLayout=new ArrayList<>();

        for(int i=0; i<itemsList.size(); i++){
            RelativeLayout rl = itemsListLayout.get(i);
            TextView txt = itemsList.get(i);
            if(txt.getText().toString().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(txt);
                filteredListLayout.add(rl);
            }
        }

        /*for(TextView item: itemsList){
            if(item.getText().toString().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(item);
            }
        }*/
        for (RelativeLayout a : itemsListLayout) {
            a.setVisibility(View.GONE);
        }
        for (RelativeLayout a : filteredListLayout) {
            a.setVisibility(View.VISIBLE);
        }
    }

    public void musterileriYukle(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        ScrollView sc = (ScrollView) findViewById(R.id.kisilerScrollView);
        LinearLayout linear = (LinearLayout) findViewById(R.id.scrollViewLayout);
        Button musteriBtn;


        for(int i=1;i <= MainActivity.musteriSayisi;i++){
            String name = musteriler.getString("musteri_" + i + "_ad","");
            Boolean isDeleted = musteriler.getBoolean("musteri_" + i + "_isdeleted", false);

            if(!isDeleted) {
                RelativeLayout rl = new RelativeLayout(this);
                itemsListLayout.add(rl);
                rl.setBackgroundResource(R.drawable.image_musteribg);

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(20, 10, 10, 20);




                RelativeLayout.LayoutParams btnParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                //params.setMargins(20, 15, 20, 15);

                musteriBtn = new Button(this);
                musteriBtn.setLayoutParams(btnParams);
                musteriBtn.setText(i + "");
                musteriBtn.setId(R.id.kisilerpanel_btn);
                musteriBtn.setBackgroundResource(R.color.nothing);
                musteriBtn.setTextColor(getResources().getColor(R.color.nothing));
                String musteriAdSoyadCode = musteriBtn.getText().toString();
                musteriBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String adSoyadCode = musteriAdSoyadCode;
                        openUserPanel(adSoyadCode);
                    }
                });

                RelativeLayout.LayoutParams btnParamsRules = (RelativeLayout.LayoutParams) musteriBtn.getLayoutParams();
                btnParamsRules.addRule(RelativeLayout.LEFT_OF, R.id.kisilerpanel_btn);

                RelativeLayout.LayoutParams isimParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                TextView isim = new TextView(this);
                isim.setLayoutParams(isimParams);
                itemsList.add(isim);
                isim.setText(name);
                isim.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
                isim.setTextColor(getResources().getColor(R.color.black));
                isim.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                isim.setTextSize(20);

                RelativeLayout.LayoutParams isimParamsRules = (RelativeLayout.LayoutParams) isim.getLayoutParams();
                isimParamsRules.addRule(RelativeLayout.CENTER_IN_PARENT);
                isimParamsRules.leftMargin = 20;

                RelativeLayout.LayoutParams ucNoktaParams = new RelativeLayout.LayoutParams(75, 75);
                ImageButton ucNokta = new ImageButton(this);
                ucNokta.setLayoutParams(ucNoktaParams);
                ucNokta.setId(R.id.kisilerpanel_btn);
                ucNokta.setBackgroundResource(R.drawable.uc_nokta);
                ucNokta.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        musteriEditOrDelete(ucNokta, musteriAdSoyadCode);
                    }
                });

                RelativeLayout.LayoutParams ucNoktaParamsRules = (RelativeLayout.LayoutParams) ucNokta.getLayoutParams();
                ucNoktaParamsRules.addRule(RelativeLayout.CENTER_IN_PARENT);
                ucNoktaParamsRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

                rl.addView(isim, isimParamsRules);
                rl.addView(musteriBtn, btnParamsRules);
                rl.addView(ucNokta, ucNoktaParamsRules);
                linear.addView(rl, params);
            }
            editor.commit();
        }
    }
    public void musteriEditOrDelete(View view,String code) {
        this.code = code;
        PopupMenu popup = new PopupMenu(this,view);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.popup_menu_kisiler);
        popup.show();
    }
    String code;
    @Override
    public boolean onMenuItemClick(MenuItem item){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        switch (item.getItemId()){
            case R.id.kisiler_item1:
                //DuzenleActivity.activeCode = activeCode;
                KisiyiDuzenleActivity.code = code;
                startActivity(new Intent(Kisiler_Ekrani.this, KisiyiDuzenleActivity.class));
                finish();
                return true;
            case R.id.kisiler_item2:
                editor.putBoolean("musteri_" + code + "_isdeleted", true);
                editor.remove("musteri_" + code + "_ad");
                editor.remove("musteri_" + code + "_telefon");
                editor.remove("musteri_" + code + "_aciklama");

                startActivity(new Intent(Kisiler_Ekrani.this,Kisiler_Ekrani.class));
                Toast.makeText(this,"Başarıyla silindi",Toast.LENGTH_SHORT).show();
                editor.commit();
                return true;
            default:
                return false;
        }
    }
    public void openUserPanel(String code) {
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

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
