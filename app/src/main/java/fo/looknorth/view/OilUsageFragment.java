package fo.looknorth.view;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import fo.looknorth.app.app.R;

/**
 * Created by jakup on 3/16/16.
 */
public class OilUsageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);

        // Create the adapter that will return the fragments
        OilUsageFragmentPagerAdapter oilUsageFragmentPagerAdapter = new OilUsageFragmentPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(oilUsageFragmentPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.getId();
        getActivity().setTitle(R.string.oil_usage_name);

        return view;
    }
}

