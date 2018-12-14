package com.example.jsrgjhl.hlapp.Sample;

public class Devicepoint{
    private int Id ;
    private String Type;
    private double Lat,Lng;
    public Devicepoint(int a,String b,double c,double d){
        Id=a;Type=b;Lat=c;Lng=d;
    }
    private void setId(int x){Id=x;}
    private void setType(String x){Type=x;}
    private void setLat(double x){Lat=x;}
    private void setLng(double x){Lng=x;}
    private void setposition(double a,double b){
        Lat=a;Lng=b;
    }
    public int getId(){
        return Id;
    }
    public String getType(){
        return Type;
    }
    public double getLat(){
        return Lat;
    }
    public double getLng(){
        return Lng;
    }
    public String getStringId(){
        return "'"+Id+"'";
    }
}
