/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import entity.Messagerie;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import tn.esprit.tahwissa.LoginManager;

/**
 *
 * @author User
 */
public class MessagerieService {

    public static Map<String, List<Map<String, Object>>> getInbox() {
        Map<String, List<Map<String, Object>>> toReturn = new HashMap<>();
        int user_id = LoginManager.getUser().getId();
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/inbox");
        request.addArgument("user_id", String.valueOf(user_id));

        request.addResponseListener((evt) -> {
            JSONParser parser = new JSONParser();

            try {
                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
                // System.out.println(result);
                Map<String, Object> sendersAndDates = result;

                List<Map<String, Object>> senders = (List<Map<String, Object>>) sendersAndDates.get("senders");
                List<Map<String, Object>> lastDates = (List<Map<String, Object>>) sendersAndDates.get("lastDates");

                for (int i = 0; i < senders.size(); i++) {
                    Map<String, Object> sender = senders.get(i);
                    Map<String, Object> lastDate = lastDates.get(i);
                    System.out.println("Sender : " + sender.get("name") + " ( " + sender.get("mail") + ")");
                    System.out.println("Date : " + lastDate.get("date"));
                }
                toReturn.put("senders", senders);
                toReturn.put("dates", lastDates);
            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return toReturn;
    }

    public static List<Messagerie> getConversation(String mail) {
        List<Messagerie> toReturn = new ArrayList<>();
        int user_id = LoginManager.getUser().getId();
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/messages");
        request.addArgument("user_id", String.valueOf(user_id));
        request.addArgument("other_mail", mail);
        request.addResponseListener((evt) -> {
            JSONParser parser = new JSONParser();
            try {
                Map<String, Object> result = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(request.getResponseData())));
                // System.out.println(result);
                Map<String, Object> convers = result;
                System.out.println(result);
                List<Map<String, Object>> messages = (List<Map<String, Object>>) convers.get("root");

                for (int i = 0; i < messages.size(); i++) {
                    Map<String, Object> message = messages.get(i);
                    Messagerie m = new Messagerie();
                    m.setContenuMsg((String) message.get("contenu"));
                    m.setDateHeureEnvoi((String) message.get("date"));
                    m.setSender_id(((Double) message.get("sender_id")).intValue());
                    m.setReciever_id(((Double) message.get("reciever_id")).intValue());
                    m.setDeletedSender(Boolean.valueOf((String) message.get("deleted_sender")));
                    m.setDeletedReciever(Boolean.valueOf((String) message.get("deleted_reciever")));
                    //System.out.println("Contenu : " + message.get("contenu"));
                    toReturn.add(m);
                }
            } catch (IOException ex) {
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
        return toReturn;
    }

    public static void addNewMessage(Messagerie msg) {

        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/addMessageFromConv");
        request.setPost(false);
        request.addArgument("contenu", String.valueOf(msg.getContenuMsg()));

        request.addArgument("reciever_id", String.valueOf(msg.getReciever_id()));
        request.addArgument("sender_id", String.valueOf(msg.getSender_id()));
        request.addResponseListener((evt) -> {
            System.out.println(new String(request.getResponseData()));
        });
        System.out.println(request.getUrl());

        // request.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueue(request);
    }

    public static void NewMessage(Messagerie msg) {

        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/addMessage");
        request.setPost(false);

        request.addArgument("contenu", String.valueOf(msg.getContenuMsg()));
        request.addArgument("reciever_id", String.valueOf(msg.getReciever_id()));
        request.addArgument("sender_id", String.valueOf(msg.getSender_id()));

        request.addResponseListener((evt) -> {
            System.out.println(new String(request.getResponseData()));
        });
        System.out.println(request.getUrl());

        // request.setDisposeOnCompletion(dlg);
        NetworkManager.getInstance().addToQueue(request);
    }

    public static void deleteConv(int other_id) {
        ConnectionRequest request = new ConnectionRequest("http://localhost/tahwissa/web/app_dev.php/service/deleteConv");
        request.setPost(false);
        request.addArgument("user_id", String.valueOf(LoginManager.getUser().getId()));
        request.addArgument("other_id", String.valueOf(other_id));
        request.addResponseListener((evt) -> {
            System.out.println(new String(request.getResponseData()));
        });
        NetworkManager.getInstance().addToQueueAndWait(request);
    }

}
