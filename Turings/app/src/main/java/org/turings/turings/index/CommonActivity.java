package org.turings.turings.index;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.zhuang.likeviewlibrary.LikeView;


import org.turings.turings.R;
import org.turings.turings.index.adapter.RecyclerCommentAdapter;
import org.turings.turings.index.entity.Comment2;
import org.turings.turings.index.entity.Comment2Json;
import org.turings.turings.index.entity.Story;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class CommonActivity extends AppCompatActivity {
    private RecyclerView comment_list;
    private EditText comment_content;
    private Button comment_send;
    private Button speak;
    private TextView text_title;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private TextView text_content;
    private LikeView likeView;
    private LinearLayout rl_comment;
    private List<Comment2>lists=new ArrayList<>();
    private RecyclerCommentAdapter commentAdapter;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    String string=(String)msg.obj;
                    if(string.startsWith("lph_one")){
                        Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }else if(string.startsWith("two")){
                        Toast.makeText(getApplicationContext(),"取消收藏",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 200:
                    Gson gson=new Gson();
                    String comments=(String)msg.obj;
                    Log.e("kk",comments);
                    lists.clear();
                    lists=getDatas(gson,comments);
                    Log.e("aa",lists.size()+"");
                    commentAdapter=new RecyclerCommentAdapter(getApplicationContext(),R.layout.lph_activity_text_item2,lists);
                    comment_list.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                    comment_list.setAdapter(commentAdapter);
                    break;
                case 300:
                    String n=(String)msg.obj;
                    if(n.equals("1")){
                        sendToServerForComment();
                        Toast.makeText(getApplicationContext(),"评论成功",Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lph_activity_common);
        getViews();
        rl_comment.setVisibility(View.INVISIBLE);
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
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str=speak.getText().toString();
                if(str.equals("收起")){
                    comment_list.setVisibility(View.INVISIBLE);
                    rl_comment.setVisibility(View.INVISIBLE);
                    speak.setText("评论");
                }else{
                    sendToServerForComment();
                    speak.setText("收起");
                    comment_list.setVisibility(View.VISIBLE);
                    rl_comment.setVisibility(View.VISIBLE);
                }

            }
        });
        comment_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id=Integer.parseInt(getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
                if(id==0){
                    Toast.makeText(getApplicationContext(),"请先登录再评论",Toast.LENGTH_SHORT).show();
                }else{
                    String content=comment_content.getText().toString();
                    if (content.equals("")){
                        Toast.makeText(getApplicationContext(),"请输入评论内容",Toast.LENGTH_SHORT).show();
                    }else{
                        sendToServerInsertComment(id,content);
                        comment_content.setText("");
                    }

                }
            }
        });


    }

    private void getViews() {
        comment_list = findViewById(R.id.comment_list);
        comment_content = findViewById(R.id.comment_content);
        comment_send = findViewById(R.id.comment_send);
        speak=findViewById(R.id.speak);
        text_title = findViewById(R.id.text_title);
        text_content = findViewById(R.id.text_content);
        rl_comment = findViewById(R.id.rl_comment);
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
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what=100;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void sendToServerInsertComment(final int id, final String content) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String string = "http://"+getApplicationContext().getResources().getString(R.string.lphipConfig)+":8080/Turings/lph/insertComment?id="+id+"&content="+content;
                    URL url = new URL(string);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what=300;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void sendToServerForComment(){
        new Thread() {
            @Override
            public void run() {
                try {
                    String string = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/lph/findComments";
                    URL url = new URL(string);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what=200;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    public List<Comment2> getDatas(Gson gson, String str) {
        Comment2Json comment2Json=gson.fromJson(str,Comment2Json.class);
        return comment2Json.getList();
    }
}
