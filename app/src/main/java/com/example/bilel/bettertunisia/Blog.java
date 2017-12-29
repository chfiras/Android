package com.example.firas.bettertunisia;

/**
 * Created by firas on 07/11/2016.
 */

public class Blog {
    private String Catégorie;
    private String Description;
    private String Localisation;
    private String identifiant;
    private String image;

    public Blog()
    {

    }

    public Blog(String Catégorie, String Description, String identifiant,String Localisation, String image) {
        this.Catégorie = Catégorie;
        this.Description = Description;
        this.Localisation = Localisation;
        this.identifiant = identifiant;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIdentifiant() {
        return "Email :"+identifiant;
    }

    public void setIdentifiant(String Identifiant) {
        this.identifiant = Identifiant;
    }

    public String getLocalisation() {
        return "Localisation :"+Localisation;
    }

    public void setLocalisation(String Localisation) {
        this.Localisation = Localisation;
    }

    public String getDescription() {
        return "Description :"+Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getCatégorie() {
        return "Catégorie :"+Catégorie;
    }

    public void setCatégorie(String Catégorie) {
        this.Catégorie = Catégorie;
    }




}
