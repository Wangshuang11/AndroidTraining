package org.turings.turings.near.comment;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.turings.turings.R;
import org.turings.turings.near.comment.adapter.CommentExpandAdapter;
import org.turings.turings.near.comment.bean.CommentBean;
import org.turings.turings.near.comment.bean.CommentDetailBean;
import org.turings.turings.near.comment.bean.ReplyDetailBean;
import org.turings.turings.near.comment.view.CommentExpandableListView;
import org.turings.turings.near.entity.Information;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class JumpActivity extends AppCompatActivity {

    private Handler handler;
    private String textIson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jump);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                textIson =  msg.obj.toString();
            }
        };
        sendToServer();
        Log.e("lyh",textIson+"aaaaaaaa");
    }

    public void sendToServer() {

        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/lyh/showComment?sId=1");
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
