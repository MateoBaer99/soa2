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
public class Produit {
    private UUID id;
    private double prix;
    private String name;
    private String description;
    private float poids;

    public Produit() {
        this.id=UUID.randomUUID();
    }
    
    public UUID getId() {
        return id;
    }

    public double getPrix() {
        return prix;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPoids() {
        return poids;
    }

    public Produit prix(final double value) {
        this.prix = value;
        return this;
    }

    public Produit name(final String value) {
        this.name = value;
        return this;
    }

    public Produit description(final String value) {
        this.description = value;
        return this;
    }

    public Produit poids(final float value) {
        this.poids = value;
        return this;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }
   
}
