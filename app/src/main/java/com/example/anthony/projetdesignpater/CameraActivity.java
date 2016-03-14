package com.example.anthony.projetdesignpater;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CameraActivity extends Activity {
    private String mCurrentPhotoPath;

    private static final String JPEG_FILE_PREFIX = "IMG_";
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final int ACTION_TAKE_PHOTO_B = 1;
    private String nom_photo=null;
    private String commentaire_photo=null;
    private Double lat;
    private Double lon;
    private File file;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        if (extras != null)
        {
            lat = extras.getDouble("lat");
            lon = extras.getDouble("lon");
        }

        //setContentView(R.layout.activity_camera);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        file = null;
        try {

            file = setUpPhotoFile(nom_photo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

        startActivityForResult(takePictureIntent, ACTION_TAKE_PHOTO_B);


    }


    private String getAlbumName() {
        return getString(R.string.album_name);
    }
    private File getAlbumDir() {
        File storageDir = null;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            storageDir = new File (
                    Environment.getExternalStorageDirectory()
                            + "/"
                            + getAlbumName()
            );

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraSample", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            Log.v(getString(R.string.app_name), "External storage is not mounted READ/WRITE.");
        }

        return storageDir;
    }
    private File setUpPhotoFile(String nom_photo) throws IOException {

        File f = createImageFile(nom_photo);
        mCurrentPhotoPath = f.getAbsolutePath();

        return f;
    }
    private File createImageFile(String nom_photo) throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName=null;
        if(nom_photo==null){
            imageFileName=imageFileName = JPEG_FILE_PREFIX + timeStamp;
        }else{
            imageFileName=nom_photo;
        }
        File albumF = getAlbumDir();
        File imageF = new File(albumF,imageFileName+JPEG_FILE_SUFFIX);
        return imageF;
    }
    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);

        helpBuilder.setTitle("Validation");
        LayoutInflater inflater = this.getLayoutInflater();
        final View alertDialogView = inflater.inflate(R.layout.popupphoto_layout, null);
        helpBuilder.setView(alertDialogView);
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText_nom= (EditText) alertDialogView.findViewById(R.id.popup_nom);
                        EditText editText_com= (EditText) alertDialogView.findViewById(R.id.popup_commentaire);

                        nom_photo=editText_nom.getText().toString();
                        commentaire_photo=editText_com.getText().toString();
                       /* Intent in=getIntent();
                        Bundle b=in.getExtras();
                        Double lat=null;
                        Double longi=null;
                        if(b != null){
                            lat=b.getDouble("latitude");
                            longi=b.getDouble("longitude");
                        }
*/

                        PhotoCapture photo=new PhotoCapture(nom_photo, file.getAbsolutePath() ,commentaire_photo,lat,lon);
                        saveInBase(photo);
                        finish();
                    }
                });
        helpBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
            }
        });

        // Remember, create doesn't show the dialog
        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    private void saveInBase(PhotoCapture photo){
        PhotoDataBase db= new PhotoDataBase(this);
        db.open();
        db.insert(photo);
        db.displayArticles();
        db.close();
    }
    protected void onActivityResult (int requestCode, int resultCode, Intent data) {
        // on récupère le statut de retour de l'activité 2 c'est à dire l'activité numéro 1000
        Intent mediaScanIntent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
        mCurrentPhotoPath = null;
        showSimplePopUp();
    }

}
