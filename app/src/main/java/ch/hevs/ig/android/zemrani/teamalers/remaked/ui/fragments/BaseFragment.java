package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments;

import android.app.Dialog;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.customed_widgets.MontSerratTextViewRegular;

public abstract class BaseFragment extends Fragment {
    
    private Dialog msgOnProgressDialog;
    protected View root;
    
    public void showErrorSnackBar( String msg, Boolean errorMsg){
        Snackbar snackbar = Snackbar.make( root, msg, Snackbar.LENGTH_LONG );
        View snackbarView = snackbar.getView();
        
        int errorColorId =
                (errorMsg)
                        ? ContextCompat.getColor( this.getContext(), R.color.colorSnackBarError )
                        : ContextCompat.getColor(this.getContext(), R.color.colorSnackBarSuccess );
        
        snackbarView.setBackgroundColor( errorColorId );
        snackbar.show();
    }
    
    public void showProgressDialog(String...dialogs ){
        String dialog = (null == dialogs)
                ? getString( R.string.please_wait )
                : dialogs[0];
        
        msgOnProgressDialog = new Dialog(root.getContext());
        
        msgOnProgressDialog.setContentView( R.layout.dialog_progress );
        
        (( MontSerratTextViewRegular ) msgOnProgressDialog
                .findViewById( R.id.txt_progress_text ))
                .setText( dialog );
        
        msgOnProgressDialog.setCancelable( false );
        msgOnProgressDialog.setCanceledOnTouchOutside( false );
        msgOnProgressDialog.show();
        
    }
    
    public void hideProgressDialog(){
        msgOnProgressDialog.dismiss();
    }
    
    public void updateRecyclerView( Team team ) {
    }
}
