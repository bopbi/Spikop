package com.arjunalabs.android.spikop.data.local;

import android.content.Context;
import android.content.SharedPreferences;

import com.arjunalabs.android.spikop.R;
import com.auth0.android.result.Credentials;

/**
 * Created by bobbyadiprabowo on 11/02/17.
 */

public class CredentialsManager {

    final static String REFRESH_TOKEN = "AUTH0_REFRESH_TOKEN";
    final static String ACCESS_TOKEN = "AUTH0_ACCESS_TOKEN";
    final static String ID_TOKEN = "AUTH0_ID_TOKEN";
    final static String CREDENTIAL_TYPE = "AUTH0_CREDENTIAL_TYPE";

    public static void saveCredentials(Context context, Credentials credentials){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.auth0_preferences), Context.MODE_PRIVATE);

        sharedPref.edit()
                .putString(ID_TOKEN, credentials.getIdToken())
                .putString(REFRESH_TOKEN, credentials.getRefreshToken())
                .putString(ACCESS_TOKEN, credentials.getAccessToken())
                .putString(CREDENTIAL_TYPE, credentials.getType())
                .apply();
    }

    public static Credentials getCredentials(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.auth0_preferences), Context.MODE_PRIVATE);

        Credentials credentials = new Credentials(
                sharedPref.getString(ID_TOKEN, null),
                sharedPref.getString(ACCESS_TOKEN, null),
                sharedPref.getString(CREDENTIAL_TYPE, null),
                sharedPref.getString(REFRESH_TOKEN, null));

        return credentials;
    }

    public static void deleteCredentials(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.auth0_preferences), Context.MODE_PRIVATE);

        sharedPref.edit()
                .putString(ID_TOKEN, null)
                .putString(REFRESH_TOKEN, null)
                .putString(ACCESS_TOKEN, null)
                .putString(CREDENTIAL_TYPE, null)
                .apply();
    }

}
