package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities;

import android.os.Bundle;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.ActivityAnyTeamBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.BaseActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments.DisplayAnyTeamFragment;

public class AnyTeamActivity extends BaseActivity {
    
    private ActivityAnyTeamBinding binding;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_any_team );
        binding = ActivityAnyTeamBinding.inflate(getLayoutInflater());
    
        getSupportFragmentManager()
                .beginTransaction()
                .replace(binding.teamfragmentholder2.getId(), DisplayAnyTeamFragment.class , getIntent().getBundleExtra( "bundle" ), "")
                .commit();
    }
    }
