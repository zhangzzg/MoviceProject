package com.zhangwan.movieproject.app.ui.mine;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseTitleFragment;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.FileUtil;
import com.gxtc.commlibrary.utils.LocaUtil;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.GotoUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.widget.CircleImageView;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.bean.UseInfoBean;
import com.zhangwan.movieproject.app.ui.common.CommonWebViewActivity;
import com.zhangwan.movieproject.app.ui.find.FindFragment;
import com.zhangwan.movieproject.app.ui.mine.item.AboutActivity;
import com.zhangwan.movieproject.app.ui.mine.item.setting.SettingActivity;
import com.zhangwan.movieproject.app.ui.mine.item.history.WatchHistoryActivity;
import com.zhangwan.movieproject.app.ui.mine.item.task.TaskActivity;
import com.zhangwan.movieproject.app.ui.register.RegisterActivity;
import com.zhangwan.movieproject.app.utils.CommonUtil;
import com.zhangwan.movieproject.app.utils.ErroreUtil;
import com.zhangwan.movieproject.app.utils.MyDialogUtil;
import com.zhangwan.movieproject.app.utils.RxTaskHelper;

import org.jetbrains.annotations.Nullable;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


/**
 * Created by zzg on 2018/4/11.
 */
public class MineFragment extends BaseTitleFragment implements MineContract.View{
    @BindView(R.id.head_pic)      CircleImageView   headPic;
    @BindView(R.id.name)          TextView          name;
    @BindView(R.id.vip_status)    TextView          vipStatus;
    @BindView(R.id.vip)            TextView          vip;
    @BindView(R.id.clear_cache)            TextView          cache;
    @BindView(R.id.header_layout)  View headerView;
    MineContract.Presenter mPresenter;
    public MineFragment() {}


    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void initData() {
        super.initData();
        getCacheSize();
        setActionBarTopPadding(headerView,false);
        new MinePresenter(this);
        mPresenter.getUserInfo(SpUtil.getToken(this.getContext()));
    }

    @OnClick({R.id.head_pic,R.id.task,R.id.vip_code,R.id.history,R.id.sign,R.id.coustomer,R.id.question,R.id.about,R.id.update,R.id.clear_cache,R.id.clear_cache_layout,R.id.setting})
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.head_pic:
                break;
            case R.id.task:
                if("1".equals(SpUtil.getGrade(getContext()))){
                    showDialog();
                }else {
                    startActivity(TaskActivity.class);
                }
                break;
             //对换吗
            case R.id.vip_code:
                CommonWebViewActivity.startActivity(this.getContext(),"https://www.hao123.com/");
                break;
            case R.id.history:
                startActivity(WatchHistoryActivity.class);
                break;
             //签到
            case R.id.sign:
                break;
            case R.id.coustomer:
                CommonWebViewActivity.startActivity(this.getContext(),"https://www.hao123.com/");
                break;
            case R.id.question:
                CommonWebViewActivity.startActivity(this.getContext(),"https://www.hao123.com/");
                break;
            case R.id.about:
                startActivity(AboutActivity.class);
                break;
            case R.id.update:
                CommonWebViewActivity.startActivity(this.getContext(),"https://www.hao123.com/");
                break;
            case R.id.clear_cache:
            case R.id.clear_cache_layout:
                View v = getActivity().getLayoutInflater().inflate(R.layout.commom_note_dialog_layout,null);
                final Dialog mDialog = MyDialogUtil.commentDialog(getContext(),v);
                v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mDialog.dismiss();
                    }
                });
                v.findViewById(R.id.confrom).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        clearCache();
                        mDialog.dismiss();
                    }
                });
                break;
            case R.id.setting:
                if("1".equals(SpUtil.getGrade(getContext()))){
                     showDialog();
                }else {
                    startActivity(SettingActivity.class);
                }
                break;
        }
    }

    public void showDialog(){
        MyDialogUtil.noticeDialog(getContext(), "游客权限不足,请先注册登录,方可操作,是否注册?", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),RegisterActivity.class);
                intent.putExtra("flag",true);
                startActivity(intent);
            }
        });
    }


    @Override
    public void showUserInfo(@Nullable UseInfoBean mUseInfoBean) {
        name.setText(mUseInfoBean.getNickname());
        vip.setText(mUseInfoBean.getViptime());
        ImageHelper.loadImage(getContext(),mUseInfoBean.getFace(),headPic);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            getCacheSize();
        }
    }

    private void getCacheSize() {
        Subscription sub =

                Observable.create(new Observable.OnSubscribe<Long>() {
                    @Override
                    public void call(Subscriber<? super Long> subscriber) {
                        long externalCacheDirSixe = LocaUtil.getDirSize(getActivity().getExternalCacheDir());
                        long cacheDirSixe = LocaUtil.getDirSize(getActivity().getCacheDir());
                        subscriber.onNext(externalCacheDirSixe + cacheDirSixe);
                    }
                })
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(new Action1<Long>() {
                              @Override
                              public void call(Long size) {
                                  if (0 == size) {
                                      cache.setText("");
                                  } else {
                                      cache.setText(LocaUtil.formatFileSize(size));
                                  }
                              }
                          });

        RxTaskHelper.getInstance().addTask(this, sub);
    }

    //清除缓存
    private void clearCache() {
        Observable.create(new Observable.OnSubscribe<Context>() {
            @Override
            public void call(Subscriber<? super Context> subscriber) {
                subscriber.onNext(getActivity());
                subscriber.onCompleted();
            }
        })
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<Context>() {

                      @Override
                      public void onCompleted() {
                          cache.setText("");
                      }

                      @Override
                      public void onError(Throwable e) {
                          ToastUtil.showShort(getContext(),
                                  e.getMessage());
                      }

                      @Override
                      public void onNext(Context mContext) {
                          LocaUtil.getInstance(mContext).clearAppCache();
                      }
                  });
    }


    @Override
    public void setPresenter(MineContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == Activity.RESULT_OK){
            name.setText(SpUtil.getUserName(getContext()));
        }
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showLoadFinish() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showReLoad() {

    }

    @Override
    public void showError(String code, String msg) {
        ErroreUtil.handleErrore(getActivity(),code,msg);
    }

    @Override
    public void showNetError() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        RxTaskHelper.getInstance().cancelTask(this);
    }
}
