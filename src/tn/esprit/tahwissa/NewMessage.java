/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.GenericListCellRenderer;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import entity.Messagerie;
import entity.User;
import java.util.HashMap;
import java.util.Map;
import service.MessagerieService;

/**
 *
 * @author User
 */
public class NewMessage {

    private Form current;
    private Resources theme;
    private int reciever;
    Label l;

    public NewMessage(int receiver) {
        this.reciever = receiver;
    }

    
    
    public void init(Object context) {
        theme = UIManager.initNamedTheme("/theme", "Theme1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    String reciver;

    public void start() {
        theme = UIManager.initNamedTheme("/theme", "Theme1");
        UIBuilder uibuilder = new UIBuilder();
        Form f1 = uibuilder.createContainer(theme, "newMessage").getComponentForm();
//         Form hi = new Form("ComboBox", new BoxLayout(BoxLayout.Y_AXIS));
        //ComboBox cmb = (ComboBox) uibuilder.findByName("ComboBox", f1.getContentPane());
        TextField tf = (TextField) uibuilder.findByName("TextField", f1.getContentPane());
        TextArea txt = (TextArea) uibuilder.findByName("TextArea", f1.getContentPane());
        txt.setUIID("inputText");
        txt.setText("");
        Button bt = (Button) uibuilder.findByName("Button", f1.getContentPane());
        //ListModel<String> lm = cmb.getModel();

       // cmb.addSelectionListener((oldid, newid) -> {
         //   reciver = lm.getItemAt(newid);
        //});
        bt.addActionListener(l -> {
            System.out.println(txt.getText());
            MessagerieService ms = new MessagerieService();
            
            
            LoginManager lg = new LoginManager();
            User u = lg.getUser();

            Messagerie message = new Messagerie(txt.getText(), u.getId(), reciever,false,false);

            ms.NewMessage(message);
                Inbox inb = new Inbox();
                inb.init(current);
                inb.start();
                

            
        });
        f1.show();
        
    }
//    public void showForm() {
//  Form hi = new Form("ComboBox", new BoxLayout(BoxLayout.Y_AXIS));
//  ComboBox<Map<String, Object>> combo = new ComboBox<> (
//          createListEntry("A Game of Thrones", "1996"),
//          createListEntry("A Clash Of Kings", "1998"),
//          createListEntry("A Storm Of Swords", "2000"),
//          createListEntry("A Feast For Crows", "2005"),
//          createListEntry("A Dance With Dragons", "2011"),
//          createListEntry("The Winds of Winter", "2016 (please, please, please)"),
//          createListEntry("A Dream of Spring", "Ugh"));
//  
//  combo.setRenderer(new GenericListCellRenderer<>(new MultiButton(), new MultiButton()));
//  hi.show();
//}
//
//private Map<String, Object> createListEntry(String name, String date) {
//    Map<String, Object> entry = new HashMap<>();
//    entry.put("Line1", name);
//    entry.put("Line2", date);
//    return entry;
//}
}
