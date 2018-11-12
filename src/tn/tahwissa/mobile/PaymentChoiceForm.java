/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.ui.Button;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import tn.tahwissa.mobile.entity.Article;

/**
 *
 * @author esprit
 */
public class PaymentChoiceForm extends Dialog{
    private Dialog paymentMethodeDialog;
    private Resources theme;
    private Button payerCompte;
    private Button payerStripe;
    private Button annuler;
    private UIBuilder uib;
    private boolean isBoost;
    private Article article;

    public PaymentChoiceForm(Resources theme, boolean isBoost, Article article) {
        this.theme = theme;
        this.isBoost = isBoost;
        this.article = article;
        uib = new UIBuilder();
        paymentMethodeDialog = (Dialog) uib.createContainer(theme, "PaymentDialog").getComponentForm();
    }
    
    public void init() {
        payerCompte = (Button) uib.findByName("PayerCompte", paymentMethodeDialog);
        payerStripe = (Button) uib.findByName("PayerStripe", paymentMethodeDialog);
        annuler = (Button) uib.findByName("Annuler", paymentMethodeDialog);
        
        payerCompte.addActionListener((evt) -> {
            accountPayment();
            paymentMethodeDialog.dispose();
        });
        
        payerStripe.addActionListener((evt) -> {
            stripePayment();
            paymentMethodeDialog.dispose();
        });
        
        annuler.addActionListener((evt) -> {
            paymentMethodeDialog.setTimeout(1000);
        });
    }

    @Override
    public void show() {
        init();
        paymentMethodeDialog.show();
    }
    
    
    
    public void stripePayment() {
        StripePaymentDialog stripePay = new StripePaymentDialog(theme, article, isBoost);
        stripePay.show();
    }
    
    public void accountPayment() {
        PasscodeDialog passCode = new PasscodeDialog(theme, isBoost);
        passCode.poursuivreAchat(article);
    }
    
}
