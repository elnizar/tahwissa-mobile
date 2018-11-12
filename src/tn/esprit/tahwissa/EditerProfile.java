/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.capture.Capture;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.Validator;
import entity.User;
import service.userService;
import util.MenuManager;

/**
 *
 * @author Elhraiech Nizar
 */
public class EditerProfile {

    private Form current;
    private Resources theme;
    private String s;

    public void init() {
        theme = UIManager.initFirstTheme("/theme_2");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    TextField nom;
    TextField prenom;
    TextField password;
    Button upd;
    Image img;
    Button update;
    Component c;
    String pathph = null;
    Label lbl = new Label();

    public void start(String nomm, String prenomm, String passwordd) {
        theme = UIManager.initFirstTheme("/theme_2");
        Form f = new Form("Profile", BoxLayout.y());
        MenuManager.createMenu(f, theme);
        f.getToolbar().setUIID("myToolbarProfile");
        nom = new TextField(nomm);
        prenom = new TextField(prenomm);
        password = new TextField(passwordd);
        Validator v = new Validator();
        v.addConstraint(password, new LengthConstraint(8));

        password = new TextField(passwordd);
        update = new Button("Valider", theme.getImage("round.png"), "buttonPrimary");

        upd = new Button("Modifier photho", theme.getImage("photo-camera.png"));
        upd.addActionListener(l -> {

            pathph = Capture.capturePhoto(Display.getInstance().getDisplayWidth(), -1);
            if (pathph != null) {
                try {
                    img = Image.createImage(pathph);
                    lbl.setIcon(img);
                    System.out.println();
                    c.getComponentForm().revalidate();
                } catch (Exception e) {
                }
            }
        });
        update.addActionListener(l -> {
            if (nom.getText().equals("") || prenom.getText().equals("") || password.getText().equals("") || pathph.equals(null)) {
                Dialog.show("Confirmation", "Veuillez saisir tout les champs", "Ok", null);
                return;
            }  else {
                System.out.println(pathph);
                userService userservice = new userService();
                userservice.updateUser(nom.getText(), prenom.getText(), password.getText(), pathph);
            }

        });
        f.getToolbar().addCommandToLeftBar("", theme.getImage("back-arrow.png"), (vzvvr) -> {
            profile lg = new profile();
            lg.init(current);
            lg.start();
        });
        f.add(img);
        f.add(nom);
        f.add(prenom);
        f.add(password);
        f.add(upd);
        f.add(update);
        f.add(lbl);
        f.show();
    }

    public void setText() {

    }
}
