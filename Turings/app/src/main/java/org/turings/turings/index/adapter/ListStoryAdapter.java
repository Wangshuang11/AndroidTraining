package org.turings.turings.index.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.sackcentury.shinebuttonlib.ShineButton;


import org.turings.turings.R;
//import org.turings.turings.index.CommonActivity;
import org.turings.turings.index.CommonActivity;
import org.turings.turings.index.entity.Story;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Locale;


public class ListStoryAdapter extends BaseAdapter implements TextToSpeech.OnInitListener{
    private Context context;
    private int itemlayout;
    private List<Story> stories;
    private View viewp;
    private TextToSpeech tts;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string=(String)msg.obj;
            if(string.startsWith("lph_one")){
                Toast.makeText(context,"收藏成功",Toast.LENGTH_SHORT).show();
            }else if(string.startsWith("two")){
                Toast.makeText(context,"取消收藏",Toast.LENGTH_SHORT).show();
            }
        }
    };
    public ListStoryAdapter() {
    }

    public ListStoryAdapter(Context context, int itemlayout, List<Story> stories, View viewp) {
        this.context = context;
        this.itemlayout = itemlayout;
        this.stories = stories;
        this.viewp = viewp;
        tts=new TextToSpeech(context,this);
    }

    @Override
    public int getCount() {
        if(null!=stories){
            return stories.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int i) {
        if(null!=stories){
            return stories.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(null==view){
            view= LayoutInflater.from(context).inflate(itemlayout,null);
            viewHolder=new ViewHolder();
            viewHolder.tv_titlen=view.findViewById(R.id.tv_titlen);
            viewHolder.tv_namen=view.findViewById(R.id.tv_namen);
            viewHolder.tv_contentn=view.findViewById(R.id.tv_contentn);
            viewHolder.iv_imgn=view.findViewById(R.id.iv_imgn);
            viewHolder.iv_imgr=view.findViewById(R.id.iv_imgr);
            viewHolder.shoucang=view.findViewById(R.id.shoucang);
            viewHolder.btn_sc=view.findViewById(R.id.btn_sc);
//            viewHolder.pinglun=view.findViewById(R.id.pinglun);
            viewHolder.btn_con=view.findViewById(R.id.btn_con);
            view.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.tv_titlen.setText(stories.get(i).getTitle());
        viewHolder.tv_namen.setText(stories.get(i).getName());
        viewHolder.tv_contentn.setText(stories.get(i).getContent());
        viewHolder.shoucang.setText(stories.get(i).getStarnum());
        RequestOptions  ro=new RequestOptions().placeholder(R.drawable.loading);
        int imgnId=context.getResources().getIdentifier(stories.get(i).getSmallimg(),"drawable",context.getPackageName());
        Glide.with(context).load(imgnId).apply(ro).into( viewHolder.iv_imgn);
        Glide.with(context).load(stories.get(i).getBigimg()).apply(ro).into( viewHolder.iv_imgr);
        viewHolder.shoucang.setText(stories.get(i).getStarnum());
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btn_sc.setOnCheckStateChangeListener(new ShineButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(View view, boolean checked) {
                if(checked==true){//+1
                    int starnum=Integer.valueOf(stories.get(i).getStarnum());
                    starnum=starnum+1;
                    finalViewHolder.shoucang.setText(starnum+"");
                    stories.get(i).setStarnum(starnum+"");
                    sendToServer(stories.get(i).getStarnum(),stories.get(i).getId(),1);
                }else if(checked==false){
                    int starnum=Integer.valueOf(stories.get(i).getStarnum());
                    starnum=starnum-1;
                    if (starnum<0){
                        starnum=0;
                    }
                    finalViewHolder.shoucang.setText(starnum+"");
                    stories.get(i).setStarnum(starnum+"");
                    sendToServer(stories.get(i).getStarnum(),stories.get(i).getId(),2);
                }

            }
        });
//
//        viewHolder.pinglun.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(context, CommonActivity.class);
//                String string=new Gson().toJson(stories.get(i));
//                intent.putExtra("story",string);
//                context.startActivity(intent);
//            }
//        });
        viewHolder.btn_con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                final PopupWindow popupWindow=new PopupWindow(context);
//                LayoutInflater layoutInflater=LayoutInflater.from(context);
//                View view_pop=layoutInflater.inflate(R.layout.lph_popupwindow_layout,null);
//                TextView text_pop= view_pop.findViewById(R.id.text_pop);
//                text_pop.setText(stories.get(i).getContent());
//                ImageView btn_ok=view_pop.findViewById(R.id.btn_ok);
//                ImageView img_start=view_pop.findViewById(R.id.img_start);
//                ImageView img_end=view_pop.findViewById(R.id.img_end);
//                btn_ok.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        popupWindow.dismiss();
//                    }
//                });
//                img_start.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        tts.speak(stories.get(i).getContent(),TextToSpeech.QUEUE_FLUSH,null);
//                    }
//                });
//                img_end.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        tts.stop();
//                    }
//                });
//                popupWindow.setContentView(view_pop);
//                popupWindow.showAtLocation(viewp, Gravity.FILL_HORIZONTAL,0,0);
                Intent intent=new Intent(context, CommonActivity.class);
                String string=new Gson().toJson(stories.get(i));
                intent.putExtra("story",string);
                context.startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onInit(int i) {
        if(i==TextToSpeech.SUCCESS){
            int result=tts.setLanguage(Locale.US);
            if (result==TextToSpeech.LANG_MISSING_DATA ||result==TextToSpeech.LANG_NOT_SUPPORTED){
                Toast.makeText(context,"wrong",Toast.LENGTH_LONG).show();
            }
        }
    }
    static final class ViewHolder{
        private TextView tv_titlen;
        private ImageView iv_imgn;
        private ImageView iv_imgr;
        private TextView tv_namen;
        private TextView tv_contentn;
        private TextView shoucang;
        private ShineButton btn_sc;
        //        private Button pinglun;
        private Button btn_con;
    }
    private void sendToServer(final String starnum, final int id,final int flag) {
        new Thread() {
            @Override
            public void run() {
                try {
                    String string = "http://"+context.getResources().getString(R.string.lphipConfig)+":8080/Turings/lph/updateStory?starnum="+starnum+"&id="+id+"&flag="+flag;
                    URL url = new URL(string);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    wrapperMessage(info);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
    private void wrapperMessage(String info) {
        Message msg = Message.obtain();
        msg.obj = info;
        handler.sendMessage(msg);
    }
}
