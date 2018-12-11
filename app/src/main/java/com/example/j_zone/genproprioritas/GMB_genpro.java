package com.example.j_zone.genproprioritas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class GMB_genpro extends AppCompatActivity {

    /** Called when the activity is first created. */
    private WebView view; //ini variabel supaya bisa diakses method


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gmb_genpro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        view = (WebView) this.findViewById(R.id.gmb);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        //ini manggil url web dari webview-nya
        view.loadUrl("https://m.gmbgenpro.com/login");
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
