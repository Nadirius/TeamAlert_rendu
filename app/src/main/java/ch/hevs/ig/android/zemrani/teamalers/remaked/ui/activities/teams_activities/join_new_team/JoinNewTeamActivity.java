package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.join_new_team;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;
import java.util.stream.Collectors;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.ActivityJoinNewTeamBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.firebase.Somewhere;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.BaseActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.AnyTeamActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.create_new_team.CreateNewTeamActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.adapters.TeamsRecyclerViewAdapter;

public class JoinNewTeamActivity extends BaseActivity implements TeamsRecyclerViewAdapter.OnTeamClickListener{
    
    private ActivityJoinNewTeamBinding binding;
    private TeamsRecyclerViewAdapter teamViewAdapter;
    

    
    private List< Team > teams;
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = ActivityJoinNewTeamBinding.inflate(getLayoutInflater() );
        root = binding.getRoot();
        setContentView( root );
        binding.btnCreateNewTeam.setOnClickListener( v -> startActivity( new Intent(this, CreateNewTeamActivity.class ) ) );
        
       
    }
    
    @Override
    public void onResume() {
        super.onResume();
        getListTeams();
    }

    private void getListTeams() {
        showProgressDialog( null );
        Somewhere.getListTeams(this);
    }

    @RequiresApi( api = Build.VERSION_CODES.N )
    public void setTeams( List< Team > teams ) {
        this.teams = teams.stream()
                .filter( l -> l.getMembers()
                        .contains( Somewhere.getCurrentUserId() ))
                .collect( Collectors.toList() );
    }

    @Override
    public void onViewClick( Team team ) {
        Bundle result = new Bundle();
        result.putSerializable( Team.class.getSimpleName() , team );
        Intent i = new Intent( this , AnyTeamActivity.class);
        i.putExtra( "bundle", result );
        this.startActivity(i);
    }

    @Override
    public void onRegistrationActionClick( Team team ) {

    }

    @RequiresApi( api = Build.VERSION_CODES.N )
    public void successTeamListActionOnFireStore( List< Team> list ) {
        hideProgressDialog();
        if(list.size()>0){this.teams =
                list.stream().filter( l -> !l.getMembers().contains( Somewhere.getCurrentUserId() ) )
                        .collect( Collectors.toList() );
        binding.rvAllTeams.setVisibility( View.VISIBLE );
        binding.tvNoRecordsAvailable.setVisibility( View.GONE );
        binding.rvAllTeams.setLayoutManager( new LinearLayoutManager( this ) );
        binding.rvAllTeams.setHasFixedSize( true );
        teamViewAdapter = new TeamsRecyclerViewAdapter( teams , getBaseContext() , R.layout.mask_any_team_item_list , this );
        binding.rvAllTeams.setAdapter( teamViewAdapter );
    }else {
            binding.rvAllTeams.setVisibility( View.GONE );
            binding.tvNoRecordsAvailable.setVisibility(View.VISIBLE);
        }



    }
}