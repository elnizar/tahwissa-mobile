/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.util;

import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.util.Iterator;
import tn.esprit.tahwissa.Inbox;
import tn.esprit.tahwissa.ListPublication;
import tn.esprit.tahwissa.MyApplication;
import tn.esprit.tahwissa.TwitterPosts;
import tn.esprit.tahwissa.profile;
import tn.tahwissa.mobile.AddForm;
import tn.tahwissa.mobile.BoutiqueForm;
import tn.tahwissa.mobile.MesAchats;
import tn.tahwissa.mobile.MesArticlesForm;
import tn.tahwissa.mobile.parsing.AchatsParser;
import tn.tahwissa.mobile.parsing.ArticlesParser;
import tn.tahwissa.mobile.parsing.MesArticlesParser;


/**
 *
 * @author Meedoch
 */
public class MenuManager {

    public static void createMenu(Form f, Resources theme) {
        String uuid = "sideMenuCommandCenter";
        //theme = UIManager.initFirstTheme("/theme");
        f.getToolbar().setUIID("TopContainer");
        
       // f.getToolbar().getMenuBar().setUIID(uuid);

        f.getToolbar().addCommandToSideMenu("", theme.getImage("user.png").scaled(150, 150), (evt) -> {

        });
        f.getToolbar().addCommandToSideMenu("Mehdi Ben Hemdene", null, (evt) -> {

        });

       // System.out.println(f.getToolbar().getComponentAt(3).getUIID());
       // System.out.println(b);
        f.getToolbar().addCommandToSideMenu("Evenements", theme.getImage("event.png").scaled(20, 20), (evt) -> {
            AchatsParser.getInstance().deleteObservers();
            MesArticlesParser.getInstance().stopObserving();
            MyApplication m = new MyApplication();
            m.init(f);
            m.start();
        });
        f.getToolbar().addCommandToSideMenu("Publications", theme.getImage("publication.png").scaled(20, 20), (evt) -> {
//            TwitterPosts t = new TwitterPosts();
//            t.init(f);
//            t.start();
               ListPublication a = new ListPublication();
               a.init(f);
               a.start();
        });
        f.getToolbar().addCommandToSideMenu("Boutique", theme.getImage("boutique.png").scaled(20, 20), (evt) -> {
            AchatsParser.getInstance().deleteObservers();
            MesArticlesParser.getInstance().stopObserving();
            BoutiqueForm boutiqueForm = new BoutiqueForm(UIManager.initNamedTheme("/theme", "Theme 1"));
            boutiqueForm.show();
        });
        f.getToolbar().addCommandToSideMenu("Messages", theme.getImage("messages.png").scaled(20, 20), (evt) -> {
            AchatsParser.getInstance().deleteObservers();
            MesArticlesParser.getInstance().stopObserving();
            Inbox i = new Inbox();
            i.init(f);
            i.start();
        });
        f.getToolbar().addCommandToSideMenu("Profile", theme.getImage("profile.png").scaled(20, 20), (evt) -> {
            AchatsParser.getInstance().deleteObservers();
            MesArticlesParser.getInstance().stopObserving();
             profile pr = new profile();
                        pr.start();
        });
    }
    
    public static void createLeftMenu(Form f, Resources theme) {
        NetworkManager.getInstance().shutdownSync();
        AchatsParser.getInstance().deleteObservers();
        MesArticlesParser.getInstance().stopObserving();
       
//        if (ArticlesParser.getConnectionRequest() != null)  NetworkManager.getInstance().killAndWait(ArticlesParser.getConnectionRequest()); 
//        if (AchatsParser.isRequestSent()) {
//               NetworkManager.getInstance().killAndWait(AchatsParser.getRequest());
//           }
//        if (MesArticlesParser.getRequest() != null) NetworkManager.getInstance().killAndWait(MesArticlesParser.getRequest());
        
        f.getToolbar().addCommandToOverflowMenu("Ajout article", theme.getImage("addItem.png"), (evt) -> {
           // ArticlesParser.resetConnection();
            AddForm addForm = new AddForm(theme);
            addForm.getToolbar().setTitle("Nouveau Article");
            addForm.getToolbar().getTitleComponent().setUIID("MyLabel");
            addForm.show();
        });
            
        
        f.getToolbar().addCommandToOverflowMenu("Boutique", theme.getImage("boutiqueItems.png"), (evt) -> {
           BoutiqueForm boutique = new BoutiqueForm(theme);
//           if (MesArticlesParser.getRequest() != null) MesArticlesParser.getRequest().pause();
           
            boutique.show();
        });
        
        f.getToolbar().addCommandToOverflowMenu("Mes articles", theme.getImage("mesArticles.png"), (evt) -> {
//                if (ArticlesParser.getConnectionRequest() != null) ArticlesParser.getConnectionRequest().pause();
//                if (AchatsParser.isRequestSent()) {
//                    AchatsParser.getRequest().pause();
//               }
            MesArticlesForm mesArticles = new MesArticlesForm(theme);
           
            //mesArticles.show();
        });
        f.getToolbar().addCommandToOverflowMenu("Mes achats", theme.getImage("items.png"), (evt) -> {
            MesAchats mesAchats = new MesAchats(theme);
            mesAchats.show();
        });
    }
    
    public static void setTitle(Form f, String title) {
        f.getToolbar().setTitle(title);
            f.getToolbar().getTitleComponent().setUIID("MyLabel2");
            
    }

}
