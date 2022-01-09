package ch.hevs.ig.android.zemrani.teamalers.remaked.firebase;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;

import ch.hevs.ig.android.zemrani.teamalers.remaked.models.Team;
import ch.hevs.ig.android.zemrani.teamalers.remaked.models.User;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.BaseActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.ImageUploader;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.auth_activities.LoginActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.auth_activities.RegisterActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.auth_activities.UserProfileActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.create_new_team.CreateNewTeamActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities.teams_activities.join_new_team.JoinNewTeamActivity;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments.BaseFragment;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments.DisplayAnyTeamFragment;
import ch.hevs.ig.android.zemrani.teamalers.remaked.ui.fragments.TeamsFragment;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.constants.AppConst;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.constants.AppFirebase;
import ch.hevs.ig.android.zemrani.teamalers.remaked.utils.handlers.PictureHandler;

public class Somewhere {
    

    
    public static void storeUser( RegisterActivity registerActivity , User u ) {
        FirebaseFirestore
            .getInstance()
            .collection( AppFirebase.Collections.USERS )
            .document( u.getId() )
            .set( u , SetOptions.merge() )
            .addOnSuccessListener( t -> registerActivity.registrationSucess() )
            .addOnFailureListener( e -> registerActivity.registationFailure( e.getMessage() ) );
    }
    
    public static String getCurrentUserId() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String currentUserID = "";
        if(currentUserID != null){
            currentUserID = currentUser.getUid();
        }
        return currentUserID;
        
    }
    
    public static User getUser( Activity activity ) {
        FirebaseFirestore
            .getInstance()
            .collection( AppFirebase.Collections.USERS )
            .document( getCurrentUserId() )
            .get()
            .addOnSuccessListener( documentSnapshot -> { } )
            .addOnFailureListener( e -> { } );
        return null;
    }
    
    public static void getUser( LoginActivity activity ) {
        FirebaseFirestore.getInstance()
            .collection( AppFirebase.Collections.USERS )
            .document( getCurrentUserId() )
            .get()
            .addOnSuccessListener( documentSnapshot -> {
                User user = documentSnapshot.toObject( User.class );
                activity.userLoggedInSuccess( user );
                activity.getSharedPreferences( AppConst.TEAMALERTS_PREFERENCES , Context.MODE_PRIVATE )
                        .edit()
                        .putString( AppConst.CURRENT_USERNAME , user.getFirstName() + " " + user.getLastName() ).apply(); } )
            .addOnFailureListener( e -> activity.hideProgressDialog() );
    }
    
    public static void updateUserData( UserProfileActivity activity , Map< String, Object > userMap ) {
        FirebaseFirestore.getInstance()
            .collection( AppFirebase.Collections.USERS )
            .document( getCurrentUserId() )
            .update( userMap )
            .addOnSuccessListener( t -> activity.userProfileUpdateSuccess() )
            .addOnFailureListener( e -> {
                activity.hideProgressDialog();
                Log.e( activity.getLocalClassName() , "Error while updating the user details" );
            } );
    }
    
    public static void uploadImageToFirebase( ImageUploader activity , Uri imageFileUri ) {
        FirebaseStorage.getInstance()
            .getReference()
            .child( AppConst.PROFILE_USER_IMAGE + "_" + System.currentTimeMillis() + "." + PictureHandler.getFileExtention( ( Activity ) activity , imageFileUri ) )
            .putFile( imageFileUri )
            .addOnSuccessListener( taskSnapshot -> {
                Task< Uri > downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                Log.e( "Firebase Image URL" , downloadUrl.toString() );
                downloadUrl.addOnSuccessListener( uri -> {
                    Log.e( "Downloadable Image URL" , uri.toString() );
                    activity.imageUploadSuccess( uri.toString() ); } ); } )
            .addOnFailureListener( e -> {
                    activity.hideProgressDialog();
                    Log.e( activity.getLocalClassName() , e.getMessage() , e );} );
    }
    
    public static void getUserSubscribedListTeams( TeamsFragment teamsFragment ) {
        FirebaseFirestore.getInstance()
                .collection( AppFirebase.Collections.TEAMS )
                .whereArrayContains( "members", getCurrentUserId() ).get().addOnCompleteListener( task -> {
                    if( task.isSuccessful() ){
                        teamsFragment
                                .successTeamListActionOnFireStore(task.getResult()
                                        .toObjects( Team.class ));
                    }else{
                        Log.e( "Getting User subscribed list teams failed" , "Firestore Error"  );
                        teamsFragment.hideProgressDialog();
                    }
        } );
        
    }
    
    public static void storeTeam( CreateNewTeamActivity createNewTeamActivity , Team team ) {
        FirebaseFirestore.getInstance()
            .collection( AppFirebase.Collections.TEAMS )
            .add( team )
            .addOnSuccessListener( new OnSuccessListener< DocumentReference >() {
                @Override
                public void onSuccess( DocumentReference documentReference ) {
                    documentReference.update( AppFirebase.Team.ID , documentReference.getId() );
                    createNewTeamActivity.registrationSucess(); }})
            .addOnFailureListener(
                    e -> createNewTeamActivity.registationFailure( e.getMessage() ) );
    }
    
    public static void unsubscribeMeFromTeam( BaseFragment frag, Team team ) {
        FirebaseFirestore.getInstance()
                .collection( AppFirebase.Collections.TEAMS )
                .document(team.getId())
                .update( "members",FieldValue.arrayRemove( getCurrentUserId() ))
                .addOnSuccessListener( t -> frag.updateRecyclerView( team ) )
                .addOnFailureListener( exception -> {
            frag.hideProgressDialog();
            Log.e("unsubscribe from team: " , exception.getMessage()  );
        } );
    }
    
    public static void getListTeams( JoinNewTeamActivity joinNewTeamActivity ) {
        FirebaseFirestore.getInstance()
                .collection( AppFirebase.Collections.TEAMS )
                .get().addOnCompleteListener( task -> {
            if( task.isSuccessful() ){
                joinNewTeamActivity
                        .successTeamListActionOnFireStore(task.getResult()
                                .toObjects( Team.class ));
            }else{
                Log.e( "Getting User subscribed list teams failed" , "Firestore Error"  );
                joinNewTeamActivity.hideProgressDialog();
            }
        } );
    }
    
    public static void addUserToTeam( Team team , DisplayAnyTeamFragment act ) {
        FirebaseFirestore.getInstance().collection( AppFirebase.Collections.TEAMS ).document(team.getId()).update( "members", FieldValue.arrayUnion( getCurrentUserId() ) )
        .addOnSuccessListener( t -> act.onSuccessRegistartion() )
        .addOnFailureListener( e -> act.hideProgressDialog() );
    }
}