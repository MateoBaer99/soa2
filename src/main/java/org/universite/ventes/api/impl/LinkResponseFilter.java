/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.api.impl;

import java.io.IOException;
import java.util.List;
import javax.annotation.Priority;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Link;
import javax.ws.rs.ext.Provider;
import org.universite.ventes.api.GestionDesCommandesApi;
import org.universite.ventes.api.model.CdeResWithLink;
import org.universite.ventes.api.model.CommandeRes;

/**
 *
 * @author akriks
 */
@Provider
@Priority(value = 10)
public class LinkResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        if (responseContext.hasEntity()) {
           if (requestContext.getUriInfo().getMatchedResources().contains(GestionDesCommandesApi.class));
              List<Link> links=CommandeLink.add(requestContext, responseContext);
              responseContext.setEntity(
                      new CdeResWithLink().modelRes(responseContext.getEntity())
                                          .links(links));
        }
     
    }
}

