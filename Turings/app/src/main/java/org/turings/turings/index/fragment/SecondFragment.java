package org.turings.turings.index.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.turings.turings.R;
import org.turings.turings.index.adapter.ListStoryAdapter;
import org.turings.turings.index.entity.Story;
import org.turings.turings.index.entity.StoryJson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SecondFragment extends Fragment{
    private LinearLayout parent;
    private ListView listView;
    private SmartRefreshLayout srl;
    private ListStoryAdapter listStoryAdapter;
    private List<Story> stories=new ArrayList<>();
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson=new Gson();
            String story=(String)msg.obj;
            stories.clear();
            stories=getDatas(gson,story);
            listStoryAdapter = new ListStoryAdapter(getContext(),
                    R.layout.lph_inspire_item2, stories, parent);
            listView.setAdapter(listStoryAdapter);
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_second_fragment, container, false);
        getViews(view);
        initDatas();
        srl.setReboundDuration(2000);
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                initDatas();
                srl.finishRefresh();
            }
        });
        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                Toast.makeText(getContext(), "已经到底了", Toast.LENGTH_SHORT).show();
                srl.finishLoadMore();
            }
        });
        return view;
    }
    private void getViews(View view) {
        parent = view.findViewById(R.id.parent);
        listView = view.findViewById(R.id.list_inspire);
        srl =view.findViewById(R.id.srl);
    }


    public List<Story> getDatas(Gson gson,String str) {
        StoryJson storyJson=gson.fromJson(str,StoryJson.class);
        return storyJson.getList();
    }
    public void initDatas(){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+getResources().getString(R.string.lphipConfig)+":8080/Turings/lph/findStoryAll");
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
    }
}
