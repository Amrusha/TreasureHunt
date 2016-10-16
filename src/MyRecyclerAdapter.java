package com.myapp.ahm.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by hp on 14-02-2016.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private List<FeedItem> feedItemList;
    private Context mContext;
    Integer no,ch_no;
    String status;
    Integer[] ch_no1;
    String[] status1;
    int i=0;
    DBopenhelper dBopenhelper;
    Header header;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public MyRecyclerAdapter(Context context, List<FeedItem> feedItemList,Header header) {
        this.feedItemList = feedItemList;
        this.mContext = context;
        this.header = header;
    }

    @Override
    public int getItemViewType(int position) {
        return (position==0) ? 0 : 1;   //try this line
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        dBopenhelper = new DBopenhelper(mContext);
        ch_no1 = new Integer[100];
        status1 = new String[200];

        if(viewType == TYPE_HEADER)
        {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_item, parent, false);
            return  new VHHeader(v);
        }
        else if(viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, null);
            CustomViewHolder viewHolder = new CustomViewHolder(view);
            return viewHolder;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }



    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,  int i) {

        if(holder instanceof VHHeader)
        {
            VHHeader VHheader = (VHHeader)holder;
            VHheader.txtTitle.setText(header.getHeader());
        }
        else if(holder instanceof CustomViewHolder) {
            CustomViewHolder customViewHolder = (CustomViewHolder)holder;
            FeedItem feedItem = feedItemList.get(i);
            Log.i("MyRecyclerAdapter", "feeding");

            Cursor cr = dBopenhelper.get_info(dBopenhelper);
            int j = 0;
            if (cr.moveToFirst()) {
                do {
                    ch_no = cr.getInt(0);
                    status = cr.getString(1);
                    ch_no1[j] = ch_no;
                    status1[j] = status;
                    j++;
                    Log.e("MyRecyclerAdapter", "challenge(j) " + ch_no + "," + status);
                } while (cr.moveToNext());
            } else
                Toast.makeText(mContext, "Something wrong", Toast.LENGTH_LONG).show();

            no = feedItem.getNo();
            if (feedItem.getNo().equals(0)||feedItem.getNo().equals(1)) {
                Picasso.with(mContext).load(feedItem.getThumbnail())
                        .error(R.drawable.unlock6)
                        .placeholder(R.drawable.unlock6)
                        .into(customViewHolder.imageView);
            } else if (status1[no].equals("true")) {
                Picasso.with(mContext).load(feedItem.getThumbnail())
                        .error(R.drawable.unlock6)
                        .placeholder(R.drawable.unlock6)
                        .into(customViewHolder.imageView);
            } else {
                Picasso.with(mContext).load(feedItem.getThumbnail())
                        .error(R.drawable.lock2)
                        .placeholder(R.drawable.lock2)
                        .into(customViewHolder.imageView);
            }

            customViewHolder.textView.setText(Html.fromHtml(feedItem.getTitle()));
            customViewHolder.textView.setOnClickListener(clickListener);
            customViewHolder.imageView.setOnClickListener(clickListener);
            customViewHolder.button.setOnClickListener(clickListener);
            customViewHolder.textView.setTag(customViewHolder);
            customViewHolder.imageView.setTag(customViewHolder);
            customViewHolder.button.setTag(customViewHolder);
        }

    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CustomViewHolder holder = (CustomViewHolder) view.getTag();
            int position = holder.getPosition();
            FeedItem feedItem = feedItemList.get(position);
            int no1 = feedItem.getNo();
            int len;
            Cursor cr = dBopenhelper.get_info(dBopenhelper);
            if(cr.moveToFirst()) {
                do {
                    ch_no = cr.getInt(0);
                    status = cr.getString(1);
                    ch_no1[i] = ch_no;
                    status1[i] = status;
                    Log.e("MyRecyclerAdapter", "challenge " + ch_no1[i] + "," + status1[i]);
                    i++;
                } while (cr.moveToNext());
            }
            len = i;
            for(int k=0;k<len;k++)
                Log.e("MyRecyclerAdapter","for loop status1 values"+status1[k]);

            if(status1[no1].equals("true")) {
                Intent intent = new Intent(mContext, Answer_me.class);
                intent.putExtra("SEND", feedItem.getNo());
                mContext.startActivity(intent);
            }
            else
                Toast.makeText(mContext, "Sorry!You can't do that.", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 60);
            int mins = secs / 60;
            secs = secs % 60;

            int milliseconds = (int) (updatedTime % 100);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"

                    + String.format("%02d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;
        protected Button  button;
        protected ImageView imageView;

        public CustomViewHolder(View view) {
            super(view);
            this.imageView = (ImageView) view.findViewById(R.id.thumbnail);
            this.textView = (TextView) view.findViewById(R.id.title);
            this.button = (Button)view.findViewById(R.id.b);
            imageView.setVisibility(View.VISIBLE);

        }
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView txtTitle;
        public VHHeader(View itemView) {
            super(itemView);
            this.txtTitle = (TextView)itemView.findViewById(R.id.txtHeader);
        }
    }
}



