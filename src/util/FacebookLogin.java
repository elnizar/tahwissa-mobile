/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.facebook.FaceBookAccess;
import com.codename1.io.AccessToken;
import com.codename1.io.Oauth2;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import java.io.IOException;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author Chen
 */
public class FacebookLogin extends Observable {

    private Form main;
    public static String TOKEN;
    private static final String client_id= "406954319675411";
    private static final String client_secret="6f38f37d6869bc2b62bcfdbedf16dbff";
     
    public FacebookLogin(Form f, Observer o) {
        //super("Login Form");
        this.main = f;
        //setLayout(new LayeredLayout());

        signIn(main, o);

        // addComponent(login);
    }

    private static void saveToken(String token, long expireDate) throws IOException {
        if (Database.exists("dbtokenn.db") == false) {
            Database db = Database.openOrCreate("dbtokenn.db");
            db.execute("CREATE TABLE token (id integer,token varchar(200),expire_date long)");
            db.execute("INSERT INTO token values(1,'" + token + "'," + expireDate + ")");
            System.out.println("Saved token " + token + ", expires at " + new Date(expireDate));
            
            db.close();
        } else {
            Database db = Database.openOrCreate("dbtokenn.db");
            db.execute("delete from token");
            db.execute("INSERT INTO token values(1,'" + token + "'," + expireDate + ")");
            db.close();
        }
    }

    private static void signIn(final Form main, Observer o) {
        FaceBookAccess.setClientId("406954319675411");
        FaceBookAccess.setClientSecret("6f38f37d6869bc2b62bcfdbedf16dbff");
        FaceBookAccess.setRedirectURI("http://localhost/tahwissa/web/app_dev.php/service/redirecturi");
        FaceBookAccess.setPermissions(new String[]{"publish_actions"});

        FaceBookAccess.getInstance().showAuthentication(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() instanceof AccessToken) {
                    try {
                        AccessToken token = (AccessToken) evt.getSource();
                        System.out.println(token.getToken());
                        System.out.println(token.getExpires());
                        long now = new Date().getTime();
                        long expiresDate = Double.valueOf(token.getExpires()).intValue();
                        System.out.println(new Date(now + expiresDate));
                        String expires = Oauth2.getExpires();
                        TOKEN = token.getToken();
                        //System.out.println("recived a token " + token.getToken() + " which expires on " + expires);
                        //System.out.println("Token initial Length : "+token.getToken().length());
                        //store token for future queries.
                        //Storage.getInstance().writeObject("token", token.getToken());
                        saveToken(TOKEN, expiresDate + new Date().getTime());
                        if (main != null) {

                            main.showBack();
                            o.update(null, null);
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        //Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    Exception err = (Exception) evt.getSource();
                    err.printStackTrace();
                    Dialog.show("Error", "An error occurred while logging in: " + err, "OK", null);
                }
            }
        });
    }

    public static boolean firstLogin() throws IOException {
        return true;/*
        if (Database.exists("dbtokenn.db") == false) {
            System.out.println("database does not exist");
            return true;
        } else {
            Database db = Database.openOrCreate("dbtokenn.db");
            Cursor c = db.executeQuery("SELECT * from token");
            int count = 0;
            while (c.next()) {
                Row r = c.getRow();
                long expirationDate = r.getLong(2);
                //System.out.println(new Date(expirationDate));
                if (expirationDate < new Date().getTime()) {
                    System.out.println("token expired");
                    return true;
                }
                TOKEN = r.getString(1);
                System.out.println("Token Length from db : "+TOKEN.length());
                //System.out.println(r.getString(1));
                count++;
            }
            c.close();
            db.close();
            if (count == 0) {
                return true;
            }

        }

        return false;*/
       
    }

    public static void login(final Form form, Observer o) {
        try {

            if (firstLogin()) {
                System.out.println("first");
                signIn(form, o);

            } else {
                
                System.out.println("Already did this");
                //token exists no need to authenticate
                FaceBookAccess.setToken(TOKEN);
               
                o.update(null, null);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
