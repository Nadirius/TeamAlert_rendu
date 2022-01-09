package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.ActivityDashboardBinding;

public class DashboardActivity extends BaseActivity {
    
    private ActivityDashboardBinding binding;
    
    @SuppressLint( "UseCompatLoadingForDrawables" )
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        
        binding = ActivityDashboardBinding.inflate( getLayoutInflater() );
        setContentView( binding.getRoot() );
        
        Objects.requireNonNull( getSupportActionBar() ).setBackgroundDrawable( getDrawable( R.drawable.app_gradient_color_background ));
        
        BottomNavigationView navView = findViewById( R.id.nav_view );
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_teams ,
                R.id.navigation_alerts ,
                R.id.navigation_settings ).build();
        NavController navController = Navigation.findNavController( this , R.id.nav_host_fragment_activity_dashboard );
        NavigationUI.setupActionBarWithNavController( this , navController , appBarConfiguration );
        NavigationUI.setupWithNavController( binding.navView , navController );
    }
    
    @Override
    public void onBackPressed() {
        doubleBackToExit();
    }
}