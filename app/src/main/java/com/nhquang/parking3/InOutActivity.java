package com.nhquang.parking3;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nhquang.parking3.UITask.InOutTask;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import CTLs.CameraPreview;
import CTLs.Convert;
import CTLs.IPSS;
import CTLs.ImageFunc;
import Models.Base.ClientConfig;
import Models.Base.OnlineServerConfig;
import Models.CustomClass.InOutProperties;
import Models.ServerClass.Car;
import Offline.MyFiles.FileManager;
import Online.API.APICar;
import Online.API.APIImage;

public class InOutActivity extends FragmentActivity {

    //region var View
    FrameLayout InOut_cameraLayout;
    ImageView InOut_img_In;
    ImageView InOut_img_Out2;
    TextView InOut_tv_InfoInOut;
    TextView InOut_tv_Cost;
    TextView InOut_tv_Notice;
    Button InOut_btn_SwapLane;
    LinearLayout InOut_containerFrame;
    ProgressBar InOut_ProgressBar;
    ImageView InOut_img_SuccesStatus;
    //endregion var View

    //region var Process
    Camera camera;
    CameraPreview camera_preview;
    public File sdRoot;
    public String dir;
    public File sdRootSD;
    public String dirSD;
    public String nfcCardID ="";

    public APICar carAPI;
    public InOutTask inOutTask;
    public NfcAdapter nfcAdapter;

    public String currentCardID = "";//
    public String currentImgName = "";
    public String cDateTime = "";
    public ImageView currentImg = null;//

    public String in_oldID ="";
    public String in_oldFileName ="";
    public Boolean in_isUpdate=false;
    public Boolean in_isCapture=false;
    public Car in_oldCar=null;

    public Boolean isINprocess=true;

    public static InOutProperties prop;
    //endregion var Process

    //region var x y of View
    int InOut_cameraLayoutH = 0;
    int InOut_img_InH = 0;
    int InOut_tv_InfoInOutH = 0;
    int InOut_img_Out2H = 0;
    int InOut_containerFrameH = 0;
    int InOut_tv_NoticeH = 0;
    int InOut_btn_SwapLaneH = 0;
    int InOut_ProgressBarH =0;
    int InOut_cameraLayoutW = 0;
    int InOut_img_InW = 0;
    int InOut_tv_InfoInOutW = 0;
    int InOut_img_Out2W = 0;
    int InOut_containerFrameW = 0;
    int InOut_tv_NoticeW = 0;
    int InOut_btn_SwapLaneW = 0;
    int InOut_ProgressBarW =0;

    int actionbarSize=0;
    int device_width = 0;
    int device_height = 0;
    //endregion var x y of View

    public Context myContext;
    public Activity myActivity;
    public String oldIDOut="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_in);

        myContext=getBaseContext();
        myActivity=this;
        isINprocess=true;

        //region tao folder luu hinh
        sdRoot = Environment.getExternalStorageDirectory();
        sdRoot = new File(FileManager.SD_PATH);//
        dir = "/VNP/Camera/";
        FileManager.createFolder(FileManager.SD_PATH
                + "/VNP/");
        FileManager.createFolder(FileManager.SD_PATH
                + FileManager.IMG_FOLDER_PATH);
        FileManager.createFolder(FileManager.SD_PATH
                + FileManager.IMG_IN_FOLDER_PATH);
        FileManager.createFolder(FileManager.SD_PATH
                + FileManager.IMG_OUT_FOLDER_PATH);

        //endregion tao folder luu hinh

        newProp();

