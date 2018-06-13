package com.gxtc.commlibrary.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.gxtc.commlibrary.R;
import com.gxtc.commlibrary.utils.ActivityController;
import com.gxtc.commlibrary.utils.GotoUtil;
import com.gxtc.commlibrary.utils.LogUtil;
import com.gxtc.commlibrary.utils.ToastUtil;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import rx.Subscription;

//import com.umeng.message.PushAgent;


public class BaseTitleActivity extends AppCompatActivity {

    private ViewStub viewStubBaseHead;           //标题栏
    private ViewStub viewStubBaseLoading;        //正在加载
    private ViewStub viewStubBaseEmpty;          //无数据

    private FrameLayout contentArea;

    private LoadingView baseLoadingView;
    private EmptyView baseEmptyView;
    private HeadView baseHeadView;

    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private String curFragmentName = "";

    private List<Subscription> mSubscriptions;
    private View statusBarView;
    protected interface HandlerCallBackLisnner{
         void handlerCallBack(Message msg);
    }
    protected MyHandler mMyHandler = new MyHandler( new HandlerCallBackLisnner() {
        @Override
        public void handlerCallBack( Message msg) {
            handleMessage(msg);
        }
    });

    //对handler做了封装(避免内存泄漏)，统一处理，只需在子类里获取Handler对象（已经初始化好了（mMyHandler），直接拿来用），重写方法handlerMeaage来接收消息就行
    public static class MyHandler extends Handler{

        HandlerCallBackLisnner callBackLisnner;
        public MyHandler(HandlerCallBackLisnner callBackLisnner) {
            this.callBackLisnner = callBackLisnner;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(callBackLisnner != null){
                callBackLisnner.handlerCallBack(msg);
            }
        }
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //渐变式状态栏
//延时加载数据.
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                if (isStatusBar()) {
                    initStatusBar();
                    getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                        @Override
                        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                            initStatusBar();
                        }
                    });
                }
                //只走一次
                return false;
            }
        });

        super.setContentView(R.layout.base_head_activity);

