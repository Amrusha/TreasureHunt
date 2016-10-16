package com.myapp.ahm.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Answer_me extends AppCompatActivity implements View.OnClickListener {

    private TextView timerValue;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    EditText et;
    TextView tv,go,tv1;
    Button bt;
    String st,ques,answ;
    Questions qn = new Questions(this);
    Share sh;
    Integer challenge_no,chance=0;
    Context ctx = this;
    int c_mins,c_secs,c_milliseconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_me);
        et = (EditText)findViewById(R.id.editText);
        tv = (TextView)findViewById(R.id.textView);
        tv1 = (TextView)findViewById(R.id.txtHeader);
        bt = (Button)findViewById(R.id.button);
        go = (TextView)findViewById(R.id.gameOver);

        timerValue = (TextView) findViewById(R.id.timerValue);
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);

                bt.setOnClickListener(this);
                Bundle bundle = getIntent().getExtras();
                challenge_no = bundle.getInt("SEND");
                Log.e("Answer_me", "no" + challenge_no);
                ques = qn.qtn(challenge_no);
                answ = qn.ans(challenge_no);
                tv.setText(ques);
                SpannableString content = new SpannableString("Task "+challenge_no);
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                tv1.setText(content);
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_answer_me, menu);
                return true;
            }

            @Override
            public boolean onOptionsItemSelected(MenuItem item) {
                // Handle action bar item clicks here. The action bar will
                // automatically handle clicks on the Home/Up button, so long
                // as you specify a parent activity in AndroidManifest.xml.
                int id = item.getItemId();

                //noinspection SimplifiableIfStatement
                if (id == R.id.action_settings) {
                    return true;
                }

                return super.onOptionsItemSelected(item);
            }

             @Override
            public void onBackPressed(){
            return;
            }


            @Override
            public void onClick(View v) {
                //timeSwapBuff += timeInMilliseconds;
                updatedTime = timeSwapBuff + timeInMilliseconds;
                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (updatedTime % 100);
                c_mins = mins/2;
                c_secs = secs/2;
                c_milliseconds = milliseconds/2;
                DBopenhelper dop = new DBopenhelper(this);
                Log.e("Trouble","time:"+c_mins+c_secs+c_milliseconds);
                dop.put_info3(dop,mins,secs,milliseconds);
                customHandler.removeCallbacks(updateTimerThread);

                Integer it_no = null;
                int ch_no;
                String status;
                DBopenhelper dop1 = new DBopenhelper(ctx);
                Cursor cr = dop1.get_info2(dop1);
                if (cr.moveToFirst()) {
                    do {
                        it_no = cr.getInt(0);
                        Log.e("Answer_me", "iteration no:" + it_no);
                    } while (cr.moveToNext());
                }
                //DBopenhelper dop = new DBopenhelper(ctx);
                if (chance<=4) {
                    st = et.getText().toString();
                    if(st.contains("\n"))
                        st = st.replaceAll("\n","");
                    if (st.equals(answ)) {
                        ++challenge_no;
                        dop1.update_monitor(dop1, challenge_no);
                        dop1.update_iteration(dop1, it_no);
                        Cursor cr1 = dop1.get_info(dop1);
                        if (cr1.moveToFirst()) {
                            do {
                                ch_no = cr1.getInt(0);
                                status = cr1.getString(1);
                                Log.e("Answer_me", "challenge " + ch_no + "," + status);
                            } while (cr1.moveToNext());
                        }
                        Cursor cr2 = dop1.get_info(dop1);
                        if (cr2.moveToFirst()) {
                            do {
                                it_no = cr2.getInt(0);
                                Log.e("Answer_me", "iteration number: " + it_no);
                            } while (cr2.moveToNext());
                        }
                        startActivity(new Intent(this, FeedListActivity.class));
                    } else {
                        chance++;
                        startTime = SystemClock.uptimeMillis();
                        customHandler.postDelayed(updateTimerThread, 0);
                        Log.e("Answer", "your answer: " + st + " correct answer: " + answ);
                        Toast toast = Toast.makeText(ctx, "You have " + (5 - chance) + " chances left", Toast.LENGTH_SHORT);
                        LinearLayout toastLayout = (LinearLayout) toast.getView();
                        TextView toastTV = (TextView) toastLayout.getChildAt(0);
                        toastTV.setTextSize(20);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                        //Toast.makeText(getApplicationContext(), "you have " + (6 - chance) + " chances left", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    go.setText("GAME OVER!!");
                    tv.setText("");
                    for(int i=0;i<11;i++)
                     dop1.update_monitor(dop1,i);
                }

            }
    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 100);
            timerValue.setText("" + mins + ":"
                    + String.format("%02d", secs) + ":"
                    + String.format("%02d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };
        }
