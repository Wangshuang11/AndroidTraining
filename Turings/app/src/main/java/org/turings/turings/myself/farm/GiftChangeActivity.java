package org.turings.turings.myself.farm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.R;
import org.turings.turings.myself.entity.Gift;
import org.turings.turings.myself.tools.MyUrl;
import org.w3c.dom.Text;

public class GiftChangeActivity extends AppCompatActivity {
    private int id;//全局id
    private int type=0;
    private LinearLayout linear;
    private Button button;
    private MyUrl myUrl;

    private EditText name;
    private EditText addr;
    private EditText phone;
    private TextView typeTxt;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_farm_gift);
        getViews();
//        linear.setBackgroundColor(Color.argb(175, 0, 0, 0));
        type=getIntent().getIntExtra("info",0);
        getInfo();

        //OK按钮进行的操作
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText()==null || addr.getText()==null || phone.getText()==null){
                    Toast.makeText(getApplicationContext(),"请完善信息！",Toast.LENGTH_LONG).show();
                }else {
                    toServer();
                    Toast.makeText(getApplicationContext(),"信息获取成功！",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    //获取收货信息
    private void getInfo(){
        switch (type){
            case 1:
                typeTxt.setText("'小号礼物'");
                break;
            case 2:
                typeTxt.setText("'精美礼物'");
                break;
            case 3:
                typeTxt.setText("'大号礼物'");
                break;
        }

    }
    private void toServer(){
        myUrl=new MyUrl(this);
        id=Integer.parseInt(getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        //添加一个gift数据同时将成长值清空
        myUrl.sendToServerChange(getResources().getString(R.string.connUrl)+"/ChangeGift?uid="+id+
                "&name="+name.getText().toString().trim()+"&addr="+addr.getText().toString().trim()+
                "&gid="+type+"&phone="+phone.getText().toString().trim(),R.layout.sxn_mynickname);
    }
    private void getViews(){
        typeTxt=findViewById(R.id.sxn_gift_type);
        name=findViewById(R.id.sxn_gift_nameEdit);
        addr=findViewById(R.id.sxn_gift_addrEdit);
        phone=findViewById(R.id.sxn_gift_phoneEdit);
        linear=findViewById(R.id.sxn_gift_linear);
        button=findViewById(R.id.sxn_gift_ok_btn);
    }
}
