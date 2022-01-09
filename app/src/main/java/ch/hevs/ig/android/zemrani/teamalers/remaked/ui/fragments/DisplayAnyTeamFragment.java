package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.FragmentDisplayAnyTeamBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.firebase.Somewhere;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.DashboardActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.handlers.PictureHandler;

public class DisplayAnyTeamFragment extends BaseFragment {
    
    Team team;
    private FragmentDisplayAnyTeamBinding binding;
    
    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
        binding = FragmentDisplayAnyTeamBinding.inflate( inflater , container , false );
        root = binding.getRoot();
        team = ( Team ) getArguments().getSerializable( Team.class.getSimpleName()  );
        PictureHandler.loadPicture(getContext(), team.getImg(), binding.ivTeamPhoto);
        binding.tvName.setText( team.getName() );
        binding.tvCity.setText( team.getCity() );
        binding.tvDescription.setText( team.getDescription() );
        if(team.getIspublic()){
            binding.tvIsPrivate.setText( "PUBLIC" );
            binding.tvIsPrivate.setBackgroundColor( getResources().getColor( R.color.colorSnackBarSuccess ) );
            binding.btnViewTeamAlerts.setOnClickListener( v -> Toast.makeText( getContext() , "View team alerts" , Toast.LENGTH_SHORT ).show() );
            binding.btnJoinNewTeam.setOnClickListener( v -> {
                showProgressDialog( null );
                Somewhere.addUserToTeam(team, this); });
         
        }else{
            binding.tvIsPrivate.setText( "PRIVATE" );
            binding.tvIsPrivate.setBackgroundColor( getResources().getColor( R.color.colorSnackBarError ) );
            binding.btnJoinNewTeam.setBackgroundColor( getResources().getColor( R.color.colorLightGrey ));
            binding.btnViewTeamAlerts.setBackgroundColor( getResources().getColor( R.color.colorLightGrey ));
            binding.btnJoinNewTeam.setEnabled( false );
            binding.btnViewTeamAlerts.setEnabled( false );
            binding.btnViewTeamAlerts.setOnClickListener( v -> Toast.makeText( getContext() , "Private Team, please write to owner" , Toast.LENGTH_SHORT ).show() );
            binding.btnJoinNewTeam.setOnClickListener( v -> Toast.makeText( getContext() , "Private Team, please write to owner" , Toast.LENGTH_SHORT ).show() );
        }
        binding.btnWriteToOwner.setOnClickListener( v -> Toast.makeText( getContext() , "Send message to owner - Not implemented" , Toast.LENGTH_SHORT ).show() );
        
        
    
        
        return root;
    }
    
    public void onSuccessRegistartion() {
        hideProgressDialog();
        startActivity( new Intent(getContext(), DashboardActivity.class ) );
    }
}