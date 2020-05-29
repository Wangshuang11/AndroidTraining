package org.turings.turings.myself.farm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.turings.turings.R;
import org.turings.turings.index.adapter.RecyclerCommentAdapter;
import org.turings.turings.index.entity.Comment2;
import org.turings.turings.index.entity.Comment2Json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static com.hyphenate.chat.EMClient.TAG;

public class TeskPopupwindow extends PopupWindow {


    private View mView; // PopupWindow 菜单布局
    private Activity mContext; // 上下文参数
    private int id;
    private Button btn_login;
    private Button btn_mistaken;
    private Button btn_course;
    private Button btn_pinglun;
    private Map<String,Integer> map;
    /*@SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
         public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 001:



                    break;
                case 200:
//                    Gson gson=new Gson();
//                    String str=(String)msg.obj;
//                    Log.e("任务弹窗后台数据",str+"");
//
//                    Type type = new TypeToken<Map<String, Integer>>(){}.getType();
//                    map=gson.fromJson(str, type);
//                    Init();
                    break;
                *//*case 300:
                    String n=(String)msg.obj;
                    if(n.equals("1")){
                        sendToServerForComment();
                        Toast.makeText(getApplicationContext(),"评论成功",Toast.LENGTH_SHORT).show();
                    }
                    break;*//*
            }
        }
    };*/

    public TeskPopupwindow(Activity context) {
        super(context);
        this.mContext = context;
        id= Integer.parseInt(mContext.getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        Log.e(TAG,"id:"+id );
        Gson gson=new Gson();


        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mContext.getResources().getString(R.string.connUrl)+"/getMyTasks?uid="+id);
                    Log.e(TAG, "run: "+url );
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what=200;
                    Gson gson=new Gson();
                    String str=(String)msg.obj;
                    Log.e("任务弹窗后台数据",str+"");

                    Type type = new TypeToken<Map<String, Integer>>(){}.getType();
                    map=gson.fromJson(str, type);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };


        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        Log.e(TAG, "TeskPopupwindow: "+"老子等你" );
        Init();
    }

    private void getServer() {

    }

    private void setText() {

    }

    private ImageView imageView;

    /**
     * 设置布局以及点击事件
     */
    private void Init() {
        Log.e(TAG, "Init: 没运行");
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.jxy_tesk_popup_item, null);
        btn_login = (Button) mView.findViewById(R.id.islogin);

//        Button btn_select = (Button) mView.findViewById(R.id.icon_btn_select);
//        Button btn_cancel = (Button) mView.findViewById(R.id.icon_btn_cancel);
//
//        btn_select.setOnClickListener(mSelectListener);
//        btn_camera.setOnClickListener(mCaptureListener);


        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 1.0f;
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mContext.getWindow().setAttributes(lp);
        // 导入布局
        this.setContentView(mView);
        // 设置动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
//         单击弹出窗以外处 关闭弹出窗
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mView.findViewById(R.id.popup_bg).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        if(map.get("islogin")==1){
            Log.e(TAG, "Init: "+1 );
            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(btn_login.getText().toString().equals("已领取")){
                        Toast.makeText(mContext, "你已经领取过了", Toast.LENGTH_SHORT).show();
                    }else {
                        Log.e(TAG, "onClick: " + 1);
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL(mContext.getResources().getString(R.string.connUrl) + "/changeLoginTaskWater?uid=" + id);
                                    Log.e(TAG, "run: " + url);
                                    URLConnection conn = url.openConnection();
                                    InputStream in = conn.getInputStream();
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                                    String info = reader.readLine();
                                    Message msg = Message.obtain();
                                    msg.obj = info;
                                    msg.what = 001;

//                                handler.sendMessage(msg);
                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        thread.start();
                        try {
                            thread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(mContext, "领取成功", Toast.LENGTH_SHORT).show();
                        LayoutInflater layout=mContext.getLayoutInflater();
                        View view001=layout.inflate(R.layout.sxn_farm_index, null);
                        TextView b=(TextView)view001.findViewById(R.id.farm_water);
//                    b.setText((Integer.getInteger(b.getText().toString())+5));
                        btn_login.getBackground().setAlpha(0);
                        btn_login.setText("已领取");
                        btn_login.setTextColor(0xff880089);

//                    btn_login.setBackgroundColor(0xfffdfdfd);
//                dismiss();
                    }
                }
            });
        }else if(map.get("islogin")==2){
            Log.e(TAG, "Init: "+2 );
            btn_login.getBackground().setAlpha(0);
            btn_login.setText("已领取");
            btn_login.setTextColor(0xffffff);
        }

    }

    public List<Comment2> getDatas(Gson gson, String str) {
        Comment2Json comment2Json=gson.fromJson(str,Comment2Json.class);
        Log.e(TAG, "getDatas: "+ comment2Json +":"+comment2Json.getList());
        return comment2Json.getList();
    }
}