package com.rance.lvlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

public class Blur {
    private static final float SCALE = 0.4F;

    private float radius;

    private Thread blurThread;
    private Context context;
    private Bitmap inBitmap;
    private Callback callback;

    public Blur() {
        initThread();
    }

    private void initThread() {
        blurThread = new Thread(new Runnable() {
            @Override
            public void run() {
                final Bitmap blurred = getBlurBitmap(context, inBitmap, radius);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (callback != null) {
                            callback.onBlurred(blurred);
                        }
                    }
                });
            }
        });
    }

    public void setParams(Callback callback, Context context, Bitmap inBitmap, float radius) {
        this.callback = callback;
        this.context = context;
        this.inBitmap = inBitmap;
        this.radius = radius;
    }

    public void execute() {
        blurThread.run();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)

    private Bitmap getBlurBitmap(Context context, Bitmap inBitmap, float radius) {
        if (context == null || inBitmap == null) {
            throw new IllegalArgumentException("have not called setParams() before call execute()");
        }

        int width = Math.round(inBitmap.getWidth() * SCALE);
        int height = Math.round(inBitmap.getHeight() * SCALE);

        Bitmap in = Bitmap.createScaledBitmap(inBitmap, width, height, false);
        Bitmap out = Bitmap.createBitmap(in);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));

        Allocation allocationIn = Allocation.createFromBitmap(rs, in);
        Allocation allocationOut = Allocation.createFromBitmap(rs, out);

        blurScript.setRadius(radius);
        blurScript.setInput(allocationIn);
        blurScript.forEach(allocationOut);
        allocationOut.copyTo(out);

        allocationIn.destroy();
        allocationOut.destroy();
        blurScript.destroy();
        rs.destroy();

        return out;
    }

    public interface Callback {
        void onBlurred(Bitmap blurredBitmap);
    }
}
