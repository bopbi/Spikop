package com.arjunalabs.android.spikop.data;

/**
 * Created by bobbyadiprabowo on 19/02/17.
 */

public class DelegationRequestDTO {

    private String client_id;
    private String grant_type = "urn:ietf:params:oauth:grant-type:jwt-bearer";
    private String refresh_token;
    private String scope = "openId";
    private String api_type;

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getApi_type() {
        return api_type;
    }

    public void setApi_type(String api_type) {
        this.api_type = api_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }
}
