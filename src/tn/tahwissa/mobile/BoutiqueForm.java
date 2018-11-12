/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import tn.tahwissa.mobile.List.ArticleList;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.entity.ImageShop;
import tn.tahwissa.mobile.parsing.ArticlesParser;
import tn.tahwissa.mobile.util.MenuManager;

/**
 *
 * @author esprit
 */
public class BoutiqueForm extends Form implements Observer{
    private Resources theme;
           

    private Form boutiqueForm;
    private ArticlesParser articleParser;
    private UIBuilder uib;

    public BoutiqueForm(Resources theme) {
        this.theme = theme;
        // theme = UIManager.initFirstTheme(resourceFile);
        this.theme = UIManager.initNamedTheme("/theme", "Theme 1");
        uib = new UIBuilder();
        Container ctnr = uib.createContainer(theme, "Boutique");
        boutiqueForm = ctnr.getComponentForm();
        boutiqueForm.removeAll();
        boutiqueForm.addShowListener((evt) -> {
            boutiqueForm.refreshTheme();
        });
        MenuManager.createMenu(boutiqueForm, theme);
        MenuManager.createLeftMenu(boutiqueForm, theme);
        MenuManager.setTitle(boutiqueForm, "Boutique");
         articleParser = new ArticlesParser();
         if(ArticlesParser.getConnectionRequest() == null) {
             articleParser.handle(this);
         } else {
//             ArticlesParser.getConnectionRequest().kill();
//             ArticleList.getListArticles().clear();
            articleParser.handle(this);
             boutiqueForm.removeAll();
            initBoutique();
             show();
         }
        
    }
    
    public BoutiqueForm(Resources theme, boolean isReset) {
        this.theme = theme;
         uib = new UIBuilder();
        Container ctnr = uib.createContainer(theme, "Boutique");
        boutiqueForm = ctnr.getComponentForm();
        boutiqueForm.removeAll();
        boutiqueForm.addShowListener((evt) -> {
            boutiqueForm.refreshTheme();
        });
        MenuManager.createMenu(boutiqueForm, theme);
        MenuManager.createLeftMenu(boutiqueForm, theme);
        MenuManager.setTitle(boutiqueForm, "Boutique");
         articleParser = new ArticlesParser();
         articleParser.deleteObserver(this);
         resetBoutique();
    }
    
    public void resetBoutique() {
          
        //NetworkManager.getInstance().killAndWait(ArticlesParser.getConnectionRequest());
        
      
        boutiqueForm.removeAll();
        articleParser.handle(this);
        
             //initBoutique();
             
    }

    public void initBoutique() {
        System.out.println("Taille tableau:"+ArticleList.getListArticles().size());
                    for(Article article : ArticleList.getListArticles()) {
                       
                        Label libelle = new Label(theme.getImage("shopping-store.png"));
                       // libelle.setUIID("Libelle");
                       libelle.setUIID("MyLabel");
                        Label prix = new Label(theme.getImage("coins (1).png"));
                       // prix.setUIID("prix");
                       prix.setUIID("MyLabel");
                        Label etat = new Label(theme.getImage("package.png"));
                        etat.setUIID("MyLabel1");
                        ImageViewer viewer = new ImageViewer();
                        ListModel<Image> images = new DefaultListModel<>();
                        List<ImageShop> imgs = article.getImages();
                        Image imageSecondaire;
                        EncodedImage ph = EncodedImage.createFromImage(theme.getImage("duke-no-logos.png").scaled(150, 150), true);
                        imageSecondaire = URLImage.createToStorage(ph, article.getIdImage(), "http://localhost:8000/images/articles/"+article.getIdImage());
//                        System.out.println("Nombre d'images : "+imgs.size());
                        System.out.println("Imge Name: "+article.getIdImage());
                        images.addItem(imageSecondaire);
                        if (imgs != null) {
                            for (int j = 0; j < imgs.size(); j++) {
                                String imageName = imgs.get(j).getImageName();
                                System.out.println("Image name : "+imageName);
                                imageSecondaire = URLImage.createToStorage(ph, imageName, "http://localhost:8000/web/images/articles/"+imageName);
                                images.addItem(imageSecondaire.scaled(150, 150));
                            }
                        }
                        viewer.setImageList(images);
                        String lib = article.getLibelle();
                        lib = (lib.length() >20) ? lib.substring(0, 19)+"..." : lib;
                        libelle.setText(lib);
                        prix.setText(String.valueOf(article.getPrix())+"TND");
                        etat.setText(article.getEtat());
                        
                        Button acheter = new Button("Acheter");
                        acheter.setIcon(theme.getImage("shopping-bag.png"));
                        acheter.setUIID("TransparentButtonWhite");
                        
                        Button moreDetail = new Button(theme.getImage("more (1).png"));
                        moreDetail.setUIID("WhiteButton2");
                        moreDetail.addActionListener((evt) -> {
                            afficheArticle(article);
                        });
                        
                        Container ctnr = new Container(new BorderLayout()); ctnr.setUIID("BoutiqueItem");
                        Container ctnrLibelle = new Container(new FlowLayout(LEFT, TOP));
                        ctnrLibelle.add(libelle);
                        Container ctnrPrix = new Container(new FlowLayout(RIGHT, TOP));
                        ctnrPrix.add(prix);
                        Container ctnrLibellePrix = Container.encloseIn(new GridLayout(1, 2), ctnrLibelle, ctnrPrix);
                        ctnrLibellePrix.setUIID("BoutiquePurpleTab");
                        
                        Container ctnrImage = Container.encloseIn(new FlowLayout(), viewer);
                        ctnrImage.setUIID("ImageContainer");
                        
                        Container ctnrAcheter = new Container(new FlowLayout(LEFT, TOP));
                        ctnrAcheter.add(acheter);
                        Container ctnrMore = new Container(new FlowLayout(RIGHT, CENTER));
                        ctnrMore.add(moreDetail);
                        Container ctnrButtons = Container.encloseIn(new GridLayout(1, 2), ctnrAcheter, ctnrMore);
                        ctnrButtons.setUIID("BoutiquePurpleTab");
                        
                        Container CtnrSouth = Container.encloseIn(BoxLayout.y(), etat, ctnrButtons);
                        CtnrSouth.setUIID("WhiteTab");
                        
                        ctnr.add(BorderLayout.CENTER, ctnrImage);
                        ctnr.add(BorderLayout.NORTH, ctnrLibellePrix);
                        ctnr.add(BorderLayout.SOUTH, CtnrSouth);
                        ctnr.setLeadComponent(moreDetail);
                        boutiqueForm.add(ctnr);
                        //this.show();
                    }
    }

    @Override
    public void show() {
        boutiqueForm.show();
    }

    @Override
    public void update(Observable o, Object arg) {
        //System.out.println("sldkhfmksqdj");
        try {
            initBoutique();
        } catch (java.util.ConcurrentModificationException e) {
            
        }
        
    }

    private void afficheArticle(Article article) {
        DetailsForm detilsForm = new DetailsForm(theme, article);
        detilsForm.show();
    }
    
    
}
