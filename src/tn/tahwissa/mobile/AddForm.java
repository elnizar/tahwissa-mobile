/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UIBuilder;
import com.codename1.util.Callback;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Map;
import tn.tahwissa.mobile.parsing.ArticlesParser;
import tn.tahwissa.mobile.util.LoginManager;
import tn.tahwissa.mobile.util.MenuManager;


/**
 *
 * @author esprit
 */
public class AddForm extends Form implements ActionListener<ActionEvent>{
    private TextField libelle;
    private TextField prix;
    private TextArea description;
    private RadioButton bonneEtat; 
    private RadioButton moyenneEtat; 
    private RadioButton bonneOccasion; 
    private RadioButton nouveau; 
    private Container libellePrixContainer;
    private Container imageContainer;
    private Container southContainer;
    private Container buttonContainer;
    private Button acheter;
    private Button upload;
    private Resources theme;
    private UIBuilder uib;
    private Form addForm;
    private Image imageProd;
    private InputStream imageUpStream;
    private String pathImage;
    
    public  AddForm(Resources theme){
        NetworkManager.getInstance().shutdownSync();
    this.theme = theme;
    uib = new UIBuilder();
    
    Container formContainer = uib.createContainer(theme, "AddForm");
    addForm = formContainer.getComponentForm();
    
    //NetworkManager.getInstance().start();

    MenuManager.createMenu(addForm, theme);
        MenuManager.createLeftMenu(addForm, theme);
        MenuManager.setTitle(addForm, "Nouveau Article");

    upload = (Button) uib.findByName("AddImg", addForm);
    acheter = (Button) uib.findByName("Acheter", addForm);
    
    libelle = (TextField) uib.findByName("LibelleInput", addForm);
    bonneEtat = (RadioButton) uib.findByName("BonneEtat", addForm);
    bonneOccasion = (RadioButton) uib.findByName("BonneOccasion", addForm);
    moyenneEtat = (RadioButton) uib.findByName("EtatMoyen", addForm);
    nouveau = (RadioButton) uib.findByName("Nouveau", addForm);
    prix = (TextField) uib.findByName("PrixInput", addForm);
    description = (TextArea) uib.findByName("DescriptionArea", addForm);
    
    
//
//    addComponent(upload);
//    addComponent(acheter);
    
//    libellePrixContainer = new Container(BoxLayout.x());
//    imageContainer = new Container(new FlowLayout());
//    southContainer = new Container(BoxLayout.y());
//    buttonContainer = new Container(BoxLayout.x());
    
    //libellePrixContainer.add(libelle);
    //libellePrixContainer.add(prix);
    
        

    upload.addActionListener(this);
    acheter.addActionListener(this);



}

    @Override
    public void actionPerformed(ActionEvent evt) {
         if (evt.getSource().equals(upload)){
        //browse here

        upload.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                Display.getInstance().openGallery(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                if (ev != null && ev.getSource() != null) {
                    String filePath = (String) ev.getSource();
                    int fileNameIndex = filePath.lastIndexOf("/") + 1;
                    String fileName = filePath.substring(fileNameIndex);
                    System.out.println(fileName);
                    System.out.println("File path: "+filePath);

                    Image img = null;
                    try {
                        imageProd = Image.createImage(FileSystemStorage.getInstance().openInputStream(filePath));
                        imageUpStream = FileSystemStorage.getInstance().openInputStream(filePath);
                        pathImage = filePath;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    
//                    Hashtable tableItem = new Hashtable();
//                    tableItem.put("icon", img.scaled(Display.getInstance().getDisplayHeight() / 10, -1));
//                    tableItem.put("emblem", fileName);
                    
                    // Do something, add to List
                }
            }
        }, Display.GALLERY_IMAGE);
               
            }
        });
        acheter.addActionListener((ev) -> {
            try {
                System.out.println("Validating request ..");
                valider();
            } catch (IOException ex) {
                //Logger.getLogger(AddForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });


    }   
    }

    @Override
    public void show() {
        addForm.show();
    }

    private void valider() throws IOException {
         int fileNameIndex = pathImage.lastIndexOf("/") + 1;
                    String fileName = pathImage.substring(fileNameIndex);
                    int mimeIndex = fileName.lastIndexOf(".")+1;
                    String extension = fileName.substring(mimeIndex);
                    String mimeType = "image/"+extension;
         MultipartRequest request = new MultipartRequest() {
             @Override
             protected void readResponse(InputStream input) throws IOException {
                        Dialog dialog = new Dialog("Article ajouté avec succès", new FlowLayout());
                        Button btnOk = new Button("Ok", theme.getImage("checked.png"), "RedButton");
                        btnOk.addActionListener((evt) -> {
                            dialog.dispose();
                             new BoutiqueForm(theme, true).show();  
                        });
                        dialog.add(btnOk);
                        dialog.show();
             }
             
         };
            request.setUrl("http://localhost/codenameonescripts/AddArticleService.php");
            request.setPost(true);
            request.addData("idImage", pathImage, mimeType);
            request.setFilename("idImage", "myPicture."+extension);
            request.addArgumentNoEncoding("libelle", libelle.getText());
            String etat;
            if (bonneEtat.isSelected()) {
                etat = bonneEtat.getText();
            } else if (bonneOccasion.isSelected()) { 
                etat = bonneEtat.getText();
            }else if (nouveau.isSelected()) {
                etat = nouveau.getText();
            } else {
                etat = moyenneEtat.getText();
            }
            
             request.addArgumentNoEncoding("etat", etat);
              request.addArgumentNoEncoding("description", description.getText());
               request.addArgument("prix", prix.getText());
               request.addArgument("id_user", String.valueOf(LoginManager.getUser().getId()));
               request.setPriority(ConnectionRequest.PRIORITY_HIGH);
            
            if (NetworkManager.getInstance().isQueueIdle())
                NetworkManager.getInstance().addToQueueAndWait(request);
        
    }
    
    
    
}
