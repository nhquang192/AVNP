package Models.ServerClass;

import java.util.Date;

import CTLs.Convert;

/**
 * Created by QUANG-PC on 10/07/2017.
 */

public class CarType {
    public CarType(String cardTypeName)
    {
        LineID=-1;
        CarTypeID=-1;
        CardTypeName=cardTypeName;
    }
    public CarType()
    {
    }
    private int LineID;
    private int CarTypeID;
    private String CardTypeName;
    private int Amount;
    private String SignID;
    private String BUID;
    private boolean Stop;
    private Date CreateDate;
    private String CreateByUser;
    private Date UpdtDate;
    private String UpdtByUser;

    public int getLineID() {
        return LineID;
    }

    public void setLineID(Object lineID) {
        LineID = (int)lineID;
    }

    public int getCarTypeID() {
        return CarTypeID;
    }

    public void setCarTypeID(Object carTypeID) {
        CarTypeID = (int)carTypeID;
    }

    public String getCardTypeName() {
        return CardTypeName;
    }

    public void setCardTypeName(Object cardTypeName) {
        CardTypeName = (String)cardTypeName;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(Object amount) {
        Amount = (int)amount;
    }

    public String getSignID() {
        return SignID;
    }

    public void setSignID(Object signID) {
        SignID = (String)signID;
    }

    public String getBUID() {
        return BUID;
    }

    public void setBUID(Object BUID) {
        try {
            this.BUID = (String) BUID;
        }catch (Exception ex)
        {
            this.BUID="CT1";
        }
    }

    public boolean isStop() {
        return Stop;
    }

    public void setStop(Object stop) {
        Stop = (Boolean) stop;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Object createDate) {
        try {
            CreateDate = Convert.serverDateFormat.parse((String) createDate);
        }catch (Exception ex1)
        {
            try{
                CreateDate = Convert.serverDateFormat2.parse((String) createDate);
            }
            catch (Exception ex2)
            {
                CreateDate=null;
            }
        }
    }
    public void setCreateDate(Date createDate) {
            CreateDate =createDate;
    }

    public String getCreateByUser() {
        return CreateByUser;
    }

    public void setCreateByUser(Object createByUser) {
        CreateByUser = (String)createByUser;
    }

    public Date getUpdtDate() {
        return UpdtDate;
    }

    public void setUpdtDate(Object updtDate) {
        try {
            UpdtDate = Convert.serverDateFormat.parse((String) updtDate);
        }catch (Exception ex1)
        {
            try{
                UpdtDate = Convert.serverDateFormat2.parse((String) updtDate);
            }
            catch (Exception ex2)
            {
                UpdtDate=null;
            }
        }
    }
    public void setUpdtDate(Date updtDate) {
            UpdtDate =  updtDate;
    }

    public String getUpdtByUser() {
        return UpdtByUser;
    }

    public void setUpdtByUser(Object updtByUser) {
        UpdtByUser = (String)updtByUser;
    }
}
