/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author antoanne
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Actor {
    
    @XmlElement(name = "idactor")
    private int idActor;
    @XmlElement(name = "actorname")
    private String actorName;
    
    public Actor(String actorName) {
        this.actorName = actorName;
    }

    public Actor(int idActor, String actorName) {
        this.idActor = idActor;
        this.actorName = actorName;
    }

    public int getIdActor() {
        return idActor;
    }

    public void setIdActor(int idActor) {
        this.idActor = idActor;
    }

    public String getActorName() {
        return actorName;
    }

    public void setActorName(String actorName) {
        this.actorName = actorName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.idActor;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Actor other = (Actor) obj;
        return this.idActor == other.idActor;
    }

    @Override
    public String toString() {
        return actorName;
    }
}
