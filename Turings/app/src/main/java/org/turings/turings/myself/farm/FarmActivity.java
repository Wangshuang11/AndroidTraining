package org.turings.turings.myself.farm;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import org.turings.turings.R;
import org.turings.turings.MainActivity;

/*
*小葵花农场主页
* 其中包括：
* 1.小葵花浇水后不同时期的生长状态（4个）判断
* 2.根据用户收集水滴周期判断花盆的繁盛枯萎（2个）
* 3.最后阶段交换礼物
* 4.任务上拉菜单
* 5.点击小葵花出现励志名言
 */
public class FarmActivity extends AppCompatActivity {
    private ImageView flowerImg;//花
    private ImageView flowerpotImg;//花盆
    private int waterDroplet;//水滴（积分）
    private Button changeGiftImg;//交换礼物
    private ImageView flowerWaterImg;//浇花
    private ImageView process;//成长
    private Button taskBtn;//任务
    private MyListener listener;
    private Intent intent=new Intent();
    private int giftSatus=0;//不能交换礼物的状态
    private int dryStatus=0;//非枯萎状态
    private TeskPopupwindow mPhotoPopupWindow;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_farm_index);
        //获取控件
        getViews();
        //加载点击事件
        registerClick();
        //1.获取当前用户成长值,水滴拥有量
        getWaterDroplet(1);
        //2.判断多久未用本APP,修改dry值
        judgeNotGetWater();
        //3.根据2及成长值显示小葵花的生长状态、判断花盆状态
        judgeProcess1(250,dryStatus);
        //金鑫媛的部分
        jxypart();

    }

    private void jxypart() {
        Button tesk_btn = findViewById(R.id.sxn_flower_task);
        Button gift_btn = findViewById(R.id.sxn_change_gift);
        tesk_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mPhotoPopupWindow = new TeskPopupwindow(FarmActivity.this);
                View rootView = LayoutInflater.from(FarmActivity.this).inflate(R.layout.sxn_farm_index, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        gift_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GiftPopupwindow giftPopupwindow = new GiftPopupwindow(FarmActivity.this);
                View rootView = LayoutInflater.from(FarmActivity.this).inflate(R.layout.sxn_farm_index, null);
                giftPopupwindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
//                case R.id.sxn_change_gift:
//                    //跳转交换礼物（当无法交换礼物时，可以理解规则，可以交换礼物时进行填信息交换）
//                    intent.setClass(FarmActivity.this, GiftChangeActivity.class);
//                    startActivity(intent);
//                    break;
                case R.id.farm_head:
                    //跳转主页或fragment
                    intent.setClass(FarmActivity.this, MainActivity.class);
                    intent.setAction("loginBackMyself");
                    startActivityForResult(intent,0);
                    break;
//                case R.id.sxn_flower_task:
//                    //出现上拉框
//                    break;
                case R.id.sxn_flower_water:
                    //出现动画浇水
                    break;
            }
        }
    }
    //判断已经多少天未使用本软件（未收集水滴）
    private void judgeNotGetWater(){
        //若超过20天，则进入花枯萎状态
        //超过一个月，花盆也枯萎
    }
    //判断生长状态
    private void judgeProcess1(int process1,int dryStatus1) {
        if (dryStatus1 == 0) {
            if (process1 < 50) {
                flowerImg.setImageResource(R.drawable.farm_flower_1);
            } else if ((50 < process1 || process1 == 50) && process1 < 100) {
                flowerImg.setImageResource(R.drawable.farm_flower_2);
            } else if ((100 < process1 || process1 == 100) && process1 < 250) {
                flowerImg.setImageResource(R.drawable.farm_flower_3);
            } else if (250 < process1 || process1 == 250) {
                flowerImg.setImageResource(R.drawable.farm_flower_4);
                //交换礼物的按钮出现变化
                giftSatus = 1;
            }
        } else if(dryStatus1==1) {
            //花枯萎
            flowerImg.setImageResource(R.drawable.farm_flower_dry2);
        }else {
            //花、盆全枯萎
            flowerImg.setImageResource(R.drawable.farm_flower_dry2);
            flowerpotImg.setImageResource(R.drawable.farm_flower_dry2);
        }
    }
    //获得当前用户的水滴（积分）
    private int getWaterDroplet(int userId){
        return 0;
    }
    //获得当前用户的成长值
    private int getProcess(int userId){
        return 0;
    }
    //获取视图
    private void getViews(){
        flowerImg=findViewById(R.id.sxn_flower);
        flowerpotImg=findViewById(R.id.sxn_flowerpot);
        flowerWaterImg=findViewById(R.id.sxn_flower_water);
        changeGiftImg=findViewById(R.id.sxn_change_gift);
        process=findViewById(R.id.sxn_process);
        taskBtn=findViewById(R.id.sxn_flower_task);
    }
    //点击事件
    private void registerClick(){
        listener=new MyListener();
        changeGiftImg.setOnClickListener(listener);
        changeGiftImg.setOnClickListener(listener);
        taskBtn.setOnClickListener(listener);
        flowerWaterImg.setOnClickListener(listener);
    }
}
