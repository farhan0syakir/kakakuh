package com.kakakuh.c4ppl.kakakuh.model;

/**
 * Created by Anas on 4/10/2015.
 */
public class PengaturanListItem {
    private String title;
    private int icon;

    public PengaturanListItem(){}

    public PengaturanListItem(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }
}