package br.edu.ifpe.pdm.cardapiolanches.utils;

/**
 * Created by Richardson on 03/06/2015.
 */
public class NavDrawerItem {

    private String title;

    public NavDrawerItem(){}

    public NavDrawerItem(String title){
        this.title = title;

    }


    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

}