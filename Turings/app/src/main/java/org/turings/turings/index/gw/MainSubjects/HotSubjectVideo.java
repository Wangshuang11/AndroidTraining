package org.turings.turings.index.gw.MainSubjects;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import org.turings.turings.R;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class HotSubjectVideo extends AppCompatActivity {
    private ListView mListView;
    private List<String> listUrl = new ArrayList<>();
    private List<String> listTitle = new ArrayList<>();
    private HotVideoAdapter hotVideoAdapter;
    private Handler courseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==2) {
                hotVideoAdapter.notifyDataSetChanged();
            }
        }
    };


    //搜索框
    private SearchView searchView;

    private SmartRefreshLayout srl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gw_hot_subject_video_list);
        srl = findViewById(R.id.Gsrl);
        init();
        mListView = (ListView) findViewById(R.id.videoList);
        hotVideoAdapter = new HotVideoAdapter(this,listUrl,listTitle);
        mListView.setAdapter(hotVideoAdapter);

        //搜索框精准查找
        // 3. 绑定组件
        searchView = findViewById(R.id.search_view);

        // 4. 设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(final String search) {
                new Thread(){
                    @Override
                    public void run() {
                        listTitle.clear();
                        listUrl.clear();
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url("http://"+getResources().getString(R.string.hostGuo)+":8080/Turings/SearchServlet?search="+search).build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure( Call call, IOException e) {
                                Log.e("gw","返回失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<HotVideo>>() {
                                }
                                        .getType();
                                String json = response.body().string();
                                if (json != "[]") {
                                    List<HotVideo> videos = gson.fromJson(json, type);
                                    for (int i = 0; i < videos.size(); i++) {
                                        Log.e("gw", videos.get(i).getVideoUrl());
                                        listUrl.add(videos.get(i).getVideoUrl());
                                        Log.e("gw", videos.get(i).getBranch());
                                        listTitle.add(videos.get(i).getBranch() + "");
                                    }
                                    Message message = Message.obtain();
                                    message.what = 2;
                                    courseHandler.sendMessage(message);
                                }
                            }
                        });
                    }
                }.start();
            }
        });
        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });

        srl.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (listTitle.size()>=6){
                    srl.finishLoadMoreWithNoMoreData();
                    Toast.makeText(getApplicationContext(),
                            "没有更多视频了",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    LoadMoreVideos();
                    srl.finishLoadMore();
//                    Toast.makeText(getApplicationContext(),
//                            "加载完成",
//                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void LoadMoreVideos() {
        new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://"+getResources().getString(R.string.hostGuo)+":8080/Turings/VideoServlet?begin=3").build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Log.e("gw","返回失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<HotVideo>>(){}
                                .getType();
                        String json = response.body().string();
                        List<HotVideo> videos = gson.fromJson(json,type);
//                        if (!videos.equals("[]")){
                        for (int i = 0 ; i<videos.size(); i++){
                            Log.e("gw",videos.get(i).getVideoUrl());
                            listUrl.add(videos.get(i).getVideoUrl());
                            Log.e("gw",videos.get(i).getBranch());
                            listTitle.add(videos.get(i).getBranch()+"");
                        }
                        Message message = Message.obtain();
                        message.what = 2;
                        courseHandler.sendMessage(message);
//                        }
                    }
                });
            }
        }.start();
    }

    private void init(){
        new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://"+getResources().getString(R.string.hostGuo)+":8080/Turings/VideoServlet?begin=0").build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure( Call call, IOException e) {
                        Log.e("gw","返回失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<HotVideo>>(){}
                                .getType();
                        String json = response.body().string();
                        List<HotVideo> videos = gson.fromJson(json,type);
                            for (int i = 0 ; i<videos.size(); i++){
                                Log.e("gw",videos.get(i).getVideoUrl());
                                listUrl.add(videos.get(i).getVideoUrl());
                                Log.e("gw",videos.get(i).getBranch());
                                listTitle.add(videos.get(i).getBranch()+"");
                            }
                            Message message = Message.obtain();
                            message.what = 2;
                            courseHandler.sendMessage(message);
                    }
                });
            }
        }.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}