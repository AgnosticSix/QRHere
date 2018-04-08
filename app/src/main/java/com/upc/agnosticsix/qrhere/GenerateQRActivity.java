package com.upc.agnosticsix.qrhere;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

public class GenerateQRActivity extends AppCompatActivity implements View.OnClickListener {

    private String LOG_TAG = "GenerateQRCode";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrscannergenerator);

        Button button1 = (Button) findViewById(R.id.generarbtn);
        button1.setOnClickListener(this);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.generarbtn:
                EditText qrInput = (EditText) findViewById(R.id.generartxt);
                String qrInputText = qrInput.getText().toString();
                Log.v(LOG_TAG, qrInputText);

                //Find screen size
                WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
                Display display = manager.getDefaultDisplay();
                Point point = new Point();
                display.getSize(point);
                int width = point.x;
                int height = point.y;
                int smallerDimension = width < height ? width : height;
                smallerDimension = smallerDimension * 3/4;

                //Encode with a QR Code image
                QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                        null,
                        Contents.Type.TEXT,
                        BarcodeFormat.QR_CODE.toString(),
                        smallerDimension);
                try {
                    Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
                    ImageView myImage = (ImageView) findViewById(R.id.imageView1);
                    myImage.setImageBitmap(bitmap);

                } catch (WriterException e) {
                    e.printStackTrace();
                }


                break;

            // More buttons go here (if any) ...

        }
    }
}
