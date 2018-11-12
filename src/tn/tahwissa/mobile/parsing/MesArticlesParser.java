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
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import tn.tahwissa.mobile.List.MesArticlesList;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.util.LoginManager;
import tn.tahwissa.mobile.util.Tahwissa;

/**
 *
 * @author esprit
 */
public class MesArticlesParser extends Observable{
    private static ConnectionRequest request;
    private static MesArticlesParser articlesParser;
    
    public static MesArticlesParser getInstance() {
        articlesParser = (articlesParser == null) ? new MesArticlesParser() : articlesParser;
        return articlesParser;
    }
    
    public void handle(Observer observer) {
        this.addObserver(observer);
        request= new ConnectionRequest(Tahwissa.URL+"/boutique/mes_articles", true) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser jp = new JSONParser();
                Map responeGlobale = jp.parseJSON(reader);
                String isEmpty = (String) responeGlobale.get("empty");
                Map articleMap;
                Article article;
                if(isEmpty.equals("no")){
                    for (int i = 1; i < responeGlobale.size(); i++) {
                      articleMap = (Map) responeGlobale.get(String.valueOf(i));
                      article = new Article();
                      article.setLibelle((String) articleMap.get("libelle"));
                      article.setDescription((String) articleMap.get("description"));
                      article.setPrix(Double.valueOf((String) articleMap.get("prix")));
                      article.setBoosted(Boolean.valueOf((String) articleMap.get("boosted")));
                      article.setVendu((Boolean.valueOf((String) articleMap.get("vendu"))));
                      article.setLivre(Boolean.valueOf((String) articleMap.get("livre")));
                      article.setId(new Double((Double) articleMap.get("id")).intValue());
                      article.setOwner(LoginManager.getUser());
                      article.setIdImage((String) articleMap.get("image"));
                      article.setEtat((String) articleMap.get("etat"));
                        MesArticlesList.addItem(article);
                    }
                }
            }  
        };
        request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
        NetworkManager.getInstance().addToQueue(request);
        NetworkManager.getInstance().addProgressListener((evt) -> {
            this.setChanged();
            this.notifyObservers();
        });
    }
    
    public static boolean isRequestSent() {
        return request != null;
    }
    
    public static ConnectionRequest getRequest() {
        return request;
    }

   public void stopObserving() {
       this.deleteObservers();
   }
    
    
}
