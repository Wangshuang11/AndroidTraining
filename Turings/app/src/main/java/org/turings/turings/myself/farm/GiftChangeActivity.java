package org.turings.turings.myself.farm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.turings.turings.R;

public class GiftChangeActivity extends AppCompatActivity {
    private LinearLayout linear;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_farm_gift);
        getViews();
//        linear.setBackgroundColor(Color.argb(155, 0, 0, 0));
        //OK按钮进行的操作
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"信息获取成功！",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void getViews(){
        linear=findViewById(R.id.sxn_gift_linear);
        button=findViewById(R.id.sxn_gift_ok_btn);
    }
}
