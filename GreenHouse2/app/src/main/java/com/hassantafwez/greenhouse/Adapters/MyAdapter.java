package com.hassantafwez.greenhouse.Adapters;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hassantafwez.greenhouse.Fragments.PhotoFragment;
import com.hassantafwez.greenhouse.Fragments.PostFragments;

public class MyAdapter extends FragmentPagerAdapter {

    private Context myContext;
    int totalTabs;

    public MyAdapter(Context context, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
               PhotoFragment photoFragment = new PhotoFragment();
                return photoFragment;
            case 1:
                PostFragments postFragments = new PostFragments();
                return postFragments;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }
}