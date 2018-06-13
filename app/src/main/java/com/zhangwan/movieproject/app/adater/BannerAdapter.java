package com.zhangwan.movieproject.app.adater;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.gxtc.commlibrary.helper.ImageHelper;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.bean.HomeBean;



public class BannerAdapter implements Holder<HomeBean.Ad> {
    private ImageView iv;
    private TextView title;
    private View view;

    @Override
    public View createView(Context context) {
        view = View.inflate(context, R.layout.banner_layout_news, null);
        iv = (ImageView) view.findViewById(R.id.iv_news_banner);
        title = (TextView) view.findViewById(R.id.title);
        return view;
    }

    @Override
    public void UpdateUI(Context context, int position, HomeBean.Ad data) {
        iv.setScaleType(ImageView.ScaleType.FIT_XY);
        title.setText(data.getShname());
        ImageHelper.loadImage(context,data.getPic(), iv);
    }
}
