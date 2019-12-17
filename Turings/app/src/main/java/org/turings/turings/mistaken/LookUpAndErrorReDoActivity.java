package org.turings.turings.mistaken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.turings.turings.MainActivity;
import org.turings.turings.R;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.turings.turings.mistaken.SubjectMsg;

public class LookUpAndErrorReDoActivity extends AppCompatActivity {

    private ImageView img_ylx;//返回按钮
    private ImageView printer_ylx;//打印机
    private TextView math_text_ylx;//选择数学
    private TextView chinese_text_ylx;//选择语文
    private TextView english_text_ylx;//选择英语
    private LinearLayout choose_one_ylx;//选择第一个选项
    private ImageView leaf_one_ylx;//第一个叶子
    private TextView  leaf_one_text_ylx;//第一个叶子内容
    private LinearLayout choose_two_ylx;//选择第二个选项
    private ImageView leaf_two_ylx;//第二个叶子
    private TextView  leaf_two_text_ylx;//第二个叶子内容
    private LinearLayout choose_three_ylx;//选择第二个选项
    private ImageView leaf_three_ylx;//第二个叶子
    private TextView  leaf_three_text_ylx;//第二个叶子内容
    private ImageView drop_more_menu_ylx;//下拉加载更多tag标签
    private ListView subject_list_ylx;//题目listView
    private CustomClickListener listener;//点击事件监听器
    private List<SubjectMsg> list;//列表要展示的题目资源
    private SmartRefreshLayout srl;//刷新
    private ImageView nullYlx;//如果没有题目显示
    private LinearLayout lyYlx;//空空如也外框
    private Menu menu;
    private String subject ="数学";//选中的学科
    private String tag = "集合";
    private OkHttpClient okHttpClient;
    private CustomAdapterYLX customAdapterYLX;//适配器
    private List<SubjectMsg> lists;//列表要展示的题目资源
    private String uId;//用户的id
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String ms = (String) msg.obj;
            if(ms.equals("刷新")){
                list.clear();
                for(SubjectMsg subjectMsg : lists){
                    list.add(subjectMsg);
                    customAdapterYLX.notifyDataSetChanged();
                }
                if(list.size() == 0 || list == null){
                    nullYlx.setVisibility(View.VISIBLE);
                    lyYlx.setVisibility(View.VISIBLE);
                }else {
                    nullYlx.setVisibility(View.GONE);
                    lyYlx.setVisibility(View.GONE);
                }
            }else {
                list.clear();
                if(list.size() == 0 || list == null){
                    nullYlx.setVisibility(View.VISIBLE);
                    lyYlx.setVisibility(View.VISIBLE);
                }else {
                    nullYlx.setVisibility(View.GONE);
                    lyYlx.setVisibility(View.GONE);
                }
                customAdapterYLX.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up_and_error_re_do_ylx);
        //获取用户的id
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        uId = sp.getString("uId",null);
        //获取控件
        getViews();
        //初始化数据（数据应该从数据库中获取）
        InitData();
        if (list.size() == 0 || list == null){
            nullYlx.setVisibility(View.VISIBLE);
        }else {
            nullYlx.setVisibility(View.GONE);
        }
        //创建适配器
        customAdapterYLX = new CustomAdapterYLX(list,getApplicationContext(),R.layout.item_list_subject_ylx_layout);
        //绑定适配器
        subject_list_ylx.setAdapter(customAdapterYLX);
        //绑定事件
        rigisterListener();
        //点击listView的item事件
        subject_list_ylx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入错题重做页
                redoErrorQuestions(list.get(position));
            }
        });
        customAdapterYLX.notifyDataSetChanged();
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
                Toast.makeText(getApplicationContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    //刷新数据
    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchSubjectMsgBySubjectAndTag(subject,tag);
            }
        }).start();
    }

    //进入错题重做页
    private void redoErrorQuestions(SubjectMsg msg) {
        if(msg.getType().equals("选择题")){
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), RedoWrongQuestionsActivity.class);
            intent.putExtra("subject", msg);
            startActivity(intent);
        }else{
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), RedoWrongBigQuestionActivity.class);
            intent.putExtra("subject", (Serializable) msg);
            startActivity(intent);
        }
    }

    //初始化数据(应该从数据库中获取)
    private void InitData() {
        list = new ArrayList<>();
        subject = "数学";
        tag = "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchSubjectMsgBySubjectAndTag(subject,tag);
            }
        }).start();
    }

    //绑定事件
    private void rigisterListener() {
        listener = new CustomClickListener();
        drop_more_menu_ylx.setOnClickListener(listener);
        math_text_ylx.setOnClickListener(listener);
        chinese_text_ylx.setOnClickListener(listener);
        english_text_ylx.setOnClickListener(listener);
        img_ylx.setOnClickListener(listener);
        leaf_one_text_ylx.setOnClickListener(listener);
        leaf_two_text_ylx.setOnClickListener(listener);
        leaf_three_text_ylx.setOnClickListener(listener);
    }

    //获取控件
    private void getViews() {
        img_ylx = findViewById(R.id.img_ylx);
        printer_ylx=findViewById(R.id.printer_ylx);
        math_text_ylx=findViewById(R.id.math_text_ylx);
        chinese_text_ylx=findViewById(R.id.chinese_text_ylx);
        english_text_ylx=findViewById(R.id.english_text_ylx);
        choose_one_ylx=findViewById(R.id.choose_one_ylx);
        leaf_one_ylx = findViewById(R.id.leaf_one_ylx);
        leaf_one_text_ylx = findViewById(R.id.left_one_text_ylx);
        choose_two_ylx=findViewById(R.id.choose_two_ylx);
        leaf_two_ylx=findViewById(R.id.leaf_two_ylx);
        leaf_two_text_ylx=findViewById(R.id.left_two_text_ylx);
        choose_three_ylx=findViewById(R.id.choose_three_ylx);
        leaf_three_ylx=findViewById(R.id.leaf_three_ylx);
        leaf_three_text_ylx=findViewById(R.id.left_three_text_ylx);
        drop_more_menu_ylx=findViewById(R.id.drop_more_menu_ylx);
        subject_list_ylx=findViewById(R.id.subject_list_ylx);
        srl = findViewById(R.id.srl);
        nullYlx = findViewById(R.id.nullYlx);
        lyYlx = findViewById(R.id.lyylx);
    }
    //事件监听器类
    class CustomClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_ylx:
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setAction("mistake");
                    startActivity(intent);
                    finish();
                    break;
                case R.id.left_one_text_ylx://选中第一个叶子中的内容为搜索条件，去数据库进行搜索
                    tag = leaf_one_text_ylx.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            searchSubjectMsgBySubjectAndTag(subject,tag);
                        }
                    }).start();
                    break;
                case R.id.left_two_text_ylx://选中第二个叶子中的内容为搜索条件，去数据库进行搜索
                    tag = leaf_two_text_ylx.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            searchSubjectMsgBySubjectAndTag(subject,tag);
                        }
                    }).start();
                    break;
                case R.id.left_three_text_ylx://选中第三个叶子中的内容为搜索条件，去数据库进行搜索
                    tag = leaf_three_text_ylx.getText().toString();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            searchSubjectMsgBySubjectAndTag(subject,tag);
                        }
                    }).start();
                    break;
                case R.id.drop_more_menu_ylx://点击更多tag
                    if(subject.equals("数学")){
                        showPopupMenu(drop_more_menu_ylx);
                    }else if(subject.equals("语文")){
                        showPopupChineseMenu(drop_more_menu_ylx);
                    }else {
                        showPopupEnglishMenu(drop_more_menu_ylx);
                    }
                    break;
                case R.id.math_text_ylx://选中数学
                    selectionMop();
                    break;
                case R.id.chinese_text_ylx://选中语文
                    selectionCop();
                    break;
                case R.id.english_text_ylx://选中英语
                    selectionEop();
                    break;
            }
        }
    }

    //选中英语
    private void selectionEop() {
        english_text_ylx.setBackground(getResources().getDrawable(R.drawable.tab_subject_background));
        chinese_text_ylx.setBackgroundColor(Color.WHITE);
        math_text_ylx.setBackgroundColor(Color.WHITE);
        leaf_one_text_ylx.setText("听力");
        leaf_two_text_ylx.setText("阅读");
        leaf_three_text_ylx.setText("完型");
        //去数据库中搜索符合条件的题目展示在listView当中
        subject = "英语";
        tag = "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchSubjectMsgBySubjectAndTag(subject,tag);
            }
        }).start();
    }


    //选中语文
    private void selectionCop() {
        chinese_text_ylx.setBackground(getResources().getDrawable(R.drawable.tab_subject_background));
        math_text_ylx.setBackgroundColor(Color.WHITE);
        english_text_ylx.setBackgroundColor(Color.WHITE);
        leaf_one_text_ylx.setText("阅读");
        leaf_two_text_ylx.setText("古文");
        leaf_three_text_ylx.setText("作文");
        //去数据库中搜索符合条件的题目展示在listView当中
        subject = "语文";
        tag = "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchSubjectMsgBySubjectAndTag(subject,tag);
            }
        }).start();
    }

    //选中数学
    private void selectionMop() {
        math_text_ylx.setBackground(getResources().getDrawable(R.drawable.tab_subject_background));
        chinese_text_ylx.setBackgroundColor(Color.WHITE);
        english_text_ylx.setBackgroundColor(Color.WHITE);
        leaf_one_text_ylx.setText("集合");
        leaf_two_text_ylx.setText("映射");
        leaf_three_text_ylx.setText("函数");
        //去数据库中搜索符合条件的题目展示在listView当中
        subject = "数学";
        tag = "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchSubjectMsgBySubjectAndTag(subject,tag);
            }
        }).start();
    }

    //更多标签的显示方法
    private void showPopupMenu(View view) {
        //popupMenu显示在view的相对位置
        PopupMenu popupMenu = new PopupMenu(this,view);
        //menu布局
        popupMenu.getMenuInflater().inflate(R.menu.tag_menu_layout, popupMenu.getMenu());
        menu = popupMenu.getMenu();
        //设置字体颜色为蓝色
        fixTextColor(R.id.item_one,"导数",0,2);
        fixTextColor(R.id.item_two,"微积分",0,3);
        fixTextColor(R.id.item_three,"三角函数",0,4);
        fixTextColor(R.id.item_four,"平面向量",0,4);
        fixTextColor(R.id.item_five,"数列",0,2);
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //要去数据库进行搜索符合条件的题目展示在listView中
                tag = item.getTitle().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        searchSubjectMsgBySubjectAndTag(subject,tag);
                    }
                }).start();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    //英语条件下的更多标签显示方法
    private void showPopupEnglishMenu(View view) {
        //popupMenu显示在view的相对位置
        PopupMenu popupMenu = new PopupMenu(this,view);
        //menu布局
        popupMenu.getMenuInflater().inflate(R.menu.tag_menu_english_ylx_layout, popupMenu.getMenu());
        menu = popupMenu.getMenu();
        //设置字体颜色为蓝色
        fixTextColor(R.id.item_one,"七选五",0,3);
        fixTextColor(R.id.item_two,"英语知识运用",0,6);
        fixTextColor(R.id.item_three,"语法填空",0,4);
        fixTextColor(R.id.item_four,"短文改错",0,4);
        fixTextColor(R.id.item_five,"书面表达",0,4);
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //要去数据库进行搜索符合条件的题目展示在listView中
                tag = item.getTitle().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        searchSubjectMsgBySubjectAndTag(subject,tag);
                    }
                }).start();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();

    }
    //语文条件下的更多标签显示方法
    private void showPopupChineseMenu(View view) {
        //popupMenu显示在view的相对位置
        PopupMenu popupMenu = new PopupMenu(this,view);
        //menu布局
        popupMenu.getMenuInflater().inflate(R.menu.tag_menu_chinese_ylx_layout, popupMenu.getMenu());
        menu = popupMenu.getMenu();
        //设置字体颜色为蓝色
        fixTextColor(R.id.item_one,"文句翻译",0,4);
        fixTextColor(R.id.item_two,"诗歌鉴赏",0,4);
        fixTextColor(R.id.item_three,"名句名篇",0,4);
        fixTextColor(R.id.item_four,"文字表达",0,4);
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //要去数据库进行搜索符合条件的题目展示在listView中
                tag = item.getTitle().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        searchSubjectMsgBySubjectAndTag(subject,tag);
                    }
                }).start();
                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();

    }

    //改变pupopMenu的字体颜色的方法
    private void fixTextColor(int id,String title,int start,int end) {
        SpannableStringBuilder builder = new SpannableStringBuilder(title);
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.themeColor));
        builder.setSpan(foregroundColorSpan,start,end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        menu.findItem(id).setTitle(builder);
    }
    //按照学科（数学、语文、英语）搜索题目，放入listView中
    private void searchSubjectMsgBySubjectAndTag(String subject,String tag) {
        okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("subject",subject)
                .add("tag",tag)
                .add("uId", uId)
                .build();
        String url = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/SearchSubjectMsgBySubjectServlet";
        final Request request = new Request.Builder().post(formBody).url(url).build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("lww", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        Type type = new TypeToken<List<SubjectMsg>>(){}
                                .getType();
                        //反序列化
                        lists = new ArrayList<>();
                        lists = gson.fromJson(response.body().string(),type);
                        Message msg = Message.obtain();
                        if(lists.size() != 0){
                            msg.obj = "刷新";
                        }else {
                            msg.obj = "无数据";
                        }
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }
}
