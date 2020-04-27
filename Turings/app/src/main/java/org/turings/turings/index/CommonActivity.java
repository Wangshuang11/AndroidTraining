package org.turings.turings.index;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zhuang.likeviewlibrary.LikeView;


import org.turings.turings.R;
import org.turings.turings.index.entity.Story;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class CommonActivity extends AppCompatActivity {
    private RecyclerView comment_list;
    private EditText comment_content;
    private Button comment_send;
    private TextView text_title;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private TextView text_content;
    private LikeView likeView;
    private RelativeLayout rl_comment;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string=(String)msg.obj;
            if(string.startsWith("lph_one")){
                Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_SHORT).show();
            }else if(string.startsWith("two")){
                Toast.makeText(getApplicationContext(),"取消收藏",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lph_activity_common);
        getViews();
        Intent intent = getIntent();
        String storyStr = intent.getStringExtra("story");
        final Story story = new Gson().fromJson(storyStr, Story.class);
        text_title.setText(story.getTitle());
        text_content.setText(story.getContent());
        likeView.setLikeCount(Integer.valueOf(story.getStarnum()));
        likeView.setOnLikeListeners(new LikeView.OnLikeListeners() {
            @Override
            public void like(boolean isCancel) {
                //如果设置了like_canCancel为false，则isCancel可以不管，此时表示likeview被点击了
                //如果设置了like_canCancel为true,表示可以取消点赞，那么isCancel为true时，表示取消点赞，为false时，表示点赞
                if(isCancel==true){
//                   Log.e("true",likeView.getIconScaleUnSelect()+"");
                    int num=Integer.valueOf(story.getStarnum());
                    num--;
                    if(num<0){
                        num=0;
                    }
                    story.setStarnum(num+"");
                    sendToServer(num+"",story.getId(),2);
                }else if(isCancel==false){
//                   Log.e("false",likeView.getIconScaleUnSelect()+"");
                    int num=Integer.valueOf(story.getStarnum());
                    num++;
                    story.setStarnum(num+"");
                    sendToServer(num+"",story.getId(),1);
                }
            }
        });
        RequestOptions ro = new RequestOptions().placeholder(R.drawable.loading);
        Glide.with(CommonActivity.this).load("https://p1.ssl.qhimgs1.com/sdr/400__/t01989576057c377587.jpg").apply(ro).into(img1);
        Glide.with(CommonActivity.this).load("http://img.smzy.com/Soft/UploadPic/2016-3/201631214351846179.jpg").apply(ro).into(img2);
        Glide.with(CommonActivity.this).load("https://p0.ssl.qhimgs1.com/sdr/400__/t01221d3ed68502af20.jpg").apply(ro).into(img3);
//        comment_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        comment_list.setAdapter(commentAdapter2);
//        comment_list.setVisibility(View.INVISIBLE);


    }

    private void getViews() {
//        comment_list = findViewById(R.id.comment_list);
//        comment_content = findViewById(R.id.comment_content);
//        comment_send = findViewById(R.id.comment_send);
        text_title = findViewById(R.id.text_title);
        text_content = findViewById(R.id.text_content);
//        rl_comment = findViewById(R.id.rl_comment);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        likeView=findViewById(R.id.likeView);
    }
    private void sendToServer(final String starnum, final int id,final int flag) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String string = "http://"+getApplicationContext().getResources().getString(R.string.lphipConfig)+":8080/Turings/lph/updateStory?starnum="+starnum+"&id="+id+"&flag="+flag;
                    URL url = new URL(string);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}
