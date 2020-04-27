package org.turings.turings.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.login.LoginActivity;
import org.turings.turings.login.RegisterNewUserActivity;
import org.turings.turings.myself.sxn.AboutInfoActivity;
import org.turings.turings.myself.sxn.MyAchieveActivity;
import org.turings.turings.myself.sxn.MyConcernActivity;
import org.turings.turings.myself.sxn.MyCourseActivity;
import org.turings.turings.myself.sxn.MyFansActivity;
import org.turings.turings.myself.sxn.MyMottoActivity;
import org.turings.turings.myself.sxn.MyNicknameActivity;
import org.turings.turings.myself.sxn.MySchoolActivity;
import org.turings.turings.myself.tools.MyUrl;
import org.turings.turings.myself.tools.PhotoPopupWindow;
import org.turings.turings.myself.tools.SimplePopupWindow;
import org.turings.turings.myself.tools.UpLoadFileTask;
import org.turings.turings.near.Location.ShareTitleActivity;
import org.turings.turings.near.Location.WriteActivity;

import java.io.File;
import java.io.FileOutputStream;

import static android.content.Context.MODE_PRIVATE;


public class MyselfFragment extends Fragment {
    private Intent intent=new Intent();
    private Bundle bundle;
    private LinearLayout logged;
    private LinearLayout myfans;
    private LinearLayout myattention;
    private LinearLayout myachieve;
    private RelativeLayout myshare;
    private LinearLayout nameL;
    private LinearLayout mottoL;
    private RelativeLayout mycourse;
    private RelativeLayout school;
    private RelativeLayout track;
    private ImageView avatarI;
    private TextView nameT;
    private TextView mottoT;
    private TextView scoreT;
    private TextView fanCount;
    private TextView conCount;
    private TextView achCount;
    private MyListener myListener=new MyListener();
    private PopupWindow popWindow;
    private MyUrl myUrl;
    private Bitmap photo;
    private RequestOptions requestOptions;
    private PhotoPopupWindow mPhotoPopupWindow;

    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";

    private ImageView ivUnLogOfMyself_ws;//退出登录按钮
    private  LinearLayout parent_ws;//弹出窗口父视图
    private ImageView ivBackground_ws;//头像上方的封面图片
    private TextView tvJumpLogin_ws;//未登录界面的登录按钮
    private ImageView ivUnLogin_ws;//未登录界面的上方图片
    private TextView tvJumpRegister_ws;//未登录界面的注册按钮
    private View view;

    private int id;

    private SimplePopupWindow mPopupWindow;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //用户是否登录
        if(!checkUserIsLogin()){//未登录
            view = inflater.inflate(R.layout.sxn_activity_unlogged, container,false);
            //加载上方图片
            loadTopImg();
            //点击跳转按钮，跳到登录或注册界面
            jumpToLogin();
            return view;
        }

        //登录
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();
        view = inflater.inflate(R.layout.sxn_activity_logged, container,false);
        myUrl=new MyUrl(getContext());
        //初始化控件
        initController();

        //绑定事件监听器
        bindingListener();

        //无缝填充头像上方的封面
        centerCropCoverPhoto();

        //退出登录
        unLogin();
        //显示我的头像，我的昵称，关注数量，粉丝数量
        myUrl.sendToServerMyMessage(getResources().getString(R.string.connUrl)+"/ReFreshMyInfomation?uid="+id+"",
                R.layout.sxn_activity_logged,
                fanCount, conCount, achCount, nameT, mottoT, avatarI);
        Log.e("myurl",getResources().getString(R.string.connUrl)+"/ReFreshMyInfomation?uid="+id+"");
        if (photo!=null){
            circleCropCoverPhoto();
        }

