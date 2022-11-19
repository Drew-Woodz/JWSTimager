package com.example.jwstimager.model;

import com.example.jwstimager.model.entry.Entry;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.List;

@Root(name = "feed", strict = false)
public class Feed implements Serializable {

    @Element(name = "icon")
    private String icon;
    @Element(name = "id")
    private String id;
    @Element(name = "logo")
    private String logo;
    @Element(name = "title")
    private String title;
    @Element(name = "updated")
    private String updated;
    @Element(name = "subtitle")
    private String subtitle;
    @ElementList(inline = true, name = "entry")
    private List<Entry> entrys;

    //icon get/set
    public String getIcon(){
        return icon;
    }
    public void setIcon(String icon){
        this.icon = icon;
    }
    //id get/set
    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    //logo get/set
    public String getLogo(){
        return logo;
    }
    public void setLogo(String logo){
        this.logo = logo;
    }
    //title get/set
    public String getTitle(){
        return id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    //update get/set
    public String getUpdated(){
        return updated;
    }
    public void setUpdated(String updated){
        this.updated = updated;
    }
    //subtitle get/set
    public String getSubtitle(){
        return subtitle;
    }
    public void setSubtitle(String subtitle){
        this.subtitle = subtitle;
    }

    //entry get/set
    public List<Entry> getEntrys(){
        return entrys;
    }
    public void setEntrys(List<Entry>entrys){
        this.entrys = entrys;
    }
    @Override
    public String toString(){
        return "Feed: \n [Entrys: \n"+ entrys +"]";
    }
}
