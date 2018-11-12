/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.components.ImageViewer;
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
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import entity.User;
import java.util.ArrayList;
import service.userService;
import util.MenuManager;

/**
 *
 * @author Elhraiech Nizar
 */
public class ShowProfile {

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

    public void start(String mail) {
        theme = UIManager.initFirstTheme("/theme_2");

        userService userservice = new userService();
        userservice.getUsser(mail);

    }
    int compt = 0;

    public void setText(String nom, String prenom, String img, String password, String email) {
        theme = UIManager.initFirstTheme("/theme_2");
        Form f = new Form("Profile", BoxLayout.y());
        f.getToolbar().setUIID("myToolbarProfile");
        MenuManager.createMenu(f, theme);
        Container c3 = new Container(new FlowLayout(Component.CENTER));
        Container c1 = new Container(new FlowLayout(Component.CENTER));

        Dimension d = new Dimension(200, 30);
        System.out.println("hédhi l'image " + img);

        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(150, 150, 0xffffff), true);
        Image roundMask = Image.createImage(placeholder.getWidth(), placeholder.getHeight(), 0xff000000);
        Graphics gr = roundMask.getGraphics();
        gr.setColor(0xffffff);
        gr.fillArc(0, 0, placeholder.getWidth(), placeholder.getHeight(), 0, 360);

        URLImage.ImageAdapter ada = URLImage.createMaskAdapter(roundMask);
        Image i = URLImage.createToStorage(placeholder, img, "http://localhost/" + img, ada);
        Label picture = new Label("", "Container");

        //image = new ImageViewer(imagee);
        picture.setIcon(i);
        c3.add(picture);
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
        Button message = new Button("message", theme.getImage("question.png"), "buttonPrimary");
        message.addActionListener((evt) -> {
            userService us = new userService();
            NewMessage newMessage = new NewMessage( us.getUserNotConnectedByMail(email).getId());
            newMessage.start();
        });
        Button signalisation = new Button("Signaler", theme.getImage("mail.png"), "buttonPrimary");
        Container c2 = new Container(new FlowLayout(Component.CENTER));
        c2.add(message);
        c2.add(signalisation);
        f.add(c2);
        //f.add(image);
        System.out.println(User.testemail);
        mail.setText(email);
        f.getToolbar().addCommandToRightBar("Back", null, (evennt) -> {
            profile pr = new profile();
            pr.init(current);
            pr.start();
        });
        f.show();
    }
}
