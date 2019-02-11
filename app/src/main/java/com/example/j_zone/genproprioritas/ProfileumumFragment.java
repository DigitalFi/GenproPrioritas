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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static com.example.j_zone.genproprioritas.Edit_Profile.REQUEST_TAKE_PHOTO;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileumumFragment extends Fragment {
    private EditText mail, namdep, namblkng, nohp, fb, ig, twitter;
    private Spinner Spbank;
    private Button save, take;
    private ImageView imgview;
    private SharedPreferences user;
    private SharedPreferences updt;

    public ProfileumumFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profileumum, container, false);
        mail = view.findViewById(R.id.email);
        namdep = view.findViewById(R.id.namadepan);
        namblkng = view.findViewById(R.id.namabelakang);
        nohp = view.findViewById(R.id.nohp);
        fb = view.findViewById(R.id.facebook);
        ig = view.findViewById(R.id.instagram);
        twitter = view.findViewById(R.id.twitter);
        Spbank = view.findViewById(R.id.SpFirst);
        save = view.findViewById(R.id.btn_submit);
        take = view.findViewById(R.id.btn_picture);
        imgview = view.findViewById(R.id.imageView);

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, REQUEST_TAKE_PHOTO);

        } else {

        }

        final SharedPreferences user = getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);

        final SharedPreferences updt = getActivity().getSharedPreferences("data_update", Context.MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Bank, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spbank.setAdapter(adapter);

        Spbank.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = Spbank.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        take.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });


        return view;
    }


    private void dispatchTakePictureIntent() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            final Bitmap bitmap = (Bitmap) extras.get("data");
            imgview.setImageBitmap(bitmap);

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final SharedPreferences data_user = getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
                    final String id = data_user.getString("user_id","");
                    String emails = mail.getText().toString().trim();
                    String namadepans = namdep.getText().toString().trim();
                    String namabelakangs = namblkng.getText().toString().trim();
                    String hp = nohp.getText().toString().trim();
                    String pesbuk = fb.getText().toString().trim();
                    String insta = ig.getText().toString().trim();
                    String twit = twitter.getText().toString().trim();
                    String spbank = Spbank.getSelectedItem().toString();

                    if (!id.isEmpty()&&!spbank.isEmpty()&&!emails.isEmpty()&&!namadepans.isEmpty()&&!namabelakangs.isEmpty()&&!hp.isEmpty()&&!pesbuk.isEmpty()&&!insta.isEmpty()&&!twit.isEmpty()){
                        UploadBitmap(bitmap,spbank,id,emails,namadepans,namabelakangs,hp,pesbuk,insta,twit);
                    }else {
                        Toast.makeText(getActivity(), "Ada yang kurang", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }



    }

    private void UploadBitmap(final Bitmap bitmap, final String spbank, final String id, final String emails, final String namadepans, final String namabelakangs, final String hp, final String pesbuk, final String insta, final String twit) {
        String tag_string_req = "req_login";
        VolleyMultipartRequest strReq = new VolleyMultipartRequest(Request.Method.POST, AppConfig.URL_UMUM, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {
                    JSONObject jObj = new JSONObject(new String(response.data));
                    boolean error = jObj.getBoolean("error");
                    if (!error) {

                        Intent intent = new Intent(getActivity(),
                                Menu_main.class);
                        startActivity(intent);

                        getActivity().finish();


                        Toast.makeText(getActivity(), "Success Updated !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),
                                "Error Submited , Please Try Again"+error+bitmap + spbank + emails + namadepans + namabelakangs + hp + pesbuk + insta + twit, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if ( error instanceof TimeoutError || error instanceof NoConnectionError ||error instanceof NetworkError) {
                    Toast.makeText(getActivity(),
                            "Please Check Your Connection" + error.getMessage(),
                            Toast.LENGTH_SHORT).show();}
            }
            }){

            @Override
            protected Map<String, String> getParams() {
                // kirim parameter ke server
                Map<String, String> params = new HashMap<>();
                params.put("user_id", id);
                params.put("email_umum", emails);
                params.put("nama_depan", namadepans);
                params.put("nama_belakang", namabelakangs);
                params.put("bank",spbank );
                params.put("phone", hp);
                params.put("facebook", pesbuk);
                params.put("instagram", insta);
                params.put("twitter", twit);

                return params;

            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }

//            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
//                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
//                long imagename = System.currentTimeMillis();
//                params.put("pic", new VolleyMultipartRequest.DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
//                return params;
//            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
