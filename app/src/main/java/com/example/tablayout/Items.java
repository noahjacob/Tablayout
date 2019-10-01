package com.example.tablayout;

public class Items {
    private String Name;
    private String Desc;
    private int Count;

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public int getCount() {
        return Count;
    }

    public Items(String Name, String Desc, int Count) {
        this.Count = Count;
        this.Desc = Desc;
        this.Name = Name;
    }

}
