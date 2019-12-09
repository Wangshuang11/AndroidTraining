package org.turings.turings.index.fragment;


import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.turings.turings.R;
import org.turings.turings.index.gw.CouseXiangqing.CourseDetail;
import org.turings.turings.index.gw.GrideBooks.GrideBooks;
import org.turings.turings.index.gw.HotBooks.HotBook;
import org.turings.turings.index.gw.HotBooks.HotBookAdapter;
import org.turings.turings.index.gw.HotCourses.HotCourse;
import org.turings.turings.index.gw.HotCourses.HotCourseAdapter;
import org.turings.turings.index.gw.MainSubjects.Subject;
import org.turings.turings.index.gw.MainSubjects.SubjectAdapter;
import org.turings.turings.index.gw.MeizuBanner.MZModeNotCoverFragment;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class FirstFragment extends Fragment{
    private   boolean isRun = true;
    private Thread thread;
    //主要的八个学科
    private List<Subject> subjects = new ArrayList<>();
    private RecyclerView lvStus;
    private SubjectAdapter subjectAdapter;


    private  ImageView gaokao;
    //    //倒计时
//    //倒计时
    private static TextView day,hour,second,minute;
    private static long mDay = 00;
    private static long mHour = 10;
    private static long mMin = 30;
    private static long mSecond = 9;// 天 ,小时,分钟,秒
    private static MyAsyncTask myAsyncTask;

//    private Handler timeHandler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if (msg.what==1) {
//                computeTime();
//                day.setText(mDay+"");
//                hour.setText(mHour+"");
//                minute.setText(mMin+"");
//                second.setText(mSecond+"");
//            }
//        }
//    };

    //精品课程
    private List<HotCourse> hotCourses = new ArrayList<>();
    private ListView courseListview;
    private HotCourseAdapter hotCourseAdapter;
    private TextView changeCourse;
    private Handler courseHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==2) {
                hotCourseAdapter.notifyDataSetChanged();
            }
        }
    };

    //图书
    private List<HotBook> mBook = new ArrayList<>();
    private RecyclerView bookList;
    private HotBookAdapter hotBookAdapter;

    //更多图书
    private  TextView gengduobook;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_first_fragment, container, false);
//        thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                // TODO Auto-generated method stub
//                while (isRun) {
//                    try {
//                        Thread.sleep(1000); // sleep 1000ms
//                        Message message = Message.obtain();
//                        message.what = 1;
//                        timeHandler.sendMessage(message);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        });
        //getActivity().onBackPressed();
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        //仿魅族轮播图
        Fragment fragment = MZModeNotCoverFragment.newInstance();
        getFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();

        //主要的八个学科
        lvStus = view.findViewById(R.id.mainSubjectsRecycleView);
        initSubjects();
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),5);
        lvStus.setLayoutManager(layoutManager);
        subjectAdapter = new SubjectAdapter(subjects);
        lvStus.setAdapter(subjectAdapter);


