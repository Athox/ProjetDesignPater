package com.example.anthony.projetdesignpater;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Vince on 02/02/2016.
 */
public class DataBase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    public static final String PHOTO_KEY = "id";
    public static final String PHOTO_NOM = "nom";
    public static final String PHOTO_COMMENTAIRE = "commentaire";
    public static final String PHOTO_lIEN = "lien";
    public static final String PHOTO_DATE = "date";
    public static final String PHOTO_LOCALISATION_LATITUDE= "latitude";
    public static final String PHOTO_LOCALISATION_LONGITUDE= "longitude";
    public static final String PHOTO_TABLE_NAME = "Photo";
    public static final String PHOTO_TABLE_CREATE =
            "CREATE TABLE " + PHOTO_TABLE_NAME + " (" +
                    PHOTO_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PHOTO_NOM + " TEXT, " +
                    PHOTO_DATE + " TEXT, " +
                    PHOTO_lIEN + " TEXT, " +
                    PHOTO_LOCALISATION_LATITUDE + " TEXT, " +
                    PHOTO_LOCALISATION_LONGITUDE + " TEXT, " +
                    PHOTO_COMMENTAIRE + " TEXT);";

    public DataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(PHOTO_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Lorsque l'on change le numéro de version de la base on supprime la
        // table puis on la recrée
        if (newVersion > DATABASE_VERSION) {
            db.execSQL("DROP TABLE " + PHOTO_TABLE_NAME + ";");
            onCreate(db);
        }
    }
}