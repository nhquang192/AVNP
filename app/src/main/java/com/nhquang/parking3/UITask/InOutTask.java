package com.nhquang.parking3.UITask;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.text.Html;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.nhquang.parking3.InOutActivity;
import com.nhquang.parking3.R;

import org.json.JSONObject;

import java.text.DecimalFormat;

import CTLs.Convert;
import CTLs.JSONtoClass;
import Models.Base.ClientConfig;
import Models.Base.OnlineServerConfig;
import Models.CustomClass.InOutProperties;
import Models.ServerClass.Car;
import Models.ServerClass.TicketMonth;
import Online.API.APICar;
import Online.API.APITicketMonth;
import Online.Volley.CustomVolleyRequest;

/**
 * Created by QUANG-PC on 11/13/2017.
 */

public class InOutTask extends AsyncTask<Void, Integer, InOutProperties> {

    //region var View
    ProgressBar InOut_ProgressBar;
    ImageView InOut_img_SuccesStatus;
    ImageView InOut_img_In;
    ImageView InOut_img_Out2;
    TextView InOut_tv_InfoInOut;
    TextView InOut_tv_Cost;
    TextView InOut_tv_Notice;
    Button InOut_btn_SwapLane;
    //endregion var View

    //region var Process
    public Car responresult;
    public int progressBarValue=0;
    public InOutProperties _prop;
    //endregion var Process

    public Activity _activity;
    public Context _context;
    public APICar _carAPI;
    public APITicketMonth _apiTicketMonth;
    TicketMonth ticketMonth=null;


