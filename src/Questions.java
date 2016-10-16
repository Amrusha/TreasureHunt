package com.myapp.ahm.recyclerview;

import android.content.Context;

/**
 * Created by hp on 13-02-2016.
 */
public class Questions {
    Context context;
    Questions(Context ctx){
        this.context = ctx;
    }
    DBopenhelper dBopenhelper = new DBopenhelper(context);
    String[] qArray = new String[11];
    String[] aArray = new String[11];

    String qtn(int i){

        qArray[1] = "I'm alone,taller than most,similar to palm and easy to see. Find me.";
        qArray[2] = "Run to stationary, someone is waiting for you.";
        qArray[3] = "I'm flat, round as a ring, has two eyes can't see things.";
        qArray[4] = "Reach to marvel department.";
        qArray[5] = "Find the place where you get the top view of SMVITM campus.";
        qArray[6] = "I make two people out of one. what am I?";
        qArray[7] = "Welcome to department of silicon.";
        qArray[8] = "Quick, run royal department and find emoji.";
        qArray[9] = "Find the flag in the department.";
        qArray[10] = "I'm a yellow fruit that you might eat at lunch. When there's a group of me we are known as a bunch.Find me.";

        return qArray[i];
    }
    String ans(int i){

            aArray[1] = "74AD36";
            aArray[2] = "CI4568";
            aArray[3] = "button";
            aArray[4] = "block6";
            aArray[5] = "S339IT";
            aArray[6] = "mirror";
            aArray[7] = "ECE420";
            aArray[8] = "892GI6";
            aArray[9] = "JAZZ22";
            aArray[10] = "PDPDP";
        return aArray[i];

    }

}
