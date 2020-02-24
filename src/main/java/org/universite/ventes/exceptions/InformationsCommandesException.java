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
public class InformationsCommandesException extends AppliException {

    public InformationsCommandesException() {
        super("La commande est incorrecte, veuillez vérifier son contenu");
    }
    
}
