/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.ui.validation.Validator;
import java.util.Date;
import service.userService;

/**
 *
 * @author Elhraiech Nizar
 */
public class SignupFb {

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
    long dtest;
    Form form;
    TextField nom;
    TextField prenom;
    TextField email;

    public void start() {
        UIBuilder ui = new UIBuilder();
        form = ui.createContainer(theme, "Signup").getComponentForm();
        //form1.setUIID("Logins");

        Validator v = new Validator();
        Date da = new Date();
        Picker p = new Picker();
        p.addActionListener(l -> {
            if (p.getDate().getTime() > (da.getTime())) {
                Dialog.show("Confirmation", "Veuillez saisir une date inférieur a " + da, "Ok", null);
                dtest = p.getDate().getTime();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            s = formatter.format(p.getDate());
        });
        form.addComponent(0, p);

        nom = (TextField) ui.findByName("nom", form.getContentPane());
        prenom = (TextField) ui.findByName("prenom", form.getContentPane());
        email = (TextField) ui.findByName("email", form.getContentPane());
        //Picker date = (Picker) ui.findByName("date", form);
        TextField pswd = (TextField) ui.findByName("mdp", form.getContentPane());
        v.addConstraint(email, RegexConstraint.validEmail("You must enter a valid email address to receive confirmation"));
        Button valider = (Button) ui.findByName("sinscrire", form.getContentPane());
        v.addConstraint(pswd, new LengthConstraint(8));
        valider.addActionListener(l -> {
            if (nom.getText().equals("") || prenom.equals("") || email.equals("") || pswd.equals("")) {
                Dialog.show("Confirmation", "Veuillez saisir tout les champs", "Ok", null);
                return;
            } 
//            else if (dtest > (da.getTime())) {
//                System.out.println(dtest);
//                Dialog.show("Confirmation", "Veuillez saisir une date inférieur a " + da, "Ok", null);
//                return;
//            } 
            else if (!v.isValid()) {
                Dialog.show("Confirmation", "mot de pass ou email invalide", "Ok", null);
            } else {
                userService userservice = new userService();
                userservice.addUser(nom.getText(), prenom.getText(), email.getText(), pswd.getText(), s);
            }
        });
        form.getToolbar().addCommandToLeftBar("", theme.getImage("back-arrow.png"), (vzr) -> {
            LoginUser lg = new LoginUser();
            lg.init(current);
            lg.start();
        });
        form.show();
    }

}
