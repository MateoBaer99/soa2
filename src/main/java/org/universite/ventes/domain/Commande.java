/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package org.universite.ventes.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author PAKI6340
 */
@XmlRootElement (name= "Commande")
public class Commande {
    
    private UUID id;
    private LocalDate date;
    private List<LigneCommande> lignes; 
    

    
    public Commande() {
        this.id=UUID.randomUUID();
        lignes=new ArrayList<>();
    }
    
    
    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public List<LigneCommande> getLignes() {
        return lignes;
    }


    public Commande date(final LocalDate value) {
        this.date = value;
        return this;
    }

    public Commande addLigne(Produit produit,int qte)  {
        lignes.add(new LigneCommande(produit,qte));
        return this;
    }
    
    public double getMontant() {
        double montant=lignes.stream()
                         .mapToDouble(x -> x.getMontant())
                         .sum();
        return montant;
    }   

    public void setId(UUID id) {
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setLignes(List<LigneCommande> lignes) {
        this.lignes = lignes;
    }
    
    public boolean isSupprimable() {
        return getMontant()<500;
    }
}
