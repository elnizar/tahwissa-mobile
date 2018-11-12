/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.entity.ImageShop;
import tn.tahwissa.mobile.util.AchatRequest;
import tn.tahwissa.mobile.util.MenuManager;

/**
 *
 * @author esprit
 */
public class DetailsForm extends Form{
    private Resources theme;
    private Form detailsForm;
    private UIBuilder uib;
    private ImageViewer imageProd;
    private Label libelle;
    private Label prix;
    private Label etat;
    private SpanLabel description;
    private Button acheter;
    private Article article;

    public DetailsForm() {
    }

    public DetailsForm(Resources theme, Article article) {
        this.theme = theme;
        this.article = article;
        uib = new UIBuilder();
         UIBuilder.registerCustomComponent("SpanLabel", SpanLabel.class);
        detailsForm = uib.createContainer(this.theme, "articleDetail").getComponentForm();
        MenuManager.createMenu(detailsForm, theme);
        MenuManager.createLeftMenu(detailsForm, theme);
        libelle = (Label) uib.findByName("Libelle", detailsForm);
        prix = (Label) uib.findByName("Prix", detailsForm);
        etat = (Label) uib.findByName("Etat", detailsForm);
        description = (SpanLabel) uib.findByName("Description", detailsForm);
        imageProd = (ImageViewer) uib.findByName("imageArticle", detailsForm);
        acheter = (Button) uib.findByName("Acheter", detailsForm);
        imageProd = (ImageViewer) uib.findByName("imageArticle", detailsForm);
        //imageProd =new ImageViewer();
      //  Image i = image.
    }
    
    public void initForm() {
         detailsForm = uib.createContainer(this.theme, "articleDetail").getComponentForm();
        MenuManager.createMenu(detailsForm, theme);
        MenuManager.createLeftMenu(detailsForm, theme);
        libelle = (Label) uib.findByName("Libelle", detailsForm);
        prix = (Label) uib.findByName("Prix", detailsForm);
        etat = (Label) uib.findByName("Etat", detailsForm);
        description = (SpanLabel) uib.findByName("Description", detailsForm);
        imageProd = (ImageViewer) uib.findByName("imageArticle", detailsForm);
        acheter = (Button) uib.findByName("Acheter", detailsForm);
        imageProd = (ImageViewer) uib.findByName("imageArticle", detailsForm);
    }
    
    public void initDetail() {
        libelle.setText(article.getLibelle());
        prix.setText(String.valueOf(article.getPrix()));
        etat.setText(article.getEtat());
        description.setText(article.getDescription());
        ListModel<Image> listImage = new DefaultListModel<>();
        Image imageSecondaire;
        EncodedImage ph = EncodedImage.createFromImage(theme.getImage("duke-no-logos.png"), true);
        imageSecondaire = URLImage.createToStorage(ph, article.getIdImage(), "http://localhost:8000/images/articles/"+article.getIdImage());
        imageSecondaire.scaled(200, 200);
        System.out.println("Image de l'article à affiché : "+article.getIdImage());
        listImage.addItem(imageSecondaire);
        if( article.getImages() != null ) {
            for(ImageShop img : article.getImages()) {
                 ph = EncodedImage.createFromImage(theme.getImage("duke-no-logos.png"), true);
                 imageSecondaire = URLImage.createToStorage(ph, article.getIdImage(), "http://localhost:8000/images/articles/"+img.getImageName());
                  imageSecondaire.scaled(200, 200);
                 listImage.addItem(imageSecondaire);
            }
        }
        imageProd.setImage(imageSecondaire);
        imageProd.setImageList(listImage);
        detailsForm.refreshTheme();
        if (article.isVendu() == false) 
            acheter.addActionListener((evt) -> {
                //AchatRequest.sendAchatRequest(article, passcode);
                  PaymentChoiceForm payChoiceForm = new PaymentChoiceForm(theme, false, article);
                  payChoiceForm.show();
                  detailsForm.removeAll();
                  new BoutiqueForm(theme).show();
    //            PasscodeDialog passDialog = new PasscodeDialog(theme, false);
    //            passDialog.poursuivreAchat(article);
            });
      
    }
    @Override
    public void show() {
        initDetail();
        this.detailsForm.show();
    }
    
}
