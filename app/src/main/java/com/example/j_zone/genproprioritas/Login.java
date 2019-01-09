package com.example.j_zone.genproprioritas;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends Activity {


    private static final String TAG = Login.class.getSimpleName();

    private Button btnLogin;
    private EditText inputEmail;
    private EditText inputPassword;
    private ProgressDialog pDialog;
    private SharedPreferences user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        user = getSharedPreferences("data_user", MODE_PRIVATE);

        inputEmail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        btnLogin = (Button) findViewById(R.id.btn_login);

        // Progress dialog
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        //Toast
        Toast.makeText(this, "cek :"+user.getString("url","")+user.getString("pic", "")+user.getString("picture", ""), Toast.LENGTH_SHORT).show();

        // ketika login button di klik
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // ngecek apakah inputannya kosong atau Tidak
                if (!email.isEmpty() && !password.isEmpty()) {
                    // login user
                    checkLogin(email, password);
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),
                            "Jangan kosongkan email dan password!", Toast.LENGTH_LONG)
                            .show();
                }
            }

        });

        // ngecek apakah user udah login atau belum
        user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final int login = user.getInt("login", 0);
        final String nama = user.getString("user_name","");
        if (login == 1) {

            Intent intent = new Intent(Login.this,
                    Menu_main.class);

            Toast.makeText(getApplicationContext(),
                    "Selamat Datang : "+nama, Toast.LENGTH_LONG).show();

            startActivity(intent);

            finish();
        }


    }

    private void checkLogin(final String email, final String password) {

        // Tag biasanya digunakan ketika ingin membatalkan request volley
        String tag_string_req = "req_login";
        pDialog.setMessage("Logging in ...");
        showDialog();

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_LOGIN, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response);
                hideDialog();

                try
                {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // ngecek node error dari api
                    if (!error) {
                        //user berhasil login
                        String user_id = jObj.getString("user_id");
                        String user_name = jObj.getString("user_name");
                        String email = jObj.getString("email");
                        String n_anggota = jObj.getString("no_anggota");
                        String pic = jObj.getString("picture");
						String alamat = jObj.getString("alamat");
                        String tlp = jObj.getString("phone");
						
						Toast.makeText(getApplicationContext(),
                            "Debug :"+alamat+tlp,
                            Toast.LENGTH_SHORT).show();
						//bang btw atas ini kan toast ya , nah di toastnya itu alamat sama nope keambil cuman ga nampi//tronjal tronkwkwkw
                        // buat session user yang sudah login yang menyimpan id,nama,full name, roles id, roles name laman tempat setextnya mana activiy
                        SharedPreferences.Editor editor = user.edit();
                        editor.putString("user_id", user_id);
                        editor.putString("user_name", user_name);
                        editor.putString("email", email);
                        editor.putString("alamat", alamat);
                        editor.putString("no_anggota", n_anggota);
                        editor.putString("tlp", tlp);
                        editor.putString("pic", pic);
                        editor.putString("link", pic);
                        editor.putString("url", "http://genprodev.lavenderprograms.com/img/mobile_apps/");
                        editor.putInt("login", 1);
                        editor.commit();

                        //setelah mendapatkan value maka langsung lanjut pada activity selanjutnya
                        Intent intent = new Intent(Login.this,
                                Menu_main.class);
                        startActivity(intent);

                        finish();
                    } else {
                        //terjadi error dan tampilkan pesan error dari API
                        //String errorMsg = jObj.getString("message");
                        Toast.makeText(getApplicationContext(),
                                "Username atau password yang anda masukan salah", Toast.LENGTH_LONG).show();
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
                params.put("email", email);
                params.put("password", password);

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

    public void Register(View view) {
        Intent intent = new Intent(Login.this, Register.class);
        startActivity(intent);
    }
}