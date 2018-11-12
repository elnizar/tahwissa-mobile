/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import tn.tahwissa.mobile.List.MesArticlesList;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.parsing.AchatsParser;
import tn.tahwissa.mobile.parsing.MesArticlesParser;
import tn.tahwissa.mobile.util.LoginManager;
import tn.tahwissa.mobile.util.MenuManager;
import tn.tahwissa.mobile.util.Tahwissa;

/**
 *
 * @author esprit
 */
public class MesArticlesForm extends Form implements Observer{
    private Form mesArticlesForm;
    private MesArticlesParser mesArticlesParser;
    private UIBuilder uib;
    private Resources theme;
    private Container itemContainer;
    private Container rightContainer;
    private Container leftContainer;
    private Container detailsContainer;
    private Container leftDetailsContainer;
    private Container rightDetailsContainer;
    private Container buttonContainer;
    private Button deleteButton;
    private Label libelleLabel;
    private Label prixLabel;
    private Label etatLabel;
    private SpanLabel descriptionLabel;
    private Label venduLabel;
    private Label boosteLabel;
    private Button venduButton;
    private Button boosterButton;
    private ImageViewer imageProd;
    private boolean isFirst = true;

    public MesArticlesForm(Resources theme) {
        this.theme = theme;
        uib = new UIBuilder();
        UIBuilder.registerCustomComponent("SpanLabel", SpanLabel.class);
        mesArticlesForm = uib.createContainer(theme, "mesArticles").getComponentForm();
        MenuManager.createMenu(mesArticlesForm, theme);
        MenuManager.createLeftMenu(mesArticlesForm, theme);
        MenuManager.setTitle(mesArticlesForm, "Mes Articles");

        mesArticlesParser = MesArticlesParser.getInstance();
        if (MesArticlesParser.isRequestSent() == false)  {
            mesArticlesParser.handle(this);
        } else {
           // MesArticlesParser.getRequest().resume();
           MesArticlesList.getListArticles().clear();
           mesArticlesParser.handle(this);
          //  show();
        }
    }
    
    public void initMesArticlesForm() {
        
        for (Article article : MesArticlesList.getListArticles()) {
            initDetails(article);
        }
       
    }
    
