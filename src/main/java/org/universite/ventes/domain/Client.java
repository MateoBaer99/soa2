/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.domain;
 
import java.util.UUID;

/**
 *
 * @author PAKI6340
 */ 
public class Client {
    
    private UUID identifiant;
    private String nom;
    private Adresse adresseLivraison;
    private Adresse adresseFacturation;
    private String tel;
    private String mail;
    private boolean vip;

    public UUID getIdentifiant() {
        return identifiant;
    }

    public String getNom() {
        return nom;
    }

    public Adresse getAdresseLivraison() {
        return adresseLivraison;
    }
    
    public Adresse getAdresseFacturation() {
        return adresseFacturation;
    }

    public String getTel() {
        return tel;
    }

    public String getMail() {
        return mail;
    }

    public boolean isVip() {
        return vip;
    }

    public Client identifiant(final UUID value) {
        this.identifiant = value;
        return this;
    }

    public Client nom(final String value) {
        this.nom = value;
        return this;
    }

    public Client adresseLivraison(final Adresse value) {
        this.adresseLivraison = value;
        return this;
    }
    public Client adresseFacturation(final Adresse value) {
        this.adresseFacturation = value;
        return this;
    }
    public Client tel(final String value) {
        this.tel = value;
        return this;
    }

    public Client mail(final String value) {
        this.mail = value;
        return this;
    }

    public Client vip(final boolean value) {
        this.vip = value;
        return this;
    }


    
}
