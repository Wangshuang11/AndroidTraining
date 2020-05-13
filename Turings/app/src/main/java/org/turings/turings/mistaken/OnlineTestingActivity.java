package org.turings.turings.mistaken;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import org.turings.turings.R;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomAdapterTestContentYLX;
import org.turings.turings.mistaken.customAdapterAndDialog.GridViewAnswerSheetAdapter;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class OnlineTestingActivity extends AppCompatActivity implements GestureDetector.OnGestureListener, View.OnClickListener, Chronometer.OnChronometerTickListener {

    ViewFlipper mViewFlipper;
    //1.定义手势检测器对象
    GestureDetector mGestureDetector;
    //2.定义一个动画数组，用于为ViewFilpper指定切换动画效果。
    Animation[] animations = new Animation[4];
    //3.定义手势两点之间的最小距离
    private static int FLIP_DISTANCE = 100 ;
    private List<SubjectMsg> subjectMsgs = new ArrayList<>();
    //题目数量
    private TextView tvCount;
    //上一题
    private LinearLayout preMsg;
    //下一题
    private LinearLayout nextMsg;
    //做题计时
    private Chronometer chronometer;
    private int miss=0;
    private boolean isChronometerStart = false;
    private int mRangeTime;
    //交卷
    private LinearLayout finishBtn;
    //题型
    private TextView tvQType;
    //答题卡
    private LinearLayout carBtn;
    //大题或选择题答题框
    private LinearLayout writeLayoutYlx;
    private EditText etWriteAnswer;
    //记录答题情况(题号，答题状态)
    private Map<Integer,Integer> result = new HashMap<>();
    //记录选择结果
    private Map<Integer,Object> choosesStated = new HashMap<>();
    //答题正误状态
    private static final int UNANWSERED_QUESTIONS=0;//未答题
    private static final int ANWSERED_BINGO = 1;//答对
    private static final int ANWSERED_WRONG= 2;//答错
    private static String str;//答题或者填空题的答案
    //卷子名称
    private String titleName;
    //返回箭头（放弃此次测试）
    private ImageView ivBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_testing);
        getViews();
        register();
        chronometer.start();
        //1.构建手势检测器
        mGestureDetector = new GestureDetector(OnlineTestingActivity.this, (GestureDetector.OnGestureListener) this);
        //2准备数据
        List<SubjectMsg> subjectMsgsList = initData();
        subjectMsgs.addAll(subjectMsgsList);
        //3.为ViewFilpper添加子控件。
        for (int i = 0;i<subjectMsgs.size();i++){
            SubjectMsg subjectMsg = subjectMsgs.get(i);
            mViewFlipper.addView(addQuestionView(subjectMsg,i));
        }
        //4.初始化Animation数组
        animations[0] = AnimationUtils.loadAnimation(this, R.anim.left_in_ylx);
        animations[1] = AnimationUtils.loadAnimation(this, R.anim.left_out_ylx);
        animations[2] = AnimationUtils.loadAnimation(this, R.anim.right_in_ylx);
        animations[3] = AnimationUtils.loadAnimation(this, R.anim.right_out_ylx);
        //设置数量
        tvCount.setText(1+"/"+subjectMsgs.size());
        fixQType(subjectMsgs.get(0));
    }

    //获取控件
    private void getViews() {
        mViewFlipper = findViewById(R.id.viewFlipper);
        tvCount = findViewById(R.id.count_ylx);
        preMsg = findViewById(R.id.practise_btnPrevious);
        nextMsg = findViewById(R.id.practise_btnNext);
        chronometer = findViewById(R.id.practise_tvTestTime);
        finishBtn = findViewById(R.id.practise_btnSubmit);
        tvQType = findViewById(R.id.practise_tvQType);
        carBtn = findViewById(R.id.practise_btnNumMenu);
        ivBack = findViewById(R.id.practise_back);
    }
    //绑定事件
    private void register() {
        preMsg.setOnClickListener(this);
        nextMsg.setOnClickListener(this);
        chronometer.setOnChronometerTickListener(this);
        finishBtn.setOnClickListener(this);
        carBtn.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }
    public List<SubjectMsg> initData() {
        //正常是其他页面传来的
        List<SubjectMsg> lists = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lists = (List<SubjectMsg>) bundle.getSerializable("subInCart");
        Log.i("www", "onCreate: "+lists.toString());
        titleName=intent.getStringExtra("title");
        return lists;
    }

    private View addQuestionView(SubjectMsg subjectMsg, int pos){
        View view = View.inflate(this, R.layout.ylx_item_test_content_layout, null);
        TextView textView = view.findViewById(R.id.tv_num);
        TextView tes = view.findViewById(R.id.tv_question);
        ListView listview = view.findViewById(R.id.lv_question_answer);
        writeLayoutYlx = view.findViewById(R.id.write_layout_ylx);
        EditText etWriteAnswer = view.findViewById(R.id.write_ylx);
        Button btn = view.findViewById(R.id.commit_btn);
        textView.setText("题号"+(pos+1)+"");
        listview.addHeaderView(new View(this));
        listview.addFooterView(new View(this));
        CustomAdapterTestContentYLX adapter = new CustomAdapterTestContentYLX(subjectMsg,getApplicationContext(), R.layout.ylx_test_content_anwser_layout);
        listview.setAdapter(adapter);
        listview.setDivider(null);
        tes.setText(subjectMsg.getContent());
        fixQType(listview,subjectMsg);
        result.put(pos,UNANWSERED_QUESTIONS);
        choosesStated.put(pos,UNANWSERED_QUESTIONS);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("www", "onClick: 提交填空题"+etWriteAnswer.getText().toString());
                result.remove(mViewFlipper.getDisplayedChild());
                choosesStated.remove(mViewFlipper.getDisplayedChild());
                Log.i("www", "onFling: "+etWriteAnswer.getText().toString().trim());
                if(etWriteAnswer.getText().toString().trim().length()==0){//没有内容
                    result.put(mViewFlipper.getDisplayedChild(),UNANWSERED_QUESTIONS);
                    choosesStated.put(mViewFlipper.getDisplayedChild(),UNANWSERED_QUESTIONS);
                } else {//写答案了
                    Log.i("www", "onFling: 添加答案"+etWriteAnswer.getText().toString().trim());
                    if(etWriteAnswer.getText().toString().equals(subjectMsgs.get(mViewFlipper.getDisplayedChild()).getAnswer())){
                        result.put(mViewFlipper.getDisplayedChild(),ANWSERED_BINGO );
                    }else {
                        result.put(mViewFlipper.getDisplayedChild(),ANWSERED_WRONG);
                    }
                    choosesStated.put(mViewFlipper.getDisplayedChild(),etWriteAnswer.getText().toString());
                }
                if (mViewFlipper.getDisplayedChild() == subjectMsgs.size() - 1) {
                    Toast.makeText(OnlineTestingActivity.this,"最后一个题", Toast.LENGTH_SHORT).show();
                    mViewFlipper.stopFlipping();
                    return;
                }else {
                    mViewFlipper.setInAnimation(animations[2]);
                    mViewFlipper.setOutAnimation(animations[3]);
                    mViewFlipper.showNext();
                }
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String ans = "";
                Toast.makeText(OnlineTestingActivity.this,position + "", Toast.LENGTH_SHORT).show();
                switch (position){
                    case 1:
                        ans = "A";
                        break;
                    case 2:
                        ans = "B";
                        break;
                    case 3:
                        ans = "C";
                        break;
                    case 4:
                        ans = "D";
                        break;
                }
                //点击事件
                if(result.get(mViewFlipper.getDisplayedChild()) == UNANWSERED_QUESTIONS){//未选择过
                    if(ans.equals(subjectMsgs.get(mViewFlipper.getDisplayedChild()).getAnswer())){
                        result.put(mViewFlipper.getDisplayedChild(),ANWSERED_BINGO );
                    }else {
                        result.put(mViewFlipper.getDisplayedChild(),ANWSERED_WRONG);
                    }
                    choosesStated.put(mViewFlipper.getDisplayedChild(),position);
                    adapter.clearSelection(subjectMsgs.get(mViewFlipper.getDisplayedChild()).getId(),position);
                    adapter.notifyDataSetChanged();
                }else {//此题做过
                    result.remove(mViewFlipper.getDisplayedChild());
                    choosesStated.remove(mViewFlipper.getDisplayedChild());
                    if(ans.equals(subjectMsgs.get(mViewFlipper.getDisplayedChild()).getAnswer())){
                        result.put(mViewFlipper.getDisplayedChild(),ANWSERED_BINGO );
                    }else {
                        result.put(mViewFlipper.getDisplayedChild(),ANWSERED_WRONG);
                    }
                    choosesStated.put(mViewFlipper.getDisplayedChild(),position);
                    adapter.clearSelection(subjectMsgs.get(mViewFlipper.getDisplayedChild()).getId(),position);
                    adapter.notifyDataSetChanged();
                }
                if (mViewFlipper.getDisplayedChild() == subjectMsgs.size() - 1) {
                    Toast.makeText(OnlineTestingActivity.this,"最后一个题", Toast.LENGTH_SHORT).show();
                    mViewFlipper.stopFlipping();
                    return;
                }else {
                    mViewFlipper.setInAnimation(animations[2]);
                    mViewFlipper.setOutAnimation(animations[3]);
                    mViewFlipper.showNext();
                }
            }
        });
        return view;
    }
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Toast.makeText(OnlineTestingActivity.this,"按下",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //将Activity上的触发的事件交个GestureDetector处理
        return mGestureDetector.onTouchEvent(event);
    }
    //重点实现在这里切换
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() < FLIP_DISTANCE){
            if (mViewFlipper.getDisplayedChild() == 0) {
                mViewFlipper.stopFlipping();
                Toast.makeText(getApplicationContext(),"第一个题",Toast.LENGTH_SHORT).show();
                tvCount.setText(1+"/"+subjectMsgs.size());
                fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                return false;
            } else {
                mViewFlipper.setInAnimation(animations[0]);
                mViewFlipper.setOutAnimation(animations[1]);
                mViewFlipper.showPrevious();
                tvCount.setText((mViewFlipper.getDisplayedChild()+1)+"/"+subjectMsgs.size());
                fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                return true;
            }
        }else if (e1.getX() - e2.getX() > FLIP_DISTANCE){
            if (mViewFlipper.getDisplayedChild() == subjectMsgs.size() - 1) {
                Toast.makeText(OnlineTestingActivity.this,"最后一个题",Toast.LENGTH_SHORT).show();
                mViewFlipper.stopFlipping();
                tvCount.setText(subjectMsgs.size()+"/"+subjectMsgs.size());
                fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                return false;
            }else {
                mViewFlipper.setInAnimation(animations[2]);
                mViewFlipper.setOutAnimation(animations[3]);
                mViewFlipper.showNext();
                tvCount.setText((mViewFlipper.getDisplayedChild()+1)+"/"+subjectMsgs.size());
                fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.practise_btnNext:
                if (mViewFlipper.getDisplayedChild() == subjectMsgs.size() - 1) {
                    Toast.makeText(OnlineTestingActivity.this,"最后一个题",Toast.LENGTH_SHORT).show();
                    mViewFlipper.stopFlipping();
                    tvCount.setText(subjectMsgs.size()+"/"+subjectMsgs.size());
                    fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                }else {
                    mViewFlipper.setInAnimation(animations[2]);
                    mViewFlipper.setOutAnimation(animations[3]);
                    mViewFlipper.showNext();
                    tvCount.setText((mViewFlipper.getDisplayedChild()+1)+"/"+subjectMsgs.size());
                    fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                }
                break;
            case R.id.practise_btnPrevious://上一题
                if (mViewFlipper.getDisplayedChild() == 0) {
                    mViewFlipper.stopFlipping();
                    Toast.makeText(getApplicationContext(),"第一个题",Toast.LENGTH_SHORT).show();
                    tvCount.setText(1+"/"+subjectMsgs.size());
                    fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                } else {
                    mViewFlipper.setInAnimation(animations[0]);
                    mViewFlipper.setOutAnimation(animations[1]);
                    mViewFlipper.showPrevious();
                    tvCount.setText((mViewFlipper.getDisplayedChild()+1)+"/"+subjectMsgs.size());
                    fixQType(subjectMsgs.get(mViewFlipper.getDisplayedChild()));
                }
                break;
            case R.id.practise_btnSubmit://交卷
                this.chronometer.stop();
                isChronometerStart = true;
                mRangeTime = miss;
                showDialog();
                break;
            case R.id.practise_btnNumMenu://答题卡
                showAnswerSheetDialog();
                break;
            case R.id.practise_back://放弃此次测试
                //返回到组卷车
                //弹出框询问确定放弃此次答题吗
                this.chronometer.stop();
                isChronometerStart = true;
                mRangeTime = miss;
                AlertDialog.Builder builder  = new AlertDialog.Builder(OnlineTestingActivity.this);
                builder.setTitle("提示" ) ;
                builder.setMessage("您确定放弃此次测试吗？" ) ;
                builder.setPositiveButton("确定",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(OnlineTestingActivity.this, AutoGeneratingPaperYLXActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        chronometer.start();
                    }
                });
                builder.show();
                break;
        }
    }


    //答题计时
    @Override
    public void onChronometerTick(Chronometer chronometer) {
        if(isChronometerStart){
            miss = mRangeTime;
            chronometer.setText(FormatMiss(miss));
            isChronometerStart = false;
        }else {
            miss++;
            chronometer.setText(FormatMiss(miss));
        }
    }
    public static String FormatMiss(int miss){
        String hh=miss/3600>9?miss/3600+"":"0"+miss/3600;
        String  mm=(miss % 3600)/60>9?(miss % 3600)/60+"":"0"+(miss % 3600)/60;
        String ss=(miss % 3600) % 60>9?(miss % 3600) % 60+"":"0"+(miss % 3600) % 60;
        return hh+":"+mm+":"+ss;
    }
    //判断题型
    public void fixQType(ListView listview, SubjectMsg subjectMsg){
        switch (subjectMsg.getType()){
            case "选择题":
                tvQType.setText("选择题");
                writeLayoutYlx.setVisibility(View.GONE);
                listview.setVisibility(View.VISIBLE);
                break;
            case "填空题":
                tvQType.setText("填空题");
                writeLayoutYlx.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                break;
            case "大题":
                tvQType.setText("大题");
                writeLayoutYlx.setVisibility(View.VISIBLE);
                listview.setVisibility(View.GONE);
                break;
        }
    }
    public void fixQType(SubjectMsg subjectMsg){
        switch (subjectMsg.getType()){
            case "选择题":
                tvQType.setText("选择题");
                break;
            case "填空题":
                tvQType.setText("填空题");
                break;
            case "大题":
                tvQType.setText("大题");
                break;
        }
    }
    //交卷后的弹出框
    //初始化并弹出对话框方法
    private void showDialog(){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ylx_hand_exams_layout,null,false);
        AlertDialog dialog = new AlertDialog.Builder(OnlineTestingActivity.this).setView(view).create();
        Button btnOk = view.findViewById(R.id.button_ok_ylx);
        Button btnNo = view.findViewById(R.id.button_no_ylx);
        TextView tvTitle = view.findViewById(R.id.tv_message_ylx);
        Iterator<Map.Entry<Integer,Integer>> it=result.entrySet().iterator();
        int n = 0;//未做题数
        List<Integer> titleNum = new ArrayList<>();//未做的题目的题号
        while(it.hasNext()){
            Map.Entry<Integer,Integer> entry=it.next();
            if(entry.getValue() == UNANWSERED_QUESTIONS){
                n = n+1;
                titleNum.add(entry.getKey()+1);
            }
        }
        Collections.sort(titleNum);
        if(n==0){
            tvTitle.setText("你有0道题未完成，确定提交吗？");
        }else {
            String str = "你有"+n+"道题未完成，题号分别是";
            for (int i=0;i<titleNum.size();i++){
                if (i == titleNum.size()-1){
                    str=str+titleNum.get(i);
                }else {
                    str= str+titleNum.get(i)+"、";
                }
            }
            str = str+",确定要提交吗？填空题和大题请注意提交";
            tvTitle.setText(str);
        }
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        // WindowManager 接口的嵌套类
        WindowManager.LayoutParams windowParams = window.getAttributes();
        //设置弹出框周围的透明度
        windowParams.dimAmount = 0.5f;
        //设置弹出框内容的透明度
        windowParams.alpha = 1f;
        //设置弹出框距离上面的距离
        windowParams.y = 100;
        window.setBackgroundDrawableResource(R.drawable.dialog_stroke_layout_ylx);
        windowParams.width = 800;// 调整该值可以设置对话框显示的宽度
        window.setAttributes(windowParams);
        //DisplayMetrics类 获取手机显示屏的基本信息 包括尺寸、密度、字体缩放等信息
        DisplayMetrics dm = new DisplayMetrics();
        //将屏幕尺寸赋给dm
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("www", "onClick: 确定提交");
                //跳到交卷界面（应该包含做题情况）
                Intent intent = new Intent(OnlineTestingActivity.this,TestResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subInCart", (Serializable)subjectMsgs);
                bundle.putSerializable("result", (Serializable) result);
                bundle.putSerializable("chooseMap", (Serializable) choosesStated);
                intent.putExtras(bundle);
                intent.putExtra("title",titleName);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //... To-do
                Log.i("www", "onClick: 取消返回做题");
                //时间继续
                chronometer.start();
                dialog.dismiss();
            }
        });
    }
    //答题卡弹出框
    private void showAnswerSheetDialog() {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ylx_answer_sheet_ylx,null,false);
        AlertDialog dialog2 = new AlertDialog.Builder(OnlineTestingActivity.this).setView(view).create();
        //关闭答题卡按钮
        Button btn = view.findViewById(R.id.close_answer_sheet);
        //选择题区域
        LinearLayout linearLayoutX = view.findViewById(R.id.x_layout);
        TextView tvX = view.findViewById(R.id.tv_x);
        GridView gvX = view.findViewById(R.id.x_gv);
        //填空题区域
        LinearLayout linearLayoutT = view.findViewById(R.id.t_layout);
        TextView tvT = view.findViewById(R.id.tv_t);
        GridView gvT = view.findViewById(R.id.t_gv);
        //大题区域
        LinearLayout linearLayoutD = view.findViewById(R.id.d_layout);
        TextView tvD = view.findViewById(R.id.tv_d);
        GridView gvD = view.findViewById(R.id.d_gv);
        //先判断题型(把各个题型题号分出来)
        //选择题
        List<Integer> listX = new ArrayList<>();
        //填空题
        List<Integer> listT = new ArrayList<>();
        //大题
        List<Integer> listD = new ArrayList<>();
        for(int i=0;i<subjectMsgs.size();i++){
            if(subjectMsgs.get(i).getType().equals("选择题")){
                listX.add(i);
            }else if(subjectMsgs.get(i).getType().equals("填空题")){
                listT.add(i);
            }else {
                listD.add(i);
            }
        }
        //选择题情况
        if(listX.size() == 0){
            linearLayoutX.setVisibility(View.GONE);
        }else {
            tvX.setText("一、选择题(共"+listX.size()+"题)");
        }
        //填空题情况
        if(listT.size() == 0){
            linearLayoutT.setVisibility(View.GONE);
        }else {
            if(listX.size() == 0){
                tvT.setText("一、填空题(共"+listT.size()+"题)");
            }else {
                tvT.setText("二、填空题(共"+listT.size()+"题)");
            }
        }
        //大题情况
        if(listD.size() == 0){
            linearLayoutD.setVisibility(View.GONE);
        }else {
            if(listX.size() == 0 && listT.size()==0){
                tvD.setText("一、大题(共"+listD.size()+"题)");
            }else if(listX.size() == 0 || listT.size()==0){
                tvD.setText("二、大题(共"+listD.size()+"题)");
            }else {
                tvD.setText("三、大题(共"+listD.size()+"题)");
            }
        }
        //三种题型答题情况（答题和未答题）
        //选择题
        Map<Integer,Integer> chooseMapX = new HashMap<>();
        //填空题
        Map<Integer,Integer> chooseMapT = new HashMap<>();
        //大题
        Map<Integer,Integer> chooseMapD = new HashMap<>();
        Iterator<Map.Entry<Integer,Integer>> it=result.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Integer,Integer> entry=it.next();
            if(listX.contains(entry.getKey())){
                chooseMapX.put(entry.getKey(),entry.getValue());
            }else if(listT.contains(entry.getKey())){
                chooseMapT.put(entry.getKey(),entry.getValue());
            }else {
                chooseMapD.put(entry.getKey(),entry.getValue());
            }
        }
        //绑定适配器
        GridViewAnswerSheetAdapter gridViewAnswerSheetAdapterX = new GridViewAnswerSheetAdapter(getApplicationContext(),listX, R.layout.ylx_item_answer_sheet_layout,chooseMapX);
        GridViewAnswerSheetAdapter gridViewAnswerSheetAdapterT = new GridViewAnswerSheetAdapter(getApplicationContext(),listT, R.layout.ylx_item_answer_sheet_layout,chooseMapT);
        GridViewAnswerSheetAdapter gridViewAnswerSheetAdapterD = new GridViewAnswerSheetAdapter(getApplicationContext(),listD, R.layout.ylx_item_answer_sheet_layout,chooseMapD);
        gvX.setAdapter(gridViewAnswerSheetAdapterX);
        gvT.setAdapter(gridViewAnswerSheetAdapterT);
        gvD.setAdapter(gridViewAnswerSheetAdapterD);
        /*弹出框样式设置*/
        dialog2.setCancelable(false);
        dialog2.show();

        Window window = dialog2.getWindow();
        dialog2.getWindow().setBackgroundDrawable(null);
        window.getDecorView().setPadding(0, 100, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.y=400;
        window.setAttributes(lp);
        /*点击事件*/
        //点击题号跳转到题目
        gvX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mViewFlipper.setDisplayedChild(listX.get(i));
                dialog2.dismiss();
            }
        });
        gvT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mViewFlipper.setDisplayedChild(listT.get(i));
                dialog2.dismiss();
            }
        });
        gvD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mViewFlipper.setDisplayedChild(listD.get(i));
                dialog2.dismiss();
            }
        });
        gridViewAnswerSheetAdapterX.setmOnItemClickListener(new GridViewAnswerSheetAdapter.onItemClickListener() {
            @Override
            public void onNumClick(int position) {
                tvQType.setText("选择题");
                mViewFlipper.setDisplayedChild(listX.get(position));
                dialog2.dismiss();
            }
        });
        gridViewAnswerSheetAdapterT.setmOnItemClickListener(new GridViewAnswerSheetAdapter.onItemClickListener() {
            @Override
            public void onNumClick(int position) {
                tvQType.setText("填空题");
                mViewFlipper.setDisplayedChild(listT.get(position));
                dialog2.dismiss();
            }
        });
        gridViewAnswerSheetAdapterD.setmOnItemClickListener(new GridViewAnswerSheetAdapter.onItemClickListener() {
            @Override
            public void onNumClick(int position) {
                tvQType.setText("大题");
                mViewFlipper.setDisplayedChild(listD.get(position));
                dialog2.dismiss();
            }
        });
        //关闭答题卡
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("www", "onClick: 关闭答题卡");
                dialog2.dismiss();
            }
        });
    }
}
