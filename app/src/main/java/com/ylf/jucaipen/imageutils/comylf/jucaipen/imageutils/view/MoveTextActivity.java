package com.ylf.jucaipen.imageutils.comylf.jucaipen.imageutils.view;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.ViewGroup;

import com.ylf.jucaipen.imageutils.R;

import java.util.Random;

/**
 * Created by Administrator on 2015/10/23.
 */
public class MoveTextActivity  extends Activity{
    //两两弹幕之间的间隔时间
    public static final int DELAY_TIME = 800;
    private Random random = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_move_text);
        initView();

    }
    private void initView() {
        final String texts[] = getResources().getStringArray(R.array.default_text_array);
        //设置宽高全屏
        final ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        final Handler handler = new Handler();
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                //新建一条弹幕，并设置文字
                BarrageView barrageView=new BarrageView(MoveTextActivity.this);
                barrageView.setText(texts[random.nextInt(texts.length)]);
                addContentView(barrageView,lp);
                handler.postDelayed(this,DELAY_TIME);
            }
        };
        handler.post(runnable);
    }
}