        FindViewByID();
        prop.in_isUpdate=false;
        prop.in_oldCardID ="";
        prop.in_oldFileName="";
        SetEventToView();
        getCurrentXY();
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        ViewIn();
    }
    private void getCurrentXY()
    {
        final TypedArray styledAttributes = getBaseContext().getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        actionbarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        device_width = (int) getBaseContext().getResources()
                .getDisplayMetrics().widthPixels;
        device_height = (int) getBaseContext().getResources()
                .getDisplayMetrics().heightPixels - actionbarSize;

        int StatusH=(int)device_height/13;

        InOut_cameraLayoutH = device_width-60;
        InOut_cameraLayoutW = device_width;

        int imgHeight=(int)(device_height - device_width)-StatusH+50;
        int imgWidth=(int)(device_height - device_width)-StatusH;
        InOut_img_InH = imgHeight+60;
        InOut_img_InW = imgWidth;

        InOut_tv_InfoInOutH = imgHeight-150;
        InOut_tv_InfoInOutW =(int)(device_width-imgWidth);

        InOut_img_Out2H = device_width-60;
        InOut_img_Out2W = device_width;

        InOut_tv_NoticeH = StatusH;
        InOut_tv_NoticeW = device_width-50;

        InOut_btn_SwapLaneH = 200;
        InOut_btn_SwapLaneW = (int)(device_width-imgWidth)-12;

        InOut_ProgressBarH =5;
        InOut_ProgressBarW =device_width;

    }
    private void newProp()
    {
        prop=new InOutProperties();
        prop.nfcCardID ="";
        prop.in_isUpdate=false;
        prop.in_oldCar=null;
        prop.in_oldCardID ="";
        prop.isINprocess=true;
        prop.isProcessing=false;
        prop.in_oldFileName="";
        prop.in_FileName="";
    }
    private void FindViewByID() {
        InOut_cameraLayout = (FrameLayout) findViewById(R.id.MInOut_cameraLayout);
        InOut_img_In = (ImageView) findViewById(R.id.MInOut_img_In);
        InOut_tv_InfoInOut = (TextView) findViewById(R.id.MInOut_tv_InfoInOut);
        InOut_tv_Cost = (TextView) findViewById(R.id.MInOut_tv_Cost);
        InOut_img_Out2 = (ImageView) findViewById(R.id.MInOut_img_Out2);
        InOut_containerFrame = (LinearLayout) findViewById(R.id.MInOut_containerFrame);
        InOut_tv_Notice = (TextView) findViewById(R.id.MInOut_tv_Notice);
        InOut_btn_SwapLane = (Button) findViewById(R.id.MInOut_btn_SwapLane);
        InOut_ProgressBar =(ProgressBar)findViewById(R.id.MInOut_ProgressBar);

    }
    private void ViewIn()
    {
        ViewGroup.LayoutParams params;
        // set camera_preview parameters______________________checked
        params = InOut_cameraLayout.getLayoutParams();
        params.height = InOut_cameraLayoutH;
        params.width = InOut_cameraLayoutW;
        InOut_cameraLayout.setLayoutParams(params);

        params = InOut_img_Out2.getLayoutParams();
        params.height = 1;
        params.width = 1;
        InOut_img_Out2.setLayoutParams(params);

        params = InOut_tv_Notice.getLayoutParams();
        params.height = InOut_tv_NoticeH ;
        params.width = InOut_tv_NoticeW;
        InOut_tv_Notice.setLayoutParams(params);
        InOut_tv_Notice.setText("    XE VÀO");

        params = InOut_img_In.getLayoutParams();
        params.height = InOut_img_InH;
        params.width = InOut_img_InW;
        InOut_img_In.setLayoutParams(params);

        params = InOut_tv_InfoInOut.getLayoutParams();
        params.height = InOut_tv_InfoInOutH;
        params.width = InOut_tv_InfoInOutW;
        InOut_tv_InfoInOut.setLayoutParams(params);
        InOut_tv_InfoInOut.setTextSize(16);

        params = InOut_tv_Cost.getLayoutParams();
        params.height = 1;
        params.width = 1;
        InOut_tv_Cost.setLayoutParams(params);
        InOut_tv_Cost.setTextSize(20);

        params = InOut_btn_SwapLane.getLayoutParams();
        params.height = InOut_btn_SwapLaneH;
        params.width = InOut_btn_SwapLaneW;
        InOut_btn_SwapLane.setLayoutParams(params);

        params = InOut_ProgressBar.getLayoutParams();
        params.height = InOut_ProgressBarH;
        params.width = InOut_ProgressBarW;
        InOut_ProgressBar.setLayoutParams(params);

        InOut_img_Out2.setImageBitmap(null);
        InOut_img_In.setImageBitmap(null);
    }
    private void ViewOut()
    {
        ViewGroup.LayoutParams params;
        // set camera_preview parameters______________________checked
        params = InOut_cameraLayout.getLayoutParams();
        params.height = 1;
        params.width = 1;
        InOut_cameraLayout.setLayoutParams(params);

        params = InOut_img_Out2.getLayoutParams();
        params.height = InOut_cameraLayoutH;
        params.width = InOut_cameraLayoutW;
        InOut_img_Out2.setLayoutParams(params);

        params = InOut_tv_Notice.getLayoutParams();
        params.height = InOut_tv_NoticeH ;
        params.width = InOut_tv_NoticeW;
        InOut_tv_Notice.setLayoutParams(params);
        InOut_tv_Notice.setText("XE RA");

        params = InOut_img_In.getLayoutParams();
        params.height = 1;
        params.width = 1;
        InOut_img_In.setLayoutParams(params);

        params = InOut_tv_InfoInOut.getLayoutParams();
        params.height = InOut_tv_InfoInOutH-90;
        params.width = device_width;
        InOut_tv_InfoInOut.setLayoutParams(params);
        InOut_tv_InfoInOut.setTextSize(19);

        params = InOut_tv_Cost.getLayoutParams();
        params.height = 100;
        params.width = device_width;
        InOut_tv_Cost.setLayoutParams(params);
        InOut_tv_Cost.setTextSize(46);


        params = InOut_btn_SwapLane.getLayoutParams();
        params.height = InOut_btn_SwapLaneH-10;
        params.width = device_width;
        InOut_btn_SwapLane.setLayoutParams(params);

        params = InOut_ProgressBar.getLayoutParams();
        params.height = InOut_ProgressBarH;
        params.width = InOut_ProgressBarW;
        InOut_ProgressBar.setLayoutParams(params);

        InOut_img_Out2.setImageBitmap(null);
        InOut_img_In.setImageBitmap(null);
    }
    private void SwapLayout()
    {
        oldIDOut="";
        cleanText();
        if(isINprocess==true) {
            ViewOut();
            isINprocess=false;
            newProp();
            prop.isINprocess=false;

        }
        else {
            ViewIn();
            isINprocess=true;
            newProp();
            prop.in_isUpdate=false;
            prop.in_oldCardID ="";
            prop.in_oldFileName="";
            prop.isINprocess=true;

        }
    }
    private void SetEventToView() {
        InOut_btn_SwapLane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwapLayout();
            }
        });
    }
    //region setup camera
    private void SetCameraParameters() {
        Camera.Parameters params = camera.getParameters();
        params.setJpegQuality(60);
        camera.setParameters(params);
        camera.setDisplayOrientation(90);
    }
    private void createCamera() {
        // Create an instance of Camera
        camera = getCameraInstance();
        // Setting the right parameters in the camera
        SetCameraParameters();
        // Create our Preview view and set it as the content of our activity.
        camera_preview = new CameraPreview(this, camera);
        // Adding the camera preview after the FrameLayout and before the mainact_btn_InOutAct
        // as a separated element.
        InOut_cameraLayout.addView(camera_preview, 0);
    }
    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            // attempt to get a Camera instance
            c = Camera.open();
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
        }
        // returns null if camera is unavailable
        return c;
    }
    //endregion setup camera

    //region NFC setup
    private void enableForegroundDispatchSystem() {
        if(ClientConfig.CURRENT_USER=="" || ClientConfig.CURRENT_USER.equals("")) {
            Toast.makeText(this,"Xin đăng nhập trước khi quét thẻ",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            Intent intent = new Intent(this, InOutActivity.class).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            IntentFilter[] intentFilters = new IntentFilter[]{};
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, null);
        }
    }
    private void disableForegroundDispatchSystem() {
        if(ClientConfig.CURRENT_USER=="" || ClientConfig.CURRENT_USER.equals("")) {
            Toast.makeText(this,"Xin đăng nhập trước khi quét thẻ",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(ClientConfig.CURRENT_USER=="" || ClientConfig.CURRENT_USER.equals("")) {
            Toast.makeText(this,"Xin đăng nhập trước khi quét thẻ",Toast.LENGTH_LONG).show();
            return;
        }
        else {
            if (intent.hasExtra(NfcAdapter.EXTRA_TAG)) {
                if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
                    prop.nfcCardID =Convert.bytesToHex(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID));
                    if (prop.nfcCardID.equals("") == true) {
                    } else {
                        try {
                            camera.takePicture(null, null, mPicture);
                        } catch (Exception ex) {
                            ex.getMessage();
                        }
                    }
                }
            }
        }
    }
    //endregion NFC setup

    //region IN OUT process
    protected void OUTProcess(byte[] dataCamera)
    {
        if(oldIDOut.length()==8 && oldIDOut.equals(prop.nfcCardID)==true)
            return;
        //region OUT
        Bitmap bmp;
        prop.timeScan = new Date();
        //1.Get image from camera
        //BitmapFactory.Options o = new BitmapFactory.Options();
        //o.inSampleSize = 2;
        //bmp = BitmapFactory.decodeByteArray(dataCamera, 0, dataCamera.length, o);
        //bmp = ImageFunc.ResizedBitmap(bmp, 400, 400);
        //prop.imgBitmap =bmp;
       // prop.imgBitmap = ImageFunc.FixBitmapfromCamera(dataCamera);
        //2.Get Digit from image with ipss
        //prop.ipssDigit= IPSS.getDigit(prop.imgBitmap,this);
        prop.ipssDigit= "0000";
        prop.in_FileName = prop.nfcCardID +"_"+ Convert.imageDateFormat.format(prop.timeScan).toString()+"_"+prop.ipssDigit+"_3.jpg";
        //Bitmap imgSaveSucces=ImageFunc.SaveImage(prop.imgBitmap,sdRoot,dir, prop.in_FileName);
        //Bitmap imgSaveSucces=new Bitmap();
        if(1==1) {

            //data MCar
            Car mCar=new Car();
            mCar.setSmartCardCode(prop.nfcCardID);
            mCar.setTimeEnd(prop.timeScan);
            mCar.setDigit(prop.ipssDigit);
            mCar.setImages3(prop.in_FileName);
            mCar.setImages4(prop.in_FileName);
            //using CarAPI.ScanOut
            carAPI=new APICar(myContext);
            carAPI.ScanCarOut(mCar);
            inOutTask =new InOutTask(myContext,myActivity,carAPI,prop);
            inOutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            oldIDOut=prop.nfcCardID;
        }else {
            InOut_tv_InfoInOut.setText("Lỗi lưu hình");
            return;
        }
        //endregion OUT
    }

    protected void INProcess(byte[] dataCamera)
    {
        Bitmap bmp;
        try {
            prop.timeScan = new Date();//get current datetime of client
            //region lay hinh anh tu camera
            prop.imgBitmap =ImageFunc.FixBitmapfromCamera(dataCamera);
            //endregion lay hinh anh tu camera

            Bitmap bm=prop.imgBitmap;
            try {
                if(ClientConfig.IS_IPSS==true)
                    prop.ipssDigit = IPSS.getDigit(bm, this);// nhan dang bien so bang ipss
                    //prop.ipssDigit="0000";
                else
                    prop.ipssDigit="0000";
            }catch (Exception exx) {
                prop.ipssDigit="0000";
            }
            prop.in_FileName = prop.nfcCardID +"_"+ Convert.imageDateFormat.format(prop.timeScan).toString()+"_"+prop.ipssDigit+"_1.jpg";
            dir=FileManager.IMG_IN_FOLDER_PATH+FileManager.PRESENT_DAY;
            Bitmap imgSaveSucces=ImageFunc.SaveImage(prop.imgBitmap,sdRoot,dir, prop.in_FileName);
            //up imageSaveSuccess to server

            if(imgSaveSucces!=null) {
                APIImage imageAPI=new APIImage(myContext);
                imageAPI.UploadImage(prop.in_FileName,imgSaveSucces);

                //tao doi tuong MCar de luu data
                Car mCar=new Car();
                mCar.setSmartCardCode(prop.nfcCardID);
                mCar.setDigit(prop.ipssDigit);
                mCar.setTimeStart( prop.timeScan);
                mCar.setImages1(prop.in_FileName);
                mCar.setImages2(prop.in_FileName);
                //su dung CarAPI.ScanIn
                carAPI=new APICar(myContext);
                carAPI.ScanCarIn(mCar);
                inOutTask =new InOutTask(myContext,myActivity,carAPI,prop);
                inOutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }else {
                InOut_tv_InfoInOut.setText("Lỗi lưu hình");
            }
        } catch (Exception e) {
            Log.d("DG_DEBUG", "File not found: " + e.getMessage());
        }
        try{
            //File pictureFile2 = new File(sdRootSD, "/SD_VNP/" + fileName);
            //copyFile(pictureFile,pictureFile2);
        }catch (Exception ex){}

    }
    protected void INProcessUpdate(byte[] dataCamera)
    {
        try {
            prop.timeScan = OnlineServerConfig.ServertimeScan;//get current datetime of client
            //region lay hinh anh tu camera
            prop.imgBitmap =ImageFunc.FixBitmapfromCamera(dataCamera);
            //endregion lay hinh anh tu camera
            Bitmap bm=prop.imgBitmap;
            try {
                if(ClientConfig.IS_IPSS==true) {
                    prop.ipssDigit = IPSS.getDigit(bm, this);// nhan dang bien so bang ipss
                }
                else
                    prop.ipssDigit="0000";
            }catch (Exception exx) {
                prop.ipssDigit="0000";
            }
            prop.in_FileName = prop.nfcCardID +"_"+ Convert.imageDateFormat.format(prop.timeScan).toString()+"_"+prop.ipssDigit+"_1.jpg";
            dir=FileManager.IMG_IN_FOLDER_PATH+FileManager.PRESENT_DAY;
            Bitmap imgSaveSucces=ImageFunc.SaveImage(prop.imgBitmap,sdRoot,dir, prop.in_FileName);

            if(imgSaveSucces!=null) {
                APIImage imageAPI=new APIImage(myContext);
                imageAPI.UploadImage(prop.in_FileName,imgSaveSucces);
                //tao doi tuong MCar de luu data
                Car mCar=prop.in_oldCar;
                mCar.setDigit(prop.ipssDigit);
                mCar.setTimeStart( prop.timeScan);
                mCar.setImages1(prop.in_FileName);
                mCar.setImages2(prop.in_FileName);
                //su dung CarAPI.ScanIn
                carAPI=new APICar(myContext);
                carAPI.Update(mCar);
                inOutTask =new InOutTask(myContext,myActivity,carAPI,prop);
                inOutTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }else {
                InOut_tv_InfoInOut.setText("Lỗi lưu hình");
            }
        } catch (Exception e) {
            Log.d("DG_DEBUG", "File not found: " + e.getMessage());
        }
        try{
            //File pictureFile2 = new File(sdRootSD, "/SD_VNP/" + fileName);
            //copyFile(pictureFile,pictureFile2);
        }catch (Exception ex){}
    }
    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            camera.startPreview();
            if(prop.isINprocess==true) {
                if(prop.nfcCardID.equals(prop.in_oldCardID))//kiem tra the update and the da dung =true
                {
                    prop.in_isUpdate=true;
                    prop.in_oldCardID = prop.nfcCardID;
                    prop.in_oldFileName=prop.in_FileName;
                }
                else
                {
                    prop.in_isUpdate=false;
                }

                if(prop.in_isUpdate==true)
                {
                    INProcessUpdate(data);
                }
                else {
                    INProcess(data);
                }
            }
            else
            {
                OUTProcess(data);
            }

        }
    };
    //endregion IN OUT process

    //region swap layout

    //endregion swap layout
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        FileManager.PRESENT_DAY = "/"
                + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "/";
        FileManager.createFolder(FileManager.SD_PATH
                + FileManager.IMG_IN_FOLDER_PATH + FileManager.PRESENT_DAY);
        if(camera==null) {
            try {
                createCamera();
            }catch (Exception ex2)
            {}
        }
        enableForegroundDispatchSystem();
        super.onResume();
    }

    @Override
    protected void onPause() {
        disableForegroundDispatchSystem();
        super.onPause();
    }

    private long exitTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                Toast.makeText(getApplicationContext(),
                        "press back again to exit",
                        Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void cleanText()
    {
        InOut_tv_Cost.setText("");
        InOut_tv_InfoInOut.setText("");
    }
}
