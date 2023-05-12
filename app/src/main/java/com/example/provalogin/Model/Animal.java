package com.example.provalogin.Model;

public class Animal {

    public String ImgUrl;
    public String id;
    public String nome_animale;
    private String eta_animale;
    private String sesso_animale;
    private String chip_animale;
    private String sterilizzazione_animale;
    private String padrone_animale;
    public String specie_animale;
    private String preferenza_animale;
    private String salute_animale;

    public Animal () {

        // costruttore di default senza argomenti
    }

    public Animal(String id, String nomeanimale, String etaanimale, String sessoa, String chipanimale, String sterilizzazioneanimale, String padroneanimale, String specieanimale, String preferenzacibo, String saluteanimale) {
        this.id = id;
        this.nome_animale = nomeanimale;
        this.eta_animale = etaanimale;
        this.sesso_animale = sessoa;
        this.chip_animale = chipanimale;
        this.sterilizzazione_animale = sterilizzazioneanimale;
        this.padrone_animale = padroneanimale;
        this.specie_animale = specieanimale;
        this.preferenza_animale = preferenzacibo;
        this.salute_animale = saluteanimale;
    }

}
