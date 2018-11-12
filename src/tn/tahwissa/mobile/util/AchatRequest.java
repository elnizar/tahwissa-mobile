/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.util;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import tn.tahwissa.mobile.List.ArticleList;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.parsing.ArticlesParser;

/**
 *
 * @author esprit
 */
public class AchatRequest {
    private static ConnectionRequest request;
    private static String message;
    private static int httpCode = 0;
    private static String url;
    
    
    public static void sendAchatRequest(Article article, String passcode, boolean isBoost) {
        url = (isBoost) ? Tahwissa.URL+"/boutique/article/booster" : Tahwissa.URL+"/boutique/commander/article";
        request = new ConnectionRequest(url, true) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                httpCode = getResponseCode();
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser jp = new JSONParser();
                Map response = jp.parseJSON(reader);
                message = (String) response.get("message");
                
                
            }
            
        };
        System.out.println("L'id article à acheter: "+article.getId());
        request.addArgument("id_article", String.valueOf(article.getId()));
        System.out.println("L'id User :"+String.valueOf(LoginManager.getUser().getId()));
        request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
        System.out.println("Passcode : "+passcode);
        request.addArgument("passcode", passcode);
        request.setPriority(ConnectionRequest.PRIORITY_CRITICAL);
        request.getDisposeOnCompletion();
        NetworkManager.getInstance().addToQueueAndWait(request);
        NetworkManager.getInstance().addProgressListener((evt) -> {
//            System.out.println("Message Data : "+new String(evt.getConnectionRequest().getResponseData()));
        });
    }

    public static ConnectionRequest getRequest() {
        return request;
    }

    public static String getMessage() {
        return message;
    }

    public static int getHttpCode() {
        return httpCode;
    }
    
}
