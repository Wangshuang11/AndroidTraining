package org.turings.turings;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class UrlActivity extends AppCompatActivity {
    private WebView wv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        wv = findViewById(R.id.wv);
        /*
        加上报错，删除
        if(NavUtils.getParentActivityName(UrlActivity.this)!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
        Intent intent = getIntent();
        String urlStr = intent.getStringExtra("urlStr");
        wv.loadUrl(urlStr);
    }
}
