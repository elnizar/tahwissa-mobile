/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.AccessToken;
import com.codename1.io.NetworkManager;
import com.codename1.io.Oauth2;
import com.codename1.io.services.TwitterRESTService;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 *
 * @author Chen
 */
public class TwitterLogin extends Observable {

    private Form main;
    public static String TOKEN;
    private static final String client_id = "f834eeqRBGmICasYWyMzRFvIc";
    private static final String client_secret = "94l9Yrhtpd4wuDcncNiut00O2h3czUkjvdVSolKqSGoK6JkO4j";

    private TwitterLogin() {
        //super("Login Form");
        //  this.main = f;
        //setLayout(new LayeredLayout());

        // signIn(main, o);
        // addComponent(login);
    }

  
    public static void getPosts(Observer o) {
        String token = TwitterRESTService.initToken(client_id, client_secret);
        TwitterRESTService.setToken(token);
        TwitterRESTService t = new TwitterRESTService("GET");
        t.setUrl("https://api.twitter.com/1.1/search/tweets.json");
        t.addArgument("q", "tahwissa");
        //t.addArgument("count", "");
        NetworkManager.getInstance().addToQueue(t);
        List<Map<String, Object>> posts = new ArrayList<>();
        t.addResponseListener((evt) -> {
            Vector results = (Vector) t.getParseTree().get("statuses");
            for (int i = 0; i < results.size(); i++) {
                Hashtable<String, Object> post = (Hashtable<String, Object>) (results.get(i));
                System.out.println("Created at :" + post.get("created_at"));
                System.out.println("Text : " + post.get("text"));
                Hashtable<String, Object> user = (Hashtable<String, Object>) (post.get("user"));
                System.out.println("User name : " + user.get("name"));
                System.out.println("Screen Name " + user.get("screen_name"));
                System.out.println("Picture : " + user.get("profile_image_url"));
                System.out.println("**********************************");
                Map<String, Object> myPost = new HashMap<>();
                myPost.put("created_at", post.get("created_at"));
                myPost.put("text", post.get("text"));
                myPost.put("username", user.get("name"));
                myPost.put("screen_name", user.get("screen_name"));
                if (user.get("profile_image_url") != null) {
                    myPost.put("picture", user.get("profile_image_url"));
                } else {
                    myPost.put("picture", "");
                }
                if (user.get("profile_image_url") != null) {
                    posts.add(myPost);
                }

            }
            o.update(null, posts);

        });
        System.out.println(token);
    }

}
