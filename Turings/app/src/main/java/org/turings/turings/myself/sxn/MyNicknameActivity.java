package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.turings.turings.R;


public class MyNicknameActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private Button back;
    private Intent intent;
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_mynickname);
        getViews();
        intent=getIntent();
        bundle=intent.getExtras();
        editText.setText(bundle.getString("beforeName"), TextView.BufferType.NORMAL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText.getText().toString();
                bundle.putString("afterName",name);
                intent.putExtras(bundle);
                intent.setClass(MyNicknameActivity.this,PersonalDataActivity.class);
                startActivityForResult(intent,0);
                //将修改后的数据传到数据库，并通过toast提示或直接跳到前一个界面
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyNicknameActivity.this, PersonalDataActivity.class);
                startActivity(intent);
            }
        });
    }
    private void getViews(){
        editText=findViewById(R.id.sxn_fixedNickname_edit);
        button=findViewById(R.id.sxn_fixedNickname_btn);
        back=findViewById(R.id.sxn_nickname_back);
    }
}
