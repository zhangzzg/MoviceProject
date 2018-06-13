package com.zhangwan.movieproject.app.ui.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.gxtc.commlibrary.base.BaseTitleActivity;

import com.just.agentwebX5.AgentWebX5;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;
import com.zhangwan.movieproject.app.R;
import com.zhangwan.movieproject.app.utils.ADFilterTool;

import butterknife.BindView;

/**
 * Created by laoshiren on 2018/4/14.
 * 公共webview，只展示，如果涉及交互重新写一个
 */
public class CommonWebViewActivity extends BaseTitleActivity {
    AgentWebX5 mAgentWebX5;
    @BindView(R.id.ll_angent_web_head)
    LinearLayout mLinearLayout;

    private String mUrl;

    public static void startActivity(Context context, String url) {
        Intent intent = new Intent(context, CommonWebViewActivity.class);
        intent.putExtra("web_url", url);

        context.startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_web);


    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("web_url");

        getBaseHeadView().showBackButton(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!mAgentWebX5.back()){
                    CommonWebViewActivity.this.finish();
                }else {
                    mAgentWebX5.back();
                }
            }
        });
        mAgentWebX5 = AgentWebX5.with(this)//
                .setAgentWebParent(mLinearLayout, new LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator()//
                .setIndicatorColor(getResources().getColor(R.color.colorPrimary))
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setSecutityType(AgentWebX5.SecurityType.strict)
                .createAgentWeb()//
                .ready()
                .go(mUrl);
    }


    private WebViewClient mWebViewClient = new WebViewClient() {

        @Override
        public void onPageFinished(WebView webView, String s) {

            super.onPageFinished(webView, s);
            //运营商挟持
            webView.loadUrl("javascript:document.getElementById('statusholder').remove();");
            webView.loadUrl("javascript:document.getElementById('progresstextholder').remove();");
            webView.loadUrl("javascript:document.getElementById('ftsiappholder').remove();");
            webView.loadUrl("javascript:document.getElementById('tlbstoolbar').remove();");
        }

        @Override
        public WebResourceResponse shouldInterceptRequest(WebView webView, String url) {
            url = url.toLowerCase();
            if (!url.contains(mUrl)) {
                if (!ADFilterTool.hasAd(CommonWebViewActivity.this, url)) {
                    return super.shouldInterceptRequest(webView, url);
                } else {
                    return new WebResourceResponse(null, null, null);
                }
            } else {
                return super.shouldInterceptRequest(webView, url);
            }

        }
    };
    private com.tencent.smtt.sdk.WebChromeClient mWebChromeClient = new com.tencent.smtt.sdk.WebChromeClient() {

        @Override
        public void onProgressChanged(com.tencent.smtt.sdk.WebView webView, int i) {
            super.onProgressChanged(webView, i);

        }

        @Override
        public void onReceivedTitle(WebView webView, String title) {
            super.onReceivedTitle(webView, title);
            getBaseHeadView().showTitle(title);
        }
    };


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mAgentWebX5.handleKeyEvent(keyCode, event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        mAgentWebX5.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        mAgentWebX5.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.i("Info", "result:" + requestCode + " result:" + resultCode);
        mAgentWebX5.uploadFileResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mAgentWebX5.destroy();
        mAgentWebX5.getWebLifeCycle().onDestroy();
    }
}
