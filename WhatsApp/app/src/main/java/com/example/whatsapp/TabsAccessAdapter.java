package com.example.whatsapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsAccessAdapter extends FragmentPagerAdapter {
    public TabsAccessAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i)
        {
            case 0:chatsFragment chatsFragment=new chatsFragment();
            return chatsFragment;
            case 1:groupFragment groupFragment=new groupFragment();
                return groupFragment;
            case 2:contactFragment  contactFragment=new contactFragment();
                return contactFragment;
                default:
                    return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "Chats";
            case 1:
                return "Groups";
            case 2:
                return "Contact";
            default:
                return null;
        }
    }
}
