/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.api.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import org.universite.ventes.api.impl.RestException;
import org.universite.ventes.exceptions.AppliException;
import org.universite.ventes.exceptions.InformationsCommandesException;
/**
 * Gestion des exceptions : retourne un message plus explicite avec le bon format
 * log la méthode ayant déclenchée l'exception
 * @author akriks
 */
@Provider
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class ApplicationExceptionMapper extends MyMapper implements ExceptionMapper<Exception> {
    

    public ApplicationExceptionMapper() {
        super();
    }

    @Override
    public Response toResponse(Exception e) {
        Status status=Status.BAD_REQUEST;
        if (e instanceof UnsupportedOperationException) {
            status=Status.NOT_IMPLEMENTED;
        } else if (e instanceof ValidationException ) {
            status=Status.BAD_REQUEST;
        } else if (e instanceof InformationsCommandesException ) {
            status=Status.BAD_REQUEST;
        } else if (e instanceof AppliException) {
            status=Status.NOT_FOUND;
        }  

        RestException restEx=new RestException(status.getStatusCode(),e.getMessage(),e.getCause());
        return Response.status(status)
                       .entity(restEx)
                       .type(getMediaType())
                       .build();
     }
         
}
