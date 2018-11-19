package com.nhquang.parking3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.nhquang.parking3.UITask.InOutStatisticTask;

import java.util.ArrayList;
import java.util.Calendar;

import CTLs.Convert;
import CustomView.CarTypeSpinnerCustom;
import CustomView.ListCarAdapter;
import Models.Base.ClientConfig;
import Models.CustomClass.QueryReportStatus;
import Models.ServerClass.Car;
import Models.ServerClass.CarType;
import Online.API.APICar;
import Online.API.APICarType;
import Online.API.APIReport;
import Online.APITask.APIReportTask;

public class InOutStatisticActivity extends FragmentActivity {

    //region var process
    private String dir;

    String fTime="";
    String tTime="";

    int year_f=0;
    int month_f=0;
    int day_f=0;
    int hour_f=0;
    int minute_f=0;
    int second_f=0;
    int year_t=0;
    int month_t=0;
    int day_t=0;
    int hour_t=0;
    int minute_t=0;
    int second_t=0;
    Car selectedCar;
    CarType selectCarType;

    static final  int DIALOG_FROM_DAY =0;
    static final  int DIALOG_TO_DAY=1;
    static final  int DIALOG_FROM_TIME=2;
    static final  int DIALOG_TO_TIME=3;

    APICarType apiCarType;
    APIReport apiReport;
    InOutStatisticTask inOutStatisticTask;
    public ArrayList<Car> lstCarBase=new ArrayList<Car>();
    //endregion var process

    //region var View
    Bitmap selectedIMG=null;
    ListView InOutStatisticAct_lstCar;
    TextView InOutStatisticAct_txt_SmartCardID;
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
    EditText InOutStatisticAct_TimKiemSoThe;

    public CarTypeSpinnerCustom spinnerCarTypeAdapter;
    //endregion var View