        return view;
    }

    //退出登录
    private void unLogin() {
        ivUnLogOfMyself_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*//弹出PopupWindow  ：样式待改，和修改昵称的相同
                final PopupWindow popupWindow=new PopupWindow(getContext());
                popupWindow.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
                LayoutInflater layoutInflater=getLayoutInflater();
                View popupView=layoutInflater.inflate(R.layout.ws_popup_unlogin,null);
                Button btnCancel_ws=popupView.findViewById(R.id.btnCancel_ws);
                btnCancel_ws.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });
                Button btnUnLogin_ws=popupView.findViewById(R.id.btnUnLogin_ws);
                btnUnLogin_ws.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //清空个人登录信息
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("name","");
                        editor.putString("password","");
                        editor.putString("phone","");
                        editor.putString("uId","");
                        editor.commit();
                        //跳回我的fragment
                        popupWindow.dismiss();
                        Intent intent=new Intent(getContext(), MainActivity.class);
                        intent.setAction("loginBackMyself");
                        startActivity(intent);
                    }
                });
                popupWindow.setContentView(popupView);
                popupWindow.showAtLocation(parent_ws, Gravity.CENTER,0,0);*/

                //展示popup
                mPopupWindow = new SimplePopupWindow(getActivity(), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //清空个人登录信息
                        SharedPreferences sharedPreferences=getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
                        SharedPreferences.Editor editor=sharedPreferences.edit();
                        editor.putString("name","");
                        editor.putString("password","");
                        editor.putString("phone","");
                        editor.putString("uId","");
                        editor.commit();
                        //跳回我的fragment
                        mPopupWindow.dismiss();
                        Intent intent=new Intent(getContext(), MainActivity.class);
                        intent.setAction("loginBackMyself");
                        startActivity(intent);
                    }
                },"退出当前账号");
                View rootView1 = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
                mPopupWindow.showAtLocation(rootView1,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }

        });
    }

    //加载上方图片
    private void loadTopImg() {
        ivUnLogin_ws=view.findViewById(R.id.ivUnLogin_ws);
        requestOptions=new RequestOptions().circleCrop();
        Glide.with(getContext()).asGif().load(R.mipmap.myselfthinkingtwo).apply(requestOptions).into(ivUnLogin_ws);
    }

    //点击跳转按钮，跳到登录或注册界面
    private void jumpToLogin() {
        tvJumpLogin_ws= view.findViewById(R.id.tvJumpLogin_ws);
        tvJumpRegister_ws=view.findViewById(R.id.tvJumpRegister_ws);
        tvJumpLogin_ws.setOnClickListener(new View.OnClickListener() {//跳到登录界面
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
        tvJumpRegister_ws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), RegisterNewUserActivity.class);
                startActivity(intent);
            }
        });
    }

    //用户是否登录
    private boolean checkUserIsLogin() {
        SharedPreferences sharedPreferences=getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        String uName=sharedPreferences.getString("name","");
        String uTel=sharedPreferences.getString("phone","");
        if (uName.equals("") && uTel.equals("")){//用户名或者密码两个都为空，就是用户没登录
            return false;
        }else{//只要用户名或者密码有一个不为空，就是用户登录了
            id= Integer.parseInt(getContext().getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
            return true;
        }
    }

    //绑定事件监听器
    private void bindingListener() {
        logged.setOnClickListener(myListener);
        myachieve.setOnClickListener(myListener);
        myattention.setOnClickListener(myListener);
        myfans.setOnClickListener(myListener);
        myshare.setOnClickListener(myListener);
        school.setOnClickListener(myListener);
        mycourse.setOnClickListener(myListener);
        track.setOnClickListener(myListener);
        nameT.setOnClickListener(myListener);
        nameL.setOnClickListener(myListener);
        avatarI.setOnClickListener(myListener);
        mottoT.setOnClickListener(myListener);
        mottoL.setOnClickListener(myListener);
    }

    //无缝填充头像上方的封面
    private void centerCropCoverPhoto() {
        requestOptions=new RequestOptions().centerCrop();
        Glide.with(view).load(R.mipmap.myselfbackground).apply(requestOptions).into(ivBackground_ws);
    }

    //初始化控件
    private void initController() {
        ivUnLogOfMyself_ws=view.findViewById(R.id.ivUnLogOfMyself_ws);
        ivBackground_ws=view.findViewById(R.id.ivBackground_ws);
        logged=view.findViewById(R.id.sxn_log_linear);
        myachieve=view.findViewById(R.id.sxn_myachieve_linear);
        myattention=view.findViewById(R.id.sxn_myattention_linear);
        myfans=view.findViewById(R.id.sxn_myfans_linear);
        mycourse=view.findViewById(R.id.sxn_course_linear);
        myshare=view.findViewById(R.id.sxn_share_linear);

        school=view.findViewById(R.id.sxn_school_linear);
        track=view.findViewById(R.id.sxn_track_linear);
        //本页面我的头像
        avatarI= view.findViewById(R.id.sxn_avatar_img);
        //本页面我的昵称
        nameL=view.findViewById(R.id.sxn_nickname_linear);
        nameT=view.findViewById(R.id.sxn_nickname_text);
        //本页面我的motto
        mottoT=view.findViewById(R.id.sxn_motto_text);
        mottoL=view.findViewById(R.id.sxn_motto_linear);
        //粉丝数量
        fanCount= view.findViewById(R.id.sxn_fans_count);
        conCount=view.findViewById(R.id.sxn_concerns_count);
        achCount=view.findViewById(R.id.sxn_achieves_count);
        parent_ws=view.findViewById(R.id.parent_ws);
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.sxn_course_linear:
                    intent.setClass(getContext(), MyCourseActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_motto_text:
                    mPopupWindow = new SimplePopupWindow(getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 进入相册选择
                            mPopupWindow.dismiss();
                            intent.putExtra("beforeMotto", mottoT.getText().toString());
                            intent.setClass(getContext(), MyMottoActivity.class);
                            startActivityForResult(intent, 0);
                        }
                    },"修改座右铭");
                    View rootView0 = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
                    mPopupWindow.showAtLocation(rootView0,
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                case R.id.sxn_myachieve_linear:
                    intent.setClass(getContext(), MyAchieveActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_myfans_linear:
                    intent.setClass(getContext(), MyFansActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_myattention_linear:
                    intent.setClass(getContext(), MyConcernActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_school_linear:
                    intent.setClass(getContext(), MySchoolActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_avatar_img:
                    mPhotoPopupWindow = new PhotoPopupWindow(getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 进入相册选择
                            mPhotoPopupWindow.dismiss();
                            Intent intent = new Intent(Intent.ACTION_PICK);
                            intent.setType("image/*");
                            // 判断系统中是否有处理该 Intent 的 Activity
                            if (intent.resolveActivity(getContext().getPackageManager())!=null) {
                                startActivityForResult(intent, REQUEST_IMAGE_GET);
                            } else {
                                Toast.makeText(getContext(), "未找到图片查看器", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 拍照

                            mPhotoPopupWindow.dismiss();

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);

                        }
                    });
                    View rootView = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
                    mPhotoPopupWindow.showAtLocation(rootView,
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

                    break;
                case R.id.sxn_share_linear:
                    intent.setClass(getContext(), WriteActivity.class);
                    startActivity(intent);
                    break;
                case R.id.sxn_nickname_text:
                    //展示popup
                    mPopupWindow = new SimplePopupWindow(getActivity(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // 进入相册选择
                            mPopupWindow.dismiss();
                            intent.putExtra("beforeName", nameT.getText().toString());
                            intent.setClass(getContext(), MyNicknameActivity.class);
                            startActivityForResult(intent, 0);
                        }
                    },"修改昵称");
                    View rootView1 = LayoutInflater.from(getContext()).inflate(R.layout.activity_main, null);
                    mPopupWindow.showAtLocation(rootView1,
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
                case R.id.sxn_track_linear:
                    Intent about = new Intent();
                    about.setClass(getContext(), AboutInfoActivity.class);
                    startActivity(about);
                    break;
            }
        }

    }

    //讲头像显示为圆形
    private void circleCropCoverPhoto() {
//        requestOptions=new RequestOptions().circleCrop();
//        Glide.with(view).load(new BitmapDrawable(photo)).apply(requestOptions).into(avatarI);

    }

    private void showPopwindow(final int id) {

        View view0 = LayoutInflater.from(getContext()).inflate(R.layout.sxn_popup_nick, null, false);
        final Button btnChange = view0.findViewById(R.id.sxn_change);
        Button btnCancel = view0.findViewById(R.id.sxn_cancel);
        if (id==R.id.sxn_motto_text){
            btnChange.setText("修改我的座右铭");
        }
        popWindow = new PopupWindow(view0,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popWindow.setAnimationStyle(R.style.slide_anim);  //设置加载动画
        popWindow.setTouchable(true);
        popWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        popWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popWindow.showAtLocation(view0, Gravity.BOTTOM, 0, 0);
        //设置popupWindow里的按钮的事件
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.sxn_change:
                        intent = new Intent();
                        if (id==R.id.sxn_motto_text){
                            intent.putExtra("beforeMotto", mottoT.getText().toString());
                            intent.setClass(getContext(), MyMottoActivity.class);
                        }else {
                            intent.putExtra("beforeName", nameT.getText().toString());
                            intent.setClass(getContext(), MyNicknameActivity.class);
                        }
                        startActivityForResult(intent, 0);
                        break;
                    case R.id.sxn_cancel:
                        popWindow.dismiss();
                        break;
                }
            }
        };
        btnCancel.setOnClickListener(listener);
        btnChange.setOnClickListener(listener);
    }
    /**
     * 处理回调结果
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == -1) {
            switch (requestCode) {

                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    if (photo!=null){
                        circleCropCoverPhoto();
                    }
                    break;

                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        startSmallPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    if (photo!=null){
                        circleCropCoverPhoto();
                    }
                    break;

                // 拍照
                case REQUEST_IMAGE_CAPTURE:
                    File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                    startSmallPhotoZoom(Uri.fromFile(temp));
                    if (photo!=null){
                        circleCropCoverPhoto();
                    }
                    break;
            }
        }
    }
    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
    public void startSmallPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }
    /**
     * 小图模式中，保存图片后，设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            photo = extras.getParcelable("data"); // 直接获得内存中保存的 bitmap
            // 创建 smallIcon 文件夹
            File dirFile=null;
            String fileName=null;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                dirFile = new File(getContext().getFilesDir().getAbsolutePath());
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                    }
                }
//                Log.e("我要的路径", dirFile+"#####"+System.currentTimeMillis()+ ".jpg");
                fileName = System.currentTimeMillis() + ".jpg";
                File file = new File(getContext().getFilesDir().getAbsolutePath(), fileName);
                // 保存图片


                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            Log.e("log",photo.toString());
            avatarI.setImageBitmap(photo);

            String filePath =dirFile+"/"+fileName;

            Log.e("路径2", dirFile+"/"+fileName);

//            Log.e("路径1", getFilesDir().getAbsolutePath()+"/d8f9d72a6059252dfd030078349b033b5bb5b9b1.jpg");
//                    getFilesDir().getAbsolutePath()+"/d8f9d72a6059252dfd030078349b033b5bb5b9b1.jpg";
//            String filePath =getFilesDir().getAbsolutePath()+"/d8.jp  g";
            UpLoadFileTask task = new UpLoadFileTask(
                    getActivity(),
                    filePath
            );
            //开始执行异步任务
            task.execute(getResources().getString(R.string.connUrl)+"/InputAvatar?id="+id);





        }
    }

}
