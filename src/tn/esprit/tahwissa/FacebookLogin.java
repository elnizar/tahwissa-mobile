/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.facebook.FaceBookAccess;
import com.codename1.facebook.User;
import com.codename1.io.AccessToken;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Oauth2;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author Elhraiech Nizar
 */
public class FacebookLogin {

    public static String TOKEN;

    public static void signIn() {
        String clientId = "1578414245504369";
        String redirectURI = "http://localhost/pidev2017/test.php";
        String clientSecret = "a615c3a8462380dddc8410da92de909c";
        FaceBookAccess.setClientId(clientId);
        FaceBookAccess.setClientSecret(clientSecret);
        FaceBookAccess.setRedirectURI(redirectURI);
        FaceBookAccess.setPermissions(new String[]{"public_profile"});

        FaceBookAccess.getInstance().showAuthentication(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() instanceof AccessToken) {

                    AccessToken token = (AccessToken) evt.getSource();
                    FaceBookAccess.setToken(TOKEN);
                    String expires = Oauth2.getExpires();
                    TOKEN = token.getToken();
                    System.out.println("recived a token " + token.getToken() + " which expires on " + expires);
                    //store token for future queries.
                    //Storage.getInstance().writeObject("token", token);
                    ConnectionRequest req = new ConnectionRequest();
                    req.setUrl("https://graph.facebook.com/me?fields=id,name,email,birthday,first_name,last_name&access_token=" + TOKEN);
                    req.setPost(false);
                    req.addResponseListener((event) -> {
                        String json = new String(req.getResponseData());
                        JSONParser j = new JSONParser();
                        try {
                            
                            Map<String, Object> etudiants = j.parseJSON(new CharArrayReader(json.toCharArray()));
                            System.out.println(etudiants);
                            System.out.println();
                            //Map<String, Object> list = (Map<String, Object>) etudiants.get("etudiant");

                            String nom = (String) etudiants.get("first_name");
                            String prenom = (String) etudiants.get("last_name");
                            String email = (String) etudiants.get("email");
                            String birthday = (String) etudiants.get("birthday");
                            Signup sp = new Signup(nom, prenom, email, birthday);
                            sp.init();
                            sp.start();
                            //sp.setText();
                                   
                        } catch (IOException exc) {
                        }
                    });
                    NetworkManager.getInstance().addToQueueAndWait(req);
                } else {
                    // Exception err = (Exception) evt.getSource();
                    System.out.println(evt.getSource());
                }
            }
        });
    }
}
