package fo.looknorth.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import fo.looknorth.app.app.R;

/**
 * Created by jakup on 3/16/16.
 */
public class ProductsInProductionFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);
        // Create the adapter that will return the fragments
        ProductsInProductionPagerAdapter productsInProductionPagerAdapter = new ProductsInProductionPagerAdapter(getChildFragmentManager());
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(productsInProductionPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);
        Typeface t = Typeface.create("casual", Typeface.ITALIC);
        pagerSlidingTabStrip.setTypeface(t, R.style.myOwnFont);


        SpannableString s = new SpannableString(getResources().getString(R.string.name_products_in_production));
        s.setSpan(new TypefaceSpan("casual"), 0, s.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getActivity().setTitle(s);

        Toast.makeText(getActivity(), R.string.name_products_in_production, Toast.LENGTH_SHORT);
        return view;
    }
}

