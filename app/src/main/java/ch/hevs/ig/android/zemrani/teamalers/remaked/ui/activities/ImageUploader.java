package ch.hevs.ig.android.zemrani.teamalers.remaked.ui.activities;

public interface ImageUploader {
    void imageUploadSuccess( String toString );
    
    String getLocalClassName();
    
    void hideProgressDialog();
}
