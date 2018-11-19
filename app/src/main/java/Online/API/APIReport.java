package Online.API;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import CTLs.Convert;
import Models.Base.ClientConfig;
import Models.ServerClass.Car;
import Online.Volley.VolleyRequestMethod;

/**
 * Created by HOME on 11/07/2017.
 */

public class APIReport extends VolleyRequestMethod {
    public int Status = -1;
    public String StatusName;
    public ArrayList<Car> lstCar=new ArrayList<Car>();

    public APIReport(Context context) {
        super(context);
    }

    //POST api/report/RevenuesForUser
    public void RevenuesForUser(Date TimeStart, Date TimeEnd, String ListCarTypeID, Boolean IsTicketMonth, String UserName) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("TimeStart", Convert.clientDateFormat.format(TimeStart));
            jsonBody.put("TimeEnd", Convert.clientDateFormat.format(TimeEnd));
            jsonBody.put("ListCarTypeID", ListCarTypeID);
            jsonBody.put("IsTicketMonth", IsTicketMonth);
            jsonBody.put("UserName", UserName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String api_name = "api/report/RevenuesForUser";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = jsonBody.toString();
        POST();
    }

    //POST api/report/RevenuesForCarType
    public void RevenuesForCarType(Date TimeStart, Date TimeEnd, String ListCarTypeID, Boolean IsTicketMonth) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("TimeStart", Convert.clientDateFormat.format(TimeStart));
            jsonBody.put("TimeEnd", Convert.clientDateFormat.format(TimeEnd));
            jsonBody.put("ListCarTypeID", ListCarTypeID);
            jsonBody.put("IsTicketMonth", IsTicketMonth);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String api_name = "api/report/RevenuesForCarType";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = jsonBody.toString();
        POST();
    }

    //POST api/report/SearchCarInOut
    public void SearchCarInOut(int querryReportStatus , Date TimeStart, Date TimeEnd, String ListCarTypeID, Boolean IsTicketMonth) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("TypeQuery", querryReportStatus);
            jsonBody.put("TimeStart", Convert.clientDateFormat2.format(TimeStart));
            jsonBody.put("TimeEnd", Convert.clientDateFormat2.format(TimeEnd));
            jsonBody.put("ListCarTypeID", ListCarTypeID);
            jsonBody.put("IsTicketMonth", IsTicketMonth);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String api_name = "api/report/SearchCarInOut";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = jsonBody.toString();
        POST();
    }

    //POST api/report/RevenuesDetailForCar
    public void RevenuesDetailForCar(Date TimeStart, Date TimeEnd, String ListCarTypeID, Boolean IsTicketMonth, String UserName) {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("TimeStart", Convert.clientDateFormat2.format(TimeStart));
            jsonBody.put("TimeEnd", Convert.clientDateFormat2.format(TimeEnd));
            jsonBody.put("ListCarTypeID", ListCarTypeID);
            jsonBody.put("IsTicketMonth", IsTicketMonth);
            jsonBody.put("UserName", UserName);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String api_name = "api/report/RevenuesDetailForCar";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = jsonBody.toString();
        POST();
    }
    @Override
    public void POST()
    {
            StringRequest stringRequest=new StringRequest(Request.Method.POST, API_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(final String response) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                //result[0] = response;
                                JSONObject jsonObj = new JSONObject(response);
                                lstCar = CTLs.JSONtoClass.toListCar(jsonObj.getJSONArray("listCar"));
                                isComplete = true;
                            }catch (Exception ex)
                            {}
                        }
                    }).start();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try {
                        String s=new String(error.networkResponse.data,"UTF-8");
                        result[0] =s;
                    } catch (Exception e) {
                        result[0] ="";
                        e.printStackTrace();
                    }
                    isComplete=true;
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return _requestBody == null ? null : _requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", _requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("userName", ClientConfig.CURRENT_USER);
                    params.put("Authorization","Bearer " +ClientConfig.ACCESS_TOKEN);
                    return params;
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.data);
                        // can get more details such as response.headers
                    }
                    return super.parseNetworkResponse(response);
                }
            };
            start(stringRequest);
    }
}
