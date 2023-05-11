package com.example.provalogin.Model;

public class Animal {

    public String ImgUrl;
    public String id;
    public String nomeanimale;
    private String etaanimale;
    private String sessoa;
    private String chipanimale;
    private String sterilizzazioneanimale;
    private String padroneanimale;
    public String specieanimale;
    private String preferenzacibo;
    private String saluteanimale;

    public Animal () {

        // costruttore di default senza argomenti
    }

    public Animal(String id, String nomeanimale, String etaanimale, String sessoa, String chipanimale, String sterilizzazioneanimale, String padroneanimale, String specieanimale, String preferenzacibo, String saluteanimale) {
        this.id = id;
        this.nomeanimale = nomeanimale;
        this.etaanimale = etaanimale;
        this.sessoa = sessoa;
        this.chipanimale = chipanimale;
        this.sterilizzazioneanimale = sterilizzazioneanimale;
        this.padroneanimale = padroneanimale;
        this.specieanimale = specieanimale;
        this.preferenzacibo = preferenzacibo;
        this.saluteanimale = saluteanimale;
    }

}
