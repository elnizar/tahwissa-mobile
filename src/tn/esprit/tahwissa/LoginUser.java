/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import service.userService;

/**
 *
 * @author Elhraiech Nizar
 */
public class LoginUser {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme_2_1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    public void start() {
         theme = UIManager.initFirstTheme("/theme_2_1");
        UIBuilder ui = new UIBuilder();
        Form form = ui.createContainer(theme, "GUI 1").getComponentForm();
        form.setUIID("Logins");
        form.setTitle("Tahwissa");
        TextField mail = (TextField) ui.findByName("username", form.getContentPane());
        TextField pswd = (TextField) ui.findByName("password", form.getContentPane());
        Button connexion = (Button) ui.findByName("connexion", form.getContentPane());
        Button connexionfacebook = (Button) ui.findByName("Facebook", form.getContentPane());
        Button sinscrire = (Button) ui.findByName("signup", form.getContentPane());
        userService userservice = new userService();
        //        form.add(connexionfacebook);
        connexionfacebook.addActionListener(lll->{
            System.out.println("houniiiiii");
            FacebookLogin.signIn();
        });
        connexion.addActionListener(l -> {
            if (mail.getText().equals("") || pswd.getText().equals("")) {
                Dialog.show("Confirmation", "Champ manqunat", "Ok", null);
                return;

            } else {
                System.out.println(mail.getText());
                userservice.connexionuser(mail.getText(), pswd.getText());
            }
        });
        sinscrire.addActionListener(ll -> {
            SignupFb s = new SignupFb();
            s.init();
            s.start();
        });
        form.show();
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }
}
