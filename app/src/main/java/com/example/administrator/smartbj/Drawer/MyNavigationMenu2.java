package com.example.administrator.smartbj.Drawer;

import android.content.Context;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationSubMenu;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.view.SubMenu;

/**
 * Created by Administrator on 2015/10/27.
 */
public class MyNavigationMenu2 extends NavigationMenu {
    public MyNavigationMenu2(Context context) {
        super(context);
    }

    @Override
    public SubMenu addSubMenu(int group, int id, int categoryOrder, CharSequence title) {
        final MenuItemImpl item = (MenuItemImpl) addInternal(group, id, categoryOrder, null);
        final SubMenuBuilder subMenu = new NavigationSubMenu(getContext(), this, item);
        item.setSubMenu(subMenu);
        return subMenu;
    }
}
