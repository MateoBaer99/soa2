/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package org.universite.ventes.api.impl;

import org.apache.cxf.validation.ResponseConstraintViolationException;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.Iterator;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Provider
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public class MyValidationExceptionMapper extends MyMapper implements ExceptionMapper<ValidationException> {

    public MyValidationExceptionMapper() {
        super();
    }
    
    
    public Response toResponse(ValidationException exception) {
        StringBuffer message = new StringBuffer("Erreur lors de la validation du message : ");
        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException constraint = (ConstraintViolationException) exception;
            Iterator i$ = constraint.getConstraintViolations().iterator();
                while(i$.hasNext()) {
                    ConstraintViolation<?> v = (ConstraintViolation)i$.next();
                    message.append("\n")
                            .append(v.getPropertyPath())
                            .append(" : ")
                            .append(v.getMessage())                    
                            .append("\n");
                }
        }
        Response.Status status = Response.Status.BAD_REQUEST;
        RestException restEx = new RestException(status.getStatusCode(), message.toString(), exception.getCause());
        return Response.status(status)
                .entity(restEx)
                .type(getMediaType())
                .build();
    }

}
