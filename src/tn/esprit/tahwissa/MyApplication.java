package tn.esprit.tahwissa;

import com.codename1.components.FileTree;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.UIBuilder;
import entity.Evenement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import service.EventService;
import service.TestService;
import static tn.esprit.tahwissa.LoginManager.user;

import util.MenuManager;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
public class MyApplication implements Observer {

    private Form current;
    private Resources theme;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);
    }

    Form f;
    private Container distanceContainer;
    private static Map<String, Double> distances = new HashMap<>();

    private void initSearchBar() {

        f.getToolbar().addSearchCommand((evt) -> {
            String keyWord = (evt.getSource()).toString().toLowerCase();
            if (keyWord.length() == 0) {
                f.removeAll();
                f.add(distanceContainer);
                initList(0, 4, events);
                f.refreshTheme();
            }
            if (keyWord.length() >= 4) {
                //System.out.println("Searching for " + keyWord);
                List<Evenement> results = new ArrayList<>();
                for (int i = 0; i < events.size(); i++) {
                    Evenement event = events.get(i);
                    boolean descriptionMatched = (event.getDescription().toLowerCase().indexOf(keyWord) != -1);
                    boolean destinationMatched = (event.getDestination().toLowerCase().indexOf(keyWord) != -1);
                    boolean evenementTypeMatched = (event.getEvenementType().toLowerCase().indexOf(keyWord) != -1);
                    boolean userNameMatched = (event.getUserName().toLowerCase().indexOf(keyWord) != -1);
                    boolean userMailMatched = (event.getUserMail().toLowerCase().indexOf(keyWord) != -1);
                    if ((descriptionMatched) || (destinationMatched) || (evenementTypeMatched) || (userNameMatched) || (userMailMatched)) {
                        results.add(event);
                    }
                }
                f.removeAll();
                f.add(distanceContainer);
                initList(0, 4, results);
                f.refreshTheme();
            }
        });
    }

    private List<Evenement> events = new ArrayList<>();

    public void initList(int from, int to, List<Evenement> evenements) {
        if (evenements.size() < to) {
            to = evenements.size();
        }

        for (int i = from; i < to; i++) {

            Container eventContainer = new Container(new BorderLayout());
            Evenement event = evenements.get(i);
            System.out.println("id:" + event.getId());
            String description = event.getDescription();
            if (description.length() > 40) {
                description = description.substring(0, 40).concat("...");
            }
            Container northContainer = new Container(new BorderLayout());

            Container imageAndNameAndMail = new Container(new BoxLayout(BoxLayout.X_AXIS));
            // Label userImage = new Label("taswira");

            Container nameAndMail = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Label name = new Label(event.getUserName());
            Label email = new Label(event.getUserMail());
            name.setUIID("userName");
            email.setUIID("userEmail");
            nameAndMail.add(name);
            nameAndMail.add(email);

            // imageAndNameAndMail.add(userImage);
            imageAndNameAndMail.add(nameAndMail);

            Container destAndTime = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            System.out.println(event.getDestination());
            Label dest;
            if (event.getDestination().indexOf(",") != -1) {

                dest = new Label(event.getDestination().substring(0, event.getDestination().indexOf(",")));
            } else {
                dest = new Label(event.getDestination());
            }
            Label time = new Label(event.getDateHeureDepart());
            destAndTime.add(dest);
            destAndTime.add(time);
            dest.setUIID("destinationEvent");
            time.setUIID("dateEvent");

            northContainer.addComponent(BorderLayout.WEST, imageAndNameAndMail);
            northContainer.addComponent(BorderLayout.EAST, destAndTime);

            Label imageEvent = new Label("");

            EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(400, 200), true);
            /*Image image = URLImage.createToStorage(placeholder, event.getImages().get(0).getChemin(),
                        "http://localhost/tahwissa/web/images/evenements/" + event.getImages().get(0).getChemin());
             */
            String path = event.getImages().get(0).getChemin();
            Image image;
            if (path.indexOf("filestack") == -1) {
                System.out.println("Image path : http://localhost/tahwissa/web/images/evenements/" + path);
                image = URLImage.createToStorage(placeholder, path + "123", "http://localhost/tahwissa/web/images/evenements/" + event.getImages().get(0).getChemin());
            } else {
                image = URLImage.createToStorage(placeholder, event.getImages().get(0).getChemin().substring(path.lastIndexOf("/") + 1), event.getImages().get(0).getChemin());
            }
            imageEvent.setIcon(image);

            Container centerContainer = FlowLayout.encloseCenterMiddle(imageEvent);

            SpanLabel descriptionEvent = new SpanLabel(description, "descriptionEventText");
            Button btnDetails = new Button(theme.getImage("arrow.png"));
            btnDetails.setUIID("detailsEvent");
            descriptionEvent.add(BorderLayout.EAST, btnDetails);

            descriptionEvent.setUIID("descriptionEvent");
            Label l;

            if (event.getEvenementType().equals("camping")) {
                l = new Label("Camping");
                l.setTextPosition(Label.RIGHT);
                l.setIcon(theme.getImage("camping2.png").scaled(35, 35));
            } else {
                l = new Label("Randonnée");
                l.setTextPosition(Label.RIGHT);
                l.setIcon(theme.getImage("rando.png").scaled(35, 35));

            }
            l.setUIID("typeEvent");
            l.setGap(10);
            northContainer.add(BorderLayout.NORTH, l);
            btnDetails.addActionListener((evt) -> {
                DetailsEvent.e = event;
                DetailsEvent d = new DetailsEvent();
                d.init(current);
                d.start();
            });
            eventContainer.add(BorderLayout.NORTH, northContainer);
            eventContainer.add(BorderLayout.CENTER, centerContainer);
            eventContainer.add(BorderLayout.SOUTH, descriptionEvent);
            f.add(eventContainer);
        }

        paginate(evenements);
    }

    public void start() {
        
        if (LoginManager.getUser() == null) {
            LoginUser login = new LoginUser();
            login.start();
        } else {
             theme = UIManager.initFirstTheme("/theme");
            UIBuilder uibuilder = new UIBuilder();
        UIBuilder.registerCustomComponent("ImageViewer", ImageViewer.class);
        UIBuilder.registerCustomComponent("FileTree", FileTree.class);

        Container container = uibuilder.createContainer(theme, "eventshome");

        f = container.getComponentForm();
        distanceContainer = (Container) uibuilder.findByName("distanceContainer", container);
        f.getToolbar().addCommandToOverflowMenu("Consulter les évènements", null, (evt) -> {

        });
        f.getToolbar().addCommandToOverflowMenu("Organiser un evenement", null, (evt) -> {
            Organiser o = new Organiser();
            o.init(current);
            o.start();
        });
        f.getToolbar().addCommandToOverflowMenu("Mes évenements", null, (evt) -> {
            MyEvents e = new MyEvents();
            e.init(current);
            e.start();
        });

        f.getContentPane().addPullToRefresh(() -> {
            f.removeAll();
            f.add(distanceContainer);
            refresh();
        });

        MenuManager.createMenu(f, theme);
        initSearchBar();
        Slider distanceSlider = (Slider) uibuilder.findByName("distance", container);
        final Label value = (Label) uibuilder.findByName("value", container);
        distanceSlider.addActionListener((evt) -> {
            value.setText(String.valueOf(distanceSlider.getProgress()) + " km");

        });
        Button searchDistance = (Button) uibuilder.findByName("destinationButton", container);
        searchDistance.addActionListener((evt) -> {
            if (distances.size() != 0) {
                filterByDistance(Double.valueOf(distanceSlider.getProgress()));
            }
        });
        f.show();

        refresh();
          
        }
    }

    public void refresh() {

        InfiniteProgress ip = new InfiniteProgress();
        Dialog dlg = ip.showInifiniteBlocking();
        EventService t = new EventService();
        t.getAvailable(dlg, this);
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
        if (current instanceof Dialog) {
            ((Dialog) current).dispose();
            current = Display.getInstance().getCurrent();
        }
    }

    public void destroy() {
    }

    public void paginate(List<Evenement> evenements) {
        float pagesFloat = ((float) (evenements.size())) / 4;
        int pages = 0;
        if (pagesFloat % 1 != 0) {
            pages = ((int) pagesFloat) + 1;
        } else {
            pages = ((int) pagesFloat);
        }
        Container pageNumberContainer = new Container(new FlowLayout(Component.CENTER));
        for (int i = 1; i <= pages; i++) {
            Label l = new Label(String.valueOf(i));
            l.setUIID("pageNumber");
            l.setFocusable(true);
            l.addPointerPressedListener((evt) -> {
                Label lab = (Label) (evt.getSource());
                int selectedPage = Integer.valueOf(lab.getText());

                int from = (selectedPage - 1) * 4;
                int to = from + 4;
                System.out.println("PAge : " + selectedPage + " (from " + from + " to " + to + ")");
                f.removeAll();
                f.add(distanceContainer);
                initList(from, to, evenements);
                f.refreshTheme();

            });
            // pageNumberContainer.add(l);
            f.add(l);
        }
        f.add(pageNumberContainer);
    }

    @Override
    public void update(Observable o, Object arg) {
        System.out.println("notfied");
        EventService t = (EventService) o;
        events = t.events;
        initList(0, 4, events);
        if (distances.size() == 0) {
            distances = TestService.calculateDistance(events);
        }
        //filterByDistance(1.0);
        //System.out.println(f);
    }

    public void filterByDistance(Double km) {
        List<Evenement> filteredEvents = new ArrayList<Evenement>();
        for (int i = 0; i < events.size(); i++) {
            if (distances.get(events.get(i).getDestination()) < km) {
                filteredEvents.add(events.get(i));
            }
        }
        f.removeAll();
        f.add(distanceContainer);
        initList(0, 4, filteredEvents);
        f.refreshTheme();
    }

}
