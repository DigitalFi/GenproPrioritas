package com.example.j_zone.genproprioritas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Add_Usaha extends AppCompatActivity {

    private static final String TAG = Add_Usaha.class.getSimpleName();

    private Button btnSubmit;
    private EditText inputBisnisLain;
//    private EditText inputNamaUsaha;
    private EditText inputMerek;
    private EditText inputTgl_terdaftar;
    private EditText inputJml_karyawan;
    private EditText inputJml_cabang;
    private EditText inputOmsetTahunan;
    private EditText inputNoTlp;
    private EditText inputFacebook;
    private EditText inputInstagram;
    private EditText inputNamaUsaha;
    private EditText inputNamaLain;
    private EditText inputTentang;
    private ProgressDialog pDialog;
    private SharedPreferences user;
    private Spinner sp,sp1;


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

        sp = findViewById(R.id.omst_tahunan);
        sp1 = findViewById(R.id.jenis_usaha);
//        inputNamaUsaha = (EditText) findViewById(R.id.jenis_usaha_lain);
        inputNamaUsaha = (EditText)findViewById(R.id.nama_usaha) ;
        inputTentang = findViewById(R.id.Tentang);
        inputMerek = (EditText) findViewById(R.id.Merek);
        inputNamaLain = (EditText) findViewById(R.id.nama_lain);
        inputJml_karyawan = (EditText) findViewById(R.id.jumlah_karyawan);
        inputJml_cabang = (EditText) findViewById(R.id.jml_cabang);
        inputNoTlp = (EditText) findViewById(R.id.tlp);
        inputFacebook = (EditText) findViewById(R.id.acc_facebook);
        inputInstagram = (EditText) findViewById(R.id.acc_instagram);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
//
//        inputOmsetTahunan.addTextChangedListener(omset());

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.omset, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);

        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(this, R.array.bisnis, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapters);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String names = sp1.getSelectedItem().toString();
//                int idSpinner = getResources().getIdentifier(names, "array", Add_Usaha.this.getPackageName());
//
//                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Add_Usaha.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(idSpinner));
//                //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                sp1.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = sp.getSelectedItem().toString();
//                int idSpinner = getResources().getIdentifier(name, "array", Add_Usaha.this.getPackageName());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // ketika login button di klik
        btnSubmit.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String namabisnis = sp1.getSelectedItem().toString();
//                String namausaha = inputNamaUsaha.getText().toString().trim();
                String merek = inputMerek.getText().toString().trim();
                String namausaha = inputNamaUsaha.getText().toString().trim();
                String namausahalain = inputNamaLain.getText().toString().trim();
                String tentangperusahaan = inputTentang.getText().toString().trim();
                String jml_karyawan = inputJml_karyawan.getText().toString().trim();
                String jml_cabang = inputJml_cabang.getText().toString().trim();
                String omset_tahunan = sp.getSelectedItem().toString();
                String no_tlp = inputNoTlp.getText().toString().trim();
                String facebook = inputFacebook.getText().toString().trim();
                String instagram = inputInstagram.getText().toString().trim();

                user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String id = user.getString("user_id", "");

               if (id.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Wajib diisi semua !", Toast.LENGTH_SHORT).show();
               }else if (namabisnis.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Nama Bisnis Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
               }else if (namausaha.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Nama Usaha Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
               }else if (merek.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Merk Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
               }else if (jml_karyawan.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Jumlah Karyawan Tidak Boleh Kosong !", Toast.LENGTH_SHORT).show();
               }else  if (jml_cabang.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Jumlah Cabang Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
               }else if (omset_tahunan.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Omset Tahunan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
               }else if (no_tlp.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Nomor Telepon Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
               }else if (facebook.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Facebook Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
             }else if (instagram.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Instagram Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
               }else if (namausahalain.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Nama Usaha Lain Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
               }else if (tentangperusahaan.isEmpty()){
                   Toast.makeText(Add_Usaha.this, "Tentang Perusahaan Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
               }else {
                   checkSubmit(id,namausaha,namausahalain,tentangperusahaan, namabisnis, merek, jml_karyawan, jml_cabang, omset_tahunan, no_tlp, facebook, instagram);
               }

            }

        });

    }

    //currency format
