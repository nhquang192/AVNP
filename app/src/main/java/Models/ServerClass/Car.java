package Models.ServerClass;


import java.util.Date;

import CTLs.Convert;

/**
 * Created by QUANG-PC on 10/07/2017.
 */

public class Car {
    private Integer lineID;
    private Integer smartCardID;
    private String smartCardCode;
    private Date timeStart;
    private Date timeEnd;
    private String digit;
    private String userLoginIn;
    private String userLogOut;
    private Integer cost;
    private String signID;
    private Boolean isTicketMonth;
    private Integer carTypeID;
    private String carTypeName;
    private String images1;
    private String images2;
    private String images3;
    private String images4;
    private String imagesUrl1;
    private String imagesUrl2;
    private String imagesUrl3;
    private String imagesUrl4;
    private Boolean lostCardStatus;
    private Integer lostCardMoney;
    private String computerID;
    private String note;
    private Integer costBefore;
    private String bUID;
    private Boolean stop;
    private Date createDate;
    private String createByUser;
    private Date updtDate;
    private String updtByUser;

    public Integer getLineID() {
        return lineID;
    }

    public void setLineID(Object lineID) {
        try {
            this.lineID = (int)lineID;
        }catch (Exception ex)
        {
            this.lineID = -1;
        }
    }
    public Integer getSmartCardID() {
        return smartCardID;
    }

    public void setSmartCardID(Object smartCardID) {
        try {
            this.smartCardID = (int)smartCardID;
        }catch (Exception ex)
        {
            this.smartCardID = -1;
        }
    }
    public String getSmartCardCode() {
        return smartCardCode;
    }

