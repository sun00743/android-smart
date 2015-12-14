package com.example.administrator.smartbj.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.administrator.smartbj.ImageGroupActivity;
import com.example.administrator.smartbj.MainActivity;
import com.example.administrator.smartbj.R;
import com.example.administrator.smartbj.SettingsActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

/**
 * Created by Administrator on 2015/11/23.
 */
public class AddSildingMenu {

    public static void add(final Activity context, Toolbar toolbar) {
        // Create the AccountHeader
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(context)
                .withHeaderBackground(R.color.material_yellow)
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
        final PrimaryDrawerItem item1 = new PrimaryDrawerItem().withName("首页").withIdentifier(1).withIcon(R.drawable.ico_main);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withName("反馈").withIdentifier(2);

        Drawer sildingmenu = new DrawerBuilder()
                .withActivity(context)
                .withToolbar(toolbar)
                .withAccountHeader(headerResult)
                //添加选项
                .addDrawerItems(
                        item1,
                        new PrimaryDrawerItem().withName("专题").withIdentifier(3).withIcon(R.drawable.ico_zhuanti),
                        new PrimaryDrawerItem().withName("组图").withIdentifier(4).withIcon(R.drawable.ico_ig),
                        new PrimaryDrawerItem().withName("互动").withIdentifier(5).withIcon(R.drawable.ico_hudong),
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
//                                if (context.equals(MainActivity.class)) {
//                                    break;
//                                }else {
//                                    context.startActivity(new Intent(context, MainActivity.class));
//                                    context.finish();
//                                    Log.i("cccccc","111111");
//                                }

                                if (context.equals(MainActivity.class)) {
                                    break;
                                }
                                context.startActivity(new Intent(context, MainActivity.class));
                                break;
                            case 4:
                                if (context.equals(ImageGroupActivity.class)) {
                                    break;
                                } else {
                                    context.startActivity(new Intent(context, ImageGroupActivity.class));
                                    Log.i("cccccc", "2222222");
                                }
//                                if (!context.equals(MainActivity.class)) {
//                                    context.finish();
//                                    Log.i("cccccc", "2222222");
//                                }
                                break;
                            case 6:
                                context.startActivity(new Intent(context, SettingsActivity.class));
//                                if (!context.equals(MainActivity.class)) {
//                                    context.finish();
//                                    Log.i("cccccc", "2222222");
//                                }
                                break;
                        }

                        return false;
                    }
                })
                .build();
    }

}
