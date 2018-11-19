package Online.API;

import android.content.Context;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import Online.Volley.VolleyRequestMethod;

/**
 * Created by QUANG-PC on 11/30/2017.
 */

public class APILogin extends VolleyRequestMethod {

    public APILogin(Context context) {
        super(context);
    }
    String _username;
    String _password;

    public void GetToken(String username,String password)
    {
        //http://mobileparking.ddns.net/api/oauth/token
        _username=username;
        _password=password;
        String api_name = "api/oauth/token";
        this.API_URL = BASE_URL + api_name;
        this._requestBody = "";
        POST();
    }
    @Override
    public void POST()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result[0] =response;
                isComplete=true;
                isOauth=true;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    String s=new String(error.networkResponse.data,"UTF-8");
                    result[0] =s;
                    isOauth=false;
                } catch (Exception e) {
                    result[0] ="";
                    isOauth=false;
                    e.printStackTrace();
                }
                isComplete=true;
            }
        }) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", _username);
                params.put("password", _password);
                params.put("grant_type", "password");
                return params;
            }
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
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
