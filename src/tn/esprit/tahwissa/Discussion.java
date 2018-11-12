/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.tahwissa;

import com.codename1.components.FileTree;
import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import entity.Messagerie;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import service.MessagerieService;
import util.MenuManager;

/**
 *
 * @author User
 */
public class Discussion {

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

    Form f;
    List<Messagerie> discussion = new ArrayList<>();

    public Discussion(List<Messagerie> messages) {
        discussion = messages;
    }
    Container messagesContainer;

    public void start() {

        UIBuilder uibuilder = new UIBuilder();
        UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
        UIBuilder.registerCustomComponent("FileTree", FileTree.class);

        Container container = uibuilder.createContainer(theme, "inbox");

        f = new Form("Discussion", new BorderLayout());
        messagesContainer = new Container(BoxLayout.y());
        MenuManager.createMenu(f, theme);
        loadDiscussion();
        f.add(BorderLayout.CENTER, messagesContainer);
        f.getToolbar().addCommandToOverflowMenu("Supprimer cette discussion", null, (evt) -> {
            Messagerie m = new Messagerie();
            m.setDateHeureEnvoi(new Date().toString());
            m.setSender_id(LoginManager.getUser().getId());
            if (discussion.get(0).getSender_id() != m.getSender_id()) {
                m.setReciever_id(discussion.get(0).getSender_id());
            } else {
                m.setReciever_id(discussion.get(0).getReciever_id());
            }
            MessagerieService.deleteConv(m.getReciever_id());
            Inbox i = new Inbox();
            i.init(current);
            i.start();
        });
        Container repondre = new Container(BoxLayout.x());
        TextField repondreText = new TextField("");
        repondreText.setColumns(repondreText.getColumns() * 2 / 3 + 2);
        repondreText.setHint("Répondre...");
        repondreText.setUIID("inputText");
        Label envoyer = new Label();
        envoyer.setIcon(theme.getImage("forward.png"));
        envoyer.setFocusable(true);
        envoyer.addPointerPressedListener((event) -> {
            Messagerie m = new Messagerie();
            m.setDateHeureEnvoi(new Date().toString());
            m.setSender_id(LoginManager.getUser().getId());
            if (discussion.get(0).getSender_id() != m.getSender_id()) {
                m.setReciever_id(discussion.get(0).getSender_id());
            } else {
                m.setReciever_id(discussion.get(0).getReciever_id());
            }
            m.setContenuMsg(repondreText.getText());
            addMessage(m);
            repondreText.setText("");
            MessagerieService.addNewMessage(m);

            f.refreshTheme();
        });

        repondre.add(repondreText);
        repondre.add(envoyer);

        f.add(BorderLayout.SOUTH, repondre);
        f.setScrollableY(false);
        repondre.setScrollableY(false);
        messagesContainer.setScrollableY(true);

        f.show();
        // refresh();
    }

    public void addMessage(Messagerie msg) {
        final Messagerie message = msg;

        Container messageContainer = new Container(new BorderLayout());
        Container messageSubContainer = new Container(new BorderLayout());
        Label date = new Label(message.getDateHeureEnvoi());
        SpanLabel contenu = new SpanLabel(message.getContenuMsg());
        messageContainer.setSize(new Dimension(Display.getInstance().getDisplayWidth() / 2, messageContainer.getLayoutHeight()));

        messageSubContainer.add(BorderLayout.NORTH, contenu);
        messageSubContainer.add(BorderLayout.WEST, date);
        if (message.getSender_id() == LoginManager.getUser().getId()) {
            messageContainer.add(BorderLayout.EAST, messageSubContainer);
            messageContainer.setUIID("messageOut");
        } else {
            messageContainer.add(BorderLayout.WEST, messageSubContainer);
            messageContainer.setUIID("messageIn");
        }
        //System.out.println("here");
        messagesContainer.add(messageContainer);
    }

    public void loadDiscussion() {

        for (int i = 0; i < discussion.size(); i++) {
            final Messagerie message = discussion.get(i);

            Container messageContainer = new Container(new BorderLayout());
            Container messageSubContainer = new Container(new BorderLayout());
            Label date = new Label(message.getDateHeureEnvoi());
            SpanLabel contenu = new SpanLabel(message.getContenuMsg());
            messageContainer.setSize(new Dimension(Display.getInstance().getDisplayWidth() / 2, messageContainer.getLayoutHeight()));

            messageSubContainer.add(BorderLayout.NORTH, contenu);
            messageSubContainer.add(BorderLayout.WEST, date);
            if (message.getSender_id() == LoginManager.getUser().getId()) {
                messageContainer.add(BorderLayout.EAST, messageSubContainer);
                messageContainer.setUIID("messageOut");
            } else {
                messageContainer.add(BorderLayout.WEST, messageSubContainer);
                messageContainer.setUIID("messageIn");
            }
            //System.out.println("here");
            messagesContainer.add(messageContainer);
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
