/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.charts.compat.GradientDrawable;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import tn.tahwissa.mobile.List.AchatList;
import tn.tahwissa.mobile.entity.Achat;
import tn.tahwissa.mobile.parsing.AchatsParser;
import tn.tahwissa.mobile.util.AchatRequest;
import tn.tahwissa.mobile.util.LoginManager;
import tn.tahwissa.mobile.util.MenuManager;
import tn.tahwissa.mobile.util.Tahwissa;


/**
 *
 * @author esprit
 */
public class MesAchats extends Form implements Observer{
    private Resources theme;
    private UIBuilder uib;
    private Form achatsForm;
    private Container achatContainer;
    private Container itemContainer;
    private Container buttonContainer;
    private Container leftButtonContainer;
    private Container righButtonContainer;
    private Label membre;
    private Label libelle;
    private Label prix;
    private Label status;
    private Label dateAchat;
    private Button accuser;
    private Button reclamer;
    private AchatsParser achatsParser;

    public MesAchats(Resources theme) {
        this.theme = theme;
        uib = new UIBuilder();
        achatsForm = uib.createContainer(theme, "AchatsForm").getComponentForm();
        MenuManager.createMenu(achatsForm, theme);
        MenuManager.createLeftMenu(achatsForm, theme);
        MenuManager.setTitle(achatsForm, "Mes Achats");
        achatsParser = AchatsParser.getInstance(); achatsParser.handle(this);
//        if (!AchatsParser.isRequestSent()) {
//            achatsParser.handle(this);   
//        } else {
//           // AchatsParser.getRequest().resume();
//           //AchatList.reset();
//           achatsParser.handle(this);
//           // achatsForm.removeAll();
//            initForm();
//            show();
//        }
    }
    
    public void initForm() {
        System.out.println("Ibitibg theme !");
//        achatsForm.removeAll();
        achatsForm = uib.createContainer(theme, "AchatsForm").getComponentForm();
        MenuManager.createMenu(achatsForm, theme);
        MenuManager.createLeftMenu(achatsForm, theme);
        MenuManager.setTitle(achatsForm, "Mes Achats");
        initAchats();
  
       show();
    }
    
