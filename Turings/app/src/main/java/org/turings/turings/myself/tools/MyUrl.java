package org.turings.turings.myself.tools;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.turings.turings.myself.entity.Fan;
import org.turings.turings.myself.entity.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyUrl {
    public String url;
    private List<User> users;
    private List<Fan> fans;
    private List<HashMap<String,Object>> lists;
    private HashMap<String,Object> map;
    private Gson gson;
    private Context mc;
    private Handler handler;

    public MyUrl(Context mc) {
        super();
        this.mc = mc;
    }

    private MyUrl() {
        super();

    }
    public List<HashMap<String,Object>> sendToServerUser(final String str) {
        users = new ArrayList<>();
        lists= new ArrayList<>();
        gson=new Gson();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(str);
                    URLConnection conn = null;
                    conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    users= new ArrayList<>();
                    users=gson.fromJson(info, new TypeToken<List<User>>(){}.getType());
        for (User user2 : users) {
            map = new HashMap<>();

            Resources a = mc.getResources();
            int nn = a.getIdentifier(user2.getuAvatar(), "mimap",mc.getPackageName());
//            int nn = getResources(mc,"dfgcdfg").getIdentifier(user2.getuAvatar(), "mimap", getBaseContext().getPackageName());
            map.put("userName", user2.getuName());
            map.put("userMotto", user2.getuMotto());
            map.put("userAvatar", nn);
            lists.add(map);
            Log.e("aalist", String.valueOf(lists.size()));
        }
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        while (lists == null) {
            try {
                thread.join(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }
    public List<HashMap<String,Object>> sendToServerFan(final String str) {
        users = new ArrayList<>();
        lists= new ArrayList<>();
        gson=new Gson();
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(str);
                    URLConnection conn = null;
                    conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message msg = Message.obtain();
                    msg.obj = info;
                    fans= new ArrayList<>();
                    fans=gson.fromJson(info, new TypeToken<List<User>>(){}.getType());
                    for (Fan fan : fans) {
                        map = new HashMap<>();
                        Resources a = mc.getResources();
                        int nn = a.getIdentifier(fan.getImg(), "mimap",mc.getPackageName());
//            int nn = getResources(mc,"dfgcdfg").getIdentifier(user2.getuAvatar(), "mimap", getBaseContext().getPackageName());
                        map.put("name", fan.getName());
                        map.put("motto",fan.getMotto());
                        map.put("img", nn);
                        lists.add(map);
                        Log.e("aalist", String.valueOf(lists.size()));
                    }
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        while (lists == null) {
            try {
                thread.join(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return lists;
    }
}
