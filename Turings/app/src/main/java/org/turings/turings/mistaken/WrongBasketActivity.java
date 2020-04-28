package org.turings.turings.mistaken;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.R;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomAdapterInCartYLX;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WrongBasketActivity extends AppCompatActivity implements View.OnClickListener {
    private List<SubjectMsg> lsData = new ArrayList<>();//选在篮子里的所有题目
    private List<SubjectMsg> lsDataX = new ArrayList<>();//选择题集合
    private List<SubjectMsg> lsDataT = new ArrayList<>();//填空题集合
    private List<SubjectMsg> lsDataD = new ArrayList<>();//大题集合
    //适配器
    private CustomAdapterInCartYLX customAdapterInCartYLXX;//选择题的适配器
    private CustomAdapterInCartYLX customAdapterInCartYLXT;//填空题的适配器
    private CustomAdapterInCartYLX customAdapterInCartYLXD;//大题的适配器
    //listView
    private ListView listViewX;//选择
    private ListView listViewT;//填空
    private ListView listViewD;//大题
    //控件
    private TextView txX;
    private TextView txT;
    private TextView txD;
    private ImageView back;//返回箭头
    private TextView tvClear;//清空
    private ImageView ivTitle;//组卷标题
    private TextView titleName;//卷名
    private Button btnFinish;//完成组卷
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ylx_activity_wrong_basket);
        getViews();
        inits();
        rigister();
    }

    //初始化数据
    private void inits() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lsData = (List<SubjectMsg>) bundle.getSerializable("subInCart");
        Log.i("www", "onCreate: "+lsData.toString());
        titleName.setText(intent.getStringExtra("titleName"));
        for(SubjectMsg subjectMsg:lsData){
            if(subjectMsg.getType().equals("选择题")){
                lsDataX.add(subjectMsg);
            }else if(subjectMsg.getType().equals("填空题")){
                lsDataT.add(subjectMsg);
            }else {
                lsDataD.add(subjectMsg);
            }
        }
        txX.setText("一、选择题(共"+lsDataX.size()+"题)");
        txT.setText("二、填空题(共"+lsDataT.size()+"题)");
        txD.setText("三、大题(共"+lsDataD.size()+"题)");
        //注册适配器绑定数据源
        customAdapterInCartYLXX = new CustomAdapterInCartYLX(lsDataX,getApplicationContext(), R.layout.ylx_subject_list_item_cart_layout);
        customAdapterInCartYLXT = new CustomAdapterInCartYLX(lsDataT,getApplicationContext(), R.layout.ylx_subject_list_item_cart_layout);
        customAdapterInCartYLXD = new CustomAdapterInCartYLX(lsDataD,getApplicationContext(), R.layout.ylx_subject_list_item_cart_layout);
        listViewX.setAdapter(customAdapterInCartYLXX);
        listViewT.setAdapter(customAdapterInCartYLXT);
        listViewD.setAdapter(customAdapterInCartYLXD);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewX);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewT);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewD);

    }
    //注册监听器
    private void rigister() {
        back.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        ivTitle.setOnClickListener(this);

        //删除已选题目
        customAdapterInCartYLXX.setmOnItemDeleteClickListener(new CustomAdapterInCartYLX.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int position) {
                SubjectMsg subjectMsg = lsDataX.get(position);
                lsData.remove(subjectMsg);
                lsDataX.remove(position);
                customAdapterInCartYLXX.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(listViewX);
                txX.setText("一、选择题(共"+lsDataX.size()+"题)");
            }
        });
        customAdapterInCartYLXT.setmOnItemDeleteClickListener(new CustomAdapterInCartYLX.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int position) {
                SubjectMsg subjectMsg = lsDataT.get(position);
                lsData.remove(subjectMsg);
                lsDataT.remove(position);
                customAdapterInCartYLXT.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(listViewT);
                txT.setText("二、填空题(共"+lsDataT.size()+"题)");
            }
        });
        customAdapterInCartYLXD.setmOnItemDeleteClickListener(new CustomAdapterInCartYLX.onItemDeleteListener() {
            @Override
            public void onDeleteClick(int position) {
                SubjectMsg subjectMsg = lsDataD.get(position);
                lsData.remove(subjectMsg);
                lsDataD.remove(position);
                customAdapterInCartYLXD.notifyDataSetChanged();
                ListViewUtil.setListViewHeightBasedOnChildren(listViewD);
                txD.setText("三、大题(共"+lsDataD.size()+"题)");
            }
        });
        //上移题目
        customAdapterInCartYLXX.setmOnItemTopMoveClickListener(new CustomAdapterInCartYLX.onItemTopMoveListener() {
            @Override
            public void onTopMoveClick(int position) {
                if(position==0){
                    Toast.makeText(getApplicationContext(),"已经在第一位不能在向上移动了",Toast.LENGTH_SHORT).show();
                }else {
                    SubjectMsg subjectMsg = lsDataX.get(position-1);
                    lsDataX.set(position-1,lsDataX.get(position));
                    lsDataX.set(position,subjectMsg);
                    customAdapterInCartYLXX.notifyDataSetChanged();
                }
            }
        });
        customAdapterInCartYLXT.setmOnItemTopMoveClickListener(new CustomAdapterInCartYLX.onItemTopMoveListener() {
            @Override
            public void onTopMoveClick(int position) {
                if(position==0){
                    Toast.makeText(getApplicationContext(),"已经在第一位不能在向上移动了",Toast.LENGTH_SHORT).show();
                }else {
                    SubjectMsg subjectMsg = lsDataT.get(position-1);
                    lsDataT.set(position-1,lsDataT.get(position));
                    lsDataT.set(position,subjectMsg);
                    customAdapterInCartYLXT.notifyDataSetChanged();
                }
            }
        });
        customAdapterInCartYLXD.setmOnItemTopMoveClickListener(new CustomAdapterInCartYLX.onItemTopMoveListener() {
            @Override
            public void onTopMoveClick(int position) {
                if(position==0){
                    Toast.makeText(getApplicationContext(),"已经在第一位不能在向上移动了",Toast.LENGTH_SHORT).show();
                }else {
                    SubjectMsg subjectMsg = lsDataD.get(position-1);
                    lsDataD.set(position-1,lsDataD.get(position));
                    lsDataD.set(position,subjectMsg);
                    customAdapterInCartYLXD.notifyDataSetChanged();
                }
            }
        });
        //下移题目
        customAdapterInCartYLXX.setmOnItemDownMoveClickListener(new CustomAdapterInCartYLX.onItemDownMoveListener() {
            @Override
            public void onDownMoveClick(int position) {
                if(position==(lsDataX.size()-1)){
                    Toast.makeText(getApplicationContext(),"已经在最后不能在向下移动了",Toast.LENGTH_SHORT).show();
                }else {
                    SubjectMsg subjectMsg = lsDataX.get(position+1);
                    lsDataX.set(position+1,lsDataX.get(position));
                    lsDataX.set(position,subjectMsg);
                    customAdapterInCartYLXX.notifyDataSetChanged();
                }
            }
        });
        customAdapterInCartYLXT.setmOnItemDownMoveClickListener(new CustomAdapterInCartYLX.onItemDownMoveListener() {
            @Override
            public void onDownMoveClick(int position) {
                if(position==(lsDataT.size()-1)){
                    Toast.makeText(getApplicationContext(),"已经在最后不能在向下移动了",Toast.LENGTH_SHORT).show();
                }else {
                    SubjectMsg subjectMsg = lsDataT.get(position+1);
                    lsDataT.set(position+1,lsDataT.get(position));
                    lsDataT.set(position,subjectMsg);
                    customAdapterInCartYLXT.notifyDataSetChanged();
                }
            }
        });
        customAdapterInCartYLXD.setmOnItemDownMoveClickListener(new CustomAdapterInCartYLX.onItemDownMoveListener() {
            @Override
            public void onDownMoveClick(int position) {
                if(position==(lsDataD.size()-1)){
                    Toast.makeText(getApplicationContext(),"已经在最后不能在向下移动了",Toast.LENGTH_SHORT).show();
                }else {
                    SubjectMsg subjectMsg = lsDataD.get(position+1);
                    lsDataD.set(position+1,lsDataD.get(position));
                    lsDataD.set(position,subjectMsg);
                    customAdapterInCartYLXD.notifyDataSetChanged();
                }
            }
        });
    }

    //获取控件
    private void getViews() {
        txX = findViewById(R.id.tv_x);
        txD = findViewById(R.id.tv_D);
        txT = findViewById(R.id.tv_T);
        listViewX = findViewById(R.id.lvSubX_ylx);
        listViewT = findViewById(R.id.lvSubT_ylx);
        listViewD = findViewById(R.id.lvSubD_ylx);
        back = findViewById(R.id.imgBack_ylx);
        tvClear=findViewById(R.id.tv_clear);
        ivTitle=findViewById(R.id.fixName_ylx);
        btnFinish=findViewById(R.id.btnFinish_ylx);
        titleName = findViewById(R.id.titleName_ylx);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack_ylx://返回（应该记录已选题目)
                lsData.clear();
                addLsData(lsDataX);
                addLsData(lsDataT);
                addLsData(lsDataD);
                Intent intent = new Intent(WrongBasketActivity.this,AutoGeneratingPaperYLXActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subInCart", (Serializable)lsData);
                intent.putExtras(bundle);
                intent.putExtra("title",titleName.getText().toString().trim());
                startActivity(intent);
                finish();
                break;
            case R.id.tv_clear://清空
                clearCart();
                break;
            case R.id.btnFinish_ylx://完成组卷
                //弹出框
                showDialog();
                break;
            case R.id.fixName_ylx://修改组卷名称
                showFixDialog();
                break;
        }
    }
    //添加各种题型到lsData
    public void addLsData(List<SubjectMsg> data){
        for (SubjectMsg subjectMsg:data){
            lsData.add(subjectMsg);
        }
    }
    //修改框
    private void showFixDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(WrongBasketActivity.this);
        builder.setTitle("请输入标题名");
        final EditText editText = new EditText(WrongBasketActivity.this);
        builder.setView(editText);
        builder.setPositiveButton("是",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(editText.getText().toString().trim()!=null && !editText.getText().toString().trim().equals("")){
                    titleName.setText(editText.getText().toString().trim());
                }
            }
        });
        builder.setNegativeButton("否", null);
        builder.show();
    }

    //清空错题篮
    private void clearCart() {
        lsData.clear();
        lsDataT.clear();
        lsDataX.clear();
        lsDataD.clear();
        customAdapterInCartYLXT.notifyDataSetChanged();
        customAdapterInCartYLXX.notifyDataSetChanged();
        customAdapterInCartYLXD.notifyDataSetChanged();
        ListViewUtil.setListViewHeightBasedOnChildren(listViewT);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewX);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewD);
        txX.setText("一、选择题(共"+lsDataX.size()+"题)");
        txT.setText("二、填空题(共"+lsDataT.size()+"题)");
        txD.setText("三、大题(共"+lsDataD.size()+"题)");
        //弹出框返回继续选题
        AlertDialog.Builder builder  = new AlertDialog.Builder(WrongBasketActivity.this);
        builder.setTitle("提示" ) ;
        builder.setMessage("篮子里已经没有题目了，去添加吧" ) ;
        builder.setPositiveButton("去添加",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(WrongBasketActivity.this,AutoGeneratingPaperYLXActivity.class);
                startActivity(intent);
                finish();
            }
        });
        builder.show();
    }
    //初始化并弹出对话框方法
    private void showDialog(){
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.ylx_zujuan_dialog_layout,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(WrongBasketActivity.this).setView(view).create();
        Button btnOk = view.findViewById(R.id.button_ok_ylx);
        Button btnNo = view.findViewById(R.id.button_no_ylx);
        TextView tvTitle = view.findViewById(R.id.tv_message_ylx);
        TextView tvX = view.findViewById(R.id.tv_xuanze_ylx);
        TextView tvT = view.findViewById(R.id.tv_tiankong_ylx);
        TextView tvD = view.findViewById(R.id.tv_dati_ylx);
        tvTitle.setText(titleName.getText());
        tvX.setText("选择题："+lsDataX.size());
        tvT.setText("填空题："+lsDataT.size());
        tvD.setText("大题："+lsDataD.size());
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
                Log.i("www", "onClick: 确定组卷");
                //... To-do
                Intent intent = new Intent(WrongBasketActivity.this, ShowPaperActivity.class);
                lsData.clear();
                addLsData(lsDataX);
                addLsData(lsDataT);
                addLsData(lsDataD);
                Bundle bundle = new Bundle();
                bundle.putSerializable("subInCart", (Serializable)lsData);
                intent.putExtras(bundle);
                intent.putExtra("title",titleName.getText().toString().trim());
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //... To-do
                Log.i("www", "onClick: 取消组卷");
                dialog.dismiss();
            }
        });
    }
}
