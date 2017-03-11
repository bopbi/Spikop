package com.arjunalabs.android.spikop.utils;

import android.content.Context;

import com.arjunalabs.android.spikop.R;
import com.arjunalabs.android.spikop.data.DelegationRequestDTO;
import com.arjunalabs.android.spikop.data.remote.SpikListResponseDTO;
import com.arjunalabs.android.spikop.data.local.CredentialsManager;
import com.arjunalabs.android.spikop.data.remote.SpikResponseDTO;
import com.auth0.android.result.Credentials;
import com.auth0.android.result.Delegation;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.net.HttpURLConnection;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by bobbyadiprabowo on 12/02/17.
 */

public class NetworkAuthService {

    public interface SpikopService {

        @POST("api/spiks")
        Call<SpikResponseDTO> addSpik(@Field("content") String content);

        @GET("api/timeline")
        Call<SpikListResponseDTO> getTimeline(@Query("since_id") long sinceId);
    }

    class AuthenticationInterceptor implements Interceptor {

        private Credentials credentials;
        private String clientId;
        private String auth0Domain;
        private Context context;

        public AuthenticationInterceptor(Context context) {
            clientId = context.getString(R.string.auth0_client_id);
            auth0Domain = context.getString(R.string.auth0_domain);
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            credentials = CredentialsManager.getCredentials(context);

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer "+ credentials.getIdToken() ); // <-- this is the important line

            Request request = requestBuilder.build();
            Response response = chain.proceed(request);

            if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");

                OkHttpClient client = new OkHttpClient();

                DelegationRequestDTO delegationRequestDto = new DelegationRequestDTO();
                delegationRequestDto.setClient_id(clientId);
                delegationRequestDto.setApi_type(credentials.getType());
                delegationRequestDto.setId_token(credentials.getIdToken());

                Gson gson = new Gson();
                String json = gson.toJson(delegationRequestDto, DelegationRequestDTO.class);

                RequestBody body = RequestBody.create(JSON, json);
                Request tokenRequest = new Request.Builder()
                        .url("https://"+auth0Domain+"/delegation")
                        .post(body)
                        .build();
                Response tokenResponse = client.newCall(tokenRequest).execute();
                String res = tokenResponse.body().string();

                Delegation delegation = gson.fromJson(res, Delegation.class);

                Credentials newCredentials = new Credentials(delegation.getIdToken(), credentials.getAccessToken(), credentials.getType(), credentials.getRefreshToken());
                credentials = newCredentials;

                if (context != null) {
                    CredentialsManager.saveCredentials(context, credentials);
                }

                // retry the request again
                // Request customization: add request headers
                requestBuilder = original.newBuilder()
                        .header("Authorization", "Bearer "+ credentials.getIdToken() ); // <-- this is the important line

                request = requestBuilder.build();
                response = chain.proceed(request);
            }
            return response;
        }
    }
    private SpikopService spikopService;

    private NetworkAuthService(Context context) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(context))
                .build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-166-196-16.compute-1.amazonaws.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        spikopService = retrofit.create(SpikopService.class);
    }

    private static NetworkAuthService networkAuthService;

    public static NetworkAuthService getInstance(Context context) {
        if (networkAuthService == null) {
            networkAuthService = new NetworkAuthService(context);
        }
        return networkAuthService;
    }

    public SpikopService getSpikopService() {
        return spikopService;
    }
}
