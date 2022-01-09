package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.FragmentDisplayTeamBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.firebase.Somewhere;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.DashboardActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.handlers.PictureHandler;


public class DisplayTeamFragment extends BaseFragment {
    
    Team team;
    private FragmentDisplayTeamBinding binding;
    
    @Override
    public View onCreateView( LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
        binding = FragmentDisplayTeamBinding.inflate( inflater , container , false );
        root = binding.getRoot();
            team = ( Team ) getArguments().getSerializable( Team.class.getSimpleName()  );
            PictureHandler.loadPicture(getContext(), team.getImg(), binding.ivTeamPhoto);
            binding.tvName.setText( team.getName() );
            binding.tvCity.setText( team.getCity() );
            binding.tvDescription.setText( team.getDescription() );
            if(team.getIspublic()){
                binding.tvIsPrivate.setText( "PUBLIC" );
                binding.tvIsPrivate.setBackgroundColor( getResources().getColor( R.color.colorSnackBarSuccess ) );
            }else{
                binding.tvIsPrivate.setText( "PRIVATE" );
                binding.tvIsPrivate.setBackgroundColor( getResources().getColor( R.color.colorSnackBarError ) );
            }
            if(!team.getOwner().equals( Somewhere.getCurrentUserId() )){
                binding.tvEdit.setText( "Quit" );
                binding.tvEdit.setOnClickListener( v -> {
                    if(Somewhere.getCurrentUserId().equals( team.getOwner() )){
        
                        Toast.makeText( getContext() , "You can't subscribe yourself from your own team" , Toast.LENGTH_SHORT ).show();
                        return;
                    }
    
                    showProgressDialog( null );
                    Somewhere.unsubscribeMeFromTeam(this, team);
                } );
            }else{
                binding.tvEdit.setText( getString( R.string.lbl_edit ) );
                binding.tvEdit.setOnClickListener( v -> Toast.makeText( getContext() , "Edit Team if you own it- Not implemented ! " , Toast.LENGTH_SHORT ).show() );
            }
        
    
        binding.btnViewTeamAlerts.setOnClickListener( v -> Toast.makeText( getContext() , "View team alerts" , Toast.LENGTH_SHORT ).show() );
        binding.btnCreateNewAlert.setOnClickListener( v -> Toast.makeText( getContext() , "Create new alert" , Toast.LENGTH_SHORT ).show() );
        binding.btnWriteToOwner.setOnClickListener( v -> Toast.makeText( getContext() , "Send message to owner - Not implemented" , Toast.LENGTH_SHORT ).show() );
       
        return root;
    }
    
    public void updateRecyclerView(Team team){
        hideProgressDialog();
        startActivity( new Intent(getContext(), DashboardActivity.class ) );
    }
    
        
}