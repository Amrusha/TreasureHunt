package com.myapp.ahm.recyclerview;

/**
 * Created by hp on 14-02-2016.
 */
public class Share {
    private static final Share INSTANCE = new Share();
    public String status = null;
    private Share() {
    }
    public static Share getInstance() {
        return INSTANCE;
    }
}
