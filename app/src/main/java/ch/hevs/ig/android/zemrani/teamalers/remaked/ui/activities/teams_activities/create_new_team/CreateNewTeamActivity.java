package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.create_new_team;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import ch.hevs.ig.android.zemrani.teamalers.remaked.R;
import ch.hevs.ig.android.zemrani.teamalers.remaked.databinding.ActivityCreateNewTeamBinding;
import ch.hevs.ig.android.zemrani.teamalers.remaked.firebase.Somewhere;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.BaseActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.ImageUploader;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.constants.AppPermissions;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.handlers.PictureHandler;

public class CreateNewTeamActivity extends BaseActivity implements ImageUploader {
    
    private ActivityCreateNewTeamBinding binding;
    private Team team;
    private Uri selectedImageFileUri = null;
    private String selectedImageFileUrl = "";
    
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        binding = ActivityCreateNewTeamBinding.inflate( getLayoutInflater() );
        root = binding.getRoot();
        setContentView( root );
        setupActionBarBack( binding.toolbarCreateNewTeamActivity );
    
        setListeners();
    }
    

    public void imageUploadSuccess( String url ) {
        selectedImageFileUrl = url;
        storeNewTeam();
    }
    
    private void setListeners() {
        binding.ivTeamGetImage.setOnClickListener( v -> {
            if ( ContextCompat.checkSelfPermission( this , Manifest.permission.READ_EXTERNAL_STORAGE ) == PackageManager.PERMISSION_GRANTED ) {
                AppPermissions.showImageChooser( this );
            } else {
                ActivityCompat.requestPermissions( this , new String[]{Manifest.permission.READ_EXTERNAL_STORAGE} , AppPermissions.READ_STORAGE_PERMISSION_CODE );
            }
        } );
        
        binding.btnSubmit.setOnClickListener( v -> {
            
            if ( validateNewTeamDetails() ) {
                showProgressDialog( getString( R.string.please_wait ) );
                if ( null != selectedImageFileUri ) {
                    Somewhere.uploadImageToFirebase( this , selectedImageFileUri );
                } else {
                    storeNewTeam();
                }
            }
        } );
    }
    
    private void storeNewTeam() {
        
        Team team = new Team();
        team.getMembers().add( Somewhere.getCurrentUserId() );
        team.setOwner( Somewhere.getCurrentUserId() );
        team.setName( binding.etTeamName.getText().toString().trim() );
        team.setCity( binding.etTeamCity.getText().toString().trim() );
        team.setDescription( binding.etTeamDescription.getText().toString().trim() );
        team.setIspublic( binding.rbTeamPublic.isChecked() );
        if(!selectedImageFileUrl.isEmpty()) {
        team.setImg( selectedImageFileUrl );
        }
        Somewhere.storeTeam(this, team);
    }
    
    @Override
    public void onRequestPermissionsResult( int requestCode , @NonNull String[] permissions , @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult( requestCode , permissions , grantResults );
        if ( requestCode == AppPermissions.READ_STORAGE_PERMISSION_CODE ) {
            if ( grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED ) {
                AppPermissions.showImageChooser( this );
            } else {
                Toast.makeText( this , getString( R.string.read_stotage_permission_denied ) , Toast.LENGTH_LONG ).show();
            }
        }
    }
    
    @Override
    protected void onActivityResult( int requestCode , int resultCode , @Nullable Intent data ) {
        super.onActivityResult( requestCode , resultCode , data );
        if ( resultCode == Activity.RESULT_OK ) {
            if ( requestCode == AppPermissions.PICK_IMAGE_REQUEST_CODE ) {
                if ( null != data ) {
                    try {
                        selectedImageFileUri = data.getData();
                        PictureHandler.loadUserPicture( getBaseContext() , selectedImageFileUri , binding.ivTeamImage );
                    } catch ( Exception e ) {
                        e.printStackTrace();
                        Toast.makeText( this , getString( R.string.image_selection_failed ) , Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        } else if ( resultCode == Activity.RESULT_CANCELED ) {
            Log.e( "Request Cancelled" , "Image selection cancelled " );
        }
    }
    
    private Boolean validateNewTeamDetails() {
        
        if ( TextUtils.isEmpty( binding.etTeamName.getText().toString().trim() ) ) {
            showErrorSnackBar( getString( R.string.err_msg_enter_team_name ) , true );
            return false;
        }
        
        if ( TextUtils.isEmpty( binding.etTeamCity.getText().toString().trim() ) ) {
            showErrorSnackBar( getString( R.string.err_msg_enter_team_city ) , true );
            return false;
        }
        
        if ( TextUtils.isEmpty( binding.etTeamDescription.getText().toString().trim() ) ) {
            showErrorSnackBar( getString( R.string.err_msg_enter_team_description ) , true );
            return false;
        }
        
        return true;
    }
    
    
    public void registrationSucess() {
        hideProgressDialog();
        Toast.makeText(getBaseContext(), getString( R.string.team_stored_successfull ), Toast.LENGTH_LONG).show();
        finish();
    }
    
    public void registationFailure( String errorMsg ) {
        hideProgressDialog();
        Toast.makeText(getBaseContext(), errorMsg , Toast.LENGTH_LONG).show();
    }
}