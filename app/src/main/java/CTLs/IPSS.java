package CTLs;

import android.content.Context;
import android.graphics.Bitmap;

import com.nhquang.parking3.IPSSLib;

import org.opencv.android.Utils;
import org.opencv.core.Mat;


/**
 * Created by QUANG-PC on 10/31/2017.
 */

public class IPSS {
    public static String getDigit(Bitmap bmp, Context context)
    {
        IPSSLib.getInstance().init(context);
        Mat mat = new Mat();
        Mat matResult = new Mat();
        Utils.bitmapToMat(bmp, mat);
        String text = IPSSLib.getInstance().ReadPlate(mat.getNativeObjAddr(), matResult.getNativeObjAddr());
        if(text.length()<=3)
            return "0000";
        return  makeBeautiDigit(text);

    }
    private static String makeBeautiDigit(String digit)
    {
        String temp=digit.trim();
        temp=temp.replace("_","");
        temp=temp.replace(" ","-");
        if(temp.length()<=3)
            return "0000";
        return temp;
    }
    public static String cutDigitfromImageString(String imgString)
    {
        String temp="0000";
        try{
            temp=(imgString.split("_"))[2];
        }
        catch (Exception ex)
        {}
        return temp;

    }
}
