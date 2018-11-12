/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.tahwissa.mobile.entity;

import java.util.Date;

/**
 *
 * @author esprit
 */
public class ImageShop {
    protected int id;
    protected String imageName;
    protected Date updateAt = new Date();
    protected Article article;

    public ImageShop(String imageName, Date updateAt, Article article) {
        this.imageName = imageName;
        this.updateAt = updateAt;
        this.article = article;
    }

    public ImageShop(String imageName, Article article) {
        this.imageName = imageName;
        this.article = article;
    }

    public ImageShop(String imageName) {
        this.imageName = imageName;
    }
    
    

    public ImageShop() {
    }

    public ImageShop(Date updateAt, Article article) {
        this.updateAt = updateAt;
        this.article = article;
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
    
}
