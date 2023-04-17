package com.example.provalogin.Model;

public class User {


    private String Nome;
    private String Cognome;
    private String TipoUtente;
    private String id;
    private String imageurl;
    private String bio;

    public User(String id, String username, String fullname, String imageurl, String bio) {
        this.id = id;
        this.Nome = username;
        this.Cognome = fullname;
        this.imageurl = imageurl;
        this.bio = bio;
    }

    public User(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return Nome;
    }

    public void setUsername(String username) {
        this.Nome = username;
    }

    public String getFullname() {
        return Cognome;
    }

    public void setFullname(String fullname) {
        this.Cognome = fullname;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
