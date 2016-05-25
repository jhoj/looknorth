package fo.looknorth.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fo.looknorth.logic.Logic;

public class ProductsInProductionPagerAdapter extends FragmentPagerAdapter {

    public ProductsInProductionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return ProductsInProductionContentFragment.newInstance(position);
    }

    @Override
    public int getCount() {
        //every machine.
        return Logic.instance.machines.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int number = position + 1;
        return "Maskina " + number;
    }


}