    public InOutTask(Context context, Activity activity, APICar carAPI, InOutProperties prop)
    {
        _context=context;
        _activity=activity;
        _carAPI=carAPI;
        _prop=prop;
        FindViewByID();
    }
    private void FindViewByID() {
        InOut_img_In = (ImageView) _activity.findViewById(R.id.MInOut_img_In);
        InOut_tv_InfoInOut = (TextView) _activity.findViewById(R.id.MInOut_tv_InfoInOut);
        InOut_tv_Cost = (TextView) _activity.findViewById(R.id.MInOut_tv_Cost);
        InOut_img_Out2 = (ImageView) _activity.findViewById(R.id.MInOut_img_Out2);
        InOut_tv_Notice = (TextView) _activity.findViewById(R.id.MInOut_tv_Notice);
        InOut_btn_SwapLane = (Button) _activity.findViewById(R.id.MInOut_btn_SwapLane);
        InOut_ProgressBar =(ProgressBar)_activity.findViewById(R.id.MInOut_ProgressBar);
        InOut_img_SuccesStatus =(ImageView)_activity.findViewById(R.id.MInOut_img_SuccesStatus);
    }
    //region asyncTask
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        cleanText();
        InOut_btn_SwapLane.setEnabled(false);
        progressBarValue=0;
        InOut_img_SuccesStatus.setImageResource(R.mipmap.no_check_circle);
        _prop.isProcessing=true;//dang thuc hien task,
        _apiTicketMonth=new APITicketMonth(_context);
        //_apiTicketMonth.CheckStatusTickMonth(_prop.nfcCardID,0);
        _apiTicketMonth.isComplete=true;
    }

    @Override
    protected InOutProperties doInBackground(Void... params) {

        //while(_apiTicketMonth.isComplete==false) {}
        while (_carAPI.isComplete == false) {
            SystemClock.sleep(10);
            progressBarValue++;
            publishProgress(progressBarValue);
        }
        _apiTicketMonth.isComplete=false;
        _carAPI.isComplete=false;
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if(values[0]==0) {
            InOut_tv_InfoInOut.setText("processing... !!!");
        }
        if(progressBarValue<90) {
            InOut_ProgressBar.setProgress(progressBarValue);
        }
    }

    @Override
    protected void onPostExecute(InOutProperties aVoid) {
        InOut_btn_SwapLane.setEnabled(true);
        JSONObject jsonObject = null;
        JSONObject jsonObject2 = null;
        ticketMonth=null;

        try {
            jsonObject2 = new JSONObject(_apiTicketMonth.result[0]);
            ticketMonth=JSONtoClass.toTicketMonth(jsonObject2);
        }catch (Exception exxx)
        {}
        try {
            jsonObject = new JSONObject(_carAPI.result[0]);
            responresult = JSONtoClass.toMCar(jsonObject);

            _carAPI.Status = jsonObject.getInt("status");
            _carAPI.StatusName = jsonObject.getString("statusName");
        }catch (Exception ex)
        {
            if(responresult!=null)
            {
                _carAPI.Status = 0;
                _carAPI.StatusName = "";
            }
            ex.getMessage();
        }
        if (_prop.isINprocess == true) {
            if(_prop.in_isUpdate==true)
            {
                UI_InUpdate();
            }
            else
            {
                UI_In();
            }
        }
        else  {
            UI_Out();
        }
        InOut_img_SuccesStatus.setImageResource(R.mipmap.yes_check_circle);
        InOut_ProgressBar.setProgress(100);
        _prop.isProcessing=false;
        aVoid=_prop;
        super.onPostExecute(aVoid);
    }
    //endregion asyntask
    protected void UI_In()
    {
        try {
            if(_prop.isINprocess==true) {
                if (_carAPI.Status == 0) {
                    InOut_img_In.setImageBitmap(_prop.imgBitmap);
                    if(ticketMonth!=null)
                    {
                        InOut_tv_InfoInOut.setText(responresult.getCarTypeName() +"\n"+ticketMonth.Digit+ "\n, mời xe vào");
                    }
                    else
                    {
                        InOut_tv_InfoInOut.setText(responresult.getCarTypeName() + ", mời xe vào"+ "\n" + "Biển số: " + _prop.ipssDigit);
                    }
                    _prop.in_oldCardID = _prop.nfcCardID;
                    _prop.in_oldFileName = _prop.in_FileName;
                    _prop.in_oldCar = responresult;
                    InOutActivity.prop = _prop;
                    OnlineServerConfig.ServertimeScan=responresult.getTimeStart();
                } else {
                    InOut_tv_InfoInOut.setText(_carAPI.StatusName);
                }
            }
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }
    protected void UI_Out()
    {
        try {
            if(_prop.isINprocess==false ){
                if (_carAPI.Status == 0) {//"Biển số: " + responresult.getDigit() + "\n" +
                    String thongbaoxera ="";
                    if (ticketMonth!=null) {
                        String text = "This is <font color='red'>red</font>. This is <font color='blue'>blue</font>.";

                        thongbaoxera = responresult.getCarTypeName() + ",tháng:   " + ticketMonth.Digit +"<br/>"+
                                "<font color='#cc0000'>Tg vào&nbsp;:&nbsp;</font>" + Convert.ConvertDatetimeToShow(responresult.getTimeStart()) + "<br/>" +
                                "<font color='#009900'>Tg ra </font>" + Convert.ConvertDatetimeToShow(responresult.getTimeEnd()) + "\n";
                    }
                    else
                    {
                        String[] temp=(Convert.ConvertDatetimeToShow(responresult.getTimeStart())).split(" ");
                        String[] temp2=(Convert.ConvertDatetimeToShow(responresult.getTimeEnd())).split(" ");
                        thongbaoxera = responresult.getCarTypeName()+":   "+responresult.getDigit()+"<br/>"+
                                "<font color='#e60000'>Tg vào:&nbsp;" +temp[0] +"&nbsp;&nbsp;&nbsp;"+temp[1]+"</font>"+ "<br/>" +
                                "<font color='#007700'>&nbsp;Tg ra&nbsp;&nbsp;&nbsp;:&nbsp;</font>"+"<font color='#007700'>"+ temp2[0]+"&nbsp;&nbsp;&nbsp;"+"</font>" +"<font color='#007700'>"+temp2[1]+"</font>"+ "\n";
                    }
                    InOut_tv_InfoInOut.setText(Html.fromHtml(thongbaoxera), TextView.BufferType.SPANNABLE);
                    InOut_tv_Cost.setText(new DecimalFormat("#,###,###").format(responresult.getCost()));
                    ///UploadedFiles/CarIn/0C5DBD491642170001.jpg"
                    String base_img_url = ClientConfig.BASE_API_URL+"UploadedFiles/CarIn/" + Convert.imageFolderDateFormat.format(responresult.getTimeStart()) + "/";
                    DownloadImage(base_img_url + responresult.getImages1());
                    //InOut_img_Out.setImageBitmap(ImageFunc.GetImgIn(Convert.clientDateFormat.format(responresult.getTimeStart()), responresult.getImages1()));
                } else {
                    InOut_tv_InfoInOut.setText(_carAPI.StatusName);
                }
            }
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }
    protected void UI_InUpdate()
    {
        try {
            if(_prop.isINprocess==true) {
                if (_carAPI.Status == 0) {
                    InOut_img_In.setImageBitmap(_prop.imgBitmap);
                    if(ticketMonth!=null) {
                        InOut_tv_InfoInOut.setText(responresult.getCarTypeName()+"\n"+ticketMonth.Digit + "\n(cập nhật hình ảnh)");
                    }
                    else
                    {
                        InOut_tv_InfoInOut.setText(responresult.getCarTypeName() +"\n"+_prop.ipssDigit+ "\n(cập nhật hình ảnh)");
                    }
                    _prop.in_oldCardID = _prop.nfcCardID;
                    _prop.in_oldFileName = _prop.in_FileName;
                    _prop.in_oldCar = responresult;
                    InOutActivity.prop = _prop;
                } else {
                    InOut_tv_InfoInOut.setText(_carAPI.StatusName);
                }
            }
        }catch (Exception ex)
        {
            ex.getMessage();
        }
    }
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
                    InOut_img_Out2.setImageBitmap(response.getBitmap());
                }
            }
        });
    }

    private void cleanText()
    {
        InOut_tv_Cost.setText("");
        InOut_tv_InfoInOut.setText("");
    }
}