    public void initAchats() {
        System.out.println("init achats");
        achatContainer = (Container) uib.findByName("AchatContainer", achatsForm);
        itemContainer = (Container) uib.findByName("ItemContainer", achatContainer);
        buttonContainer = (Container) uib.findByName("ButtonContainer", achatsForm);
        leftButtonContainer = (Container) uib.findByName("LeftButtonContainer", achatsForm);
        righButtonContainer = (Container) uib.findByName("RightButtonContainer", achatsForm);
        membre = (Label) uib.findByName("Membre", achatsForm);
        libelle = (Label) uib.findByName("Libelle", achatsForm);
        prix = (Label) uib.findByName("Prix", achatsForm);
        status = (Label) uib.findByName("Statut", achatsForm);
        dateAchat = (Label) uib.findByName("DateAchat", achatsForm);
        accuser = (Button) uib.findByName("Accuser", achatsForm);
        reclamer = (Button) uib.findByName("Reclamer", achatsForm);
        System.out.println("init achats : looping");
        boolean isFirst = true;
        System.out.println("Arrayt size ; mes Achats "+AchatList.getListAchats());
        for(Achat achat : AchatList.getListAchats()) {
            System.out.println("Inside loop:");
            if (isFirst) {
                System.out.println("First");
                libelle.setText(achat.getArticle().getLibelle());
                membre.setText(achat.getMembre().getUsername());
                prix.setText(String.valueOf(achat.getArticle().getPrix()));
                status.setText(achat.getStatut());
                dateAchat.setText(achat.getDateHeureAchat().toString());
                if(achat.getStatut().equalsIgnoreCase("EFFECTUE") == false){
                    accuser.addActionListener((evt) -> {
                        showDialogConfirmation(achat);
                    });
                    reclamer.addActionListener((evt) -> {

                    });
                } else {
                    accuser.setVisible(false);
                    reclamer.setVisible(false);
                }
            
                isFirst = false;
            } else { 
               
                Container achatContainer = new  Container(new FlowLayout());
                ((FlowLayout)achatContainer.getLayout()).setFillRows(true);
                achatContainer.setUIID("MyColdGreenTab");
               
                Container itemContainer = new Container(BoxLayout.y());
                //Container for buttons
                FlowLayout layout = new FlowLayout(); layout.setFillRows(true);
                Container buttonContainer = new Container(layout);
                ((FlowLayout)buttonContainer.getLayout()).setFillRows(true);
                //Container to align button on the left
                Container leftButtonContainer = new Container(new FlowLayout());
                ((FlowLayout)leftButtonContainer.getLayout()).setFillRows(false);
                Container righButtonContainer = new Container(new FlowLayout());
                ((FlowLayout)righButtonContainer.getLayout()).setFillRows(false);
                ((FlowLayout)righButtonContainer.getLayout()).setAlign(RIGHT);
                
                Label membre = new Label(achat.getMembre().getUsername(), theme.getImage("twitter (1).png"), "MyLabel1");
                System.out.println("Achat id "+achat.getId());
                Label libelle = new Label(achat.getArticle().getLibelle(), theme.getImage("shopping-store.png"), "MyLabel1");
                Label status = new Label(achat.getStatut(), theme.getImage("package (1).png"), "MyLabel1");
                Label prix = new Label(String.valueOf(achat.getArticle().getPrix()), theme.getImage("coins (1).png"), "MyLabel1");
                Label dateAchat = new Label(achat.getDateHeureAchat().toString(), theme.getImage("calendar (1).png"), "MyLabel1");
                Button accuser = new Button("Accuser", theme.getImage("success.png"));
                
                Button reclamer = new Button("Reclamer", theme.getImage("tasks.png"));
                reclamer.setUIID("RedButton");
                accuser.setUIID("BlueButton");
                leftButtonContainer.add(accuser);
                righButtonContainer.add(reclamer);
                itemContainer.addAll(membre, libelle, prix, status, dateAchat, buttonContainer);
                
                achatContainer.add(itemContainer);
                
                buttonContainer.add(leftButtonContainer);
                buttonContainer.add(righButtonContainer);
                
                achatsForm.add(achatContainer);
                if (achat.getStatut().equalsIgnoreCase("effectue") == false){
                     accuser.addActionListener((evt) -> {
                    showDialogConfirmation(achat);
                });
                reclamer.addActionListener((evt) -> {
                    
                });
                } else {
                    accuser.setVisible(false);
                    reclamer.setVisible(false);
                }
               
            }
        }
        
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("Notified!");
        initForm();
//        show();
    }

    @Override
    public void show() {
        System.out.println("Show Form achaaaaaaat!");
        //initForm();
        achatsForm.show();
    }
    
    public void showDialogConfirmation(Achat achat) {
        Dialog dialog = new Dialog("Voulez-vous continuer?", BoxLayout.y());
        Button oui = new Button("Oui", theme.getImage("checked.png"), "BlueButton");
        oui.addActionListener((evt) -> {
            accuserReeption(achat);
        });
        dialog.addComponent(oui);
        Button non = new Button("Non", theme.getImage("cancel (2).png"), "RedButton");
        non.addActionListener((evt) -> {
            dialog.dispose();
        });
        dialog.addComponent(non);
        dialog.setTimeout(15000);
        dialog.showPacked(BorderLayout.CENTER, true);
    }
    
    private String messageHttp;
    public void accuserReeption(Achat achat) {
        ConnectionRequest request = new ConnectionRequest(Tahwissa.URL+"/boutique/accuser/achat", true){
            @Override
            protected void readResponse(InputStream input) throws IOException {
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser jp = new JSONParser();
                Map response = jp.parseJSON(reader);
                messageHttp = (String) response.get("message");
                
                if(getResponseCode() == 200) {
                    System.out.println("Nombre des achats : "+AchatList.getListAchats().size());
                    achat.setStatut("EFFECTUE");
                    initForm();
                }
            }
            
        };
        
        request.addArgument("id_achat", String.valueOf(achat.getId()));
        request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
        NetworkManager.getInstance().addToQueueAndWait(request);
        Dialog dialog = new Dialog(messageHttp, BoxLayout.y());
        Button ok = new Button("OK", theme.getImage("checked.png"), "RedButton");
        ok.addActionListener((evt) -> {
            dialog.dispose();
        });
        dialog.add(ok);
        dialog.setTimeout(7000);
        dialog.showDialog();
    }
    
}
