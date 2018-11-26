package com.example.j_zone.genproprioritas;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.j_zone.genproprioritas.helper.AppConfig;
import com.example.j_zone.genproprioritas.helper.AppController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

public class Edit_Profile extends AppCompatActivity {

    private static final String TAG = Edit_Profile.class.getSimpleName();

    Spinner Sp1, Sp2;
    private Button btnImage;
    private Button btnSubmit;
    private ImageView imageView;
    private EditText nama, namaPanjang, phone, alamat ;
    private SharedPreferences provinsi;
    private SharedPreferences profile;
    private ProgressDialog pdialog;

    static final int REQUEST_TAKE_PHOTO = 1;

    String URL="http://genprodev.lavenderprograms.com/apigw/reff/get_propinsi";
    String URLS="http://genprodev.lavenderprograms.com/apigw/reff/get_kabupaten";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);

        } else {

        }

        btnImage = findViewById(R.id.btn_picture);
        btnSubmit = findViewById(R.id.btn_submit);
        imageView = findViewById(R.id.imageView);
        Sp1 = findViewById(R.id.SpFirst);
        nama = findViewById(R.id.nama);
        namaPanjang = findViewById(R.id.nama_pendek);
        phone = findViewById(R.id.tlp);
        alamat = findViewById(R.id.alamat);
        Sp2 = findViewById(R.id.Spsecond);
        pdialog = new ProgressDialog(this);
        pdialog.setCancelable(false);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String namadepan = nama.getText().toString().trim();
                String namabelakang = namaPanjang.getText().toString().trim();
                String phones = phone.getText().toString().trim();
                String alamats = alamat.getText().toString().trim();

                String spinner =  Sp1.getSelectedItem().toString().trim();
//                String spins = Sp2.getSelectedItem().toString().trim();

                profile = getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String id = profile.getString("user_id", "");
                profile = getSharedPreferences("provinsi", Context.MODE_PRIVATE);
                final String ids = profile.getString("kode", "");
                profile = getSharedPreferences("kabupaten", Context.MODE_PRIVATE);
                final String idkab = profile.getString("kode", "");

                if (!ids.isEmpty()&&!namadepan.isEmpty() && !namabelakang.isEmpty() && !phones.isEmpty() && !alamats.isEmpty() && !spinner.isEmpty()) {
                    // login user
                    Toast.makeText(getApplicationContext(),
                            namadepan+namabelakang+phones+alamats+id+spinner+ids, Toast.LENGTH_LONG).show();
                    checkSubmit(id,ids,namadepan,namabelakang,phones,alamats,spinner,idkab);
                } else {
                    // jika inputan kosong tampilkan pesan
                    Toast.makeText(getApplicationContext(),
                            "Jangan kosongkan email dan password!", Toast.LENGTH_LONG)
                            .show();
                }

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dispatchTakePictureIntent();

            }
        });
//
//        Sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String name = Sp1.getSelectedItem().toString();
//                int idSpinner = getResources().getIdentifier(name, "array", Edit_Profile.this.getPackageName());
//
//                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Edit_Profile.this, android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(idSpinner));
//                Sp2.setAdapter(spinnerArrayAdapter);
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Toast.makeText(Edit_Profile.this, "Tolong Pilih", Toast.LENGTH_SHORT).show();
//            }
//        });


        loadSpinnerData(URL);
        loadSpinnerData2(URLS);
        Sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                provinsi kode_provinsi = (provinsi) adapterView.getSelectedItem();
                String kode_prov = kode_provinsi.getKode();

                final SharedPreferences provinsi = getApplicationContext().getSharedPreferences("provinsi", 0);// 0 - for private mode
                final SharedPreferences.Editor editor = provinsi.edit();
                editor.putString("kode", kode_prov);
                editor.commit();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // DO Nothing here
            }
        });

