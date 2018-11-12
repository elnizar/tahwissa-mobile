/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.List;

import java.util.ArrayList;
import java.util.List;
import tn.tahwissa.mobile.entity.Achat;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.util.LoginManager;

/**
 *
 * @author esprit
 */
public class AchatList {
    private static List<Achat> achats = new ArrayList<>();
    
        
    public static List<Achat> getListAchats() {
        
        return achats;
    }
    
    public static boolean addItem(Achat achat) {
        
            achats.add(achat);
            return true;
        
    }
    
    public static boolean isExist(Achat achat) {
        return achats.contains(achat);
    }
    
    public static boolean removeItem(Achat achat) {
        if (achats.contains(achat))  {
            achats.remove(achat);
            return true;
        }
        return false;
    }
    
    public static Achat getArticle(int id) {
        for (Achat achat : achats) {
            if (achat.getId() == id) return achat;
        }
        return null;
    }
    
    public static List<Achat> myArticles() {
        List<Achat> myAchats = new ArrayList<>();
        for(Achat achat : achats) {
            if (achat.getId() == LoginManager.getUser().getId()) {
                myAchats.add(achat);
            }
        }
       return myAchats;
    }
    
    public static void reset() {
        achats.clear();
    }
}
