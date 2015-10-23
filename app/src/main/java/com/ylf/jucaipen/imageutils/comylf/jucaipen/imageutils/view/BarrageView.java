package com.ylf.jucaipen.imageutils.comylf.jucaipen.imageutils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Administrator on 2015/10/23.
 *
 *    自定义弹屏View
 */
public class BarrageView extends TextView {
    private Paint mPaint=new Paint();
    private  int posX;  //x 坐标

    private int windowWidth; //屏幕宽
    private int windowHeight; //屏幕高

    private  Thread thread;

    private int textSize = 30; //字体大小
    public static final int TEXT_MIN = 10;
    public static final int TEXT_MAX = 60;
    //字体颜色
    private int color = 0xffffffff;
    private Random random=new Random();
    private int posY;
    private  OnRollEndListener listener;
    /**
     * 滚动结束接听器
     */
   interface  OnRollEndListener{
       void onRollEnd();
   }

    public  void setOnRollEndListener(OnRollEndListener listener){
            this.listener=listener;
    }

    public BarrageView(Context context) {
        super(context);
        init();
    }
    public BarrageView(Context context, AttributeSet attr){
       super(context, attr);
        init();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(getText().toString(), posX, posY, mPaint);
        if(thread==null){
            thread=new RollThread();
            thread.start();
        }
    }

    private void init() {

        //1.设置文字大小
        textSize = TEXT_MIN + random.nextInt(TEXT_MAX - TEXT_MIN);
        mPaint.setTextSize(textSize);

        //2.设置文字颜色
        color = Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        mPaint.setColor(color);
        //得到屏幕宽高
        Rect rect=new Rect();
        getWindowVisibleDisplayFrame(rect);
        windowWidth=rect.width();
        windowHeight=rect.height();
        //设置x为屏幕宽度
        posX=windowWidth;

        //5.设置y为屏幕高度内内随机，需要注意的是，文字是以左下角为起始点计算坐标的，所以要加上TextSize的大小
        posY = textSize + random.nextInt(windowHeight - textSize);
    }

    class  RollThread extends  Thread{
        @Override
        public void run() {
            //文字动画
            while (true) {
                if(needStopRollThread()) {
                    if (listener != null) {
                        listener.onRollEnd();
                    }
                    post(new Runnable() { //从父类中移除本view
                        @Override
                        public void run() {
                            ((ViewGroup) BarrageView.this.getParent()).removeView(BarrageView.this);
                        }
                    });
                    break;
                }
                animLogic();
                //2.绘制图像
                postInvalidate();
                Log.i("111", "x:" + posX);
                //3.延迟，不然会造成执行太快动画一闪而过
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    /**
      * @return  判断是否退出线程
      */
    private boolean needStopRollThread() {
        if(posX <= -mPaint.measureText(getText().toString())){
            return  true;
        }
        return  false;
    }

    /*
    x坐标递减8
     */
    private void animLogic() {
         posX-=8;
    }
}
