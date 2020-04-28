package org.turings.turings.mistaken.subjectFragment;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.turings.turings.R;
import org.turings.turings.mistaken.RedoWrongBigQuestionActivity;
import org.turings.turings.mistaken.RedoWrongQuestionsActivity;
import org.turings.turings.mistaken.entity.SubjectMsg;
import org.turings.turings.mistaken.customAdapterAndDialog.CustomAdapterYLX;
import org.turings.turings.mistaken.customAdapterAndDialog.GridViewAdapter;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * 数学学科下的所有错题类型检索展示
 */
public class MathFragment extends Fragment implements View.OnClickListener{
    //水平滑动标签
    private GridView gridView;
    private List<String> tags = new ArrayList<>();
    private ListView subject_list_ylx;//题目listView
    private List<SubjectMsg> list;//列表要展示的题目资源
    private SmartRefreshLayout srl;//刷新
    private ImageView timeType;//时间类型筛选
    private ImageView nullYlx;//如果没有题目显示
    private LinearLayout lyYlx;//空空如也外框
    //所有搜索条件(默认值设置)
    private String subject ="数学";//选中的学科
    private String tag = "集合";
    private String date="";//时间
    private String tyep="";//题型
    private String uId;//用户的id
    private OkHttpClient okHttpClient;
    //适配器
    private CustomAdapterYLX customAdapterYLX;//适配器
    private GridViewAdapter adapter;//标签的适配器
    private List<SubjectMsg> lists;//列表要展示的题目资源

