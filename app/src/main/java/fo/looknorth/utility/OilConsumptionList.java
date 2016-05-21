package fo.looknorth.utility;

import java.util.ArrayList;

/**
 * Created by jakup on 5/20/16.
 */
public class OilConsumptionList<T> extends ArrayList<T> {

    public OilConsumptionList() {
        super();
    }

    @Override
    public boolean add(T t) {

        //the list should only hold 10 items
        if (super.size() > 9) {

            //remove the first object
            super.remove(0);

            return super.add(t);
        } else {
            return super.add(t);
        }
    }
}
