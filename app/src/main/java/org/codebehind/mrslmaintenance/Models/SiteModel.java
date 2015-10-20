package org.codebehind.mrslmaintenance.Models;

import org.codebehind.mrslmaintenance.Entities.Site;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Gavin on 24/12/2014.
 */
public class SiteModel  {
    ArrayList<Site> _list;
    static SiteModel _instance;
    private SiteModel(){
        //todo: get all the sites from the database
        _list=new ArrayList<>();
        for (int i = 0; i<5; i++) {
            Site s = new Site();
            s.setDescription("some place "+i);
            s.setName("Site name #"+i);
            s.setAddress("10"+i+", Oldtown Manor, Cavan, Cavan");
            add(s);
        }
    }
    public static SiteModel getInstance(){
        if (_instance==null)_instance=new SiteModel();
        return _instance;
    }
    public ArrayList<Site> getList() {
        return _list;
    }

    public Site getSite(UUID id) {
        for (Site s: _list) if (s.getUUID().equals(id)) return  s;
        return null;
    }

    public Site add(Site item) {
        if (item.getUUID()== null) item.setUUID(UUID.randomUUID());
        _list.add(item);
        return  item;
    }

    public void delete(UUID siteId) {
        Site site;

        site=getSite(siteId);
        if (site==null) return;

        _list.remove(site);
    }

    public Site update(Site item) {
        delete(item.getUUID());
        add(item);
        return item;
    }

    public Site getTemplate(UUID siteId){
        Site    s=new Site(),
                site=getSite(siteId);

        s.setUUID(UUID.randomUUID());
        if (site==null)return s;

        s.setName(site.getName());
        s.setDescription(site.getDescription());
        s.setAddress(site.getAddress());
        s.setSystem(site.getSystem());
        s.setPlantId(site.getPlantId());
        s.setLatLng(site.getLatLng());
        // the add has been removed from here as I want an explicit save for the site
        // it's also causing a bug, loads of sites of the same type can be create too quickly
        //add(s);
        return s;
    }
}