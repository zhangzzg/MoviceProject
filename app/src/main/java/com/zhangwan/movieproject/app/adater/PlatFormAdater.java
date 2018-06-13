package com.zhangwan.movieproject.app.adater;

import android.content.Context;
import android.util.Log;

import com.gxtc.commlibrary.base.BaseRecyclerAdapter;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.gxtc.commlibrary.widget.CircleImageView;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.bean.HomeBean;

import java.util.List;

/**
 * Created by zzg on 2018/4/11.
 */

public class PlatFormAdater extends BaseRecyclerAdapter<HomeBean.Platform> {

    public PlatFormAdater(Context context, List<HomeBean.Platform> list, int itemLayoutId) {
        super(context, list, itemLayoutId);
    }

    @Override
    public void bindData(ViewHolder holder, int position, HomeBean.Platform platform) {
        holder.setText(R.id.platform_name,platform.getName());
        CircleImageView circleImageView = (CircleImageView) holder.getView(R.id.platform_image);
        ImageHelper.loadImage(context,platform.getPic(),circleImageView);
    }
}
