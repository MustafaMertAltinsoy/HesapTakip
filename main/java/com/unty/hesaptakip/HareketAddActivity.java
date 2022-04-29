package com.unty.hesaptakip;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class HareketAddActivity extends AppCompatActivity {

    public static int islemSayisi;
    public static int activeCode;


    public static float tutarText = 0.0f;

    public void hareketleriYukle(){
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        String name = musteriler.getString("musteri_" + activeCode ,"");
        TextView txt = (TextView) findViewById(R.id.cariIsim);
        TextView userPanelName = (TextView) findViewById(R.id.userPanelName);
        txt.setText(name);
        userPanelName.setText(name);
    }

    private static final String TAG = "MainActivity";

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add_activity);
        hareketleriYukle();

        TextView text = (TextView) findViewById(R.id.tutarText);
        text.setText(tutarText+"");

        mDisplayDate = (TextView) findViewById(R.id.tarihInput);

        Calendar c = Calendar.getInstance();
        String s = "";
        switch (c.get(Calendar.MONTH) + 1){
            case 1:
                s = "Ocak";
                break;
            case 2:
                s = "Şubat";
                break;
            case 3:
                s = "Mart";
                break;
            case 4:
                s = "Nisan";
                break;
            case 5:
                s = "Mayıs";
                break;
            case 6:
                s = "Haziran";
                break;
            case 7:
                s = "Temmuz";
                break;
            case 8:
                s = "Ağustos";
                break;
            case 9:
                s = "Eylül";
                break;
            case 10:
                s = "Ekim";
                break;
            case 11:
                s = "Kasım";
                break;
            case 12:
                s = "Aralık";
                break;
        }
        mDisplayDate.setText(c.get(Calendar.DAY_OF_MONTH) + " " + s + " " + c.get(Calendar.YEAR));

        borcOrAlacak=true;
        BorcOrAlacakDegistir();
    }

    public void tutarEkle(View view) {
        startActivity(new Intent(HareketAddActivity.this,ParaGiris.class));
        finish();
    }

    private boolean borcOrAlacak;//true = alacak, false = borc

    public void BorcOrAlacakDegistir() {
        /*ScrollView alacakSv = (ScrollView) findViewById(R.id.alacak);
        ScrollView borcSv = (ScrollView) findViewById(R.id.borc);*/

        Button borcBtn = (Button) findViewById(R.id.borcBtn);
        Button alacakBtn = (Button) findViewById(R.id.alacakBtn);

        ImageButton cariImage=(ImageButton) findViewById(R.id.userImage);
        TextView cariText=(TextView) findViewById(R.id.user);
        ImageButton tutarImage=(ImageButton) findViewById(R.id.tutarImage);
        TextView tutarText=(TextView) findViewById(R.id.tutar);
        ImageButton tarihImage=(ImageButton) findViewById(R.id.tarihImage);
        TextView tarihText=(TextView) findViewById(R.id.tarih);
        Button btn = (Button) findViewById(R.id.kaydetBtn);



        if(!borcOrAlacak){
            cariImage.setBackgroundResource(R.drawable.image_userkirmizi);
            cariText.setTextColor(getResources().getColor(R.color.red));
            tutarImage.setBackgroundResource(R.drawable.image_tutar);
            tutarText.setTextColor(getResources().getColor(R.color.red));
            tarihImage.setBackgroundResource(R.drawable.image_calendar);
            tarihText.setTextColor(getResources().getColor(R.color.red));
            btn.setBackgroundResource(R.drawable.image_redbtn);
            alacakBtn.setBackgroundResource(R.drawable.image_redbtn);
            borcBtn.setBackgroundResource(R.drawable.btninactive);

            borcBtn.setTextColor(getResources().getColor(R.color.black));
            alacakBtn.setTextColor(getResources().getColor(R.color.white));
        }else{
            cariImage.setBackgroundResource(R.drawable.image_useryesil);
            cariText.setTextColor(getResources().getColor(R.color.green));
            tutarImage.setBackgroundResource(R.drawable.image_paraicon);
            tutarText.setTextColor(getResources().getColor(R.color.green));
            tarihImage.setBackgroundResource(R.drawable.image_alacak_calendar);
            tarihText.setTextColor(getResources().getColor(R.color.green));
            btn.setBackgroundResource(R.drawable.image_kaydetbg);
            alacakBtn.setBackgroundResource(R.drawable.btninactive);
            borcBtn.setBackgroundResource(R.drawable.image_kaydetbg);
            borcBtn.setTextColor(getResources().getColor(R.color.white));
            alacakBtn.setTextColor(getResources().getColor(R.color.black));
        }
    }

    public void AlacakBtn(View view) {
        borcOrAlacak=true;
        BorcOrAlacakDegistir();
    }
    public void BorcBtn(View view) {
        borcOrAlacak=false;
        BorcOrAlacakDegistir();
    }

    public void hareketKaydet(View view) {
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = musteriler.edit();

        TextView tutarText = (TextView) findViewById(R.id.tutarText);
        float tutar = Float.parseFloat(tutarText.getText().toString());

        TextView tarihText=(TextView) findViewById(R.id.tarihInput);
        String tarih = tarihText.getText().toString();

        EditText aciklamaText = (EditText) findViewById(R.id.acıklamaInput);
        String aciklama = aciklamaText.getText().toString();

        islemSayisi++;
        editor.putInt("musteri_" + activeCode + "_islemSayisi_", islemSayisi);
        editor.putBoolean("musteri_" + activeCode + "_islem_" + islemSayisi + "_alacakORborc", borcOrAlacak);
        editor.putFloat("musteri_" + activeCode + "_islem_" + islemSayisi + "_tutar", tutar);
        editor.putString("musteri_" + activeCode + "_islem_" + islemSayisi + "_tarih", tarih);
        editor.putString("musteri_" + activeCode + "_islem_" + islemSayisi + "_aciklama", aciklama);

        editor.commit();

        startActivity(new Intent(HareketAddActivity.this, UserPanelActivity.class));
        finish();
    }

    public void tarihSec(View view){
        mDisplayDate = (TextView) findViewById(R.id.tarihInput);

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        HareketAddActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String s = "";
                //Turkce
                switch (month){
                    case 1:
                        s = "Ocak";
                        break;
                    case 2:
                        s = "Şubat";
                        break;
                    case 3:
                        s = "Mart";
                        break;
                    case 4:
                        s = "Nisan";
                        break;
                    case 5:
                        s = "Mayıs";
                        break;
                    case 6:
                        s = "Haziran";
                        break;
                    case 7:
                        s = "Temmuz";
                        break;
                    case 8:
                        s = "Ağustos";
                        break;
                    case 9:
                        s = "Eylül";
                        break;
                    case 10:
                        s = "Ekim";
                        break;
                    case 11:
                        s = "Kasım";
                        break;
                    case 12:
                        s = "Aralık";
                        break;
                }
                String date = day + " " + s + " " + year;
                mDisplayDate.setText(date);
            }
        };
    }

    public void geriGit(View view) {
        startActivity(new Intent(HareketAddActivity.this, UserPanelActivity.class));
        finish();
    }

}