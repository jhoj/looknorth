package fo.looknorth.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import fo.looknorth.app.app.R;
import fo.looknorth.logic.LooknorthLogic;
import fo.looknorth.model.Product;

public class ProductsInProductionContentFragment extends Fragment implements AdapterView.OnItemSelectedListener, Runnable {

    public int id;
    TextView currentProductText;
    Spinner productListSpinner;
    private ArrayAdapter arrayAdapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProductsInProductionContentFragment newInstance(int machineId)
    {
        ProductsInProductionContentFragment p = new ProductsInProductionContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("machineId", machineId);
        p.setArguments(bundle);
        return p;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products_in_production, container, false);

        id = getArguments().getInt("machineId");
        Product p = LooknorthLogic.instance.machines.get(id).currentProduct;
        LooknorthLogic.instance.observers.add(this);

        currentProductText = (TextView) rootView.findViewById(R.id.currently_active_product_text);
        productListSpinner = (Spinner) rootView.findViewById(R.id.product_list);
        arrayAdapter = new ArrayAdapter(getActivity(), R.layout.spinner_view_layout, R.id.productText, LooknorthLogic.instance.productList) {
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
        currentProductText.setText(p.toString());

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LooknorthLogic.instance.observers.remove(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        currentProductText.setText(LooknorthLogic.instance.machines.get(id).currentProduct.toString());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long idd) {

        // 0 = not active
        if (position == 0)
        {
            //red color
            currentProductText.setTextColor(getResources().getColor(R.color.inactive_product));
        }
        else
        {
            //green color
            currentProductText.setTextColor(getResources().getColor(R.color.active_product));
        }

        Product p = (Product) parent.getItemAtPosition(position);
        LooknorthLogic.instance.machines.get(id).currentProduct = p;
        LooknorthLogic.instance.putActiveProduct(id);

        for (Runnable r: LooknorthLogic.instance.observers) { r.run(); }

        Toast.makeText(getActivity(), currentProductText.getText().toString(), Toast.LENGTH_SHORT).show();

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        currentProductText.setText(LooknorthLogic.instance.machines.get(id).currentProduct.toString());
    }

    @Override
    public void run() {
        currentProductText.setText(LooknorthLogic.instance.machines.get(id).currentProduct.toString());
    }

}
