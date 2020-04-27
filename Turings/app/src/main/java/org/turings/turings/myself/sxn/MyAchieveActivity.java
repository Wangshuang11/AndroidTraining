package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.myself.tools.MyUrl;


public class MyAchieveActivity extends AppCompatActivity {

    private Button back;
    private ImageView avatar;
    private Bitmap bitmap;
    private TextView name;
    private MyUrl myUrl;
    private int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achieve);

        back=findViewById(R.id.sxn_achieve_back);
        avatar=findViewById(R.id.sxn_achieve_avatar);
        name=findViewById(R.id.sxn_achieve_name);
        circleAvatar();
        Intent intent1=getIntent();
        myUrl=new MyUrl(this);
        id= Integer.parseInt(getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        if(intent1!=null){
            myUrl.sendToServerMyAch(getResources().getString(R.string.connUrl)+"/ReFreshMyInfomation?uid="+id,
                    R.layout.activity_achieve,avatar,name);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setClass(MyAchieveActivity.this, MainActivity.class);
                        intent.setAction("loginBackMyself");
                        startActivityForResult(intent,0);
                    }
                });
            }
        });

    }
    private void circleAvatar(){
        bitmap= BitmapFactory.decodeResource(getResources(),R.mipmap.girl);
        RequestOptions requestOptions=new RequestOptions().circleCrop();
        Glide.with(this).load(new BitmapDrawable(bitmap)).apply(requestOptions).into(avatar);
    }
}
