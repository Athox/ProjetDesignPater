package com.example.anthony.projetdesignpater;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class PhotoDataBase {
    private static final int DATABASE_VERSION = 1;
    private static final String DBNAME = "geophoto.db";
    public static final String PHOTO_TABLE_NAME = "Photo";

    public static final String PHOTO_KEY = "id";
    public static final String PHOTO_NOM = "nom";
    public static final String PHOTO_COMMENTAIRE = "commentaire";
    public static final String PHOTO_lIEN = "lien";
    public static final String PHOTO_DATE = "date";
    public static final String PHOTO_LOCALISATION_LATITUDE= "latitude";
    public static final String PHOTO_LOCALISATION_LONGITUDE= "longitude";


    private SQLiteDatabase bdd;
    private DataBase photoBaseSQLite;

    public PhotoDataBase(Context context){
        photoBaseSQLite = new DataBase(context, DBNAME, null, DATABASE_VERSION);
    }

    public void open(){
        bdd = photoBaseSQLite.getWritableDatabase();
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insert(PhotoCapture photo){
        ContentValues values = new ContentValues();
        values.put(PHOTO_NOM, photo.getNom());
        values.put(PHOTO_COMMENTAIRE, photo.getCommentaire());
        values.put(PHOTO_DATE, photo.getDate());
        values.put(PHOTO_lIEN, photo.getLien());
        values.put(PHOTO_LOCALISATION_LATITUDE, photo.getLocalisation_latitude());
        values.put(PHOTO_LOCALISATION_LONGITUDE, photo.getLocalisation_longitude());
        return bdd.insert(PHOTO_TABLE_NAME, null, values);
    }

    public int update(int id, PhotoCapture photo){
        ContentValues values = new ContentValues();
        values.put(PHOTO_NOM, photo.getNom());
        values.put(PHOTO_COMMENTAIRE, photo.getCommentaire());
        values.put(PHOTO_DATE, photo.getDate());
        values.put(PHOTO_lIEN, photo.getLien());
        values.put(PHOTO_LOCALISATION_LATITUDE, photo.getLocalisation_latitude());
        values.put(PHOTO_LOCALISATION_LONGITUDE, photo.getLocalisation_longitude());
        return bdd.update(PHOTO_TABLE_NAME, values, PHOTO_KEY + " = " +id, null);
    }

    public int remove(int id){
        return bdd.delete(PHOTO_TABLE_NAME, PHOTO_KEY + " = " +id, null);
    }

    public PhotoCapture selectWithID(String[] args) {
        Cursor cursor = bdd.rawQuery("SELECT * from " + PHOTO_TABLE_NAME + " WHERE id = ? ", args);
        PhotoCapture photo = cursorToArticle(cursor, false);
        cursor.close();
        return photo;
    }

    public LinkedList<PhotoCapture> selectAll(){
        LinkedList<PhotoCapture> photos = new LinkedList<PhotoCapture>();
        Cursor cursor = bdd.rawQuery("SELECT "+PHOTO_NOM+","+PHOTO_lIEN+","+PHOTO_COMMENTAIRE+","+PHOTO_LOCALISATION_LATITUDE+","+PHOTO_LOCALISATION_LONGITUDE+" from " + PHOTO_TABLE_NAME, null);
        if(cursor.getCount() != 0){
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                PhotoCapture photo = cursorToArticle(cursor, true);
                photos.add(photo);
            }
        }
        cursor.close();
        return photos;
    }

    public void displayArticles() {
        LinkedList<PhotoCapture> photo = selectAll();
        for (int i = 0; i < photo.size(); i++) {
            System.out.println(photo.get(i));
        }
    }

    private PhotoCapture cursorToArticle(Cursor c, boolean multipleResult){
        if(!multipleResult) {
            c.moveToFirst();
        }
        PhotoCapture photo = new PhotoCapture(c.getString(0),c.getString(1),c.getString(1),c.getDouble(1),c.getDouble(1));

        return photo;
    }
}