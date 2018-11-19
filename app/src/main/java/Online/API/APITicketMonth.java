package Online.API;

import android.content.Context;

import Models.ServerClass.TicketMonth;
import Online.Volley.VolleyRequestMethod;

/**
 * Created by HOME on 11/07/2017.
 */

public class APITicketMonth extends VolleyRequestMethod {
    public APITicketMonth(Context context) {
        super(context);
    }
    public int Status=-1;
    public String StatusName;
    TicketMonth crTicketMonth=null;
    //GET api/ticketMonth/CheckStatusTickMonth?smartCardCode=? &statusID= ?
    public void CheckStatusTickMonth(String smartCardCode, int StatusID) {
        String api_name = "api/ticketMonth/CheckStatusTickMonth?smartCardCode="+smartCardCode+ "&statusID="+String.valueOf(StatusID);
        this.API_URL = BASE_URL + api_name;
        this._requestBody = "";
        GET();
    }
}
