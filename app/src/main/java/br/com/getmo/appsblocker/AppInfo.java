package br.com.getmo.appsblocker;

import com.orm.SugarRecord;

/**
 * Created by fabio.licks on 04/05/16.
 */
public class AppInfo extends SugarRecord {

    private String appId;
    private Boolean isAllowed;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public Boolean getAllowed() {
        return isAllowed;
    }

    public void setAllowed(Boolean allowed) {
        isAllowed = allowed;
    }
}
