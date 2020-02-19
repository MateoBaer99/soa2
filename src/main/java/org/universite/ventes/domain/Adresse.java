/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.domain;
 
/**
 *
 * @author PAKI6340
 */
public class Adresse {
   private Integer numeroVoie = null;

    public enum TypeVoieEnum {

        RUE("rue"), 
        IMPASSE("impasse"), 
        BOULEVARD("boulevard"), 
        AVENUE("avenue");

        private String value;

        TypeVoieEnum(String v) {
            value = v;
        }

        public String value() {
            return value;
        }

        @Override
        public String toString() {
            return String.valueOf(value);
        }
    }

    private TypeVoieEnum typeVoie = null;
    /**
     * Nom de la voie.
     */
    private String nomVoie = null;

    private int codePostal;
    /**
     * Nom de la commune.
     */
    private String commune = null;

    public Integer getNumeroVoie() {
        return numeroVoie;
    }

    public TypeVoieEnum getTypeVoie() {
        return typeVoie;
    }

    public String getNomVoie() {
        return nomVoie;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public String getCommune() {
        return commune;
    }

    public Adresse numeroVoie(final Integer value) {
        this.numeroVoie = value;
        return this;
    }

    public Adresse typeVoie(final TypeVoieEnum value) {
        this.typeVoie = value;
        return this;
    }

    public Adresse nomVoie(final String value) {
        this.nomVoie = value;
        return this;
    }

    public Adresse codePostal(final int value) {
        this.codePostal = value;
        return this;
    }

    public Adresse commune(final String value) {
        this.commune = value;
        return this;
    }


    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Adresse {\n");

        sb.append("    numeroVoie: ").append(numeroVoie).append("\n");
        sb.append("    typeVoie: ").append(typeVoie).append("\n");
        sb.append("    nomVoie: ").append(nomVoie).append("\n");
        sb.append("    codePostal: ").append(codePostal).append("\n");
        sb.append("    commune: ").append(commune).append("\n");
        sb.append("}");
        return sb.toString();
    }
  
}
