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
public class User implements java.io.Serializable {


     private Integer id;
     private Compte compte;
     private String username;
     private String usernameCanonical;
     private String email;
     private String emailCanonical;
     private boolean enabled;
     private String salt;
     private String password;
     private Date lastLogin;
     private String confirmationToken;
     private Date passwordRequestedAt;
     private String roles;
     private String adresse;
     private Boolean banned;
     private Date dateInscription;
     private Date dateNaissance;
     private String informationPersonnel;
     private String nom;
     private String prenom;
     private int nombreOrganisations;
     private int nombreSignalisations;
     private String numeroTelephone;
     private String profession;
     private Double ratingFlobal;
     private String sexe;
     private Double solde;

   
     public User(){
         
     }
	
    public User(String username, String usernameCanonical, String email, String emailCanonical, boolean enabled, String password, String roles, int nombreOrganisations, int nombreSignalisations) {
        this.username = username;
        this.usernameCanonical = usernameCanonical;
        this.email = email;
        this.emailCanonical = emailCanonical;
        this.enabled = enabled;
        this.password = password;
        this.roles = roles;
        this.nombreOrganisations = nombreOrganisations;
        this.nombreSignalisations = nombreSignalisations;
    }
   
  
    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }


    public Compte getCompte() {
        return this.compte;
    }
    
    public void setCompte(Compte compte) {
        this.compte = compte;
    }

 
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    
   
    public String getUsernameCanonical() {
        return this.usernameCanonical;
    }
    
    public void setUsernameCanonical(String usernameCanonical) {
        this.usernameCanonical = usernameCanonical;
    }

    
   
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
  
    public String getEmailCanonical() {
        return this.emailCanonical;
    }
    
    public void setEmailCanonical(String emailCanonical) {
        this.emailCanonical = emailCanonical;
    }

    
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    
    
    public String getSalt() {
        return this.salt;
    }
    
    public void setSalt(String salt) {
        this.salt = salt;
    }

    
   
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

   
    public Date getLastLogin() {
        return this.lastLogin;
    }
    
    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    
   
    public String getConfirmationToken() {
        return this.confirmationToken;
    }
    
    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    
    public Date getPasswordRequestedAt() {
        return this.passwordRequestedAt;
    }
    
    public void setPasswordRequestedAt(Date passwordRequestedAt) {
        this.passwordRequestedAt = passwordRequestedAt;
    }

    
  
    public String getRoles() {
        return this.roles;
    }
    
    public void setRoles(String roles) {
        this.roles = roles;
    }

    
   
    public String getAdresse() {
        return this.adresse;
    }
    
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    
    
    public Boolean getBanned() {
        return this.banned;
    }
    
    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

   
    public Date getDateInscription() {
        return this.dateInscription;
    }
    
    public void setDateInscription(Date dateInscription) {
        this.dateInscription = dateInscription;
    }

    
    public Date getDateNaissance() {
        return this.dateNaissance;
    }
    
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    
   
    public String getInformationPersonnel() {
        return this.informationPersonnel;
    }
    
    public void setInformationPersonnel(String informationPersonnel) {
        this.informationPersonnel = informationPersonnel;
    }

    
    
    public String getNom() {
        return this.nom;
    }
    
    public void setNom(String nom) {
        this.nom = nom;
    }

    
    
    public String getPrenom() {
        return this.prenom;
    }
    
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    
   
    public int getNombreOrganisations() {
        return this.nombreOrganisations;
    }
    
    public void setNombreOrganisations(int nombreOrganisations) {
        this.nombreOrganisations = nombreOrganisations;
    }

    
   
    public int getNombreSignalisations() {
        return this.nombreSignalisations;
    }
    
    public void setNombreSignalisations(int nombreSignalisations) {
        this.nombreSignalisations = nombreSignalisations;
    }

    
    
    public String getNumeroTelephone() {
        return this.numeroTelephone;
    }
    
    public void setNumeroTelephone(String numeroTelephone) {
        this.numeroTelephone = numeroTelephone;
    }

    
  
    public String getProfession() {
        return this.profession;
    }
    
    public void setProfession(String profession) {
        this.profession = profession;
    }

    
  
    public Double getRatingFlobal() {
        return this.ratingFlobal;
    }
    
    public void setRatingFlobal(Double ratingFlobal) {
        this.ratingFlobal = ratingFlobal;
    }

    
   
    public String getSexe() {
        return this.sexe;
    }
    
    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    
    
    public Double getSolde() {
        return this.solde;
    }
    
    public void setSolde(Double solde) {
        this.solde = solde;
    }
}
