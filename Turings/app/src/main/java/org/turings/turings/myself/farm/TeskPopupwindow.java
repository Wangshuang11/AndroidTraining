package org.turings.turings.myself.farm;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;

import org.turings.turings.R;

public class TeskPopupwindow extends PopupWindow {

    private View mView; // PopupWindow 菜单布局
    private Activity mContext; // 上下文参数

    public TeskPopupwindow(Activity context) {
            super(context);
            this.mContext = context;
            Init();
    }
    private ImageView imageView;

    /**
     * 设置布局以及点击事件
     */
    private void Init() {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.jxy_tesk_popup_item, null);
//        Button btn_camera = (Button) mView.findViewById(R.id.icon_btn_camera);
//        Button btn_select = (Button) mView.findViewById(R.id.icon_btn_select);
//        Button btn_cancel = (Button) mView.findViewById(R.id.icon_btn_cancel);
//
//        btn_select.setOnClickListener(mSelectListener);
//        btn_camera.setOnClickListener(mCaptureListener);
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dismiss();
//            }
//        });

        WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
        lp.alpha = 1.0f;
        mContext.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        mContext.getWindow().setAttributes(lp);
        // 导入布局
        this.setContentView(mView);
        // 设置动画效果
        this.setAnimationStyle(R.style.popwindow_anim_style);
        this.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置可触
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x0000000);
        this.setBackgroundDrawable(dw);
//         单击弹出窗以外处 关闭弹出窗
        mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = mView.findViewById(R.id.popup_bg).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
}