package org.turings.turings.near.Location;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.turings.turings.R;

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
import java.util.Map;

public class WriteThingsActivity extends AppCompatActivity {
    private ImageView btnBack_lyh;
    private Spinner spinner;
    private TextView btnOk;
    private EditText edtTitle;
    private EditText edtContent;
    private MyLinstener linstener;
    private RelativeLayout llBack;
    private Handler handler;
    private String userName;
    private String title;
    private String content;
    private String background;
    private int backId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_things);

        getViews();
        registerLinstener();
        //声明一个简单simpleAdapter
        SimpleAdapter simpleAdapter =new SimpleAdapter(this, getListData(), R.layout.spinner_layout,
                new String[]{"npic","namepic"}, new int[]{R.id.ivBack,R.id.tvName_lyh});

        //绑定到sp02
        spinner.setAdapter(simpleAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //parent为一个Map结构的和数据
                Map<String, Object> map = (Map<String, Object>) parent.getItemAtPosition(position);
                Object object = map.get("npic");
                backId = (Integer) object;
                llBack.setBackgroundResource(backId);
                Toast.makeText(WriteThingsActivity.this, map.get("namepic").toString(),Toast.LENGTH_SHORT).show();
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String json =  msg.obj.toString();
                if (json.equals("1")){
                    Toast.makeText(getApplicationContext(),"添加成功",Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    public List<Map<String, Object>> getListData() {

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

        //每个Map结构为一条数据，key与Adapter中定义的String数组中定义的一一对应。

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("npic", R.drawable.bj1);
        map.put("namepic", "繁星天际");
        list.add(map);

        HashMap<String, Object> map2 = new HashMap<String, Object>();
        map2.put("npic", R.drawable.bj2);
        map2.put("namepic", "冰海雪原");
        list.add(map2);

        HashMap<String, Object> map3 = new HashMap<String, Object>();
        map3.put("npic", R.drawable.bj3);
        map3.put("namepic", "紫玉成烟");
        list.add(map3);

        HashMap<String, Object> map4 = new HashMap<String, Object>();
        map4.put("npic", R.drawable.bj4);
        map4.put("namepic", "翩若惊鸿");
        list.add(map4);

        HashMap<String, Object> map5 = new HashMap<String, Object>();
        map5.put("npic", R.drawable.bj5);
        map5.put("namepic", "安之若素");
        list.add(map5);

        HashMap<String, Object> map6 = new HashMap<String, Object>();
        map6.put("npic", R.drawable.bj6);
        map6.put("namepic", "微光倾城");
        list.add(map6);

        HashMap<String, Object> map7 = new HashMap<String, Object>();
        map7.put("npic", R.drawable.bj7);
        map7.put("namepic", "浮光掠影");
        list.add(map7);

        HashMap<String, Object> map8 = new HashMap<String, Object>();
        map8.put("npic", R.drawable.bj8);
        map8.put("namepic", "流绪微梦");
        list.add(map8);

        HashMap<String, Object> map9 = new HashMap<String, Object>();
        map9.put("npic", R.drawable.bj9);
        map9.put("namepic", "秋水盈盈");
        list.add(map9);

        return list;
    }

    private void registerLinstener() {
        linstener = new MyLinstener();
        btnBack_lyh.setOnClickListener(linstener);
        btnOk.setOnClickListener(linstener);
    }

    private void getViews() {
        btnBack_lyh = findViewById(R.id.btnBack_lyh);
        spinner = findViewById(R.id.spinner);
        llBack = findViewById(R.id.llBack);
        edtTitle = findViewById(R.id.edtTitle);
        edtContent = findViewById(R.id.edtContent);
        btnOk = findViewById(R.id.btnOk);
    }

    class MyLinstener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btnBack_lyh:
                    finish();
                    break;
                case R.id.btnOk:
                    SharedPreferences sharedPreferences=getSharedPreferences("userInfo",MODE_PRIVATE);
                    userName=sharedPreferences.getString("name","");
                    title = edtTitle.getText().toString().trim();
                    content = edtContent.getText().toString().trim();
                    String back = String.valueOf(backId);
                    background = back.replace("R.drawable","");
                    Log.i("lww",userName + title+content+background);
                    sendToServer(userName,title,content,background);
                    Intent intent = new Intent(WriteThingsActivity.this,WriteActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

    public void sendToServer(final String userName, final String title, final String content, final String background) {

        new Thread() {
            @Override
            public void run() {
                try {
//                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/InsertShareServlet?userName="+userName+"&title="+title
//                    +"&content="+content+"&background="+background);
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/lyh/insertShare?userName="+userName+"&title="+title
                            +"&content="+content+"&background="+background);
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
