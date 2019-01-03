package com.example.j_zone.genproprioritas;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class Menu_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences user;
    private SharedPreferences updt;
    private RecyclerView mList;

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<Bisnis> bisnisList;
    private RecyclerView.Adapter adapter;
    private LinearLayout linearprofiles;
    private WebView view; //ini variabel supaya bisa diakses method
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
        linearprofiles = (LinearLayout)findViewById(R.id.linearprofile);
        linearprofiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(Menu_main.this,Profile.class);
                startActivity(a);
            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final String nama = user.getString("user_name", "");
        final String email = user.getString("email", "");
        final String pic1 = user.getString("pic", "");

        updt = getSharedPreferences("data_update", Context.MODE_PRIVATE);
        final String nomor = updt.getString("no_anggota", "");
        final String pic = updt.getString("picture", "");
        final String link = updt.getString("url", "");

        View headerView = navigationView.getHeaderView(0);

        view = (WebView) headerView.findViewById(R.id.Profile);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new profile());
        //ini manggil url web dari webview-nya

        // ngecek apakah inputannya kosong atau Tidak
        if (!pic.isEmpty()) {
            // login user

            String url=link+pic;

            view.loadUrl(url);

        } else {

            String url=link+pic1;

            view.loadUrl(url);

        }



        //Toast.makeText(getApplicationContext(), "url="+link+pic, Toast.LENGTH_SHORT).show();



        TextView n = (TextView) headerView.findViewById(R.id.nama_user);
        TextView r = (TextView) headerView.findViewById(R.id.nomor_anggota);

        TextView depan = (TextView) findViewById(R.id.NamaDepan);
        TextView kolom_nama = (TextView) findViewById(R.id.profil_nama);
        TextView kolom_email = (TextView) findViewById(R.id.profil_email);

        n.setText(nama);
        r.setText(nomor);

        depan.setText(nama);
        kolom_email.setText("Email : "+email);
        kolom_nama.setText("Nama : "+nama);

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

    public void showBottomSheetDialogFragment() {
        Bottomqrcode bottomSheetFragment = new Bottomqrcode();
        bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
    }

    public void qrcode(View view) {
        showBottomSheetDialogFragment();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            logoutUser();

            //Toast.makeText(getApplicationContext(), "Logout Succed!", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_search) {
            // Handle the camera action
            Intent intent = new Intent(Menu_main.this, Search.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage_usaha) {
            Intent intent = new Intent(Menu_main.this, Business.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage_profile) {
            Intent intent = new Intent(Menu_main.this, Profile.class);
            startActivity(intent);
        } else if (id == R.id.nav_gmb_genpro) {
            Intent intent = new Intent(Menu_main.this, GMB_genpro.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void edit(View view) {

        Intent h= new Intent(Menu_main.this,Edit_Profile.class);
        startActivity(h);

    }

    public void logoutUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setMessage("Anda Yakin Ingin Keluar?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //jika user klik yes harusnya ke halaman login
                SharedPreferences user = getSharedPreferences("data_user", MODE_PRIVATE);
                SharedPreferences.Editor editor = user.edit();
                editor.putInt("login", 0);
                editor.clear();
                editor.commit();
				
				SharedPreferences updt = getSharedPreferences("data_update", MODE_PRIVATE);
				SharedPreferences.Editor editor = user.edit();
				editor.clear();
				editor.commit();
				
				SharedPreferences updt = getSharedPreferences("updt", MODE_PRIVATE);
				Intent intent = new Intent(Menu_main.this, Login.class);
                startActivity(intent);
                finish();

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //jika user klik no ya gak terjadi apa-apa
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

        // pergi ke login activity
        // Intent intent = new Intent(MainKecamatan.this, Login.class);
        //startActivity(intent);
        //finish();
    }

    private class profile extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

}
