package org.turings.turings.mistaken;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomAdapterInResultYLX;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TestResultActivity extends AppCompatActivity implements View.OnClickListener {
    //返回箭头
    private ImageView imBack;
    //结果layout
    private LinearLayout resultLayout;
    //卷名
    private TextView tvTitleName;
    //答对题数
    private TextView tvYesNum;
    //答错题数
    private TextView tvNoNum;
    //选择题
    private LinearLayout xLayout;
    private TextView tvX;
    private ListView listViewX;
    //填空题
    private LinearLayout tLayout;
    private TextView tvT;
    private ListView listViewT;
    //大题
    private LinearLayout dLayout;
    private TextView tvD;
    private ListView listViewD;
    //三种题型的适配器
    private CustomAdapterInResultYLX customAdapterInResultYLXX;
    private CustomAdapterInResultYLX customAdapterInResultYLXD;
    private CustomAdapterInResultYLX customAdapterInResultYLXT;
    //数据
    private List<SubjectMsg> subjectMsgs = new ArrayList<>();
    //答题结果
    private Map<Integer,Integer> result = new HashMap<>();
    //个人做题的记录
    private Map<Integer,Object> choosesStated = new HashMap<>();
    //卷子名称
    private String titleName;
    //选择题
    private List<Integer> listX;
    private List<SubjectMsg> subjectMsgsX;
    //填空题
    private List<Integer> listT;
    private List<SubjectMsg> subjectMsgsT;
    //大题
    private List<Integer> listD ;
    private List<SubjectMsg> subjectMsgsD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_result);
        getViews();
        register();
        inits();
    }

    //初始化数据
    private void inits() {
        //接收在下答题传来的数据
        Intent intent = getIntent();
        subjectMsgs = (List<SubjectMsg>) intent.getSerializableExtra("subInCart");
        result = (Map<Integer, Integer>) intent.getSerializableExtra("result");
        titleName = intent.getStringExtra("title");
        choosesStated = (Map<Integer, Object>) intent.getSerializableExtra("chooseMap");
        //先判断题型(把各个题型题号分出来)
        //选择题
        listX = new ArrayList<>();
        subjectMsgsX = new ArrayList<>();
        //填空题
        listT = new ArrayList<>();
        subjectMsgsT = new ArrayList<>();
        //大题
        listD = new ArrayList<>();
        subjectMsgsD = new ArrayList<>();
        for(int i=0;i<subjectMsgs.size();i++){
            if(subjectMsgs.get(i).getType().equals("选择题")){
                listX.add(i);
                subjectMsgsX.add(subjectMsgs.get(i));
            }else if(subjectMsgs.get(i).getType().equals("填空题")){
                listT.add(i);
                subjectMsgsT.add(subjectMsgs.get(i));
            }else {
                listD.add(i);
                subjectMsgsD.add(subjectMsgs.get(i));
            }
        }
        //选择题情况
        if(listX.size() == 0){
            xLayout.setVisibility(View.GONE);
        }else {
            tvX.setText("一、选择题(共"+listX.size()+"题)");
        }
        //填空题情况
        if(listT.size() == 0){
            tLayout.setVisibility(View.GONE);
        }else {
            if(listX.size() == 0){
                tvT.setText("一、填空题(共"+listT.size()+"题)");
            }else {
                tvT.setText("二、填空题(共"+listT.size()+"题)");
            }
        }
        //大题情况
        if(listD.size() == 0){
            dLayout.setVisibility(View.GONE);
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
        //答对题数
        int yNum = 0;
        //答错题数
        int nNum = 0;
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
            if(entry.getValue() == 1){
                yNum = yNum+1;
            }else {
                nNum = nNum+1;
            }
        }
        //绑定适配器
        customAdapterInResultYLXX = new CustomAdapterInResultYLX(getApplicationContext(), R.layout.ylx_test_result_item_lauout,subjectMsgsX,chooseMapX,listX);
        customAdapterInResultYLXT = new CustomAdapterInResultYLX(getApplicationContext(), R.layout.ylx_test_result_item_lauout,subjectMsgsT,chooseMapT,listT);
        customAdapterInResultYLXD = new CustomAdapterInResultYLX(getApplicationContext(), R.layout.ylx_test_result_item_lauout,subjectMsgsD,chooseMapD,listD);
        listViewX.setAdapter(customAdapterInResultYLXX);
        listViewT.setAdapter(customAdapterInResultYLXT);
        listViewD.setAdapter(customAdapterInResultYLXD);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewX);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewT);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewD);
        //卷名设置
        tvTitleName.setText(titleName);
        tvYesNum.setText("您答对"+yNum+"道题目");
        tvNoNum.setText("您答错"+nNum+"道题目");
        customAdapterInResultYLXX.setmOnItemClickListener(new CustomAdapterInResultYLX.onItemClickListener() {
            @Override
            public void onNumClick(int position) {
                //展示题目弹出框
                showSubjectMsgDialog(position,subjectMsgsX,listX);
            }
        });
        customAdapterInResultYLXT.setmOnItemClickListener(new CustomAdapterInResultYLX.onItemClickListener() {
            @Override
            public void onNumClick(int position) {
                //展示题目弹出框
                showSubjectMsgDialog(position,subjectMsgsT,listT);
            }
        });
        customAdapterInResultYLXD.setmOnItemClickListener(new CustomAdapterInResultYLX.onItemClickListener() {
            @Override
            public void onNumClick(int position) {
                //展示题目弹出框
                showSubjectMsgDialog(position,subjectMsgsD,listD);
            }
        });
        listViewX.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击弹出题目
                showSubjectMsgDialog(i,subjectMsgsX,listX);
            }
        });
        listViewT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击弹出题目
                showSubjectMsgDialog(i,subjectMsgsT,listT);
            }
        });
        listViewD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //点击弹出题目
                showSubjectMsgDialog(i,subjectMsgsD,listD);
            }
        });
    }

    //展示题目详情
    private void showSubjectMsgDialog(int position, List<SubjectMsg> subjectMsg, List<Integer> list) {
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ylx_show_subject_details_layout,null,false);
        AlertDialog dialog2 = new AlertDialog.Builder(TestResultActivity.this).setView(view).create();
        dialog2.setCancelable(false);
        dialog2.show();
        ImageView imageView = view.findViewById(R.id.subjectImg_ylx);
        TextView textView = view.findViewById(R.id.subjectText_ylx);
        //个人答案
        TextView textView1 = view.findViewById(R.id.your_anwser);
        TextView textView2 = view.findViewById(R.id.answer_show_ylx);
        imageView.setVisibility(View.GONE);
        if(subjectMsg.get(position).getType().equals("选择题")){
            textView.setText(subjectMsg.get(position).getContent()+"\n"+subjectMsg.get(position).getOptionA()
                    +"\n"+subjectMsg.get(position).getOptionB()+"\n"
                    +subjectMsg.get(position).getOptionC()+"\n"
                    +subjectMsg.get(position).getOptionD());
            switch (result.get(list.get(position))){
                case 0:
                    textView1.setText("您此题没有选择答案");
                    break;
                case 1:
                    textView1.setText("A");
                    break;
                case 2:
                    textView1.setText("B");
                    break;
                case 3:
                    textView1.setText("C");
                    break;
                case 4:
                    textView1.setText("D");
                    break;
            }
        }else {
            textView.setText(subjectMsg.get(position).getContent());
            textView1.setText(choosesStated.get(list.get(position))+"");
        }
        textView2.setText(subjectMsg.get(position).getAnswer());
        //返回箭头
        Button btn = view.findViewById(R.id.btn);
        Window window = dialog2.getWindow();
        dialog2.getWindow().setBackgroundDrawable(null);
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog2.dismiss();
            }
        });
    }


    //获取控件
    private void getViews() {
        imBack = findViewById(R.id.imgBack_ylx);
        resultLayout = findViewById(R.id.result_layout);
        tvTitleName = findViewById(R.id.titleName_ylx);
        tvYesNum = findViewById(R.id.yes_num);
        tvNoNum = findViewById(R.id.no_num);
        xLayout = findViewById(R.id.x_layout);
        tvX = findViewById(R.id.tv_x);
        listViewX = findViewById(R.id.lvSubX_ylx);
        tLayout = findViewById(R.id.t_layout);
        tvT = findViewById(R.id.tv_T);
        listViewT = findViewById(R.id.lvSubT_ylx);
        dLayout = findViewById(R.id.d_layout);
        tvD = findViewById(R.id.tv_D);
        listViewD = findViewById(R.id.lvSubD_ylx);
    }

    //绑定监听器
    private void register() {
        imBack.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack_ylx:
                //正常应该跳转到卷子中心
                Intent intent = new Intent(TestResultActivity.this, AutoGeneratingPaperYLXActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

}
