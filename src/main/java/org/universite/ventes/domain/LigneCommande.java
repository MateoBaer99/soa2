/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.domain;
 
/**
 *
 * @author akriks
 */
public class LigneCommande {
    private int qte;
    private Produit produit;
    
     public LigneCommande(){
    }
    public LigneCommande(Produit produit, int qte) {
        this.produit=produit;
        this.qte=qte;
    }   

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    public double getMontant() {
        return qte*produit.getPrix();
    }   

    public LigneCommande qte(final int value) {
        this.qte = value;
        return this;
    }

    public LigneCommande produit(final Produit value) {
        this.produit = value;
        return this;
    }
    

}
