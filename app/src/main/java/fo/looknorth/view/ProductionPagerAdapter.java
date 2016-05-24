package fo.looknorth.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fo.looknorth.logic.LooknorthLogic;


public class ProductionPagerAdapter extends FragmentPagerAdapter {

    public ProductionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ProductionContentFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        //total and all machines.
        return LooknorthLogic.instance.machines.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int number = position + 1;
        return "Machine " + number;
    }
}
