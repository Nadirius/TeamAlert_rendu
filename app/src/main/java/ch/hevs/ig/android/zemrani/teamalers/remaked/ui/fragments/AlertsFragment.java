package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.FragmentAlertsBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.adapters.AlertsRecyclerViewAdapter;


public class AlertsFragment extends BaseFragment {
    

    private FragmentAlertsBinding binding;
    private AlertsRecyclerViewAdapter alertsViewAdapter;
    @Override
    public void onCreate( @Nullable Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setHasOptionsMenu( true );
    }
    
    
    public View onCreateView( @NonNull LayoutInflater inflater , ViewGroup container , Bundle savedInstanceState ) {
    
        binding = FragmentAlertsBinding.inflate( inflater , container , false );
        root = binding.getRoot();
    
        requireActivity().setTitle( "My Alerts" );
        return root;
    }
    
    @Override
    public void onResume() {
        super.onResume();
        //getUserSubscribedListTeams();
    }
    
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}