package org.turings.turings.index.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


import org.turings.turings.R;
import org.turings.turings.index.entity.Pre;
import org.turings.turings.index.util.KeyWordUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>implements TextToSpeech.OnInitListener{
    private Context context;
    private List<Pre> pres=new ArrayList<>();
    private int itemlayout;
    private View view;
    private TextToSpeech tts;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String string=(String)msg.obj;
            if(string.equals("ok")){
                Toast.makeText(context,"阅读完成",Toast.LENGTH_LONG).show();
            }
        }
    };
    public RecyclerListAdapter() {
    }

    public RecyclerListAdapter(Context context, List<Pre> pres, int itemlayout, View view) {
        this.context = context;
        this.pres = pres;
        this.itemlayout = itemlayout;
        this.view = view;
        tts=new TextToSpeech(context,this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        RecyclerView.ViewHolder holder = null;
        v = LayoutInflater.from(context).inflate(itemlayout,parent, false);
        holder = new ItemHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final String[] array=context.getResources().getStringArray(R.array.array_text);
        ItemHolder holdert = (ItemHolder) holder;
        holdert.tv_title.setText(pres.get(position).getTitle());
        holdert.tv_content.setText(pres.get(position).getContent());
        holdert.tv_author.setText(pres.get(position).getAuthor());
        holdert.tv_num.setText(pres.get(position).getNum());
        holdert.btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCustomMessageDialog(position,array,((ItemHolder) holder).tv_num);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(null!=pres){
            return pres.size();
        }
        return 0;
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

    private class ItemHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private TextView tv_content;
        private TextView tv_author;
        private TextView tv_num;
        private Button btn_read;
        public ItemHolder(View v) {
            super(v);
            tv_title=v.findViewById(R.id.tv_title);
            tv_content=v.findViewById(R.id.tv_content);
            tv_author=v.findViewById(R.id.tv_author);
            tv_num=v.findViewById(R.id.tv_num);
            btn_read=v.findViewById(R.id.btn_read);
        }
    }
    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void showCustomMessageDialog(final int position,String[] array,final TextView tv_num){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(pres.get(position).getTitle());
        //设置内容视图
        View view = LayoutInflater.from(context).inflate(R.layout.lph_dialog,null);
        //得到自定义视图中的控件对象
        TextView textView = view.findViewById(R.id.tv_text);
        SpannableString sbs= KeyWordUtil.setSpannableString(Color.BLACK,pres.get(position).getContent(),array);
        textView.setText(sbs);
        textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        ImageView img_start=view.findViewById(R.id.img_start);
        ImageView img_end=view.findViewById(R.id.img_end);
        img_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.speak(pres.get(position).getContent(),TextToSpeech.QUEUE_FLUSH,null);
            }
        });
        img_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tts.stop();
            }
        });
        builder.setView(view);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tts.stop();
                int num=Integer.valueOf(pres.get(position).getNum())+1;
                pres.get(position).setNum(num+"");
                tv_num.setText(num+"");
                sendToServer(pres.get(position).getId(),num+"");
            }
        });

        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPink));
    }
    public void sendToServer(final int id,final String num){
        new Thread(){
            @Override
            public void run() {
                try {
                    URL url = new URL("http://"+context.getResources().getString(R.string.lphipConfig)+":8080/Turings/lph/updatePre?id="+id+"&num="+num);
                    URLConnection conn = url.openConnection();
                    InputStream in = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in, "utf-8"));
                    String info = reader.readLine();
                    Message message=Message.obtain();
                    message.what=100;
                    message.obj=info;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
