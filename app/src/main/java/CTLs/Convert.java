package CTLs;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by QUANG-PC on 10/13/2017.
 */

public class Convert {
    public static SimpleDateFormat serverDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ");
    public static SimpleDateFormat serverDateFormat2=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    public static SimpleDateFormat clientDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat clientDateFormat2=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    public static SimpleDateFormat clientDateFormat4=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
    public static SimpleDateFormat clientDateFormat3=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");
    public static SimpleDateFormat imageDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat imageFolderDateFormat=new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat imageFolderDateFormat2=new SimpleDateFormat("yyyy-MM-d");

    final protected static char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        int v;
        for ( int j = 0; j < bytes.length; j++ ) {
            v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
    public static String ConvertDatetimeToShow(Date date)
    {
        String result="";
        try
        {
            result=clientDateFormat.format(date);
        }catch (Exception ex1)
        {
            try{ result=serverDateFormat2.format(date);}
            catch (Exception ex2)
            {
                try {
                    result=serverDateFormat.format(date);
                }catch (Exception ex3){}
            }
        }
        return result;
    }
}
