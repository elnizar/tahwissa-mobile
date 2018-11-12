/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.components.ImageViewer;
import com.codename1.io.Log;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.FocusListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import service.userService;
import util.MenuManager;

/**
 *
 * @author Elhraiech Nizar
 */
public class profile {

    private boolean searchShown = false;
    private Form current;
    private Resources theme;
    private String s;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme_2");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    private Container container;
    Label nomprenom;
    ImageViewer image;

    public void start() {
        theme = UIManager.initFirstTheme("/theme_2");

        userService userservice = new userService();
        userservice.getUsser(User.testemail);

    }
    int compt = 0;

    public void setText(String nom, String prenom, String img, String password) {
        theme = UIManager.initFirstTheme("/theme_2");
        Form f2 = new Form();
        f2.getToolbar().setUIID("myToolbarProfile");
        MenuManager.createMenu(f2, theme);
        Button logout = new Button();
        Form f = new Form("Profile", BoxLayout.y());
        f.getToolbar().setUIID("myToolbarProfile");
        MenuManager.createMenu(f, theme);
        f.getToolbar().addCommandToRightBar("", theme.getImage("power.png"), (ev) -> {
            LoginUser lg = new LoginUser();
            LoginManager.user = null;
            lg.init(current);
            lg.start();
        });
        Container c3 = new Container(new FlowLayout(Component.CENTER));
        Container c1 = new Container(new FlowLayout(Component.CENTER));
        final DefaultListModel<String> options = new DefaultListModel<>();

        userService userservice = new userService();
        
        ArrayList<User> ll = new ArrayList<>();
        ll.addAll(userservice.getListUsers());

        for (int i = 0; i < ll.size(); i++) {
            options.addItem(ll.get(i).getEmail());
            System.out.println(ll.get(i).getEmail());
        }
        AutoCompleteTextField auto = new AutoCompleteTextField();
        auto.addPointerPressedListener((evt) -> {
            if (searchShown == true) {
                return;
            }
            searchShown = true;
            AutoCompleteTextField auto2 = new AutoCompleteTextField(options);
            auto2.addPointerPressedListener(lds -> {
                auto2.setMinimumElementsShownInPopup(10);
                options.addSelectionListener((oldid, newid) -> {
                    System.out.println(options.getItemAt(newid));
                    String ss = options.getItemAt(newid);
                    ShowProfile sp = new ShowProfile();
                    sp.init(current);
                    sp.start(ss);
                    return;
                });
            });
            f2.add(auto2);
            f2.show();
            System.out.println(theme.getImage("back-arrow.png"));
            f2.getToolbar().addCommandToLeftBar("Back", theme.getImage("back-arrow.png"), (event) -> {
                searchShown = false;
                init(current);
                start();
            });
        });

        Dimension d = new Dimension(200, 30);
        auto.setPreferredSize(d);
        Button b = new Button("Editer",theme.getImage("edit.png"), "buttonPrimary");
        b.addActionListener(l -> {
            EditerProfile epr = new EditerProfile();
            epr.init();
            epr.start(nom, prenom, password);

        });
        System.out.println("hédhi l'image " + img);

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(150, 150, 0xffffff), true);
        Image roundMask = Image.createImage(placeholder.getWidth(), placeholder.getHeight(), 0xff000000);
        Graphics gr = roundMask.getGraphics();
        gr.setColor(0xffffff);
        gr.fillArc(0, 0, placeholder.getWidth(), placeholder.getHeight(), 0, 360);

        URLImage.ImageAdapter ada = URLImage.createMaskAdapter(roundMask);
        
        Image i = URLImage.createToStorage(placeholder, img+"hk5", "http://localhost/tahwissa/web/images/profilepics/" + img);
        Label picture = new Label("", "Container");

        //image = new ImageViewer(imagee);
        picture.setIcon(i);
        c3.addComponent(auto);
        c1.add(picture);
        // picture.setIcon(imagee);--
        nomprenom = new Label();
        System.out.println(nom + "hédha nom");
        nomprenom.setText(nom + " " + prenom);
        Label mail = new Label();
        f.add(c3);
        f.add(c1);
        //f.add(picture);
        f.add(nomprenom);
        f.add(mail);
        f.add(b);
        //f.add(image);
        System.out.println(User.testemail);
        mail.setText(User.testemail);

        //   Label picture = new Label("", "Container");
//                     Image roundMask = Image.createImage(image.getWidth(), image.getHeight(), 0xff000000);
//        Graphics gr = roundMask.getGraphics();
//        gr.setColor(0xffffff);
//        gr.fillArc(0, 0, 100, 100, 0, 360);
//        Object mask = roundMask.createMask();
//       
//        picture.setIcon(image.applyMask(mask));
//
        f.show();

    }

}
