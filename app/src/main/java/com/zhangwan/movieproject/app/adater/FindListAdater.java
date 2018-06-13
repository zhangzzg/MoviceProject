package com.zhangwan.movieproject.app.adater;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseMoreTypeRecyclerAdapter;
import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.utils.WindowUtil;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.bean.FindBean;
import com.zhangwan.movieproject.app.bean.HomeBean;
import com.zhangwan.movieproject.app.holder.RecyclerItemNormalHolder;

import java.util.List;

/**
 * Created by zzg on 2018/4/11.
 */

public class FindListAdater extends BaseMoreTypeRecyclerAdapter<FindBean.FindSubBean> {
    public FindListAdater(Context mContext, List<FindBean.FindSubBean> datas, int... resid) {
        super(mContext, datas, resid);
    }

    @Override
    public int getItemViewType(int position) {
       switch (super.getDatas().get(position).getType()){
           //1文章
           case "1":
               return 0;
           //2视频
           case "2":
               return 1;
       }
        return 0;
    }

    @Override
    public void bindData(BaseMoreTypeRecyclerAdapter.ViewHolder holder, int position, FindBean.FindSubBean bean) {
        switch (bean.getType()){
            // 1文章
            case "1":
                TextView  name = (TextView) holder.getView(R.id.name);
                TextView  content = (TextView) holder.getView(R.id.content);
                RecyclerView  imageList = (RecyclerView) holder.getView(R.id.platform_list);
                name.setText(bean.getTitle());
                content.setText(bean.getContent()+"  阅读:"+bean.getReadnum());
                if(bean.getImg() != null && bean.getImg().size() > 0){
                    imageList.setVisibility(View.VISIBLE);
                    GridLayoutManager mGridLayoutManager = new GridLayoutManager(getContext(),3);
                    imageList.setLayoutManager(mGridLayoutManager);
                    ImageAdater imageAdater = new ImageAdater(getContext(),bean.getImg(),R.layout.find_imag_item_layout);
                    imageList.setAdapter(imageAdater);
                }else {
                    imageList.setVisibility(View.GONE);
                }
                break;
            // 2视频
            case "2":
                TextView  nameV = (TextView) holder.getView(R.id.name);
                TextView  contentV = (TextView) holder.getView(R.id.content);
                nameV.setText(bean.getTitle());
                contentV.setText(bean.getContent()+"  播放:"+bean.getReadnum());
                RecyclerItemNormalHolder recyclerItemViewHolder = new RecyclerItemNormalHolder(getContext(),holder.getItemView());
                recyclerItemViewHolder.setRecyclerBaseAdapter(this);
                recyclerItemViewHolder.onBind(position, bean);
                break;
        }
    }

    public static class ImageAdater extends BaseRecyclerAdapter<String>{
        int width;
        public ImageAdater(Context context, List<String> list, int itemLayoutId) {
            super(context, list, itemLayoutId);
            int paddingLeft = (int) context.getResources().getDimension(R.dimen.margin_10);
            int paddingRight = (int) context.getResources().getDimension(R.dimen.margin_10);
            int divide = WindowUtil.dip2px(context, 5) * 2;
            width = (WindowUtil.getScreenWidth(context) - paddingLeft - paddingRight - divide) / 3;
        }

        @Override
        public void bindData(ViewHolder holder, int position, String s) {
            ImageView               imageView = (ImageView) holder.getView(R.id.image);
            LinearLayout.LayoutParams params    = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            params.width = width;
            params.height = 140;
           ImageHelper.loadImage(getContext(),s,imageView);
        }
    }
}
