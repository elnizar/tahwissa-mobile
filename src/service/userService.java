/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import tn.esprit.tahwissa.LoginManager;
import tn.esprit.tahwissa.MyApplication;
import tn.esprit.tahwissa.MyApplicationBackup;
import tn.esprit.tahwissa.ShowProfile;
import tn.esprit.tahwissa.profile;
import util.MenuManager;

/**
 *
 * @author Elhraiech Nizar
 */
public class userService {

    public static User userscooo;
    public static User connectedUser;

    public void connexionuser(String mail, String Password) {
        getUser(mail, Password);

    }
    boolean testtt = false;

    public void updateUser(String nom, String prenom, String password, String image) {

        ConnectionRequest req = new ConnectionRequest();
        req.setUrl("http://localhost/cn1assets/updateUser.php?nom=" + nom + "&prenom=" + prenom + "&chemin=" + image + "&password=" + password + "&email=" + User.testemail);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = (byte[]) evt.getMetaData();
                String s = new String(data);
                System.out.println(s);
                if (s.equals("success")) {
                    Dialog.show("Confirmation", "Inscription avec succées", "Ok", null);
                    return;
                }
            }
        });

        NetworkManager.getInstance().addToQueue(req);

    }

    public void addUser(String nom, String prenom, String mail, String password, String date) {

        ConnectionRequest req = new ConnectionRequest();
        req.setUrl("http://localhost/cn1assets/insert.php?nom=" + nom + "&prenom=" + prenom + "&date=" + date + "&password=" + password + "&email=" + mail);

        req.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                byte[] data = (byte[]) evt.getMetaData();
                String s = new String(data);
                System.out.println(s);
                if (s.equals("success")) {
                    Dialog.show("Confirmation", "Inscription avec succées", "Ok", null);
                    return;
                }
            }
        });

        NetworkManager.getInstance().addToQueueAndWait(req);

    }



    public User getUsser(String mail) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cn1assets/selectUser.php?email=" + mail);
        ArrayList<User> listuser = new ArrayList<>();
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                userscooo = getListEtudiant(new String(con.getResponseData()));
                if (mail == User.testemail) {
                    profile pr = new profile();
                    pr.setText(userscooo.getNom(), userscooo.getPrenom(), userscooo.getImage(), userscooo.getPassword());
                } else {
                    ShowProfile sp = new ShowProfile();
                    sp.setText(userscooo.getNom(), userscooo.getPrenom(), userscooo.getImage(), userscooo.getPassword(), userscooo.getEmail());
                }
            }

        });
        NetworkManager.getInstance().addToQueue(con);
        return userscooo;
    }
    
    public User getUserByMail(String mail) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cn1assets/selectUser.php?email=" + mail);
        ArrayList<User> listuser = new ArrayList<>();
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("The Logged Mail "+mail);
                LoginManager.user = getListEtudiant(new String(con.getResponseData()));    
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return LoginManager.user;
    }
    
    
    public User getUserNotConnectedByMail(String mail) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cn1assets/selectUser.php?email=" + mail);
        ArrayList<User> listuser = new ArrayList<>();
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println("The Logged Mail "+mail);
                userscooo = getListEtudiant(new String(con.getResponseData()));    
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return userscooo;
    }
    
    public void getUser(String mail, String password) {
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cn1assets/selectUser.php?email=" + mail);
        ArrayList<User> listuser = new ArrayList<>();
        con.addResponseListener(new ActionListener<NetworkEvent>() {

            @Override
            public void actionPerformed(NetworkEvent evt) {
                System.out.println(con.getResponseData() + " naj7at");
                byte[] data = (byte[]) evt.getMetaData();
                String s = new String(data);
                System.out.println(s + "test de ...");
                if (s.equals("fail{}")) {
                    Dialog.show("Confirmation", "main ou pass invalid", "Ok", null);
                    System.out.println("lénaaa");
                    return;
                } else {
                    User u = getListEtudiant(new String(con.getResponseData()));
                    if (u.getEmail().equals(mail) && u.getPassword().equals(password)) {
                        User.testemail = mail;
                        getUserByMail(User.testemail);
                        //System.out.println("Nom: "+connectedUser.getNom());
                        MyApplication app = new MyApplication();
                        app.start();
                        //return;
                    } else {
                        Dialog.show("Confirmation", "mot de pass incorrect", "Ok", null);
                        System.out.println("lénaaa");
                        return;
                    }

                }
            }

        }
        );
        NetworkManager.getInstance()
                .addToQueue(con);
    }

    public User getListEtudiant(String json) {
        ArrayList<User> listEtudiants = new ArrayList<>();
        User e = new User();

        try {

            JSONParser j = new JSONParser();

            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

            System.out.println();
            Map<String, Object> list = (Map<String, Object>) etudiants.get("etudiant");
            e.setId(Integer.parseInt((String) list.get("id")));
            e.setPrenom((String) list.get("prenom"));
            e.setNom((String) list.get("nom"));
            System.out.println(list.get("nom"));
            e.setEmail(list.get("email").toString());
            //e.setInformationPersonnel(list.get("info").toString());
            e.setDateNaissance(list.get("date").toString());
            e.setImage(list.get("image").toString());
            System.out.println(list.get("image").toString() + "aaa");
            e.setPassword(list.get("password").toString());

        } catch (IOException ex) {
        }
        return e;

    }

    public ArrayList<User> getListUsers() {
        ArrayList<User> listEtudiants = new ArrayList<>();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/cn1assets/select.php");

        con.addResponseListener(a -> {
            String json = new String(con.getResponseData());
            try {

                JSONParser j = new JSONParser();

                Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));

                System.out.println();
                List<Map<String, Object>> list = (List<Map<String, Object>>) etudiants.get("etudiant");

                for (Map<String, Object> obj : list) {
                    User e = new User();
                    e.setId(Integer.parseInt((String) obj.get("id")));
                    e.setPrenom(obj.get("prenom").toString());
                    e.setNom(obj.get("nom").toString());
                    e.setEmail(obj.get("email").toString());
                    System.out.println("limail" + obj.get("email").toString());
                    listEtudiants.add(e);

                }

            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance()
                .addToQueueAndWait(con);
        for (int i = 0; i < listEtudiants.size(); i++) {
            System.out.println("hédha nom " + listEtudiants.get(i).getNom() + "hédha prenom " + listEtudiants.get(i).getPrenom() + "hédha email " + listEtudiants.get(i).getEmail());
        }
        return listEtudiants;

    }
    
}