//        PushAgent.getInstance(this).onAppStart();
        ActivityController.getInstant().addActivity(this);
        mManager = getSupportFragmentManager();
        contentArea = (FrameLayout) findViewById(R.id.contentArea);
        viewStubBaseEmpty = (ViewStub) findViewById(R.id.view_stub_base_empty);
        viewStubBaseLoading = (ViewStub) findViewById(R.id.view_stub_base_loading);
        viewStubBaseHead = (ViewStub) findViewById(R.id.view_stub_base_head);

        mSubscriptions = new ArrayList<>();
    }

    private void initStatusBar() {
        if (statusBarView == null) {
            int identifier = getResources().getIdentifier("statusBarBackground", "id", "android");
            statusBarView = getWindow().findViewById(identifier);
        }
        if (statusBarView != null) {
            statusBarView.setBackgroundResource(R.drawable.shape_title_color);
        }
    }

    protected boolean isStatusBar() {
        return true;
    }

    @Override
    public void setContentView(int layoutResID) {
        View v = getLayoutInflater().inflate(layoutResID, contentArea, false);
        setContentView(v);

    }

    protected void handleMessage(Message message) {
    }


    @Override
    public void setContentView(View view) {
        setContentView(view, view.getLayoutParams());
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        contentArea.addView(view, 0, params);
        ButterKnife.bind(this);
        initView();
        initListener();
        initData();
    }

    protected void hideContentLayout() {
        View content = contentArea.getChildAt(0);
        if (content != null)
            contentArea.getChildAt(0).setVisibility(View.INVISIBLE);
    }

    protected void showContentLayout() {
        View content = contentArea.getChildAt(0);
        if (content != null)
            content.setVisibility(View.VISIBLE);
    }

    //启动沉浸式状态栏设置
    protected void setImmerse() {
        if(Build.VERSION.SDK_INT == 19){
            getWindow().getDecorView() .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }else if(Build.VERSION.SDK_INT >= 21){
            getWindow().getDecorView() .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void initView() {
    }

    public void initListener() {
    }

    public void initData() {
    }

    private Fragment mContent;

    public Fragment getCurrentFragment() {
        return mContent;
    }

    /**
     * 替换fragment的方法
     *
     * @param fragment
     * @param simpleName
     * @param id
     */
    public void switchFragment(Fragment fragment, String simpleName, int id) {
        mTransaction = mManager.beginTransaction();
        curFragmentName = simpleName;
        if (fragment == null)
            return;

        if (mContent == null) {
            mTransaction.add(id, fragment, simpleName);
        } else {

            if (fragment.isAdded()) {
                mTransaction.hide(mContent).show(fragment);
            } else {
                mTransaction.hide(mContent).add(id, fragment, simpleName);
            }
        }
        mContent = fragment;
        mTransaction.commitAllowingStateLoss();
    }

    public LoadingView getBaseLoadingView() {
        if (baseLoadingView == null) {
            baseLoadingView = new LoadingView(viewStubBaseLoading.inflate());
        }
        return baseLoadingView;
    }

    public EmptyView getBaseEmptyView() {
        if (baseEmptyView == null) {
            baseEmptyView = new EmptyView(viewStubBaseEmpty.inflate());

        }
        return baseEmptyView;
    }

    public HeadView getBaseHeadView() {
        if (baseHeadView == null) {
            baseHeadView = new HeadView(viewStubBaseHead.inflate());
//            setActionBarTopPadding(baseHeadView.getParentView(),false);
        }
        return baseHeadView;
    }

    public void addSubscription(Subscription sub) {
        if (sub != null) {
            mSubscriptions.add(sub);
        }
    }

    protected void showToast(String mesaage) {
        ToastUtil.showShort(this, mesaage);
    }

    protected void setActionBarTopPadding(View v, boolean changeHeight) {
        if (v != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int stataHeight = getStatusBarHeight();
                if (changeHeight) {
                    v.getLayoutParams().height = v.getLayoutParams().height + stataHeight;
                }
                v.setPadding(v.getPaddingLeft(),
                        stataHeight,
                        v.getPaddingRight(),
                        v.getPaddingBottom());

            }
        }
    }

    protected int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        Window window;
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }


    /**
     * =====================
     * 申请权限相关
     * =====================
     */
    public interface PermissionsResultListener {

        void onPermissionGranted();

        void onPermissionDenied();

    }


    private PermissionsResultListener mListener;

    private int mRequestCode;

    /**
     * 其他 Activity 继承 BaseActivity 调用 performRequestPermissions 方法
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     * @param listener    实现的接口
     */
    protected void performRequestPermissions(String desc, String[] permissions, int requestCode, PermissionsResultListener listener) {
        if (permissions == null || permissions.length == 0) {
            return;
        }
        mRequestCode = requestCode;
        mListener = listener;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkEachSelfPermission(permissions)) {// 检查是否声明了权限
                requestEachPermissions(desc, permissions, requestCode);
            } else {// 已经申请权限
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            }
        } else {
            if (mListener != null) {
                mListener.onPermissionGranted();
            }
        }
    }

    /**
     * 申请权限前判断是否需要声明
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void requestEachPermissions(String desc, String[] permissions, int requestCode) {
        if (shouldShowRequestPermissionRationale(permissions)) {// 需要再次声明
            showRationaleDialog(desc, permissions, requestCode);
        } else {
            ActivityCompat.requestPermissions(BaseTitleActivity.this, permissions, requestCode);
        }
    }

    /**
     * 弹出声明的 Dialog
     *
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提醒")
                .setMessage(desc)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(BaseTitleActivity.this, permissions, requestCode);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }


    /**
     * 再次申请权限时，是否需要声明
     *
     * @param permissions
     * @return
     */
    private boolean shouldShowRequestPermissionRationale(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 检察每个权限是否申请
     *
     * @param permissions
     * @return true 需要申请权限,false 已申请权限
     */
    private boolean checkEachSelfPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        }
        return false;
    }

    /**
     * 申请权限结果的回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mRequestCode) {
            if (checkEachPermissionsGranted(grantResults)) {
                if (mListener != null) {
                    mListener.onPermissionGranted();
                }
            } else {// 用户拒绝申请权限
                if (mListener != null) {
                    mListener.onPermissionDenied();
                }
            }
        }
    }

    /**
     * 检查回调结果
     *
     * @param grantResults
     * @return
     */
    private boolean checkEachPermissionsGranted(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (Subscription sub : mSubscriptions) {
            sub.unsubscribe();
        }
        mMyHandler = null;
        ActivityController.getInstant().removedActivity(this);
    }

    protected void gone(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.GONE);
                }
            }
        }
    }

    protected void visible(final View... views) {
        if (views != null && views.length > 0) {
            for (View view : views) {
                if (view != null) {
                    view.setVisibility(View.VISIBLE);
                }
            }
        }

    }

    protected boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public void startActivity(Class<?> cl) {
        GotoUtil.goToActivity(this, cl);
    }
}
