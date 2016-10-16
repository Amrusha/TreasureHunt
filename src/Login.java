package com.myapp.ahm.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Login extends AppCompatActivity implements View.OnClickListener {
    Button btn;
    Context ctx = this;
    EditText et1,et2;
    String st1,st2;
    DBopenhelper dop = new DBopenhelper(ctx);

    public static Bitmap getRoundedCornerImage(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = 100;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et1 = (EditText)findViewById(R.id.editText2);
        et2 = (EditText)findViewById(R.id.editText3);
        btn = (Button)findViewById(R.id.button2);
        btn.setOnClickListener(this);

        Cursor cr = dop.get_info(dop);
        if(cr.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "Trying to be smart huh?!", Toast.LENGTH_LONG).show();
            System.exit(0);
        }
        dop.delete_monitor(dop);
        dop.delete_iteration(dop);
        dop.delete_timer(dop);

        ImageView image = (ImageView) findViewById(R.id.imageView);
        Bitmap bitImg = BitmapFactory.decodeResource(getResources(),
                R.drawable.login4);
        image.setImageBitmap(getRoundedCornerImage(bitImg));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
        st1=et1.getText().toString();
        st2=et2.getText().toString();

        if(st1.contains("\n"))
            st1 = st1.replaceAll("\n","");
        if(st2.contains("\n"))
            st2 = st2.replaceAll("\n","");
        if((st1.equals("smvitm")||st1.equals("Smvitm"))&&(st2.equals("hunt")||st2.equals("Hunt"))) {
            for (int i = 0; i < 11; i++) {
                if (i == 0||i==1)
                    dop.put_info(dop, i, "true");
                else
                    dop.put_info(dop, i, "false");
            }

            DBopenhelper dop1 = new DBopenhelper(ctx);
            dop1.put_info2(dop, 0);

            startActivity(new Intent(this, Instructions.class));
        }
        else
            Toast.makeText(getApplicationContext(),"Enter valid Login Id and password",Toast.LENGTH_SHORT).show();

    }
}
