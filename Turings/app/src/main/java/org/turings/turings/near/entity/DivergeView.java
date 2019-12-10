package org.turings.turings.near.entity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class DivergeView extends View implements Runnable {
    public static final float mDuration = 0.01F;
    public static final int mDefaultHeight = 100;
    protected static final long mQueenDuration = 200L;
    protected final Random mRandom;
    protected ArrayList<DivergeInfo> mDivergeInfos;
    protected List<Object> mQueen;
    protected PointF mPtStart;
    protected PointF mPtEnd;
    protected ArrayList<DivergeInfo> mDeadPool;
    private Paint mPaint;
    private DivergeViewProvider mDivergeViewProvider;
    private long mLastAddTime;
    private Thread mThread;
    private boolean mRunning;
    private boolean mIsDrawing;

    public DivergeView(Context context) {
        this(context, (AttributeSet) null);
    }

    public DivergeView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DivergeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mRandom = new Random();
        this.mDeadPool = new ArrayList();
        this.mLastAddTime = 0L;
        this.mRunning = true;
        this.mIsDrawing = false;
        this.init();
    }

    public void run() {
        while (this.mRunning) {
            if (this.mDivergeViewProvider != null && this.mQueen != null && !this.mIsDrawing && this.mDivergeInfos != null) {
                this.dealQueen();
                if (this.mDivergeInfos.size() != 0) {
                    this.dealDiverge();
                    this.mIsDrawing = true;
                    this.postInvalidate();
                }
            }
        }

        this.release();
    }

    private void dealDiverge() {
        for (int i = 0; i < this.mDivergeInfos.size(); ++i) {
            DivergeInfo divergeInfo = (DivergeInfo) this.mDivergeInfos.get(i);
            float timeLeft = 1.0F - divergeInfo.mDuration;
            divergeInfo.mDuration += 0.01F;
            float time1 = timeLeft * timeLeft;
            float time2 = 2.0F * timeLeft * divergeInfo.mDuration;
            float time3 = divergeInfo.mDuration * divergeInfo.mDuration;
            float x = time1 * this.mPtStart.x + time2 * divergeInfo.mBreakPoint.x + time3 * divergeInfo.mEndPoint.x;
            divergeInfo.mX = x;
            float y = time1 * this.mPtStart.y + time2 * divergeInfo.mBreakPoint.y + time3 * divergeInfo.mEndPoint.y;
            divergeInfo.mY = y;
            if (divergeInfo.mY <= divergeInfo.mEndPoint.y) {
                this.mDivergeInfos.remove(i);
                this.mDeadPool.add(divergeInfo);
                --i;
            }
        }

    }

    private void dealQueen() {
        long now = System.currentTimeMillis();
        if (this.mQueen.size() > 0 && now - this.mLastAddTime > 200L) {
            this.mLastAddTime = System.currentTimeMillis();
            DivergeInfo divergeInfo = null;
            if (this.mDeadPool.size() > 0) {
                divergeInfo = (DivergeInfo) this.mDeadPool.get(0);
                this.mDeadPool.remove(0);
            }

            if (divergeInfo == null) {
                divergeInfo = this.createDivergeNode(this.mQueen.get(0));
            }

            divergeInfo.reset();
            divergeInfo.mType = this.mQueen.get(0);
            this.mDivergeInfos.add(divergeInfo);
            this.mQueen.remove(0);
        }

    }

    private void init() {
        this.mPaint = new Paint(1);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setDivergeViewProvider(DivergeViewProvider divergeViewProvider) {
        this.mDivergeViewProvider = divergeViewProvider;
    }

    public PointF getStartPoint() {
        return this.mPtStart;
    }

    public boolean isRunning() {
        return this.mRunning;
    }

    public void startDiverges(Object obj) {
        if (this.mDivergeInfos == null) {
            this.mDivergeInfos = new ArrayList(30);
        }

        if (this.mQueen == null) {
            this.mQueen = Collections.synchronizedList(new ArrayList(30));
        }

        this.mQueen.add(obj);
        if (this.mThread == null) {
            this.mThread = new Thread(this);
            this.mThread.start();
        }

    }

    public void stop() {
        if (this.mDivergeInfos != null) {
            this.mDivergeInfos.clear();
        }

        if (this.mQueen != null) {
            this.mQueen.clear();
        }

        if (this.mDeadPool != null) {
            this.mDeadPool.clear();
        }

    }

    public void release() {
        this.stop();
        this.mPtEnd = null;
        this.mPtStart = null;
        this.mDivergeInfos = null;
        this.mQueen = null;
        this.mDeadPool = null;
    }

    public void setStartPoint(PointF point) {
        this.mPtStart = point;
    }

    public void setEndPoint(PointF point) {
        this.mPtEnd = point;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRunning = false;
    }

    protected void onDraw(Canvas canvas) {
        if (this.mRunning && this.mDivergeViewProvider != null && this.mDivergeInfos != null) {
            Iterator var2 = this.mDivergeInfos.iterator();

            while (var2.hasNext()) {
                DivergeInfo divergeInfo = (DivergeInfo) var2.next();
                this.mPaint.setAlpha((int) (255.0F * divergeInfo.mY / this.mPtStart.y));
                canvas.drawBitmap(this.mDivergeViewProvider.getBitmap(divergeInfo.mType), divergeInfo.mX, divergeInfo.mY, this.mPaint);
            }
        }

        this.mIsDrawing = false;
    }

    private PointF getBreakPointF(int scale1, int scale2) {
        PointF pointF = new PointF();
        pointF.x = (float) (this.mRandom.nextInt((this.getMeasuredWidth() - this.getPaddingRight() + this.getPaddingLeft()) / scale1) + this.getMeasuredWidth() / scale2);
        pointF.y = (float) (this.mRandom.nextInt((this.getMeasuredHeight() - this.getPaddingBottom() + this.getPaddingTop()) / scale1) + this.getMeasuredHeight() / scale2);
        return pointF;
    }

    protected DivergeInfo createDivergeNode(Object type) {
        PointF endPoint = this.mPtEnd;
        if (endPoint == null) {
            endPoint = new PointF((float) this.mRandom.nextInt(this.getMeasuredWidth()), 0.0F);
        }

        if (this.mPtStart == null) {
            this.mPtStart = new PointF((float) (this.getMeasuredWidth() / 2), (float) (this.getMeasuredHeight() - 100));
        }

        return new DivergeInfo(this.mPtStart.x, this.mPtStart.y, this.getBreakPointF(2, 3), endPoint, type);
    }

    public class DivergeInfo {
        public float mDuration = 0.0F;
        public PointF mBreakPoint;
        public PointF mEndPoint;
        public float mX;
        public float mY;
        public Object mType;
        public float mStartX;
        public float mStartY;

        public DivergeInfo(float x, float y, PointF breakPoint, PointF endPoint, Object type) {
            this.mEndPoint = endPoint;
            this.mX = x;
            this.mY = y;
            this.mStartX = x;
            this.mStartY = y;
            this.mBreakPoint = breakPoint;
            this.mType = type;
        }

        public void reset() {
            this.mDuration = 0.0F;
            this.mX = this.mStartX;
            this.mY = this.mStartY;
        }
    }

    public interface DivergeViewProvider {
        Bitmap getBitmap(Object var1);
    }
}
