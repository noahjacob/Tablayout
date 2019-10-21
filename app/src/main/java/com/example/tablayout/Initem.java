package com.example.tablayout;

public class Initem {
    private String itemname;
    private String desc,id,location;
    private int count,date;

    public Initem(){

    }

    public String getLocation() {
        return location;
    }

    public Initem(String itemname, String desc, int count, String location, String id, int date){
        this.itemname = itemname;
        this.count = count;
        this.desc = desc;
        this.date = date;
        this.id = id;
        this.location = location;
    }

    public String getItemname() {
        return itemname;
    }
    public String getId() {
        return id;
    }
    public int getDate() {
        return date;
    }

    public String getDesc() {
        return desc;
    }

    public int getCount() {
        return count;
    }
}
