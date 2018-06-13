package com.zhangwan.movieproject.app.ui.find;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.base.BaseTitleActivity;
import com.gxtc.commlibrary.utils.SpUtil;
import com.gxtc.commlibrary.utils.ToastUtil;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.movieproject.app.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
/**
 * Created by zzg on 2018/4/11.
 */
public class SearchActivity extends BaseTitleActivity {
    @BindView(R.id.history_list)  com.gxtc.commlibrary.recyclerview.RecyclerView mRecyclerView;
    @BindView(R.id.rcent_search) TextView                                rcentSearch;
    @BindView(R.id.clear_history) TextView                               clearHistory;
    @BindView(R.id.line)           View                                    line;
    public                        EditText                                mInputEdit;
    public                        HistoryAdater                           historyAdater;
    private                       List<String>                            historyList;
    public  static  final  int MaxCount = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    @Override
    public void initView() {
        super.initView();
        View headView = LayoutInflater.from(this).inflate(R.layout.model_search_view,
                getBaseHeadView().getParentView(), false);
        mInputEdit =  headView.findViewById(R.id.et_input_search);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) headView.getLayoutParams();
        params.topMargin = 2;
        params.addRule(RelativeLayout.RIGHT_OF, R.id.headBackButton);
        params.addRule(RelativeLayout.LEFT_OF, R.id.headRightLinearLayout);
        getBaseHeadView().getParentView().addView(headView);
        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getBaseHeadView().showHeadRightButton("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(mInputEdit.getText().toString().trim())){
                    showToast("搜索内容不能为空");
                }else {
                    WindowUtil.closeInputMethod(SearchActivity.this);
                    save();
                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        historyList =  new ArrayList<String>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        String history = SpUtil.getString(this,"history", "");
        if(TextUtils.isEmpty(history)){
            rcentSearch.setVisibility(View.GONE);
            mRecyclerView.setVisibility(View.GONE);
            line.setVisibility(View.GONE);
            clearHistory.setVisibility(View.GONE);
        }
        // 用逗号分割内容返回数组
        String[] history_arr = history.split(",");
        // 新建适配器，适配器数据为搜索历史文件内容
        Collections.addAll(historyList,history_arr);
        historyAdater = new HistoryAdater(this,historyList,R.layout.history_item_layout);

        // 保留前50条数据
        if (history_arr.length > MaxCount) {
            String[] newArrays = new String[MaxCount];
            // 实现数组之间的复制
            System.arraycopy(history_arr, 0, newArrays, 0, MaxCount);
            historyAdater = new HistoryAdater(this,historyList,R.layout.history_item_layout);
        }
        mRecyclerView.setAdapter(historyAdater);
        historyAdater.setOnItemClickLisntener(new BaseRecyclerAdapter.OnItemClickLisntener() {
            @Override
            public void onItemClick(RecyclerView parentView, View v, int position) {
                mInputEdit.setText(historyAdater.getList().get(position));
            }
        });
        historyAdater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                mRecyclerView.removeData(historyAdater,position);
                StringBuilder newHistory = new StringBuilder();
                for(String str:historyAdater.getList()){
                    newHistory = newHistory.append(str+",");
                }
                SpUtil.putString(SearchActivity.this,"history", newHistory.toString());
                if(historyAdater.getList().size() == 0){
                    rcentSearch.setVisibility(View.GONE);
                    line.setVisibility(View.GONE);
                    clearHistory.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void initListener() {
        super.initListener();
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cleanHistory();
            }
        });

        mInputEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)){
                     WindowUtil.closeInputMethod(SearchActivity.this);
                     save();
                    return true;
                }
                return false;
            }
        });
    }

    public void save() {
        // 获取搜索框信息
        String text = mInputEdit.getText().toString();
        String old_text = SpUtil.getString(this,"history", "");

        // 利用StringBuilder.append新增内容，逗号便于读取内容时用逗号拆分开
        StringBuilder builder = new StringBuilder(old_text);
        builder.append(text + ",");

        // 判断搜索内容是否已经存在于历史文件，已存在则不重复添加
        if (!old_text.contains(text + ",")) {
            SpUtil.putString(this,"history", builder.toString());
            ToastUtil.showShort(this,"保存成功");
        }
        mInputEdit.setText("");
    }

    //清除搜索记录
    public void cleanHistory(){
        SpUtil.putString(this,"history", "");
        historyAdater.getList().clear();
        historyAdater.notifyDataSetChanged();
        rcentSearch.setVisibility(View.GONE);
        line.setVisibility(View.GONE);
        clearHistory.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.GONE);
    }


    public static class HistoryAdater extends BaseRecyclerAdapter<String>{
        View.OnClickListener mOnClickListener;

        public void setOnClickListener(View.OnClickListener onClickListener) {
            mOnClickListener = onClickListener;
        }

        public HistoryAdater(Context context, List<String> list, int itemLayoutId) {
            super(context, list, itemLayoutId);
        }

        @Override
        public void bindData(ViewHolder holder, final int position, final String s) {
            holder.setText(R.id.name,s);
            holder.getView(R.id.close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnClickListener != null){
                        view.setTag(position);
                        mOnClickListener.onClick(view);
                    }
                }
            });
        }
    }
}
