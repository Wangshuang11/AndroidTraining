package org.turings.turings.myself.sxn;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.turings.turings.R;
import org.turings.turings.near.Location.InformationActivity;
import org.turings.turings.near.Location.MiddleActivity;

public class ShowAchActivity extends AppCompatActivity {

    private ImageView ivAch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_ach);

        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(4000);//休眠
                    Intent intent = new Intent(ShowAchActivity.this, MyAchieveActivity.class);
                    intent.setAction("ach");
                    startActivity(intent);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

<<<<<<< Updated upstream
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("achStatus",Context.MODE_PRIVATE);
=======
        SharedPreferences sharedPref = getApplicationContext().getSharedPreferences("achStatus", Context.MODE_PRIVATE);
>>>>>>> Stashed changes
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("status",2);
        editor.commit();

        ivAch = findViewById(R.id.ivAch);
        AnimationDrawable amDrawable = (AnimationDrawable) ivAch.getDrawable();
        amDrawable.start();
    }
}
