package Online.API;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import CTLs.Convert;
import Models.ServerClass.CarType;
import Online.Volley.VolleyRequestMethod;

/**
 * Created by HOME on 11/07/2017.
 */

public class APICarType extends VolleyRequestMethod {

    public int Status = -1;
    public String StatusName;

    public APICarType(Context context) {
        super(context);
    }

    //GET api/carType/GetAll
    public void GetAll() {
        String api_name = "api/carType/GetAll";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = "";
        GET();
    }

    //GET api/carType/GetSingleById?lineID={lineID}
    public void GetSingleById(int LineID) {
        String api_name = "api/carType/GetSingleById?lineID=" + LineID;
        this.API_URL = BASE_URL + api_name;
        this._requestBody = "";
        GET();
    }

    //GET api/carType/GetSingleByCarTypeID?carTypeID={carTypeID}
    public void GetSingleByCarTypeID(int carTypeID) {
        String api_name = "api/carType/GetSingleByCarTypeID?carTypeID=" + carTypeID;
        this.API_URL = BASE_URL + api_name;
        this._requestBody = "";
        GET();
    }

    //POST api/carType/Add
    public void Add(CarType carType) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("LineID", carType.getLineID());
            jsonBody.put("CarTypeID", carType.getCarTypeID());
            jsonBody.put("CarTypeName", carType.getCardTypeName());
            jsonBody.put("Amount", carType.getAmount());
            jsonBody.put("SignID", carType.getSignID());
            jsonBody.put("BUID", carType.getBUID());
            jsonBody.put("Stop", carType.isStop());
            jsonBody.put("CreateDate", Convert.clientDateFormat.format(carType.getCreateDate()));
            jsonBody.put("CreateByUser", carType.getCreateByUser());
            jsonBody.put("UpdtDate", Convert.clientDateFormat.format(carType.getUpdtDate()));
            jsonBody.put("UpdtByUser", carType.getUpdtByUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String api_name = "api/carType/Add";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = jsonBody.toString();
        POST();
    }

    //PUT api/carType/Update
    public void Update(CarType carType) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("LineID", carType.getLineID());
            jsonBody.put("CarTypeID", carType.getCarTypeID());
            jsonBody.put("CarTypeName", carType.getCardTypeName());
            jsonBody.put("Amount", carType.getAmount());
            jsonBody.put("SignID", carType.getSignID());
            jsonBody.put("BUID", carType.getBUID());
            jsonBody.put("Stop", carType.isStop());
            jsonBody.put("CreateDate", Convert.clientDateFormat.format(carType.getCreateDate()));
            jsonBody.put("CreateByUser", carType.getCreateByUser());
            jsonBody.put("UpdtDate", Convert.clientDateFormat.format(carType.getUpdtDate()));
            jsonBody.put("UpdtByUser", carType.getUpdtByUser());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String api_name = "api/carType/Update";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = jsonBody.toString();
        PUT();
    }

    //DELETE api/carType/Delete?lineID={lineID}
    public void Delete(int lineID) {
        String api_name = "api/carType/Delete?lineID=" + lineID;
        this.API_URL = BASE_URL + api_name;
        DELETE();
    }
}
