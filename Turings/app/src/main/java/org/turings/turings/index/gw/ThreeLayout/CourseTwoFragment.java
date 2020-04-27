package org.turings.turings.index.gw.ThreeLayout;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.turings.turings.R;
import org.turings.turings.index.gw.HotCourses.HotCourse;
import org.turings.turings.index.gw.MuLu.MuLuAdapter;
import org.turings.turings.index.gw.MuLu.Mulu;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CourseTwoFragment extends Fragment {
    private List<Mulu> mulus = new ArrayList<>();
    private RecyclerView mululist;
    private MuLuAdapter muLuAdapter;

    private Handler courseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==2) {
                muLuAdapter.notifyDataSetChanged();
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.video_mulu,container,false);
        Bundle bundle = getArguments();
        String a  = bundle.getString("parentId");
        initMulus(a);
       mululist = view.findViewById(R.id.muluList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
        mululist.setLayoutManager(layoutManager1);
        muLuAdapter = new MuLuAdapter(mulus);
        mululist.setAdapter(muLuAdapter);
        return view;
    }
    private void initMulus(final String a) {
        new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/gw/Detail?parentId="+a).build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("gw", "返回失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<HotCourse>>() {}.getType();
                        String json = response.body().string();
                        List<HotCourse> courses = gson.fromJson(json, type);
                        for (int i = 0; i < courses.size(); i++) {
                            Mulu mulu = new Mulu(R.drawable.videoshow,courses.get(i).getCourseTitle());
                            mulus.add(mulu);
                        }
                        Message message = Message.obtain();
                        message.what = 2;
                        courseHandler.sendMessage(message);
                    }
                });
            }
        }.start();
        Mulu mulu1 = new Mulu(R.drawable.videoshow,"开学第1课");
        Mulu mulu2 = new Mulu(R.drawable.videoshow,"开学第2课");
        Mulu mulu3 = new Mulu(R.drawable.videoshow,"开学第3课");
        Mulu mulu4 = new Mulu(R.drawable.videoshow,"开学第4课");
        Mulu mulu5 = new Mulu(R.drawable.videoshow,"开学第5课");
        mulus.add(mulu1);
        mulus.add(mulu2);
        mulus.add(mulu3);
        mulus.add(mulu4);
        mulus.add(mulu5);
    }
}