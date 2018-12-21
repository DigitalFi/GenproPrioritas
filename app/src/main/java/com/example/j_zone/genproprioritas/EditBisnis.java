package com.example.j_zone.genproprioritas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.j_zone.genproprioritas.helper.AppConfig;
import com.example.j_zone.genproprioritas.helper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditBisnis extends AppCompatActivity {
    private Button btnSubmit;
    private EditText inputBisnisLain;
    private EditText inputMerek;
    private EditText inputTgl_terdaftar;
    private EditText inputJml_karyawan;
    private EditText inputJml_cabang;
    private EditText inputOmsetTahunan;
    private EditText inputNoTlp;
    private EditText inputFacebook;
    private EditText inputInstagram;
    private SharedPreferences updt;
    private SharedPreferences user;
    private EditText inputNamaUsaha;
    private ProgressDialog pDialog;
    private SharedPreferences  user_edit;
    private static final String TAG = EditBisnis.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bisnis);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        updt = getSharedPreferences("data_update", MODE_PRIVATE);
        user = getSharedPreferences("data_user", MODE_PRIVATE);

        user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final String userid = user.getString("user_id", "");

        inputBisnisLain = (EditText) findViewById(R.id.jenis_usaha);
        inputNamaUsaha = (EditText) findViewById(R.id.nama_usaha);
        inputMerek = (EditText) findViewById(R.id.Merek);
        inputJml_karyawan = (EditText) findViewById(R.id.jumlah_karyawan);
        inputJml_cabang = (EditText) findViewById(R.id.jml_cabang);
        inputOmsetTahunan = (EditText) findViewById(R.id.omst_tahunan);
        inputNoTlp = (EditText) findViewById(R.id.tlp);
        inputFacebook = (EditText) findViewById(R.id.acc_facebook);
        inputInstagram = (EditText) findViewById(R.id.acc_instagram);
        btnSubmit = (Button) findViewById(R.id.btn_edit);


        user_edit = getSharedPreferences("data_edit", Context.MODE_PRIVATE);
