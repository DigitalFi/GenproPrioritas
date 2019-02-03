package com.example.j_zone.genproprioritas;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class DomisiliFragment extends Fragment {
    private EditText almt,rt,kelurahan,kecamatan,kabupaten;
    private Spinner spprovinsi,spkabupaten;
    private Button btnsave;
    private SharedPreferences data_domisili,data_updatedomisili;
    public DomisiliFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_domisili, container, false);
        almt = view.findViewById(R.id.alamat);
        rt = view.findViewById(R.id.rt);
        kelurahan = view.findViewById(R.id.kelurahan);
        kecamatan = view.findViewById(R.id.kecamatan);
        btnsave = view.findViewById(R.id.btn_submit);
        spprovinsi = view.findViewById(R.id.Spdomisili);
        spkabupaten = view.findViewById(R.id.Spkabupaten);

        final SharedPreferences data_domisili = getActivity().getSharedPreferences("data_domisili", Context.MODE_PRIVATE);
        final String almts = data_domisili.getString("alamat_domisili","");
        final String rtrw = data_domisili.getString("rt_rw_domisili","");
        final String lurah = data_domisili.getString("kelurahan_domisili","");
        final String camatan = data_domisili.getString("kecamatan_domisili","");
        final String compareValuesp1 = data_domisili.getString("id_kabupaten_domisili","");
        final String compareValuesp2 = data_domisili.getString("id_propinsi_domisili","");

        final SharedPreferences data_updatedomisili = getActivity().getSharedPreferences("data_updomisili", Context.MODE_PRIVATE);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.Provinsi, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spprovinsi.setAdapter(adapter);

        if (compareValuesp2 != null) {
            int spinnerPosition = adapter.getPosition(compareValuesp1);
            spprovinsi.setSelection(spinnerPosition);
        }

        spprovinsi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String name = spprovinsi.getSelectedItem().toString();
                int idSpinner = getResources().getIdentifier(name, "array", getActivity().getPackageName());

                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(idSpinner));
                //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spkabupaten.setAdapter(spinnerArrayAdapter);
                if (compareValuesp1 != null) {
                    int spinnerPosition = spinnerArrayAdapter.getPosition(compareValuesp1);
                    spkabupaten.setSelection(spinnerPosition);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SharedPreferences data_domisili = getActivity().getSharedPreferences("data_domisili", Context.MODE_PRIVATE);
                String alamats = almt.getText().toString().trim();
                String userid = almt.getText().toString().trim();
                String erte = rt.getText().toString().trim();
                String lurahan = kelurahan.getText().toString().trim();
                String camatan = kecamatan.getText().toString().trim();
                String kd_kab = spkabupaten.getSelectedItem().toString();
                String kd_prov = spprovinsi.getSelectedItem().toString();

                final SharedPreferences user = getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
                final String id = user.getString("user_id", "");

                if (!id.isEmpty() && !alamats.isEmpty() && !erte.isEmpty() && !lurahan.isEmpty() && !camatan.isEmpty() && !kd_kab.isEmpty() && !kd_prov.isEmpty()){
                    UploadData(id,alamats,erte,lurahan,camatan,kd_kab,kd_prov);
                }else {
                    Toast.makeText(getActivity(), "Ada Yang Salah", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void UploadData(final String id,final String alamats, final String erte, final String lurahan, final String camatan, final String kd_kab, final String kd_prov) {
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_DOMISILI, new Response.Listener<String>() {
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
                params.put("alamat_domisili", alamats);
                params.put("rt_rw_domisili", erte);
                params.put("kelurahan_domisili", lurahan);
                params.put("kecamatan_domisili", camatan);
                params.put("id_kabupaten_domisili", kd_kab);
                params.put("id_propinsi_domisili", kd_prov);

                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);

    }

}
