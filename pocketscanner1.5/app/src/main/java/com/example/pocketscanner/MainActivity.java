package com.example.pocketscanner;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.IOException;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;


public class MainActivity extends AppCompatActivity {
    Button scan_btn;//qr scanner
    Button button3;//pdf
    Button button5;//my files
    private Bundle savedInstanceState;
    private ZXingScannerView scannerView;//qr scanner

    @Override
    protected void onCreate(Bundle savedInstanceState) {//qr
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      checkPermission();
        scan_btn = (Button) findViewById(R.id.btn_scan);//qr scanner
        scan_btn.setOnClickListener(new View.OnClickListener() {//qr scanner
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));//qr scanner

            }
        });
        button5=(Button)findViewById(R.id.button5);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent=new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setType("*/*");
                    startActivity(intent);
                }
                catch(Exception e)
                {
                  e.printStackTrace();
                }//my scanned images
            }
        });
        button3 = (Button) findViewById(R.id.button3);//pdf
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openActivity2();
            }
        });//pdf


    }//qr,pdf,rtp

    public void checkPermission(){
        Dexter.withActivity(this)
        .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();

            }
        }).onSameThread().check();
    }//rtp

    public void openActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }//pdf

public void camera(View v){
    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
    startActivity(intent);
}
    public void OpenGallery(View v) {
        int REQUEST_CODE = 99;
        int preference = ScanConstants.OPEN_MEDIA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }//scan library

    public void OpenCamera(View v) {
        int REQUEST_CODE = 99;
        int preference = ScanConstants.OPEN_CAMERA;
        Intent intent = new Intent(this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);
        startActivityForResult(intent, REQUEST_CODE);
    }//scan library

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // getContentResolver().delete(uri, null, null);
                // scannedImageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }//scan library




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.draw_menu, menu);
        return true;

    }//menu(Privacy,About us)

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.aboutus) {
            Intent intent = new Intent(MainActivity.this, Aboutus.class);
            startActivity(intent);
            return false;
        }
        if (id == R.id.privacy) {
            Intent intent = new Intent(MainActivity.this, Privacy.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }//menu(Privacy,About us)

   }








