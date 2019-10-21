package com.example.tablayout;

public class Items {
    private String name;
    private String desc,id;
    private int count;
    public Items(){

    }

    public Items(String Name, String Desc, int Count,String id) {
        this.count = Count;
        this.desc = Desc;
        this.name = Name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getId() {
        return id;
    }

    public int getCount() {
        return count;
    }
}
