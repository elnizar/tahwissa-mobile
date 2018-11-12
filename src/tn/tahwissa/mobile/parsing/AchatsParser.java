/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.parsing;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import tn.tahwissa.mobile.List.AchatList;
import tn.tahwissa.mobile.entity.Achat;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.entity.User;
import tn.tahwissa.mobile.util.LoginManager;
import tn.tahwissa.mobile.util.Tahwissa;

/**
 *
 * @author esprit
 */
public class AchatsParser extends Observable {
    private static ConnectionRequest request;
    private static AchatsParser achatParser;
    
    public static AchatsParser getInstance() {
        achatParser = (achatParser == null) ? new AchatsParser() : achatParser;
        return achatParser;
    }
    
    public void handle(Observer observer) {
        this.addObserver(observer);
        AchatList.reset();
        request = new ConnectionRequest(Tahwissa.URL+"/boutique/achats/all", true){
            @Override
            protected void readResponse(InputStream input) throws IOException {
                Achat achat;
                User user;
                Double datee;
                Date date ;
                Article article;
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser jp = new JSONParser();
                Map<String, Object> responseOriginal = jp.parseJSON(reader);
                Map<String, Object> response2;
                ArrayList full = (ArrayList) responseOriginal.get("empty");
                String isEmpty = (String) full.get(0) ;
     
                Map<String, Object> response;
                if (isEmpty.equals("no")) {
                    for(int i = 1 ; i < responseOriginal.size() ; i++) {
                       achat = new Achat() ;
                       response = (Map<String, Object>) responseOriginal.get(String.valueOf(i));
                     
                            
                            //System.out.println("This is the date :"+(String) response.get("date_achat"));
                            //System.out.println("statut; "+(String) response.get("statut"));
                            datee = new Double((Double) response.get("date_achat"));
                      
                            date = new Date(datee.longValue());
                            achat.setDateHeureAchat(date);
                            achat.setStatut((String) response.get("statut"));
                            achat.setId(new Double((double)response.get("id")).intValue());
                            article = new Article();
                            response2 = (Map<String, Object>) response.get("article");
                            article.setLibelle((String) response2.get("libelle"));
                            article.setEtat((String) response2.get("etat"));
                            article.setDescription((String) response2.get("description"));
                            article.setPrix(Double.valueOf((String) response2.get("prix")));
                            Map response3 = (Map<String, Object>) response2.get("membre");
                            
                            user = new User();
                            user.setUsername((String) response3.get("username"));
                            user.setId(new Double(Double.valueOf((Double) response3.get("id"))).intValue());
                            achat.setArticle(article);
                            achat.setMembre(user);
                            System.out.println("Libelle"+article.getLibelle());
                            AchatList.addItem(achat);
                      
                    }
                }
            }
            
        };
        request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
        NetworkManager.getInstance().addToQueue(request);
        NetworkManager.getInstance().addProgressListener((evt) -> {
            System.out.println(AchatList.getListAchats().size());
            this.setChanged();
            this.notifyObservers();
        });
    }
    
    public static void resetRequest() {
        request = null;
    }
    
    public static ConnectionRequest getRequest() {
        return request;
    }
    
    public static boolean isRequestSent() {
        return request != null;
    }
    
      public void stopObserving(Observer o) {
       this.deleteObserver(o);
   }
}
