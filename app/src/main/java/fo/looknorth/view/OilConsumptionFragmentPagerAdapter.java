package fo.looknorth.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fo.looknorth.logik.Logik;

/**
 * Created by jakup on 4/28/16.
 */
public class OilConsumptionFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public OilConsumptionFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return OilConsumptionContentFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        int totalFragment = 1;
        int numberOfMachines = Logik.instance.machines.length;

        return totalFragment + numberOfMachines;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Total";
            default:
                return "Machine " + position;
        }
    }
}
