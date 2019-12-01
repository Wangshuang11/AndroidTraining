package org.turings.turings.index;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.MainActivity;
import org.turings.turings.R;

public class IndexActivity extends AppCompatActivity {
    private Button button;
    private boolean flag=false;
    private ImageView img;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String info=(String)msg.obj;
            if(msg.what==100){
                if("success".equals(info)){
                    Intent intent=new Intent();
                    intent.setClass(IndexActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in, R.anim.out);
                    IndexActivity.this.finish();
                }
            }else{
                button.setText(info+" | 点击跳过");
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        button=findViewById(R.id.btn);
        img=findViewById(R.id.img_lph);

        //无缝填充三秒跳广告图片的背景图
        setImgBackground();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag=true;
                Intent intent=new Intent();
                intent.setClass(IndexActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.in, R.anim.out);
                IndexActivity.this.finish();
            }
        });
        new Thread(){
            @Override
            public void run() {
                for(int i=10;i>0;i--){
                    try {
                        Message message=Message.obtain();
                        message.what=200;
                        message.obj=i+"";
                        handler.sendMessage(message);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                if(!flag){
                    Message message=Message.obtain();
                    message.what=100;
                    message.obj="success";
                    handler.sendMessage(message);
                }
            }
        }.start();
    }

    //无缝填充三秒跳广告图片的背景图
    private void setImgBackground() {
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.centerCrop();
        Glide.with(this).load(R.mipmap.advertisement).apply(requestOptions).into(img);
    }
}

