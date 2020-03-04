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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.springframework.stereotype.Component;
import org.universite.ventes.util.Utility;
import org.universite.ventes.api.GestionDesCommandesApi;
import org.universite.ventes.api.model.CommandeRes;
import org.universite.ventes.domain.Adresse.TypeVoieEnum;
import org.universite.ventes.exceptions.AppliException;
import org.universite.ventes.exceptions.InformationsCommandesException;
import org.universite.ventes.exceptions.ProduitsInconnusException;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response.Status;
import org.universite.ventes.exceptions.CommandeInconnueException;

/**
 *
 * @author PAKI6340
 */
@Component("GestionDesCommandesApi")
public class CommandesResourceRAM implements GestionDesCommandesApi{
    
    @Context UriInfo uriInfo;
  
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
        Client client = new Client().identifiant("cccccccc-cccc-cccc-cccc-cccccccc")
                             .nom("Larivee")
                             .mail("jerome.larivee@univ-tlse.fr")
                             .tel("33609090909")
                             .vip(false)
                             .adresseLivraison(adresse);
        clients.put(client.getIdentifiant(), client);
        
        // Création de produits avec forçage des identifiants pour simplifier les tests
        Produit prod1=new Produit().name("Cutter4X").description("Cutter fer 4 lames").poids((float) 0.2).prix(23.34);
        prod1.setId(UUID.fromString("fd5362a7-6eb9-4786-ba3d-5fd4fa711cb8"));
        produits.put(prod1.getId(), prod1);
        Produit prod2=new Produit().name("CutterPs").description("Cutter plastique 1ier prix").poids((float) 0.1).prix(5.30);
        prod2.setId(UUID.fromString("298ab06e-1750-4b9c-80ba-d65879616659"));
        produits.put(prod2.getId(), prod2);
        Produit prod3=new Produit().name("CiseauxXT").description("Ciseaux carton").poids((float) 0.15).prix(13.34);
        prod3.setId(UUID.fromString("841f7963-04a6-479b-ae28-964436abf374"));
        produits.put(prod3.getId(), prod3);        
        Produit prod4=new Produit().name("MasseH100").description("Masse professionnelle").poids((float) 1.5).prix(128.55);
        prod4.setId(UUID.fromString("41a54374-b77c-4bb1-946c-7e1dec94d890"));
        produits.put(prod4.getId(), prod4); 
        Produit prod5=new Produit().name("MarteauTR2").description("Marteau de charpentier").poids((float) 1.0).prix(45);
        prod5.setId(UUID.fromString("3bcd447c-5d85-4efc-9826-ede4d5c0a505"));
        produits.put(prod5.getId(), prod5);
        Produit prod6=new Produit().name("CrayonXX124").description("Crayon mine bois pro").poids((float) 0.05).prix(2.5);
        prod6.setId(UUID.fromString("3bcd447c-5d85-4efc-9826-ede4d5c0a408"));
        produits.put(prod6.getId(), prod6);
        
       
        // Création d'1 commande de 3 produits                
        Commande com1=new Commande().client(client)
                           .date(LocalDate.of(2019, 03, 13))
                           .addLigne(prod1,5)
                           .addLigne(prod3,8)
                           .addLigne(prod5,20);
        commandes.put(com1.getId(),com1);
        
        //Création d'1 autre commande avec 2 produits
        Commande com2=new Commande().client(client)
                           .date(LocalDate.of(2020, 01, 18))
                           .addLigne(prod3,1)
                           .addLigne(prod6,10);
        commandes.put(com2.getId(),com2);        
    }
    
    

    @Override
    public Response creerCommande(CommandeRes commandeRes) {
        Commande cde;
        CommandeRes cdeRes;
        //on vérifie que les produits commandés existent bien*
         try {
            
            if (commandeRes.getLignes().size()!= 
                    commandeRes.getLignes().stream()
                            .filter(lc -> produits.containsKey(lc.getProduit().getIdentifiant()))
                            .count()) {
                throw new ProduitsInconnusException();
            }
            //On affecte le produit du catalogue
            cde=Utility.toDomain(commandeRes);
            commandes.put(cde.getId(),cde);
            cdeRes=Utility.toResource(cde);
   
        } catch (Exception e) {
            if (e instanceof AppliException) {
                throw e;
            } else {
                throw new InformationsCommandesException(e);
            }
        }

       List<Link> links=addLinks(cde);

       return Response.ok(cdeRes)
                      .status(Status.CREATED)
                      .links(links.toArray(new Link[links.size()]))
                      .build();
    }    

    @Override
    public Response getCommandes() {    
        GenericEntity<List<CommandeRes>> cdeRes =
           new GenericEntity<List<CommandeRes>>(commandes.values()
                                                      .stream()
                                                      .map(cde -> Utility.toResource(cde))
                                                      .collect(Collectors.toList())) {};    
        List<Link> links=
           new ArrayList<Link>(commandes.values().stream().map(cde -> 
                                        Link.fromUriBuilder(uriInfo.getBaseUriBuilder()
                                                                   .path(GestionDesCommandesApi.class)
                                                                   .path(GestionDesCommandesApi.class, "getCommande"))
                                            .rel("details")
                                            .build(cde.getId()))
                                   .collect(Collectors.toList())) {};
                                     
        return Response.ok(cdeRes)
                       .links(links.toArray(new Link[links.size()]))
                       .build();
    }
    
    @Override
    public Response getCommande(String identifiant) {
        Commande cde;
        CommandeRes cdeRes;
        try {
            cde=commandes.get(UUID.fromString(identifiant));
            cdeRes=Utility.toResource(cde);
        } catch (Exception e) {
                throw new CommandeInconnueException(e);
        }
        List<Link> links=addLinks(cde);
                                     
        return Response.ok(cdeRes)
                       .links(links.toArray(new Link[links.size()]))
                       .build();        
    }
    
    @Override
    public Response deleteCommande(String identifiant) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    
    private List<Link> addLinks(Commande cde) {
                List<Link> links=new ArrayList<>();
        links.add(Link.fromUriBuilder(uriInfo.getRequestUriBuilder())
                      .rel("self")
                      .build());
        links.add(Link.fromUriBuilder(uriInfo.getBaseUriBuilder()
                                             .path(GestionDesCommandesApi.class)
                                             .path(GestionDesCommandesApi.class,"getCommandes"))
                      .rel("collection")
                      .build());
        // On met le link delete uniquement si c possible (montant commande < 500
        if (cde.isSupprimable()) {
             links.add(Link.fromUriBuilder(uriInfo.getBaseUriBuilder()
                                                  .path(GestionDesCommandesApi.class)
                                                  .path(GestionDesCommandesApi.class, "deleteCommande"))
                           .rel("delete")
                           .build(cde.getId()));    
        }
        return links;
    }
}
