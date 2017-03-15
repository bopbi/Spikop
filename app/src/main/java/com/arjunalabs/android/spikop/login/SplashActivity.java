package com.arjunalabs.android.spikop.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.data.local.CredentialsManager;
import com.arjunalabs.android.spikop.spiks.SpiksActivity;
import com.auth0.android.Auth0;
import com.auth0.android.authentication.AuthenticationAPIClient;
import com.auth0.android.lock.AuthenticationCallback;
import com.auth0.android.lock.Lock;
import com.auth0.android.lock.LockCallback;
import com.auth0.android.lock.utils.LockException;
import com.auth0.android.result.Credentials;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by bobbyadiprabowo on 04/02/17.
 */

public class SplashActivity extends Activity {

    private Lock mLock;
    private AuthenticationAPIClient apiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Auth0 auth0 = new Auth0(getString(R.string.auth0_client_id), getString(R.string.auth0_domain));
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("scope", "openid offline_access");
        parameters.put("device", UUID.randomUUID().toString());
        mLock = Lock.newBuilder(auth0, mCallback)
                //Add parameters to the build
                .withAuthenticationParameters(parameters)
                .build(this);

        if (CredentialsManager.getCredentials(this).getIdToken() == null) {
            startActivity(mLock.newIntent(this));
            return;
        } else {
            startActivity(new Intent(getApplicationContext(), SpiksActivity.class));
            finish();
        }
        // decided later
        /*
        apiClient = new AuthenticationAPIClient(auth0);
        apiClient.userInfo(CredentialsManager.getCredentials(this).getAccessToken())
                .start(new BaseCallback<UserProfile, AuthenticationException>() {
                    @Override
                    public void onSuccess(final UserProfile payload) {
                        SplashActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SplashActivity.this, "Automatic Login Success", Toast.LENGTH_SHORT).show();
                            }
                        });
                        startActivity(new Intent(getApplicationContext(), SpiksActivity.class));
                        finish();
                    }

                    @Override
                    public void onFailure(AuthenticationException error) {
                        SplashActivity.this.runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(SplashActivity.this, "Session Expired, please Log In Again", Toast.LENGTH_SHORT).show();
                            }
                        });
                        CredentialsManager.deleteCredentials(getApplicationContext());
                        startActivity(mLock.newIntent(SplashActivity.this));
                    }
                });
          */
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Your own Activity code
        mLock.onDestroy(this);
        mLock = null;
    }

    private final LockCallback mCallback = new AuthenticationCallback() {
        @Override
        public void onAuthentication(Credentials credentials) {
            Toast.makeText(getApplicationContext(), "Log In - Success", Toast.LENGTH_SHORT).show();
            CredentialsManager.saveCredentials(getApplicationContext(), credentials);
            startActivity(new Intent(getApplicationContext(), SpiksActivity.class));
            finish();
        }

        @Override
        public void onCanceled() {
            Toast.makeText(getApplicationContext(), "Log In - Cancelled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(LockException error) {
            Toast.makeText(getApplicationContext(), "Log In - Error Occurred", Toast.LENGTH_SHORT).show();
        }
    };

}
