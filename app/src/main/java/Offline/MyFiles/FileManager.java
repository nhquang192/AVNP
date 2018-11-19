package Offline.MyFiles;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by QUANG-PC on 11/13/2017.
 */

public class FileManager {

    public static String SD_PATH="/storage/emulated/0/";
    public static String IMG_FOLDER_PATH="/VNP/Camera/";
    public static String IMG_IN_FOLDER_PATH="/VNP/Camera/IN/";
    public static String IMG_OUT_FOLDER_PATH="/VNP/Camera/OUT/";
    public static String IMG_FOLDER_PATH2="/SD_VNP/Camera/";
    public static String IMG_IN_FOLDER_PATH2="/SD_VNP/Camera/IN/";
    public static String IMG_OUT_FOLDER_PATH2="/SD_VNP/Camera/OUT/";
    public static String PRESENT_DAY="yyyy-MM-dd";
    public static String SD_VNP="";
    public static String DAY_PATH1="/storage/";
    public static String DAY_PATH2="/storage/";
    public static String DAY_PATH3="/storage/";
    public static String SD_ROOT="/storage/";
    public static  String sExeption="";
    final static String TAG = FileManager.class.getName();

    //public FileManager() {

		/*SD_PATH = System.getenv("SECONDARY_STORAGE");
        if ((SD_PATH == null) || (SD_PATH.length() == 0)) {
             SD_PATH = System.getenv("EXTERNAL_SDCARD_STORAGE");
        }*/
		/*SD_PATH=Environment.getExternalStorageDirectory().getAbsolutePath().toString();

        if (SD_PATH!="")
        {
        	IMG_FOLDER_PATH=SD_PATH+"/IMAGE";
        	IMG_VEHICLE_IN_FOLDER_PATH=IMG_FOLDER_PATH+"/VEHICLE_IN";
        	IMG_VEHICLE_OUT_FOLDER_PATH=IMG_FOLDER_PATH+"/VEHICLE_OUT";
        }*/

    //}
    public static void getSD_VNP()
    {
        String path = SD_ROOT;
        File directory = new File(path);
        File[] files = directory.listFiles();

        for (int k = 0; k < files.length; k++)
        {
            try {
                File directory2 = new File(files[k].getPath());
                File[] files2 = directory2.listFiles();

                for (int i = 0; i < files2.length; i++) {
                    if (files2[i].getName().equals("SD_VNP") == true) {
                        SD_VNP = files[k].getPath()+"/";
                        break;
                    }
                }
            }catch (Exception ex)
            {}
        }
    }
    public static Boolean createFolder(String fileName) {
        try {

            File f = new File(fileName);
            boolean success = true;
            if (!f.exists()) {
                success = f.mkdirs();
            }
            if (success) {
                Log.e("Folder: ", f.getAbsolutePath() + " folder created!!");
            } else {
                Log.e("Folder Error: ", f.getAbsolutePath()
                        + " folder cannot be created!!");
            }
            return success;
        }catch (Exception ex)
        {
            sExeption=ex.getMessage();
            return false;
        }
    }
    public static  String ReadFile( Context context){
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream (new File(SD_PATH+IMG_FOLDER_PATH+"config.txt"));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ( (line = bufferedReader.readLine()) != null )
            {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }
        catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile( String data,String filename){
        try {
            File file = new File(SD_PATH+IMG_FOLDER_PATH+filename);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file,true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());

            return true;
        }  catch(FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        }  catch(IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return  false;
    }

    public static void deleteDir(Date date)
    {
        try {
            File dir = new File(SD_PATH+IMG_IN_FOLDER_PATH);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    File chil = new File(dir, children[i]);
                    Calendar c = Calendar.getInstance();
                    c.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(children[i]));
                    if(date.after(c.getTime())) {
                        deleteDir(SD_PATH+IMG_IN_FOLDER_PATH+"/"+children[i]);
                    }
                }
            }
        }catch (Exception ex)
        {}
    }
    public static void deleteDir(String filedir)
    {
        try {
            File dir = new File(filedir);
            if (dir.isDirectory()) {
                String[] children = dir.list();
                for (int i = 0; i < children.length; i++) {
                    File chil = new File(dir, children[i]);
                    if (chil.isFile() == true)
                        chil.delete();
                }
                dir.delete();
            }
        }catch (Exception ex)
        {}
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void copy(File src, File dst) throws IOException {
        try (InputStream in = new FileInputStream(src)) {
            try (OutputStream out = new FileOutputStream(dst)) {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            }
        }
    }


}

