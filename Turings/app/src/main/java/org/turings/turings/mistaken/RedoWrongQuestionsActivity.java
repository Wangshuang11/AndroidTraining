package org.turings.turings.mistaken;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.turings.turings.MainActivity;
import org.turings.turings.R;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.turings.turings.mistaken.SubjectMsg;
public class RedoWrongQuestionsActivity extends AppCompatActivity {

    private ImageView img_ylx;//返回键
    private ImageView choose_ylx;//选择修改键
    private ImageView subjectImg_ylx;//题目图片
    private LinearLayout a_choose_ylx;//A选项
    private LinearLayout b_choose_ylx;//B选项
    private LinearLayout c_choose_ylx;//C选项
    private LinearLayout d_choose_ylx;//D选项
    private Button a_choose_btn_ylx;//a选项button按钮
    private Button b_choose_btn_ylx;//b选项button按钮
    private Button c_choose_btn_ylx;//c选项button按钮
    private Button d_choose_btn_ylx;//d选项button按钮
    private TextView a_choose_text_ylx;//a选项内容
    private TextView b_choose_text_ylx;//b选项内容
    private TextView c_choose_text_ylx;//c选项内容
    private TextView d_choose_text_ylx;//d选项内容
    private TextView answer_show_ylx;//答案展示
    private Button pre_question_ylx;//上一题
    private Button next_question_ylx;//下一题
    private CustomOnclickListener listener;//监听器
    private PopupWindow popupWindow;
    private SubjectMsg msgs;//题目
    private OkHttpClient okHttpClient;
    private String tagChange;//标签（存更改的标签）
    private String subjectChange;//学科(存更改的学科)
    private AlertDialog alertDialog;//弹出框
    private int uId;//用户的id
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String ms = (String) msg.obj;
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            switch (msg.what){
                case 100:
                    if(ms.equals("这已经是第一题了")){
                        Toast.makeText(getApplicationContext(),"这已经是第一题了",Toast.LENGTH_SHORT).show();
                    }else if(ms.equals("这已经是最后一道题了")){
                        Toast.makeText(getApplicationContext(),"这已经是最后一道题了",Toast.LENGTH_SHORT).show();
                    }else {
                        msgs = gson.fromJson(ms,SubjectMsg.class);
                        if(!msgs.getType().equals("选择题")){
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), RedoWrongBigQuestionActivity.class);
                            intent.putExtra("subject", msgs);
                            startActivity(intent);
                            finish();
                        }
                        //获取图片id,从data的files目录下取出来
                        String dataFileStr = getFilesDir().getAbsolutePath()+"/"+msgs.getTitleImg();
                        Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
                        //添加题目图片
                        subjectImg_ylx.setImageBitmap(bitmap);
                        answer_show_ylx.setBackgroundColor(getResources().getColor(R.color.answerColor));
                        answer_show_ylx.setText("刮刮乐查看答案");
                        answer_show_ylx.setTextColor(getResources().getColor(R.color.themeColor));
                    }
                    break;
                case 200:
                    if(ms.equals("修改成功")){
                        msgs.setTag(tagChange);
                        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"修改失败，请重试",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 300:
                    if(ms.equals("修改成功")){
                        msgs.setSubject(subjectChange);
                        Toast.makeText(getApplicationContext(),"修改成功",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(getApplicationContext(),"修改失败，请重试",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 400:
                    if(ms.equals("删除失败，请重新删除")){
                        Toast.makeText(getApplicationContext(),"删除失败，请重试",Toast.LENGTH_SHORT).show();
                    }else if(ms.equals("最后一道题被删除了呢，请重新筛选题目吧")){//设计一个弹出框吧
                        //删除file目录下上一道题
                        deletePathFromFile(getFilesDir().getAbsolutePath()+"/"+msgs.getTitleImg());
                        msgs = null;
                        subjectImg_ylx.setVisibility(View.INVISIBLE);
                        answer_show_ylx.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(),"最后一道题被删除了呢，请重新筛选题目吧",Toast.LENGTH_SHORT).show();
                        //没有符合您筛选条件的题目了，请重新筛选或者立即上传新的题目
                        showAlertDialog();

                    }else{
                        //删除file目录下上一道题
                        deletePathFromFile(getFilesDir().getAbsolutePath()+"/"+msgs.getTitleImg());
                        msgs = gson.fromJson(ms,SubjectMsg.class);
                        if(!msgs.getType().equals("选择题")){
                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), RedoWrongBigQuestionActivity.class);
                            intent.putExtra("subject", msgs);
                            startActivity(intent);
                            finish();
                        }
                        //获取图片id,从data的files目录下取出来
                        String dataFileStr = getFilesDir().getAbsolutePath()+"/"+msgs.getTitleImg();
                        Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
                        //添加题目图片
                        subjectImg_ylx.setImageBitmap(bitmap);
                        a_choose_text_ylx.setText(msgs.getOptionA());
                        b_choose_text_ylx.setText(msgs.getOptionB());
                        c_choose_text_ylx.setText(msgs.getOptionC());
                        d_choose_text_ylx.setText(msgs.getOptionD());
                    }
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redo_wrong_questions_ylx);
        //获取用户的id
        SharedPreferences sp = getSharedPreferences("userInfo",MODE_PRIVATE);
        uId = Integer.parseInt(sp.getString("uId",null));
        //获取控件
        getViews();
        //获取从LookUpAndErrorReDoActivity传来的数据
        initData();
        //给控件绑定点击事件监听器
        registerOnclickListener();
    }

    //获取从LookUpAndErrorReDoActivity传来的数据
    private void initData() {
        Intent intent = getIntent();
        msgs = (SubjectMsg) intent.getSerializableExtra("subject");
        String dataFileStr = getFilesDir().getAbsolutePath()+"/"+msgs.getTitleImg();
        Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
        //添加题目图片
        subjectImg_ylx.setImageBitmap(bitmap);
        a_choose_text_ylx.setText(msgs.getOptionA());
        b_choose_text_ylx.setText(msgs.getOptionB());
        c_choose_text_ylx.setText(msgs.getOptionC());
        d_choose_text_ylx.setText(msgs.getOptionD());

    }
    //删除file目录下指定路径的图片
    private void deletePathFromFile(String pathCropPhoto) {
        File file = new File(pathCropPhoto);
        if (file.exists()) {
            Boolean b  = file.delete();
        }
    }
    private void registerOnclickListener() {
        listener = new CustomOnclickListener();
        img_ylx.setOnClickListener(listener);
        choose_ylx.setOnClickListener(listener);
        a_choose_ylx.setOnClickListener(listener);
        b_choose_ylx.setOnClickListener(listener);
        c_choose_ylx.setOnClickListener(listener);
        d_choose_ylx.setOnClickListener(listener);
        answer_show_ylx.setOnClickListener(listener);
        pre_question_ylx.setOnClickListener(listener);
        next_question_ylx.setOnClickListener(listener);

    }

    //获取控件
    private void getViews() {
        img_ylx = findViewById(R.id.img_ylx);
        choose_ylx=findViewById(R.id.choose_ylx);
        a_choose_ylx = findViewById(R.id.a_choose_ylx);
        b_choose_ylx = findViewById(R.id.b_choose_ylx);
        c_choose_ylx = findViewById(R.id.c_choose_ylx);
        d_choose_ylx = findViewById(R.id.d_choose_ylx);
        answer_show_ylx = findViewById(R.id.answer_show_ylx);
        pre_question_ylx = findViewById(R.id.pre_question_ylx);
        next_question_ylx = findViewById(R.id.next_question_ylx);
        a_choose_btn_ylx = findViewById(R.id.a_choose_btn_ylx);
        b_choose_btn_ylx = findViewById(R.id.b_choose_btn_ylx);
        c_choose_btn_ylx = findViewById(R.id.c_choose_btn_ylx);
        d_choose_btn_ylx = findViewById(R.id.d_choose_btn_ylx);
        a_choose_text_ylx = findViewById(R.id.a_choose_text_ylx);
        b_choose_text_ylx = findViewById(R.id.b_choose_text_ylx);
        c_choose_text_ylx = findViewById(R.id.c_choose_text_ylx);
        d_choose_text_ylx = findViewById(R.id.d_choose_text_ylx);
        subjectImg_ylx = findViewById(R.id.subjectImg_ylx);
    }
    class CustomOnclickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.img_ylx://点击返回
                    Intent intent = new Intent(getApplicationContext(),LookUpAndErrorReDoActivity.class);
                    startActivity(intent);
                    break;
                case R.id.choose_ylx://点击弹出选项（编辑错题，删除错题，修改科目，取消）
                    showPopupWindow();
                    break;
                case R.id.answer_show_ylx://点击刮刮乐查看答案
                    answer_show_ylx.setBackgroundColor(Color.WHITE);
                    answer_show_ylx.setTextColor(Color.RED);
                    answer_show_ylx.setText(msgs.getAnswer());
                    break;
                case R.id.a_choose_ylx:
                    chooseAoption();
                    break;
                case R.id.b_choose_ylx:
                    chooseBoption();
                    break;
                case R.id.c_choose_ylx:
                    chooseCoption();
                    break;
                case R.id.d_choose_ylx:
                    chooseDoption();
                    break;
                case R.id.next_question_ylx://下一题
                    //从数据库中搜索下一题并显示
                    searchNextSubject();
                    break;
                case R.id.pre_question_ylx://上一题
                    //从数据库中搜索上一题并显示
                    searchPreSubject();
                    break;
            }
        }
    }

    //查询上一题
    private void searchPreSubject() {
        String url = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/SearchPreSubjectServlet";
        searchSubjectFromServlet(url);
    }
    //查询下一题
    private void searchNextSubject() {
        String url = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/SearchNextSubjectServlet";
        searchSubjectFromServlet(url);
    }
    //从数据库中查题
    private void searchSubjectFromServlet(String url) {
        okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(msgs.getId()))
                .add("subject",msgs.getSubject())
                .add("tag",msgs.getTag())
                .add("uId", String.valueOf(uId))
                .build();

        final Request request = new Request.Builder().post(formBody).url(url).build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("lww", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message msg = Message.obtain();
                        String rp = response.body().string();
                        msg.obj = rp;
                        msg.what=100;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();

    }

    //点击弹出选项（编辑错题，删除错题，修改科目，取消）的Popupwindow
    private void showPopupWindow(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_window_choose_layout_ylx,null,false);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT);
        //获取弹出窗布局当中取消按钮
        Button button = view.findViewById(R.id.btn_cancel);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关闭弹出窗口
                popupWindow.dismiss();
            }
        });
        //选择修改学科
        Button btnFix = view.findViewById(R.id.btn_fix_ylx);
        btnFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showSubjectFixPopupWindow();
            }
        });
        //选择删除错题
        Button btnDelete = view.findViewById(R.id.btn_delete_ylx);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //数据库中删除错题
                deleteSubject();
                popupWindow.dismiss();
            }
        });
        //选择编辑错题，更改标签
        Button btnEdit = view.findViewById(R.id.btn_edt_ylx);
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                showFixTagPopupWindow();
            }
        });
        //设置弹出窗口显示内容的视图
        popupWindow.setContentView(view);
        //设置背景透明
        addBackground();
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        //弹出窗口父视图对象
        RelativeLayout parent = findViewById(R.id.parent_ylx);
        //显示弹出窗口
        popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
    }
    //展示修改科目的popupWindow
    private void  showSubjectFixPopupWindow(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_window_subject_fix_layout_ylx,null,false);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT);
        //设置弹出窗口显示内容的视图
        popupWindow.setContentView(view);
        //设置背景透明
        addBackground();
        subjectChange = "数学";
        //语文
        final Button btnChinese = view.findViewById(R.id.btn_chinese_ylx);
        //数学
        final Button btnMath = view.findViewById(R.id.btn_math_ylx);
        //英语
        final Button btnEnglish = view.findViewById(R.id.btn_english_ylx);
        //确认按钮
        final Button btnOk = view.findViewById(R.id.btn_ok);
        //点击选中语文
        btnChinese.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnChinese.setBackground(getResources().getDrawable(R.drawable.subject_choose_btn_background));
                btnMath.setBackground(getResources().getDrawable(R.drawable.subject_btn_background));
                btnEnglish.setBackground(getResources().getDrawable(R.drawable.subject_btn_background));
                subjectChange = "语文";
            }
        });
        //点击选中数学
        btnMath.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnMath.setBackground(getResources().getDrawable(R.drawable.subject_choose_btn_background));
                btnChinese.setBackground(getResources().getDrawable(R.drawable.subject_btn_background));
                btnEnglish.setBackground(getResources().getDrawable(R.drawable.subject_btn_background));
                subjectChange = "数学";

            }
        });
        //点击选中英语
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnEnglish.setBackground(getResources().getDrawable(R.drawable.subject_choose_btn_background));
                btnChinese.setBackground(getResources().getDrawable(R.drawable.subject_btn_background));
                btnMath.setBackground(getResources().getDrawable(R.drawable.subject_btn_background));
                subjectChange = "英语";
            }
        });
        //获取弹出框布局中的确定按钮，点击关闭此弹出框，回到最初弹出框
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnOk.setBackgroundColor(getResources().getColor(R.color.themeColor));
                changeSjtOfSubject();
                popupWindow.dismiss();
                showPopupWindow();
            }
        });
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        //弹出窗口父视图对象
        RelativeLayout parent = findViewById(R.id.parent_ylx);
        //显示弹出窗口
        popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
    }
    //展示修改标签的popupwindow
    private void showFixTagPopupWindow(){
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.popup_window_edit_subject_tag_layout_ylx,null,false);
        popupWindow = new PopupWindow(view, ActionBar.LayoutParams.MATCH_PARENT,ActionBar.LayoutParams.WRAP_CONTENT);
        //设置弹出窗口显示内容的视图
        popupWindow.setContentView(view);
        //设置背景透明
        addBackground();
        //获取弹出框布局中的确定按钮，点击关闭此弹出框，回到最初弹出框
        final Button btnOk = view.findViewById(R.id.btn_ok);
        final Spinner spinner = view.findViewById(R.id.spinner_ylx);
        tagChange = msgs.getTag();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tagChange = (String) spinner.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTagOfSubject();
                popupWindow.dismiss();
                showPopupWindow();
            }
        });
        popupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
        //弹出窗口父视图对象
        RelativeLayout parent = findViewById(R.id.parent_ylx);
        //显示弹出窗口
        popupWindow.showAtLocation(parent, Gravity.BOTTOM,0,0);
    }
    //数据库中更改标签
    private void changeTagOfSubject() {
        okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(msgs.getId()))
                .add("tag",tagChange)
                .build();
        String url = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/ChangeTagOfSubjectServlet";
        final Request request = new Request.Builder().post(formBody).url(url).build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("lww", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message msg = Message.obtain();
                        String rp = response.body().string();
                        msg.obj = rp;
                        msg.what=200;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }

    //给popupwindow添加透明背景
    private void addBackground(){
        //设置背景颜色变暗
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        //调节透明度
        lp.alpha=0.5f;
        getWindow().setAttributes(lp);
        //dismiss恢复原样
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha=1f;
                getWindow().setAttributes(lp);
            }
        });
    }
    //选中A
    private void chooseAoption() {
        a_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_linearlayout_blue));
        a_choose_btn_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
        a_choose_btn_ylx.setTextColor(Color.WHITE);
        b_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        b_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        b_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        c_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        c_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        c_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        d_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        d_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        d_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
    }
    //选中B
    private void chooseBoption() {
        b_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_linearlayout_blue));
        b_choose_btn_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
        b_choose_btn_ylx.setTextColor(Color.WHITE);
        a_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        a_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        a_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        c_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        c_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        c_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        d_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        d_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        d_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
    }
    //选中C
    private void chooseCoption() {
        c_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_linearlayout_blue));
        c_choose_btn_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
        c_choose_btn_ylx.setTextColor(Color.WHITE);
        b_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        b_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        b_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        a_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        a_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        a_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        d_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        d_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        d_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
    }
    //选中D
    private void chooseDoption() {
        d_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_linearlayout_blue));
        d_choose_btn_ylx.setBackgroundColor(getResources().getColor(R.color.themeColor));
        d_choose_btn_ylx.setTextColor(Color.WHITE);
        b_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        b_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        b_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        c_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        c_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        c_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
        a_choose_ylx.setBackground(getResources().getDrawable(R.drawable.frame_to_answer_edit));
        a_choose_btn_ylx.setBackgroundColor(Color.WHITE);
        a_choose_btn_ylx.setTextColor(getResources().getColor(R.color.themeColor));
    }
    //数据库中更改学科
    private void changeSjtOfSubject() {
        okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(msgs.getId()))
                .add("subject",subjectChange)
                .build();
        String url = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/ChangeSjtOfSubjectServlet";
        final Request request = new Request.Builder().post(formBody).url(url).build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("lww", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message msg = Message.obtain();
                        String rp = response.body().string();
                        msg.obj = rp;
                        msg.what=300;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();

    }
    //在数据库中删除当前题目，并显示下一条题目
    private void deleteSubject() {
        okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("id", String.valueOf(msgs.getId()))
                .add("subject",msgs.getSubject())
                .add("tag",msgs.getTag())
                .add("uId", String.valueOf(uId))
                .build();
        String url = "http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/DeleteSubjectServlet";
        final Request request = new Request.Builder().post(formBody).url(url).build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.i("lww", "请求失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message msg = Message.obtain();
                        String rp = response.body().string();
                        msg.obj = rp;
                        msg.what=400;
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }
    //弹出框，去拍照上传题目or去筛选重新定义筛选条件
    private void showAlertDialog() {
        // 构建dialog显示的view布局
        View view_dialog = getLayoutInflater().from(this).inflate(R.layout.dialog_layout_ylx, null);

        if (alertDialog == null){
            // 创建AlertDialog对象
            alertDialog = new AlertDialog.Builder(this)
                    .create();
            alertDialog.show();
            // 设置点击可取消
            alertDialog.setCancelable(true);

            // 获取Window对象
            Window window = alertDialog.getWindow();
            window.setBackgroundDrawableResource(R.drawable.dialog_stroke_layout_ylx);
            WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
            lp.width = 900;// 调整该值可以设置对话框显示的宽度
            window.setAttributes(lp);
            // 设置显示视图内容
            window.setContentView(view_dialog);
            Button button = view_dialog.findViewById(R.id.button_ylx);
            //去上传
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.setAction("mistake");
                    startActivity(intent);
                    finish();
                }
            });
        }else {
            alertDialog.show();
        }
    }

}
