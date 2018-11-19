package Models.CustomClass;

import android.graphics.Bitmap;

import java.util.Date;

import Models.ServerClass.Car;

/**
 * Created by QUANG-PC on 11/13/2017.
 */

public class InOutProperties {
    public String nfcCardID ="";
    public String in_oldCardID ="";
    public String in_FileName ="";
    public String in_oldFileName ="";
    public Boolean in_isUpdate=false;
    public Boolean isProcessing=false;
    public Car in_oldCar=null;
    public Boolean isINprocess=true;
    public Date timeScan;
    public Bitmap imgBitmap;
    public String ipssDigit;
}
