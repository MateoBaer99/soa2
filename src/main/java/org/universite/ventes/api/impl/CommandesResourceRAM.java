/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.api.impl;

import org.universite.ventes.domain.Commande;
import org.universite.ventes.domain.Adresse;
import org.universite.ventes.domain.Client;
import org.universite.ventes.domain.Produit;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import org.springframework.stereotype.Component;
import org.universite.ventes.util.Utility;
import org.universite.ventes.api.GestionDesCommandesApi;
import org.universite.ventes.api.model.CommandeRes;
import org.universite.ventes.domain.Adresse.TypeVoieEnum;


/**
 *
 * @author PAKI6340
 */
@Component("GestionDesCommandesApi")
public class CommandesResourceRAM implements GestionDesCommandesApi{
  
    //Initialisation de données en RAM
    public static Map<String,Client> clients=new HashMap<>();
    public static Map<UUID,Commande> commandes=new HashMap<>();
    public static Map<UUID,Produit> produits=new HashMap<>();
        
    static {
       // Initialisation d'un Client
        Adresse adresse=new Adresse().numeroVoie(12)
                                     .typeVoie(TypeVoieEnum.RUE)
                                     .nomVoie("des allouettes")
                                     .codePostal(31000)
                                     .commune("Toulouse");
        Client client = new Client().identifiant("45874AV")
                             .nom("Larivee")
                             .mail("jerome.larivee@univ-tlse.fr")
                             .tel("33609090909")
                             .vip(false)
                             .adresseLivraison(adresse);
        clients.put(client.getIdentifiant(), client);
        
        // Création de produits
        Produit prod1=new Produit().name("Cutter4X").description("Cutter fer 4 lames").poids((float) 0.2).prix(23.34);
        produits.put(prod1.getId(), prod1);
        Produit prod2=new Produit().name("CutterPs").description("Cutter plastique 1ier prix").poids((float) 0.1).prix(5.30);
        produits.put(prod2.getId(), prod2);
        Produit prod3=new Produit().name("CiseauxXT").description("Ciseaux carton").poids((float) 0.15).prix(13.34);
        produits.put(prod3.getId(), prod3);        
        Produit prod4=new Produit().name("MasseH100").description("Masse professionnelle").poids((float) 1.5).prix(128.55);
        produits.put(prod4.getId(), prod4); 
        Produit prod5=new Produit().name("MarteauTR2").description("Marteau de charpentier").poids((float) 1.0).prix(45);
        produits.put(prod5.getId(), prod5);
        Produit prod6=new Produit().name("CrayonXX124").description("Crayon mine bois pro").poids((float) 0.05).prix(2.5);
        produits.put(prod6.getId(), prod6);
        
       
        // Création d'1 commande de 3 produits                
        Commande com1=new Commande().date(LocalDate.of(2019, 03, 13))
                           .addLigne(prod1,5)
                           .addLigne(prod3,8)
                           .addLigne(prod5,20);
        commandes.put(com1.getId(),com1);
        
        //Création d'1 autre commande avec 2 produits
        Commande com2=new Commande().date(LocalDate.of(2020, 01, 18))
                           .addLigne(prod3,1)
                           .addLigne(prod6,10);
        commandes.put(com2.getId(),com2);        
    }
    
    

    @Override
    public CommandeRes creerCommande(CommandeRes commandeRes) {
        Commande cde;
        //on vérifie que les produits commandés existent bien*
         try {
            if (commandeRes.getLignes().size()== commandeRes.getLignes().stream()
                                                                  .filter(lc -> produits.containsKey(lc.getProduit().getIdentifiant()))
                                                                  .count()) { 
            } else {
                throw new WebApplicationException("Produits commandés non valides : inconnus au catalogue",Response.Status.BAD_REQUEST);
            }
            //On affecte le produit du catalogue
            cde=Utility.toDomain(commandeRes);
            commandes.put(cde.getId(),cde);
   
        } catch (NullPointerException ne) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST)
                                                      .entity("{\"Erreur\": \"Infos commandes incorrectes\"}")
                                                      .type("application/json")
                                                      .build());
        }


//        commandes.put(commande.getId(),commande);
       // return Response.created(URI.create("/commandes/"+commande.getId())).build();
       return Utility.toResource(cde);

    }    

    @Override
    public CommandeRes getCommande(String identifiant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<CommandeRes> getCommandes() {
         return (List<CommandeRes>) commandes.values()
                                             .stream()
                                             .map(cde -> Utility.toResource(cde))
                                             .collect(Collectors.toList());
    }
}
