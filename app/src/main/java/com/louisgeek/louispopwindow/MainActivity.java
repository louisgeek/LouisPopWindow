package com.louisgeek.louispopwindow;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @Bind(R.id.id_btn)
    Button idBtn;
    @Bind(R.id.id_btn_2)
    Button idBtn2;

    List<String> stringList=new ArrayList<>();
    PopupWindow popupWindow2;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();

    }

    private void initData() {
        for (int i = 0; i <20 ; i++) {

            stringList.add("内容"+i);
        }
    }

    private void initPopWindow(View v) {
        View view=LayoutInflater.from(this).inflate(R.layout.popwindow_content,null,false);
        GridView id_gv=ButterKnife.findById(view, R.id.id_gv);
        MyBaseAdapter myBaseAdapter=new MyBaseAdapter(stringList,this);
        id_gv.setAdapter(myBaseAdapter);
        id_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position"+position, Toast.LENGTH_SHORT).show();
            }
        });
        PopupWindow popupWindow=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
       // popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
       // popupWindow.setBackgroundDrawable(new ColorDrawable(0x00EEEEEE));
        /**setFocusable(true)：设置焦点，PopupWindow弹出后，所有的触屏和物理按键都由PopupWindows 处理。
         * 其他任何事件的响应都必须发生在PopupWindow消失之后，（home 等系统层面的事件除外）。
         * 比如这样一个PopupWindow出现的时候，按back键首先是让PopupWindow消失，第二次按才是退出 activity，
         * 准确的说是想退出activity你得首先让PopupWindow消失，因为不并是任何情况下按back PopupWindow都会消失，
         * 必须在PopupWindow设置了背景的情况下 。*/
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//必须设置  ps:xml bg和这个不冲突
        popupWindow.setFocusable(true);//设置后  达到返回按钮先消失popupWindow
       /* popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });*/

        popupWindow.showAsDropDown(v);
        popupWindow.setAnimationStyle(R.anim.anim_pop);//补间动画

    }

    @OnClick({R.id.id_btn, R.id.id_btn_2})
    public void onClick(View view) {
       switch (view.getId()){
           case R.id.id_btn:
               initPopWindow(view);
               break;
           case R.id.id_btn_2:
               initPopWindow2(view);
               break;
       }
    }

    private void initPopWindow2(View v) {
        View view=LayoutInflater.from(this).inflate(R.layout.popwindow_content_2,null,false);
        final TextView id_tv_1=ButterKnife.findById(view, R.id.id_tv_1);
        id_tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_1.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });
        final TextView id_tv_2=ButterKnife.findById(view, R.id.id_tv_2);
        id_tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_2.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });

        popupWindow2=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /**setFocusable(true)：设置焦点，PopupWindow弹出后，所有的触屏和物理按键都由PopupWindows 处理。
         * 其他任何事件的响应都必须发生在PopupWindow消失之后，（home 等系统层面的事件除外）。
         * 比如这样一个PopupWindow出现的时候，按back键首先是让PopupWindow消失，第二次按才是退出 activity，
         * 准确的说是想退出activity你得首先让PopupWindow消失，因为不并是任何情况下按back PopupWindow都会消失，
         * 必须在PopupWindow设置了背景的情况下 。*/
        popupWindow2.setBackgroundDrawable(new BitmapDrawable());//必须设置  ps:xml bg和这个不冲突
        popupWindow2.setFocusable(true);//设置后  达到返回按钮先消失popupWindow

        //popupWindow.setOutsideTouchable(false);//false点击popupwindow外部，popupwindow不会dismiss

     /*   popupWindow2.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.d(TAG, "onTouch: " + event.getAction());

                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    popupWindow2.dismiss();
                    Log.d(TAG, "onTouch: ACTION_OUTSIDE");
                    //return false;
                } else {
                    Log.d(TAG, "onTouch: NOT  ACTION_OUTSIDE");
                }
                return true;//拦截
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });*/
        // popupWindow2.setTouchable(false); // true设置PopupWindow可触摸
      //  popupWindow2.setOutsideTouchable(true);// true设置非PopupWindow区域可触摸
     /*   popupWindow2.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "onTouch: getAction"+event.getAction());
                Log.d(TAG, "onTouch: getActionMasked"+event.getActionMasked());
                *//**** 如果点击了popupwindow的外部，popupwindow也会消失 ****//*
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    Log.d(TAG, "onTouch: ACTION_OUTSIDE");
                    //popupWindow2.dismiss();
                    return true;
                }
                return false;
            }
        });*/

            popupWindow2.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

            configWindowAlpha(0.5f);
           // popupWindow2.setAnimationStyle(R.anim.anim_pop_2);//补间动画
           // popupWindow2.update();
            popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener()

            {
                @Override
                public void onDismiss () {
                configWindowAlpha(1.0f);
            }
            }

            );


        }

    private void configWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp=getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

}
