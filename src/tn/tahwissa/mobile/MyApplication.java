package tn.tahwissa.mobile;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ext.filechooser.FileChooser;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.List;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.util.UITimer;
import java.io.IOException;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.list.ListModel;
import com.codename1.ui.list.MultiList;
import com.codename1.ui.util.UIBuilder;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename One</a> for the purpose 
 * of building native mobile applications using Java.
 */
public class MyApplication {

    private Form current;
    private Form articleDetail;
    private Resources theme;
    private UIBuilder uib;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");
        uib = new UIBuilder();
        UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
        UIBuilder.registerCustomComponent("SpanLabel", SpanLabel.class);
        Container ctnr = uib.createContainer(theme, "Boutique");
        current = ctnr.getComponentForm();
        ImageViewer img = (ImageViewer) uib.findByName("ImageProduit", current);
            img.setImage(img.getImage().scaled(75, 100));
//            
//            img = (ImageViewer) uib.findByName("ImageItem2", current);
//            img.setHeight(75);
//            img.setWidth(75);
//            img.setImage(img.getImage().scaled(75, 75));
        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }
    
    public void start() {
        
        if(true) {
            
            BoutiqueForm form = new BoutiqueForm(theme);
            
            
            form.show(); return ;
        }
        if (false) {
            MesArticlesForm mesArticlesForm = new MesArticlesForm(theme);
            return;
        }
        if (false) {
            MesAchats mesAchats = new MesAchats(theme); 
            mesAchats.show();
            return;
        }
        if (true) {
            current.show();
            return;
        }
        Form addForm = new AddForm(theme);
        Form boutiqueForm = new BoutiqueForm(theme);
        if (false) {
            boutiqueForm.show();
            return;
        }
        if (true) {
            addForm.show();
            return;
        }
        if (false) {
            
         UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
         UIBuilder.registerCustomComponent("SpanLabel", SpanLabel.class);
        Container ctnr = uib.createContainer(theme, "articleDetail");
        articleDetail = ctnr.getComponentForm();
        articleDetail.getToolbar().setUIID("TopContainer");
        articleDetail.getToolbar().addCommandToSideMenu("Evenement", theme.getImage("cart.png"), (evt) -> {
            });
        ImageViewer image = (ImageViewer) uib.findByName("imageArticle", articleDetail);
        Image i = image.getImage().scaled(450, 200);
        
        image.setImage(i);
        
        Container bigContainer = (Container) uib.findByName("DetailContainer", articleDetail);
        Container imageCtr = (Container) uib.findByName("ImageContainer", articleDetail);
        
        
        
        articleDetail.show();
        return ;
        }
         
        
        
        if(current != null){
            
           ConnectionRequest request;
            request = new ConnectionRequest() {
                @Override
                protected void readResponse(InputStream input) throws IOException {
                    InputStreamReader reader = new InputStreamReader(input);
                    JSONParser jp = new JSONParser();
                    Map <String, Object> response1 = jp.parseJSON(reader);
                    Map <String, Object> response = (Map <String, Object>) response1.get("data");
                    Container newContainer;
                    for(int i = 1; i <= response.size(); i++) {
                        Map article = (Map) response.get(String.valueOf(i));
                        Label libelle = new Label(theme.getImage("price-tab (4).png"));
                       // libelle.setUIID("Libelle");
                        Label prix = new Label(theme.getImage("price-tab (5).png"));
                       // prix.setUIID("prix");
                        Label etat = new Label(theme.getImage("sticker.png"));
                        etat.setUIID("Libelle");
                        ImageViewer viewer = new ImageViewer();
                        ListModel<Image> images = new DefaultListModel<>();
                        ArrayList imgs = (ArrayList) article.get("images");
                        Image imageSecondaire;
                        EncodedImage ph = EncodedImage.createFromImage(theme.getImage("duke-no-logos.png"), true);
                        System.out.println("Nombre d'images : "+imgs.size());
                        for (int j = 0; j < imgs.size(); j++) {
                            String imageName = (String) imgs.get(j);
                            System.out.println("Image name : "+imageName);
                            imageSecondaire = URLImage.createToStorage(ph, imageName, "http://localhost/tahwissa-web/web/images/articles/"+imageName);
                            images.addItem(imageSecondaire);
                        }
                        viewer.setImageList(images);
                        String lib = (String) article.get("libelle");
                        lib = (lib.length() >20) ? lib.substring(0, 19)+"..." : lib;
                        libelle.setText(lib);
                        prix.setText((String) article.get("prix")+"TND");
                        etat.setText((String) article.get("etat"));
                        Button acheter = new Button("Acheter");
                        acheter.setUIID("TransparentButton");
                        Button moreDetail = new Button(theme.getImage("more (1).png"));
                        moreDetail.setUIID("WhiteButton2");
                        Container ctnr = new Container(new BorderLayout());
                        Container ctnrLibellePrix = Container.encloseIn(BoxLayout.x(), libelle, prix);
                        ctnrLibellePrix.setUIID("MyColdGreenTab");
                        Container ctnrImage = Container.encloseIn(new FlowLayout(), viewer);
                        ctnrImage.setUIID("ImageContainer");
                        Container ctnrButtons = Container.encloseIn(BoxLayout.x(), acheter, moreDetail);
                        ctnrButtons.setUIID("MyColdGreenTab");
                        Container CtnrSouth = Container.encloseIn(BoxLayout.y(), etat, ctnrButtons);
                        CtnrSouth.setUIID("WhiteTab");
                        ctnr.add(BorderLayout.CENTER, ctnrImage);
                        ctnr.add(BorderLayout.NORTH, ctnrLibellePrix);
                        ctnr.add(BorderLayout.SOUTH, CtnrSouth);
                        current.add(ctnr);
                        
                        
                    }
                }
                
            };
           
            request.setPost(false);
            request.setHttpMethod("GET");
            request.setUrl("http://localhost/tahwissa-web/web/app_dev.php/boutique/articles/all");
    
            NetworkManager.getInstance().addToQueue(request);
            
            Toolbar tool = new Toolbar();
            tool.setUIID("TopContainer");
            //tool.getMenuBar().setUIID("bgMenu");
            
            current.setToolbar(tool);
            tool.addCommandToSideMenu("Evenement", theme.getImage("cart.png"), (evt) -> {
            });
           UIBuilder uib = new UIBuilder();
            UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
            Container ctnr = uib.createContainer(theme, "Boutique");
           
            current.show();
            return;
        }
        
    }
    
    public void openAlbum(ImageViewer iv)
    {
         Display.getInstance().openGallery((e) -> {
            if(e != null && e.getSource() != null) {
                String file = (String)e.getSource();
                Image image;
                try {
                    image = Image.createImage(FileSystemStorage.getInstance().openInputStream(file));
                    System.out.println(image.getHeight()); 
                    iv.setImage(image);
                } catch (IOException ex) {
                   // Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
                }
                System.out.println("File : "+file);
                //                    hi.removeAll();
//                    hi.add(BorderLayout.CENTER, new MediaPlayer(video));
//                    hi.revalidate();
                
            }
        }, Display.GALLERY_IMAGE);
    }
    
    public void fileChooser(Form form){
        if (FileChooser.isAvailable()) {
    FileChooser.showOpenDialog(".xls, .csv, text/plain", e2-> {
        String file = (String)e2.getSource();
        if (file == null) {
            form.add("No file was selected");
            form.revalidate();
        } else {
           String extension = null;
           if (file.lastIndexOf(".") > 0) {
               extension = file.substring(file.lastIndexOf(".")+1);
           }
           if ("txt".equals(extension)) {
               FileSystemStorage fs = FileSystemStorage.getInstance();
               try {
                   InputStream fis = fs.openInputStream(file);
                   form.addComponent(new SpanLabel(Util.readToString(fis)));
               } catch (Exception ex) {
                   Log.e(ex);
               }
           } else {
               form.add("Selected file "+file);
           }
        }
        form.revalidate();
    });
}
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }
    
    public void destroy() {
    }
   

}
