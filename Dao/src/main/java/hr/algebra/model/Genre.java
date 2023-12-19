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
public class Genre {
    
    @XmlElement(name = "idgenre")
    private int idGenre;
    @XmlElement(name = "genrename")
    private String genreName;


    public Genre(String genreName) {
        this.genreName = genreName;
    }

    

    public Genre(int idGenre, String genreName) {
        this.idGenre = idGenre;
        this.genreName = genreName;
    }

    

    public int getIdGenre() {
        return idGenre;
    }

    public void setIdGenre(int idGenre) {
        this.idGenre = idGenre;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.idGenre;
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
        final Genre other = (Genre) obj;
        return this.idGenre == other.idGenre;
    }
    
    @Override
    public String toString() {
        return genreName;
    }
}

