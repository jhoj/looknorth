package fo.looknorth.productionapp.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fo.looknorth.productionapp.logik.Logik;

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
        //GET number of machines
        return Logik.instance.machines.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        int number = position + 1;
        return "Machine " + number;
    }


}
