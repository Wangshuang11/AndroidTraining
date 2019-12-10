package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.myself.tools.MyUrl;


//修改我的座右铭，将修改后的传入数据库,在修改之前，传入之前的文字
public class MyMottoActivity extends AppCompatActivity {

    private Intent intent;
    private EditText editText;
    private Button button;
    private Button back;
    private Bundle bundle;
    private MyUrl myUrl;
    private int id;
    private String info;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case R.layout.sxn_mymotto:
                    info= (String) msg.obj;
                    //返回值为true，则修改成功
                    if (info=="true"){
                        Toast.makeText(getApplicationContext(),"更改成功",Toast.LENGTH_SHORT);
                    }else {
                        Toast.makeText(getApplicationContext(),"更改失败",Toast.LENGTH_SHORT);
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_mymotto);
        //姑且先令id=1
        id=1;
        getViews();
        myUrl=new MyUrl(this);
        intent=getIntent();
        bundle=intent.getExtras();
        editText.setText(bundle.getString("beforeMotto"), TextView.BufferType.NORMAL);
        //将修改后的数据传到数据库，并直接跳到前一个界面
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String motto=editText.getText().toString();
                //去修改数据库
                myUrl.sendToServerChange(getResources().getString(R.string.connUrl)+"/EditMotto?uid="+id+"&umotto="+motto,R.layout.sxn_mymotto);
                Log.e("座右铭",getResources().getString(R.string.connUrl)+"/EditMotto?uid="+id+"&umotto="+motto);
                intent.setClass(MyMottoActivity.this, MainActivity.class);
                intent.setAction("loginBackMyself");
                startActivityForResult(intent,13);


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyMottoActivity.this, MainActivity.class);
                intent.putExtra("fragment",3);
                startActivityForResult(intent,13);
            }
        });
    }
    private void getViews(){
        editText=findViewById(R.id.sxn_fixmotto_edit);
        button=findViewById(R.id.sxn_fixedmotto_btn);
        back=findViewById(R.id.sxn_motto_back);
    }
}
