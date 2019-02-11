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

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
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
        WebSettings webSettings = views.getSettings();
        webSettings.setJavaScriptEnabled(true);
        views.setWebViewClient(new profile());

        SharedPreferences user = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final String nama = user.getString("user_name", "");
        final String userid = user.getString("user_id", "");

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

        private void getdata(final String userid,final String foto) {

        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_data";

        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_GET_EDIT_PROFILE, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "LOADING: " + response.toString());

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // ngecek node error dari api
                    if (!error) {
                        JSONObject obj = jObj.getJSONObject("data");
                        JSONObject umum = obj.getJSONObject("umum");
                        String picture = umum.getString("picture");

                        Toast.makeText(getActivity(), "fotonya"+picture, Toast.LENGTH_SHORT).show();

                        Glide.with(getActivity()).load(picture).into(images);
                        //coba run ya

                    } else {
                        //terjadi error dan tampilkan pesan error dari API
                        //String errorMsg = jObj.getString("message");
                        Toast.makeText(getActivity(),
                                "Some Eror , Please Contact Developer", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                //cek error timeout, noconnection dan network error
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getActivity(),
                            "Please Check Your Connection" + error.getMessage(),
                            Toast.LENGTH_SHORT).show();}
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);
                params.put("pic", foto);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private class profile extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
