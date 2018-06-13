package com.zhangwan.movieproject.app.adater;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gxtc.commlibrary.base.BaseMoreTypeRecyclerAdapter;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.bean.HomeBean;

import java.util.List;

/**
 * Created by zzg on 2018/4/11.
 */

public class HomeVideoAdater extends BaseMoreTypeRecyclerAdapter<HomeBean.Video> {

    public HomeVideoAdater(Context mContext, List<HomeBean.Video> datas, int... resid) {
        super(mContext, datas, resid);
    }

    @Override
    public int getItemViewType(int position) {
       switch (super.getDatas().get(position).getIsfirst()){
           case "0":
               return 1;
           case "1":
               return 0;
       }
        return 0;
    }

    @Override
    public void bindData(BaseMoreTypeRecyclerAdapter.ViewHolder holder, int position, HomeBean.Video video) {
        switch (video.getIsfirst()){
            //小图
            case "0":
                TextView title = (TextView) holder.getView(R.id.title);
                title.setText(video.getTitle());
                ImageView image = (ImageView) holder.getView(R.id.image);
                ImageHelper.loadImage(getContext(),video.getPic(),image);
                TextView descrite = (TextView) holder.getView(R.id.descrite);
                if(TextUtils.isEmpty(video.getDesc())){
                    descrite.setVisibility(View.GONE);
                }else {
                    descrite.setVisibility(View.VISIBLE);
                    descrite.setText(video.getDesc());
                }
                break;
            //大图
            case "1":
                TextView mtitle = (TextView) holder.getView(R.id.video_title);
                mtitle.setText(video.getTitle());
                ImageView mImage = (ImageView) holder.getView(R.id.video_image);
                ImageHelper.loadImage(getContext(),video.getIsfirstpic(),mImage);
                TextView mdescrite = (TextView) holder.getView(R.id.video_descrite);
                if(TextUtils.isEmpty(video.getDesc())){
                    mdescrite.setVisibility(View.GONE);
                }else {
                    mdescrite.setVisibility(View.VISIBLE);
                    mdescrite.setText(video.getDesc());
                }
                break;
        }
    }
}
