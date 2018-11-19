package com.nhquang.parking3;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HOME on 11/07/2017.
 */

public class IPSSLib {
    static String mCascadeQuadFile, mORCModelFile;

    static {
        System.loadLibrary("native_lib");
    }

    private static volatile IPSSLib instance = new IPSSLib();

    private IPSSLib(){
    };

    public static void init(Context c){
        InputStream is = c.getResources().openRawResource(R.raw.cascade_square);
        File cascadeDir = c.getDir("cascade", Context.MODE_PRIVATE);
        File tmp = new File(cascadeDir, "cascade_quad.xml");

        byte[] buffer = new byte[4096];
        int bytesRead;
        try {
            FileOutputStream os = new FileOutputStream(tmp);
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCascadeQuadFile = tmp.getAbsolutePath();
        buffer = null;


        InputStream is3 = c.getResources().openRawResource(R.raw.char_18x42);
        File tmp3 = new File(cascadeDir, "char_18x42.knn");

        byte[] buffer3 = new byte[4096];
        int bytesRead3;
        try {
            FileOutputStream os3 = new FileOutputStream(tmp3);
            while ((bytesRead3 = is3.read(buffer3)) != -1) {
                os3.write(buffer3, 0, bytesRead3);
            }
            is3.close();
            os3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mORCModelFile = tmp3.getAbsolutePath();
        buffer3 = null;

        IPSSLib.getInstance().Init(mCascadeQuadFile, mORCModelFile);
    }

    public static IPSSLib getInstance() {
        return instance;
    }

    public native String ReadPlate(long matImg, long matResult);
    public native void Init(String casadeQuadFile, String ORCfile);
}
