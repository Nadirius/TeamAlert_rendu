package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.auth_activities.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );
        
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
                );
        
        new Handler().postDelayed( ( () -> {
                    startActivity( new Intent(getBaseContext(), LoginActivity.class) );
                    finish();
                }),1500);
    }
}