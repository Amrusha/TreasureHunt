package com.myapp.ahm.recyclerview;

/**
 * Created by hp on 13-02-2016.
 */
public class DBstructure {
    public static abstract class monitor{
        public static final String challenge = "CHALLENGE_NO";
        public static final String status = "STATUS";
        public static final String TABLE_NAME = "MONITOR";
        public static final String DATABASE_NAME = "INFO";
    }
    public static abstract class iteration{
        public static final String number = "NUMBER";
        public static final String TABLE_NAME = "ITERATION";
    }

    public static abstract class timer{
        public static final String min = "MINUTES";
        public static final String sec = "SECONDS";
        public static final String mili = "MILISECONDS";
        public static final String TABLE_NAME = "TIMER";
    }
}
