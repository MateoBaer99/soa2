/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.api.impl;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.Link;
import javax.ws.rs.core.UriInfo;
import org.universite.ventes.api.GestionDesCommandesApi;
import org.universite.ventes.api.model.CommandeRes;
import org.universite.ventes.domain.Commande;
import org.universite.ventes.util.Utility;

/**
 *
 * @author akriks
 */
class CommandeLink {

    static List<Link> add(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {

        List<Link> links=new ArrayList<>();
        UriInfo uriInfo=requestContext.getUriInfo();
        Object entity=responseContext.getEntity();


        if (null==entity) {
            return links;
        }
        //Links pour getCommande() et creerCommande()
        if (entity instanceof CommandeRes){
            Commande cde=Utility.toDomain((CommandeRes)entity);
            links.add(Link.fromUriBuilder(uriInfo.getRequestUriBuilder()
                                                .path(cde.getId().toString()))
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
               
        //Links pour getCommandes()
        } else if (entity instanceof List) {
            links.add(Link.fromUriBuilder(uriInfo.getBaseUriBuilder()
                                                 .path(GestionDesCommandesApi.class)
                                                 .path(GestionDesCommandesApi.class, "getCommandes"))
                .rel("self")
                .build());   
            List<CommandeRes> commandesRes=(List<CommandeRes>) entity;
            commandesRes.forEach((cdeRes) -> {
                links.add(Link.fromUriBuilder(uriInfo.getBaseUriBuilder()
                                                    .path(GestionDesCommandesApi.class)
                                                    .path(GestionDesCommandesApi.class, "getCommande"))
                    .rel("details")
                    .build(Utility.toDomain(cdeRes).getId()));    
                if (Utility.toDomain(cdeRes).isSupprimable()) {
                   links.add(Link.fromUriBuilder(uriInfo.getBaseUriBuilder()
                                                    .path(GestionDesCommandesApi.class)
                                                    .path(GestionDesCommandesApi.class, "deleteCommande"))
                       .rel("delete")
                       .build(Utility.toDomain(cdeRes).getId()));                      
                }
             });            
        }
        return links;
    }
}


  