package com.gxtc.commlibrary.base;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.gxtc.commlibrary.R;
import com.gxtc.commlibrary.utils.GotoUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;


public abstract class BaseTitleFragment extends Fragment {

    private View baseRootView;

    private ViewStub viewStubBaseHead;           //标题栏
    private ViewStub viewStubBaseLoading;        //正在加载
    private ViewStub viewStubBaseEmpty;          //无数据

    private FrameLayout contentArea;

    private LoadingView baseLoadingView;
    private EmptyView baseEmptyView;
    private HeadView baseHeadView;

    private List<Subscription> mSubscriptions;

    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseRootView = inflater.inflate(R.layout.base_head_activity, container, false);
        contentArea = (FrameLayout) baseRootView.findViewById(R.id.contentArea);
        viewStubBaseEmpty = (ViewStub) baseRootView.findViewById(R.id.view_stub_base_empty);
        viewStubBaseLoading = (ViewStub) baseRootView.findViewById(R.id.view_stub_base_loading);
        viewStubBaseHead = (ViewStub) baseRootView.findViewById(R.id.view_stub_base_head);
        mSubscriptions = new ArrayList<>();

        setContentView(initView(inflater,container));
        onGetBundle(getArguments());
        initListener();
        initData();
        return baseRootView;
    }

    private View setContentView(View view) {
        contentArea.addView(view, 0);
        unbinder = ButterKnife.bind(this, baseRootView);
        return baseRootView;
    }

    protected void hideContentLayout(){
        View content = contentArea.getChildAt(0);
        if(content != null)
            contentArea.getChildAt(0).setVisibility(View.INVISIBLE);
    }

    protected void showContentLayout(){
        View content = contentArea.getChildAt(0);
        if(content != null)
            content.setVisibility(View.VISIBLE);
    }

    /**
     * onCreatView 中调用
     * @param inflater
     * @param container
     * @return
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    public void initListener(){
    }

    public void initData(){
    }


    public void addSubscription(Subscription sub){
        if(sub != null){
            mSubscriptions.add(sub);
        }
    }

    /**
     * onCreateView中调用
     * 可以获取上一个Fragment传过来的数据
     */
    protected void onGetBundle(Bundle bundle) {}


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
//            setActionBarTopPadding(baseHeadView.getParentView());
        }
        return baseHeadView;
    }

    /**
     *   是否启用沉浸式
     */
    protected boolean isImmerse(){
        return true;
    }

    protected void setActionBarTopPadding(View v,boolean changeHeight) {
        if (isImmerse() && v != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                int stataHeight = getStatusBarHeight();
                if(changeHeight){
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
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }

        return result;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //官方文档只是解除了对 fragment 的绑定
        unbinder.unbind();
        for (Subscription sub : mSubscriptions) {
            sub.unsubscribe();
        }
    }

    public View getRootView() {
        return baseRootView;
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


    private BaseTitleActivity.PermissionsResultListener mListener;

    private int mRequestCode;

    /**
     * 其他 Activity 继承 BaseActivity 调用 performRequestPermissions 方法
     *
     * @param desc        首次申请权限被拒绝后再次申请给用户的描述提示
     * @param permissions 要申请的权限数组
     * @param requestCode 申请标记值
     * @param listener    实现的接口
     */
    protected void performRequestPermissions(String desc, String[] permissions, int requestCode, BaseTitleActivity.PermissionsResultListener listener) {
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
            ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
        }
    }

    /**
     * 弹出声明的 Dialog
     * @param desc
     * @param permissions
     * @param requestCode
     */
    private void showRationaleDialog(String desc, final String[] permissions, final int requestCode) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("提醒")
                .setMessage(desc)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(getActivity(), permissions, requestCode);
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
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
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
            if (ContextCompat.checkSelfPermission(getActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
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

    public void startActivity(Class<?> cl) {
        GotoUtil.goToActivity(getActivity(), cl);
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

}
