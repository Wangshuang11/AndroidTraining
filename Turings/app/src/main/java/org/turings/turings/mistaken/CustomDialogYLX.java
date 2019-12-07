package org.turings.turings.mistaken;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.turings.turings.R;
import org.turings.turings.mistaken.SubjectMsg;

public class CustomDialogYLX extends DialogFragment {
    private SubjectMsg subjectMsgDa;//需上传的数据
    private OkHttpClient okHttpClient;
    private Response response;//响应
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_dialog_commit_layout_ylx,container,false);//布局，父视图，是否立刻加载
        Button btnOk  = view.findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击确定按钮执行的操作
                //关闭activity对象
                //将信息存入数据库，并跳转到添加错题页
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        uploadToDataBase(subjectMsgDa);
                    }
                }).start();
                Intent intent = new Intent();
                intent.setClass(getActivity(),LookUpAndErrorReDoActivity.class);
                intent.setAction("mistake");
                startActivity(intent);
                getActivity().finish();//获得加载CustomDialog的activity
            }
        });
        Button btnCancel = view.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击取消按钮执行的操作
                getDialog().dismiss();//关闭当前的对话框
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        // WindowManager 接口的嵌套类
        WindowManager.LayoutParams windowParams = window.getAttributes();
        //设置弹出框周围的透明度
        windowParams.dimAmount = 0.5f;
        //设置弹出框内容的透明度
        windowParams.alpha = 1f;
        //设置弹出框距离上面的距离
        windowParams.y = 100;
        window.setBackgroundDrawableResource(R.drawable.dialog_stroke_layout_ylx);
        windowParams.width = 900;// 调整该值可以设置对话框显示的宽度
        window.setAttributes(windowParams);
        //DisplayMetrics类 获取手机显示屏的基本信息 包括尺寸、密度、字体缩放等信息
        DisplayMetrics dm = new DisplayMetrics();
        //将屏幕尺寸赋给dm
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        //设置弹出框的高度和宽度
    }
    public void subjectMsgData(SubjectMsg subjectMsg){
        subjectMsgDa = subjectMsg;
    }

    //访问服务器上传至数据库
    private void uploadToDataBase(SubjectMsg subjectMsg) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        String subject = gson.toJson(subjectMsg);
        okHttpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=utf-8"),subject);
        String url = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/UploadWrongQuestionsServlet";
//        String url = "http://192.168.2.142:8080/Turings/UploadWrongQuestionsServlet";
        Request request = new Request.Builder().post(requestBody).url(url).build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("lww", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.i("lww", response.body().string());
                    }
                });
            }
        }).start();
    }
}
