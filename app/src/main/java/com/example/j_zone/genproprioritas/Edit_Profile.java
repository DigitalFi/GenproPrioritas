package com.example.j_zone.genproprioritas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.example.j_zone.genproprioritas.helper.AppConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Edit_Profile extends AppCompatActivity {

    private static final String TAG = Edit_Profile.class.getSimpleName();

    Spinner Sp1, Sp2;
    private Button btnImage;
    private Button btnSubmit;
    private ImageView imageView;
    private EditText nama, namaPanjang, phone, alamat ;
    private SharedPreferences user;
    private SharedPreferences updt;

    static final int REQUEST_TAKE_PHOTO = 1;

    String URL="http://genprodev.lavenderprograms.com/apigw/reff/get_propinsi";
    //String URL1="http://genprodev.lavenderprograms.com/apigw/reff/get_propinsi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        user = getSharedPreferences("data_user", MODE_PRIVATE);
        updt = getSharedPreferences("data_update", MODE_PRIVATE);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);

        } else {

        }

        btnImage = (Button)findViewById(R.id.btn_picture);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        imageView = (ImageView)findViewById(R.id.imageView);
        Sp1 = (Spinner)findViewById(R.id.SpFirst);
        nama = (EditText)findViewById(R.id.nama);
        namaPanjang = (EditText)findViewById(R.id.nama_pendek);
        phone = (EditText)findViewById(R.id.tlp);
        alamat = (EditText)findViewById(R.id.alamat);
        Sp2 = (Spinner)findViewById(R.id.SpSecond);

        //String test = "ini isi";

        //nama.setHint(test);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();

            }
        });

        Sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String name = Sp1.getSelectedItem().toString();
                int idSpinner = getResources().getIdentifier(name, "array", Edit_Profile.this.getPackageName());

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Edit_Profile.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(idSpinner));
                Sp2.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        /*btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();

            }
        });*/

        /*loadSpinnerData(URL);
        Sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                provinsi kode_provinsi = (provinsi) adapterView.getSelectedItem();
                String kode_prov = kode_provinsi.getKode();

                final SharedPreferences provinsi = getApplicationContext().getSharedPreferences("provinsi", 0);// 0 - for private mode
                final SharedPreferences.Editor editor = provinsi.edit();
                editor.putString("kode", kode_prov);
                editor.commit();

            }*

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });*/



    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap bitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(bitmap);

            //mengambil data dari switch
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    user = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                    final String id = user.getString("user_id", "");

                    String namadepan = nama.getText().toString().trim();
                    String namabelakang = namaPanjang.getText().toString().trim();
                    String almt = alamat.getText().toString().trim();
                    String telepon = phone.getText().toString().trim();
                    String kd_prov = Sp1.getSelectedItem().toString();
                    String kd_kab = Sp2.getSelectedItem().toString();


                    if (!namadepan.isEmpty() && !id.isEmpty() && !namabelakang.isEmpty() && !almt.isEmpty() && !telepon.isEmpty() && !kd_prov.isEmpty() && !kd_kab.isEmpty() ) {
                        // login user
                        uploadBitmap(bitmap, id, namadepan, namabelakang, almt, telepon, kd_prov, kd_kab);

                        //Toast.makeText(getApplicationContext(),
                                //"Debug :" + id + "," + kd_prov + "," + namadepan + "," + namabelakang + "," + almt + "," + telepon + "," + kd_kab, Toast.LENGTH_LONG)
                               // .show();

                    } else {
                        // jika inputan kosong tampilkan pesan
                        Toast.makeText(getApplicationContext(),
                                "Jangan kosongkan email dan password!" + kd_prov, Toast.LENGTH_LONG)
                                .show();
                    }

                        ;


                }
            });

        }
    }

    private void uploadBitmap(final Bitmap bitmap, final String id, final String namadepan, final String namabelakang, final String almt, final String telepon, final String kd_prov, final String kd_kab) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConfig.URL_EDIT_PROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject jObj = new JSONObject(new String(response.data));
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                                JSONObject obj = jObj.getJSONObject("data");
                                String id = obj.getString("user_id");
                                String depan = obj.getString("nama_depan");
                                String belakang = obj.getString("nama_belakang");
                                String nomor_anggota = obj.getString("no_anggota");
                                String propinsi = obj.getString("id_propinsi");
                                String kabupaten = obj.getString("id_kabupaten");
                                String alamat = obj.getString("alamat");
                                String phone = obj.getString("phone");
                                String picture = obj.getString("picture");
                                String update = obj.getString("update_date");

                                // buat session user yang sudah login yang menyimpan id,nama,full name, roles id, roles name
                                SharedPreferences.Editor editor = updt.edit();
                                editor.putString("nama_depan",depan);
                                editor.putString("nama_belakang", belakang);
                                editor.putString("alamat",alamat);
                                editor.putString("phone",phone);
                                editor.putString("no_anggota", nomor_anggota);
                                editor.putString("picture", picture);
                                editor.putString("url", "http://genprodev.lavenderprograms.com/img/mobile_apps/");
                                editor.commit();

                                Intent intent = new Intent(Edit_Profile.this,
                                        Menu_main.class);
                                startActivity(intent);
                                finish();

                                //Toast.makeText(getApplicationContext(),"Success Updated !"+depan+","+belakang+","+nomor_anggota+","+propinsi+","+kabupaten+","+alamat+","+phone+","+picture+","+update, Toast.LENGTH_SHORT).show();

                                Toast.makeText(getApplicationContext(),"Success Updated !", Toast.LENGTH_SHORT).show();


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

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("nama_depan", namadepan);
                params.put("nama_belakang", namabelakang);
                params.put("id_propinsi", kd_prov);
                params.put("id_kabupaten", kd_kab);
                params.put("alamat", almt);
                params.put("phone", telepon);
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }

    /*
* The method is taking Bitmap as an argument
* then it will return the byte[] array for the given bitmap
* and we will send this array to the server
* here we are using PNG Compression with 80% quality
* you can give quality between 0 to 100
* 0 means worse quality
* 100 means best quality
* */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    /*Function untuk membuka image galery
    private void dispatchTakePictureIntent() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQUEST_PICK_PHOTO);
    }*/


}
