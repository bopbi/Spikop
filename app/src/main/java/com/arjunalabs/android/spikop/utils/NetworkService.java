package com.arjunalabs.android.spikop.utils;

import com.arjunalabs.android.spikop.data.Timeline;
import com.arjunalabs.android.spikop.data.TimelineDTO;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.POST;

/**
 * Created by bobbyadiprabowo on 12/02/17.
 */

public class NetworkService {

    public interface SpikopService {

        @POST("api/spiks")
        Call<TimelineDTO> addSpik(@Field("content") String content);

    }

    class TokenInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder()
                    .header("Authorization", "Bearer "+"auth-value"); // <-- this is the important line

            Request request = requestBuilder.build();
            return chain.proceed(request);
        }
    }
    private SpikopService spikopService;

    private NetworkService(boolean withAuthToken) {
        OkHttpClient okHttpClient = new OkHttpClient();
        if (withAuthToken) {
            okHttpClient.interceptors().add(new TokenInterceptor());
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://ec2-54-166-196-16.compute-1.amazonaws.com/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        spikopService = retrofit.create(SpikopService.class);
    }

    private static NetworkService networkService;
    private static NetworkService networkServiceWithAuth;

    public static NetworkService getInstance() {
        if (networkService == null) {
            networkService = new NetworkService(false);
        }
        return networkService;
    }

    public static NetworkService getInstanceWithAuth() {
        if (networkServiceWithAuth == null) {
            networkServiceWithAuth = new NetworkService(true);
        }
        return networkServiceWithAuth;
    }

    public static NetworkService resetInstanceWithAuth() {
        networkServiceWithAuth = new NetworkService(true);
        return networkServiceWithAuth;
    }


    public SpikopService getSpikopService() {
        return spikopService;
    }
}
