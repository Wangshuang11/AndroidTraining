package org.turings.turings.mistaken.customAdapterAndDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.turings.turings.R;
import org.turings.turings.mistaken.entity.SubjectMsg;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomAdapterInResultYLX extends BaseAdapter {
    private Context context;
    private int itemLayout;
    private List<SubjectMsg> subjectMsgs;
    private List<Integer> list;//题号
    private Map<Integer,Integer> answerResult = new HashMap<>();

    public CustomAdapterInResultYLX(Context context, int itemLayout, List<SubjectMsg> subjectMsgs, Map<Integer, Integer> answerResult, List<Integer> lists) {
        this.context = context;
        this.itemLayout = itemLayout;
        this.subjectMsgs = subjectMsgs;
        this.answerResult = answerResult;
        this.list = lists;
    }

    @Override
    public int getCount() {
        if(subjectMsgs != null){
            return subjectMsgs.size();
        }else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        if(subjectMsgs != null){
            return subjectMsgs.get(position);
        }else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        if(subjectMsgs != null){
            return position;
        }else {
            return 0;
        }
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //Item的位置
        final int listPosition = i;
        ViewHolder holder = null;
        if(null == view){
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(itemLayout,null);
            holder.subjectSub = view.findViewById(R.id.subject_ylx);
            holder.subjectImgSub=view.findViewById(R.id.subject_img_ylx);
            holder.resultImg = view.findViewById(R.id.img_result);
            holder.resultTv = view.findViewById(R.id.tv_result_ylx);
            holder.btnCheck = view.findViewById(R.id.check_ylx);
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();

        }
        //获取图片id,从data的files目录下取出来
//        String dataFileStr = context.getFilesDir().getAbsolutePath()+"/"+list.get(position).getTitleImg();
//        Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
        //添加图片
//        if(subjectMsgs.get(position).getTitleImg()!=null){
//            String dataFileStr = context.getFilesDir().getAbsolutePath()+"/"+subjectMsgs.get(position).getTitleImg();
//            Bitmap bitmap = BitmapFactory.decodeFile(dataFileStr);
//            //添加图片
////            holder.tvImg.setImageResource(R.mipmap.aylx);
//            holder.subjectImgSub.setImageBitmap(bitmap);
//            holder.subjectImgSub.setVisibility(View.VISIBLE);
//            holder.subjectSub.setVisibility(View.GONE);
//        }else {
        if(subjectMsgs.get(i).getType().equals("选择题")){
            holder.subjectSub.setText(subjectMsgs.get(i).getContent()+"\n"+subjectMsgs.get(i).getOptionA()
                    +"\n"+subjectMsgs.get(i).getOptionB()+"\n"
                    +subjectMsgs.get(i).getOptionC()+"\n"
                    +subjectMsgs.get(i).getOptionD());
        }else {
            holder.subjectSub.setText(subjectMsgs.get(i).getContent());
        }
        holder.subjectImgSub.setVisibility(View.GONE);
        holder.subjectSub.setVisibility(View.VISIBLE);
//        }
//        holder.imageView.setImageBitmap(bitmap);
        //判断答没答对
        if(answerResult.get(list.get(i)) == 1){//答对了
            holder.resultImg.setImageResource(R.mipmap.yesylx);
            holder.resultTv.setText("恭喜你答对了");
        }else {
            holder.resultImg.setImageResource(R.mipmap.noylx);
            holder.resultTv.setText("很遗憾你答错了");
        }
        holder.btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击返回题号
                mOnItemClickListener.onNumClick(listPosition);
            }
        });
        return view;
    }
    public final static class ViewHolder{
        public TextView subjectSub;//题目
        public ImageView subjectImgSub;//题目图片
        public ImageView resultImg;//答题结果图片
        public TextView resultTv;//答题结果
        public Button btnCheck;//去看题目详情
    }
    /**
     * 点击查看题目按钮的监听接口
     */
    public interface onItemClickListener {
        void onNumClick(int position);
    }

    private onItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(onItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener  = mOnItemClickListener;
    }
}
