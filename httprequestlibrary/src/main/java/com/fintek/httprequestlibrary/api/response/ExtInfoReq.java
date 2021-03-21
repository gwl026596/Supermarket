package com.fintek.httprequestlibrary.api.response;

import java.util.List;

public class ExtInfoReq {

    public ExtInfoReq(ExtInfoReqBean extInfoReq) {
        this.extInfoReq = extInfoReq;
    }

    /**
     * extInfoReq : {"appList":[{"appName":"string","appType":"string","flags":"string","inTime":"string","packageName":"string","upTime":"string","versionCode":"string","versionName":"string"}],"blackbox":"string","callLogList":[{"duration":"string","name":"string","phone":"string","time":"string","type":0}],"contactList":[{"hasPhoneNumber":"string","id":"string","inVisibleGroup":"string","isUserProfile":"string","lastTimeContacted":"string","name":"string","phone":"string","sendToVoicemail":"string","starred":"string","timesContacted":"string","upTime":"string"}],"equipmentInfoMap":{},"gps":{"latitude":0,"longitude":0},"merchantId":"string","smsList":[{"body":"string","name":"string","phone":"string","receiver":"string","time":"string","type":"string"}],"userId":0}
     */

    private ExtInfoReqBean extInfoReq;

    public ExtInfoReqBean getExtInfoReq() {
        return extInfoReq;
    }

    public void setExtInfoReq(ExtInfoReqBean extInfoReq) {
        this.extInfoReq = extInfoReq;
    }

    public static class ExtInfoReqBean {
        /**
         * appList : [{"appName":"string","appType":"string","flags":"string","inTime":"string","packageName":"string","upTime":"string","versionCode":"string","versionName":"string"}]
         * blackbox : string
         * callLogList : [{"duration":"string","name":"string","phone":"string","time":"string","type":0}]
         * contactList : [{"hasPhoneNumber":"string","id":"string","inVisibleGroup":"string","isUserProfile":"string","lastTimeContacted":"string","name":"string","phone":"string","sendToVoicemail":"string","starred":"string","timesContacted":"string","upTime":"string"}]
         * equipmentInfoMap : {}
         * gps : {"latitude":0,"longitude":0}
         * merchantId : string
         * smsList : [{"body":"string","name":"string","phone":"string","receiver":"string","time":"string","type":"string"}]
         * userId : 0
         */

        private List<AppListBean> appList;
        private String blackbox;
        private List<CallLogListBean> callLogList;
        private List<ContactListBean> contactList;
        private EquipmentInfoMapBean equipmentInfoMap;
        private GpsBean gps;
        private String merchantId;
        private List<SmsListBean> smsList;
        private int userId;

        public List<AppListBean> getAppList() {
            return appList;
        }

        public void setAppList(List<AppListBean> appList) {
            this.appList = appList;
        }

        public String getBlackbox() {
            return blackbox;
        }

        public void setBlackbox(String blackbox) {
            this.blackbox = blackbox;
        }

        public List<CallLogListBean> getCallLogList() {
            return callLogList;
        }

        public void setCallLogList(List<CallLogListBean> callLogList) {
            this.callLogList = callLogList;
        }

        public List<ContactListBean> getContactList() {
            return contactList;
        }

        public void setContactList(List<ContactListBean> contactList) {
            this.contactList = contactList;
        }

        public EquipmentInfoMapBean getEquipmentInfoMap() {
            return equipmentInfoMap;
        }

        public void setEquipmentInfoMap(EquipmentInfoMapBean equipmentInfoMap) {
            this.equipmentInfoMap = equipmentInfoMap;
        }

        public GpsBean getGps() {
            return gps;
        }

        public void setGps(GpsBean gps) {
            this.gps = gps;
        }

        public String getMerchantId() {
            return merchantId;
        }

        public void setMerchantId(String merchantId) {
            this.merchantId = merchantId;
        }

        public List<SmsListBean> getSmsList() {
            return smsList;
        }

