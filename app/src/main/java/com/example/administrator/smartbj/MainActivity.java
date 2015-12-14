package com.example.administrator.smartbj;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.administrator.smartbj.fragment.HomeFragment;
import com.example.administrator.smartbj.utils.AddSildingMenu;
import com.example.administrator.smartbj.utils.SetStatusBar;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    private FragmentTransaction fTransaction;

//    private ViewPager viewPager;
//    private PagerSlidingTabStrip tabStrip;
//    private String[] TITLES = {"北京", "中国", "国际", "体育"};
//    private ViewAdapter adapter;
//    private Fragment beijing = new BeiJingFragment();
//    private Fragment china = new ChinaFragment();
//    private Fragment inter = new InternationalFragment();
//    private Fragment sport = new SportFragment();
//    private List<Fragment> list = null ;

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

//        setStatusBar();
        new SetStatusBar().setStatusBar(this);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("Smart");
        setSupportActionBar(toolbar);

//        viewPager = (ViewPager) findViewById(R.id.viewpager_main);
//        tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabStrip_main);

        fragmentManager = getSupportFragmentManager();
        fTransaction = fragmentManager.beginTransaction();
        fTransaction.add(R.id.fm_container, new HomeFragment());
        fTransaction.commit();

//        list = new ArrayList<Fragment>();
//        list.add(beijing);
//        list.add(china);
//        list.add(inter);
//        list.add(sport);
//        adapter = new ViewAdapter(fragmentManager);
//        viewPager.setAdapter(adapter);

//        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4,
//                getResources().getDisplayMetrics());
//        viewPager.setPageMargin(pageMargin);

//        tabStrip.setViewPager(viewPager);

        AddSildingMenu.add(MainActivity.this,toolbar);
//        addSildingMenu();
    }

    private void addSildingMenu() {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.colorMain)
                .withSelectionListEnabled(false)
                .addProfiles(
                        new ProfileDrawerItem().withName("SmartBJ").
                                withIcon(R.mipmap.ic_launcher)
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

        //if you want to update the items at a later time it is recommended to keep it in a variable
        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("首页").withIdentifier(1);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("反馈").withIdentifier(2);

        Drawer sildingmenu = new DrawerBuilder()
                .withActivity(MainActivity.this)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                //添加选项
                .addDrawerItems(
                        item1,
                        new PrimaryDrawerItem().withName("专题").withIdentifier(3),
                        new PrimaryDrawerItem().withName("组图").withIdentifier(4),
                        new PrimaryDrawerItem().withName("互动").withIdentifier(5),
                        new DividerDrawerItem(),  //分割条item
                        item2,
                        new PrimaryDrawerItem().withName("设置").withIdentifier(6)
                )
                //添加item监听事件
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {

                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        switch (drawerItem.getIdentifier()) {
                            case 1:
                                Toast.makeText(MainActivity.this, " " + item1.getName(),
                                        Toast.LENGTH_SHORT).show();
                                break;
                            case 4:
                                startActivity(new Intent(MainActivity.this, ImageGroupActivity.class));
                                break;
                            case 6:
                                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                                break;
                        }

                        return false;
                    }
                })
                .build();
    }

    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, SlidingMenu.class);
        startActivity(intent);
    }

    /**
     * 修改顶部通知栏颜色
     */
    public void setStatusBar(){
        // 修改状态栏颜色，4.4+生效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.status_bar_bg);//通知栏所需颜色
    }

    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

//    class ViewAdapter extends FragmentPagerAdapter {
//
//        public ViewAdapter(FragmentManager fm) {
//            super(fm);
//        }
//
//        @Override
//        public Fragment getItem(int position) {
////            switch (position) {
////                case 0:
////                    return new BeiJingFragment();
////                case 1:
////                    return new ChinaFragment();
////                case 2:
////                    return new InternationalFragment();
////                case 3:
////                    return new SportFragment();
////            }
////            return null;
//            return list.get(position);
//        }
//
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return TITLES[position % TITLES.length];
//        }
//
//        @Override
//        public int getCount() {
//            return TITLES.length;
//        }
//    }

}
