package com.yhy.leftmenu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yhy.leftmenu.R;
import com.yhy.leftmenu.customview.CircleImageView;
import com.yhy.leftmenu.event.ShowButtonEvent;
import com.yhy.leftmenu.event.HideButtonEvent;
import com.yhy.leftmenu.leftmenu.MenuFragment;

import de.greenrobot.event.EventBus;


/**
 * 作者 : YangHaoyi on 2016/8/23.
 * 邮箱 ：yanghaoyi@neusoft.com
 */
public class MenuLeftFragment extends MenuFragment {

    private CircleImageView head;
    public static MenuLeftFragment newInstance() {
        Bundle args = new Bundle();
        
        MenuLeftFragment fragment = new MenuLeftFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private View leftMenu;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        leftMenu = inflater.inflate(R.layout.fragment_leftmenu, container, false);
        init();
        return setupReveal(leftMenu);           /**坑点**/
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void init(){
        head = (CircleImageView) leftMenu.findViewById(R.id.head);
    }

    @Override
    public void onOpenMenu() {
        super.onOpenMenu();
        ShowButtonEvent showButtonEvent = new ShowButtonEvent();
        showButtonEvent.setShow(true);
        EventBus.getDefault().post(showButtonEvent);
    }

    @Override
    public void onCloseMenu() {
        super.onCloseMenu();
        HideButtonEvent hideButtonEvent = new HideButtonEvent();
        hideButtonEvent.setHide(true);
        EventBus.getDefault().post(hideButtonEvent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
