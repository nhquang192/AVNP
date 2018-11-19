package Online.API;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import CTLs.Convert;
import Models.ServerClass.Car;
import Online.Volley.VolleyRequestMethod;

/**
 * Created by HOME on 11/07/2017.
 */

public class APICar extends VolleyRequestMethod {

    public int Status=-1;
    public String StatusName;
    public APICar(Context context) {
        super(context);
    }
    Car crCar=null;
    //POST api/car/ScanCarIn
    public void ScanCarIn(Car carin)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("SmartCardCode", carin.getSmartCardCode());
            jsonBody.put("Digit", carin.getDigit());
            jsonBody.put("TimeScan", Convert.clientDateFormat.format(carin.getTimeStart()));
            jsonBody.put("Images1", carin.getImages1());
            jsonBody.put("Images2", carin.getImages2());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        crCar=carin;
        String api_name="api/car/ScanCarIn";
        this.API_URL=BASE_URL+api_name;
        this._requestBody=jsonBody.toString();
        POST();
    }
    //POST api/car/ScanCarOut
    public void ScanCarOut(Car carout)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("SmartCardCode", carout.getSmartCardCode());
            jsonBody.put("Digit", carout.getDigit());
            jsonBody.put("TimeScan",Convert.clientDateFormat.format(carout.getTimeEnd()));
            jsonBody.put("Images3", carout.getImages3());
            jsonBody.put("Images4", carout.getImages4());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        crCar=carout;
        String api_name="api/car/ScanCarOut";
        this.API_URL=BASE_URL+api_name;
        this._requestBody=jsonBody.toString();
        POST();
    }
    //PUT api/car/Update
    public void Update(Car car)
    {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("LineID",car.getLineID());
            jsonBody.put("SmartCardCode", car.getSmartCardCode());
            try {
                jsonBody.put("TimeStart",Convert.clientDateFormat.format(car.getTimeStart()));
            }catch (Exception ex)
            {
                jsonBody.put("TimeStart", null);
            }
            try {
                jsonBody.put("TimeEnd", Convert.clientDateFormat.format(car.getTimeEnd()));
            }catch (Exception ex)
            {
                jsonBody.put("TimeEnd", null);
            }
            jsonBody.put("Digit", car.getDigit());
            jsonBody.put("UserLoginIn",car.getUserLoginIn());
            jsonBody.put("UserLogOut",car.getUserLogOut());
            jsonBody.put("Cost",car.getCost().toString());
            jsonBody.put("SignID","");
            jsonBody.put("IsTicketMonth",car.getIsTicketMonth());
            jsonBody.put("CarTypeID",car.getCarTypeID());
            jsonBody.put("CarTypeName",car.getCarTypeName());
            jsonBody.put("Images1", car.getImages1());
            jsonBody.put("Images2", car.getImages2());
            jsonBody.put("Images3", car.getImages3());
            jsonBody.put("Images4", car.getImages4());
            jsonBody.put("LostCardStatus",car.getLostCardStatus());
            jsonBody.put("LostCardMoney",car.getLostCardMoney());
            jsonBody.put("ComputerID",car.getComputerID());
            jsonBody.put("Note",car.getNote().toString());
            jsonBody.put("CostBefore",car.getCostBefore());
            jsonBody.put("BUID",car.getBUID());
            jsonBody.put("Stop",car.getStop());
            try {
                jsonBody.put("CreateDate",Convert.clientDateFormat.format(car.getCreateDate()));
            }catch (Exception ex)
            {
                jsonBody.put("CreateDate", null);
            }
            jsonBody.put("CreateByUser",car.getCreateByUser());
            try {
                jsonBody.put("UpdtDate",Convert.clientDateFormat.format(car.getUpdtDate()));
            }catch (Exception ex)
            {
                jsonBody.put("UpdtDate", null);
            }
            jsonBody.put("UpdtByUser",car.getUpdtByUser());

        } catch (Exception e) {
            e.printStackTrace();
        }
        String api_name="api/car/Update";
        this.API_URL=BASE_URL+api_name;
        this._requestBody=jsonBody.toString();
        PUT();
    }
    //PUT api/car/SaveLostCard?smartCardCode={smartCardCode}
    public void SaveLostCard(String smarcardCode)
    {
        String api_name="api/car/SaveLostCard?smartCardCode="+smarcardCode;
        this.API_URL=BASE_URL+api_name;
        this._requestBody=null;
        PUT();
    }
}
