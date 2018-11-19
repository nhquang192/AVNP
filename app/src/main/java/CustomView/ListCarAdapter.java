package CustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.nhquang.parking3.R;

import java.util.List;

import CTLs.Convert;
import Models.ServerClass.Car;

/**
 * Created by QUANG-PC on 11/14/2017.
 */

public class ListCarAdapter extends ArrayAdapter<Car> {

    public ListCarAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListCarAdapter(Context context, int resource, List<Car> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.list_car_adapter, null);
        }

        Car p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.listCar_Index);
            TextView tt2 = (TextView) v.findViewById(R.id.listCar_SmartCardCode);
            TextView tt3 = (TextView) v.findViewById(R.id.listCar_TimeStart);
            TextView tt4 = (TextView) v.findViewById(R.id.listCar_SoThe);

            if (tt1 != null) {
                tt1.setText(String.valueOf(position));
            }

            if (tt2 != null) {
                tt2.setText(p.getSmartCardCode());
            }
            if (tt4 != null) {
                tt4.setText(String.valueOf(p.getSmartCardID()));
            }

            if (tt3 != null) {
                tt3.setText(Convert.ConvertDatetimeToShow(p.getTimeStart()));
            }
        }

        return v;
    }

}