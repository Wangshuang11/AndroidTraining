package org.turings.turings.mistaken;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.R;

import java.io.File;

public class ViewDownloadedPapersActivity extends AppCompatActivity {
    //展示
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_downloaded_papers);
        textView = findViewById(R.id.tv_show);
        Intent intent = getIntent();
        String path = intent.getStringExtra("path");
        textView.setText("下载好的试卷已经存在您手机的内部存储中的turings目录下，也可以自行查看，路径为sdcard"+path);
        //下面直接打开
        try{
            Intent intent2 =  getWordFileIntent("sdcard"+path);
            startActivity(intent2);
        }catch (Exception e){
            //没有安装第三方的软件会提示
            Toast toast = Toast.makeText(getApplicationContext(), "没有找到打开该文件的应用程序", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    //android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(String Path)
    {
        File file = new File(Path);
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }
}
