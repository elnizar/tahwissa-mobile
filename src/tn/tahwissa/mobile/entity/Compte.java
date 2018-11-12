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
public class Compte  implements java.io.Serializable {


     private Integer id;
     private User user;
     private String passcode;
     private Date dateCreation;
     private Date dateModification;
     private Double solde;
     private String identifiant;
   

    public Compte() {
        
    }
    
	
    public Compte(String passcode, String identifiant) {
        this.passcode = passcode;
        this.identifiant = identifiant;
    }
  

    
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }


    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    
    
    public String getPasscode() {
        return this.passcode;
    }
    
    public void setPasscode(String passcode) {
        this.passcode = passcode;
    }

    
    public Date getDateCreation() {
        return this.dateCreation;
    }
    
    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

 
    public Date getDateModification() {
        return this.dateModification;
    }
    
    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    
    
    public Double getSolde() {
        return this.solde;
    }
    
    public void setSolde(Double solde) {
        this.solde = solde;
    }

    
  
    public String getIdentifiant() {
        return this.identifiant;
    }
    
    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }






}

