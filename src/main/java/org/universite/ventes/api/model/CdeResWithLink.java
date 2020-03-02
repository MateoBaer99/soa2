/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.universite.ventes.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author akriks
 */
@XmlAccessorType(XmlAccessType.FIELD)
 @XmlType(name = "CdeResWithLink", propOrder =
    { "commande", "links" })


@XmlRootElement(name="EntityWithLink")

public class CdeResWithLink {
    @XmlElement(name = "commande")
    private List<CommandeRes> modelRes;
    
    @XmlElement(name = "link")
    @XmlJavaTypeAdapter(Link.JaxbAdapter.class)
    private List<Link> links;

    @JsonProperty("commande")
    public List<CommandeRes> getModelRes() {
        return modelRes;
    }

    public void setModelRes(Object modelRes) {
        if (modelRes instanceof CommandeRes){
            this.modelRes.add((CommandeRes)modelRes);
        } else if (modelRes instanceof List) {
            this.modelRes=(List<CommandeRes>)modelRes;
        }
    } 

    @JsonProperty("links")
    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public CdeResWithLink modelRes(final Object modelRes) {
        if (modelRes instanceof CommandeRes){
            this.modelRes.add((CommandeRes)modelRes);
        } else if (modelRes instanceof List) {
            this.modelRes=(List<CommandeRes>)modelRes;
        }
        return this;
    }

    public CdeResWithLink links(final List<Link> value) {
        this.links = value;
        return this;
    }
    
    
    
}
