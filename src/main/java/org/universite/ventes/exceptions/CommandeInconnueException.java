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
public class CommandeInconnueException extends AppliException {

   private static final  String MESSAGE="Identifiant de commande incorrect";

    public CommandeInconnueException() {
        super(MESSAGE);
    }

        public CommandeInconnueException(Throwable t) {
        super(MESSAGE,t);
    }
    
}
