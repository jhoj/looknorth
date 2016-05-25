package fo.looknorth.view;

import android.app.Activity;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.res.ResourcesCompat;

import fo.looknorth.app.app.R;
import fo.looknorth.logic.Logic;

/**
 * Created by jakup on 4/28/16.
 */
public class OilConsumptionFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private FragmentManager fm;

    public OilConsumptionFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return OilConsumptionContentFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        int totalFragment = 1;
        int numberOfMachines = Logic.instance.machines.size();

        return totalFragment + numberOfMachines;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String s = "Maskina ";

        switch (position) {
            case 0:
                return "Total";
            default:
                return s + position;
        }
    }
}
