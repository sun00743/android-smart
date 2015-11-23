package com.example.administrator.smartbj.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.administrator.smartbj.NewsInfromationActivity;
import com.example.administrator.smartbj.R;
import com.example.administrator.smartbj.model.Children;
import com.example.administrator.smartbj.model.DataListener;
import com.example.administrator.smartbj.model.GetChildren;
import com.example.administrator.smartbj.ui.PullToRefreshBase;
import com.example.administrator.smartbj.ui.PullToRefreshScrollView;
import com.example.administrator.smartbj.utils.UrlUtil;

import java.util.ArrayList;


public class BeiJingFragment extends Fragment {

    private RelativeLayout quickNews;
    private PullToRefreshScrollView bj_ptRefresh;
    private ViewPager newsPager;
    private TextView bj_news;
    private LinearLayout newsPoints;
    private int lpPosition;
    private ArrayList<String> newsText = new ArrayList<>();
    private ArrayList<ImageView> newsImage = new ArrayList<>();
    private NewsPagerAdapter adapter;
    private boolean IMAGECHANGE = false; //handler 判定
    private boolean ISFIRSTCREATE = true;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private RequestQueue requestQueue;
    private GetChildren getChildren = new GetChildren();
    private ImageLoader imageLoader;
    private Handler handler;
    private long DELAYTIME = 5000;

    public BeiJingFragment() {
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // new一个Gson对象，别忘记了，否则会提示空指针异常
//        gson = new Gson();
//
        requestQueue = Volley.newRequestQueue(getActivity());
//        stringRequest = new MyStringRequest(UrlUtil.CONTACT_ALL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        Smart smart = gson.fromJson(response, Smart.class);
//                        List<Data> datas = smart.getData();
//                        childrens = datas.get(0).getChildren();
//                        IMG1 = UrlUtil.CONTACT_NORMAL + childrens.get(0).getImg1();
//                        Log.i("cccc"," "+IMG1);
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        requestQueue.add(stringRequest);
        imageLoader = new ImageLoader(requestQueue, new ImageLoader.ImageCache() {
            @Override
            public Bitmap getBitmap(String url) {
                return null;
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.i("ccccccc", "onCreateView");
        final View view = inflater.inflate(R.layout.fragment_beijing, container, false);
        initView(view);

        if (ISFIRSTCREATE) {
            Children children = new Children();
            //添加图片
            initImage(children);
            //添加文字
            initText(children);
        }
        //添加左下角圆点
        initPoints();
        Log.i("sssssss", " " + newsPoints.getChildCount());

        getChildren.get(getActivity(), new DataListener() {
            @Override
            public void onSuccess(Children children) {
                //添加文字
                bj_news.setText(children.getTxt1());
                loadText(children);
                //获取图片
                loadImage(children);
            }
        });

        newsPager.setAdapter(new NewsPagerAdapter());
//        adapter.notifyDataSetChanged();
        newsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bj_news.setText(newsText.get(position % newsImage.size()));
                newsPoints.getChildAt(position % newsImage.size()).setEnabled(true);
                newsPoints.getChildAt(lpPosition).setEnabled(false);
                lpPosition = position % newsImage.size();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        initView();
        //设置handler延时，并且避免多次发送滚动信息
        if (handler == null) {
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    newsPager.setCurrentItem(newsPager.getCurrentItem() + 1);
                    Log.i("hhhhhhh", "收到");
                    Message message = handler.obtainMessage(0);
                    if (IMAGECHANGE) {
                        sendMessageDelayed(message, DELAYTIME);
                        Log.i("hhhhhhh", "发送");
                    }
                }
            };

            Message message = handler.obtainMessage(0);
            handler.sendMessageDelayed(message, DELAYTIME);
            Log.i("hhhhhhh", "第一次发送");
            IMAGECHANGE = true;
        }

        //添加下拉刷新监听接口
        bj_ptRefresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {

                if (false) {
                    //添加一个adapter来过渡，否则当前item正在使用无法刷新
                    newsPager.setAdapter(new ImageAdapter());

                    getChildren.get(getActivity(), new DataListener() {

                        @Override
                        public void onSuccess(Children children) {

                            //再次请求加载数据
                            bj_news.setText(children.getTxt1());
                            loadText(children);
                            loadImage(children);
                            newsPager.setAdapter(new NewsPagerAdapter());
                            newsPager.setCurrentItem(4000);
                            newsPoints.getChildAt(0).setEnabled(true);
                        }
                    });
                }else {
                    Toast.makeText(getActivity(),"哎呀，服务器出了点小问题，无法刷新呢",Toast.LENGTH_SHORT).show();
                }
                //调用onRefreshComplete()来告诉控件刷新完了
                bj_ptRefresh.onRefreshComplete();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
            }
        });