//        private TextWatcher omset() {
//            return new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//                    inputOmsetTahunan.removeTextChangedListener(this);
//
//                    try {
//                        String originalString = s.toString();
//
//                        Long longval;
//                        if (originalString.contains(",")) {
//                            originalString = originalString.replaceAll(",", "");
//                        }
//                        longval = Long.parseLong(originalString);
//
//                        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
//                        formatter.applyPattern("#,###,###,###");
//                        String formattedString = formatter.format(longval);
//
//                        //setting text after format to EditText
//                        inputOmsetTahunan.setText(formattedString);
//                        inputOmsetTahunan.setSelection(inputOmsetTahunan.getText().length());
//
//                    } catch (NumberFormatException nfe) {
//                        nfe.printStackTrace();
//                    }
//
//                    inputOmsetTahunan.addTextChangedListener(this);
//                }
//            };
//        }//

    private void checkSubmit(final String id,final String namausaha,final String tentang,final String namausahalain, final String namabisnis, final String merek, final String jml_karyawan, final String jml_cabang, final String omset_tahunan, final String no_tlp, final String facebook, final String instagram) {

        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Sedang Menambahkan Bisnis");
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
                    // ngecek node error dari api
                    if (!error) {
                        /*user berhasil login
                        String user_id = jObj.getString("user_id");
                        String user_name = jObj.getString("user_name");
                        String email = jObj.getString("email");
                        JSO
                        buat session user yang sudah login yang menyimpan id,nama,full name, roles id, roles name
                        SharedPreferences.Editor editor = user.edit();
                        editor.putString("user_id", user_id);
                        editor.putString("user_name", user_name);
                        editor.putString("email", email);
                        editor.putInt("login", 1);
                        editor.commit();*/
//                        SharedPreferences.Editor editor = user.edit();
//                        editor.putString("nm_bisnis_lain",namabisnis);
//                        editor.putString("nm_usaha",namausaha);
//                        editor.putString("merk",merek);
//                        editor.putString("jml_karyawan",jml_karyawan);
//                        editor.putString("jml_cabang",jml_cabang);
//                        editor.putString("omset_tahunan",omset_tahunan);
//                        editor.putString("no_tlp",no_tlp);
//                        editor.putString("facebook",facebook);
//                        editor.putString("instagram",instagram);
//                        editor.commit();

                        //setelah mendapatkan value maka langsung lanjut pada activity selanjutnya
                        Intent intent = new Intent(Add_Usaha.this,
                                Menu_main.class);
                        startActivity(intent);

                        finish();

                        Toast.makeText(getApplicationContext(),"Success Updated !", Toast.LENGTH_SHORT).show();
                    } else {
                        //terjadi error dan tampilkan pesan error dari API
                        //String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                "Error Submited , Please Try Again", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
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
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("nm_bisnis_lain", namabisnis);
                params.put("nm_usaha", namausaha);
                params.put("nm_usaha_lain", namausahalain);
                params.put("merk", merek);
                params.put("jml_karyawan", jml_karyawan);
                params.put("jml_cabang", jml_cabang);
                params.put("omset_tahunan", omset_tahunan);
                params.put("no_tlp", no_tlp);
                params.put("tentang_usaha", tentang);
                params.put("facebook", facebook);
                params.put("instagram", instagram);

                return params;
            }
        };
        // menggunakan fungsi volley adrequest yang kita taro di appcontroller
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
//    public String currencyFormatter(String num) {
//        double m = Double.parseDouble(num);
//        DecimalFormat formatter = new DecimalFormat("###,###,###");
//        return formatter.format(m);
//    }
}
