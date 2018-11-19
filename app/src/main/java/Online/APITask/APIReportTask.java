package Online.APITask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.nhquang.parking3.R;

import org.json.JSONObject;

import CustomView.CarTypeSpinnerCustom;
import CustomView.ListCarAdapter;
import Models.ServerClass.CarType;
import Online.API.APIReport;

/**
 * Created by QUANG-PC on 11/20/2017.
 */

public class APIReportTask extends AsyncTask<Void, Integer, Void> {


    //region var View
    //region var View
    ListView InOutStatisticAct_lstCar;
    TextView InOutStatisticAct_txt_SmartCardCode;
    TextView InOutStatisticAct_txt_SmartCardIndex;
    TextView InOutStatisticAct_txt_Digit;
    TextView InOutStatisticAct_txt_TimeStart;
    TextView InOutStatisticAct_txt_TimeEnd;
    TextView InOutStatisticAct_txt_Cost;
    TextView InOutStatisticAct_txt_IsActive;
    ImageView InOutStatisticAct_img_ImageIn;
    TextView InOutStatisticAct_txt_FromTime;
    TextView InOutStatisticAct_txt_ToTime;
    Button InOutStatisticAct_btn_Lookup;
    Spinner InOutStatisticAct_spinner_Query;
    Spinner InOutStatisticAct_spinner_CarType;

    public CarTypeSpinnerCustom spinnerCarTypeAdapter;
    //endregion var View
    //endregion var View

    //region var Process
    public CarType responresult;
    public int progressBarValue = 0;
    //endregion var Process

    public Activity _activity;
    public Context _context;
    public APIReport _apireport;
    JSONObject jsonObj = null;

    AlertDialog.Builder mBuilder;
    View mView;
    TextView dialog_loading_text;
    AlertDialog dialog;

    public APIReportTask(Context context, Activity activity, APIReport api, int _TaskIndex) {
        _context = context;
        _activity = activity;
        _apireport = api;
        FindViewByID();
    }

    public APIReportTask(Context context, Activity activity, int _TaskIndex, String img_url) {
        _context = context;
        _activity = activity;
        FindViewByID();
    }

    private void FindViewByID() {
        InOutStatisticAct_lstCar = (ListView) _activity.findViewById(R.id.InOutStatisticAct_lstCar);
        InOutStatisticAct_txt_SmartCardCode = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_SmartCardCode);
        InOutStatisticAct_txt_Digit = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_Digit);
        InOutStatisticAct_txt_TimeStart = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_TimeStart);
        InOutStatisticAct_txt_TimeEnd = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_TimeEnd);
        InOutStatisticAct_txt_Cost = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_Cost);
        InOutStatisticAct_txt_IsActive = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_IsActive);
        InOutStatisticAct_img_ImageIn = (ImageView) _activity.findViewById(R.id.InOutStatisticAct_img_ImageIn);
        InOutStatisticAct_txt_FromTime = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_FromTime);
        InOutStatisticAct_txt_ToTime = (TextView) _activity.findViewById(R.id.InOutStatisticAct_txt_ToTime);
        InOutStatisticAct_btn_Lookup = (Button) _activity.findViewById(R.id.InOutStatisticAct_btn_Lookup);
        InOutStatisticAct_spinner_Query = (Spinner) _activity.findViewById(R.id.InOutStatisticAct_spinner_Query);
        InOutStatisticAct_spinner_CarType = (Spinner) _activity.findViewById(R.id.InOutStatisticAct_spinner_CarType);
    }

    private void DoTaskSearchCarInOut() {
        try {
            ListCarAdapter listCarAdapter = new ListCarAdapter(_context, R.layout.list_car_adapter, _apireport.lstCar);
            InOutStatisticAct_lstCar.setAdapter(listCarAdapter);
        } catch (Exception ex) {
        }
    }

    //region asyncTask
    @Override
    protected void onPreExecute() {
        InOutStatisticAct_btn_Lookup.setEnabled(false);
        mBuilder = new AlertDialog.Builder(_activity, R.style.NewDialog);
        mView = _activity.getLayoutInflater().inflate(R.layout.dialog_loading, null);
        dialog_loading_text = (TextView) mView.findViewById(R.id.dialog_loading_text);
        dialog_loading_text.setText("Loading...");
        mBuilder.setView(mView);
        dialog = mBuilder.create();
        dialog.show();
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            while (_apireport.isComplete == false) {
                SystemClock.sleep(10);
                //publishProgress(1);
            }
            //publishProgress(1);
            _apireport.isComplete = false;
        } catch (Exception ex) {
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        InOutStatisticAct_btn_Lookup.setEnabled(true);
        DoTaskSearchCarInOut();
        dialog.cancel();
    }
    //endregion asyntask
}