package org.turings.turings.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.sxt.library.chart.ChartPie;
import com.sxt.library.chart.bean.ChartPieBean;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import org.turings.turings.R;
import org.turings.turings.login.LoginActivity;
import org.turings.turings.login.RegisterNewUserActivity;
import org.turings.turings.mistaken.AutoGeneratingPaperYLXActivity;
import org.turings.turings.mistaken.LookUpAndErrorReDoActivity;
import org.turings.turings.mistaken.StatisticsResult;
import org.turings.turings.mistaken.UploadWrongQuestionsActivity;
import org.turings.turings.mistaken.entity.IdentifyResultBean;
import org.turings.turings.mistaken.entity.Words_result;
import org.turings.turings.mistaken.util.FileUtil;
import org.turings.turings.mistaken.util.RecognizeService;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;


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
    private PopupWindow popupWindow;//弹出框选择相机或相册
    private ImageView wsIvCamera;////拍照上传
    private TextView tvLizhi_ws;//底部励志的话
    private List<String> lists;//励志名言集合
    private ImageView btnSeeAll;//查看错题
    private List<ChartPieBean> pieBeanList;//饼图数据集合
    private LinearLayout lineLayoutList;//饼图线性布局
    private ImageView ivClimbMountain_ws;//顶部背景图
    private View view;
    private TextView tvInfo_ws;//错题以及组卷信息控件
    private String num;//用户添加错题的数量
    /*private StatisticsResult statisticsResult;//后台分类统计错题数量结果
    private String statisticsResultStr;//后台分类统计错题数量结果字符串*/
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 100:
                    tvLizhi_ws.setText(lists.get((Integer) msg.obj));
                    break;
                case 101:
                    tvInfo_ws.setText("已添加错题 "+num+" 道   已组 0 套试卷");
                    break;
                /*case 102:
                    Gson gson=new Gson();
                    statisticsResult=gson.fromJson(statisticsResultStr,StatisticsResult.class);
                    //饼图数据
                    initData(statisticsResult.getBigQuestion(),statisticsResult.getChoiceQuestion(),statisticsResult.getFillBlankQuestion());
                    //开始画饼图
                    drawPie();
                    break;*/
            }
        }
    };
    private int uId;//用户在用户表的id
    private TextView tvJumpLogin_ws;//未登录界面的登录按钮
    private ImageView ivUnLogin_ws;//未登录界面的上方图片
    private TextView tvJumpRegister_ws;//未登录界面的注册按钮
    private ImageView autoPaper_ylx;//自主组卷图标

    private static final int REQUEST_CODE_ACCURATE_BASIC = 107;//百度云文字识别响应码
    private boolean hasGotToken = false;//百度云文字识别的ak,sk是否有效
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //用户是否登录
        /*if(!checkUserIsLogin()){//未登录
            view = inflater.inflate(R.layout.sxn_activity_unlogged, container,false);
            //加载上方图片
            loadTopImg();
            //点击跳转按钮，跳到登录或注册界面
            jumpToLogin();
            return view;
        }*/

        // android 7.0系统解决拍照的问题（防止API24以上手机报错）
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        view=inflater.inflate(R.layout.activity_mistaken,container,false);

        //初始化控件
        initController();

        //无缝填充顶部图片
        loadImgOnTop();

        //饼图数据
        initData();

        //开始画饼图
        drawPie();

        //初始化励志名言集合
        initLizhiList();

        //动态输出中部励志的话
        addTextOnBubble();

        //点击查看全部错题
        btnSeeAll.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LookUpAndErrorReDoActivity.class);
                startActivity(intent);
            }
        });

        //点击中部相机按钮，调用手机照相机
