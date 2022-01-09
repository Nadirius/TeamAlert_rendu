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
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Alert;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.handlers.PictureHandler;

public class AlertsRecyclerViewAdapter  extends RecyclerView.Adapter< AlertsRecyclerViewAdapter.AlertViewHolder > {
    private List< Alert > alerts;
    private final Context ctx;
    private final int maskId;
    private AlertsRecyclerViewAdapter.OnAlertClickListener listener;
    
    public interface OnAlertClickListener {
        void onViewClick(Alert alert);
        void onRegistrationActionClick(Alert alert);
    }
    
    public AlertsRecyclerViewAdapter( List< Alert > alerts , Context ctx , int maskId ,  AlertsRecyclerViewAdapter.OnAlertClickListener listener ) {
        this.alerts = alerts;
        this.ctx = ctx;
        this.maskId = maskId;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public AlertsRecyclerViewAdapter.AlertViewHolder onCreateViewHolder( @NonNull ViewGroup parent , int viewType ) {
        View view = LayoutInflater
                .from( parent.getContext() )
                .inflate( maskId , parent, false );
        return new AlertsRecyclerViewAdapter.AlertViewHolder( view );
    }
    
    
    
    @Override
    public void onBindViewHolder( @NonNull AlertViewHolder holder , int position ) {
        ImageButton btnView = null;
        ImageButton btnLeaveOrJoin = null;
    
        Alert alert = alerts.get( position );
        PictureHandler.loadPicture( ctx , alert.getImg() , holder.image );
        holder.name.setText( alert.getName() );
        holder.subject.setText( alert.getSubject() );
    
        if ( maskId == R.layout.mask_team_item_list ) {
        
            btnLeaveOrJoin = holder.ctxHolder.findViewById( R.id.ib_leave );
            btnView = holder.ctxHolder.findViewById( R.id.ib_view );
        
            btnLeaveOrJoin.setOnClickListener( v -> listener.onRegistrationActionClick( alert ) );
            btnView.setOnClickListener( v -> listener.onViewClick( alert ) );
        }
    
        if ( maskId == R.layout.mask_any_team_item_list ) {

            btnView = holder.ctxHolder.findViewById( R.id.ib_view_any );
            
            btnView.setOnClickListener( v -> listener.onViewClick( alert ) );
    
        }
    }
    
    @Override
    public int getItemCount() {
        return ((alerts != null) && (alerts.size() !=0) ? alerts.size() : 0);
    }
    
    @SuppressLint( "NotifyDataSetChanged" )
    public void loadNewData( List<Alert> alerts) {
        this.alerts = alerts;
        notifyDataSetChanged();
    }
    
    public Alert getAlert(int position) {
        return ((alerts != null) && (alerts.size() !=0) ? alerts.get(position) : null);
    }
    
    static class AlertViewHolder extends RecyclerView.ViewHolder {
        ImageView image = null;
        TextView name = null;
        TextView subject = null;
        View ctxHolder = null;
        
        public AlertViewHolder( @NonNull View alertView ) {
            super( alertView );
            this.image = (ImageView ) alertView.findViewById( R.id.iv_item_image );
            this.name = (TextView ) alertView.findViewById( R.id.tv_item_name );
            this.subject = (TextView ) alertView.findViewById( R.id.tv_item_info );
            this.ctxHolder = alertView;
        }
    }
}