        public void setSmsList(List<SmsListBean> smsList) {
            this.smsList = smsList;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public static class EquipmentInfoMapBean {
        }

        public static class GpsBean {
            /**
             * latitude : 0
             * longitude : 0
             */

            private int latitude;
            private int longitude;

            public int getLatitude() {
                return latitude;
            }

            public void setLatitude(int latitude) {
                this.latitude = latitude;
            }

            public int getLongitude() {
                return longitude;
            }

            public void setLongitude(int longitude) {
                this.longitude = longitude;
            }
        }

        public static class AppListBean {
            /**
             * appName : string
             * appType : string
             * flags : string
             * inTime : string
             * packageName : string
             * upTime : string
             * versionCode : string
             * versionName : string
             */

            private String appName;
            private String appType;
            private String flags;
            private String inTime;
            private String packageName;
            private String upTime;
            private String versionCode;
            private String versionName;

            public String getAppName() {
                return appName;
            }

            public void setAppName(String appName) {
                this.appName = appName;
            }

            public String getAppType() {
                return appType;
            }

            public void setAppType(String appType) {
                this.appType = appType;
            }

            public String getFlags() {
                return flags;
            }

            public void setFlags(String flags) {
                this.flags = flags;
            }

            public String getInTime() {
                return inTime;
            }

            public void setInTime(String inTime) {
                this.inTime = inTime;
            }

            public String getPackageName() {
                return packageName;
            }

            public void setPackageName(String packageName) {
                this.packageName = packageName;
            }

            public String getUpTime() {
                return upTime;
            }

            public void setUpTime(String upTime) {
                this.upTime = upTime;
            }

            public String getVersionCode() {
                return versionCode;
            }

            public void setVersionCode(String versionCode) {
                this.versionCode = versionCode;
            }

            public String getVersionName() {
                return versionName;
            }

            public void setVersionName(String versionName) {
                this.versionName = versionName;
            }
        }

        public static class CallLogListBean {
            /**
             * duration : string
             * name : string
             * phone : string
             * time : string
             * type : 0
             */

            private String duration;
            private String name;
            private String phone;
            private String time;
            private int type;

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }
        }

        public static class ContactListBean {
            /**
             * hasPhoneNumber : string
             * id : string
             * inVisibleGroup : string
             * isUserProfile : string
             * lastTimeContacted : string
             * name : string
             * phone : string
             * sendToVoicemail : string
             * starred : string
             * timesContacted : string
             * upTime : string
             */

            private String hasPhoneNumber;
            private String id;
            private String inVisibleGroup;
            private String isUserProfile;
            private String lastTimeContacted;
            private String name;
            private String phone;
            private String sendToVoicemail;
            private String starred;
            private String timesContacted;
            private String upTime;

            public String getHasPhoneNumber() {
                return hasPhoneNumber;
            }

            public void setHasPhoneNumber(String hasPhoneNumber) {
                this.hasPhoneNumber = hasPhoneNumber;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getInVisibleGroup() {
                return inVisibleGroup;
            }

            public void setInVisibleGroup(String inVisibleGroup) {
                this.inVisibleGroup = inVisibleGroup;
            }

            public String getIsUserProfile() {
                return isUserProfile;
            }

            public void setIsUserProfile(String isUserProfile) {
                this.isUserProfile = isUserProfile;
            }

            public String getLastTimeContacted() {
                return lastTimeContacted;
            }

            public void setLastTimeContacted(String lastTimeContacted) {
                this.lastTimeContacted = lastTimeContacted;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getSendToVoicemail() {
                return sendToVoicemail;
            }

            public void setSendToVoicemail(String sendToVoicemail) {
                this.sendToVoicemail = sendToVoicemail;
            }

            public String getStarred() {
                return starred;
            }

            public void setStarred(String starred) {
                this.starred = starred;
            }

            public String getTimesContacted() {
                return timesContacted;
            }

            public void setTimesContacted(String timesContacted) {
                this.timesContacted = timesContacted;
            }

            public String getUpTime() {
                return upTime;
            }

            public void setUpTime(String upTime) {
                this.upTime = upTime;
            }
        }

        public static class SmsListBean {
            /**
             * body : string
             * name : string
             * phone : string
             * receiver : string
             * time : string
             * type : string
             */

            private String body;
            private String name;
            private String phone;
            private String receiver;
            private String time;
            private String type;

            public String getBody() {
                return body;
            }

            public void setBody(String body) {
                this.body = body;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public String getReceiver() {
                return receiver;
            }

            public void setReceiver(String receiver) {
                this.receiver = receiver;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
