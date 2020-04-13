package com.example.thugwtsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.thugwtsapp.utls.InternetConnection;

public class LauncherScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.screen_launcher);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (InternetConnection.checkConnection(LauncherScreen.this)) {
                    Intent intent = new Intent(LauncherScreen.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LauncherScreen.this, "No Internet ", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        return;
    }
}
