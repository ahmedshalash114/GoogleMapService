/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.google;

import MapserviceDB.Hospitals;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;
import org.primefaces.model.map.Polyline;

/**
 *
 * @author TamallyMaak
 */
@ManagedBean(name="MapBean")
@RequestScoped
public class MapBean {
 private final double  Distant_NM=2800;
 private final double Distance_Meter=Distant_NM*1852;
 private String centerCoords="30.583333,32.266667" ;
 private  MapModel mapModel;
 private  List<Hospitals>hospitalinfo;
 private static SessionFactory factory;
 
   
    
    public MapBean() {
        // Declartion of Map bean 
    mapModel=new DefaultMapModel();
        LatLng coordl=new LatLng(30.583333, 32.266667);
        Circle circle=new Circle(coordl, Distance_Meter);
        circle.setStrokeColor("#FFFF66");
        circle.setFillColor("#FFFF66");
        circle.setFillOpacity(0.2);
        mapModel.addOverlay(circle);
       //put the points into the screen 
        LatLng coord2=new LatLng(30.583333, 32.266667);
        mapModel.addOverlay(new Marker(coord2," Ismalia hospital","http://maps.google.com/mapfiles/ms/micons/blue-dot.png"));
        LatLng coord3=new LatLng(30.183333,31.266667);
        mapModel.addOverlay(new Marker(coord3,"Last Known postion",null,"http://maps.google.com/mapfiles/ms/micons/yellow-dot.png"));
        //not even any thing else just put the dots into the map
        Polyline polyline=new Polyline();
        polyline.getPaths().add(coord2);
        polyline.getPaths().add(coord3);
        polyline.setStrokeWeight(5);
        polyline.setStrokeColor("#660066");
        polyline.setStrokeOpacity(0.7);
        mapModel.addOverlay(polyline);
        // get the data in the constructor
        getdata();
    }   
    
    

    public String getCenterCoords() {
        return centerCoords;
    }

    public void setCenterCoords(String centerCoords) {
        this.centerCoords = centerCoords;
    }

    public MapModel getMapModel() {
        return mapModel;
    }

    public void setMapModel(MapModel mapModel) {
        this.mapModel = mapModel;
    }

    public List<Hospitals> getHospitalinfo() {
        return hospitalinfo;
    }

    public void setHospitalinfo(List<Hospitals> hospitalinfo) {
        this.hospitalinfo = hospitalinfo;
    }
    public void getdata(){
    factory=new AnnotationConfiguration().configure().buildSessionFactory();
    Session session =factory.openSession();
    try{
    hospitalinfo=new ArrayList<>();
        Transaction tx =null;
        hospitalinfo=(List<Hospitals>)session.createQuery("From Hospitals").list();
        if(tx!=null){
        tx.rollback();
        }
    }catch(Exception e){
    e.getStackTrace();
    }finally{
    
    session.close();
    }
    
    }
    
    
}
