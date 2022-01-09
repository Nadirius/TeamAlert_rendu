package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.List;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.FragmentTeamsBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.firebase.Somewhere;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.MyTeamActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.create_new_team.CreateNewTeamActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.join_new_team.JoinNewTeamActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.adapters.TeamsRecyclerViewAdapter;

public class TeamsFragment extends BaseFragment implements TeamsRecyclerViewAdapter.OnTeamClickListener{
    
    private FragmentTeamsBinding binding;
    private TeamsRecyclerViewAdapter teamViewAdapter;
    private List<Team> teams;
    
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );

    }
    
    public View onCreateView( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
        binding = FragmentTeamsBinding.inflate( inflater , container , false );
        root = binding.getRoot();
        binding.btnCreateNewTeam.setOnClickListener( v -> startActivity( new Intent(getContext(), CreateNewTeamActivity.class ) ) );
        binding.btnJoinNewTeam.setOnClickListener(  v -> startActivity(new Intent( getContext(), JoinNewTeamActivity.class)));
        requireActivity().setTitle( "My Teams" );
        return root;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        getUserSubscribedListTeams();
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    
    @Override
    public void onViewClick( Team team ) {
        Bundle result = new Bundle();
        result.putSerializable( Team.class.getSimpleName() , team );
        Intent i = new Intent( this.requireActivity().getBaseContext() , MyTeamActivity.class);
        i.putExtra( "bundle", result );
        this.startActivity(i);
    }
    
    @Override
    public void onRegistrationActionClick( Team team ) {
        if(Somewhere.getCurrentUserId().equals( team.getOwner() )){
            
            Toast.makeText( getContext() , "You can't subscribe yourself from your own team" , Toast.LENGTH_SHORT ).show();
            return;
        }
        
        showProgressDialog( null );
        Somewhere.unsubscribeMeFromTeam(this, team);
       
    }
    
    public void updateRecyclerView(Team team){
        teams.remove( team );
        successTeamListActionOnFireStore( teams  );
    }
    
    @Override
    public void onCreateOptionsMenu( @NonNull Menu menu , @NonNull MenuInflater inflater ) {
        inflater.inflate(  R.menu.teams_menu,menu);
        super.onCreateOptionsMenu( menu , inflater );
    }
    
    @Override
    public boolean onOptionsItemSelected( @NonNull MenuItem item ) {
        return super.onOptionsItemSelected( item );
    }
    //#########################################
    private void getUserSubscribedListTeams(){
        showProgressDialog( null );
        Somewhere.getUserSubscribedListTeams(this);
    }
    
    public void successTeamListActionOnFireStore( List<Team> teams ) {
        hideProgressDialog();
        if (teams.size() > 0) {
            this.teams = teams;
            binding.rvTeamItems.setVisibility( View.VISIBLE );
            binding.tvNoTeamItemsFound.setVisibility( View.GONE);
            binding.rvTeamItems.setLayoutManager(new LinearLayoutManager( getActivity() ) );
            binding.rvTeamItems.setHasFixedSize( true );
            teamViewAdapter = new TeamsRecyclerViewAdapter( teams, getContext(),  R.layout.mask_team_item_list , this);
            binding.rvTeamItems.setAdapter(teamViewAdapter);
        } else {
            binding.rvTeamItems.setVisibility( View.GONE );
            binding.tvNoTeamItemsFound.setVisibility(View.VISIBLE);
        }
    }
    
    

    

    

}