    //批量删除所需属性
    private LinearLayout mLlEditBar;//控制下方那一行的显示与隐藏
    private List<SubjectMsg> mCheckedData = new ArrayList<>();//将选中数据放入里面
    private SparseBooleanArray stateCheckedMap = new SparseBooleanArray();//用来存放CheckBox的选中状态，true为选中,false为没有选中
    private boolean isSelectedAll = true;//用来控制点击全选，全选和全不选相互切换
    private Menu menu;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String ms = (String) msg.obj;
            if(ms.equals("刷新")){
                list.clear();
                for(SubjectMsg subjectMsg : lists){
                    list.add(subjectMsg);
                    customAdapterYLX.notifyDataSetChanged();
                }
                if(list.size() == 0 || list == null){
                    nullYlx.setVisibility(View.VISIBLE);
                    lyYlx.setVisibility(View.VISIBLE);
                }else {
                    nullYlx.setVisibility(View.GONE);
                    lyYlx.setVisibility(View.GONE);
                }
            }else {
                list.clear();
                if(list.size() == 0 || list == null){
                    nullYlx.setVisibility(View.VISIBLE);
                    lyYlx.setVisibility(View.VISIBLE);
                }else {
                    nullYlx.setVisibility(View.GONE);
                    lyYlx.setVisibility(View.GONE);
                }
                customAdapterYLX.notifyDataSetChanged();
            }
            if (mLlEditBar.getVisibility() == View.VISIBLE) {
                cancel();
                return;
            }
            if(msg.obj.equals("删除成功")){
                Toast.makeText(getActivity().getApplicationContext(),"删除成功",Toast.LENGTH_SHORT);
            }else {
                Toast.makeText(getActivity().getApplicationContext(),"删除失败",Toast.LENGTH_SHORT);
            }

        }
    };

    //日期年月日
    private int mYear;
    private int mMonth;
    private int mDay;
    /**
     * 日期选择器对话框监听
     */
    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").append("0").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append("0").append(mDay).toString();
                } else {
                    days = new StringBuffer().append(mYear).append("-").
                            append(mMonth + 1).append("-").append(mDay).toString();
                }

            }
            //数据库查询符合日期条件的题目
            date = days;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    searchSubjectMsgBySubjectAndTag(subject,tag,date,tyep);
                }
            }).start();
        }
    };

    // 可用于传值
    public static MathFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        MathFragment fragment = new MathFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ylx_math_fragment_layout, container, false);
        //获取用户的id
        //获取用户的id
        SharedPreferences sp = getContext().getSharedPreferences("userInfo",MODE_PRIVATE);
        uId = sp.getString("uId",null);
        getViews(view);
        setData();
        setGridView();
        InitData();
        if (list.size() == 0 || list == null){
            nullYlx.setVisibility(View.VISIBLE);
        }else {
            nullYlx.setVisibility(View.GONE);
        }
        //创建适配器
        customAdapterYLX = new CustomAdapterYLX(list,getActivity().getApplicationContext(), R.layout.item_list_subject_ylx_layout,stateCheckedMap);
        //绑定适配器
        subject_list_ylx.setAdapter(customAdapterYLX);
        //绑定事件
        rigisterListener();
        customAdapterYLX.notifyDataSetChanged();
        srl.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshData();
                srl.finishRefresh();
                Toast.makeText(getActivity().getApplicationContext(),
                        "刷新完成",
                        Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
    //获取控件
    private void getViews(View view){
        gridView = view.findViewById(R.id.grid);
        subject_list_ylx=view.findViewById(R.id.subject_list_ylx);
        srl = view.findViewById(R.id.srl);
        nullYlx = view.findViewById(R.id.nullYlx);
        lyYlx = view.findViewById(R.id.lyylx);
        timeType = view.findViewById(R.id.type_time);
        mLlEditBar = view.findViewById(R.id.rll_view);

        view.findViewById(R.id.ll_cancel).setOnClickListener(this);
        view.findViewById(R.id.ll_delete).setOnClickListener(this);
        view.findViewById(R.id.ll_inverse).setOnClickListener(this);
        view.findViewById(R.id.ll_select_all).setOnClickListener(this);
        subject_list_ylx.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
    }
    //绑定事件
    private void rigisterListener() {
        timeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("www", "onClick:菜单 ");
                showPopupMenu(view);
            }
        });
        //点击listView的item事件
        subject_list_ylx.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //进入错题重做页
                if(mLlEditBar.getVisibility() != View.VISIBLE){
                    redoErrorQuestions(list.get(position));
                }
                updateCheckBoxStatus(view, position);
            }

        });
        //长按删除
        subject_list_ylx.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mLlEditBar.setVisibility(View.VISIBLE);//显示下方布局
                customAdapterYLX.setShowCheckBox(true);//CheckBox的那个方框显示
                updateCheckBoxStatus(view, position);
                return true;
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_cancel:
                cancel();
                break;
            case R.id.ll_delete:
                delete();
                break;
            case R.id.ll_inverse:
                inverse();
                break;
            case R.id.ll_select_all:
                selectAll();
                break;
        }
    }
    /**设置标签数据*/
    private void setData() {
        tags.add("集合");
        tags.add("函数");
        tags.add("映射");
        tags.add("导数");
        tags.add("微积分");
        tags.add("三角函数");
        tags.add("平面向量");
        tags.add("数列");
    }
    //初始化数据(应该从数据库中获取,获取所有数学类集合标签下的错题)
    private void InitData() {
        list = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchSubjectMsgBySubjectAndTag(subject,tag,date,tyep);
            }
        }).start();
        setStateCheckedMap(false);
    }

    /**设置GirdView参数，绑定数据*/
    private void setGridView() {
        int size = tags.size();
        int length = 100;
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int gridviewWidth = (int) (size * (length + 4) * density);
        int itemWidth = (int) (length * density);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                gridviewWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        gridView.setLayoutParams(params); // 设置GirdView布局参数,横向布局的关键
        gridView.setColumnWidth(itemWidth); // 设置列表项宽
        gridView.setHorizontalSpacing(5); // 设置列表项水平间距
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setNumColumns(size); // 设置列数量=列表集合数

        adapter = new GridViewAdapter(this.getContext(),tags, R.layout.ylx_item_gallery_layout);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView tvTag = view.findViewById(R.id.left_one_text_ylx);
                tvTag.setBackgroundResource(R.mipmap.lantagylx);
                tag=tvTag.getText().toString().trim();
                Log.i("www", "onItemSelected: "+tag);
                //改变选中位置
                adapter.clearSelection(i);
                //更新数据
                adapter.notifyDataSetChanged();
                //数据库查询
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        searchSubjectMsgBySubjectAndTag(subject,tag,date,tyep);
                    }
                }).start();
            }
        });
    }

    //刷新数据
    private void refreshData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                searchSubjectMsgBySubjectAndTag(subject,tag,date,tyep);
            }
        }).start();
    }
    private void cancel() {
        setStateCheckedMap(false);//将CheckBox的所有选中状态变成未选中
        mLlEditBar.setVisibility(View.GONE);//隐藏下方布局
        customAdapterYLX.setShowCheckBox(false);//让CheckBox那个方框隐藏
        customAdapterYLX.notifyDataSetChanged();//更新ListView
    }

    private void delete() {
        if (mCheckedData.size() == 0) {
            Toast.makeText(getActivity(), "您还没有选中任何数据！", Toast.LENGTH_SHORT).show();
            return;
        }
        showDialog();
    }
    //初始化并弹出对话框方法
    private void showDialog(){
        View view = LayoutInflater.from(this.getContext()).inflate(R.layout.ylx_delete_dialog_layout,null,false);
        final AlertDialog dialog = new AlertDialog.Builder(this.getContext()).setView(view).create();

        Button btnOk = view.findViewById(R.id.button_ok_ylx);
        Button btnNo = view.findViewById(R.id.button_no_ylx);
        Window window = dialog.getWindow();
        // WindowManager 接口的嵌套类
        WindowManager.LayoutParams windowParams = window.getAttributes();
        //设置弹出框周围的透明度
        windowParams.dimAmount = 0.5f;
        //设置弹出框内容的透明度
        windowParams.alpha = 1f;
        //设置弹出框距离上面的距离
        windowParams.y = 100;
        window.setBackgroundDrawableResource(R.drawable.dialog_stroke_layout_ylx);
        windowParams.width = 800;// 调整该值可以设置对话框显示的宽度
        window.setAttributes(windowParams);
        //DisplayMetrics类 获取手机显示屏的基本信息 包括尺寸、密度、字体缩放等信息
        DisplayMetrics dm = new DisplayMetrics();
        //将屏幕尺寸赋给dm
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        dialog.show();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("www", "onClick: 确定删除");
                //... To-do
                beSureDelete();
                dialog.dismiss();
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //... To-do
                Log.i("www", "onClick: 取消删除");
                dialog.dismiss();
            }
        });
    }
    //确定删除
    private void beSureDelete() {
        list.removeAll(mCheckedData);//删除选中数据
        List<Integer> ids = new ArrayList<>();
        for (SubjectMsg subjectMsg:mCheckedData){
            ids.add(subjectMsg.getId());
        }
        //在数据库删除数据
        deleteSubjectMsgById(ids);
        setStateCheckedMap(false);//将CheckBox的所有选中状态变成未选中
        mCheckedData.clear();//清空选中数据
        customAdapterYLX.notifyDataSetChanged();
    }
    /**
     * 反选就是stateCheckedMap的值为true时变为false,false时变成true
     * */
    private void inverse() {
        mCheckedData.clear();
        for (int i = 0; i < list.size(); i++) {
            if (stateCheckedMap.get(i)) {
                stateCheckedMap.put(i, false);
            } else {
                stateCheckedMap.put(i, true);
                mCheckedData.add(list.get(i));
            }
            subject_list_ylx.setItemChecked(i, stateCheckedMap.get(i));//这个好行可以控制ListView复用的问题，不设置这个会出现点击一个会选中多个
        }
        customAdapterYLX.notifyDataSetChanged();
    }
    //选中全部
    private void selectAll() {
        mCheckedData.clear();//清空之前选中数据
        if (isSelectedAll) {
            setStateCheckedMap(true);//将CheckBox的所有选中状态变成选中
            isSelectedAll = false;
            mCheckedData.addAll(list);//把所有的数据添加到选中列表中
        } else {
            setStateCheckedMap(false);//将CheckBox的所有选中状态变成未选中
            isSelectedAll = true;
        }
        customAdapterYLX.notifyDataSetChanged();
    }
    private void updateCheckBoxStatus(View view, int position) {
        CustomAdapterYLX.ViewHolder holder = (CustomAdapterYLX.ViewHolder) view.getTag();
        holder.check.toggle();//反转CheckBox的选中状态
        subject_list_ylx.setItemChecked(position, holder.check.isChecked());//长按ListView时选中按的那一项
        stateCheckedMap.put(position, holder.check.isChecked());//存放CheckBox的选中状态
        if (holder.check.isChecked()) {
            mCheckedData.add(list.get(position));//CheckBox选中时，把这一项的数据加到选中数据列表
        } else {
            mCheckedData.remove(list.get(position));//CheckBox未选中时，把这一项的数据从选中数据列表移除
        }
        customAdapterYLX.notifyDataSetChanged();
    }
    /**
     * 设置所有CheckBox的选中状态
     * */
    private void setStateCheckedMap(boolean isSelectedAll) {
        for (int i = 0; i < list.size(); i++) {
            stateCheckedMap.put(i, isSelectedAll);
            subject_list_ylx.setItemChecked(i, isSelectedAll);
        }
    }
    //进入错题重做页
    private void redoErrorQuestions(SubjectMsg msg) {
        if(msg.getType().equals("选择题")){
            Intent intent = new Intent();
            intent.setClass(getActivity().getApplicationContext(), RedoWrongQuestionsActivity.class);
            intent.putExtra("subject", msg);
            startActivity(intent);
        }else{
            Intent intent = new Intent();
            intent.setClass(getActivity().getApplicationContext(), RedoWrongBigQuestionActivity.class);
            intent.putExtra("subject", (Serializable) msg);
            startActivity(intent);
        }
    }

    private void showPopupMenu(View view) {
        //popupMenu显示在view的相对位置
        PopupMenu popupMenu = new PopupMenu(getActivity().getApplicationContext(), view);
        //menu布局
        popupMenu.getMenuInflater().inflate(R.menu.ylx_type_time_layout, popupMenu.getMenu());
        menu = popupMenu.getMenu();
        // menu的item点击事件
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1://选择题
                        Log.i("www", "onMenuItemClick: "+item.getTitle().toString().trim());
                        tyep = item.getTitle().toString().trim();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                searchSubjectMsgBySubjectAndTag(subject,tag,date,tyep);
                            }
                        }).start();
                        break;
                    case R.id.item2://填空题
                        Log.i("www", "onMenuItemClick: "+item.getTitle().toString().trim());
                        tyep = item.getTitle().toString().trim();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                searchSubjectMsgBySubjectAndTag(subject,tag,date,tyep);
                            }
                        }).start();
                        break;
                    case R.id.item3://大题
                        Log.i("www", "onMenuItemClick: "+item.getTitle().toString().trim());
                        tyep = item.getTitle().toString().trim();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                searchSubjectMsgBySubjectAndTag(subject,tag,date,tyep);
                            }
                        }).start();
                        break;
                    case R.id.item_two://选择时间
                        Log.i("www", "onMenuItemClick: "+item.getTitle().toString().trim());
                        Calendar ca = Calendar.getInstance();
                        mYear = ca.get(Calendar.YEAR);
                        mMonth = ca.get(Calendar.MONTH);
                        mDay = ca.get(Calendar.DAY_OF_MONTH);
                        new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme,onDateSetListener, mYear, mMonth, mDay).show();
                        break;
                }

                return false;
            }
        });
        // PopupMenu关闭事件
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
            }
        });
        popupMenu.show();
    }

    //按照学科（数学、语文、英语）搜索题目，放入listView中
    private void searchSubjectMsgBySubjectAndTag (String subject, String tag,String date,String type){
        okHttpClient = new OkHttpClient();
        FormBody formBody = new FormBody.Builder()
                .add("subject", subject)
                .add("tag", tag)
                .add("date",date)
                .add("type",type)
                .add("uId", uId)
                .build();
        String url = "http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/subjectMsg/findByTag";
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
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        Type type = new TypeToken<List<SubjectMsg>>() {
                        }.getType();
                        //反序列化
                        lists = new ArrayList<>();
                        lists = gson.fromJson(response.body().string(), type);
                        Message msg = Message.obtain();
                        if (lists.size() != 0) {
                            msg.obj = "刷新";
                        } else {
                            msg.obj = "无数据";
                        }
                        handler.sendMessage(msg);
                    }
                });
            }
        }).start();
    }
    //批量删除
    private void deleteSubjectMsgById (List<Integer> ids){
        okHttpClient = new OkHttpClient();
        String idList = new Gson().toJson(ids);
        Log.i("www", "deleteSubjectMsgById: id集合"+idList);
        FormBody formBody = new FormBody.Builder()
                .add("ids",idList)
                .build();
        String url = "http://" + getResources().getString(R.string.ipConfig) + ":8080/Turings/subjectMsg/deleteSubjectMsgById";
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
                        Log.i("lww", "删除成功");
                        Message msg = Message.obtain();
                        if (response.body().toString() !=null && !response.body().toString().equals("")) {
                            msg.obj = "删除成功";
                        } else {
                            msg.obj = "删除失败";
                        }
                    }
                });
            }
        }).start();
    }
}
