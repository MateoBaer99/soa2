/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.api.impl;

import java.util.HashSet;
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
public class MyExceptionMapper implements ExceptionMapper<Exception> {
    
    @Context private ResourceInfo resourceInfo;
    @Context private HttpServletRequest request;
    @Context private HttpHeaders headers;

    private final Set<MediaType> mediaProduces;

    public MyExceptionMapper() {
        mediaProduces = getMediaFromProduces(this.getClass().getAnnotation(Produces.class));
    }

    @Override
    public Response toResponse(Exception e) {
        System.out.println("Exception dans :"+resourceInfo.getClass()+":"+resourceInfo.getResourceMethod());
        System.out.println("Exception class :"+e.getClass());
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
    
    /**
     * Retourne le mediaType utilisée pour retourner le message d'erreur au client de l'API
     * Vérifie en 1ier lieu l'en-tête Accept du client et si celle-ci est compatible avec
     * l'annotation @Produces 
     * S'il n'y a pas d'en-têtes Accept, ou si celle-ci est incompatible avec les formats possibles
     * alors utilisation du 1Ier type fourni dans @Produces 
      */
    private MediaType getMediaType() {
        //media type par défaut
        MediaType mediaReponse=MediaType.TEXT_PLAIN_TYPE;
        
        // mediatypes éventuellement indiqué dans l'en-tête Accept de la requête
        Set<MediaType> mediaAccept =  new HashSet(headers.getAcceptableMediaTypes());
        
         //si l'un des mediaType dans le header est supportée par les annotations Produces de l'API
        //alors on  retient le 1er (il faudrait normalement regarder les priorités) 
        if (null!=mediaProduces) {
             mediaReponse=mediaProduces.iterator().next();
            if (!mediaAccept.isEmpty()) {
                for (MediaType medAccept:mediaAccept) {
                    if (mediaProduces.contains(medAccept)) {
                        mediaReponse=medAccept;
                        break;
                    }
                }
             }
        }
        return mediaReponse;
      }
    
        /** 
         * Retourne la liste des mediatypes des annotations produces
         * 
         * @param produces  Contenus des annotations Produces
         *                   "@Produces" propose une liste de mediaType séparée par des ","
         * @return Liste des mediaType contenus dans les annotations Produces
         */
    
        private Set<MediaType> getMediaFromProduces(Produces produces) {
            final Set<MediaType> mediaTypes = new HashSet<>();
            try {
                  for (final String medprods: produces.value()) {
                     for (final String mediat: medprods.split(",")) {
                        mediaTypes.add(MediaType.valueOf(mediat.trim()));
                     }
                  }
            } catch (Throwable t) {
                return null;
            }
            return mediaTypes;
         }
        
}
