package org.turings.turings.mistaken;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomAdapterToAddCartYLX;
import org.turings.turings.mistaken.customAdapterAndDialog.GridViewTagAdapter;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 功能：自主组卷（筛选和加错题蓝）
 */
public class AutoGeneratingPaperYLXActivity extends AppCompatActivity implements View.OnClickListener {
    public static int COUNT_IN_CART = 0;
    //筛选条件的整体布局
    private RelativeLayout chooseTop;
    //学科按钮
    private Button btnMath;//数学
    private Button btnChinese;//语文
    private Button btnEnglish;//英语
    //标签按钮
    private GridView gridView;
    //年份按钮
    private Button btnTimeAll;//全部
    private Button btnTimeOne;//2020
    private Button btnTimeTwo;//2019
    private Button btnTimeMore;//更早
    //题型按钮
    private Button btnTypeAll;//全部
    private Button btnTypeX;//选择题
    private Button btnTypeT;//填空题
    private Button btnTypeD;//大题
    //符合条件题数
    private TextView subCount;
    //题目listView
    private ListView lvMoreSub;
    //返回箭头
    private ImageView imgBack;
    //错题篮
    private RelativeLayout cuotilanRL;
    private TextView lanSubCount;//篮子中题目数量
    private List<SubjectMsg> subInCart=new ArrayList<>();//篮子中的题目
    //数据
    private List<SubjectMsg> subjectMsgs;
    //接收请求返回回来的数据
    private List<SubjectMsg> lists = new ArrayList<>();
    //搜索条件
    private String subject = "数学";//学科
    private Map<String,String> tags=new HashMap<>();//标签
    private Map<String,String> types=new HashMap<>();//题型
    private String timeYears="全部";//年份
    //用户id
    private String uId;
    //适配器
    private CustomAdapterToAddCartYLX customAdapterToAddCartYLX;
    private GridViewTagAdapter gridViewTagAdapter;
    //适配器绑定的标签数据
    private List<String> tagMEC = new ArrayList<>();
    //每个学科对应的标签
    private List<String> tagsMaths = new ArrayList<>();//数学标签
    private List<String> tagsEnglish = new ArrayList<>();//英语标签
    private List<String> tagsChinese = new ArrayList<>();//语文标签
    //筛选框的展示和收回
    private Animator mAnimator;//动画属性
    private LinearLayout showll;
    private LinearLayout shouQi;//收起
    //标记标签的选中结果
    private List<Integer> selectedPosition = new ArrayList<>();
    //title卷名
    private String titleName="自主组卷";
    private OkHttpClient okHttpClient;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String ms = (String) msg.obj;
            if(ms.equals("刷新")) {
                subjectMsgs.clear();
                for (SubjectMsg subjectMsg : lists) {
                    subjectMsgs.add(subjectMsg);
                }
                //刷新数据
                customAdapterToAddCartYLX.notifyDataSetChanged();
                subCount.setText(subjectMsgs.size()+"道题目");
            }else {
                Log.i("www", "handleMessage: 空空如也");
                Toast.makeText(getApplicationContext(),"没有符合条件的题目",Toast.LENGTH_SHORT).show();
                subjectMsgs.clear();
                subCount.setText(subjectMsgs.size()+"道题目");
                customAdapterToAddCartYLX.notifyDataSetChanged();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_generating_paper_ylx);
        //获取用户的id
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        uId = sp.getString("uId",null);
        //获取控件
        getViewss();
        Intent intent = getIntent();
        if (intent!=null){
            Bundle bundle = intent.getExtras();
            if(bundle != null && !bundle.equals("")){
                subInCart.clear();
                subInCart = (List<SubjectMsg>) bundle.getSerializable("subInCart");
                titleName = intent.getStringExtra("title");
                Log.i("www", "onCreate: "+subInCart.size()+"title"+titleName);
                lanSubCount.setText(subInCart.size()+"");
            }
        }
        //设置数据
        setData();
        //初始化数据
        init();
        getRegister();
    }

    /**设置标签数据*/
    private void setData() {
        //数学标签
        tagsMaths.add("全部");
        tagsMaths.add("集合");
        tagsMaths.add("函数");
        tagsMaths.add("映射");
        tagsMaths.add("导数");
        tagsMaths.add("微积分");
        tagsMaths.add("三角函数");
        tagsMaths.add("平面向量");
        tagsMaths.add("数列");
        //英语标签
        tagsEnglish.add("全部");
        tagsEnglish.add("听力");
        tagsEnglish.add("阅读");
        tagsEnglish.add("完型");
        tagsEnglish.add("七选五");
        tagsEnglish.add("英语知识运用");
        tagsEnglish.add("语法填空");
        tagsEnglish.add("短文改错");
        tagsEnglish.add("作文");
        //语文标签
        tagsChinese.add("全部");
        tagsChinese.add("阅读");
        tagsChinese.add("古文");
        tagsChinese.add("作文");
        tagsChinese.add("文句翻译");
        tagsChinese.add("诗歌鉴赏");
        tagsChinese.add("名句名篇");
        tagsChinese.add("文字表达");

    }
    //初始化数据
    private void init() {
        //默认搜索条件
        tags.put("全部","全部");
        types.put("全部","全部");
        //假数据

        subjectMsgs = new ArrayList<>();

        //从数据库中读取数据
        findSubjectMsgByIdCondition(subject,tags,types,timeYears);
        //标签绑定适配器
        tagMEC=new ArrayList<>();
        for (String tag:tagsMaths){
            tagMEC.add(tag);
        }
        gridViewTagAdapter = new GridViewTagAdapter(getApplicationContext(),tagMEC, R.layout.ylx_gv_item_tag_layout);
        gridView.setAdapter(gridViewTagAdapter);
        //适配器
        customAdapterToAddCartYLX = new CustomAdapterToAddCartYLX(subjectMsgs,getApplicationContext(), R.layout.ylx_subject_list_item_layout);
        //绑定适配器
        lvMoreSub.setAdapter(customAdapterToAddCartYLX);

    }

    //获取控件
    public void getViewss(){
        chooseTop = findViewById(R.id.chooseTop_ylx);
        btnMath = findViewById(R.id.btn_math_ylx);
        btnChinese = findViewById(R.id.btn_chinese_ylx);
        btnEnglish=findViewById(R.id.btn_english_ylx);
        gridView = findViewById(R.id.tagGridView_ylx);
        btnTypeAll = findViewById(R.id.btn_type_all_ylx);
        btnTypeX = findViewById(R.id.btn_typeX_ylx);
        btnTypeT = findViewById(R.id.btn_typeT_ylx);
        btnTypeD = findViewById(R.id.btn_typeD_ylx);
        btnTimeAll=findViewById(R.id.btn_time_all_ylx);
        btnTimeOne=findViewById(R.id.btn_timeOne_ylx);
        btnTimeTwo=findViewById(R.id.btn_timeTwo_ylx);
        btnTimeMore=findViewById(R.id.btn_timeMore_ylx);
        subCount = findViewById(R.id.subCount_ylx);
        imgBack = findViewById(R.id.imgBack_ylx);
        cuotilanRL = findViewById(R.id.zuJuanLan_ylx);
        lanSubCount=findViewById(R.id.count_ylx);
        lvMoreSub=findViewById(R.id.lv_moreSub_ylx);
        showll = findViewById(R.id.showll_ylx);
        shouQi = findViewById(R.id.shouQi_ylx);
    }

    //注册监听器，绑定事件
    private void getRegister() {
        showll.setOnClickListener(this);
        shouQi.setOnClickListener(this);
        btnMath.setOnClickListener(this);
        btnChinese.setOnClickListener(this);
        btnEnglish.setOnClickListener(this);
        btnTypeAll.setOnClickListener(this);
        btnTypeX.setOnClickListener(this);
        btnTypeT.setOnClickListener(this);
        btnTypeD.setOnClickListener(this);
        btnTimeAll.setOnClickListener(this);
        btnTimeOne.setOnClickListener(this);
        btnTimeTwo.setOnClickListener(this);
        btnTimeMore.setOnClickListener(this);
        imgBack.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Button button = view.findViewById(R.id.btn_math_ylx);
                Log.i("www", "onItemClick: button的值"+button.getText().toString().trim());
                if(button.getText().toString().trim().equals("全部")){
                    tags.clear();
                    tags.put("全部","全部");
                    //除了全部，其他的换背景
                    selectedPosition.clear();
                    selectedPosition.add(i);
                    //改变选中位置
                    gridViewTagAdapter.clearSelection(selectedPosition);
                    //更新数据
                    gridViewTagAdapter.notifyDataSetChanged();
                }else{
                    if(tags.containsKey("全部")){
                        tags.remove("全部");
                        selectedPosition.clear();
                    }
                    if(!tags.containsKey(button.getText().toString().trim())){
                        tags.put(button.getText().toString().trim(),button.getText().toString().trim());
                        //变色
                        selectedPosition.add(i);
                        //改变选中位置
                        gridViewTagAdapter.clearSelection(selectedPosition);
                        //更新数据
                        gridViewTagAdapter.notifyDataSetChanged();
                    }else {
                        tags.remove(button.getText().toString().trim());
                        Log.i("www", "onItemClick: 删除位置"+i);
                        //变色
                        selectedPosition.remove((Integer) i);
                        //改变选中位置
                        gridViewTagAdapter.clearSelection(selectedPosition);
                        //更新数据
                        gridViewTagAdapter.notifyDataSetChanged();
                        if(tags.size() == 0){
                            tags.clear();
                            tags.put("全部","全部");
                            //除了全部，其他的换背景
                            selectedPosition.clear();
                            selectedPosition.add(0);
                            //改变选中位置
                            gridViewTagAdapter.clearSelection(selectedPosition);
                            //更新数据
                            gridViewTagAdapter.notifyDataSetChanged();
                        }
                    }
                }
                //改变标签，数据库中查数据
                findSubjectMsgByIdCondition(subject,tags,types,timeYears);
                Log.i("www", "onItemClick: 标签"+tags.toString());
            }

        });
        //添加题目到选题蓝子
        customAdapterToAddCartYLX.setOnItemAddClickListener(new CustomAdapterToAddCartYLX.onItemAddListener() {
            @Override
            public void onAddClick(int number, int position) {
                Log.i("www", "onAddClick: 篮子中题目数量"+number);
                Log.i("www", "onAddClick: 篮子中题目位置"+position);
                lanSubCount.setText(subInCart.size()+1+"");
                customAdapterToAddCartYLX.notifyDataSetChanged();
                subInCart.add(subjectMsgs.get(position));
            }
        });
        cuotilanRL.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.showll_ylx:
                chooseTop.setVisibility(View.VISIBLE);
                showll.setVisibility(View.GONE);
                break;
            case R.id.shouQi_ylx:
                chooseTop.setVisibility(View.GONE);
                showll.setVisibility(View.VISIBLE);
                break;
            case R.id.btn_math_ylx://点击数学
                chooseSubject("数学");
                break;
            case R.id.btn_chinese_ylx://点击语文
                chooseSubject("语文");
                break;
            case R.id.btn_english_ylx://点击英语
                chooseSubject("英语");
                break;
            case R.id.btn_type_all_ylx://点击全部题型
                //其他题型全部是未选中样式
                choooseType("全部");
                break;
            case R.id.btn_typeX_ylx://点击选择题
                choooseType("选择题");
                break;
            case R.id.btn_typeT_ylx://点击填空题
                choooseType("填空题");
                break;
            case R.id.btn_typeD_ylx://点击大题
                choooseType("大题");
                break;
            case R.id.btn_time_all_ylx://点击全部
                chooseTime("全部");
                break;
            case R.id.btn_timeOne_ylx://点击2020
                chooseTime("2020");
                break;
            case R.id.btn_timeTwo_ylx://点击2019
                chooseTime("2019");
                break;
            case R.id.btn_timeMore_ylx://点击更早
                chooseTime("更早");
                break;
            case R.id.zuJuanLan_ylx://点击篮子
                Intent intent = new Intent(this, WrongBasketActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subInCart", (Serializable)subInCart);
                intent.putExtras(bundle);
                intent.putExtra("titleName",titleName);
                startActivity(intent);
                break;
            case R.id.imgBack_ylx:
                Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
                intent2.setAction("mistake");
                startActivity(intent2);
                finish();
                break;
        }
    }
    //选中学科
    public void chooseSubject(String subject){
        if (subject.equals("数学")){//选中数学
            this.subject = "数学";
            //改变背景
            btnMath.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnChinese.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnEnglish.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnMath.setTextColor(Color.WHITE);
            btnChinese.setTextColor(Color.BLACK);
            btnEnglish.setTextColor(Color.BLACK);
            //改变标签
            updateGridView(tagsMaths);
            //从数据库查数据（改变数据和符合条件的题目）
        }else if(subject.equals("语文")){//选中语文
            this.subject = "语文";
            //改变背景
            btnMath.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnChinese.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnEnglish.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnChinese.setTextColor(Color.WHITE);
            btnMath.setTextColor(Color.BLACK);
            btnEnglish.setTextColor(Color.BLACK);
            //改变标签
            updateGridView(tagsChinese);
            //从数据库查数据（改变数据和符合条件的题目）
        }else {//选中英语
            this.subject = "英语";
            //改变背景
            btnMath.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnChinese.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnEnglish.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnEnglish.setTextColor(Color.WHITE);
            btnMath.setTextColor(Color.BLACK);
            btnChinese.setTextColor(Color.BLACK);
            //改变标签
            updateGridView(tagsEnglish);
            //从数据库查数据（改变数据和符合条件的题目）
        }
        //改变学科，从数据库中查询
        findSubjectMsgByIdCondition(subject,tags,types,timeYears);
    }
    public void updateGridView(List<String> tags){
        tagMEC.clear();
        for (String tag:tags){
            tagMEC.add(tag);
        }
        gridViewTagAdapter.notifyDataSetChanged();
    }
    //选中题型(如果题型选择全部，其他的题型就不可选，如果不是全部可多选)
    public void choooseType(String type){
        if(type.equals("全部")){
            types.clear();
            types.put("全部","全部");
            btnTypeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnTypeX.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTypeD.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTypeT.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTypeAll.setTextColor(Color.WHITE);
            btnTypeX.setTextColor(Color.BLACK);
            btnTypeD.setTextColor(Color.BLACK);
            btnTypeT.setTextColor(Color.BLACK);

        }else if(type.equals("选择题")){
            if(types.containsKey("全部")){
                types.remove("全部");
                btnTypeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                btnTypeAll.setTextColor(Color.BLACK);
            }
            if(!types.containsKey("选择题")){
                types.put("选择题","选择题");
                btnTypeX.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
                btnTypeX.setTextColor(Color.WHITE);
            }else {
                types.remove("选择题");
                btnTypeX.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                btnTypeX.setTextColor(Color.BLACK);
            }
        }else if(type.equals("填空题")){
            if(types.containsKey("全部")){
                types.remove("全部");
                btnTypeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                btnTypeAll.setTextColor(Color.BLACK);
            }
            if(!types.containsKey("填空题")){
                types.put("填空题","填空题");
                btnTypeT.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
                btnTypeT.setTextColor(Color.WHITE);
            }else {
                types.remove("填空题");
                btnTypeT.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                btnTypeT.setTextColor(Color.BLACK);
            }
        }else {//大题
            if(types.containsKey("全部")){
                types.remove("全部");
                btnTypeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                btnTypeAll.setTextColor(Color.BLACK);
            }
            if(!types.containsKey("大题")){
                types.put("大题","大题");
                btnTypeD.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
                btnTypeD.setTextColor(Color.WHITE);
            }else {
                types.remove("大题");
                btnTypeD.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
                btnTypeD.setTextColor(Color.BLACK);
            }
        }
        if(types.size()==0){
            types.clear();
            types.put("全部","全部");
            btnTypeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnTypeX.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTypeD.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTypeT.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTypeAll.setTextColor(Color.WHITE);
            btnTypeX.setTextColor(Color.BLACK);
            btnTypeD.setTextColor(Color.BLACK);
            btnTypeT.setTextColor(Color.BLACK);
        }
        //改变题型，从数据库中查询
        findSubjectMsgByIdCondition(subject,tags,types,timeYears);
    }
    //选中年份()
    public void chooseTime(String time){
        if(time.equals("全部")){
            btnTimeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnTimeOne.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeTwo.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeMore.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeAll.setTextColor(Color.WHITE);
            btnTimeOne.setTextColor(Color.BLACK);
            btnTimeTwo.setTextColor(Color.BLACK);
            btnTimeMore.setTextColor(Color.BLACK);
            timeYears="全部";

        }else if(time.equals("2020")){
            btnTimeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeOne.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnTimeTwo.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeMore.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeAll.setTextColor(Color.BLACK);
            btnTimeOne.setTextColor(Color.WHITE);
            btnTimeTwo.setTextColor(Color.BLACK);
            btnTimeMore.setTextColor(Color.BLACK);
            timeYears="2020";
        }else if(time.equals("2019")){
            btnTimeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeOne.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeTwo.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnTimeMore.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeAll.setTextColor(Color.BLACK);
            btnTimeOne.setTextColor(Color.BLACK);
            btnTimeTwo.setTextColor(Color.WHITE);
            btnTimeMore.setTextColor(Color.BLACK);
            timeYears="2019";
        }else {//更早
            btnTimeAll.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeOne.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeTwo.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_no_layput));
            btnTimeMore.setBackground(getResources().getDrawable(R.drawable.ylx_check_ifinfo_ok_layput));
            btnTimeAll.setTextColor(Color.BLACK);
            btnTimeOne.setTextColor(Color.BLACK);
            btnTimeTwo.setTextColor(Color.BLACK);
            btnTimeMore.setTextColor(Color.WHITE);
            timeYears="更早";
        }
        //改变年份，从数据库中查询
        findSubjectMsgByIdCondition(subject,tags,types,timeYears);
    }
    //去数据库查找题目
    private void findSubjectMsgByIdCondition (String subject,Map<String,String> tags,Map<String,String> types,String date){
        okHttpClient = new OkHttpClient();
        String tagsMap = new Gson().toJson(tags);
        String typesMap = new Gson().toJson(types);
        Log.i("www", "findSubjectMsgByIdCondition: 筛选的条件"+subject+"-----"+tagsMap+"----"+typesMap+"-----"+date);
        FormBody formBody = new FormBody.Builder()
                .add("subject", subject)
                .add("tags", tagsMap)
                .add("date",date)
                .add("types",typesMap)
                .add("uId", String.valueOf(uId))
                .build();
        String url = "http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/subjectMsg/findSubForPaper";
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
                        Type type = new TypeToken<List<SubjectMsg>>() {
                        }.getType();
                        //反序列化
                        lists = new ArrayList<>();
                        lists = gson.fromJson(response.body().string(), type);
                        Message msg = Message.obtain();
                        if (lists.size() != 0) {
                            msg.obj = "刷新";
                        } else {
                            msg.obj = "无数据";
                        }
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }
}
