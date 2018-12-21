package com.example.j_zone.genproprioritas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.example.j_zone.genproprioritas.helper.AppConfig;
import com.example.j_zone.genproprioritas.helper.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Business extends AppCompatActivity {


    private SharedPreferences user;
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Bisnis> bisnisList;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mList = findViewById(R.id.main_list);
        bisnisList = new ArrayList<>();
        adapter = new BisnisAdapter(getApplication(),bisnisList);

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(mList.getContext(),linearLayoutManager.getOrientation());

        mList.setAdapter(adapter);
        mList.setHasFixedSize(true);
        mList.setLayoutManager(linearLayoutManager);
        mList.addItemDecoration(dividerItemDecoration);
        getBisnis();

    }

    private void getBisnis() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_LIST_USAHA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray jsonArray = object.getJSONArray("data");
                    for (int i = 0; i<jsonArray.length(); i++){
                        JSONObject object2 = jsonArray.getJSONObject(i);
                        Bisnis bisnis2 = new Bisnis();
                        bisnis2.setNm_usaha(object2.getString("nm_usaha"));
                        bisnis2.setMerk(object2.getString("merk"));
                        bisnis2.setTglTerdaftar(object2.getString("tgl_terdaftar"));
                        bisnis2.setNmbisnislain(object2.getString("nm_bisnis_lain"));
                        bisnis2.setJumlahkaryawan(object2.getString("jml_karyawan"));
                        bisnis2.setJumlahcabang(object2.getString("jml_cabang"));
                        bisnis2.setOmsettahunan(object2.getString("omset_tahunan"));
                        bisnis2.setTelepon(object2.getString("no_tlp"));
                        bisnis2.setFacebooks(object2.getString("facebook"));
                        bisnis2.setInstagrams(object2.getString("instagram"));

                        bisnisList.add(bisnis2);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volley", "Error: " + error.getMessage());
                error.printStackTrace();
                progressDialog.dismiss();
            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String userid = user.getString("user_id","");
                params.put("user_id",userid);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);
    }

    public void tambah(View view) {
        Intent h= new Intent(Business.this,Add_Usaha.class);
        startActivity(h);
    }
}
