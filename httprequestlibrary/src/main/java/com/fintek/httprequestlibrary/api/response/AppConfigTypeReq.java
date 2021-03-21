package com.fintek.httprequestlibrary.api.response;

import java.util.List;

public class AppConfigTypeReq {
    private List<String> appConfigTypeEnumsReqs;

    public AppConfigTypeReq(List<String> appConfigTypeEnumsReqs) {
        this.appConfigTypeEnumsReqs = appConfigTypeEnumsReqs;
    }

    public List<String> getAppConfigTypeEnumsReqs() {
        return appConfigTypeEnumsReqs;
    }

    public void setAppConfigTypeEnumsReqs(List<String> appConfigTypeEnumsReqs) {
        this.appConfigTypeEnumsReqs = appConfigTypeEnumsReqs;
    }
}
