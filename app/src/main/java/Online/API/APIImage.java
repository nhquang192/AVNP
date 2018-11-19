package Online.API;

import android.content.Context;
import android.graphics.Bitmap;

import Online.Volley.VolleyRequestMethod;

/**
 * Created by HOME on 11/07/2017.
 */

public class APIImage extends VolleyRequestMethod {
    public APIImage(Context context) {
        super(context);
    }
    //POST api/uploadFile/UploadImage?typeUploadImageStatus={typeUploadImageStatus}
    public void UploadImage (String filename,Bitmap bitmap)
    {
        String api_name="api/uploadFile/UploadImage?typeUploadImageStatus=1";
        this.API_URL=BASE_URL+api_name;
        this._requestBitmap=bitmap;
        this._requestBitmapName=filename;
        POSTimg();
    }
}