//        final String bisnislain = user_edit.getString("namadepan", "");
        final String namausaha= user_edit.getString("nm_usaha", "");
        final String merk = user_edit.getString("merk", "");
        final String karyawan = user_edit.getString("jml_karyawan", "");
        final String cabang = user_edit.getString("jml_cabang", "");
        final String omset = user_edit.getString("omset_tahunan", "");
        final String telpom = user_edit.getString("no_tlp", "");
        final String fbs = user_edit.getString("facebook", "");
        final String igs = user_edit.getString("instagram", "");

        inputNamaUsaha.setText(namausaha);
        inputMerek.setText(merk);
        inputJml_karyawan.setText(karyawan);
        inputJml_cabang.setText(cabang);
        inputOmsetTahunan.setText(omset);
        inputNoTlp.setText(telpom);
        inputFacebook.setText(fbs);
        inputInstagram.setText(igs);

        if (!userid.isEmpty()) {
            // login user
            getdata(userid);

        } else {
            // jika inputan kosong tampilkan pesan
            Toast.makeText(getApplicationContext(),
                    userid+"Tidak ditemukan", Toast.LENGTH_LONG)
                    .show();
        }



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String id = user.getString("user_id", "");

                String bisnislain = inputBisnisLain.getText().toString().trim();
                String namausaha = inputNamaUsaha.getText().toString().trim();
                String merk = inputMerek.getText().toString().trim();
                String karyawan = inputJml_karyawan.getText().toString().trim();
                String cabang = inputJml_cabang.getText().toString().trim();
                String omset = inputOmsetTahunan.getText().toString().trim();
                String telpon = inputNoTlp.getText().toString().trim();
                String fbs = inputFacebook.getText().toString().trim();
                String igs = inputInstagram.getText().toString().trim();

                if (bisnislain.isEmpty()&&namausaha.isEmpty() && merk.isEmpty() && karyawan.isEmpty() && cabang.isEmpty() && omset.isEmpty() && telpon.isEmpty() && fbs.isEmpty() && igs.isEmpty()) {
                    Toast.makeText(getApplicationContext(),id+" ,"+" ,"+namausaha+", "+merk+" ,"+karyawan+" ,"+cabang+" ,"+omset+" ,"+telpon+","+fbs+" ,"+igs,Toast.LENGTH_LONG).show();
                    update(id,bisnislain, namausaha, merk, karyawan, cabang, omset, telpon, fbs, igs);
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),id+" ,"+" ,"+namausaha+", "+merk+" ,"+karyawan+" ,"+cabang+" ,"+omset+" ,"+telpon+","+fbs+" ,"+igs,Toast.LENGTH_LONG).show();
                }
            }
        });


    }


    private void getdata(final String userid) {
        String tag_string_req = "req_data";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_GET_EDIT_BISNIS, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "LOADING: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    if (error) {
                        JSONObject obj = jObj.getJSONObject("data");
                        String id = obj.getString("user_id");
                        String nmusaha = obj.getString("nm_usaha");
                        String merk = obj.getString("merk");
                        String jml_karyawan = obj.getString("jml_karyawan");
                        String jml_cabang = obj.getString("jml_cabang");
                        String omset_tahunan = obj.getString("omset_tahunan");
                        String no_tlp = obj.getString("no_tlp");
                        String facebook = obj.getString("facebook");
                        String instagram = obj.getString("instagram");

                        SharedPreferences.Editor editor = user_edit.edit();
                        editor.putString("userid", id);
                        editor.putString("nm_usaha", nmusaha);
                        editor.putString("merk", merk);
                        editor.putString("jml_karyawan", jml_karyawan);
                        editor.putString("jml_cabang", jml_cabang);
                        editor.putString("omset_tahunan", omset_tahunan);
                        editor.putString("no_tlp", no_tlp);
                        editor.putString("facebook", facebook);
                        editor.putString("instagram", instagram);
                        editor.commit();


                    } else {
                        Toast.makeText(getApplicationContext(), "Gagal", Toast.LENGTH_SHORT).show();
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
                if (error instanceof TimeoutError || error instanceof NoConnectionError || error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection" + error.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", userid);

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


    private void update(final String id,final String bisnislain, final String namausaha, final String merk, final String karyawan, final String cabang, final String omset, final String telpon, final String fbs, final String igs) {
        final VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConfig.URL_GET_EDIT_BISNIS,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        try {
                            JSONObject jObj = new JSONObject(new String(response.data));
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                                JSONObject obj = jObj.getJSONObject("data");
                                String id = obj.getString("user_id");
                                Log.d("id","anda"+id);
                                String bisnislain = obj.getString("nm_bisnis_lain");
                                String nmusaha = obj.getString("nm_usaha");
                                String merk = obj.getString("merk");
                                String jml_karyawan = obj.getString("jml_karyawan");
                                String jml_cabang = obj.getString("jml_cabang");
                                String omset_tahunan = obj.getString("omset_tahunan");
                                String no_tlp = obj.getString("no_tlp");
                                String facebook = obj.getString("facebook");
                                String instagram = obj.getString("instagram");

                                // buat session user yang sudah login yang menyimpan id,nama,full name, roles id, roles name
                                SharedPreferences.Editor editor = updt.edit();
                                editor.putString("userid", id);
                                editor.putString("nm_usaha", nmusaha);
                                editor.putString("merk", merk);
                                editor.putString("jml_karyawan", jml_karyawan);
                                editor.putString("jml_cabang", jml_cabang);
                                editor.putString("omset_tahunan", omset_tahunan);
                                editor.putString("no_tlp", no_tlp);
                                editor.putString("facebook", facebook);
                                editor.putString("instagram", instagram);
                                editor.commit();

                                Intent intent = new Intent(EditBisnis.this,
                                        Menu_main.class);
                                startActivity(intent);
                                finish();

                                //Toast.makeText(getApplicationContext(),"Success Updated !"+depan+","+belakang+","+nomor_anggota+","+propinsi+","+kabupaten+","+alamat+","+phone+","+picture+","+update, Toast.LENGTH_SHORT).show();

                                Toast.makeText(getApplicationContext(), "Success Updated !", Toast.LENGTH_SHORT).show();


                            } else {
                                //terjadi error dan tampilkan pesan error dari API
                                //String errorMsg = jObj.getString("message");
                                Toast.makeText(getApplicationContext(),
                                        "Username atau password yang anda masukan salah", Toast.LENGTH_LONG).show();
                            }

                            //JSONObject obj = new JSONObject(new String(response.data));
                            //Toast.makeText(getApplicationContext(), obj.getString("msg"), Toast.LENGTH_SHORT).show();
                            //String NomorAnggota = obj.getString("no_anggota");
                            //save id kelembagaan

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("nm_bisnis_lain",bisnislain);
                params.put("nm_usaha",namausaha);
                params.put("merk", merk);
                params.put("jml_karyawan", karyawan);
                params.put("jml_cabang", cabang);
                params.put("omset_tahunan", omset);
                params.put("no_tlp", telpon);
                params.put("facebook", fbs);
                params.put("instagram", igs);
                return params;
            }
        };
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
