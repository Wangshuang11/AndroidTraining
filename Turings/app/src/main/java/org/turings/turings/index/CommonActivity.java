package org.turings.turings.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;


import org.turings.turings.R;
import org.turings.turings.index.adapter.RecyclerCommentAdapter;
import org.turings.turings.index.entity.Comment2;
import org.turings.turings.index.entity.Story;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CommonActivity extends AppCompatActivity {
    private RecyclerView comment_list;
    private EditText comment_content;
    private Button comment_send;
    private List<Comment2> data;
    private RecyclerCommentAdapter commentAdapter2;
    private TextView text_title;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private TextView text_content;
    private RelativeLayout rl_comment;
    private Button speak;
    private TextView tv_click;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lph_activity_common);
        getViews();
        Intent intent=getIntent();
        String storyStr=intent.getStringExtra("story");
        Story story=new Gson().fromJson(storyStr,Story.class);
        text_title.setText(story.getTitle());
        text_content.setText(story.getContent());
        RequestOptions ro=new RequestOptions().placeholder(R.drawable.loading);
        Glide.with(CommonActivity.this).load(story.getImg1()).apply(ro).into(img1);
        Glide.with(CommonActivity.this).load(story.getImg2()).apply(ro).into(img2);
        Glide.with(CommonActivity.this).load(story.getImg3()).apply(ro).into(img3);
        comment_list.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        comment_list.setAdapter(commentAdapter2);
        comment_list.setVisibility(View.INVISIBLE);
        rl_comment.setVisibility(View.INVISIBLE);
        tv_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comment_content.setText("");
                comment_content.setHint("输入评论内容");
                comment_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String s=comment_content.getText().toString();
                        if(s.equals("")){
                            Toast.makeText(getApplicationContext(), "评论不能为空！", Toast.LENGTH_SHORT).show();
                        }else {
                            Comment2 comment2 = new Comment2();
                            comment2.setImg("frog");
                            comment2.setName("hello");
                            comment2.setContent(s);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                            String strDate = sdf.format(new Date());
                            comment2.setDatetv(strDate);
                            data.add(comment2);
                            commentAdapter2.notifyDataSetChanged();
                        }
                    }
                });
            }
        });
        speak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string=speak.getText().toString();
                switch (string){
                    case "我要评论":
                        comment_list.setVisibility(View.VISIBLE);
                        rl_comment.setVisibility(View.VISIBLE);
                        speak.setText("收起");
                        break;
                    case "收起":
                        comment_list.setVisibility(View.INVISIBLE);
                        rl_comment.setVisibility(View.INVISIBLE);
                        speak.setText("我要评论");
                        break;
                }

            }
        });
    }

    private void getViews() {
        comment_list=findViewById(R.id.comment_list);
        comment_content=findViewById(R.id.comment_content);
        comment_send=findViewById(R.id.comment_send);
        text_title=findViewById(R.id.text_title);
        text_content=findViewById(R.id.text_content);
        rl_comment=findViewById(R.id.rl_comment);
        speak=findViewById(R.id.speak);
        img1=findViewById(R.id.img1);
        img2=findViewById(R.id.img2);
        img3=findViewById(R.id.img3);
        tv_click=findViewById(R.id.tv_click);
        data=new ArrayList<>();
        innitDatas(data);
        commentAdapter2=new RecyclerCommentAdapter(this,R.layout.lph_activity_text_item,data,comment_content,comment_send);
    }
    private void innitDatas(List<Comment2> list){
        Comment2 c1=new Comment2("boilt","会飞的猪","打Call!!!","2019-12-04 19:07:13");
        Comment2 c2=new Comment2("boilt","甲骨文","the same to you","2019-12-04 09:27:33");
        list.add(c1);
        list.add(c2);
    }
}
