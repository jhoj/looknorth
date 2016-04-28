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
import fo.looknorth.logik.Logik;

public class ProductsInProductionContentFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public static int id;
    TextView currentProductText;
    Spinner productListSpinner;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ProductsInProductionContentFragment newInstance(int machineId)
    {
        id = machineId;
        return new ProductsInProductionContentFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_products_in_production, container, false);

        currentProductText = (TextView) rootView.findViewById(R.id.currently_active_product_text);
        productListSpinner = (Spinner) rootView.findViewById(R.id.product_list);

        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, Logik.instance.productList) {
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
        currentProductText.setText(Logik.instance.machines[id].currentProduct.name);

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
        Logik.instance.machines[id].currentProduct = Logik.instance.productList.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    //todo send product change to database
}
