package ch.hevs.ig.android.zemrani.teamalers.remaked.utils.constants;

import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;

public class AppPermissions {
    public static final int  CAMERA_PERMISSION_CODE  = 1;
    public static final int  READ_STORAGE_PERMISSION_CODE  = 2;
    public static final int  PICK_IMAGE_REQUEST_CODE  = 3;
    public static final int FINE_LOCATION_PERMISSION_CODE = 4;
    
    public static void showImageChooser( Activity activity){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        activity.startActivityForResult( galleryIntent, PICK_IMAGE_REQUEST_CODE );
    }
}
