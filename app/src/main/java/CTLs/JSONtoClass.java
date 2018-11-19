package CTLs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import Models.CustomClass.Token;
import Models.ServerClass.Car;
import Models.ServerClass.CarType;
import Models.ServerClass.TicketMonth;

/**
 * Created by TaoLyLuong on 10/15/2017.
 */

public class JSONtoClass {
    public static Car toMCar(JSONObject jsonObject )
    {
        Car car =new Car();
        try {
            JSONObject jsonCar;
            try {
                jsonCar = jsonObject.getJSONObject("car");
            }catch (Exception exx)
            {
                jsonCar = jsonObject.getJSONObject("car");
            }
            car.setLineID(jsonCar.getInt("lineID"));
            car.setSmartCardCode(jsonCar.getString("smartCardCode"));
            try {
                car.setTimeStart(Convert.serverDateFormat2.parse(jsonCar.getString("timeStart")));
            } catch (ParseException e) {
                try {
                    car.setTimeStart(Convert.serverDateFormat.parse(jsonCar.getString("timeStart")));
                } catch (ParseException ex) {
                    car.setTimeStart(null);
                }
            }
            try {
                car.setTimeEnd(Convert.serverDateFormat2.parse(jsonCar.getString("timeEnd")));
            } catch (ParseException e) {
                try {
                    car.setTimeEnd(Convert.serverDateFormat.parse(jsonCar.getString("timeEnd")));
                } catch (ParseException ex) {
                    car.setTimeEnd(null);
                }
            }
            try {
                car.setCreateDate(Convert.serverDateFormat2.parse(jsonCar.getString("createDate")));
            } catch (ParseException e) {
                try {
                    car.setCreateDate(Convert.serverDateFormat.parse(jsonCar.getString("createDate")));
                } catch (ParseException ex) {
                    car.setCreateDate(null);}}

            car.setCreateByUser(jsonCar.getString("createByUser"));

            try {
                car.setUpdtDate(Convert.serverDateFormat2.parse(jsonCar.getString("updtDate")));
            } catch (ParseException e) {
                try {
                    car.setUpdtDate(Convert.serverDateFormat.parse(jsonCar.getString("updtDate")));
                } catch (ParseException ex) {
                    car.setUpdtDate(null);}}

            try {
                car.setCost(jsonCar.getInt("cost"));
            }catch (Exception e)
            {
                car.setCost(0);
            }
            try {
                car.setCostBefore(jsonCar.getInt("costBefore"));
            }catch (Exception e)
            {
                car.setCostBefore(0);
            }
            try {
                car.setLostCardMoney(jsonCar.getInt("lostCardMoney"));
            }catch (Exception e)
            {
                car.setLostCardMoney(0);
            }
            try {
                car.setCarTypeID(jsonCar.getInt("carTypeID"));
            }catch (Exception e)
            {
                car.setCarTypeID(-1);
            }
            car.setDigit(jsonCar.getString("digit"));
            car.setUserLoginIn(jsonCar.getString("userLoginIn"));
            car.setUserLogOut(jsonCar.getString("userLogOut"));
            car.setSignID(jsonCar.getString("signID"));
            car.setIsTicketMonth(jsonCar.getBoolean("isTicketMonth"));
            car.setCarTypeName(jsonCar.getString("carTypeName"));
            car.setImages1(jsonCar.getString("images1"));
            car.setImages2(jsonCar.getString("images2"));
            car.setImages3(jsonCar.getString("images3"));
            car.setImages4(jsonCar.getString("images4"));
            car.setLostCardStatus(jsonCar.getBoolean("lostCardStatus"));
            car.setComputerID(jsonCar.getString("computerID"));
            car.setNote(jsonCar.getString("note"));
            car.setBUID(jsonCar.getString("buid"));
            car.setStop(jsonCar.getBoolean("stop"));
            car.setUpdtByUser(jsonCar.getString("updtByUser"));

            return car;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return car;
    }
    public static TicketMonth toTicketMonth(JSONObject jsonObject )
    {
        TicketMonth a =new TicketMonth();
        try {

            a.TicketMonthID=jsonObject.getInt("ticketMonthID");
            a.SmartCardID=jsonObject.getInt("smartCardID");
            a.SmartCardCode=jsonObject.getString("smartCardCode");
            a.Digit=jsonObject.getString("digit");


            return a;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    private List<Car> toListMCar(JSONObject jsonObject )
    {
        return null;
    }

    public static ArrayList<CarType> toListCarType(JSONArray jsonData)
    {
        ArrayList<CarType> lstCarType=new ArrayList<CarType>();
        try
        {
            if(jsonData!=null)
            {
                for(int i=0; i<jsonData.length();i++)
                {
                    CarType carType=new CarType();
                    carType.setLineID(jsonData.getJSONObject(i).get("lineID"));
                    carType.setCarTypeID(jsonData.getJSONObject(i).get("carTypeID"));
                    carType.setCardTypeName(jsonData.getJSONObject(i).get("carTypeName"));
                    carType.setAmount(jsonData.getJSONObject(i).get("amount"));
                    carType.setSignID(jsonData.getJSONObject(i).get("signID"));
                    carType.setBUID(jsonData.getJSONObject(i).get("buid"));
                    carType.setStop(jsonData.getJSONObject(i).get("stop"));
                    lstCarType.add(carType);
                }
            }
        }
        catch (Exception exfn){}
        return lstCarType;
    }
    public static ArrayList<Car> toListCar(JSONArray jsonData)
    {
        ArrayList<Car> lstCar=new ArrayList<Car>();
        try
        {
            if(jsonData!=null)
            {
                for(int i=0; i<jsonData.length();i++)
                {
                    Car car=new Car();
                    //car.setLineID(jsonData.getJSONObject(i).getInt("lineID"));
                    car.setSmartCardCode(jsonData.getJSONObject(i).get("smartCardCode"));
                    car.setTimeStart(jsonData.getJSONObject(i).get("timeStart"));
                    car.setTimeEnd(jsonData.getJSONObject(i).get("timeEnd"));
                    car.setDigit(jsonData.getJSONObject(i).get("digit"));
                    //car.setUserLoginIn(jsonData.getJSONObject(i).get("userLoginIn"));
                    //car.setUserLogOut(jsonData.getJSONObject(i).get("userLogOut"));
                    car.setCost(jsonData.getJSONObject(i).get("cost"));
                    //car.setSignID(jsonData.getJSONObject(i).get("signID"));
                    //car.setIsTicketMonth(jsonData.getJSONObject(i).get("isTicketMonth"));
                    //car.setCarTypeID(jsonData.getJSONObject(i).get("carTypeID"));
                    //car.setCarTypeName(jsonData.getJSONObject(i).get("carTypeName"));
                    car.setImages1(jsonData.getJSONObject(i).get("images1"));
                    //car.setImages2(jsonData.getJSONObject(i).get("images2"));
                    //car.setImages3(jsonData.getJSONObject(i).get("images3"));
                    //car.setImages4(jsonData.getJSONObject(i).get("images4"));
                    //car.setimagesUrl1(jsonData.getJSONObject(i).get("imagesUrl1"));
                    //car.setimagesUrl2(jsonData.getJSONObject(i).get("imagesUrl2"));
                    //car.setimagesUrl3(jsonData.getJSONObject(i).get("imagesUrl3"));
                    //car.setimagesUrl4(jsonData.getJSONObject(i).get("imagesUrl4"));
                    //car.setLostCardStatus(jsonData.getJSONObject(i).get("lostCardStatus"));
                    //car.setLostCardMoney(jsonData.getJSONObject(i).get("lostCardMoney"));
                    //car.setComputerID(jsonData.getJSONObject(i).get("computerID"));
                    //car.setNote(jsonData.getJSONObject(i).get("note"));
                    //car.setCostBefore(jsonData.getJSONObject(i).get("costBefore"));
                    //car.setBUID(jsonData.getJSONObject(i).get("buid"));
                    //car.setStop(jsonData.getJSONObject(i).get("stop"));
                    //car.setCreateDate(jsonData.getJSONObject(i).get("createDate"));
                    //car.setCreateByUser(jsonData.getJSONObject(i).get("createByUser"));
                    //car.setUpdtDate(jsonData.getJSONObject(i).get("updtDate"));
                    //car.setUpdtByUser(jsonData.getJSONObject(i).get("updtByUser"));
                    car.setSmartCardID(jsonData.getJSONObject(i).get("smartCardID"));
                    lstCar.add(car);
                }
            }
        }
        catch (Exception exfn){}
        return lstCar;
    }

    public static Token JSONtoToken(JSONObject jsonObject)
    {
        Token mytoken=new Token();
        try {
            mytoken.access_token=jsonObject.getString("access_token");
            mytoken.token_type=jsonObject.getString("token_type");
            mytoken.expires_in=jsonObject.getInt("expires_in");
            mytoken.username=jsonObject.getString("username");
            mytoken.password=jsonObject.getString("password");
        }catch (Exception ex)
        {}
        return mytoken;
    }
}
