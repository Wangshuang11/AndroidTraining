package org.turings.turings.near.Location;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.turings.turings.R;
import org.turings.turings.near.Adapter.AdepterDemo;
import org.turings.turings.near.entity.Share;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShareTitleActivity extends AppCompatActivity {
    private ListView lvShare_lyh;
    private ImageView btnBack_lyh;
    private Handler handler;
    private Gson gson;
    private AdepterDemo adapterDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_title);

        Intent intent = getIntent();
        String otherName = intent.getStringExtra("otherName");
        Log.i("lww",otherName);
        btnBack_lyh = findViewById(R.id.btnBack_lyh);
        btnBack_lyh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        lvShare_lyh = findViewById(R.id.lvShare_lyh);
        gson = new Gson();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json =  msg.obj.toString();
                Log.i("lww",json);
                //反序列化
                Type type = new TypeToken<List<Share>>(){}.getType();
                List<Share> shareList = gson.fromJson(json,type);
                initData(shareList);
            }
        };

        sendToServer(otherName);
    }

    private void initData(List<Share> shareList) {
        //准备数据
        final List<Map<String,String>> shares = new ArrayList<>();
        for (int i = 0;i<shareList.size();i++){
            Map<String,String> share = new HashMap<>();
            share.put("title",shareList.get(i).getShareTitle());
            share.put("content",shareList.get(i).getShareContent());
            share.put("background",shareList.get(i).getBackground());
            shares.add(share);
        }
        List<String> list = new ArrayList<>();
        for (int i = 0;i<shareList.size();i++){
            String background = shareList.get(i).getBackground();
            list.add(background);
        }
        //创建Adapter
        adapterDemo = new AdepterDemo(ShareTitleActivity.this,shares,R.layout.sharetitle_list_layout);

        //绑定Adapter
        lvShare_lyh.setAdapter(adapterDemo);

        //给AdapterView绑定事件监听器
        lvShare_lyh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ShareTitleActivity.this,SharedThingsActivity.class);
                intent.putExtra("title",shares.get(position).get("title"));
                intent.putExtra("content",shares.get(position).get("content"));
                intent.putExtra("background",shares.get(position).get("background"));
                startActivity(intent);
            }
        });

    }

    public void sendToServer(final String userName) {

        new Thread() {
            @Override
            public void run() {
                try {
//                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/BrowseShareServlet?userName=" + userName);
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/lyh/browseShareList?userName=" + userName);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void wrapperMessage(String info){
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }

}
