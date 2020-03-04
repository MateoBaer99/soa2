/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.util;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.universite.ventes.api.model.CommandeRes;
import org.universite.ventes.api.model.LigneCommandeRes;
import org.universite.ventes.domain.Commande;
import org.universite.ventes.api.impl.CommandesResourceRAM;
import org.universite.ventes.api.model.AdresseRes;
import org.universite.ventes.api.model.ClientRes;
import org.universite.ventes.api.model.ProduitRes;
import org.universite.ventes.domain.Adresse;
import org.universite.ventes.domain.Client;
import org.universite.ventes.domain.LigneCommande;
import org.universite.ventes.domain.Produit;

/**
 *
 * @author akriks Classe utilitaire pour convertir les objets du domaine en
 * ressources api et inversement
 */
public class Utility {

    public static Commande toDomain(CommandeRes res) {
        Commande cde = new Commande();
        if (null != res.getIdentifiant()) {
            cde.setId(res.getIdentifiant());
        }
        cde.setClient(CommandesResourceRAM.clients.get(res.getClient().getIdentifiant()));
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

    private static Client toDomain(ClientRes res) {
        Client client = new Client();
        client.identifiant(res.getIdentifiant())
                .nom(res.getNom())
                .vip(res.getVip())
                .tel(res.getTel())
                .mail(res.getMail())
                .adresseFacturation(toDomain(res.getAdresseFacturation()))
                .adresseLivraison(toDomain(res.getAdresseLivraison()));
        return client;
    }

    private static Adresse toDomain(AdresseRes res) {
        Adresse adresse = new Adresse();
        adresse.numeroVoie(res.getNumeroVoie())
                .typeVoie(Adresse.TypeVoieEnum.valueOf(res.getTypeVoie()))
                .nomVoie(res.getNomVoie())
                .codePostal(res.getCodePostal())
                .commune(res.getCommune());
        return adresse;
    }

    public static CommandeRes toResource(Commande cde) {
        CommandeRes cdeRes = new CommandeRes();
        cdeRes.identifiant(cde.getId())
                .date(cde.getDate())
                .client(toResource(cde.getClient()))
                .montant(cde.getMontant())
                .lignes(cde.getLignes().stream()
                        .map((LigneCommande ligne) -> toResource(ligne))
                        .collect(Collectors.toList()));
        return cdeRes;
    }

    public static LigneCommandeRes toResource(LigneCommande ligne) {
        LigneCommandeRes ligneRes = new LigneCommandeRes();
        ligneRes.produit(toResource(ligne.getProduit()))
                .quantite(ligne.getQte())
                .montant(ligne.getMontant());
        return ligneRes;
    }

    public static ProduitRes toResource(Produit produit) {
        ProduitRes produitRes = new ProduitRes();
        produitRes.setIdentifiant(produit.getId());
        produitRes.setNom(produit.getName());
        produitRes.setDescription(produit.getDescription());
        produitRes.setPoids(produit.getPoids());
        produitRes.setPrix(produit.getPrix());
        return produitRes;
    }

    private static ClientRes toResource(Client client) {
        ClientRes clientRes = new ClientRes();
        if (null!=client)  {
            clientRes.setIdentifiant(client.getIdentifiant());
            clientRes.setNom(client.getNom());
            clientRes.setVip(client.isVip());
            clientRes.setTel(client.getTel());
            clientRes.setMail(client.getMail());
            clientRes.setAdresseFacturation(toResource(client.getAdresseFacturation()));
            clientRes.setAdresseLivraison(toResource(client.getAdresseLivraison()));
        }
        return clientRes;
    }

    private static AdresseRes toResource(Adresse adresse) {
        AdresseRes res = new AdresseRes();
        if (null!=adresse) {
            res.setNumeroVoie(adresse.getNumeroVoie());
            res.setTypeVoie(AdresseRes.TypeVoieEnum.valueOf(adresse.getTypeVoie().toString()));
            res.setNomVoie(adresse.getNomVoie());
            res.setCodePostal(adresse.getCodePostal());
            res.setCommune(adresse.getCommune());
        }
        return res;
    }    

}
