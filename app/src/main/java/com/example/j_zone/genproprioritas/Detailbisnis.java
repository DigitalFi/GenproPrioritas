package com.example.j_zone.genproprioritas;

import android.content.Intent;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.j_zone.genproprioritas.helper.AppConfig;
import com.example.j_zone.genproprioritas.helper.AppController;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;


public class Detailbisnis extends AppCompatActivity {
    TextView nmbisnislain,nmusaha,merek,jumlah_karyawan,jml_cabang,omset_tahunan,no_tlp,facebook,instagram;
    Button btnedit,btn_apus;
    private static final String TAG = Detailbisnis.class.getSimpleName();
    private  static final String TAG_SUCCESS = "error";
    private  static final String TAG_MESSAGE = "msg";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailbisnis);



        nmbisnislain = findViewById(R.id.nmbisnislain);
        nmusaha = findViewById(R.id.nmusaha);
        btn_apus = findViewById(R.id.btn_hapus);
        merek = findViewById(R.id.merek);
        jumlah_karyawan = findViewById(R.id.jumlah_karyawan);
        jml_cabang = findViewById(R.id.jml_cabang);
        omset_tahunan = findViewById(R.id.omset_tahunan);
        no_tlp = findViewById(R.id.no_tlp);
        facebook = findViewById(R.id.facebook);
        instagram = findViewById(R.id.instagram);
        btnedit = findViewById(R.id.btn_edit);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent a = getIntent();
        String namas = a.getStringExtra("nm_bisnis_lain");
        String usaha = a.getStringExtra("nm_usaha");
        String merekss = a.getStringExtra("merk");
        String karyawans = a.getStringExtra("jml_karyawan");
        String cabangs = a.getStringExtra("jml_cabang");
        String omsed = a.getStringExtra("omset_tahunan");
        String nope = a.getStringExtra("no_tlp");
        String faceboo = a.getStringExtra("facebook");
        String insta = a.getStringExtra("instagram");



        nmbisnislain.setText("Bisnis Lain :"+namas);
        nmusaha.setText("Nama Usaha : "+usaha);
        merek.setText("Merk : "+merekss);
        jumlah_karyawan.setText("Jumlah Karyawan :"+karyawans);
        jml_cabang.setText("Jumlah Cabang :"+cabangs);
        omset_tahunan.setText("Omset Tahunan :"+currencyFormatter(omsed));
        no_tlp.setText("Nomor Telepon :"+nope);
        facebook.setText("Facebook :"+faceboo);
        instagram.setText("Instagram :"+insta);




    }
    public void hapus(final String userid) {
            StringRequest delete = new StringRequest(Request.Method.POST, AppConfig.URL_LIST_USAHA, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG,"Response "+ response.toString());
                    try{
                        JSONObject jobj = new JSONObject(response);
                        boolean error = jobj.getBoolean("error");
                        if (!error){
                            Log.d("delete",jobj.toString());
                            Toast.makeText(Detailbisnis.this,jobj.getString(TAG_MESSAGE), Toast.LENGTH_SHORT).show();
                            Intent a = new Intent(Detailbisnis.this,Menu_main.class);
                            startActivity(a);
                        }else {
                            Toast.makeText(Detailbisnis.this, "gagal menghapus", Toast.LENGTH_SHORT).show();
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText(Detailbisnis.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error.response",error.toString());
                    Toast.makeText(Detailbisnis.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams(){
                    Map<String,String>params = new HashMap<String, String>();
                    params.put("idbisnis_info",userid);
                    return params;
                }
            };
            AppController.getInstance().addToRequestQueue(delete);
        }

    public void hapus_bisnis(View view){
        hapus("iddbisnis_info");

    }

    public void edit_bisnis(View view) {

        Intent h= new Intent(Detailbisnis.this,EditBisnis.class);
        startActivity(h);

    }
    public String currencyFormatter(String num) {
        double m = Double.parseDouble(num);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(m);
    }
}
