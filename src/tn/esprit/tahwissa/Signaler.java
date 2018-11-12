/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import entity.Signalisation;
import entity.User;
import service.SignalisationService;

/**
 *
 * @author User
 */
public class Signaler {
    
    private Form current;
    private Resources theme;
    Label l;

    public void init(Object context) {
        theme = UIManager.initNamedTheme("/theme", "Theme1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    

    public void start() {

        UIBuilder uibuilder = new UIBuilder();
        Form f1 = uibuilder.createContainer(theme, "Signalisation").getComponentForm();
        
        //ComboBox cmb = (ComboBox) uibuilder.findByName("ComboBox", f1.getContentPane());
        TextField tf = (TextField) uibuilder.findByName("TextField", f1.getContentPane());
        tf.setUIID("inputText");
        tf.setText("");
        TextArea txt = (TextArea) uibuilder.findByName("TextArea", f1.getContentPane());
        txt.setUIID("inputText");
        txt.setText("");
        Button bt = (Button) uibuilder.findByName("signaler", f1.getContentPane());
        
        bt.addActionListener(l -> {
            System.out.println(txt.getText());
            SignalisationService ms = new SignalisationService();
            LoginManager lg = new LoginManager();
            User u = lg.getUser();

            Signalisation sig = new Signalisation( u.getId(),tf.getText(),txt.getText(), 2);
           
                ms.NewSignal(sig);

        });
        f1.show();
    }
    
}
