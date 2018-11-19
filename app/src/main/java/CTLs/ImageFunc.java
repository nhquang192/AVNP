package CTLs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;

import Offline.MyFiles.FileManager;

/**
 * Created by TaoLyLuong on 10/15/2017.
 */

public class ImageFunc {
    public static Bitmap ResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, true);
        //Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm,newWidth,newHeight,false);
        return resizedBitmap;
    }
    public static Bitmap SaveImage(Bitmap bmp,File sdRoot, String dir,String fileName)
    {
        Bitmap bmpS=bmp;
        try {
            File mkDir = new File(sdRoot, dir);
            mkDir.mkdirs();
            File pictureFile = new File(sdRoot, dir + fileName);
            FileOutputStream purge = new FileOutputStream(pictureFile);
            bmpS.compress(Bitmap.CompressFormat.WEBP, 70, purge);
            purge.flush();
            purge.close();
            return bmpS;
        }catch (Exception ex) {
            return null;
        }
    }
    public static Bitmap GetImgIn(String sTimeStart,String imgName)
    {
        String CarInDay="";
        try
        {
            CarInDay="/"+sTimeStart.split(" ")[0]+"/";
        }
        catch (Exception ex)
        {
            CarInDay= FileManager.PRESENT_DAY;
        }
        File sdRoot = Environment.getExternalStorageDirectory();
        sdRoot = new File(FileManager.SD_PATH);// ****//
        String dir = FileManager.IMG_IN_FOLDER_PATH+CarInDay;
        File mkDir = new File(sdRoot, dir);
        mkDir.mkdirs();
        File pictureFile = new File(sdRoot, dir + imgName);
        try {

            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(pictureFile.getAbsolutePath(),bmOptions);
            return bitmap;

        } catch (Exception e) {
            return null;
        }
    }
    public static Bitmap FixBitmapfromCamera(byte[] dataCamera)
    {
        try
        {
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inSampleSize = 2;
            Bitmap bmp = BitmapFactory.decodeByteArray(dataCamera, 0, dataCamera.length, o);
            bmp = ImageFunc.ResizedBitmap(bmp, 500, 500);
            Matrix matrix = new Matrix();
            matrix.postRotate(90);
            Bitmap bmpS = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmp.recycle();
            return bmpS;
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
