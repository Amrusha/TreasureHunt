package com.myapp.ahm.recyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Instructions extends AppCompatActivity implements View.OnClickListener {
    Button gt;
    TextView inst,tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        gt = (Button)findViewById(R.id.button3);
        gt.setOnClickListener(this);
        tv5 = (TextView)findViewById(R.id.t5);
        SpannableString content = new SpannableString("Instructions");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        tv5.setText(content);

        inst = (TextView)findViewById(R.id.in);
        inst.setText("-> There are a total of 10 challenges." +
                "\n\n-> Each comes with a count up timer." +
                "\n\n-> You will be given 5 chances to guess the right answer." +
                "\n  For each incorrect guess ,the number of chances is decremented." +
                "\n  Please note ,the game ends after all chances have been utilized ." +
                "\n\n-> If the right answer is given  the next challenge is unlocked." +
                "\n\n-> Once you complete all 10 challenges notify the coordinator of the time consumed before closing the app." +
                "\n\n-> The team with the best timing wins." +
                "\n\n\nGOOD LUCK! :)");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructions, menu);
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
    public void onClick(View v) {
        startActivity(new Intent(this,FeedListActivity.class));
    }
}
