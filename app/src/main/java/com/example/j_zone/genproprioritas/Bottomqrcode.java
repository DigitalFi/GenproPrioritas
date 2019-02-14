package com.example.j_zone.genproprioritas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.j_zone.genproprioritas.helper.AppConfig;
import com.example.j_zone.genproprioritas.helper.AppController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.VolleyLog.TAG;

public class Bottomqrcode extends BottomSheetDialogFragment  {
    String text2qr;
    ImageView image,images;
    private WebView views;
    private SharedPreferences updt;
    private SharedPreferences user;
    private SharedPreferences user_edit;

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
        views.loadUrl("http://genprodev.lavenderprograms.com/img/mobiile_apps/");
        views.getSettings().setJavaScriptEnabled(true);
        views.setWebViewClient(new profile());
        getFoto();

        SharedPreferences user = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final String nama = user.getString("user_name", "");
        final String userid = user.getString("user_id", "");

        final String pic1 = user.getString("pic", "");

        SharedPreferences updt = this.getActivity().getSharedPreferences("data_update", Context.MODE_PRIVATE);
        final String no_anggota = updt.getString("no_anggota", "");
        final String pic = updt.getString("picture", "");
        final String link = updt.getString("url", "");


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

    private void getFoto() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_GET_EDIT_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONObject data = object.getJSONObject("data").getJSONObject("umum");
                    String urlfoto = data.getString("picture");

                    if (!urlfoto.isEmpty()){
                        String url = urlfoto;
                        views.loadUrl(url);
                    }else if (!urlfoto.isEmpty()){
                        String url = urlfoto;
                        views.loadUrl(url);
                    }else {
                        views.setVisibility(View.GONE);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                SharedPreferences user = getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String userid = user.getString("user_id","");
                params.put("user_id",userid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private class profile extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