    public Context myContext;
    public Activity myActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_in_out_statistic);

        myContext=getBaseContext();
        myActivity=this;

        FindViewByID();

        //region first setup
        Calendar c = Calendar.getInstance();
        year_f = c.get(Calendar.YEAR);
        month_f= c.get(Calendar.MONTH)+1;
        day_f = c.get(Calendar.DAY_OF_MONTH);
        hour_f=c.get(Calendar.HOUR_OF_DAY);
        minute_f=c.get(Calendar.MINUTE);
        second_f=c.get(Calendar.SECOND);
        year_t=year_f;
        month_t=month_f;
        day_t=day_f;
        hour_t=hour_f;
        minute_t=minute_f+1;
        second_t=second_f;

        fTime=String.format("%02d",year_f)+"/"+String.format("%02d",month_f)+"/"+String.format("%02d",day_f)+" "+String.format("%02d",0)+":"+String.format("%02d",0)+":"+String.format("%02d",0);
        tTime=String.format("%02d",year_t)+"/"+String.format("%02d",month_t)+"/"+String.format("%02d",day_t)+" "+String.format("%02d",hour_t)+":"+String.format("%02d",minute_t)+":"+String.format("%02d",0);
        InOutStatisticAct_txt_FromTime.setText("Từ:   "+fTime);
        InOutStatisticAct_txt_ToTime.setText("Đến: "+tTime);
        //endregion first setup

        //region first load
        LoadSpinnerCarType();

        apiCarType=new APICarType(myContext);
        apiCarType.GetAll();
        inOutStatisticTask=new InOutStatisticTask(myContext,myActivity,apiCarType, InOutStatisticTask.M1Status.TaskLoadSpinnerCarType);
        inOutStatisticTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        /*try {
            apiReport=new APIReport(myContext);
            apiReport.SearchCarInOut(QueryReportStatus.XeVao, Convert.clientDateFormat2.parse(fTime),Convert.clientDateFormat2.parse(tTime),null,null);
            inOutStatisticTask=new InOutStatisticTask(myContext,myActivity,apiReport, InOutStatisticTask.M1Status.TaskSearchCarInOut);
            inOutStatisticTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        //endregion first load

        SetEventToView();
    }
    public void FindViewByID()
    {
        InOutStatisticAct_lstCar=(ListView)findViewById(R.id.InOutStatisticAct_lstCar);
        InOutStatisticAct_TimKiemSoThe=(EditText) findViewById(R.id.InOutStatisticAct_TimKiemSoThe);
        InOutStatisticAct_txt_SmartCardID=(TextView)findViewById(R.id.InOutStatisticAct_txt_SmartCardID);
        InOutStatisticAct_txt_SmartCardCode=(TextView)findViewById(R.id.InOutStatisticAct_txt_SmartCardCode);
        InOutStatisticAct_txt_Digit=(TextView)findViewById(R.id.InOutStatisticAct_txt_Digit);
        InOutStatisticAct_txt_TimeStart=(TextView)findViewById(R.id.InOutStatisticAct_txt_TimeStart);
        InOutStatisticAct_txt_TimeEnd=(TextView)findViewById(R.id.InOutStatisticAct_txt_TimeEnd);
        InOutStatisticAct_txt_Cost=(TextView)findViewById(R.id.InOutStatisticAct_txt_Cost);
        InOutStatisticAct_txt_IsActive=(TextView)findViewById(R.id.InOutStatisticAct_txt_IsActive);
        InOutStatisticAct_img_ImageIn=(ImageView)findViewById(R.id.InOutStatisticAct_img_ImageIn);
        InOutStatisticAct_txt_FromTime=(TextView)findViewById(R.id.InOutStatisticAct_txt_FromTime);
        InOutStatisticAct_txt_ToTime=(TextView)findViewById(R.id.InOutStatisticAct_txt_ToTime);
        InOutStatisticAct_btn_Lookup=(Button)findViewById(R.id.InOutStatisticAct_btn_Lookup);
        InOutStatisticAct_spinner_Query =(Spinner)findViewById(R.id.InOutStatisticAct_spinner_Query);
        InOutStatisticAct_spinner_CarType=(Spinner)findViewById(R.id.InOutStatisticAct_spinner_CarType);
    }
    private void DoTaskSearchCarInOut(String smartcardID) {
        if(apiReport.lstCar==null || apiReport.lstCar.size()<=0)
            return;
        ArrayList<Car> lstCar=new ArrayList<Car>();
        for (int i=0; i<apiReport.lstCar.size();i++)
        {
            Car abc=new Car();
            abc=apiReport.lstCar.get(i);
            if(smartcardID.equals(String.valueOf(abc.getSmartCardID())) || smartcardID.equals("") || abc.getDigit().contains(smartcardID))
            {
                lstCar.add(abc);
            }
        }
        try {
            ListCarAdapter listCarAdapter = new ListCarAdapter(myContext, R.layout.list_car_adapter,lstCar);
            InOutStatisticAct_lstCar.setAdapter(listCarAdapter);
        } catch (Exception ex) {
        }
    }
    public void SetEventToView()
    {
        InOutStatisticAct_txt_FromTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DIALOG_FROM_DAY);
            }
        });
        InOutStatisticAct_txt_ToTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showDialog(DIALOG_TO_DAY);
            }
        });
        InOutStatisticAct_TimKiemSoThe.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                    try {
                        DoTaskSearchCarInOut(InOutStatisticAct_TimKiemSoThe.getText().toString());
                    }catch (Exception ex)
                    {}
            }
        });

        InOutStatisticAct_lstCar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                //region process
                selectedCar=new Car();
                selectedCar=(Car)InOutStatisticAct_lstCar.getItemAtPosition(i);
                //selectedCar.setSmartCardCode(lsm2.get(i).getSmartCardCode());
                //selectedCar.setTimeStart(lsm2.get(i).getTimeStart());
                //selectedCar.setTimeEnd(lsm2.get(i).getTimeEnd());
                //selectedCar.setCost(lsm2.get(i).getCost());

                InOutStatisticAct_txt_SmartCardID.setText("Số Thẻ: "+selectedCar.getSmartCardID());
                InOutStatisticAct_txt_SmartCardCode.setText("Mã Thẻ: "+selectedCar.getSmartCardCode());
                InOutStatisticAct_txt_Digit.setText("Biển Số: "+selectedCar.getDigit());
                InOutStatisticAct_txt_TimeStart.setText("TG Vào: "+Convert.ConvertDatetimeToShow(selectedCar.getTimeStart()));
                InOutStatisticAct_txt_TimeEnd.setText("TG Ra: "+Convert.ConvertDatetimeToShow(selectedCar.getTimeEnd()));
                InOutStatisticAct_txt_Cost.setText("Tiền Thu: "+selectedCar.getCost());
                if(selectedCar.getTimeEnd()==null)
                {
                    InOutStatisticAct_txt_IsActive.setText("Tình Trạng: Chưa Ra");
                }
                else{
                    InOutStatisticAct_txt_IsActive.setText("Tình Trạng: Đã Ra");
                }
                //downloadimage
                String base_img_url= ClientConfig.BASE_API_URL+"UploadedFiles/CarIn/"+ Convert.imageFolderDateFormat.format(selectedCar.getTimeStart())+"/";
                inOutStatisticTask=new InOutStatisticTask(myContext,myActivity, InOutStatisticTask.M1Status.TaskDownloadImage,base_img_url+selectedCar.getImages1());
                inOutStatisticTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                //endregion process
            }
        });

        InOutStatisticAct_btn_Lookup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region process
                try
                {
                    selectCarType =(CarType) InOutStatisticAct_spinner_CarType.getSelectedItem();
                    apiReport=new APIReport(myContext);
                    if(InOutStatisticAct_spinner_Query.getSelectedItem().toString().equals("Xe Vào"))
                    {
                        if(selectCarType.getCarTypeID()==-1)
                            apiReport.SearchCarInOut(QueryReportStatus.XeVao, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), null, null);
                        else
                            apiReport.SearchCarInOut(QueryReportStatus.XeVao, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), String.valueOf(selectCarType.getCarTypeID()), null);
                    }
                    else if(InOutStatisticAct_spinner_Query.getSelectedItem().toString().equals("Xe Ra"))
                    {
                        if(selectCarType.getCarTypeID()==-1)
                            apiReport.SearchCarInOut(QueryReportStatus.XeRa, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), null, null);
                        else
                            apiReport.SearchCarInOut(QueryReportStatus.XeRa, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), String.valueOf(selectCarType.getCarTypeID()), null);
                    }
                    else if(InOutStatisticAct_spinner_Query.getSelectedItem().toString().equals("Xe Tồn"))
                    {
                        if(selectCarType.getCarTypeID()==-1)
                            apiReport.SearchCarInOut(QueryReportStatus.XeTon, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), null, null);
                        else
                            apiReport.SearchCarInOut(QueryReportStatus.XeTon, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), String.valueOf(selectCarType.getCarTypeID()), null);
                    }
                    else
                    {
                        if(selectCarType.getCarTypeID()==-1)
                            apiReport.SearchCarInOut(QueryReportStatus.XeChuaRa, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), null, null);
                        else
                            apiReport.SearchCarInOut(QueryReportStatus.XeChuaRa, Convert.clientDateFormat2.parse(fTime), Convert.clientDateFormat2.parse(tTime), String.valueOf(selectCarType.getCarTypeID()), null);
                    }
                    APIReportTask apiReportTask= new APIReportTask(myContext,myActivity,apiReport,0);
                    apiReportTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                }
                catch(Exception ex)
                {}
                //endregion process
            }
        });

        InOutStatisticAct_img_ImageIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //region process
                AlertDialog.Builder mBuilder= new AlertDialog.Builder(InOutStatisticActivity.this,R.style.NewDialog);
                View mView=getLayoutInflater().inflate(R.layout.dialog_inoutact_statistic,null);
                ImageView dialog_inoutact_statistic_img_CarIn=(ImageView)mView.findViewById(R.id.dialog_inoutact_statistic_img_CarIn);
                final Button dialog_inoutact_statistic_btn_SaveLostCard=(Button)mView.findViewById(R.id.dialog_inoutact_statistic_btn_SaveLostCard);
                TextView dialog_inoutact_statistic_txt_TimeStart=(TextView)mView.findViewById(R.id.dialog_inoutact_statistic_txt_TimeStart);
                TextView dialog_inoutact_statistic_txt_TimeEnd=(TextView)mView.findViewById(R.id.dialog_inoutact_statistic_txt_TimeEnd);
                TextView dialog_inoutact_statistic_txt_Digit=(TextView)mView.findViewById(R.id.dialog_inoutact_statistic_txt_Digit);
                TextView dialog_inoutact_statistic_txt_Cost=(TextView)mView.findViewById(R.id.dialog_inoutact_statistic_txt_Cost);
                TextView dialog_inoutact_statistic_txt_SmartCardCode=(TextView)mView.findViewById(R.id.dialog_inoutact_statistic_txt_SmartCardCode);
                TextView dialog_inoutact_statistic_txt_Status=(TextView)mView.findViewById(R.id.dialog_inoutact_statistic_txt_Status);
                Button dialog_inoutact_statistic_btn_btn_SaveLostCard=(Button) mView.findViewById(R.id.dialog_inoutact_statistic_btn_SaveLostCard);

                if(selectedCar!=null)
                {
                    dialog_inoutact_statistic_txt_SmartCardCode.setText("Mã Thẻ: "+selectedCar.getSmartCardCode());
                    dialog_inoutact_statistic_txt_Digit.setText("Biển số: "+ selectedCar.getDigit());
                    try {
                        dialog_inoutact_statistic_txt_TimeStart.setText("TG Vào: " + Convert.clientDateFormat2.format(selectedCar.getTimeStart()));
                    }catch (Exception ex1)
                    {dialog_inoutact_statistic_txt_TimeStart.setText("TG Vào: ");}
                    try {
                        dialog_inoutact_statistic_txt_TimeEnd.setText("TG Ra: " + Convert.clientDateFormat2.format(selectedCar.getTimeEnd()));
                    }catch (Exception ex1)
                    {dialog_inoutact_statistic_txt_TimeEnd.setText("TG Ra: ");}

                    dialog_inoutact_statistic_txt_Cost.setText("Tiền Thu: "+selectedCar.getCost());
                    if(selectedCar.getTimeEnd()==null)
                    {
                        dialog_inoutact_statistic_txt_Status.setText("Tình Trạng: Chưa Ra");
                        dialog_inoutact_statistic_btn_SaveLostCard.setEnabled(true);
                    }
                    else{
                        dialog_inoutact_statistic_txt_Status.setText("Tình Trạng: Đã Ra");
                        dialog_inoutact_statistic_btn_SaveLostCard.setEnabled(false);
                    }
                }
                else
                {
                    dialog_inoutact_statistic_btn_SaveLostCard.setEnabled(false);
                }
                Bitmap bitmap = ((BitmapDrawable)InOutStatisticAct_img_ImageIn.getDrawable()).getBitmap();
                dialog_inoutact_statistic_img_CarIn.setImageBitmap(bitmap);
                dialog_inoutact_statistic_btn_btn_SaveLostCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(InOutStatisticActivity.this);
                        builder1.setMessage("Bạn có muốn lưu mất thẻ này ?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Có",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        APICar apiCar=new APICar(myContext);
                                        apiCar.SaveLostCard(selectedCar.getSmartCardCode());
                                        Toast.makeText(getBaseContext(),"lưu mất thẻ", Toast.LENGTH_LONG);
                                        dialog_inoutact_statistic_btn_SaveLostCard.setEnabled(false);
                                        dialog.cancel();
                                    }
                                });

                        builder1.setNegativeButton(
                                "Không",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                });
                mBuilder.setView(mView);
                AlertDialog dialog=mBuilder.create();
                dialog.show();
                //endregion process
            }
        });
    }
    public void LoadSpinnerCarType()
    {
        apiCarType=new APICarType(myContext);
        apiCarType.GetAll();
        inOutStatisticTask=new InOutStatisticTask(myContext,myActivity,apiCarType, InOutStatisticTask.M1Status.TaskLoadSpinnerCarType);
        inOutStatisticTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
    //region DATETIME PICKER
    @Override
    protected Dialog onCreateDialog(int id)
    {
        if(id==DIALOG_FROM_DAY) {
            Dialog dpDialog=new DatePickerDialog(InOutStatisticActivity.this, fDatePickerListener, year_f, month_f-1, day_f);
            return dpDialog;
        }
        else if(id==DIALOG_FROM_TIME) {
            TimePickerDialog abc=new TimePickerDialog(InOutStatisticActivity.this, fTimePickerListener, hour_f, minute_f, true);
            return  abc;
        }
        else if(id==DIALOG_TO_DAY)
            return new DatePickerDialog(InOutStatisticActivity.this,tDatePickerListener,year_t,month_t-1,day_t);
        else if(id==DIALOG_TO_TIME)
            return new TimePickerDialog(InOutStatisticActivity.this,tTimePickerListener,hour_t,minute_t,true);
        return null;
    }
    protected  DatePickerDialog.OnDateSetListener fDatePickerListener =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year_f=i;
            month_f=i1+1;
            day_f=i2;
            fTime=String.format("%02d",year_f)+"/"+String.format("%02d",month_f)+"/"+String.format("%02d",day_f) +" 00:00:00";
            InOutStatisticAct_txt_FromTime.setText("Từ:   "+fTime);
            showDialog(DIALOG_FROM_TIME);
        }
    };

    protected TimePickerDialog.OnTimeSetListener fTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            hour_f=i;
            minute_f=i1;
            second_f=0;
            fTime=String.format("%02d",year_f)+"/"+String.format("%02d",month_f)+"/"+String.format("%02d",day_f)+" "+String.format("%02d",hour_f)+":"+String.format("%02d",minute_f)+":"+String.format("%02d",0);
            InOutStatisticAct_txt_FromTime.setText("Từ:   "+fTime);
        }
    };
    protected  DatePickerDialog.OnDateSetListener tDatePickerListener =new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            year_t=i;
            month_t=i1+1;
            day_t=i2;
            tTime=String.format("%02d",year_t)+"/"+String.format("%02d",month_t)+"/"+String.format("%02d",day_t)+" 00:00:00";
            InOutStatisticAct_txt_ToTime.setText("Đến: "+tTime);
            showDialog(DIALOG_TO_TIME);
        }
    };

    protected TimePickerDialog.OnTimeSetListener tTimePickerListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int i, int i1) {
            hour_t=i;
            minute_t=i1;
            second_t=0;
            tTime=String.format("%02d",year_t)+"/"+String.format("%02d",month_t)+"/"+String.format("%02d",day_t)+" "+String.format("%02d",hour_t)+":"+String.format("%02d",minute_t)+":"+String.format("%02d",second_t);
            InOutStatisticAct_txt_ToTime.setText("Đến: "+tTime);
        }
    };
    //endregion DATETIME PICKER
}
