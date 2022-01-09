package ch.hevs.ig.android.zemrani.teamalers.remaked.utils.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.handlers.PictureHandler;

public class TeamsRecyclerViewAdapter extends RecyclerView.Adapter< TeamsRecyclerViewAdapter.TeamViewHolder > {
    private List< Team > teams;
    private final Context ctx;
    private final int maskId;
    private final OnTeamClickListener listener;
    
    public interface OnTeamClickListener {
        void onViewClick(Team team);
        void onRegistrationActionClick(Team team);
    }
    
    public TeamsRecyclerViewAdapter( List< Team > teams , Context ctx , int maskId , OnTeamClickListener listener) {
        this.teams = teams;
        this.ctx = ctx;
        this.maskId = maskId;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public TeamViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater
                .from( parent.getContext() )
                .inflate( maskId , parent, false );
        return new TeamViewHolder( view );
    }
    
    @Override
    public void onBindViewHolder( @NonNull TeamViewHolder holder , int position ) {
        
        ImageButton btnView = null;
        
    
        Team team = teams.get(position);
            PictureHandler.loadPicture(ctx, team.getImg(), holder.image);
        holder.title.setText( team.getName() );
        holder.canton.setText( team.getCity());
        
        if (maskId == R.layout.mask_team_item_list ){
            ImageButton btnLeave = holder.ctxHolder.findViewById( R.id.ib_leave );
            btnView = holder.ctxHolder.findViewById( R.id.ib_view );
    
            btnLeave.setOnClickListener( v -> listener.onRegistrationActionClick( team ) );
            btnView.setOnClickListener( v -> listener.onViewClick( team ));
        }
        
        if (maskId == R.layout.mask_any_team_item_list ){
            btnView = holder.ctxHolder.findViewById( R.id.ib_view_any );
            btnView.setOnClickListener( v -> listener.onViewClick( team ));
        }
    }
    
    @Override
    public int getItemCount() {
        return ((teams != null) && (teams.size() !=0) ? teams.size() : 0);
    }
    
    @SuppressLint( "NotifyDataSetChanged" )
    public void loadNewData( List<Team> teams) {
        this.teams = teams;
        notifyDataSetChanged();
    }
    
    public Team getTeam(int position) {
        return ((teams != null) && (teams.size() !=0) ? teams.get(position) : null);
    }
    
    static class TeamViewHolder extends RecyclerView.ViewHolder {
        ImageView image = null;
        TextView title = null;
        TextView canton = null;
        View ctxHolder = null;
        
        public TeamViewHolder( @NonNull View teamView ) {
            super( teamView );
            this.image = (ImageView ) teamView.findViewById( R.id.iv_item_image );
            this.title = (TextView ) teamView.findViewById( R.id.tv_item_name );
            this.canton = (TextView ) teamView.findViewById( R.id.tv_item_info );
            this.ctxHolder = teamView;
        }
    }
}
