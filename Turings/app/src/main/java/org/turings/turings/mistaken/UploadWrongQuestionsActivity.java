package org.turings.turings.mistaken;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;
import com.yayandroid.rotatable.Rotatable;

import org.turings.turings.MainActivity;
import org.turings.turings.R;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomDialogYLX;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UploadWrongQuestionsActivity extends AppCompatActivity {

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
    private String dataFileStr;//file绝对路径
    private ImageView back_ylx;
    private ImageView delete_ylx;//错题图片删除按钮
    private ImageView question_img_ylx;//错题图片
    private Button chinese_ylx;//语文学科选择按钮
    private Button math_ylx;//数学学科选择按钮
    private Button english_ylx;//英语学科选择按钮
    private Spinner spinner_ylx;//标签下拉
    private Button choose_ylx;//选择题选择按钮
    private Button fill_ylx;//填空题选择按钮
    private Button big_question_ylx;//大题选择按钮

    //难度选择按钮
    private Button easy_ws;
    private Button middle_ws;
    private Button difficult_ws;

    //掌握程度选择按钮
    private Button notMastered_ws;
    private Button mastered_ws;
    private Button skilled_ws;

    //错误原因选择按钮
    private Button confusedIdeas_ws;
    private Button wrongThinking_ws;
    private Button arithmeticError_ws;
    private Button examinationError_ws;

    private LinearLayout answer_ylx;//选择题答案书写框架
    private EditText option_anwser_ylx;//选择题答案书写
    private LinearLayout answer_big_ylx;//大题答案书写框架
    private EditText anwser_edt_ylx;//大题答题EditText
    private Button add_wrong_questions_ylx;//添加错题确认按钮
    private CustomOnclickListener listener;//事件监听器
    private ViewGroup.LayoutParams ap;//选择题答题框layoutparam
    private ViewGroup.LayoutParams lp;//大题答题框layoutparam
    private TextView question_content_ylx;//“题面”二字
    private static String[] tags;
    private List<String> list;//存tag
    private ArrayAdapter<String> tagAdapter;
    private String path;//图片存储的路径
    private SubjectMsg subjectMsg;//上传的题目
    private Bitmap photo;//相机拍下的照片
    private String uId;//用户的id
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    tags = getResources().getStringArray(R.array.spinnerChinese);
                    notifyList();
                    break;
                case 200:
                    tags = getResources().getStringArray(R.array.spinner);
                    notifyList();
                    break;
                case 300:
                    tags = getResources().getStringArray(R.array.spinnerEnglish);
                    notifyList();
                    break;
            }
        }
    };

    private TextView question_ws;//识别出的题目
    private ImageView questionPic_ws;//拍的题目图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_up_layout_ylx);
        // android 7.0系统解决拍照的问题（防止api24手机以上报错）
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


        //获取用户的id
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        uId = sp.getString("uId",null);

        //获取控件
        getViews();

        //获得传过来的图片识别结果,构建subjectMag类
        initData2();

        //绑定事件监听器
        registerListener();

        //展示拍照后的图片
        showWrongQuestionPhoto2();

        //设置触摸翻转
        Rotatable rotatable = new Rotatable.Builder(findViewById(R.id.rl_card_root))
                .sides(R.id.question_ws, R.id.questionPic_ws)
                .listener(new Rotatable.RotationListener() {
                    @Override
                    public void onRotationChanged(float newRotationX, float newRotationY) {
                        if (View.VISIBLE == question_ws.getVisibility()) {
                            initData2();
                        }else if(View.VISIBLE == questionPic_ws.getVisibility()){
                            ViewHelper.setRotationY(questionPic_ws, 180f);
                            showWrongQuestionPhoto2();
                        }
                    }
                })
                .direction(Rotatable.ROTATE_Y)
                .build();


        //给标签绑定adapter
        tags = getResources().getStringArray(R.array.spinner);
        list = new ArrayList<>();
        for(String str:tags){
            list.add(str);
        }
        tagAdapter = new ArrayAdapter<String>(this, R.layout.spinner_layout_ylx,list);
        spinner_ylx.setAdapter(tagAdapter);
        spinner_ylx.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String tag = (String) spinner_ylx.getSelectedItem();
                subjectMsg.setTag(tag);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String tag = (String) spinner_ylx.getSelectedItem();
                subjectMsg.setTag(tag);
            }
        });

    }

    //更新数据的方法
    private void notifyList() {
        list.clear();
        for(String str:tags){
            list.add(str);
        }
        tagAdapter.notifyDataSetChanged();
        spinner_ylx.setAdapter(tagAdapter);
    }

    //百度文字识别结果
    private void initData2() {
        Intent intent=getIntent();
        subjectMsg=new SubjectMsg();
        String optionA=intent.getStringExtra("A");
        String optionB=intent.getStringExtra("B");
        String optionC=intent.getStringExtra("C");
        String optionD=intent.getStringExtra("D");
        String question=intent.getStringExtra("题干");
        if(optionA!=null || optionB!=null || optionC!=null || optionD!=null){//选择题
            subjectMsg.setOptionA(optionA);
            subjectMsg.setOptionB(optionB);
            subjectMsg.setOptionC(optionC);
            subjectMsg.setOptionD(optionD);
            question_ws.setText(question+"\n"+optionA+"\n"+optionB+"\n"+optionC+"\n"+optionD);
            subjectMsg.setContent(question);
        }else{//填空题或者大题
            question_ws.setText(question);
            subjectMsg.setContent(question);
        }
        subjectMsg.setSubject("数学");
        subjectMsg.setTag("集合");
        subjectMsg.setType("填空题");
        subjectMsg.setTime(new Date());
//        subjectMsg.setTitleImg("files");
        subjectMsg.setuId(Integer.parseInt(uId));
    }

    //展示拍照后的图片：百度拍照识别，默认将剪裁后的图片(pic.jpg)放在files文件夹下
    private void showWrongQuestionPhoto2() {
        String picDir=getFilesDir().getAbsolutePath()+"/pic.jpg";
        Bitmap bitmap = BitmapFactory.decodeFile(picDir);
        path=picDir;
        subjectMsg.setTitleImg(picDir);
//        question_img_ylx.setImageBitmap(bitmap);
        ViewGroup.LayoutParams lp = questionPic_ws.getLayoutParams();
        lp.width=question_ws.getWidth();
        lp.height=question_ws.getHeight();
        questionPic_ws.setLayoutParams(lp);
        questionPic_ws.setImageBitmap(bitmap);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //获得用户拍照上传的照片
//        if (resultCode == RESULT_OK) {
//            switch (requestCode) {                // 选择请求码
//                case CAMERA_REQUEST_CODE:
//                    try {
//                        // 裁剪
//                        startCrop();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case UCrop.REQUEST_CROP:
//                    //保存裁剪后的图片并显示
//                    final Uri croppedUri = UCrop.getOutput(data);
//                    try {
//                        if (croppedUri != null) {
//                            photo =BitmapFactory.decodeStream(getContentResolver().openInputStream(croppedUri));
//                            path = saveImgToFile(photo);
//                            subjectMsg.setTitleImg(path);
//                            question_img_ylx.setPadding(25,25,25,25);
//                            question_img_ylx.setScaleType(ImageView.ScaleType.FIT_XY);
//                            question_img_ylx.setImageBitmap(photo);
//                            delete_ylx.setVisibility(View.VISIBLE);
//                            question_content_ylx.setVisibility(View.VISIBLE);
//                            //删除裁剪后保存的图片
//                            deletePathFromFile(pathCropPhoto);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    break;
//
//                case UCrop.RESULT_ERROR:
//                    final Throwable cropError = UCrop.getError(data);
//                    break;
//            }
//        }
//    }

//    //删除file目录下指定路径的图片
//    private void deletePathFromFile(String pathCropPhoto) {
//        File file = new File(pathCropPhoto);
//        if (file.exists()) {
//            file.delete();
//        }
//    }

//    //保存拍的图片到系统中
//    private String saveImgToFile(Bitmap photo) {
//        dataFileStr = getFilesDir().getAbsolutePath()+"/";
//        String fileName = System.currentTimeMillis() + ".jpg";
//        File file = new File(dataFileStr+fileName);
//        try {// 写入图片
//            FileOutputStream fos = new FileOutputStream(file);
//            BufferedOutputStream bos = new BufferedOutputStream(fos);
//            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//            bos.flush();
//            bos.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        //通知更新
//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        sendBroadcast(intent);
//        return fileName;
//    }
    //点击删除，重新拍照上传
//    private void uploadWrongQuestionPhotoAgain() {
//        delete_ylx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                question_img_ylx.setVisibility(View.INVISIBLE);
//                question_img_ylx.setImageResource(R.mipmap.mistakencamera_2);
//                question_img_ylx.setVisibility(View.VISIBLE);
//                question_img_ylx.setScaleType(ImageView.ScaleType.FIT_CENTER);
//                question_img_ylx.setPadding(50,80,50,50);
//                delete_ylx.setVisibility(View.INVISIBLE);
//                question_content_ylx.setVisibility(View.INVISIBLE);
//                //删除刚刚保存的图片
//                deletePathFromFile(dataFileStr+path);
//            }
//        });
//        question_img_ylx.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //调用手机照相机
//                takePhoto();
//            }
//        });
//    }

    //调用相机拍照
//    private void takePhoto(){
//        // 跳转到系统的拍照界面
//        Intent intentToTakePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // 指定照片存储位置为sd卡本目录下
//        // 这里设置为固定名字 这样就只会只有一张temp图 如果要所有中间图片都保存可以通过时间或者加其他东西设置图片的名称
//        // File.separator为系统自带的分隔符 是一个固定的常量
//        mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
//        // 获取图片所在位置的Uri路径    *****这里为什么这么做参考问题2*****
//        imageUri = Uri.fromFile(new File(mTempPhotoPath));
//        //下面这句指定调用相机拍照后的照片存储的路径
//        intentToTakePhoto.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        startActivityForResult(intentToTakePhoto,CAMERA_REQUEST_CODE);
//    }

    //裁剪图片
//    private void startCrop() {
//        String dataFileStr = getFilesDir().getAbsolutePath()+"/";
//        String fileName = System.currentTimeMillis() + ".jpg";
//        pathCropPhoto = dataFileStr+fileName;
//        Uri destinationUri = Uri.fromFile(new File(dataFileStr+fileName));
//        UCrop uCrop = UCrop.of(imageUri, destinationUri);
//        UCrop.Options options = new UCrop.Options();
//        //设置裁剪图片可操作的手势
//        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.ROTATE, UCropActivity.ALL);
//        //设置toolbar颜色
//        options.setToolbarColor(ActivityCompat.getColor(getApplicationContext(),R.color.themeColor));
//        //设置状态栏颜色
//        options.setStatusBarColor(ActivityCompat.getColor(getApplicationContext(), R.color.themeColor));
//        uCrop.withOptions(options);
//        uCrop.start(this);
//    }

    //绑定事件
    private void registerListener() {
        listener = new CustomOnclickListener();
        chinese_ylx.setOnClickListener(listener);
        math_ylx.setOnClickListener(listener);
        english_ylx.setOnClickListener(listener);
        add_wrong_questions_ylx.setOnClickListener(listener);
        choose_ylx.setOnClickListener(listener);
        fill_ylx.setOnClickListener(listener);
        big_question_ylx.setOnClickListener(listener);
//        delete_ylx.setOnClickListener(listener);
        back_ylx.setOnClickListener(listener);
        add_wrong_questions_ylx.setOnClickListener(listener);

        easy_ws.setOnClickListener(listener);
        middle_ws.setOnClickListener(listener);
        difficult_ws.setOnClickListener(listener);

        notMastered_ws.setOnClickListener(listener);
        mastered_ws.setOnClickListener(listener);
        skilled_ws.setOnClickListener(listener);

        confusedIdeas_ws.setOnClickListener(listener);
        wrongThinking_ws.setOnClickListener(listener);
        arithmeticError_ws.setOnClickListener(listener);
        examinationError_ws.setOnClickListener(listener);

    }
    //获取控件
    private void getViews() {
//        delete_ylx = findViewById(R.id.delete_ylx);
//        question_img_ylx=findViewById(R.id.question_img_ylx);
        chinese_ylx=findViewById(R.id.chinese_ylx);
        math_ylx = findViewById(R.id.math_ylx);
        english_ylx = findViewById(R.id.english_ylx);
        spinner_ylx = findViewById(R.id.spinner_ylx);
        choose_ylx  = findViewById(R.id.choose_ylx);
        fill_ylx = findViewById(R.id.fill_ylx);
        big_question_ylx = findViewById(R.id.big_question_ylx);
        answer_ylx = findViewById(R.id.answer_ylx);
        answer_big_ylx = findViewById(R.id.answer_big_ylx);
        anwser_edt_ylx = findViewById(R.id.anwser_edt_ylx);
        add_wrong_questions_ylx =findViewById(R.id.add_wrong_questions_ylx);
        ap = answer_ylx.getLayoutParams();
        lp = answer_big_ylx.getLayoutParams();
//        question_content_ylx = findViewById(R.id.question_content_ylx);
        back_ylx = findViewById(R.id.img_ylx);
        option_anwser_ylx = findViewById(R.id.option_answer_ylx);

        question_ws=findViewById(R.id.question_ws);
        questionPic_ws=findViewById(R.id.questionPic_ws);

        easy_ws=findViewById(R.id.easy_ws);
        middle_ws=findViewById(R.id.middle_ws);
        difficult_ws=findViewById(R.id.difficult_ws);

        notMastered_ws=findViewById(R.id.notMastered_ws);
        mastered_ws=findViewById(R.id.mastered_ws);
        skilled_ws=findViewById(R.id.skilled_ws);

        confusedIdeas_ws=findViewById(R.id.confusedIdeas_ws);
        wrongThinking_ws=findViewById(R.id.wrongThinking_ws);
        arithmeticError_ws=findViewById(R.id.arithmeticError_ws);
        examinationError_ws=findViewById(R.id.examinationError_ws);
    }
    //点击事件
    public class CustomOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //错题图片删除按钮
//                case R.id.delete_ylx:
//                    uploadWrongQuestionPhotoAgain();
//                    break;
                case R.id.img_ylx://点击返回
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.setAction("mistake");
                    startActivity(intent);
                    finish();
                    break;
                //选择学科
                case R.id.chinese_ylx://选择语文学科
                    chinese_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    math_ylx.setBackgroundColor(Color.WHITE);
                    english_ylx.setBackgroundColor(Color.WHITE);
                    Message msg = Message.obtain();
                    msg.obj = "语文";
                    msg.what=100;
                    handler.sendMessage(msg);
                    subjectMsg.setSubject("语文");
                    break;
                case R.id.math_ylx://选择数学学科
                    chinese_ylx.setBackgroundColor(Color.WHITE);
                    math_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    english_ylx.setBackgroundColor(Color.WHITE);
                    Message msg1 = Message.obtain();
                    msg1.obj = "数学";
                    msg1.what=200;
                    handler.sendMessage(msg1);
                    subjectMsg.setSubject("数学");
                    break;
                case R.id.english_ylx://选择英语学科
                    chinese_ylx.setBackgroundColor(Color.WHITE);
                    math_ylx.setBackgroundColor(Color.WHITE);
                    english_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    Message msg2 = Message.obtain();
                    msg2.obj = "英语";
                    msg2.what=300;
                    handler.sendMessage(msg2);
                    subjectMsg.setSubject("英语");
                    break;
                //选择题型
                case R.id.choose_ylx://选择题
                    choose_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    fill_ylx.setBackgroundColor(Color.WHITE);
                    big_question_ylx.setBackgroundColor(Color.WHITE);
                    lp.height=0;
                    answer_big_ylx.setLayoutParams(lp);
                    ap.height=1000;
                    answer_ylx.setLayoutParams(ap);
                    subjectMsg.setType("选择题");
                    break;
                case R.id.fill_ylx://填空题
                    choose_ylx.setBackgroundColor(Color.WHITE);
                    fill_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    big_question_ylx.setBackgroundColor(Color.WHITE);
                    ap.height=0;
                    answer_ylx.setLayoutParams(ap);
                    lp.height=1200;
                    answer_big_ylx.setLayoutParams(lp);
                    subjectMsg.setType("填空题");
                    break;
                case R.id.big_question_ylx://大题
                    choose_ylx.setBackgroundColor(Color.WHITE);
                    fill_ylx.setBackgroundColor(Color.WHITE);
                    big_question_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    ap.height=0;
                    answer_ylx.setLayoutParams(ap);
                    lp.height=1200;
                    answer_big_ylx.setLayoutParams(lp);
                    subjectMsg.setType("大题");
                    break;
                    //选择难度
                case R.id.easy_ws://选择易
                    easy_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    middle_ws.setBackgroundColor(Color.WHITE);
                    difficult_ws.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.middle_ws://选择中等
                    middle_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    easy_ws.setBackgroundColor(Color.WHITE);
                    difficult_ws.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.difficult_ws://选择难
                    difficult_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    easy_ws.setBackgroundColor(Color.WHITE);
                    middle_ws.setBackgroundColor(Color.WHITE);
                    break;
                    //选择掌握程度
                case R.id.notMastered_ws:
                    notMastered_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    mastered_ws.setBackgroundColor(Color.WHITE);
                    skilled_ws.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.mastered_ws:
                    mastered_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    notMastered_ws.setBackgroundColor(Color.WHITE);
                    skilled_ws.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.skilled_ws:
                    skilled_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    mastered_ws.setBackgroundColor(Color.WHITE);
                    notMastered_ws.setBackgroundColor(Color.WHITE);
                    break;
                    //选择错误原因
                case R.id.confusedIdeas_ws:
                    confusedIdeas_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    wrongThinking_ws.setBackgroundColor(Color.WHITE);
                    arithmeticError_ws.setBackgroundColor(Color.WHITE);
                    examinationError_ws.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.wrongThinking_ws:
                    wrongThinking_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    confusedIdeas_ws.setBackgroundColor(Color.WHITE);
                    arithmeticError_ws.setBackgroundColor(Color.WHITE);
                    examinationError_ws.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.arithmeticError_ws:
                    arithmeticError_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    wrongThinking_ws.setBackgroundColor(Color.WHITE);
                    confusedIdeas_ws.setBackgroundColor(Color.WHITE);
                    examinationError_ws.setBackgroundColor(Color.WHITE);
                    break;
                case R.id.examinationError_ws:
                    examinationError_ws.setBackgroundColor(getResources().getColor(R.color.themeColor));
                    wrongThinking_ws.setBackgroundColor(Color.WHITE);
                    arithmeticError_ws.setBackgroundColor(Color.WHITE);
                    confusedIdeas_ws.setBackgroundColor(Color.WHITE);
                    break;
                //信息填写完毕添加错题到数据库
                case R.id.add_wrong_questions_ylx:
                    //错题答案
                    if(subjectMsg.getType().equals("选择题")){
                        subjectMsg.setAnswer(option_anwser_ylx.getText().toString().trim());
                    }else {
                        subjectMsg.setAnswer(anwser_edt_ylx.getText().toString().trim());
                    }
                    //弹出框
                    showCustomDialog(subjectMsg);
                    break;

            }
        }
    }

    //确认添加弹出框
    private void showCustomDialog(SubjectMsg subjectMsg) {
        //管理多个Fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        //事务（一系列原子性操作）
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        CustomDialogYLX customDialog = new CustomDialogYLX();
        //是否添加过
        if(!customDialog.isAdded()){
            //没添加过添加
            transaction.add(customDialog,"dialog");
        }
        //传入要上传的数据
        customDialog.subjectMsgData(subjectMsg);
        //显示Fragment
        transaction.show(customDialog);
        //提交，只有提交了上面的操作才会生效
        transaction.commit();
    }
}

