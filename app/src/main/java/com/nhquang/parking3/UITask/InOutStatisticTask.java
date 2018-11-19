package com.nhquang.parking3.UITask;

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

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.nhquang.parking3.R;

import org.json.JSONObject;

import java.util.ArrayList;

import CTLs.JSONtoClass;
import CustomView.CarTypeSpinnerCustom;
import CustomView.ListCarAdapter;
import Models.ServerClass.CarType;
import Online.API.APIReport;
import Online.Volley.CustomVolleyRequest;
import Online.Volley.VolleyRequestMethod;

/**
 * Created by QUANG-PC on 11/14/2017.
 */

public class InOutStatisticTask  extends AsyncTask<Void, Integer, Void> {

    public static class M1Status
    {
        public static int TaskLoadSpinnerCarType=1;
        public static int TaskSearchCarInOut=2;
        public static int TaskDownloadImage=3;
    }


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

    //region var Process
    public CarType responresult;
    public int progressBarValue=0;
    //endregion var Process

    public Activity _activity;
    public Context _context;
    public VolleyRequestMethod _carTypeAPI;
    public APIReport _reportAPI;
    public int TaskIndex=0;
    public String IMG_URL="";
    JSONObject jsonObj=null;

    AlertDialog.Builder mBuilder;
    View mView;
    TextView dialog_loading_text;
    AlertDialog dialog;

    public InOutStatisticTask(Context context,Activity activity, VolleyRequestMethod carTypeAPI, int _TaskIndex)
    {
        _context=context;
        _activity=activity;
        _carTypeAPI=carTypeAPI;
        TaskIndex=_TaskIndex;
        FindViewByID();
    }
    public InOutStatisticTask(Context context,Activity activity, int _TaskIndex, String img_url)
    {
        _context=context;
        _activity=activity;
        TaskIndex=_TaskIndex;
        IMG_URL=img_url;
        FindViewByID();
    }
    private void FindViewByID()
    {
        InOutStatisticAct_lstCar=(ListView)_activity.findViewById(R.id.InOutStatisticAct_lstCar);
        InOutStatisticAct_txt_SmartCardCode=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_SmartCardCode);
        InOutStatisticAct_txt_Digit=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_Digit);
        InOutStatisticAct_txt_TimeStart=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_TimeStart);
        InOutStatisticAct_txt_TimeEnd=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_TimeEnd);
        InOutStatisticAct_txt_Cost=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_Cost);
        InOutStatisticAct_txt_IsActive=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_IsActive);
        InOutStatisticAct_img_ImageIn=(ImageView)_activity.findViewById(R.id.InOutStatisticAct_img_ImageIn);
        InOutStatisticAct_txt_FromTime=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_FromTime);
        InOutStatisticAct_txt_ToTime=(TextView)_activity.findViewById(R.id.InOutStatisticAct_txt_ToTime);
        InOutStatisticAct_btn_Lookup=(Button)_activity.findViewById(R.id.InOutStatisticAct_btn_Lookup);
        InOutStatisticAct_spinner_Query=(Spinner)_activity.findViewById(R.id.InOutStatisticAct_spinner_Query);
        InOutStatisticAct_spinner_CarType=(Spinner)_activity.findViewById(R.id.InOutStatisticAct_spinner_CarType);
    }

    private void DoTaskLoadSpinnerCarType()
    {
        try{
            ArrayList<CarType> lstCarType= JSONtoClass.toListCarType(jsonObj.getJSONArray("data"));
            lstCarType.add(0,new CarType("Tất cả"));
            CarTypeSpinnerCustom carTypeSpinnerCustom=new CarTypeSpinnerCustom(_activity,lstCarType);
            InOutStatisticAct_spinner_CarType.setAdapter(carTypeSpinnerCustom);
        }
        catch (Exception ex){}
    }
    private void DoTaskSearchCarInOut()
    {
        try {
            //ArrayList<Car> lstCar = JSONtoClass.toListCar(jsonObj.getJSONArray("listCar"));
            ListCarAdapter listCarAdapter=new ListCarAdapter(_context,R.layout.list_car_adapter,((APIReport)_carTypeAPI).lstCar);
            InOutStatisticAct_lstCar.setAdapter(listCarAdapter);
        }
        catch (Exception ex){}
    }
    //region asyncTask
    @Override
    protected void onPreExecute() {
        InOutStatisticAct_btn_Lookup.setEnabled(false);
        mBuilder= new AlertDialog.Builder(_activity,R.style.NewDialog);
        mView=_activity.getLayoutInflater().inflate(R.layout.dialog_loading,null);
        dialog_loading_text=(TextView)mView.findViewById(R.id.dialog_loading_text);
        dialog_loading_text.setText("Loading...");
        mBuilder.setView(mView);
        dialog=mBuilder.create();
        dialog.show();
        super.onPreExecute();

    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            while (_carTypeAPI.isComplete == false) {
                SystemClock.sleep(10);
                //publishProgress(1);
            }
            if (TaskIndex == M1Status.TaskLoadSpinnerCarType)
                jsonObj=new JSONObject(_carTypeAPI.result[0]);
            else if (TaskIndex == M1Status.TaskSearchCarInOut) {
                jsonObj=new JSONObject(_carTypeAPI.result[0]);}
            //publishProgress(1);
            _carTypeAPI.isComplete = false;
        }catch (Exception ex){}
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        InOutStatisticAct_btn_Lookup.setEnabled(true);
        if (TaskIndex == M1Status.TaskLoadSpinnerCarType)
            DoTaskLoadSpinnerCarType();
        else if (TaskIndex == M1Status.TaskSearchCarInOut) {
            DoTaskSearchCarInOut();}
        else if (TaskIndex == M1Status.TaskDownloadImage) {
            DownloadImage(IMG_URL);
        }
        dialog.cancel();
    }
    //endregion asyntask
    public void DownloadImage(String img_url)
    {
        ImageLoader imageLoader ;
        imageLoader = CustomVolleyRequest.getInstance(_context)
                .getImageLoader();

        // If you are using normal ImageView
        imageLoader.get(img_url, new ImageLoader.ImageListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                //Log.e(TAG, "Image Load Error: " + error.getMessage());
            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean arg1) {
                if (response.getBitmap() != null) {
                    // load image into imageview
                    InOutStatisticAct_img_ImageIn.setImageBitmap(response.getBitmap());
                }
            }
        });
    }
}
