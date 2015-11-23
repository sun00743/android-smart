package com.example.administrator.smartbj;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.example.administrator.smartbj.utils.SetStatusBar;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.sharesdk.tencent.qzone.QZone;
import me.imid.swipebacklayout.lib.SwipeBackLayout;

public class NewsInfromationActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private WebView webView;
    private RelativeLayout layou;
    private ProgressBar progressBar;
    private SwipeBackLayout mSB_layout;
    private Toolbar toolbar,
            view;
    private ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_infromation);
        new SetStatusBar().setStatusBar(this);
        //shareSDK
        ShareSDK.initSDK(this);
//        mSB_layout = getSwipeBackLayout();
//        mSB_layout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);

        toolbar = (Toolbar) findViewById(R.id.in_toolbar);
        toolbar.setTitle("AC 文章");
        toolbar.setTitleTextColor(getResources().getColor(R.color.color_white));
        toolbar.setLogo(R.drawable.btn_back);
        toolbar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        view = (Toolbar) findViewById(R.id.view);

        setSupportActionBar(toolbar);

        layou = (RelativeLayout) findViewById(R.id.webViewlayout);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        webView = (WebView) findViewById(R.id.webView);
//        back = (ImageButton) findViewById(R.id.btn_newsin_back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });

        //更该webview设置
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);

        //webViewClient 点击链接可以直接由此webview打开，不需要使用外部的浏览器
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }

        });

        webView.loadUrl("http://www.acfun.tv/v/list110/index.htm");
    }

    /**
     * 重写onKeyDown方法，判断按键等于键盘返回键，并且webView可以返回上一页。
     * 如果两个条件满足，那么webView返回上一个页面
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_nesin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        String url = webView.getUrl();
        String titleUrl = webView.getTitle();

        switch (item.getItemId()) {
            case R.id.wechat:
                Log.i("ccccccccc", " " + url);
                showShare(url);
                break;
            case R.id.qqzone:
                QZone.ShareParams sp = new QZone.ShareParams();
                sp.setTitle("查看全文");
                sp.setTitleUrl(url); // 标题的超链接
                sp.setText(titleUrl);
                sp.setImageUrl("http://cdn.aixifan.com/dotnet/artemis/u/cms/www/201511/141548417g8kpepm.png");
                sp.setSite("acfun");
                sp.setSiteUrl(url);

                Platform qzone = ShareSDK.getPlatform(QZone.NAME);
                qzone.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
                        Log.i("ccccc","分享完成");
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {
                        Log.i("cccc",""+throwable);
                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                }); // 设置分享事件回调
// 执行图文分享
                qzone.share(sp);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showShare(String url) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(url);
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(url);
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(url);

// 启动分享GUI
        oks.show(this);
    }
}
