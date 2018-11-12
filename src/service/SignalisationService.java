/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import entity.Signalisation;
import tn.esprit.tahwissa.LoginManager;

/**
 *
 * @author User
 */
public class SignalisationService {
    
    public static void NewSignal(Signalisation s) {
       
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa-web/web/app_dev.php/service/signalisation");
        request.setPost(false);
        
        request.addArgument("objet", String.valueOf(s.getObjet()));
        request.addArgument("motif", String.valueOf(s.getMotif()));
        request.addArgument("user1_id", String.valueOf(s.getUser_id()));
        request.addArgument("user2_id", String.valueOf(s.getUser2_id()));
       // request.addArgument("nombre_signalisation", String.valueOf(s.getNombre_Signlisations));
        
        
        request.addResponseListener((evt) -> {
            String reply = (new String(request.getResponseData()));
            if (reply.indexOf("+")!=-1){
                String messageUrl = "http://smsc.vianett.no/v3/send.ashx?src=+21651819882&"
                        + "dst="+reply+"&msg=Votre+compte+a+ete+banni&username=khoubeib.charradi@gmail.com&password=mi597";
                ConnectionRequest r = new ConnectionRequest(messageUrl);
                r.setPost(false);
                r.addResponseListener((eventt)->{
                    System.out.println(r.getResponseCode());
                });
                NetworkManager.getInstance().addToQueue(r);
            }
        });
        System.out.println(request.getUrl());

        // request.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueue(request);
    }
    
}
