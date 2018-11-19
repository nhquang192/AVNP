package CustomView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nhquang.parking3.R;

import java.util.ArrayList;

import Models.ServerClass.CarType;

/**
 * Created by QUANG-PC on 11/14/2017.
 */

public class CarTypeSpinnerCustom extends BaseAdapter {
    Context context;
    public ArrayList<CarType> lstPart;
    LayoutInflater inflter;

    public CarTypeSpinnerCustom(Context applicationContext, ArrayList<CarType> ps) {
        this.context = applicationContext;
        lstPart=ps;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        try {
            return lstPart.size();
        }catch (Exception ex) {
            return 0;
        }
    }

    @Override
    public Object getItem(int i) {
        return lstPart.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_cartype_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.cartype_spinner_TextView);
        //names.setText(lstPart.get(i).getCarTypeID()+"   "+lstPart.get(i).getCardTypeName());
        names.setText(" "+lstPart.get(i).getCardTypeName());
        return view;
    }
}