package Online.Volley;

import android.content.Context;
import android.graphics.Bitmap;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import Models.Base.ClientConfig;

/**
 * Created by HOME on 11/07/2017.
 */

public class VolleyRequestMethod {
    public String BASE_URL= ClientConfig.BASE_API_URL;
    public String API_URL="";

    Context _context;
    public boolean isComplete=false;
    public Boolean isOauth=false;

    protected String _requestBody;
    protected Bitmap _requestBitmap;
    protected String _requestBitmapName;


    public String[] result = {""};
    public JSONObject JSresult;

    public VolleyRequestMethod(Context context)
    {
        _context=context;
    }

    public void GET()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result[0] =response;
                isComplete=true;
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

    public void POST()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result[0] =response;
                isComplete=true;
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
    public void POSTimg()
    {
        VolleyMultipartRequest multipartRequest = new VolleyMultipartRequest(Request.Method.POST, API_URL, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                String resultResponse = new String(response.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NetworkResponse networkResponse = error.networkResponse;
                String errorMessage = "Unknown error";
                if (networkResponse == null) {

                } else {
                    String result = new String(networkResponse.data);
                    try {
                        JSONObject response = new JSONObject(result);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userName", ClientConfig.CURRENT_USER);
                params.put("Authorization","Bearer " +ClientConfig.ACCESS_TOKEN);
                return params;
            }
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("image", new DataPart(_requestBitmapName, AppHelper.getFileDataFromDrawable(_context,_requestBitmap), "image/jpeg"));
                return params;
            }
        };

        start(multipartRequest);

    }

    public  void PUT()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.PUT, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result[0] =response;
                isComplete=true;
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

    public void DELETE()
    {
        StringRequest stringRequest=new StringRequest(Request.Method.DELETE, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                result[0] =response;
                isComplete=true;
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
    public String runInputStreamRequest(String url) {
        RequestFuture<String> future = RequestFuture.newFuture();
        StringRequest request = new StringRequest(Request.Method.GET,url, future, future);
        start(request);
        try {
            return future.get(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            //Log.e("Retrieve cards api call interrupted.", e);
            //errorListener.onErrorResponse(new VolleyError(e));
        } catch (ExecutionException e) {
            //Log.e("Retrieve cards api call failed.", e);
            //errorListener.onErrorResponse(new VolleyError(e));
        } catch (TimeoutException e) {
            //Log.e("Retrieve cards api call timed out.", e);
            //errorListener.onErrorResponse(new VolleyError(e));
            e.getMessage();
        }
        return null;
    }
    public void start(StringRequest stringRequest)
    {
        VolleySingleton.getInstance(_context).getRequestQueue().add(stringRequest);
    }
    public void start(JsonObjectRequest jsonObjectRequest)
    {
        VolleySingleton.getInstance(_context).getRequestQueue().add(jsonObjectRequest);
    }
    public void start(VolleyMultipartRequest volleyMultipartRequest)
    {
        VolleySingleton.getInstance(_context).getRequestQueue().add(volleyMultipartRequest);
    }
}
