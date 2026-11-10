package com.unlam.followare;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

public class FollowareApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // El tema ya es claro, pero las librerías traen recursos -night propios:
        // esto evita que se usen cuando el celular está en modo oscuro.
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }
}
