package com.example.j_zone.genproprioritas;

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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Menu_main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SharedPreferences user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_main);
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

        View headerView = navigationView.getHeaderView(0);

        TextView n = (TextView) headerView.findViewById(R.id.nama_user);
        TextView r = (TextView) headerView.findViewById(R.id.nomor_anggota);

        TextView depan = (TextView) findViewById(R.id.NamaDepan);
        TextView kolom_nama = (TextView) findViewById(R.id.profil_nama);
        TextView kolom_email = (TextView) findViewById(R.id.profil_email);

        n.setText(nama);
        r.setText(email);

        depan.setText(nama);
        kolom_email.setText("Email : "+email);
        kolom_nama.setText("Nama : "+nama);


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
            Intent intent = new Intent(Menu_main.this, Add_Usaha.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage_profile) {
            Intent intent = new Intent(Menu_main.this, Edit_Profile.class);
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
}
