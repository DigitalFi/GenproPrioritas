package com.example.j_zone.genproprioritas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class Bottomqrcode extends BottomSheetDialogFragment  {
    String text2qr;
    ImageView image;

    private SharedPreferences user;

    public Bottomqrcode() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bottom, container, false);
        image = (ImageView) view.findViewById(R.id.qr);

        SharedPreferences user = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        final String nama = user.getString("user_name", "");
        final String email = user.getString("email", "");

        text2qr = "Nama :" + nama + "<br/>" + "Email :" + email;
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(text2qr, BarcodeFormat.QR_CODE,500,500);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            image.setImageBitmap(bitmap);

        }catch (WriterException e){
            e.printStackTrace();
        }

        return view;
    }

}