    public void setSmartCardCode(Object smartCardCode)
    {
        try {
            this.smartCardCode = (String)smartCardCode;
        }catch (Exception ex)
        {
            this.smartCardCode = "";
        }
    }

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Object timeStart) {
        try {
            this.timeStart = Convert.serverDateFormat.parse((String)timeStart);
        }catch (Exception ex1)
        {
            try {
                this.timeStart = Convert.serverDateFormat2.parse((String) timeStart);
            }catch (Exception ex2)
            {
                this.timeStart = null;
            }
        }
    }
    public void setTimeStart(Date timeStart) {
            this.timeStart = timeStart;
    }
    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Object timeEnd) {
        try {
            this.timeEnd = Convert.serverDateFormat.parse((String)timeEnd);
        }catch (Exception ex1)
        {
            try {
                this.timeEnd = Convert.serverDateFormat2.parse((String) timeEnd);
            }catch (Exception ex2)
            {
                this.timeEnd = null;
            }
        }
    }
    public void setTimeEnd(Date timeEnd) {
            this.timeEnd = timeEnd;

    }

    public String getDigit() {
        return digit;
    }

    public void setDigit(Object digit) {
        try {
            this.digit = (String) digit;
        }catch (Exception ex)
        {
            this.digit = "null";
        }
    }

    public String getUserLoginIn() {
        return userLoginIn;
    }

    public void setUserLoginIn(Object userLoginIn) {
        try {
            this.userLoginIn = (String) userLoginIn;
        }catch (Exception ex)
        {
            this.userLoginIn = null;
        }
    }

    public String getUserLogOut() {
        return userLogOut;
    }

    public void setUserLogOut(Object userLogOut) {
        try {
            this.userLogOut = (String) userLogOut;
        }catch (Exception ex)
        {
            this.userLogOut = null;
        }
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Object cost) {
        try {
            this.cost = Double.valueOf(cost.toString()).intValue();
        }catch (Exception ex)
        {
            this.cost = 0;
        }
    }

    public String getSignID() {
        return signID;
    }

    public void setSignID(Object signID) {
        try {
            this.signID = (String) signID;
        }catch (Exception ex)
        {
            this.signID = null;
        }
    }

    public Boolean getIsTicketMonth() {
        return isTicketMonth;
    }

    public void setIsTicketMonth(Object isTicketMonth) {

        try {
            this.isTicketMonth = (Boolean) isTicketMonth;
        }catch (Exception ex)
        {
            this.isTicketMonth = false;
        }
    }

    public Integer getCarTypeID() {
        return carTypeID;
    }

    public void setCarTypeID(Object carTypeID) {
        try {
            this.carTypeID = (Integer) carTypeID;
        }catch (Exception ex)
        {
            this.carTypeID = 0;
        }
    }

    public Object getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(Object carTypeName) {
        try {
            this.carTypeName = (String) carTypeName;
        }catch (Exception ex)
        {
            this.carTypeName = "";
        }
    }

    public String getImages1() {
        return images1;
    }

    public void setImages1(Object images1) {
        try {
            this.images1 = (String) images1;
        }catch (Exception ex)
        {
            this.images1 = "";
        }
    }

    public String getImages2() {
        return images2;
    }

    public void setImages2(Object images2) {
        try {
            this.images2 = (String) images2;
        }catch (Exception ex)
        {
            this.images2 = "";
        }
    }

    public String getImages3() {
        return images3;
    }

    public void setImages3(Object images3) {
        try {
            this.images3 = (String) images3;
        }catch (Exception ex)
        {
            this.images3 = "";
        }
    }
    public String getImages4() {
        return images4;
    }

    public void setImages4(Object images4) {
        try {
            this.images4 = (String) images4;
        }catch (Exception ex)
        {
            this.images4 = "";
        }
    }

    public String getImagesUrl1() {
        return imagesUrl1;
    }

    public void setimagesUrl1(Object imagesUrl1) {
        try {
            this.imagesUrl1 = (String)imagesUrl1;
        }catch (Exception ex)
        {
            this.imagesUrl1 = "";
        }
    }
    public String getImagesUrl2() {
        return imagesUrl2;
    }

    public void setimagesUrl2(Object imagesUrl2) {
        try {
            this.imagesUrl2 = (String)imagesUrl2;
        }catch (Exception ex)
        {
            this.imagesUrl2 = "";
        }
    }
    public String getImagesUrl3() {
        return imagesUrl3;
    }

    public void setimagesUrl3(Object imagesUrl3) {
        try {
            this.imagesUrl3 = (String)imagesUrl3;
        }catch (Exception ex)
        {
            this.imagesUrl3 = "";
        }
    }
    public String getImagesUrl4() {
        return imagesUrl4;
    }

    public void setimagesUrl4(Object imagesUrl4) {
        try {
            this.imagesUrl4 = (String)imagesUrl4;
        }catch (Exception ex)
        {
            this.imagesUrl4 = "";
        }
    }


    public Boolean getLostCardStatus() {
        return lostCardStatus;
    }

    public void setLostCardStatus(Object lostCardStatus) {
        try {
            this.lostCardStatus = (Boolean) lostCardStatus;
        }catch (Exception ex)
        {
            this.lostCardStatus = false;
        }
    }

    public Object getLostCardMoney() {
        return lostCardMoney;
    }

    public void setLostCardMoney(Object lostCardMoney) {
        try {
            this.lostCardMoney = (Integer) lostCardMoney;
        }catch (Exception ex)
        {
            this.lostCardMoney = 0;
        }
    }

    public String getComputerID() {
        return computerID;
    }

    public void setComputerID(Object computerID) {
        try {
            this.computerID = (String) computerID;
        }catch (Exception ex)
        {
            this.computerID = "null";
        }
    }

    public Object getNote() {
        return note;
    }

    public void setNote(Object note) {
        try {
            this.note = (String) note;
        }catch (Exception ex)
        {
            this.note = "";
        }
    }

    public Integer getCostBefore() {
        return costBefore;
    }

    public void setCostBefore(Object costBefore) {
        try {
            this.costBefore = (Integer) costBefore;
        }catch (Exception ex)
        {
            this.costBefore = 0;
        }
    }

    public String getBUID() {
        return bUID;
    }

    public void setBUID(Object bUID) {
        try {
            this.bUID = (String) bUID;
        }catch (Exception ex)
        {
            this.bUID = "";
        }
    }

    public Boolean getStop() {
        return stop;
    }

    public void setStop(Object stop) {
        try {
            this.stop = (Boolean) stop;
        }catch (Exception ex)
        {
            this.stop = false;
        }
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Object createDate) {
        try {
            this.createDate = Convert.serverDateFormat.parse((String)createDate);
        }catch (Exception ex1)
        {
            try {
                this.createDate = Convert.serverDateFormat2.parse((String) createDate);
            }catch (Exception ex2)
            {
                this.createDate = null;
            }
        }
    }
    public void setCreateDate(Date createDate) {
            this.createDate = createDate;
    }

    public Object getCreateByUser() {
        return createByUser;
    }

    public void setCreateByUser(Object createByUser) {
        try {
            this.createByUser = (String) createByUser;
        }catch (Exception ex)
        {
            this.createByUser = null;
        }
    }

    public Object getUpdtDate() {
        return updtDate;
    }

    public void setUpdtDate(Object updtDate) {
        try {
            this.updtDate = Convert.serverDateFormat.parse((String)updtDate);
        }catch (Exception ex1)
        {
            try {
                this.updtDate = Convert.serverDateFormat2.parse((String) updtDate);
            }catch (Exception ex2)
            {
                this.updtDate = null;
            }
        }
    }
    public void setUpdtDate(Date updtDate) {
            this.updtDate = updtDate;
    }

    public Object getUpdtByUser() {
        return updtByUser;
    }

    public void setUpdtByUser(Object updtByUser) {
        try {
            this.updtByUser = (String)updtByUser;
        }catch (Exception ex)
        {
            this.updtByUser = null;
        }
    }

}
