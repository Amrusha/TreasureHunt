package com.myapp.ahm.recyclerview;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FeedListActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewExample";
    private List<FeedItem> feedsList;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter adapter;
    private Context ctx = this;
    Integer it_no;
    DBopenhelper dop = new DBopenhelper(ctx);
    String lk = "false";
    String[] qq = new String[11];
    int i;
    int min,sec,mili,s_min=0,s_sec=0,s_mili=0,temp;

    public  Header getHeader()
    {
        s_min=0;
        s_sec=0;
        s_mili=0;
        DBopenhelper dBopenhelper = new DBopenhelper(this);
        Cursor cr = dBopenhelper.get_info3(dBopenhelper);
        if (cr.moveToFirst()) {
            do {
                min = cr.getInt(0);
                sec = cr.getInt(1);
                mili = cr.getInt(2);
                Log.e("Trouble2","time2:"+min+sec+mili);
                s_min = s_min+min;
                s_sec = s_sec+sec;
                s_mili = s_mili+mili;
                if(s_mili>60) {
                    temp = s_mili - 60;
                    s_mili = temp;
                    s_sec += 1;
                }
                if(s_sec>60) {
                    temp = s_sec - 60;
                    s_sec = temp;
                    s_min += 1;
                }
            } while (cr.moveToNext());
        }


        Header header = new Header();
        if(s_min == 0&&s_sec==0&&s_mili==0)
            header.setHeader("Time elapsed 00:00:00");
        else if(s_min == 0)
            header.setHeader("Time elapsed 00:"+s_sec+":"+s_mili);
        else
            header.setHeader("Time elapsed "+s_min+":"+s_sec+":"+s_mili);
        return header;
    }

    @Override
    public void onBackPressed(){
        return;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        feedsList = new ArrayList<>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Cursor cr = dop.get_info2(dop);
        cr.moveToFirst();
        do {
            it_no = cr.getInt(0);
        } while (cr.moveToNext());

            for (int i = 0; i < 11; i++) {
                qq[i] = "Challenge " + i;
                FeedItem item = new FeedItem();
                item.setTitle(qq[i]);
                item.setNo(i);
                feedsList.add(item);
            }

        adapter = new MyRecyclerAdapter(FeedListActivity.this, feedsList,getHeader());
        mRecyclerView.setAdapter(adapter);
    }

}


