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
import javax.ws.rs.Produces;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author akriks
 */
public class MyMapper {

    @Context
    private ResourceInfo resourceInfo;
    @Context
    private HttpServletRequest request;
    @Context
    private HttpHeaders headers;

    final Set<MediaType> mediaProduces;

    public MyMapper() {
        mediaProduces = getMediaFromProduces(this.getClass().getAnnotation(Produces.class));
    }

    /**
     * Retourne le mediaType utilisée pour retourner le message d'erreur au
     * client de l'API Vérifie en 1ier lieu l'en-tête Accept du client et si
     * celle-ci est compatible avec l'annotation @Produces S'il n'y a pas
     * d'en-têtes Accept, ou si celle-ci est incompatible avec les formats
     * possibles alors utilisation du 1Ier type fourni dans @Produces
     */
    protected MediaType getMediaType() {
        // mediatypes éventuellement indiqué dans l'en-tête Accept de la requête
        Set<MediaType> mediaAccept = new HashSet(headers.getAcceptableMediaTypes());

        //si l'un des mediaType dans le header est supportée par les annotations Produces de l'API
        //alors on  retient le 1er (il faudrait normalement regarder les priorités) 
        Optional<MediaType> mediaReponse = mediaAccept.stream().filter(media -> mediaProduces.contains(media)).findFirst();

        //media type par défaut sera TEXT_PLAIN
        if (mediaReponse.isEmpty()) {
            mediaReponse = Optional.of(MediaType.TEXT_PLAIN_TYPE);
        }
        return mediaReponse.get();
    }

    /**
     * Retourne la liste des mediatypes des annotations produces
     *
     * @param produces Contenus des annotations Produces "@Produces" propose une
     * liste de mediaType séparée par des ","
     * @return Liste des mediaType contenus dans les annotations Produces
     */
    protected Set<MediaType> getMediaFromProduces(Produces produces) {
        final Set<MediaType> mediaTypes = new HashSet<>();
        try {
            for (final String medprods : produces.value()) {
                for (final String mediat : medprods.split(",")) {
                    mediaTypes.add(MediaType.valueOf(mediat.trim()));
                }
            }
        } catch (Throwable t) {
            return null;
        }
        return mediaTypes;
    }
}
