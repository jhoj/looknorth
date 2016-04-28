package fo.looknorth.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import fo.looknorth.app.app.R;

/**
 * Created by jakup on 3/16/16.
 */
public class ProductionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);

        // Create the adapter that will return the fragments
        ProductionPagerAdapter productionPagerAdapter = new ProductionPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(productionPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

        Toast.makeText(getActivity(), R.string.production, Toast.LENGTH_SHORT).show();
        getActivity().setTitle(R.string.production);
        return view;
    }
}

