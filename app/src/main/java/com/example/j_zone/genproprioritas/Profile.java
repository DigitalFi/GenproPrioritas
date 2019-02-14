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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.j_zone.genproprioritas.helper.AppConfig;
import com.example.j_zone.genproprioritas.helper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
        final String almt1 = user.getString("alamat", "");
        final String tlp1 = user.getString("tlp","");
        final String nomor1 = user.getString("no_anggota", "");


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

		// ngecek apakah inputannya kosong atau Tidak
        if (!almt.isEmpty()){
			Nama.setText("Nama : "+nama);
			Email.setText("Email : "+email);
			Nomor.setText("Nomor Anggota : "+nomor);
			Alamat.setText("Alamat : "+almt);
			Telepon.setText("Nomor Telephone : "+tlp);
		}else{
			Nama.setText("Nama : "+nama);
			Email.setText("Email : "+email);
			Nomor.setText("Nomor Anggota : "+nomor1);
			Alamat.setText("Alamat : "+almt1);
			Telepon.setText("Nomor Telephone : "+tlp1);

		}
		
        view = (WebView) findViewById(R.id.profil_pic) ;
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new profile_pic());
        getFoto();

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
                        view.loadUrl(url);
                    }else if (!urlfoto.isEmpty()){
                        String url = urlfoto;
                        view.loadUrl(url);
                    }else {
                        view.setVisibility(View.GONE);
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
                final String userid = user.getString("user_id","");
                params.put("user_id",userid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    private class profile_pic extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void edit_dalem(View view) {

        Intent h= new Intent(Profile.this,EditProfileActivity.class);
        startActivity(h);

    }

}
