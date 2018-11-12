/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.List;

import java.util.ArrayList;
import java.util.List;
import static tn.tahwissa.mobile.List.ArticleList.articles;
import tn.tahwissa.mobile.entity.Article;
import tn.tahwissa.mobile.util.LoginManager;

/**
 *
 * @author esprit
 */
public class MesArticlesList {
    private static List<Article> articles = new ArrayList<>();
    
        
    public static List<Article> getListArticles() {
        
        return articles;
    }
    
    public static boolean addItem(Article article) {
        
            articles.add(article);
            return true;
        
    }
    
    public static boolean isExist(Article article) {
        return articles.contains(article);
    }
    
    public static boolean removeItem(Article article) {
        if (articles.contains(article))  {
            articles.remove(article);
            return true;
        }
        return false;
    }
    
    public static Article getArticle(int id) {
        for (Article article : articles) {
            if (article.getId() == id) return article;
        }
        return null;
    }
    
}
