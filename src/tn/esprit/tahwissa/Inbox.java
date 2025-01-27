package tn.esprit.tahwissa;

import com.codename1.components.FileTree;
import com.codename1.components.ImageViewer;
import com.codename1.ui.AutoCompleteTextField;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.util.UIBuilder;
import entity.Messagerie;
import entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.MessagerieService;
import service.userService;

import util.MenuManager;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class Inbox {

    private Form current;
    private Resources theme;
    Label l ;
    public void init(Object context) {
        theme = UIManager.initNamedTheme("/theme", "Theme1");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    Form f;
    Map<String,List<Map<String,Object>>> discussions = new HashMap();
    public void start() {

        UIBuilder uibuilder = new UIBuilder();
        UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
        UIBuilder.registerCustomComponent("FileTree", FileTree.class);

        Container container  = uibuilder.createContainer(theme, "inbox");
   
        Button envoyer = (Button) uibuilder.findByName("envoyer",container);
        envoyer.addActionListener((evt)->{
            NewMessage n = new NewMessage(2);
            n.init(current);
            n.start();
        });
        f= container.getComponentForm();     
        MenuManager.createMenu(f, theme);
        loadInbox();
        
        
//        NewMessage inb = new NewMessage();
//                inb.init(current);
//                inb.start();
        
        f.show();
        // refresh();
    }
    private boolean searchShown = false;
    public void loadInbox(){
        discussions = MessagerieService.getInbox();
                        Form f2 = new Form();
                        f2.getToolbar().setUIID("myToolbarInbox");
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
            f2.refreshTheme();
            f2.show();
            System.out.println(theme.getImage("back-arrow.png"));
            f2.getToolbar().addCommandToLeftBar("Back", theme.getImage("back-arrow.png"), (event) -> {
                searchShown = false;
                init(current);
                start();
            });
        });
        f.add(auto);
        final List<Map<String,Object>> senders =  discussions.get("senders");
        final List<Map<String,Object>> dates =  discussions.get("dates");
        for (int i = 0 ; i<senders.size();i++){
            final Map<String,Object> sender = senders.get(i);
            Container userAndDate= new Container(new BorderLayout());
            
            Label userName = new Label((String) senders.get(i).get("name"));
            
            Label date = new Label((String) dates.get(i).get("date"));
            
            userAndDate.add(BorderLayout.NORTH,userName);
            userAndDate.add(BorderLayout.EAST,date);
            userAndDate.setUIID("inboxItem");
            f.add(userAndDate);
            userName.addPointerPressedListener((evt)->{
                List<Messagerie> messages = MessagerieService.getConversation((String)sender.get("mail"));
                Discussion d = new Discussion(messages);
                d.init(current);
                d.start();
               // loadConversation();
            });
            userName.setFocusable(true);
            userAndDate.setLeadComponent(userName);
            
        }
    }
    Form f1;
   
        
    
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
