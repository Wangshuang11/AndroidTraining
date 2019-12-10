package org.turings.turings.myself.tools;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.turings.turings.R;
import org.turings.turings.myself.entity.Course;
import org.turings.turings.myself.entity.Fan;
import org.turings.turings.myself.entity.School;
import org.turings.turings.myself.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class MyUrl {
    private List<Fan> fans;
    private List<School> schools;
    private List<User> users;
    private List<Course> courses;
    private Gson gson;
    private Context mc;
    private String info;
    private ListView listView;
    private TextView name;
    private TextView motto;
    private TextView fanC;
    private TextView concernC;
    private TextView achieveC;
    private ImageView avatar;
    private ImageAdapter imageAdapter;
    private FanAdapter fanAdapter;
    private CourseAdapter courseAdapter;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case R.layout.sxn_item_course:
                    //获取我的课程的数据
                    info= (String) msg.obj;
                    courses= gson.fromJson(info, new TypeToken<List<Course>>(){}.getType());
                    courseAdapter=new CourseAdapter(mc,courses,R.layout.sxn_item_course);
                    listView.setAdapter(courseAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //跳转到课程详情
                        }
                    });

                    break;
                case R.layout.sxn_item_school:
                    info= (String) msg.obj;
                    schools = gson.fromJson(info, new TypeToken<List<School>>() {}.getType());
                    imageAdapter=new ImageAdapter(mc,schools,R.layout.sxn_item_school);
                    listView.setAdapter(imageAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //获得学校连接，并跳转
                            String url=schools.get(position).getUrl();
                            Uri uri = Uri.parse(url);    //设置跳转的网站
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            mc.startActivity(intent);
                        }
                    });
                    break;
                case R.layout.sxn_item_fans:
                    info= (String) msg.obj;
                    fans = gson.fromJson(info, new TypeToken<List<Fan>>() {}.getType());
                    fanAdapter=new FanAdapter(mc,fans,R.layout.sxn_item_fans,
                            R.id.sxn_fans_img,R.id.sxn_fans_nickname_text,R.id.sxn_fans_motto_text);
                    listView.setAdapter(fanAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //跳转粉丝个人你主页
                        }
                    });
                    break;
                case R.layout.sxn_item_concerns:
                    info= (String) msg.obj;
                    fans=new ArrayList<>();
                    fans = gson.fromJson(info, new TypeToken<List<Fan>>() {}.getType());
                    Log.e("数量",fans.size()+"ssssss");
                    fanAdapter=new FanAdapter(mc,fans,R.layout.sxn_item_concerns,
                            R.id.sxn_concern_img,R.id.sxn_concern_nickname_text,R.id.sxn_concern_motto_text);
                    listView.setAdapter(fanAdapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            //跳转粉丝个人你主页
                        }
                    });
                    break;
                case R.layout.sxn_activity_logged:
                    info= (String) msg.obj;
                    Log.e("数量", info);
                    users=gson.fromJson(info, new TypeToken<List<User>>(){}.getType());
                    Log.e("nnn",users.get(0).getName());
                    name.setText(users.get(0).getName());
                    RequestOptions requestOptions=new RequestOptions().circleCrop();
                    Log.e(users.get(0).getName(),users.get(0).getMotto()+"ggggggggggggggggggg");
                    motto.setText(users.get(0).getMotto());
                    Glide.with(mc).load(users.get(0).getAvatar()).apply(requestOptions).into(avatar);
                    fanC.setText(String.valueOf(users.get(0).getFancount()));
                    concernC.setText(String.valueOf(users.get(0).getConcount()));
                    achieveC.setText(String.valueOf(users.get(0).getAchcount()));
                    break;
            }
        }
    };

    public MyUrl(Context mc) {
        super();
        this.mc = mc;
    }
    public void sendToServerListview(final String str, final int layout, final ListView lv) {
        gson=new Gson();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(str);
                    URLConnection conn = url.openConnection();
                    Log.e("1",conn.toString());
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    listView=lv;
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Log.e("点点","sssssssssssss");
                        }
                    });
                    msg.what=layout;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    public void sendToServerMyMessage(final String str, final int layout, final TextView fanCount,
                                      final TextView concernCount, final TextView achieveCount,
                                      final TextView uName, final TextView uMotto, final ImageView uAvatar) {
        gson=new Gson();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(str);
                    URLConnection conn = url.openConnection();
                    Log.e("1",conn.toString());
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what=layout;
                    name=uName;
                    motto=uMotto;
                    avatar=uAvatar;
                    fanC=fanCount;
                    concernC=concernCount;
                    achieveC=achieveCount;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
    public void sendToServerChange(final String str, final int layout) {
        gson=new Gson();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(str);
                    URLConnection conn = url.openConnection();
                    Log.e("1",conn.toString());
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    msg.what=layout;
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

}

