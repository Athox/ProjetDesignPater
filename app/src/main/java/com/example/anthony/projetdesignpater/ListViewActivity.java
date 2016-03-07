package com.example.anthony.projetdesignpater;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Vince on 09/02/2016.
 */
public class ListViewActivity extends ActionBarActivity {
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listview_layout);





    }

    @Override
    protected void onResume() {
        super.onResume();
        /*mListView = (ListView) findViewById(R.id.listView);*/
        final LinkedList<PhotoCapture> photos=listePhoto();
        //System.out.println(photos.get(1).getLien());
        //System.exit(0);
        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(ListViewActivity.this,
                android.R.layout.simple_list_item_1, getNom(photos));
        mListView.setAdapter(adapter);*/

        setContentView(R.layout.listview_layout);

        CustomList adapter = new
                CustomList(ListViewActivity.this, photos,getNom(photos));
        mListView=(ListView)findViewById(R.id.listView);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ListViewActivity.this, "You Clicked at " + photos.get(+position).getNom(), Toast.LENGTH_SHORT).show();

            }
        });
        Button button_camera = (Button) findViewById(R.id.button_camera);
        button_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ArrayList<Double> geoLoc=coordonneeGPS();

                //Bundle b = new Bundle();

                //b.putDouble("latitude", geoLoc.get(0));
                //b.putDouble("longitude", geoLoc.get(1));
                Intent in = new Intent(ListViewActivity.this, CameraActivity.class);
                //in.putExtras(b);
                startActivity(in);
            }
        });

    }



    private LinkedList<PhotoCapture> listePhoto(){
        PhotoDataBase db= new PhotoDataBase(this);
        db.open();
        LinkedList<PhotoCapture> photos= db.selectAll();
        String lien = photos.get(0).getLien();
        db.displayArticles();
        db.close();
        return photos;
    }
    private ArrayList<String> getNom(LinkedList<PhotoCapture> photos){
        ArrayList<String> noms = new ArrayList<String>();
        for(PhotoCapture photo : photos){
            //System.out.println(photo.getNom());
            //System.exit(0);
            noms.add(photo.getNom());
        }

        return noms;
    }
}

