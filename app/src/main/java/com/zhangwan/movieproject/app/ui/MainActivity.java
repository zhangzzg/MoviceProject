package com.zhangwan.movieproject.app.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gxtc.commlibrary.base.BaseTitleActivity;
import com.gxtc.commlibrary.utils.LogUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.bean.TabBean;
import com.zhangwan.movieproject.app.ui.find.FindFragment;
import com.zhangwan.movieproject.app.ui.home.HomeFragment;
import com.zhangwan.movieproject.app.ui.message.MessageFragment;
import com.zhangwan.movieproject.app.ui.mine.MineFragment;

import java.util.ArrayList;

import butterknife.BindView;


/**
 * Created by zzg on 2018/4/11.
 */
public class MainActivity extends BaseTitleActivity {
    @BindView(R.id.common_tab_layout) CommonTabLayout commonTabLayout;
    //退出时间
    private long exitTime = 0;
    private String[] mTitles = {"首页", "发现", "消息", "我的"};

    private int[] mIconNormalIds = {R.drawable.shouye, R.drawable.faxian,
            R.drawable.xiaoxi, R.drawable.wode};
    private int[] mIconPressIds = {R.drawable.shouye_h, R.drawable.fax_h,
            R.drawable.xiaoxi_h, R.drawable.wode_h};

    private HomeFragment mHomeFragment;
    private FindFragment                mFindFragment;
    private MessageFragment             mMessageFragment;
    private MineFragment                mMineFragment;
    private ArrayList<CustomTabEntity> tabEntitys;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    @Override
    public void initData() {
        setImmerse();
        mHomeFragment = new HomeFragment();
        mFindFragment = new FindFragment();
        mMessageFragment = new MessageFragment();
        mMineFragment = new MineFragment();
        tabEntitys = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            tabEntitys.add(new TabBean(mTitles[i], mIconPressIds[i], mIconNormalIds[i]));
        }
        commonTabLayout.setTabData(tabEntitys);
        //临时身份跳转发现
        if("1".equals(SpUtil.getGrade(this))){
            commonTabLayout.setCurrentTab(1);
            switchFragment(mFindFragment, FindFragment.class.getSimpleName(), R.id.fl_fragment);
        }else {
            commonTabLayout.setCurrentTab(0);
            switchFragment(mHomeFragment, HomeFragment.class.getSimpleName(), R.id.fl_fragment);
        }
    }

    @Override
    public void initListener() {
        commonTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {

                switch (position) {
                    //首页
                    case 0:
                        switchFragment(mHomeFragment, HomeFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        break;
                    //发现
                    case 1:
                        switchFragment(mFindFragment, FindFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        break;
                    //消息
                    case 2:
                        switchFragment(mMessageFragment, MessageFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        break;
                    //我的
                    case 3:
                        switchFragment(mMineFragment, MineFragment.class.getSimpleName(),
                                R.id.fl_fragment);
                        String token = SpUtil.getToken(MainActivity.this);
                        LogUtil.printD("token:" + token);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;

        }

        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtil.showShort(this, "再按一次退出程序");

            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            mMineFragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
