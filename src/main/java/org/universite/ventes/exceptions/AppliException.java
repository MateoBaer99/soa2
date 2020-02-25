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
public class AppliException extends RuntimeException {
        
        public AppliException() {
            super();
        }        
        public AppliException(String message) {
            super(message);
        }
        
        public AppliException(String message, Throwable t) {
            super(message, t);
        }
        
}
