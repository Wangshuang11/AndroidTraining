package org.turings.turings.Fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.turings.turings.R;
import org.turings.turings.mistaken.LookUpAndErrorReDoActivity;
import org.turings.turings.mistaken.UploadWrongQuestionsActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class MistakenFragment extends Fragment {
    // 拍照回传码
    public final static int CAMERA_REQUEST_CODE = 0;
    // 相册选择回传吗
    public final static int GALLERY_REQUEST_CODE = 1;
    // 拍照的照片的存储位置
    private String mTempPhotoPath;
    // 照片所在的Uri地址
    private Uri imageUri;
    //裁剪图片保存的地址
    private String pathCropPhoto;
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
        // android 7.0系统解决拍照的问题（防止API24以上手机报错）
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
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
                takePhoto();

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

    private void takePhoto(){
        // 跳转到系统的拍照界面
        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 指定照片存储位置为sd卡本目录下
        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
        // 获取图片所在位置的Uri路径    *****这里为什么这么做参考问题2*****
        imageUri = Uri.fromFile(new File(mTempPhotoPath));
        //下面这句指定调用相机拍照后的照片存储的路径
        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intentToTakePhoto,CAMERA_REQUEST_CODE);
    }
    private void startCrop() {
        String dataFileStr = getContext().getFilesDir().getAbsolutePath()+"/";
        String fileName = System.currentTimeMillis() + ".jpg";
        pathCropPhoto = dataFileStr+fileName;
        Uri destinationUri = Uri.fromFile(new File(dataFileStr+fileName));
        UCrop uCrop = UCrop.of(imageUri, destinationUri);
        UCrop.Options options = new UCrop.Options();
        //设置裁剪图片可操作的手势
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
        //设置toolbar颜色
        options.setToolbarColor(ActivityCompat.getColor(getContext(),R.color.themeColor));
        //设置状态栏颜色
        options.setStatusBarColor(ActivityCompat.getColor(getContext(), R.color.themeColor));
        //是否能调整裁剪
        uCrop.withOptions(options);
        uCrop.start(getContext(),this);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //获得用户拍照上传的照片（裁剪后的），发送到错题上传详情页:UploadWrongQuestionsActivity
        if (resultCode == RESULT_OK) {
            switch (requestCode) {// 选择请求码
                case CAMERA_REQUEST_CODE:
                    try {
                        // 裁剪
                        startCrop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UCrop.REQUEST_CROP:
                    // 传送照片
                    final Uri croppedUri = UCrop.getOutput(data);
                    try {
                        if (croppedUri != null) {
                            Bitmap bit = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(croppedUri));
                            Intent intent = new Intent(getContext(), UploadWrongQuestionsActivity.class);
                            //将Bitmap对象读到字节数组中
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bit.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] datas = baos.toByteArray();
                            //删除裁剪保存的图片
                            deletePathFromFile(pathCropPhoto);
                            intent.putExtra("photo", datas);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case UCrop.RESULT_ERROR://裁剪失败响应
                    final Throwable cropError = UCrop.getError(data);
                    break;
            }
        }
    }
    //删除file目录下指定路径的图片
    private void deletePathFromFile(String pathCropPhoto) {
        File file = new File(pathCropPhoto);
        if (file.exists()) {
            file.delete();
        }
    }
}
