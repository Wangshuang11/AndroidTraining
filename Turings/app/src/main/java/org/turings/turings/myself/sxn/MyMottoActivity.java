package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.turings.turings.R;


//修改我的座右铭，将修改后的传入数据库,在修改之前，传入之前的文字
public class MyMottoActivity extends AppCompatActivity {

    private Intent intent;
    private EditText editText;
    private Button button;
    private Button back;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_mymotto);
        getViews();
        intent=getIntent();
        bundle=intent.getExtras();
        editText.setText(bundle.getString("beforeMotto"), TextView.BufferType.NORMAL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String motto=editText.getText().toString();
                bundle.putString("afterMotto",motto);
                intent.putExtras(bundle);
                intent.setClass(MyMottoActivity.this,PersonalDataActivity.class);
                startActivityForResult(intent,0);
                //将修改后的数据传到数据库，并通过toast提示或直接跳到前一个界面
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyMottoActivity.this, PersonalDataActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getViews(){
        editText=findViewById(R.id.sxn_fixmotto_edit);
        button=findViewById(R.id.sxn_fixedmotto_btn);
        back=findViewById(R.id.sxn_motto_back);
    }
}