    public void initDetails(Article article) {
        if (! isFirst) {
            libelleLabel = new Label(theme.getImage("shopping-bag (1).png"));
            libelleLabel.setUIID("MyLabel1");
            prixLabel = new Label(theme.getImage("change.png"));
            prixLabel.setUIID("MyLabel1");
            etatLabel = new Label(theme.getImage("approve.png"));
            etatLabel.setUIID("MyLabel1");
            descriptionLabel = new SpanLabel();
            descriptionLabel.setTextUIID("MyLabel1");
            descriptionLabel.setIcon(theme.getImage("content.png"));
            descriptionLabel.setIconUIID("Container");
            venduLabel = new Label(theme.getImage("sold (1).png"));
            venduLabel.setUIID("MyLabel1");
            boosteLabel = new Label(theme.getImage("favorite (1).png"));
            boosteLabel.setUIID("MyLabel1");
            deleteButton = new Button(theme.getImage("cancel (4).png"));
            deleteButton.setUIID("WhiteButton");
            imageProd = new ImageViewer();
            venduButton = new Button("Vendu", theme.getImage("checked.png"));
            venduButton.setUIID("BlueButton");
            boosterButton = new Button("Booster", theme.getImage("startup.png"));
            boosterButton.setUIID("RedButton");
            
            Container deleteButtonContainer = new Container(new FlowLayout(RIGHT));
            deleteButtonContainer.add(deleteButton);
            
            itemContainer = new Container(BoxLayout.x());
            itemContainer.setUIID("MyArticleContainer");
            rightContainer = new Container(new GridLayout(1, 1)); rightContainer.setUIID("ImageAchatContainer");
            rightContainer.add(imageProd);
            leftContainer = new Container(BoxLayout.y()); leftContainer.setUIID("MyColdGreenTab");
            
            itemContainer.addAll(rightContainer, leftContainer);
            
            detailsContainer = new Container(new GridLayout(1, 2));
            leftDetailsContainer = new Container(BoxLayout.y());
            rightDetailsContainer = new Container(BoxLayout.y());
            detailsContainer.addAll(leftDetailsContainer, rightDetailsContainer);
       
            buttonContainer = new Container(BoxLayout.x());
            buttonContainer.addAll(venduButton, boosterButton);
            leftContainer.addAll(deleteButtonContainer, detailsContainer, buttonContainer);
            
            leftDetailsContainer.addAll(libelleLabel, prixLabel, etatLabel);
            rightDetailsContainer.addAll(descriptionLabel, venduLabel, boosteLabel); 
            mesArticlesForm.add(itemContainer);
        } else {
            imageProd = (ImageViewer) uib.findByName("ImageProd", mesArticlesForm);
            imageProd.setImage(imageProd.getImage().scaled(75, 75));
            libelleLabel = (Label) uib.findByName("Libelle", mesArticlesForm);
            prixLabel = (Label) uib.findByName("Prix", mesArticlesForm);
            etatLabel = (Label) uib.findByName("Etat", mesArticlesForm);
            descriptionLabel = (SpanLabel) uib.findByName("Description", mesArticlesForm);
            venduLabel = (Label) uib.findByName("VenduLabel", mesArticlesForm);
            boosteLabel = (Label) uib.findByName("Booste", mesArticlesForm);
            venduButton = (Button) uib.findByName("Vendu", mesArticlesForm);
            boosterButton = (Button) uib.findByName("Booster", mesArticlesForm);
            deleteButton = (Button) uib.findByName("Delete", mesArticlesForm);
             isFirst = false;
        }
        EncodedImage ph = EncodedImage.createFromImage(theme.getImage("duke-no-logos.png").scaled(75, 75), true);
        Image image = URLImage.createToStorage(ph, article.getIdImage(), Tahwissa.URL_IMAGE+article.getIdImage());
        image.scaled(75, 75);
        imageProd.setImage(image);
        if(article.getLibelle().length() > 10) libelleLabel.setText(article.getLibelle().substring(0,10)+"..."); else libelleLabel.setText(article.getLibelle());
        prixLabel.setText(String.valueOf(article.getPrix()));
        etatLabel.setText(article.getEtat());
        if (article.getDescription().length() > 10)
        descriptionLabel.setText(article.getDescription().substring(0, 7)+"..."); else descriptionLabel.setText(article.getDescription());
        if (article.isVendu()) {
            venduLabel.setText("Vendu"); venduButton.setVisible(false); boosterButton.setVisible(false);
        } else venduLabel.setText("Non vendu");
        if (article.isBoosted()) {
            boosteLabel.setText("Boosté");
            boosterButton.setVisible(false);
        } else boosteLabel.setText("Non Boosté");
        venduButton.addActionListener((evt) -> {
            setArticleVendu(article);
        });
        boosterButton.addActionListener((evt) -> {
            booster(article);
        });
        deleteButton.addActionListener((evt) -> {
            deleteArticle(article);
        });
        
    }
    private String  messageRequest;
    public void setArticleVendu(Article article) {
        ConnectionRequest request = new ConnectionRequest(Tahwissa.URL+"/boutique/article/vendu", true) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser jp = new JSONParser();
                Map response = jp.parseJSON(reader);
                messageRequest = (String) response.get("message");
                if (getResponseCode() == 200) {
                    article.setVendu(true);
                    venduLabel.setText("Vendu");
                    venduButton.setVisible(false);
                    mesArticlesForm.removeAll();
                    initTheme();
                }
                Dialog dialog = new Dialog(messageRequest, new LayeredLayout());
                dialog.setTimeout(4000);
                dialog.show();
            }    
        };
        System.out.println("Article Owner : "+article.getOwner().getId());
        System.out.println("User id: "+1);
        request.addArgument("id_article", String.valueOf(article.getId()));
        request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
        NetworkManager.getInstance().addToQueueAndWait(request);
        
    }

    @Override
    public void show() {
        mesArticlesForm.removeAll();
        initMesArticlesForm();
        mesArticlesForm.show();
    }
    
    public void booster(Article article) {
        PaymentChoiceForm paymentChoice =new PaymentChoiceForm(theme, true, article);
        paymentChoice.show();
    }

    @Override
    public void update(Observable o, Object arg) {
       show();
    }
    
    public void initTheme() {
                mesArticlesForm = uib.createContainer(theme, "mesArticles").getComponentForm();
                isFirst = true;
                MenuManager.createMenu(mesArticlesForm, theme);
                MenuManager.createLeftMenu(mesArticlesForm, theme);
                 MenuManager.setTitle(mesArticlesForm, "Mes Articles");
                mesArticlesForm.refreshTheme();
                initMesArticlesForm();  
    }

    private void deleteArticle(Article article) {
         ConnectionRequest request = new ConnectionRequest(Tahwissa.URL+"/boutique/my_article/delete", true) {
            @Override
            protected void readResponse(InputStream input) throws IOException {
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser jp = new JSONParser();
                Map response = jp.parseJSON(reader);
                messageRequest = (String) response.get("message");
                if(getResponseCode() == 200) {
                    MesArticlesList.removeItem(article);
                    mesArticlesForm.removeAll();
                    initTheme();
                }
                Dialog dialog = new Dialog(messageRequest, new LayeredLayout());
                dialog.setTimeout(6000);
                dialog.show();
            }    
        };
        
        request.addArgument("id_article", String.valueOf(article.getId()));
        request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
        NetworkManager.getInstance().addToQueueAndWait(request);
    }
}
