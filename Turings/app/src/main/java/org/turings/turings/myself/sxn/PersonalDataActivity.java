package org.turings.turings.myself.sxn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.turings.turings.Fragment.MyselfFragment;
import org.turings.turings.R;


/*
已登录界面之后，点击查看我的个人信息，进入此页面点击可以对我的信息修改

 */
public class PersonalDataActivity extends AppCompatActivity {

    private ImageView imgV;
    private TextView nameT;
    private TextView mottoT;
    private Button btnN;
    private Button btnM;
    private Button back;
    private Intent intentW;
    private Intent intent;
    private MyListener listener;
    private Bundle bundle1;
    private Bundle bundle2;
    private Intent intent1;
    private Intent intent2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sxn_activity_personaldata);
        intent1=getIntent();
        intent2=getIntent();
        intent=new Intent();
        listener=new MyListener();
        getViews();
        bundle1=intent1.getExtras();
        bundle2=intent2.getExtras();
        if (bundle1!=null){
            mottoT.setText(bundle1.getString("afterMotto"));
        }
        if (bundle2!=null){
            nameT.setText(bundle2.getString("afterName"));
        }

    }
    private  void getViews(){
        imgV=findViewById(R.id.sxn_personal_img);
        imgV.setOnClickListener(listener);
        nameT=findViewById(R.id.sxn_personal_name);
        mottoT=findViewById(R.id.sxn_personal_motto);
        btnM=findViewById(R.id.sxn_fixmotto_btn);
        btnM.setOnClickListener(listener);
        btnN=findViewById(R.id.sxn_fixnickname_btn);
        btnN.setOnClickListener(listener);
        back=findViewById(R.id.sxn_personal_back);
        back.setOnClickListener(listener);
    }
    class MyListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sxn_fixmotto_btn:
                    String motto=mottoT.getText().toString().trim();
                    intent.setClass(PersonalDataActivity.this,MyMottoActivity.class);
                    intent.putExtra("beforeMotto",motto);
                    startActivityForResult(intent,1);
                    break;
                case R.id.sxn_fixnickname_btn:
                    String name=nameT.getText().toString().trim();
                    intent.setClass(PersonalDataActivity.this,MyNicknameActivity.class);
                    intent.putExtra("beforeName",name);
                    startActivityForResult(intent,1);
                    break;
                case R.id.sxn_personal_back:
                    intent=new Intent();
                    intent.setClass(PersonalDataActivity.this, MyselfFragment.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_personal_img:
                    //选择拍摄照片或者从相册找照片
                    break;
            }
        }
    }
}
