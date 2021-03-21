package com.fintek.httprequestlibrary.api.response;

public class AppConfigResponse {

    /**
     * alert_flag : true
     * alert_content : kami perlu mendata data buku telepon anda
     */

    private boolean alert_flag;
    private String alert_content;

    public boolean getAlert_flag() {
        return alert_flag;
    }

    public void setAlert_flag(boolean alert_flag) {
        this.alert_flag = alert_flag;
    }

    public String getAlert_content() {
        return alert_content;
    }

    public void setAlert_content(String alert_content) {
        this.alert_content = alert_content;
    }
}
