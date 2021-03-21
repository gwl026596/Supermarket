package com.fintek.httprequestlibrary.api.response;

public class NeedUploadExtInfoResponse {

    /**
     * appInfo : true
     * userContact : true
     * sms : false
     * callLog : false
     * equipmentInfo : false
     * equipmentInfoMap : true
     * blackbox : false
     * gps : true
     * imei : false
     */

    private boolean appInfo;
    private boolean userContact;
    private boolean sms;
    private boolean callLog;
    private boolean equipmentInfo;
    private boolean equipmentInfoMap;
    private boolean blackbox;
    private boolean gps;
    private boolean imei;

    public boolean isAppInfo() {
        return appInfo;
    }

    public void setAppInfo(boolean appInfo) {
        this.appInfo = appInfo;
    }

    public boolean isUserContact() {
        return userContact;
    }

    public void setUserContact(boolean userContact) {
        this.userContact = userContact;
    }

    public boolean isSms() {
        return sms;
    }

    public void setSms(boolean sms) {
        this.sms = sms;
    }

    public boolean isCallLog() {
        return callLog;
    }

    public void setCallLog(boolean callLog) {
        this.callLog = callLog;
    }

    public boolean isEquipmentInfo() {
        return equipmentInfo;
    }

    public void setEquipmentInfo(boolean equipmentInfo) {
        this.equipmentInfo = equipmentInfo;
    }

    public boolean isEquipmentInfoMap() {
        return equipmentInfoMap;
    }

    public void setEquipmentInfoMap(boolean equipmentInfoMap) {
        this.equipmentInfoMap = equipmentInfoMap;
    }

    public boolean isBlackbox() {
        return blackbox;
    }

    public void setBlackbox(boolean blackbox) {
        this.blackbox = blackbox;
    }

    public boolean isGps() {
        return gps;
    }

    public void setGps(boolean gps) {
        this.gps = gps;
    }

    public boolean isImei() {
        return imei;
    }

    public void setImei(boolean imei) {
        this.imei = imei;
    }
}
