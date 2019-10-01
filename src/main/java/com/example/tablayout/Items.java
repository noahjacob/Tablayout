package com.example.tablayout;

public class Items {
    private String mName;
    private String mDesc;
    private int mCount;

    public String getName() {
        return mName;
    }

    public String getDesc() {
        return mDesc;
    }

    public int getCount() {
        return mCount;
    }

    public Items(String Name, String Desc, int Count) {
        mCount = Count;
        mDesc = Desc;
        mName = Name;
    }

}
