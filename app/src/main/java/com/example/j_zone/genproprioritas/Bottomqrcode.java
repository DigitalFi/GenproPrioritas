package com.example.j_zone.genproprioritas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Bottomqrcode extends BottomSheetDialogFragment  {
    String text2qr;
    ImageView image;
    private WebView views;
    private SharedPreferences updt;
    private SharedPreferences user;


    public Bottomqrcode() {
        // Required empty public constructor

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        image = (ImageView) view.findViewById(R.id.qr);

        views = view.findViewById(R.id.Profile);
        views.loadUrl("http://genprodev.lavenderprograms.com/img/mobile_apps/");
        WebSettings webSettings = views.getSettings();
        webSettings.setJavaScriptEnabled(true);
        views.setWebViewClient(new profile());

        SharedPreferences user = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final String nama = user.getString("user_name", "");
        final String pic1 = user.getString("pic", "");

        SharedPreferences updt = this.getActivity().getSharedPreferences("data_update", Context.MODE_PRIVATE);
        final String no_anggota = updt.getString("no_anggota", "");
        final String pic = updt.getString("picture", "");
        final String link = updt.getString("url", "");

        if (!pic.isEmpty()){
            String url = "http://genprodev.lavenderprograms.com/img/mobile_apps/"+pic;
            views.loadUrl(url);
//            Toast.makeText(getApplicationContext(), "url-updated="+url, Toast.LENGTH_SHORT).show();
        }else if (!pic1.isEmpty()){
            String url = "http://genprodev.lavenderprograms.com/img/mobile_apps/"+pic1;
            views.loadUrl(url);
//            Toast.makeText(getApplicationContext(), "url-no-updated="+url, Toast.LENGTH_SHORT).show();
        }else {
            view.setVisibility(View.GONE);
        }



        text2qr = "nama :"+nama+" Nomor :"+no_anggota;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text2qr, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);

        }catch (WriterException e){
            e.printStackTrace();
        }

        return view;
    }
    private class profile extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
