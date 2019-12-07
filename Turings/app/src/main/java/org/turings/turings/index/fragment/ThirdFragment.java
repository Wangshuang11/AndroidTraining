package org.turings.turings.index.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.turings.turings.R;
import org.turings.turings.index.SpacesItemDecoration;
import org.turings.turings.index.adapter.RecyclerAdapter;
import org.turings.turings.index.adapter.RecyclerListAdapter;
import org.turings.turings.index.entity.Pre;
import org.turings.turings.index.entity.PreJson;
import org.turings.turings.index.entity.School;
import org.turings.turings.index.entity.SchoolJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class ThirdFragment extends Fragment{
    private LinearLayout parent;
    private RecyclerView listView;
    private RecyclerView recyclerView_985;
    private RecyclerView recyclerView_211;
    private List<School> schools=new ArrayList<>();
    private List<School>schools_211=new ArrayList<>();
    private List<Pre>pres=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson=new Gson();
            switch (msg.what){
                case 100:
                    String pre=(String)msg.obj;
                    PreJson preJson=gson.fromJson(pre, PreJson.class);
                    pres=preJson.getList();
                    RecyclerListAdapter recyclerListAdapter =new RecyclerListAdapter(getContext(),pres,R.layout.lph_listview_item,parent);
                    LinearLayoutManager manager2=new LinearLayoutManager(getContext(),1,false);
                    manager2.setOrientation(LinearLayoutManager.VERTICAL);
                    listView.setLayoutManager(manager2);
                    listView.setAdapter(recyclerListAdapter);
                    listView.setItemAnimator(new DefaultItemAnimator());
                    break;
                case 200:
                    String str_985=(String)msg.obj;
                    SchoolJson schoolJson=gson.fromJson(str_985,SchoolJson.class);
                    schools=schoolJson.getList();
                    GridLayoutManager manager = new GridLayoutManager(getContext(), 1);
                    manager.setOrientation(GridLayoutManager.HORIZONTAL);
                    recyclerView_985.setLayoutManager(manager);
                    RecyclerAdapter adapter_985 = new RecyclerAdapter(getContext(),R.layout.lph_gridview_item, schools);
                    recyclerView_985.setAdapter(adapter_985);
                    recyclerView_985.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_985.addItemDecoration(new SpacesItemDecoration(1));
                    break;
                case 300:
                    String str_211=(String)msg.obj;
                    SchoolJson schoolJson1=gson.fromJson(str_211,SchoolJson.class);
                    schools_211=schoolJson1.getList();
                    GridLayoutManager manager1= new GridLayoutManager(getContext(), 1);
                    manager1.setOrientation(GridLayoutManager.HORIZONTAL);
                    recyclerView_211.setLayoutManager(manager1);
                    RecyclerAdapter adapter_211 = new RecyclerAdapter(getContext(),R.layout.lph_gridview_item, schools_211);
                    recyclerView_211.setAdapter(adapter_211);
                    recyclerView_211.setItemAnimator(new DefaultItemAnimator());
                    recyclerView_211.addItemDecoration(new SpacesItemDecoration(1));
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_third_fragment, container, false);
        getViews(view);
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.ip)+":8080/Turings/SearchServlet");
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message message=Message.obtain();
                    message.what=100;
                    message.obj=info;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    String info=schoolFun("985");
                    Message message=Message.obtain();
                    message.what=200;
                    message.obj=info;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        new Thread(){
            @Override
            public void run() {
                try {
                    String info=schoolFun("211");
                    Message message=Message.obtain();
                    message.what=300;
                    message.obj=info;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        return view;
    }
    private void getViews(View view) {
        listView=view.findViewById(R.id.list);
        recyclerView_985=view.findViewById(R.id.grid_985);
        recyclerView_211=view.findViewById(R.id.grid_211);
        parent=view.findViewById(R.id.parent);
    }
    private String schoolFun(String flag) throws IOException {
        URL url = new URL("http://"+getResources().getString(R.string.ip)+":8080/Turings/SchoolServlet?flag="+flag);
        URLConnection conn = url.openConnection();
        InputStream in = conn.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
        String info = reader.readLine();
        return info;
    }
}
