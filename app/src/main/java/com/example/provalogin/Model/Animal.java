
package com.example.provalogin.Model;

public class Animal {


    public String id;
    public String imgAnimale;
    public String nomeAnimale;
    private String eta;
    private String sesso;
    private String chip;
    private String sterilizzazione;
    public String padrone;
    public String specie;
    private String preferenzaCibo;
    private String statoSalute;

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







