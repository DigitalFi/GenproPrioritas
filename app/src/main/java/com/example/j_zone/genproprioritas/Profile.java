package com.example.j_zone.genproprioritas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends AppCompatActivity {

    private SharedPreferences user;
    private SharedPreferences updt;
    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final String nama = user.getString("user_name", "");
        final String email = user.getString("email", "");
        final String pic1 = user.getString("pic", "");
        final String urls = user.getString("url","");

        updt = getSharedPreferences("data_update", Context.MODE_PRIVATE);
        final String nomor = updt.getString("no_anggota", "");
        final String pic = updt.getString("picture", "");
        final String link = updt.getString("url", "");
        final String almt = updt.getString("alamat", "");
        final String tlp = updt.getString("phone", "");

        TextView Nama = (TextView) findViewById(R.id.Nama);
        TextView Email = (TextView) findViewById(R.id.Email);
        TextView Nomor = (TextView) findViewById(R.id.Noanggota);
        TextView Telepon = (TextView) findViewById(R.id.Telepon);
        TextView Alamat = (TextView) findViewById(R.id.Alamat);


        Nama.setText("Nama : "+nama);
        Email.setText("Email : "+email);
        Nomor.setText("Nomor Anggota : "+nomor);
        Alamat.setText("Alamat : "+almt);
        Telepon.setText("Nomor Telephone : "+tlp);

        view = (WebView) findViewById(R.id.profil_pic) ;
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new profile_pic());

        //ini manggil url web dari webview-nya

        if (!pic.isEmpty()){
            String url = link+pic;

            view.loadUrl(url);
            Toast.makeText(this, "foto dlm kondisi if :"+pic, Toast.LENGTH_SHORT).show();
        }else {
            String urlss = urls+pic1;
            Toast.makeText(this, "foto :"+pic1, Toast.LENGTH_SHORT).show();
            view.loadUrl(urlss);
        }

    }

    private class profile_pic extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void edit_dalem(View view) {

        Intent h= new Intent(Profile.this,Edit_Profile.class);
        startActivity(h);

    }

}
