/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.entity;


import java.util.Date;
import java.util.List;

/**
 *
 * @author esprit
 */
public class Article {
    protected int id;
    protected String libelle;
    protected String description;
    protected double prix;
    protected String etat;
    protected boolean vendu;
    protected boolean boosted;
    protected boolean livre;
    protected Date updateAt;
    protected entity.User owner;
    protected String idImage;
    private List<ImageShop> images;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public boolean isVendu() {
        return vendu;
    }

    public void setVendu(boolean vendu) {
        this.vendu = vendu;
    }

    public boolean isBoosted() {
        return boosted;
    }

    public void setBoosted(boolean boosted) {
        this.boosted = boosted;
    }

    public boolean isLivre() {
        return livre;
    }

    public void setLivre(boolean livre) {
        this.livre = livre;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public entity.User getOwner() {
        return owner;
    }

    public void setOwner(entity.User owner) {
        this.owner = owner;
    }

    public List<ImageShop> getImages() {
        return images;
    }

    public void setImages(List<ImageShop> images) {
        this.images = images;
    }
    
    public boolean addImage(ImageShop image) {
        if (images.contains(image) == false) {
            images.add(image);
            return true;
        }
        return false;
    }
    
    public boolean removeImage(ImageShop image) {
        if(images.contains(image)) {
            images.remove(image);
            return true;
        }
        return false;
    }

    public String getIdImage() {
        return idImage;
    }

    public void setIdImage(String idImage) {
        this.idImage = idImage;
    }
    
    
}
