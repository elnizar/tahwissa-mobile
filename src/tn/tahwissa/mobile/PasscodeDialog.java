/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import tn.tahwissa.mobile.List.ArticleList;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.entity.User;
import tn.tahwissa.mobile.util.AchatRequest;

/**
 *
 * @author esprit
 */
public class PasscodeDialog extends Dialog{
    private TextField passcode;
    private Dialog dialogPasscode;
    private UIBuilder uib;
    private Resources theme;
    private TextField repasscode;
    private Button poursuivre;
    private Label message;
    private boolean isBoost;
    private Button annuler;

    public PasscodeDialog(Resources theme, boolean isBoost) {
        uib = new UIBuilder();
        this.theme = theme;
        this.isBoost = isBoost;
        dialogPasscode = (Dialog) uib.createContainer(theme, "PasscodeDialog").getComponentForm(); 

        passcode = (TextField) uib.findByName("Passcode", dialogPasscode);
        repasscode = (TextField) uib.findByName("RePasscode", dialogPasscode);
       
        message = (Label) uib.findByName("Message", dialogPasscode);
        message.setHidden(true);
        annuler = (Button) uib.findByName("Annuler", dialogPasscode);
    }
    
    public void poursuivreAchat(Article article) {
        
         poursuivre = (Button) uib.findByName("Poursuivre", dialogPasscode);
            poursuivre.addActionListener((evt) -> {
                System.out.println("fsgdsd");
                if (passcode.getText().equals(repasscode.getText()) == false) {
                    System.out.println("Passcode not Okei!");
                    System.err.println(passcode.getText());
                message.setText("Vérifiez votre Passcode !");
                message.setHidden(false);
            } else {
                System.out.println("Passcode Okei!");
                System.out.println(passcode.getText());
                AchatRequest.sendAchatRequest(article, passcode.getText(), isBoost);
                if(AchatRequest.getHttpCode() == 200) {
                    //System.out.println("Paiement avec succès");
                    Dialog dialog = new Dialog(new LayeredLayout());
                    dialog.setTitle(AchatRequest.getMessage());
                    Button ok = new Button("OK", theme.getImage("checked.png"), "RedButton"); 
                    ok.addActionListener((et) -> {
                        dialog.dispose();
                    });
                    dialog.add(ok);
                    if(isBoost) article.setBoosted(true); else {
                        //article.setVendu(true);
                        ArticleList.removeItem(article);
                    }
                    dialog.show();
                    dialogPasscode.dispose();
                    
                } else {
                    message.setText(AchatRequest.getMessage());
                    System.out.println("Qqch mal"+AchatRequest.getHttpCode());
                    message.setHidden(false);
                }
            }
            });
      
         annuler.addActionListener((evt) -> {
             dialogPasscode.dispose();
         });
         this.show();
    }

    @Override
    public void show() {
        dialogPasscode.show();  
    }
    
    
    
}