//        wsIvCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopwindow();
//            }
//        });

        //点击中部相机按钮，调用手机照相机:改进：百度云拍照识别
        //(自带照片剪切，照片暂存files里，平方识别不了)
        wsIvCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkTokenStatus()) {
                    return;
                }
                Intent intent = new Intent(getContext(), CameraActivity.class);
                intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                        FileUtil.getSaveFile(getContext()).getAbsolutePath());
                intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                        CameraActivity.CONTENT_TYPE_GENERAL);
                startActivityForResult(intent, REQUEST_CODE_ACCURATE_BASIC);
            }
        });
        initAccessTokenWithAkSk();

        //后台统计已添加错题的数量
        countMyMistaken();

        /*//后台分类统已添加错题数量
        statisticsWrongQuestionsResult();*/
        //跳转到自主组卷
        autoPaper_ylx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AutoGeneratingPaperYLXActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    /*//后台分类统已添加错题数量
    private void statisticsWrongQuestionsResult() {
        new Thread(){
            @Override
            public void run() {
                OkHttpClient okHttpClient=new OkHttpClient();
                //post-FormBody传输，在一定程度上保证用户信息的安全
                FormBody formBody=new FormBody.Builder()
                        .add("uId", String.valueOf(uId))
                        .build();
                Request request=new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/StatisticsResultServlet").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    statisticsResultStr=call.execute().body().string();
                    Message message=new Message();
                    message.obj=statisticsResultStr;
                    message.what=102;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }*/

    //后台统计已添加错题的数量
    private void countMyMistaken() {
        new Thread(){
            @Override
            public void run() {
                OkHttpClient okHttpClient=new OkHttpClient();
                //post-FormBody传输，在一定程度上保证用户信息的安全
                FormBody formBody=new FormBody.Builder()
                        .add("uId", String.valueOf(uId))
                        .build();
                Request request=new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/CountAllWrongQuestionsServlet").post(formBody).build();
                Call call = okHttpClient.newCall(request);
                try {
                    num=call.execute().body().string();
                    Message message=new Message();
                    message.obj=num;
                    message.what=101;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //初始化控件
    private void initController() {
//        uId= Integer.parseInt(getContext().getSharedPreferences("userInfo",MODE_PRIVATE).getString("uId","0"));
        uId=4;
        lineLayoutList = view.findViewById(R.id.line_layout_list);
        ivClimbMountain_ws=view.findViewById(R.id.ivClimbMountain_ws);
        tvLizhi_ws=view.findViewById(R.id.tvLizhi_ws);
        wsIvCamera=view.findViewById(R.id.ivCamera_ws);
        btnSeeAll=view.findViewById(R.id.btnSeeAll_ws);
        tvInfo_ws=view.findViewById(R.id.tvInfo_ws);
        autoPaper_ylx=view.findViewById(R.id.autoPaper_ylx);
    }

    //无缝填充顶部图片
    private void loadImgOnTop() {
        RequestOptions requestOptions=new RequestOptions().centerCrop();
        Glide.with(getContext()).load(R.mipmap.mistakenlizhi).apply(requestOptions).into(ivClimbMountain_ws);
    }

//    //饼图数据
//    private void initData(int bigQuestion,int choiceQuestion,int fillBlankQuestion) {
//        Log.e("hhhhhhhhhhhhhhhhhhh",bigQuestion+","+choiceQuestion+","+fillBlankQuestion);
//        pieBeanList = new ArrayList<>();
//        int num=(bigQuestion+choiceQuestion+fillBlankQuestion)/78;
//        pieBeanList.add(new ChartPieBean(bigQuestion*num, "选择", R.color.colorMistakenTwo));
//        pieBeanList.add(new ChartPieBean(15, "综合", R.color.colorAccent));
//        pieBeanList.add(new ChartPieBean(7, "判断", R.color.colorBlue));
//        pieBeanList.add(new ChartPieBean(fillBlankQuestion*num, "填空", R.color.colorMistakenOne));
//        pieBeanList.add(new ChartPieBean(bigQuestion*num, "简答",R.color.colorPrimary));
//    }
    //饼图数据
    private void initData() {
        pieBeanList = new ArrayList<>();
        pieBeanList.add(new ChartPieBean(23, "选择", R.color.colorMistakenTwo));
        pieBeanList.add(new ChartPieBean(15, "综合", R.color.colorAccent));
        pieBeanList.add(new ChartPieBean(7, "判断", R.color.colorBlue));
        pieBeanList.add(new ChartPieBean(32, "填空", R.color.colorMistakenOne));
        pieBeanList.add(new ChartPieBean(23, "简答",R.color.colorPrimary));
    }

    //开始画饼图
    private void drawPie() {
        View childAt = View.inflate(getContext(), R.layout.item_chart_pie, null);
        lineLayoutList.removeAllViews();
        lineLayoutList.addView(childAt);
        ChartPie chartPie = childAt.findViewById(R.id.chart_pie);
        chartPie.setData(pieBeanList).start();
        chartPie.start();
    }

    //初始化励志名言集合
    private void initLizhiList() {
        lists=new ArrayList<>();
        lists.add("千里之行，始于足下");
        lists.add("恰同学少年，风华正茂");
        lists.add("有志者，事竟成");
        lists.add("业精于勤，荒于嬉");
        lists.add("不积跬步，无以至千里");
        lists.add("知己知彼，百战不殆");
    }

    //动态输出底部励志的话
    private void addTextOnBubble() {
        new Thread(){
            @Override
            public void run() {
                try {
                    for(int i=0;i<lists.size();i++){
                        //每隔5秒改变一句话
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

    private void showPopwindow() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.select_popup_layout_ylx, null, false);
        popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //获取弹出窗布局当中某个控件的引用
        Button button = view.findViewById(R.id.btn_cancel);
        //用相机拍照
        Button button1 = view.findViewById(R.id.btn_takephoto);
        //从相册中选取
        Button button2 = view.findViewById(R.id.btn_gallery);
        addBackground();
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));    //要为popWindow设置一个背景才有效
        //设置popupWindow显示的位置，参数依次是参照View，x轴的偏移量，y轴的偏移量
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        //设置popupWindow里的按钮的事件
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_takephoto:
                        //用相机拍照
                        takePhoto();
                        break;
                    case R.id.btn_gallery:
                        choosePhoto();
                        break;
                    case R.id.btn_cancel:
                        //关闭弹出窗口
                        popupWindow.dismiss();
                        break;
                }
            }
        };
        button.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
    }
    //给popupwindow添加透明背景
    private void addBackground(){
        //设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        //调节透明度
        lp.alpha=0.5f;
        getActivity().getWindow().setAttributes(lp);
        //dismiss恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha=1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }
    //从相册中选择
    public void choosePhoto() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);//打开图库
        //REQUEST_PICTURE_CHOOSE表示请求参数，是个常量
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
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

        // 识别成功回调，通用文字识别（高精度版）:数学题不可以识别平方
        //图片存储路径(files文件里)：/files/pic.jpg
        //以为名字都为pic.jpg，拍多张会替换为一张
        if (requestCode == REQUEST_CODE_ACCURATE_BASIC && resultCode == Activity.RESULT_OK) {
            RecognizeService.recAccurateBasic(getContext(), FileUtil.getSaveFile(getContext()).getAbsolutePath(),
                    new RecognizeService.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            infoPopText(result);
                            //将识别内容json串转换为java类
                            //并将识别内容发送到错题上传详情页:UploadWrongQuestionsActivity
                            Gson gson=new Gson();
                            IdentifyResultBean identifyResultBean=gson.fromJson(result, IdentifyResultBean.class);
                            Intent intent = new Intent(getContext(), UploadWrongQuestionsActivity.class);
                            for (Words_result words_result:identifyResultBean.getWords_result()){
                                Log.e("识别内容",words_result.getWords());
                                if(words_result.getWords().startsWith("A")){
                                    intent.putExtra("A",words_result.getWords());
                                }else if(words_result.getWords().startsWith("B")){
                                    intent.putExtra("B",words_result.getWords());
                                }else if(words_result.getWords().startsWith("C")){
                                    intent.putExtra("C",words_result.getWords());
                                }else if(words_result.getWords().startsWith("D")){
                                    intent.putExtra("D",words_result.getWords());
                                }else{
                                    intent.putExtra("题干",words_result.getWords());
                                }
                            }
                            startActivity(intent);
                        }
                    });
        }

        //获得用户拍照上传的照片（裁剪后的），发送到错题上传详情页:UploadWrongQuestionsActivity
        /*if (resultCode == RESULT_OK) {
            switch (requestCode) {// 选择请求码
                case CAMERA_REQUEST_CODE:
                    try {
                        // 裁剪
                        startCrop();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case GALLERY_REQUEST_CODE://打开相册
                    imageUri = data.getData();
                    startCrop();
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
                            popupWindow.dismiss();
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
        }*/
    }
    //删除file目录下指定路径的图片
    private void deletePathFromFile(String pathCropPhoto) {
        File file = new File(pathCropPhoto);
        if (file.exists()) {
            file.delete();
        }
    }

    //加载上方图片
    private void loadTopImg() {
        ivUnLogin_ws=view.findViewById(R.id.ivUnLogin_ws);
        RequestOptions requestOptions=new RequestOptions().circleCrop();
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
            return true;
        }
    }

    /**
     * 检测ak，sk
     */
    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    /**
     * 用明文ak，sk初始化
     */
    private void initAccessTokenWithAkSk() {
        OCR.getInstance(getContext()).initAccessTokenWithAkSk(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                alertText("AK，SK方式获取token失败", error.getMessage());
            }
        }, getContext(),  "xdytmNvSVub4BM9Ad5BCnQaM", "2DqHkKCKETgQVyZG70XsovMbc369ox3P");
    }

    /**
     * 百度文字识别弹框
     */
    private void alertText(final String title, final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity().getApplicationContext(), "title"+title+";massage"+message, Toast.LENGTH_LONG).show();
            }
        });

    }

    /**
     * 百度文字识别弹框
     */
    private void infoPopText(final String result) {
        alertText("", result);
    }

    /**
     * 百度识别释放内存资源
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        OCR.getInstance(getContext()).release();
    }
}
