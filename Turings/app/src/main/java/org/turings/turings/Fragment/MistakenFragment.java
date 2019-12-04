package org.turings.turings.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.LookUpAndErrorReDoActivity;
import org.turings.turings.mistaken.UploadWrongQuestionsActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MistakenFragment extends Fragment {
    private ImageView ivFootTop_ws;//上部脚印
    private ImageView ivFootMiddle_ws;//中部脚印
    private ImageView ivFootBottom_ws;//底部脚印
    private ImageView wsIvCamera;//中部相机按钮
    private TextView tvLizhi_ws;//底部励志的话
    private ImageView gifIv_ws;//底部动态logo
    private List<String> lists;//励志名言集合
    private Button btnSeeAll;//查看全部错题
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    tvLizhi_ws.setText(lists.get((Integer) msg.obj));
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_mistaken,container,false);
        ivFootTop_ws=view.findViewById(R.id.ivFootTop_ws);
        ivFootMiddle_ws=view.findViewById(R.id.ivFootMiddle_ws);
        ivFootBottom_ws=view.findViewById(R.id.ivFootBottom_ws);
        wsIvCamera=view.findViewById(R.id.ivCamera_ws);
        tvLizhi_ws=view.findViewById(R.id.tvLizhi_ws);
        gifIv_ws=view.findViewById(R.id.gifIv_ws);
        btnSeeAll = view.findViewById(R.id.btnSeeAll_ws);

        //点击查看全部错题
        btnSeeAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LookUpAndErrorReDoActivity.class);
                startActivity(intent);
            }
        });
        //初始化励志名言集合
        initLizhiList();

        //点击中部相机按钮，调用手机照相机
        wsIvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用手机照相机
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,8888);

            }
        });

        //添加底部logo动画
        addBottomGifLogo();

        //动态输出底部励志的话
        addTextOnBubble();

        //动态化脚印
        dynamicFoot();
        return view;
    }

    //动态化脚印
    private void dynamicFoot() {
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(ivFootBottom_ws,"alpha",1,0,1);
        objectAnimator.setDuration(3000);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();

        ObjectAnimator objectAnimator2=ObjectAnimator.ofFloat(ivFootTop_ws,"alpha",1,0,1);
        objectAnimator2.setDuration(3000);
        objectAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator2.setStartDelay(2000);
        objectAnimator2.start();

        ObjectAnimator objectAnimator3=ObjectAnimator.ofFloat(ivFootMiddle_ws,"alpha",1,0,1);
        objectAnimator3.setDuration(3000);
        objectAnimator3.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator3.setStartDelay(1000);
        objectAnimator3.start();
    }


    //初始化励志名言集合
    private void initLizhiList() {
        lists=new ArrayList<>();
        lists.add("千里之行，始于足下。");
        lists.add("恰同学少年，风华正茂。");
        lists.add("山重水复疑无路，柳暗花明又一村");
        lists.add("业精于勤,荒于嬉;行成于思,毁于随");
        lists.add("不积跬步，无以至千里；不积小流，无以成江海");
    }

    //添加底部logo动画
    private void addBottomGifLogo() {
        AnimationDrawable animationDrawable= (AnimationDrawable) gifIv_ws.getDrawable();
        animationDrawable.start();
    }

    //动态输出底部励志的话
    private void addTextOnBubble() {
        new Thread(){
            @Override
            public void run() {
                try {
                    for(int i=0;i<lists.size();i++){
                        //每隔两秒改变一句话
                        sleep(5000);
                        Message message=new Message();
                        message.obj=i;
                        message.what=100;
                        handler.sendMessage(message);
                        if (i == lists.size()-1){
                            i=-1;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获得用户拍照上传的照片，发送到错题上传详情页:UploadWrongQuestionsActivity
        if (requestCode == 8888 && resultCode == RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            Intent intent = new Intent(getContext(), UploadWrongQuestionsActivity.class);
            //将Bitmap对象读到字节数组中
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] datas = baos.toByteArray();
            intent.putExtra("photo", datas);
            startActivity(intent);
        }
    }
}
