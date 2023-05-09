package com.example.provalogin.Model;

public class Segnalazioni {
    String email;
    String città;
    String tipoSegnalazione;
    String surl;

    Segnalazioni(){

    }

    public Segnalazioni(String email, String città, String tipoSegnalazione, String surl) {
        this.email = email;
        this.città = città;
        this.tipoSegnalazione = tipoSegnalazione;
        this.surl = surl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public String getTipoSegnalazione() {
        return tipoSegnalazione;
    }

    public void setTipoSegnalazione(String tipoSegnalazione) {
        this.tipoSegnalazione = tipoSegnalazione;
    }

    public String getSurl() {
        return surl;
    }

    public void setSurl(String surl) {
        this.surl = surl;
    }
}
