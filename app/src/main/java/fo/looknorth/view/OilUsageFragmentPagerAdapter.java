package fo.looknorth.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import fo.looknorth.logik.Logik;

/**
 * Created by jakup on 4/28/16.
 */
public class OilUsageFragmentPagerAdapter extends FragmentStatePagerAdapter {

    public OilUsageFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        //todo how to get correct position number?

        //NOTES...
        // starts with making position for total section = 1, when starting up
        // should be 0 to fetch correct data.

        //the last section does not update to last position, i.e. when machine 4 is in focus,
        //and then machine 5 is clicked, then postion will still be 4.


        return OilUsageContentFragment.newInstance(position);
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
