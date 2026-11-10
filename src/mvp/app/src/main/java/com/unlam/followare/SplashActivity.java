package com.unlam.followare;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

/**
 * Pantalla de entrada con la identidad de Followare.
 * Las notificaciones abren MainActivity directamente y no pasan por acá.
 */
public class SplashActivity extends AppCompatActivity {

    private static final long DURACION_MS = 1200;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable abrirApp = () -> {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler.postDelayed(abrirApp, DURACION_MS);
    }

    @Override
    protected void onDestroy() {
        handler.removeCallbacks(abrirApp);
        super.onDestroy();
    }
}
