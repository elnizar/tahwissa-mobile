/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.entity;

import java.util.Date;

/**
 *
 * @author esprit
 */
public class Achat {
    protected int id;
    protected Date dateHeureAchat;
    protected String statut;
    protected User membre;
    protected Article article;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateHeureAchat() {
        return dateHeureAchat;
    }

    public void setDateHeureAchat(Date dateHeureAchat) {
        this.dateHeureAchat = dateHeureAchat;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public User getMembre() {
        return membre;
    }

    public void setMembre(User membre) {
        this.membre = membre;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
    
}
