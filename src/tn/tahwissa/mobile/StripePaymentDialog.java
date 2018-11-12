/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.components.ImageViewer;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import tn.tahwissa.mobile.List.ArticleList;

import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.util.LoginManager;
import tn.tahwissa.mobile.util.Tahwissa;

/**
 *
 * @author esprit
 */
public class StripePaymentDialog extends Dialog {
    private Resources theme;
    private Dialog stripePaymentDialog;
    private UIBuilder uib;
    private TextField cardNumber;
    private TextField expYear;
    private TextField expMonth;
    private Button valider;
    private Button annuler;
    private Article article;
    private boolean isBoost;

    public StripePaymentDialog(Resources theme, Article article, boolean isBoost) {
        this.article = article;
        this.theme = theme;
        this.isBoost  = isBoost;
        uib = new UIBuilder();
        stripePaymentDialog = (Dialog) uib.createContainer(theme, "StripeDialog").getComponentForm();
//        ImageViewer image = (ImageViewer) uib.findByName("ImageViewer", stripePaymentDialog);
//        image.setImage(image.getImage().scaledWidth(100));
        cardNumber = (TextField) uib.findByName("CardNumber", stripePaymentDialog);
        expYear = (TextField) uib.findByName("ExpYear", stripePaymentDialog);
        expMonth = (TextField) uib.findByName("ExpMonth", stripePaymentDialog);
        valider = (Button) uib.findByName("Valider", stripePaymentDialog);
        annuler = (Button) uib.findByName("Annuler", stripePaymentDialog);
    }

    public StripePaymentDialog(Resources theme) {
         this.theme = theme;
        uib = new UIBuilder();
        stripePaymentDialog = (Dialog) uib.createContainer(theme, "StripeDialog").getComponentForm();
       
    }
    
    private String message;
    public void init() {
        
        valider.addActionListener((evt) -> {
            String number = cardNumber.getText();
            String month = expMonth.getText();
            String year = expYear.getText();
            
            payerStripe(number, month, year);
        });
        
        annuler.addActionListener((evt) -> {
            stripePaymentDialog.dispose();
        });
    }

    private void payerStripe(String number, String month, String year) {
        String url = (isBoost) ? Tahwissa.URL+"/boutique/article/booster/stripe" : Tahwissa.URL+"/boutique/commander/article/stripe";
        ConnectionRequest request = new ConnectionRequest(url, true){
            @Override
            protected void readResponse(InputStream input) throws IOException {
                InputStreamReader reader = new InputStreamReader(input);
                JSONParser jp = new JSONParser();
                Map response = jp.parseJSON(reader);
                message = (String) response.get("message");
        
                Dialog dialog = new Dialog(new LayeredLayout());
                dialog.setTitle(message); 
                dialog.setTimeout(5000);
                if (getResponseCode() == 200) {
                    if(isBoost) article.setBoosted(true); else {
                        ArticleList.removeItem(article);
                    }
                }
                Button ok = new Button("Ok", theme.getImage("checked.png"), "BlueButton");
                ok.addActionListener((evt) -> {
                    dialog.dispose(); 
                    stripePaymentDialog.dispose();
                });
                dialog.add(ok);
                dialog.show();
                
                
               // BoutiqueForm boutique = new BoutiqueForm(theme); boutique.show();
                
            }
        };
        
        request.addArgument("card_number", number);
        request.addArgument("exp_year", year);
        request.addArgument("exp_month", month);
        request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
        request.addArgument("id_article", String.valueOf(article.getId()));
        NetworkManager.getInstance().addToQueueAndWait(request);
         
    }

    @Override
    public void show() {
        init();
        stripePaymentDialog.showDialog();
    }
    
    
    
    
}
