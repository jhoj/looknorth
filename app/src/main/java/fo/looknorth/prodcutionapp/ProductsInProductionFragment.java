package fo.looknorth.prodcutionapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;

import model.Logic;

/**
 * Created by jakup on 3/16/16.
 */
public class ProductsInProductionFragment extends Fragment {

    private CurrentContextPagerAdapter currentContextPagerAdapter;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_layout, container, false);
        // Create the adapter that will return the fragments
        currentContextPagerAdapter = new CurrentContextPagerAdapter(getChildFragmentManager());
        viewPager = (ViewPager) view.findViewById(R.id.container);
        viewPager.setAdapter(currentContextPagerAdapter);

        PagerSlidingTabStrip pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
        pagerSlidingTabStrip.setViewPager(viewPager);

        getActivity().setTitle("Products in Production");

        return view;
    }

    public static class CurrentContextFragment extends Fragment implements AdapterView.OnItemSelectedListener {

        public static int id;
        TextView currentProductText;
        Spinner productListSpinner;


        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CurrentContextFragment newInstance(int machineId)
        {
            id = machineId;
            return new CurrentContextFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_products_in_production, container, false);

            currentProductText = (TextView) rootView.findViewById(R.id.currently_active_product_text);
            productListSpinner = (Spinner) rootView.findViewById(R.id.product_list);

            ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Logic.instance.machines.get(id).productList) {
                @Override
                public View getView(int position, View cachedView, ViewGroup parent) {
                    View view = super.getView(position, cachedView, parent);
                    return view;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    return getView(position, convertView, parent);
                }
            };

            productListSpinner.setAdapter(arrayAdapter);
            productListSpinner.setOnItemSelectedListener(this);
            productListSpinner.setPrompt("Choose a product");
            currentProductText.setText(Logic.instance.machines.get(id).currentProduct.name);

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            currentProductText.setText(productListSpinner.getSelectedItem().toString());
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {
            if (position == 0)
            {
                currentProductText.setTextColor(getResources().getColor(R.color.inactive_product));
            }
            else
            {
                currentProductText.setTextColor(getResources().getColor(R.color.active_product));
            }
            currentProductText.setText(productListSpinner.getSelectedItem().toString());
            Toast.makeText(getActivity(), currentProductText.getText().toString(), Toast.LENGTH_SHORT).show();

            //todo make this nicer, it sets the current product to the product chosen from the list.
            Logic.instance.machines.get(id).currentProduct = Logic.instance.machines.get(id).productList.get(position);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}

        //todo send product change to database
    }

    public class CurrentContextPagerAdapter extends FragmentPagerAdapter {

        public CurrentContextPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return CurrentContextFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            //every machine.
            //GET number of machines
            return Logic.instance.machines.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            int number = position + 1;
            return "Machine " + number;
        }


    }
}