//        //倒计时
        day = view.findViewById(R.id.day);
        minute = view.findViewById(R.id.minute);
        hour = view.findViewById(R.id.hour);
        second = view.findViewById(R.id.second);



        SimpleDateFormat s1=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat s2=new SimpleDateFormat("yyyy年MM月dd日aHH:mm");
        Date d = new Date();

        try {
            Date d2=s1.parse("2020-06-07-08-00");
            long second = (d2.getTime()-d.getTime())/1000;
            mDay = (long) Math.floor(second/(3600*24));
            mHour = (long) (Math.floor(second/3600)-mDay*24);
            mMin = (long) (Math.floor(second/60)-mDay*24*60-mHour*60);
            mSecond= (long) (Math.floor(second)-mDay*24*60*60-mHour*60*60-mMin*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        startRun();
//        if (myAsyncTask != null){
//            myAsyncTask.cancel(true);
//        }
        if (myAsyncTask == null) {
            myAsyncTask = new MyAsyncTask();
            myAsyncTask.execute();
        }

        gaokao = view.findViewById(R.id.gaokaoImg);
        final  int mode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        if (mode == Configuration.UI_MODE_NIGHT_YES) {
            gaokao.setImageResource(R.drawable.thesun);
        } else {
            gaokao.setImageResource(R.drawable.thenight);
        }
        gaokao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mode == Configuration.UI_MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                getActivity().getWindow().setWindowAnimations(R.style.WindowAnimationFadeInOut);
                getActivity().recreate();
                //startActivity(new Intent(getContext(),CourseDetail.class));
//                myAsyncTask.cancel();
//                MyAsyncTask myAsyncTask = new MyAsyncTask();
//                myAsyncTask.execute();
            }
        });
        //精品课
        final ImageView huan = view.findViewById(R.id.huanyipi);
        courseListview = view.findViewById(R.id.courseList);
        initCourses();
        hotCourseAdapter = new HotCourseAdapter(getContext(),hotCourses);
        courseListview.setAdapter(hotCourseAdapter);
        courseListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), CourseDetail.class);
                intent.putExtra("parentId",hotCourses.get(position).getCourseId());
                //Toast.makeText(getContext(),"您点击了"+position+""+hotCourses.get(position).getCourseId(),Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });
        //换一批
        changeCourse = view.findViewById(R.id.changeCourse);
        changeCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hotCourses.clear();
                //旋转
                ObjectAnimator animator = ObjectAnimator.ofFloat(huan, "rotation", 0f, 360f);
                animator.setDuration(1000);
                animator.start();

                new Thread(){
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/IndexCourseServlet?key=change").build();
                        Call call = client.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.e("gw", "返回失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                Gson gson = new Gson();
                                Type type = new TypeToken<List<HotCourse>>() {}.getType();
                                String json = response.body().string();
                                List<HotCourse> courses = gson.fromJson(json, type);
                                for (int i = 0; i < courses.size(); i++) {
                                    HotCourse course = new HotCourse(courses.get(i).getCourseId(),courses.get(i).getCourseImage(),courses.get(i).getCourseTitle(),courses.get(i).getCoursePerson(),courses.get(i).getCourserData());
                                    hotCourses.add(course);
                                }
                                Message message = Message.obtain();
                                message.what = 2;
                                courseHandler.sendMessage(message);
                            }
                        });
                    }
                }.start();
            }
        });

        //各种图书
        initBooks();
        bookList= view.findViewById(R.id.bookList);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getContext());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        bookList.setLayoutManager(layoutManager1);
        hotBookAdapter = new HotBookAdapter(mBook);
        bookList.setAdapter(hotBookAdapter);

        //更多
        gengduobook = view.findViewById(R.id.gengduobook);
        gengduobook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), GrideBooks.class);
                startActivity(intent);
            }
        });




        return view;
    }
    //初始化主要学科
    private void initSubjects() {
        subjects.clear();
        for (int i = 0; i < 1; i++) {
            Subject math = new Subject("数学", R.drawable.submath);
            subjects.add(math);
            Subject chinese = new Subject("语文", R.drawable.subchinese);
            subjects.add(chinese);
            Subject english = new Subject("英语", R.drawable.subenglish);
            subjects.add(english);
            Subject history = new Subject("历史", R.drawable.subhistory);
            subjects.add(history);
            Subject physice = new Subject("物理", R.drawable.subphysice);
            subjects.add(physice);
            Subject dili = new Subject("地理", R.drawable.subdili);
            subjects.add(dili);
            Subject huaxue = new Subject("化学", R.drawable.subhuaxue);
            subjects.add(huaxue);
            Subject zhengzhi = new Subject("政治", R.drawable.subzhengzhi);
            subjects.add(zhengzhi);
            Subject shengwu = new Subject("生物", R.drawable.subshengwu);
            subjects.add(shengwu);
            Subject gengduo = new Subject("更多", R.drawable.subgengduo);
            subjects.add(gengduo);

        }
    }

    //初始化课程 选择播放量最多的两个显示
    private void initCourses() {
        hotCourses.clear();
        new Thread(){
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder().url("http://"+getResources().getString(R.string.ipConfig)+":8080/Turings/IndexCourseServlet?key=popular").build();
                Call call = client.newCall(request);
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("gw", "返回失败");
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Gson gson = new Gson();
                        Type type = new TypeToken<List<HotCourse>>() {}.getType();
                        String json = response.body().string();
                        Log.e("gw",json);
                        List<HotCourse> courses = gson.fromJson(json, type);
                        Log.e("gw",courses.get(1).getCourseId());
                        for (int i = 0; i < courses.size(); i++) {
                            Log.e("gw",courses.get(i).getCourseId());
                            HotCourse course = new HotCourse(courses.get(i).getCourseId(),courses.get(i).getCourseImage(),courses.get(i).getCourseTitle(),courses.get(i).getCoursePerson(),courses.get(i).getCourserData());
                            hotCourses.add(course);
                        }
                        Message message = Message.obtain();
                        message.what = 2;
                        courseHandler.sendMessage(message);

                    }
                });
            }
        }.start();
    }
    //初始化图书
    private void initBooks() {
        mBook.clear();
        for (int i = 0; i < 3; i++) {
            HotBook book1 = new HotBook("平凡的世界", R.drawable.book1);
            mBook.add(book1);
            HotBook book2 = new HotBook("白鹿原", R.drawable.book2);
            mBook.add(book2);
            HotBook book3 = new HotBook("浮生六记", R.drawable.book3);
            mBook.add(book3);
        }
    }

    //    //倒计时
//    //倒计时
    private void startRun() {
        if (!thread.isInterrupted()) {
            thread.interrupt();
        }
        thread.start();
    }
    //倒计时计算
    private static void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
                if (mHour < 0) {
                    // 倒计时结束
                    mHour = 23;
                    mDay--;
                }
            }
        }
    }

    public static class MyAsyncTask extends AsyncTask<String, Long, String>
    {
        /**
         * 异步任务：AsyncTask<Params, Progress, Result>
         * 1.Params:UI线程传过来的参数。
         * 2.Progress:发布进度的类型。
         * 3.Result:返回结果的类型。耗时操作doInBackground的返回结果传给执行之后的参数类型。
         *
         * 执行流程：
         * 1.onPreExecute()
         * 2.doInBackground()-->onProgressUpdate()
         * 3.onPostExecute()
         */
        @Override
        protected void onProgressUpdate(Long...longs)//执行操作中，发布进度后
        {
            day.setText(longs[0]+"");
            hour.setText(longs[1]+"");
            minute.setText(longs[2]+"");
            second.setText(longs[3]+"");


        }
        @Override
        protected void onPreExecute()//执行耗时操作之前处理UI线程事件
        {
            //Toast.makeText(getContext(),"1",Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(String...  params)//执行耗时操作
        {
            //在此方法执行耗时操作,耗时操作中发布进度，更新进度条
            while (true) {
                try {
                    Thread.sleep(1000); // sleep 1000ms
                    computeTime();
                    publishProgress(mDay,mHour,mMin,mSecond);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

//        @Override
//        protected void onPostExecute(String result)//执行耗时操作之后处理UI线程事件
//        {
//            //在此方法执行main线程操作
//            progressBar.setVisibility(View.GONE);//下载完成后，隐藏进度条
//            textView.setText(result);
//        }

    }

}
