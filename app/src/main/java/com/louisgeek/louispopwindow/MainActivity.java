package com.louisgeek.louispopwindow;

import android.animation.ObjectAnimator;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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
    @Bind(R.id.id_btn_3)
    Button idBtn3;
    @Bind(R.id.id_btn_4)
    Button idBtn4;

    List<String> stringList = new ArrayList<>();
    PopupWindow popupWindow2;
    private static final String TAG = "MainActivity";
    RelativeLayout id_rl_bg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

         id_rl_bg=ButterKnife.findById(this,R.id.id_rl_bg);

        initData();

    }

    private void initData() {
        for (int i = 0; i < 20; i++) {

            stringList.add("内容" + i);
        }
    }

    private void initPopWindow(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_content, null, false);
        GridView id_gv = ButterKnife.findById(view, R.id.id_gv);
        MyBaseAdapter myBaseAdapter = new MyBaseAdapter(stringList, this);
        id_gv.setAdapter(myBaseAdapter);
        id_gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "position" + position, Toast.LENGTH_SHORT).show();
            }
        });
        PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
       int screenW=getResources().getDisplayMetrics().widthPixels;
        popupWindow.setWidth(screenW);
        popupWindow.showAsDropDown(v);
        popupWindow.setAnimationStyle(R.anim.anim_pop);//补间动画
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                changeBg(true);
            }
        });

        //
        changeBg(false);


    }

    private void changeBg(boolean isBack) {
        int startColor =isBack?0xff818080:0xffffffff;//0xffff0000
        int endColor = isBack?0xffffffff:0xff818080;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
             ObjectAnimator anim= ObjectAnimator.ofArgb(id_rl_bg,"backgroundColor",startColor,endColor);
             anim.setDuration(300);
             anim.start();
        }else{
            id_rl_bg.setBackgroundColor(endColor);
        }
    }

    @OnClick({R.id.id_btn, R.id.id_btn_2, R.id.id_btn_3, R.id.id_btn_4})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn:
                initPopWindow(view);
                break;
            case R.id.id_btn_2:
                initPopWindow2(view);
                break;
            case R.id.id_btn_3:
                initPopupWindow3(view);
                break;
            case R.id.id_btn_4:
                initPopupWindow4(view);
                break;
        }
    }

    //点击里面响应，点击外面不消失【方法1】
    private void initPopWindow2(View v) {
        View view = LayoutInflater.from(this).inflate(R.layout.popwindow_content_2, null, false);
        final TextView id_tv_1 = ButterKnife.findById(view, R.id.id_tv_1);
        id_tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_1.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });
        final TextView id_tv_2 = ButterKnife.findById(view, R.id.id_tv_2);
        id_tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_2.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });

        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        popupWindow2 = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        /**setFocusable(true)：设置焦点，PopupWindow弹出后，所有的触屏和物理按键都由PopupWindows 处理。
         * 其他任何事件的响应都必须发生在PopupWindow消失之后，（home 等系统层面的事件除外）。
         * 比如这样一个PopupWindow出现的时候，按back键首先是让PopupWindow消失，第二次按才是退出 activity，
         * 准确的说是想退出activity你得首先让PopupWindow消失，因为不并是任何情况下按back PopupWindow都会消失，
         * 必须在PopupWindow设置了背景的情况下 。*/
        // popupWindow2.setBackgroundDrawable(new BitmapDrawable());//必须设置  ps:xml bg和这个不冲突
        // popupWindow2.setFocusable(true);//设置后  达到返回按钮先消失popupWindow
        // popupWindow2.setOutsideTouchable(false);
        // 不是PopupWindow
   /*     popupWindow2.getContentView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.d(TAG, "onTouch: " + event.getAction());
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow2.dismiss();
                    Log.d(TAG, "onTouch: dismiss" );
                    return true;

                }
               return false;

            }
        });*/
        //
        //jian
       /*!!!!!    @Override
        public void onBackPressed() {

            if (popupWindow2.isShowing()){
                popupWindow2.dismiss();
            }else{
                super.onBackPressed();
            }
        }
*/


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
/**
 * 第一个参数是View类型的parent,虽然这里参数名是parent，其实，不是把PopupWindow放到这个parent里，并不要求这个parent是一个ViewGroup，这个参数名让人误解。官方文档”a parent view to get the android.view.View.getWindowToken() token from
 “,这个parent的作用应该是调用其getWindowToken()方法获取窗口的Token,所以，只要是该窗口上的控件就可以了。
 */
        popupWindow2.showAtLocation(v, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

       configWindowAlpha(0.5f);

        // popupWindow2.setAnimationStyle(R.anim.anim_pop_2);//补间动画
        // popupWindow2.update();
        popupWindow2.setOnDismissListener(new PopupWindow.OnDismissListener()

                                          {
                                              @Override
                                              public void onDismiss() {
                                                  configWindowAlpha(1.0f);
                                              }
                                          }

        );


    }

    //点击里面响应，点击外面不消失【方法2】
    private void initPopupWindow3(View v) {
        final View popwindow_content_3 = LayoutInflater.from(this).inflate(R.layout.popwindow_content_3, null);
        final PopupWindow popupWindow3 = new PopupWindow(popwindow_content_3, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        final TextView id_tv_1 = ButterKnife.findById(popwindow_content_3, R.id.id_tv_13);
        id_tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_1.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });
        final TextView id_tv_2 = ButterKnife.findById(popwindow_content_3, R.id.id_tv_23);
        id_tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_2.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });

        //设置点击窗口外边窗口消失
        popupWindow3.setOutsideTouchable(false);//有无这句话都无影响 好像。。。囧
        // 设置此参数获得焦点，否则无法点击
        popupWindow3.setFocusable(true);

        // popupWindow3.setBackgroundDrawable(new BitmapDrawable());  //comment by danielinbiti,如果添加了这行，那么标注1和标注2那两行不用加，加上这行的效果是点popupwindow的外边也能关闭
        popwindow_content_3.setFocusable(true);//comment by danielinbiti,设置view能够接听事件，标注1
        popwindow_content_3.setFocusableInTouchMode(true); //comment by danielinbiti,设置view能够接听事件 标注2
        popwindow_content_3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View arg0, int arg1, KeyEvent arg2) {
                if (arg1 == KeyEvent.KEYCODE_BACK) {
                    if (popwindow_content_3 != null) {
                        popupWindow3.dismiss();
                    }
                }
                return false;
            }
        });
