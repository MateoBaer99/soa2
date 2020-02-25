/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.exceptions;

/**
 *
 * @author akriks
 */
public class ClientInconnuException extends AppliException {

   private static final  String MESSAGE="Client inconnu, pas de commande possible";
    
    public ClientInconnuException() {
        super(MESSAGE);
    }
    public ClientInconnuException(Throwable t) {
        super(MESSAGE,t);
    }    
}
