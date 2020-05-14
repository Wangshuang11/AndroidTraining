package org.turings.turings.myself.sxn;

import android.content.Intent;
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

        ivAch = findViewById(R.id.ivAch);
        AnimationDrawable amDrawable = (AnimationDrawable) ivAch.getDrawable();
        amDrawable.start();
    }
}
