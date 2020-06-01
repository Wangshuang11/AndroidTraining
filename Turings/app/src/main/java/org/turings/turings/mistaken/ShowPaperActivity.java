package org.turings.turings.mistaken;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.turings.turings.R;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomAdapterInPagerYLX;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pl.droidsonroids.gif.GifImageView;

public class ShowPaperActivity extends AppCompatActivity implements View.OnClickListener {
    private List<SubjectMsg> lsData = new ArrayList<>();//选在篮子里的所有题目
    private List<SubjectMsg> lsDataX = new ArrayList<>();//选择题集合
    private List<SubjectMsg> lsDataT = new ArrayList<>();//填空题集合
    private List<SubjectMsg> lsDataD = new ArrayList<>();//大题集合
    //适配器
    private CustomAdapterInPagerYLX customAdapterInPagerYLXX;//选择题的适配器
    private CustomAdapterInPagerYLX customAdapterInPagerYLXT;//填空题的适配器
    private CustomAdapterInPagerYLX customAdapterInPagerYLXD;//大题的适配器
    //listView
    private ListView listViewX;//选择
    private ListView listViewT;//填空
    private ListView listViewD;//大题
    //控件
    private TextView txX;
    private TextView txT;
    private TextView txD;
    private ImageView back;//返回箭头
    private TextView titleName;//卷名
    private Button btnNow;
    private Button btnDown;
    private LinearLayout xLayout;
    private LinearLayout tLayout;
    private LinearLayout dLayout;
    private GifImageView gifImageView;
    // 模板文集地址
    private static final String demoPath = Environment.getExternalStorageDirectory().getPath()+"/turings/";
    // 创建生成的文件地址
    private static final String newPath = Environment.getExternalStorageDirectory().getPath()+"/turings/";
    private  String finalPath = "";
    private  String path="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ylx_activity_show_paper);
        getViews();
        registers();
        inits();
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
        titleName = findViewById(R.id.titleName_ylx);
        btnNow = findViewById(R.id.btnNow_ylx);
        btnDown = findViewById(R.id.btnDown_ylx);
        xLayout = findViewById(R.id.x_layout);
        tLayout = findViewById(R.id.t_layout);
        dLayout = findViewById(R.id.d_layout);
        gifImageView = findViewById(R.id.load_gif);
    }

    //初始化数据
    private void inits() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        lsData = (List<SubjectMsg>) bundle.getSerializable("subInCart");
        Log.i("www", "onCreate: "+lsData.toString());
        titleName.setText(intent.getStringExtra("title"));
        for(SubjectMsg subjectMsg:lsData){
            if(subjectMsg.getType().equals("选择题")){
                lsDataX.add(subjectMsg);
            }else if(subjectMsg.getType().equals("填空题")){
                lsDataT.add(subjectMsg);
            }else {
                lsDataD.add(subjectMsg);
            }
        }
        Log.i("www", "onCreate: "+lsDataT.toString());
        txX.setText("一、选择题(共"+lsDataX.size()+"题)");
        txT.setText("二、填空题(共"+lsDataT.size()+"题)");
        txD.setText("三、大题(共"+lsDataD.size()+"题)");
        //注册适配器绑定数据源
        customAdapterInPagerYLXX = new CustomAdapterInPagerYLX(lsDataX,getApplicationContext(), R.layout.ylx_paper_item_layout);
        customAdapterInPagerYLXT = new CustomAdapterInPagerYLX(lsDataT,getApplicationContext(), R.layout.ylx_paper_item_layout);
        customAdapterInPagerYLXD = new CustomAdapterInPagerYLX(lsDataD,getApplicationContext(), R.layout.ylx_paper_item_layout);
        listViewX.setAdapter(customAdapterInPagerYLXX);
        listViewT.setAdapter(customAdapterInPagerYLXT);
        listViewD.setAdapter(customAdapterInPagerYLXD);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewX);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewT);
        ListViewUtil.setListViewHeightBasedOnChildren(listViewD);
    }
    //注册事件
    private void registers() {
        back.setOnClickListener(this);
        btnNow.setOnClickListener(this);
        btnDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBack_ylx://返回
                finish();
                break;
            case R.id.btnNow_ylx://在线做题
                lsData.clear();
                addLsData(lsDataX);
                addLsData(lsDataT);
                addLsData(lsDataD);
                if(lsData.size()!=0){
                    Intent intent = new Intent(ShowPaperActivity.this, OnlineTestingActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("subInCart", (Serializable)lsData);
                    intent.putExtras(bundle);
                    intent.putExtra("title",titleName.getText().toString().trim());
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(getApplicationContext(),"空卷,去添加题目做题吧",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btnDown_ylx://下载
                try {
                    gifImageView.setVisibility(View.VISIBLE);
                    InputStream is = getApplicationContext().getAssets().open("juan.doc");
                    File file = new File(demoPath);
                    //判断文件夹是否存在，如果不存在就创建，否则不创建
                    if (!file.exists()) {
                        //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
                        file.mkdirs();
                    }
                    String filePath = demoPath+"test.doc";
                    Log.i("www", "**************************onClick: 文件位置"+filePath);
                    File file1 = new File(filePath);
                    if(!file1.exists()){
                        Log.i("www", "onClick: 文件不存在要拷贝");
                        FileOutputStream fos = new FileOutputStream(file1);
                        byte[] buffer = new byte[1024];
                        int byteCount;
                        while ((byteCount = is.read(buffer)) != -1) {// 循环从输入流读取
                            // buffer字节
                            fos.write(buffer, 0, byteCount);// 将读取的输入流写入到输出流
                        }
                        Log.i("www", "onClick: 成功了");
                        fos.flush();// 刷新缓冲区
                        is.close();
                        fos.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                doScan();
                break;
        }
    }
    //添加各种题型到lsData
    public void addLsData(List<SubjectMsg> data){
        for (SubjectMsg subjectMsg:data){
            lsData.add(subjectMsg);
        }
    }

    private void doScan(){
        //获取模板文件
        File demoFile=new File(demoPath+"test.doc");
        finalPath = newPath +titleName.getText().toString()+System.currentTimeMillis()+"试卷.doc";
        path ="/turings/"+titleName.getText().toString()+System.currentTimeMillis()+"试卷.doc";
        //拼接出选择题填空题大题的字符串
        String str = "";
        StringBuffer xStr = new StringBuffer(str);
        StringBuffer tStr = new StringBuffer(str);
        StringBuffer dStr = new StringBuffer(str);
        for(SubjectMsg subjectMsg:lsDataX){
            xStr.append(subjectMsg.getContent()+"\r\n"+subjectMsg.getOptionA()
                    +"\r\n"+subjectMsg.getOptionB()+"\r\n"
                    +subjectMsg.getOptionC()+"\r\n"
                    +subjectMsg.getOptionD()+"\r\n"
                    +"答案："+subjectMsg.getAnswer()+"\r\n");
        }
        for(SubjectMsg subjectMsg:lsDataT){
            tStr.append(subjectMsg.getContent()+"\r\n"+"答案："+subjectMsg.getAnswer()+"\r\n");
        }
        for(SubjectMsg subjectMsg:lsDataD){
            dStr.append(subjectMsg.getContent()+"\r\n"+"答案："+subjectMsg.getAnswer()+"\r\n");
        }
        //创建生成的文件
        File newFile=new File(finalPath);
        Map<String, String> map = new HashMap<String, String>();
        map.put("$titleName$", titleName.getText().toString());
        map.put("$count1$", lsDataX.size()+"");
        map.put("$content1$", xStr.toString());
        map.put("$count2$", lsDataT.size()+"");
        map.put("$content2$", tStr.toString());
        map.put("$count3$", lsDataD.size()+"");
        map.put("$content3$",dStr.toString() );
        writeDoc(demoFile,newFile,map);
    }
    /**
     * demoFile 模板文件
     * newFile 生成文件
     * map 要填充的数据
     * */
    public void writeDoc(File demoFile ,File newFile ,Map<String, String> map)
    {
        try
        {
            FileInputStream in = new FileInputStream(demoFile);
            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // 读取word文本内容
            Range range = hdt.getRange();
            // System.out.println(range.text());

            // 替换文本内容
            for(Map.Entry<String, String> entry : map.entrySet())
            {
                range.replaceText(entry.getKey(), entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream);
            // 输出字节流
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();
        } catch(IOException e) {
            Toast.makeText(getApplicationContext(),"下载失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch(Exception e) {
            Toast.makeText(getApplicationContext(),"下载失败",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),"下载成功",Toast.LENGTH_SHORT).show();
        gifImageView.setVisibility(View.GONE);
        //发送通知
        sendNotification("下载成功");
    }

    private void sendNotification(String str) {
        Intent intent = new Intent(ShowPaperActivity.this,ViewDownloadedPapersActivity.class);
        intent.putExtra("path",path);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //Android8.0以上必须添加 渠道 才能显示通知栏
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification1;
        Bitmap largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ylxloadsuccess);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建渠道
            String id = "my_channel_01";
            String name="渠道名字";
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(mChannel);
            //设置图片,通知标题,发送时间,提示方式等属性
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, id);
            builder.setContentTitle("组卷下载")  //标题
                    .setContentText("您的组卷下载成功")   //内容
                    .setWhen(System.currentTimeMillis())    //系统显示时间
                    .setSmallIcon(R.mipmap.icon1)     //收到信息后状态栏显示的小图标
                    .setLargeIcon(largeBitmap)
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                    .setDefaults(Notification.DEFAULT_SOUND)    //设置系统的提示音
                    .setAutoCancel(true);       //设置点击后取消Notification
            builder.setContentIntent(pendingIntent);    //绑定PendingIntent对象

            notification1 = builder.build();
            notificationManager.notify(1, notification1);
        } else {
            //设置图片,通知标题,发送时间,提示方式等属性
            Notification.Builder builder = new Notification.Builder(this);
            builder.setContentTitle("组卷下载")  //标题
                    .setContentText("您的组卷下载成功")   //内容
                    .setWhen(System.currentTimeMillis())    //系统显示时间
                    .setLargeIcon(largeBitmap)
                    .setSmallIcon(R.mipmap.icon1)     //收到信息后状态栏显示的小图标
                    .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE)    //设置默认的三色灯与振动器
                    .setDefaults(Notification.DEFAULT_SOUND)    //设置系统的提示音
                    .setAutoCancel(true);       //设置点击后取消Notification
            builder.setContentIntent(pendingIntent);    //绑定PendingIntent对象
            notification1 = builder.build();
            notificationManager.notify(1, notification1);

        }



    }
}
