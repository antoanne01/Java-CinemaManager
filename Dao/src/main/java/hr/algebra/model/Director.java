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
public class Director {
    
    @XmlElement(name = "iddirector")
    private int idDirector;
    @XmlElement(name = "directorname")
    private String directorName;

    public Director(String directorName) {
        this.directorName = directorName;
    }

    public Director(int idDirector, String directorName) {
        this.idDirector = idDirector;
        this.directorName = directorName;
    }
    

    public int getIdDirector() {
        return idDirector;
    }

    public void setIdDirector(int idDirector) {
        this.idDirector = idDirector;
    }

    public String getDirectorName() {
        return directorName;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + this.idDirector;
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
        final Director other = (Director) obj;
        return this.idDirector == other.idDirector;
    }
    
    @Override
    public String toString() {
        return directorName;
    }
}
