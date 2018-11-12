/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.util;

import service.userService;
import tn.tahwissa.mobile.entity.Compte;
import tn.tahwissa.mobile.entity.User;


/**
 *
 * @author esprit
 */
public class LoginManager {
    public static entity.User getUser(){
//        User u = new User();
//        u.setId(1);
//        u.setEmail("faycel.bouamoud@esprit.tn");
//        Compte c = new Compte();
//        c.setId(6);
//        c.setPasscode("753357");
//        c.setSolde(9.00);
//        u.setCompte(c);

            
        return tn.esprit.tahwissa.LoginManager.user;
    }
}
