package org.turings.turings.mistaken.myReview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import org.turings.turings.MainActivity;
import org.turings.turings.R;

public class MathMistakenReportActivity extends AppCompatActivity {
    private ImageView ivBack_ws;//返回
    private CustomOnclickListener listener;//事件监听器

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_mistaken_report);

        //初始化控件
        initController();

        //绑定事件监听器
        registerListener();
    }

    //初始化控件
    private void initController() {
        ivBack_ws=findViewById(R.id.ivBack_ws);
    }

    //绑定事件
    private void registerListener() {
        listener = new CustomOnclickListener();
        ivBack_ws.setOnClickListener(listener);
    }

    //点击事件
    public class CustomOnclickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivBack_ws://点击返回
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setAction("mistake");
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    }
}
