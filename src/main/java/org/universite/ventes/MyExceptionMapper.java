/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
/**
 * Gestion des exceptions : retourne un message plus explicite avec le bon format
 * log la méthode ayant déclenchée l'exception
 * @author akriks
 */
@Provider
public class MyExceptionMapper implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Response toResponse(UnsupportedOperationException e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }    
    WebApplicationException(Response.status(Status.BAD_REQUEST)
                                                      .entity("{\"Erreur\": \"Infos commandes incorrectes\"}")
                                                      .type(MediaType.TEXT_XML_TYPE)
                                                      .build())
}
