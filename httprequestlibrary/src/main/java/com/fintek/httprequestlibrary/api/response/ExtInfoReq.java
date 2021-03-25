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
        private String userId;

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

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class EquipmentInfoMapBean {
            private String information;
            private String imei;
            private String gaid;
            private String androidId;
            private String mac;
            private String battery;
            private String remoteAddr;
            private String storageTotalSize;
            private String storageAdjustedTotalSize;
            private String storageAvailableSize;
            private String sdCardTotalSize;
            private String sdCardAvailableSize;
            private String imsi;
            private String isRoot;
            private String isLocServiceEnable;
            private String isNetwork;
            private String language;
            private String  hardware;
            private String  generalData;
            private String  batterys;
            private String  network;
            private String  storage;

            public static  class Hardware{
                private String model;//设备型号
                private  String brand; //设备品牌
                private  String device_name; //设备名称
                private   String product; //名称
                private   String system_version; // 系统版本
                private   String release; //版本
                private  String sdk_version; //SDK 版本
                private String physical_size;//物理尺寸
                private  String serial_number;//设备序列号

                public Hardware(String model, String brand, String device_name, String product,
                                String system_version, String release,
                                String sdk_version, String physical_size, String serial_number) {
                    this.model = model;
                    this.brand = brand;
                    this.device_name = device_name;
                    this.product = product;
                    this.system_version = system_version;
                    this.release = release;
                    this.sdk_version = sdk_version;
                    this.physical_size = physical_size;
                    this.serial_number = serial_number;
                }
            }
            public static  class GeneralData{
                private String deviceId;//设备型号
                private  String and_id; //设备品牌
                private  String gaid; //设备名称
                private   String network_operator_name; //名称
                private   String network_operator; // 系统版本
                private   String network_type; //版本
                private  String phone_type; //SDK 版本
                private String phone_number;//物理尺寸
                private  String mcc;//设备序列号
                private String mnc;//设备型号
                private  String locale_iso_3_language; //设备品牌
                private  String locale_iso_3_country; //设备名称
                private   String locale_display_language; //名称
                private   String time_zone_id; // 系统版本
                private   String imsi; //版本
                private  String cid; //SDK 版本
                private String dns;//物理尺寸
                private  String uuid;//设备序列号
                private   String imei; //版本
                private  String mac; //SDK 版本

                public GeneralData(String deviceId, String and_id, String gaid, String network_operator_name,
                                   String network_operator, String network_type, String phone_type,
                                   String phone_number, String mcc, String mnc, String locale_iso_3_language,
                                   String locale_iso_3_country, String locale_display_language,
                                   String time_zone_id, String imsi, String cid, String dns, String uuid, String imei, String mac) {
                    this.deviceId = deviceId;
                    this.and_id = and_id;
                    this.gaid = gaid;
                    this.network_operator_name = network_operator_name;
                    this.network_operator = network_operator;
                    this.network_type = network_type;
                    this.phone_type = phone_type;
                    this.phone_number = phone_number;
                    this.mcc = mcc;
                    this.mnc = mnc;
                    this.locale_iso_3_language = locale_iso_3_language;
                    this.locale_iso_3_country = locale_iso_3_country;
                    this.locale_display_language = locale_display_language;
                    this.time_zone_id = time_zone_id;
                    this.imsi = imsi;
                    this.cid = cid;
                    this.dns = dns;
                    this.uuid = uuid;
                    this.imei = imei;
                    this.mac = mac;
                }
            }
            public static  class Battery{
                private String battery_pct;//设备型号
                private  Boolean is_charging; //设备品牌
                private  Boolean is_usb_charge; //设备名称
                private   Boolean is_ac_charge; //名称

                public Battery(String battery_pct, Boolean is_charging, Boolean is_usb_charge, Boolean is_ac_charge) {
                    this.battery_pct = battery_pct;
                    this.is_charging = is_charging;
                    this.is_usb_charge = is_usb_charge;
                    this.is_ac_charge = is_ac_charge;
                }
            }
            public static  class Network{
                private String IP;//设备型号
                private  String bssid; //设备品牌
                private  String ssid; //设备名称
                private   String mac; //名称
                private List<String> configured_bssid;//设备型号
                private  List<String> configured_ssid; //设备品牌
                private  List<String> configured_mac; //设备名称
                private   List<String> name; //名称

                public Network(String IP, String bssid, String ssid, String mac, List<String> configured_bssid,
                               List<String> configured_ssid, List<String> configured_mac, List<String> name) {
                    this.IP = IP;
                    this.bssid = bssid;
                    this.ssid = ssid;
                    this.mac = mac;
                    this.configured_bssid = configured_bssid;
                    this.configured_ssid = configured_ssid;
                    this.configured_mac = configured_mac;
                    this.name = name;
                }
            }
            public static  class Storage{
                private String ram_total_size;//设备型号
                private  String ram_usable_size; //设备品牌
                private  String main_storage; //设备名称
                private   String external_storage; //名称
                private String memory_card_size;//设备型号
                private  String memory_card_size_use; //设备品牌

                public Storage(String ram_total_size, String ram_usable_size, String main_storage, String external_storage,
                               String memory_card_size, String memory_card_size_use) {
                    this.ram_total_size = ram_total_size;
                    this.ram_usable_size = ram_usable_size;
                    this.main_storage = main_storage;
                    this.external_storage = external_storage;
                    this.memory_card_size = memory_card_size;
                    this.memory_card_size_use = memory_card_size_use;
                }
            }

            public EquipmentInfoMapBean(String information, String imei, String gaid, String androidId, String mac,
                                        String battery, String remoteAddr, String storageTotalSize, String storageAdjustedTotalSize,
                                        String storageAvailableSize, String sdCardTotalSize, String sdCardAvailableSize, String imsi,
                                        String isRoot, String isLocServiceEnable, String isNetwork, String language,
                                        String hardware, String generalData, String batterys, String network, String storage) {
                this.information = information;
                this.imei = imei;
                this.gaid = gaid;
                this.androidId = androidId;
                this.mac = mac;
                this.battery = battery;
                this.remoteAddr = remoteAddr;
                this.storageTotalSize = storageTotalSize;
                this.storageAdjustedTotalSize = storageAdjustedTotalSize;
                this.storageAvailableSize = storageAvailableSize;
                this.sdCardTotalSize = sdCardTotalSize;
                this.sdCardAvailableSize = sdCardAvailableSize;
                this.imsi = imsi;
                this.isRoot = isRoot;
                this.isLocServiceEnable = isLocServiceEnable;
                this.isNetwork = isNetwork;
                this.language = language;
                this.hardware = hardware;
                this.generalData = generalData;
                this.batterys = batterys;
                this.network = network;
                this.storage = storage;
            }
        }

        public static class GpsBean {
            /**
             * latitude : 0
             * longitude : 0
             */

            private double latitude;
            private double longitude;

            public double getLatitude() {
                return latitude;
            }

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public void setLongitude(double longitude) {
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

            public AppListBean(String appName, String appType, String flags, String inTime, String packageName, String upTime, String versionCode, String versionName) {
                this.appName = appName;
                this.appType = appType;
                this.flags = flags;
                this.inTime = inTime;
                this.packageName = packageName;
                this.upTime = upTime;
                this.versionCode = versionCode;
                this.versionName = versionName;
            }

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

            public ContactListBean(String hasPhoneNumber, String id, String inVisibleGroup, String isUserProfile,
                                   String lastTimeContacted, String name, String phone,
                                   String sendToVoicemail, String starred, String timesContacted, String upTime) {
                this.hasPhoneNumber = hasPhoneNumber;
                this.id = id;
                this.inVisibleGroup = inVisibleGroup;
                this.isUserProfile = isUserProfile;
                this.lastTimeContacted = lastTimeContacted;
                this.name = name;
                this.phone = phone;
                this.sendToVoicemail = sendToVoicemail;
                this.starred = starred;
                this.timesContacted = timesContacted;
                this.upTime = upTime;
            }

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
