package com.github.wangman.cuponview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.github.wangman.cuponview.R;

/**
 * Created by admin on 2016/10/27.
 */

/**
 * 自定义ViewGroup一定要在xml中设置背景色  这样onDraw()  才会被调用
 */
public class CuponView extends LinearLayout {

    private float cuponCircleRadio,cuponCircleRadioRight;
    private float cuponCircleStart;
    private float cuponCircleEnd;
    private int leftCircleCount, rightCircleCount;
    private float leftgap,rightgap;
    private Paint mPaint;
    private Path mPath=new Path();
    private int rightAreaColor;
    private float rihtAreaWeight;
    public CuponView(Context context) {
        super(context);
    }

    public CuponView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(attrs);
    }

    public CuponView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        if (null != attrs) {
            mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
            mPaint.setColor(Color.parseColor("#eeeeee"));
            mPaint.setStyle(Paint.Style.FILL);
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CuponView);
            cuponCircleRadio = array.getDimension(R.styleable.CuponView_cuponCircleRadio, 5);
            leftgap =cuponCircleRadio/3*2;

            cuponCircleRadioRight=cuponCircleRadio/3;
            rightgap=cuponCircleRadioRight;
            cuponCircleStart = array.getDimension(R.styleable.CuponView_cuponCircleStart, 15);
            cuponCircleEnd = array.getDimension(R.styleable.CuponView_cuponCircleEnd, 15);
            array.recycle();
        }

    }


    /**
     * 设置左边的圆圈个数
     *
     * @param height
     */
    private void setLeftCircleCount(int height) {

//        leftCircleCount = (int) ((height-cuponCircleStart*2)/ ((cuponCircleRadio * 2)+ leftgap));
        leftCircleCount=5;
    }

    /**
     * 设置右边的圆圈个数
     *
     * @param height
     */
    private void setRightCircleCount(int height) {
        rightCircleCount = (int) ((height-cuponCircleRadio*2) / ((cuponCircleRadioRight*2)+ rightgap));
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rihtAreaWeight=w/7*5;
        cuponCircleRadio=h/20;
        leftgap =cuponCircleRadio;
        cuponCircleRadioRight=cuponCircleRadio/3;
        rightgap=cuponCircleRadioRight;
        cuponCircleStart=(h-5*cuponCircleRadio*2-4*cuponCircleRadio)/2;
        cuponCircleEnd=cuponCircleStart;
        setLeftCircleCount(h);
        setRightCircleCount(h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode=MeasureSpec.getMode(heightMeasureSpec);
         int height=MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode==MeasureSpec.AT_MOST){
            setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec),dip2px(height));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#eeeeee"));
        drawbgArc(canvas);
        drawRightAreaColor(canvas);
        mPaint.setColor(Color.parseColor("#eeeeee"));
        draLeftCircle(canvas);
        drawRightFirstCircle(canvas);
        draRightCircle(canvas);
        drawRightLastCircle(canvas);
        setRightAreaColor(Color.parseColor("#FF4081"));
    }
    private void drawbgArc(Canvas  canvas){
        mPaint.setColor(Color.WHITE);
        RectF rect=new RectF(0,0,getWidth(),getHeight());
        canvas.drawRoundRect(rect, dip2px(5),dip2px(5),mPaint);

    }
    public  int dip2px(float dpValue) {
        return (int)(dpValue * getContext().getResources().getDisplayMetrics().density + 0.5F);
    }
    /**
     * 绘制右边区域的颜色
     *
     * @param canvas
     */
    private void drawRightAreaColor(Canvas canvas){
        mPaint.setColor(rightAreaColor);
        RectF rectF=new RectF(rihtAreaWeight , 0, getWidth(), getHeight());
        canvas.drawRoundRect(rectF,dip2px(5),dip2px(5),mPaint);
    }
    /**
     *
     * 绘制左边的的圆圈
     */
    private void draLeftCircle(Canvas canvas) {
        for (int i = 0; i <leftCircleCount ; i++) {
            float y=cuponCircleStart+cuponCircleRadio+(i*cuponCircleRadio*2)+ leftgap *i;
            canvas.drawCircle(0,y,cuponCircleRadio,mPaint);
        }
    }
    /**
     * 绘制右边第一个圆圈
     */
    private void drawRightFirstCircle(Canvas canvas){
        canvas.drawCircle(rihtAreaWeight,0,cuponCircleRadio,mPaint);
    }
    /**
     * 绘制右边最后一个一个圆圈
     */
    private void drawRightLastCircle(Canvas canvas){
        canvas.drawCircle(rihtAreaWeight,getHeight(),cuponCircleRadio,mPaint);
    }
    /**
     * 绘制右边的圆圈
     */
    private void draRightCircle(Canvas canvas)
    {
        for (int i = 0; i <rightCircleCount ; i++)
        {
            float y=cuponCircleRadio+ rightgap+cuponCircleRadioRight+(i*cuponCircleRadioRight*2)+ rightgap *i;
            canvas.drawCircle(rihtAreaWeight,y,cuponCircleRadioRight,mPaint);
        }
    }

    public void setRightAreaColor(int color)
    {
        this.rightAreaColor=color;
        invalidate();
    }
}
