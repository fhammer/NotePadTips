package play.fzh.bj.notepad.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import play.fzh.bj.notepad.R;

public class ProgressView extends View {

    // 画实心圆的画笔
    private Paint mCirclePaint;
    // 画圆环的画笔
    private Paint mRingPaint;
    // 画字体的画笔
    private Paint mTextPaint;
    // 圆形颜色
    private int mCircleColor;
    // 圆环颜色
    private int mRingColor;
    // 圆环背景颜色
    private int mRingBgColor;
    // 半径
    private float mRadius;
    // 圆环半径
    private float mRingRadius;
    // 圆环宽度
    private float mStrokeWidth;
    // 圆环宽度
    private float mBgStrokeWidth;
    // 圆心x坐标
    private int mXCenter;
    // 圆心y坐标
    private int mYCenter;
    // 字的长度
    private float mTxtWidth;
    // 字的高度
    private float mTxtHeight;

    // 总进度
    private int mTotalProgress = 100;
    // 当前进度
    private float mProgress;
    //需要显示的占用总进度
    private float mNeedProgress;

    private float mSpeed = 0.5f;

    // 画实心圆的画笔
    private Paint mRingBgPaint;

    // 画实边界
    private Paint mBrigeBgPaint;

    public ProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 获取自定义的属性
        initAttrs(context, attrs);
        initVariable();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.ProgressView, 0, 0);
        mRadius = typeArray.getDimension(R.styleable.ProgressView_radius, 80);
        mStrokeWidth = typeArray.getDimension(R.styleable.ProgressView_strokeWidth, 10);
        mBgStrokeWidth = typeArray.getDimension(R.styleable.ProgressView_ringBgStrokeWidth, mStrokeWidth);
        mCircleColor = typeArray.getColor(R.styleable.ProgressView_circleColor, 0xFFFFFFFF);
        mRingColor = typeArray.getColor(R.styleable.ProgressView_ringColor, 0xFFFFFFFF);
        mRingBgColor = typeArray.getColor(R.styleable.ProgressView_ringBgColor, 0xFFFFFFFF);
        mSpeed = typeArray.getFloat(R.styleable.ProgressView_speed, 0.5f);

        mRingRadius = mRadius + mStrokeWidth / 2;
    }

    private void initVariable() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(mCircleColor);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mBrigeBgPaint = new Paint();
        mBrigeBgPaint.setAntiAlias(true);
        mBrigeBgPaint.setColor(mRingColor);
        mBrigeBgPaint.setStyle(Paint.Style.FILL);

        mRingPaint = new Paint();
        mRingPaint.setAntiAlias(true);
        mRingPaint.setColor(mRingColor);
        mRingPaint.setStyle(Paint.Style.STROKE);
        mRingPaint.setStrokeWidth(mStrokeWidth);

        mRingBgPaint = new Paint();
        mRingBgPaint.setAntiAlias(true);
        mRingBgPaint.setColor(mRingBgColor);
        mRingBgPaint.setStyle(Paint.Style.STROKE);
        mRingBgPaint.setStrokeWidth(mBgStrokeWidth);

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setARGB(255, 255, 255, 255);
        mTextPaint.setTextSize(mRadius / 2);

        Paint.FontMetrics fm = mTextPaint.getFontMetrics();
        mTxtHeight = (int) Math.ceil(fm.descent - fm.ascent);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        mXCenter = getWidth() / 2;
        mYCenter = getHeight() / 2;

        //画实体圆心
        canvas.drawCircle(mXCenter, mYCenter, mRadius, mCirclePaint);
        //画空心圆作为背景
        canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingBgPaint);

        canvas.drawCircle(mXCenter, mYCenter - mRingRadius, mStrokeWidth / 2, mBrigeBgPaint);

        if (mProgress > 0 || mNeedProgress > 0) {
            RectF oval = new RectF();
            oval.left = (mXCenter - mRingRadius);
            oval.top = (mYCenter - mRingRadius);
            oval.right = mRingRadius * 2 + (mXCenter - mRingRadius);
            oval.bottom = mRingRadius * 2 + (mYCenter - mRingRadius);
            float angle = ((float) mProgress / mTotalProgress) * 360;

            canvas.drawArc(oval, -90, angle, false, mRingPaint); //
//			canvas.drawCircle(mXCenter, mYCenter, mRadius + mStrokeWidth / 2, mRingPaint);
            String txt = mProgress + "%";
            mTxtWidth = mTextPaint.measureText(txt, 0, txt.length());
            canvas.drawText(txt, mXCenter - mTxtWidth / 2, mYCenter + mTxtHeight / 4, mTextPaint);

            Log.d("Angle", Math.sin(angle * Math.PI / 180) + "");
            float cx = (float) (mXCenter + mRingRadius * Math.sin(angle * Math.PI / 180));
            float cy = (float) (mYCenter - mRingRadius * Math.cos(angle * Math.PI / 180));

            canvas.drawCircle(cx, cy, mStrokeWidth / 2, mBrigeBgPaint);
            mProgress = mProgress + mSpeed;
            if (mProgress <= mNeedProgress) {
                invalidate();
            }
        }

    }

    public void setProgress(float progress) {
//        mProgress = progress;
        mProgress = 0;
        mNeedProgress = progress;
        postInvalidate();
    }

    public float getSpeed() {
        return mSpeed;
    }

    public void setSpeed(float pSpeed) {
        mSpeed = pSpeed;
    }

    public int getTotalProgress() {
        return mTotalProgress;
    }

    public void setTotalProgress(int pTotalProgress) {
        mTotalProgress = pTotalProgress;
    }
}
