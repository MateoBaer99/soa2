/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.util;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.universite.ventes.api.model.CommandeRes;
import org.universite.ventes.api.model.LigneCommandeRes;
import org.universite.ventes.domain.Commande;
import org.universite.ventes.api.impl.CommandesResourceRAM;
import org.universite.ventes.api.model.ProduitRes;
import org.universite.ventes.domain.LigneCommande;
import org.universite.ventes.domain.Produit;

/**
 *
 * @author akriks
 * Classe utilitaire pour convertir les objets du domaine en ressources api et inversement
 */
public class Utility {
    
    public static Commande toDomain(CommandeRes res) {
        Commande cde=new Commande();
        if (null!=res.getIdentifiant()) cde.setId(res.getIdentifiant());
        cde.date(res.getDate());
        cde.setLignes(res.getLignes().stream()
                                     .map((LigneCommandeRes ligneRes) -> toDomain(ligneRes))
                                     .collect(Collectors.toList()));
        return cde;
    }

    public static LigneCommande toDomain(LigneCommandeRes res) {
        
        return new LigneCommande().produit(CommandesResourceRAM.produits.get(res.getProduit().getIdentifiant()))
                                  .qte(res.getQuantite());
               
    }

    public static CommandeRes toResource(Commande cde) {
        CommandeRes cdeRes=new CommandeRes();
        cdeRes.identifiant(cde.getId())
              .date(cde.getDate())
              .montant(cde.getMontant())
              .lignes(cde.getLignes().stream()
                                     .map((LigneCommande ligne) -> toResource(ligne))
                                     .collect(Collectors.toList()));
        return cdeRes;        
    }
    
    public static LigneCommandeRes toResource(LigneCommande ligne) {
       LigneCommandeRes ligneRes = new LigneCommandeRes();
       ligneRes.produit(Utility.toResource(ligne.getProduit()))
               .quantite(ligne.getQte())
               .montant(ligne.getMontant());
       return ligneRes;             
    }
    
    public static ProduitRes toResource(Produit produit) {
       ProduitRes produitRes=new ProduitRes();
       produitRes.setIdentifiant(produit.getId());
       produitRes.setNom(produit.getName());
       produitRes.setDescription(produit.getDescription());
       produitRes.setPoids(produit.getPoids());
       produitRes.setPrix(produit.getPrix());
       return produitRes;
     }
}
