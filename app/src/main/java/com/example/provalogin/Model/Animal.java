
package com.example.provalogin.Model;

import java.io.Serializable;

public class Animal implements Serializable {


    public String id;
    public String imgAnimale;
    public String nomeAnimale;
    public String eta;
    public String sesso;
    public String chip;
    public String sterilizzazione;
    public String padrone;
    public String specie;
    public String preferenzaCibo;
    public String statoSalute;

    public Animal () {

        // costruttore di default senza argomenti
    }
    // Getter e setter per altre proprietà della classe Animal

    public String getOwnerId() {
        return id;
    }

    public void setOwnerId(String id) {
        this.id = id;
    }


}







