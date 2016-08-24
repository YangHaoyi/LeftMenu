package com.yhy.leftmenu.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.yhy.leftmenu.R;
import com.yhy.leftmenu.customview.CircleImageView;
import com.yhy.leftmenu.event.ShowButtonEvent;
import com.yhy.leftmenu.event.HideButtonEvent;
import com.yhy.leftmenu.fragment.MenuLeftFragment;
import com.yhy.leftmenu.leftmenu.FlowingView;
import com.yhy.leftmenu.leftmenu.LeftDrawerLayout;

import de.greenrobot.event.EventBus;

/**
 * 作者 : YangHaoyi on 2016/8/23.
 * 邮箱 ：yanghaoyi@neusoft.com
 */
public class MenuActivity extends FragmentActivity implements View.OnClickListener {

    private FrameLayout continer,closeMenu;
    private LeftDrawerLayout leftDrawerLayout;
    private FlowingView flowingView;
    private CircleImageView openMenu;
    private ImageView qq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        EventBus.getDefault().register(this);
        init();
    }

    private void init() {
        initView();
        initEvent();
    }

    private void initView() {
        leftDrawerLayout = (LeftDrawerLayout) findViewById(R.id.leftDrawerLayout);
        flowingView = (FlowingView) findViewById(R.id.flowingView);
        openMenu = (CircleImageView) findViewById(R.id.openMenu);
        qq = (ImageView) findViewById(R.id.qq);
    }

    private void initEvent() {
        setFragment();
        openMenu.setOnClickListener(this);
        qq.setOnClickListener(this);
    }

    private void setFragment(){
        FragmentManager fm = getSupportFragmentManager();
        MenuLeftFragment menuLeftFragment = (MenuLeftFragment) fm.findFragmentById(R.id.container_menu);
        if (menuLeftFragment == null) {
            fm.beginTransaction().add(R.id.container_menu, menuLeftFragment = new MenuLeftFragment()).commit();
        }
        setCloseMenuTouch();
        leftDrawerLayout.setFluidView(flowingView);
        leftDrawerLayout.setMenuFragment(menuLeftFragment);
    }

    private void setCloseMenuTouch() {
        continer = (FrameLayout) findViewById(android.R.id.content);
        closeMenu = new FrameLayout(this);
        continer.addView(closeMenu);
        FrameLayout.LayoutParams fmPara = new FrameLayout.LayoutParams(250,FrameLayout.LayoutParams.MATCH_PARENT);
        fmPara.gravity = Gravity.RIGHT;
        closeMenu.setLayoutParams(fmPara);
        closeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (leftDrawerLayout.isShownMenu()) {
                    leftDrawerLayout.closeDrawer();
                }
            }
        });


        closeMenu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_SCROLL) {
                    if (leftDrawerLayout.isShownMenu()) {
                        leftDrawerLayout.closeDrawer();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.openMenu:
                leftDrawerLayout.openDrawer();
                break;
            case R.id.qq:
                Toast.makeText(MenuActivity.this,getResources().getString(R.string.qq),Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void onEvent(HideButtonEvent hideButtonEvent) {
        if(hideButtonEvent.isHide()){
            closeMenu.setVisibility(View.GONE);
        }
    }
    public void onEvent(ShowButtonEvent showButtonEvent){
        if(showButtonEvent.isShow()){
            closeMenu.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        if (leftDrawerLayout.isShownMenu()) {
            leftDrawerLayout.closeDrawer();
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
