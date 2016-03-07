package com.example.anthony.projetdesignpater;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Vince on 12/02/2016.
 */
public class CustomList extends ArrayAdapter<String> {

        private final Activity context;
        private LinkedList<PhotoCapture> photos;
        String[] nom;
        public CustomList(Activity context, LinkedList<PhotoCapture> photos, ArrayList<String> noms) {
            super(context, R.layout.list_single,noms);
            this.context = context;
            this.photos=photos;

        }
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView= inflater.inflate(R.layout.list_single, null, true);
            TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
            txtTitle.setText(photos.get(position).getNom());
            //System.out.println(photos.get(position).getLien());
            //System.exit(0);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inSampleSize = 10;
            String imageInSD = photos.get(position).getLien();
            //bmOptions.inJustDecodeBounds = true;
            Bitmap bitmap = BitmapFactory.decodeFile(imageInSD);
           // BitmapFactory.decodeFile(photos.get(position).getLien(), bmOptions);

            //bmOptions.inJustDecodeBounds = false;
            //bmOptions.inPurgeable = true;

		/* Decode the JPEG file into a Bitmap */


		/* Associate the Bitmap to the ImageView */
            imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,50,50,false));



            return rowView;
        }
}
