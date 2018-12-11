package com.example.j_zone.genproprioritas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Search extends AppCompatActivity {

    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        view = (WebView) this.findViewById(R.id.search);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new Search.MyBrowser1());
        //ini manggil url web dari webview-nya
        view.loadUrl("http://www.genprodev.lavenderprograms.com/bisnis_info");
    }

    private class MyBrowser1 extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
