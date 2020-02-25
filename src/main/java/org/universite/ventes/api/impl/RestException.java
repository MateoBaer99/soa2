package org.universite.ventes.api.impl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestException", propOrder = { "code", "message", "cause"})

@XmlRootElement(name="Erreur")

public class RestException  {
  
  @XmlElement(name="code")
  private int code;

  @XmlElement(name="message")
  private String message;

  @XmlElement(name="cause")
  private String cause;

    public RestException() {
    }

    public RestException(int code, String message, Throwable exception) {
        this.code = code;
        this.message = message;
        if (null!=exception) {
            this.cause = exception.getLocalizedMessage();
        }  
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getCause() {
        return cause;
    }

}

