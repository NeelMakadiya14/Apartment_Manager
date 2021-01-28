package com.inc.apartmentmanager;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

class MyAdapter_Transaction extends FragmentPagerAdapter{

    public MyAdapter_Transaction(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0 : return new Expense_Transaction();
            case 1 : return new Deposit_Transaction();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0 :
                return "Expense";
            case 1 :
                return "Deposit";
        }
        return null;
    }
}