//        Sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                kabupaten kode_kabupaten = (kabupaten)adapterView.getSelectedItem();
//                String kode_kab = kode_kabupaten.getKode();
//
//                final SharedPreferences kabupate = getApplicationContext().getSharedPreferences("kabupaten",0);
//                final SharedPreferences.Editor editor = kabupate.edit();
//                editor.putString("kode",kode_kab);
//                editor.commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//sp2 atasnya
    }



    private void checkSubmit(final String id,final String ids, final String namadepan, final String namabelakang, final String phones, final String alamats,final String spinner,final String idkab) {
        String tag_string_req = "req_login";
        pdialog.setMessage("Sedang Input");
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.URL_EDIT_PROFILE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());
                hideDialog();
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    String msg = jObj.getString("msg");
                    if (!error) {
                        Intent a = new Intent(Edit_Profile.this, Menu_main.class);
                        startActivity(a);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Pesan" + msg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Edit_Profile.this, "JSON Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getApplicationContext(),
                            "Please Check Your Connection" + error.getMessage(),
                            Toast.LENGTH_SHORT).show();}
                hideDialog();
            }
        }
        ){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String,String>();
                params.put("nama_depan", namadepan);
                params.put("nama_belakang", namabelakang);
                params.put("alamat", alamats);
                params.put("phone", phones);
                params.put("id_propinsi", ids);
                params.put("id_kabupaten", idkab);
                params.put("user_id",id);


                return params;
            }
        };

        AppController.getInstance().addToRequestQueue(stringRequest, tag_string_req);

    }

    private void showDialog() {
        if (!pdialog.isShowing())
            pdialog.show();
    }

    private void hideDialog() {
        if (pdialog.isShowing())
            pdialog.dismiss();
    }

    private void dispatchTakePictureIntent() {

//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
//        }
//        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(takePicture, 0);
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch(requestCode) {
//            case 0:
//                if(resultCode == RESULT_OK){
//                    Uri selectedImage = data.getData();
//                    imageView.setImageURI(selectedImage);
//                }
//
//                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = data.getData();
                    imageView.setImageURI(selectedImage);
                }
                break;
        }
            //mengambil data dari switch
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String namadepan = nama.getText().toString().trim();
                    String namabelakang = namaPanjang.getText().toString().trim();
                    String almt = alamat.getText().toString().trim();
                    String telepon = phone.getText().toString().trim();

                    provinsi = getSharedPreferences("provinsi", Context.MODE_PRIVATE);
                    final String kd_prov = provinsi.getString("kode", "");

                    if (!namadepan.isEmpty() && !namabelakang.isEmpty() && !almt.isEmpty() && !telepon.isEmpty() && !kd_prov.isEmpty() ) {
                        // login user
                        uploadBitmap(namadepan, namabelakang, almt, telepon, kd_prov);

                    } else {
                        // jika inputan kosong tampilkan pesan
                        Toast.makeText(getApplicationContext(),
                                "Jangan kosongkan email dan password!" + kd_prov, Toast.LENGTH_LONG)
                                .show();
                    }

                        Intent intent = new Intent(Edit_Profile.this,
                                Menu_main.class);
                        startActivity(intent);
                        finish();


                }
            });

        }


    private void uploadBitmap(final String namadepan, final String namabelakang, final String almt, final String telepon, final String kd_prov) {
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, AppConfig.URL_EDIT_PROFILE,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {

                        Toast.makeText(getApplicationContext(),"Success Updated !", Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(Edit_Profile.this, Menu_main.class);
                        startActivity(intent);

                        /*try {
                            JSONObject jObj = new JSONObject(new String(response.data));
                            boolean error = jObj.getBoolean("error");

                            if (!error) {
                                //user berhasil login
                                String namaDepan = jObj.getString("nama_depan");
                                String namaBelakang = jObj.getString("nama_belakang");
                                String nomorAnggota = jObj.getString("no_anggota");
                                String alamatAnggota = jObj.getString("alamat");




                                finish();
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
                        }*/
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
                params.put("nama_depan", namadepan);
                params.put("nama_belakang", namabelakang);
                params.put("id_propinsi", kd_prov);
                params.put("id_kabupaten", "32");
                params.put("alamat", almt);
                params.put("phone", telepon);
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
//            @Override
//            protected Map<String, DataPart> getByteData() {
//                Map<String, DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
//                return params;
//            }
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

    // function to get spiner frist
    private void loadSpinnerData(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<provinsi> provinsiList = new ArrayList<>();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if(!error) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        provinsiList.add(new provinsi("", "-Select-"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            provinsiList.add(new provinsi(jsonObject1.getString("kode"), jsonObject1.getString("nama")));
                        }
                        ArrayAdapter<provinsi> adapter = new ArrayAdapter<provinsi>(Edit_Profile.this, android.R.layout.simple_spinner_dropdown_item, provinsiList);
                        Sp1.setAdapter(adapter);
                    }
                    ArrayAdapter<provinsi> adapter = new ArrayAdapter<provinsi>(Edit_Profile.this, android.R.layout.simple_spinner_dropdown_item, provinsiList);
                    //Sp1.setAdapter(adapter);
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        stringRequest.setRetryPolicy(policy);
        requestQueue.add(stringRequest);
    }

    private void loadSpinnerData2(String urls) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest strReq = new StringRequest(Request.Method.POST, urls, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<kabupaten> kabupatenList = new ArrayList<>();
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    boolean error = jsonObject.getBoolean("error");
                    if(!error) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        kabupatenList.add(new kabupaten("", "Pilih Kabupaten"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            kabupatenList.add(new kabupaten(jsonObject1.getString("kode"), jsonObject1.getString("nama")));
                        }
                        ArrayAdapter<kabupaten> adapter = new ArrayAdapter<kabupaten>(Edit_Profile.this, android.R.layout.simple_spinner_dropdown_item, kabupatenList);
                        Sp2.setAdapter(adapter);
                    }
                }catch (JSONException e){e.printStackTrace();}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        }){
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded";
            }

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String,String>();
                params.put("kd_propinsi","32");
                return params;
            }
        };
        int socketTimeout = 30000;
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        strReq.setRetryPolicy(policy);
        requestQueue.add(strReq);
    }

}
