package com.unty.hesaptakip;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.time.Instant;

public class UserPanelActivity extends AppCompatActivity {

    public static int activeCode;
    public static int islemSayisi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cari_hesap_panel);


        Create();
    }
    public void Create(){
        TextView asad= (TextView) findViewById(R.id.para_SagTaraf);
        SharedPreferences musteriler = getSharedPreferences("preferences", Context.MODE_PRIVATE);

        LinearLayout hareketlerCreator = (LinearLayout) findViewById(R.id.hareketlerCreator);


        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(25, 10, 10, 25);


        activeCode=HareketAddActivity.activeCode;
        islemSayisi=HareketAddActivity.islemSayisi;

        String name = musteriler.getString("musteri_" + activeCode ,"");
        TextView userPanelName = (TextView) findViewById(R.id.userPanelName);
        userPanelName.setText(name);

        for(int i=1;i<=islemSayisi;i++){
            Boolean alacakORborc = musteriler.getBoolean("musteri_" + activeCode + "_islem_" + i + "_alacakORborc",true);
            String aciklama = musteriler.getString("musteri_" + activeCode + "_islem_" + i + "_aciklama","");
            Float tutar = musteriler.getFloat("musteri_" + activeCode + "_islem_" + i + "_tutar",0);
            String tarih = musteriler.getString("musteri_" + activeCode + "_islem_" + i + "_tarih","");

            RelativeLayout relativeLayout = new RelativeLayout(this);
            relativeLayout.setLayoutParams(params);
            relativeLayout.setBackgroundResource(R.drawable.kisiekleinputs);

            //Aciklama
            RelativeLayout.LayoutParams txtParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView txt = new TextView(this);
            txt.setLayoutParams(txtParam);
            txt.setId(R.id.userpanel_aciklama);
            txt.setText(aciklama);
            txt.setTextColor(getResources().getColor(R.color.gray));
            txt.setTextSize(13);
            txt.setTypeface(txt.getTypeface(), Typeface.BOLD);

            RelativeLayout.LayoutParams txtParamRules = (RelativeLayout.LayoutParams) txt.getLayoutParams();
            txtParamRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            txtParamRules.addRule(RelativeLayout.LEFT_OF,R.id.userpanel_TLImage);

            //Tarih
            RelativeLayout.LayoutParams tarihParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView tarihTxt = new TextView(this);
            tarihTxt.setLayoutParams(tarihParam);
            tarihTxt.setText(tarih);
            tarihTxt.setTextColor(getResources().getColor(R.color.gray));
            tarihTxt.setTextSize(10);
            tarihTxt.setTypeface(txt.getTypeface(), Typeface.BOLD);

            RelativeLayout.LayoutParams tarihParamRules = (RelativeLayout.LayoutParams) tarihTxt.getLayoutParams();
            tarihParamRules.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            tarihParamRules.addRule(RelativeLayout.BELOW,txt.getId());

            //Money
            RelativeLayout.LayoutParams tutarParam = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView money = new TextView(this);
            money.setLayoutParams(tutarParam);
            money.setId(R.id.userpanel_tutar);
            money.setText(tutar + "");
            money.setTextSize(18);
            money.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            money.setTypeface(money.getTypeface(), Typeface.BOLD);
            if(alacakORborc){
                money.setTextColor(getResources().getColor(R.color.green));
            }
            else{
                money.setTextColor(getResources().getColor(R.color.red));
            }


            RelativeLayout.LayoutParams tutarParamRules = (RelativeLayout.LayoutParams) money.getLayoutParams();
            tutarParamRules.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            tutarParamRules.addRule(RelativeLayout.CENTER_IN_PARENT);

            //ImageViewTL
            RelativeLayout.LayoutParams imageTLParam = new RelativeLayout.LayoutParams(50, 50);
            ImageView imageTL = new ImageView(this);
            imageTL.setLayoutParams(imageTLParam);
            imageTL.setId(R.id.userpanel_TLImage);
            if(alacakORborc){
                imageTL.setBackgroundResource(R.drawable.lira_yesil);
            }else{
                imageTL.setBackgroundResource(R.drawable.lira_red);
            }

            RelativeLayout.LayoutParams imageTLParamRules = (RelativeLayout.LayoutParams) imageTL.getLayoutParams();
            imageTLParamRules.addRule(RelativeLayout.LEFT_OF,money.getId());
            imageTLParamRules.addRule(RelativeLayout.CENTER_IN_PARENT);

            relativeLayout.addView(txt,txtParamRules);
            relativeLayout.addView(tarihTxt,tarihParamRules);
            relativeLayout.addView(imageTL,imageTLParamRules);
            relativeLayout.addView(money,tutarParamRules);
            hareketlerCreator.addView(relativeLayout);
        }
    }

    public void bakiyeGoster(View view) {
    }

    public void hareketEkle(View view) {
        startActivity(new Intent(UserPanelActivity.this, HareketAddActivity.class));
        finish();
    }

    public void geriGit(View view) {
        startActivity(new Intent(UserPanelActivity.this, Kisiler_Ekrani.class));
        finish();
    }
}