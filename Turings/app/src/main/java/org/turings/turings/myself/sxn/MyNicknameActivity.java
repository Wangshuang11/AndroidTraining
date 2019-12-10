package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.myself.tools.MyUrl;


public class MyNicknameActivity extends AppCompatActivity {

    private EditText editText;
    private Button button;
    private Button back;
    private Intent intent;
    private Bundle bundle;
    private MyUrl myUrl;
    public static int id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (getSupportActionBar() != null) { getSupportActionBar().hide(); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_mynickname);
        getViews();
        intent=getIntent();
        myUrl=new MyUrl(this);

        bundle=intent.getExtras();
        editText.setText(bundle.getString("beforeName"), TextView.BufferType.NORMAL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=editText.getText().toString();
                myUrl.sendToServerChange(getResources().getString(R.string.connUrl)+"/EditUname?uid="+id+"&uname="+name,R.layout.sxn_mynickname);
                intent.setClass(MyNicknameActivity.this, MainActivity.class);
                intent.putExtra("fragment",3);
                startActivityForResult(intent,13);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent=new Intent();
                intent.setClass(MyNicknameActivity.this, MainActivity.class);
                intent.setAction("loginBackMyself");
                startActivityForResult(intent,0);
            }
        });
    }
    private void getViews(){
        editText=findViewById(R.id.sxn_fixedNickname_edit);
        button=findViewById(R.id.sxn_fixedNickname_btn);
        back=findViewById(R.id.sxn_nickname_back);
    }
}
