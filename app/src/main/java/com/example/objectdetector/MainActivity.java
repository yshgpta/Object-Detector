package com.example.objectdetector;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ThreeBounce;

public class MainActivity extends AppCompatActivity{
    ProgressBar progressBar;
    TextView appname;
    ImageView mainlogo;
    Animation scale;
    private static final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getIntent().getBooleanExtra("EXIT",false)){
            finish();
        }
        getPermissions();
        progressBar = (ProgressBar)findViewById(R.id.spin_kit);
        Sprite threeBounce = new ThreeBounce();
        progressBar.setIndeterminateDrawable(threeBounce);

        appname = findViewById(R.id.maintext);
        mainlogo = findViewById(R.id.logo);

        scale = AnimationUtils.loadAnimation(this,R.anim.scale);
        appname.startAnimation(scale);
        mainlogo.startAnimation(scale);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
                Intent i = new Intent(MainActivity.this,ShowCamera.class);
                startActivity(i);
            }
        },5000);


    }
    public static void pause(int mSec){
        try {
            Thread.sleep(mSec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void getPermissions(){

        int camerapermissionStatus = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        int storagepermissionStatus = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionStatus = camerapermissionStatus + storagepermissionStatus;

        if (permissionStatus != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{
                                Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE
                        },REQUEST_CODE);
                while (permissionStatus != PackageManager.PERMISSION_GRANTED){
                    pause(100);
                    permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)+ ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
                }
            }
        }
        pause(1000);
    }
}
