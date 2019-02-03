package com.example.j_zone.genproprioritas;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class KtpFragment extends Fragment {

    private Spinner Spagama,Spgoldar,Spkelamin,Spstatusnikah,Spprovinsi,Spkabupaten;
    private EditText noktp,namaktp,tmptlahir,tgllahir,almt,rtrw,kelurahan,kecamatan;
    private Button save;
    private SharedPreferences dataktp,data_updatektp;

    public KtpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ktp, container, false);
        Spagama = view.findViewById(R.id.SpAgama);
        Spgoldar = view.findViewById(R.id.SpGoldar);
        Spkelamin = view.findViewById(R.id.SpJeniskelamin);
        Spstatusnikah = view.findViewById(R.id.SpStatusNikah);
        Spprovinsi = view.findViewById(R.id.SpProvinsi);
        Spkabupaten = view.findViewById(R.id.Spkabupaten);

        save = view.findViewById(R.id.btn_submit);

        noktp = view.findViewById(R.id.noktp);
        namaktp = view.findViewById(R.id.namaktp);
        tmptlahir = view.findViewById(R.id.tempatlahir);
        tgllahir = view.findViewById(R.id.Tanggallahir);
        almt = view.findViewById(R.id.alamat);
        rtrw = view.findViewById(R.id.rtrw);
        kelurahan = view.findViewById(R.id.kelurahan);
        kecamatan = view.findViewById(R.id.kecamatan);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.agama, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spagama.setAdapter(adapter);

        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(getActivity(), R.array.goldar, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spgoldar.setAdapter(adap);

        ArrayAdapter<CharSequence> adapters = ArrayAdapter.createFromResource(getActivity(), R.array.jeniskelamin, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spkelamin.setAdapter(adapters);

        ArrayAdapter<CharSequence> adapterx = ArrayAdapter.createFromResource(getActivity(), R.array.statusnikah, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spstatusnikah.setAdapter(adapterx);

        final SharedPreferences dataktp = getActivity().getSharedPreferences("data_ktp", Context.MODE_PRIVATE);
        final String noktps = dataktp.getString("no_ktp","");
        final String namaktps = dataktp.getString("nama_ktp","");
        final String tmptlahirs = dataktp.getString("tempat_lahir","");
        final String tgllahirs = dataktp.getString("tanggal_lahir","");
        final String agamas = dataktp.getString("agama","");
        final String goldars = dataktp.getString("golongan_darah","");
        final String jeniss = dataktp.getString("jenis_kelamin","");
        final String statuss = dataktp.getString("status","");
        final String alamats = dataktp.getString("alamat","");
        final String rtrws = dataktp.getString("rt_rw_ktp","");
        final String kelurahans = dataktp.getString("kelurahan_ktp","");
        final String kecamatans = dataktp.getString("kecamatan_ktp","");
        final String idkab = dataktp.getString("id_kabupaten","");
        final String idprov = dataktp.getString("id_propinsi","");

        final SharedPreferences data_updatektp = getActivity().getSharedPreferences("data_upktp", Context.MODE_PRIVATE);


        Spprovinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = Spprovinsi.getSelectedItem().toString();
                int idSpinner = getResources().getIdentifier(name, "array", getActivity().getPackageName());

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(idSpinner));
                //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Spkabupaten.setAdapter(spinnerArrayAdapter);
                if (idkab != null) {
                    int spinnerPosition = spinnerArrayAdapter.getPosition(idkab);
                    Spkabupaten.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences dataktp = getActivity().getSharedPreferences("data_ktp", Context.MODE_PRIVATE);
                final String noktps = noktp.getText().toString().trim();
                final String namaktpx = namaktp.getText().toString().trim();
                final String tmptlahirx = tmptlahir.getText().toString().trim();
                final String tgllahirx = tgllahir.getText().toString().trim();
                final String agama = Spagama.getSelectedItem().toString();
                final String goldar = Spgoldar.getSelectedItem().toString();
                final String jenis = Spkelamin.getSelectedItem().toString();
                final String status = Spstatusnikah.getSelectedItem().toString();
                final String alamata = almt.getText().toString().trim();
                final String rtrwx = rtrw.getText().toString().trim();
                final String kelurahanx = kelurahan.getText().toString().trim();
                final String kecamatanx = kecamatan.getText().toString().trim();
                final String idkab = Spkabupaten.getSelectedItem().toString();
                final String idprov = Spprovinsi.getSelectedItem().toString();

                final SharedPreferences user = getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String id = user.getString("user_id", "");

                if (!id.isEmpty() && !noktps.isEmpty() && !namaktpx.isEmpty() && !tmptlahirx.isEmpty() && !tgllahirx.isEmpty()
                        && !agama.isEmpty() && !goldar.isEmpty() && !jenis.isEmpty() && !status.isEmpty() && !alamata.isEmpty()
                        && !rtrwx.isEmpty()  && !kelurahanx.isEmpty()  && !kecamatanx.isEmpty()  && !idkab.isEmpty() && !idprov.isEmpty()){
                    UploadData(id,noktps,namaktpx,tmptlahirx,tgllahirx,agama,goldar,jenis,status,alamata,rtrwx,kelurahanx,kecamatanx,idkab,idprov);
                }else {
                    Toast.makeText(getActivity(), "Kolom Wajib Diisi Semua", Toast.LENGTH_SHORT).show();
                }

            }
        });
        return view;
    }

    private void UploadData(final String id, final String noktps, final String namaktpx, final String tmptlahirx, final String tgllahirx, final String agama, final String goldar, final String jenis, final String status, final String alamata, final String rtrwx, final String kelurahanx, final String kecamatanx, final String idkab, final String idprov) {
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_KTP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    if (!error) {


                        Intent intent = new Intent(getActivity(),
                                Menu_main.class);
                        startActivity(intent);

                        getActivity().finish();

                        Toast.makeText(getActivity(), "Success Updated !", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(),
                                "Error Submited , Please Try Again", Toast.LENGTH_LONG).show();
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("user_id", id);
                params.put("no_ktp", noktps);
                params.put("nama_ktp", namaktpx);
                params.put("tempat_lahir", tmptlahirx);
                params.put("tanggal_lahir", tgllahirx);
                params.put("agama", agama);
                params.put("golongan_darah", goldar);
                params.put("jenis_kelamin", jenis);
                params.put("status", status);
                params.put("alamat", alamata);
                params.put("rt_rw_ktp", rtrwx);
                params.put("kelurahan_ktp", kelurahanx);
                params.put("kecamatan_ktp", kecamatanx);
                params.put("id_kabupaten", idkab);
                params.put("id_propinsi", idprov);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

}
