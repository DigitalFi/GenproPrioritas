package com.example.j_zone.genproprioritas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.j_zone.genproprioritas.helper.AppConfig;
import com.example.j_zone.genproprioritas.helper.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Add_Usaha extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();

    private Button btnSubmit;
    private EditText inputBisnisLain;
    private EditText inputNamaUsaha;
    private EditText inputMerek;
    private EditText NamaUsahainput;
    private EditText inputJml_karyawan;
    private EditText inputJml_cabang;
    private EditText inputOmsetTahunan;
    private EditText inputNoTlp;
    private EditText inputFacebook;
    private EditText inputInstagram;
    private ProgressDialog pDialog;
    private SharedPreferences user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__usaha);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        inputBisnisLain = (EditText) findViewById(R.id.jenis_usaha_lain);
        inputNamaUsaha = (EditText) findViewById(R.id.jenis_usaha);
        inputMerek = (EditText) findViewById(R.id.Merek);
        inputJml_karyawan = (EditText) findViewById(R.id.jumlah_karyawan);
        inputJml_cabang = (EditText) findViewById(R.id.jml_cabang);
        inputOmsetTahunan = (EditText) findViewById(R.id.omst_tahunan);
        inputNoTlp = (EditText) findViewById(R.id.tlp);
        inputFacebook = (EditText) findViewById(R.id.acc_facebook);
        inputInstagram = (EditText) findViewById(R.id.acc_instagram);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        NamaUsahainput = findViewById(R.id.nama_usaha);



        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String namabisnis = inputBisnisLain.getText().toString().trim();
                String namausaha = inputNamaUsaha.getText().toString().trim();
                String usahainput = NamaUsahainput.getText().toString().trim();
                String merek = inputMerek.getText().toString().trim();
                String jml_karyawan = inputJml_karyawan.getText().toString().trim();
                String jml_cabang = inputJml_cabang.getText().toString().trim();
                String omset_tahunan = inputOmsetTahunan.getText().toString().trim();
                String no_tlp = inputNoTlp.getText().toString().trim();
                String facebook = inputFacebook.getText().toString().trim();
                String instagram = inputInstagram.getText().toString().trim();

                user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String id = user.getString("user_id", "");

                // ngecek apakah inputannya kosong atau Tidak
                if (!namabisnis.isEmpty() && !namausaha.isEmpty() && !merek.isEmpty() && !jml_karyawan.isEmpty() && !jml_cabang.isEmpty() && !omset_tahunan.isEmpty() && !no_tlp.isEmpty() && !facebook.isEmpty() && !instagram.isEmpty()&& !usahainput.isEmpty()&& !id.isEmpty()) {
                    // login user
                    Toast.makeText(getApplicationContext(),
                            namabisnis+namausaha+merek+jml_karyawan+jml_cabang+omset_tahunan+no_tlp+facebook+instagram+usahainput+id, Toast.LENGTH_LONG).show();
                    checkSubmit(id,namabisnis, namausaha, merek, jml_karyawan, jml_cabang, omset_tahunan, no_tlp, facebook, instagram,usahainput);
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),
                            "Jangan kosongkan email dan password!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

    }

    private void checkSubmit(final String namabisnis,final String usahainput, final String namausaha, final String merek,final String id, final String jml_karyawan, final String jml_cabang, final String omset_tahunan, final String no_tlp, final String facebook, final String instagram) {

        String tag_string_req = "req_login";
        pDialog.setMessage("Sedang Input");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ADD_USAHA, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();
                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String msg = jObj.getString("msg");


                    if (!error) {

//                        String namabisnis = jObj.getString("nm_bisnis_lain");
//                        String namausaha = jObj.getString("nm_usaha");
//                        String merek = jObj.getString("merk");
//                        String jml_karyawan = jObj.getString("jml_karyawan");
//                        String jml_cabang = jObj.getString("jml_cabang");
//                        String omset_tahunan = jObj.getString("omset_tahunan");
//                        String no_tlp = jObj.getString("no_tlp");
//                        String facebook = jObj.getString("facebook");
//                        String instagram = jObj.getString("instagram");



                        //setelah mendapatkan value maka langsung lanjut pada activity selanjutnya
                        Intent intent = new Intent(Add_Usaha.this,
                                Menu_main.class);
                        startActivity(intent);

                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(),
                                "msg "+msg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                //cek error timeout, noconnection dan network error
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection" + error.getMessage(),
                            Toast.LENGTH_SHORT).show();}
                hideDialog();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String,String>();
                params.put("user_id",id);
                params.put("nm_bisnis_lain", namabisnis);
                params.put("nm_usaha", namausaha);
                params.put("merk", merek);
                params.put("jml_karyawan", jml_karyawan);
                params.put("jml_cabang", jml_cabang);
                params.put("omset_tahunan", omset_tahunan);
                params.put("no_tlp", no_tlp);
                params.put("facebook", facebook);
                params.put("instagram", instagram);


                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
