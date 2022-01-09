package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.ActivityMyTeamBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments.DisplayTeamFragment;

public class MyTeamActivity extends AppCompatActivity {
    
    private ActivityMyTeamBinding binding;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = ActivityMyTeamBinding.inflate(getLayoutInflater());
        setContentView( R.layout.activity_my_team );
    
        getSupportFragmentManager()
                .beginTransaction()
                .replace( binding.teamfragmentholder.getId() , DisplayTeamFragment.class , getIntent().getBundleExtra( "bundle" ), "")
                .commit();
    }
}