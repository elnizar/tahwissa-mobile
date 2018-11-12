/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.parsing;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.ParseException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import tn.tahwissa.mobile.List.ArticleList;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.entity.ImageShop;

/**
 *
 * @author esprit
 */
public class ArticlesParser extends Observable {
    protected static ConnectionRequest request;

    
    public ConnectionRequest handle(Observer observer) {
        ArticleList.getListArticles().clear();
        this.addObserver(observer);
          request = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream input) throws IOException { 
                    
                    InputStreamReader reader = new InputStreamReader(input);
                    JSONParser jp = new JSONParser();
                    Map <String, Object> response1 = jp.parseJSON(reader);
                    Map <String, Object> response = (Map <String, Object>) response1.get("data");
                    Article art;
                    ImageShop img;
                    for(int i = 1; i <= response.size(); i++) {
                        System.out.println(i);
                        Map article = (Map) response.get(String.valueOf(i));
                        art = new Article();
                        art.setId(new Double((double) article.get("id")).intValue());
                        art.setPrix(Double.valueOf((String)article.get("prix")));
                        
                        System.out.println(art.getPrix());
                        
                        art.setEtat((String) article.get("etat"));
                        art.setDescription((String) article.get("description"));
                        art.setLibelle((String) article.get("libelle"));
                        art.setIdImage((String) article.get("idImage"));
                        Map imgs = (Map) article.get("images");
                        System.out.println("taille array "+imgs.size());
                        for (int j = 1; j < imgs.size(); j++) {
                            Map imageShop = (Map) imgs.get(String.valueOf(j));
                            String imageName = (String) imageShop.get("imageName");
                            
                            LinkedHashMap updateAt = (LinkedHashMap) imageShop.get("updateAt");
                            String date = (String) updateAt.get("date");
                            int id = new Double((double) imageShop.get("id")).intValue();
                            try {
                                img = new ImageShop(imageName, DateFormat.getDateInstance().parse(date), null);
                                art.addImage(img);
                            } catch (ParseException ex) {
                                //Logger.getLogger(ArticlesParser.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            
                        }
                        
                        ArticleList.addItem(art);
                    }
                }
                
            };
           
            request.setPost(false);
            request.setHttpMethod("GET");
            request.setPriority(ConnectionRequest.PRIORITY_NORMAL);
            request.setUrl("http://localhost:8000/boutique/articles/all");
            NetworkManager.getInstance().addToQueue(request);
            NetworkManager.getInstance().addProgressListener((evt) -> {
                this.setChanged();
                this.notifyObservers();
            });
            
            return request;
    }
    
    public static ConnectionRequest getConnectionRequest() {
        return request;
    }
    
    public static void resetConnection() {
        NetworkManager.getInstance().killAndWait(request);
        
    }
    
    public static void reset() {
        request = null;
    }
    
      public void stopObserving() {
       this.deleteObservers();
   }
}
