package fo.looknorth.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fo.looknorth.logik.Logik;


public class ProductionPagerAdapter extends FragmentPagerAdapter {

    public ProductionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ProductionContentFragment.newInstance();
    }

    @Override
    public int getCount() {
        //total and all machines.
        return Logik.instance.machines.length + 1;
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