/**
 * 第一个参数是View类型的parent,虽然这里参数名是parent，其实，不是把PopupWindow放到这个parent里，并不要求这个parent是一个ViewGroup，这个参数名让人误解。官方文档”a parent view to get the android.view.View.getWindowToken() token from
 “,这个parent的作用应该是调用其getWindowToken()方法获取窗口的Token,所以，只要是该窗口上的控件就可以了。
 */
        popupWindow3.showAtLocation(v, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

        configWindowAlpha(0.5f);
        // popupWindow2.setAnimationStyle(R.anim.anim_pop_2);//补间动画
        // popupWindow2.update();
        popupWindow3.setOnDismissListener(new PopupWindow.OnDismissListener()

                                          {
                                              @Override
                                              public void onDismiss() {
                                                  configWindowAlpha(1.0f);
                                              }
                                          }

        );
    }

    //点击里面响应，点击外面不消失【方法3】和方法2差不多  代码更少 更容易理解
    private void initPopupWindow4(View v) {
        final View popwindow_content_4 = LayoutInflater.from(this).inflate(R.layout.popwindow_content_4, null);
        final PopupWindow popupWindow4 = new PopupWindow(popwindow_content_4, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, false);
        final TextView id_tv_1 = ButterKnife.findById(popwindow_content_4, R.id.id_tv_14);
        id_tv_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_1.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });
        final TextView id_tv_2 = ButterKnife.findById(popwindow_content_4, R.id.id_tv_24);
        id_tv_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, id_tv_2.getText(), Toast.LENGTH_SHORT).show();
                //popupWindow2.dismiss();
            }
        });

        popupWindow4.setFocusable(true);


        popwindow_content_4.setFocusable(true); // 这个很重要
        popwindow_content_4.setFocusableInTouchMode(true);




        // onKeyListener
        popwindow_content_4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    popupWindow4.dismiss();
                    return true;
                }
                return false;
            }
        });

        /**
         * 第一个参数是View类型的parent,虽然这里参数名是parent，其实，不是把PopupWindow放到这个parent里，并不要求这个parent是一个ViewGroup，这个参数名让人误解。官方文档”a parent view to get the android.view.View.getWindowToken() token from
         “,这个parent的作用应该是调用其getWindowToken()方法获取窗口的Token,所以，只要是该窗口上的控件就可以了。
         */
        popupWindow4.showAtLocation(v, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

        configWindowAlpha(0.5f);
        // popupWindow2.setAnimationStyle(R.anim.anim_pop_2);//补间动画
        // popupWindow2.update();
        popupWindow4.setOnDismissListener(new PopupWindow.OnDismissListener()

                                          {
                                              @Override
                                              public void onDismiss() {
                                                  configWindowAlpha(1.0f);
                                              }
                                          }

        );


    }

    @Override
    public void onBackPressed() {

        if (popupWindow2.isShowing()) {
            popupWindow2.dismiss();
        } else {
            super.onBackPressed();
        }
    }

    private void configWindowAlpha(float alpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = alpha;
        getWindow().setAttributes(lp);
    }

}