        newsPager.setCurrentItem(4000);
        newsPoints.getChildAt(0).setEnabled(true);
        return view;
    }

    private void initView(View view) {
        quickNews = (RelativeLayout) view.findViewById(R.id.newsClick);
        quickNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), NewsInfromationActivity.class));
            }
        });

        newsPager = (ViewPager) view.findViewById(R.id.newsImage);
        bj_news = (TextView) view.findViewById(R.id.bj_viewText);
        newsPoints = (LinearLayout) view.findViewById(R.id.bj_pointGroup);
        bj_ptRefresh = (PullToRefreshScrollView) view.findViewById(R.id.bj_ptRefresh);
        imageView1 = new ImageView(getActivity());
        imageView2 = new ImageView(getActivity());
        imageView3 = new ImageView(getActivity());
        imageView4 = new ImageView(getActivity());
        LinearLayout alph = (LinearLayout) view.findViewById(R.id.alph);
        alph.getBackground().setAlpha(80);
    }

    /**
     * 请求获取网络文字信息
     *
     * @param children
     */
    private void loadText(Children children) {
        newsText.set(0, children.getTxt1());
        newsText.set(1, children.getTxt2());
        newsText.set(2, children.getTxt3());
        newsText.set(3, children.getTxt4());
    }

    private void initText(Children children) {
        newsText.add(" ");
        newsText.add(" ");
        newsText.add(" ");
        newsText.add(" ");
    }

    private void initImage(Children children) {
        String IMG1 = UrlUtil.CONTACT_NORMAL + children.getImg1();
        String IMG2 = UrlUtil.CONTACT_NORMAL + children.getImg2();
        String IMG3 = UrlUtil.CONTACT_NORMAL + children.getImg3();
        String IMG4 = UrlUtil.CONTACT_NORMAL + children.getImg4();

        ImageLoader.ImageListener listener1 = ImageLoader.getImageListener(imageView1,
                R.drawable.defult, R.drawable.defult);
        ImageLoader.ImageListener listener2 = ImageLoader.getImageListener(imageView2,
                R.drawable.defult, R.drawable.defult);
        ImageLoader.ImageListener listener3 = ImageLoader.getImageListener(imageView3,
                R.drawable.defult, R.drawable.defult);
        ImageLoader.ImageListener listener4 = ImageLoader.getImageListener(imageView4,
                R.drawable.defult, R.drawable.defult);
        imageLoader.get(IMG1, listener1, 0, 0);
        imageLoader.get(IMG2, listener2, 0, 0);
        imageLoader.get(IMG3, listener3, 0, 0);
        imageLoader.get(IMG4, listener4, 0, 0);
        newsImage.add(imageView1);
        newsImage.add(imageView2);
        newsImage.add(imageView3);
        newsImage.add(imageView4);
    }

    /**
     * 请求获取网络图片信息
     *
     * @param children
     */
    private void loadImage(Children children) {
        String IMG1 = UrlUtil.CONTACT_NORMAL + children.getImg1();
        String IMG2 = UrlUtil.CONTACT_NORMAL + children.getImg2();
        String IMG3 = UrlUtil.CONTACT_NORMAL + children.getImg3();
        String IMG4 = UrlUtil.CONTACT_NORMAL + children.getImg4();

        ImageLoader.ImageListener listener1 = ImageLoader.getImageListener(imageView1,
                R.drawable.defult, R.drawable.defult);
        ImageLoader.ImageListener listener2 = ImageLoader.getImageListener(imageView2,
                R.drawable.defult, R.drawable.defult);
        ImageLoader.ImageListener listener3 = ImageLoader.getImageListener(imageView3,
                R.drawable.defult, R.drawable.defult);
        ImageLoader.ImageListener listener4 = ImageLoader.getImageListener(imageView4,
                R.drawable.defult, R.drawable.defult);
        imageLoader.get(IMG1, listener1, 0, 0);
        imageLoader.get(IMG2, listener2, 0, 0);
        imageLoader.get(IMG3, listener3, 0, 0);
        imageLoader.get(IMG4, listener4, 0, 0);
        newsImage.set(0, imageView1);
        newsImage.set(1, imageView2);
        newsImage.set(2, imageView3);
        newsImage.set(3, imageView4);
    }

    /**
     * 设置右下角圆点
     */
    private void initPoints() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
        params.rightMargin = 15;
        for (int i = 0; i < 4; i++) {
            ImageView point = new ImageView(getActivity());
            point.setLayoutParams(params);
            point.setBackgroundResource(R.drawable.point_bg);
            if (i == 0) {
                point.setEnabled(true);
            } else {
                point.setEnabled(false);
            }
            newsPoints.addView(point);
        }
    }

    @Override
    public void onDestroyView() {
        if (handler != null) {
            handler.removeMessages(0);
            Log.i("ccccc", "remove handler");
        }
        IMAGECHANGE = false;
        ISFIRSTCREATE = false;
        super.onDestroyView();
        Log.i("ccccccc", "onDestroyView" + newsImage.size());
        newsPager.setAdapter(new ImageAdapter());
    }

    /**
     * ViewPagerAdapter
     */
    class NewsPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            Log.i("ccccc", "" + position);
            ImageView view = newsImage.get(position % newsImage.size());
            view.setScaleType(ImageView.ScaleType.FIT_XY);
            container.addView(view);
            //设置触摸监听
            view.setOnTouchListener(new TopNewsOnTouchListener());
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            object = null;
        }
    }

    /**
     * 下拉刷新时用来过度的adapter
     */
    class ImageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView v = new ImageView(getActivity());
            container.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    /**
     * 轮播条触摸监听
     */
    class TopNewsOnTouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.sendMessageDelayed(handler.obtainMessage(0), DELAYTIME);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    handler.sendMessageDelayed(handler.obtainMessage(0), DELAYTIME);
                    break;
            }
            return true;
        }
    }
}
