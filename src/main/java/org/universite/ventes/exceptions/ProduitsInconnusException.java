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
public class ProduitsInconnusException extends AppliException {
    private static final  String MESSAGE="Des références produits de votre commande sont inconnus";

    public ProduitsInconnusException() {
        super(MESSAGE);
    }
    public ProduitsInconnusException(Throwable t) {
        super(MESSAGE,t);
    }
    
}
