package com.example.anthony.projetdesignpater;

import android.os.Environment;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Vince on 02/02/2016.
 */
public class PhotoCapture {
    private String nom;
    private String commentaire;
    private String lien;
    private String Date;
    private String localisation_latitude;
    private String localisation_longitude;


    public PhotoCapture(String nom, String lien, String commentaire, Double latitude, Double longitude) {
        this.nom = nom;
        this.commentaire = commentaire;
        this.lien= Environment.getExternalStorageDirectory()
                /*+ "/dcim/DesignPaterRepository"*/+lien;
        this.Date=new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        this.localisation_latitude=String.valueOf(latitude);
        this.localisation_longitude=String.valueOf(longitude);


            /*
            Location location = null;
            double currentLatitude = location.getLatitude();
            double currentLongitude = location.getLongitude();

            System.out.println(currentLatitude);
            System.out.println(currentLongitude);*/

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getLocalisation_latitude() {
        return localisation_latitude;
    }

    public void setLocalisation_latitude(String localisation_latitude) {
        this.localisation_latitude = localisation_latitude;
    }

    public String getLocalisation_longitude() {
        return localisation_longitude;
    }

    public void setLocalisation_longitude(String localisation_longitude) {
        this.localisation_longitude = localisation_longitude;
    }


}
