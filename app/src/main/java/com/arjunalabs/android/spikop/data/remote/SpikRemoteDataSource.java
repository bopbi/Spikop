package com.arjunalabs.android.spikop.data.remote;

import android.content.Context;

import com.arjunalabs.android.spikop.data.Hashtag;
import com.arjunalabs.android.spikop.data.Spik;
import com.arjunalabs.android.spikop.data.SpikDataSource;
import com.arjunalabs.android.spikop.utils.NetworkAuthService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class SpikRemoteDataSource implements SpikDataSource {

    private static SpikRemoteDataSource INSTANCE;
    private NetworkAuthService networkAuthService;


    private SpikRemoteDataSource(Context context) {
        networkAuthService = NetworkAuthService.getInstance(context);
    }

    public static SpikRemoteDataSource getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SpikRemoteDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public List<Spik> getAllSpiks(boolean refresh, long lastId) {
        Call<SpikListResponseDTO> listCall = networkAuthService.getSpikopService().getTimeline(lastId);
        List<Spik> spiks = null;
        try {
            Response<SpikListResponseDTO> listResponse = listCall.execute();
            SpikListResponseDTO responseDTOList = listResponse.body();
            if (responseDTOList == null) {
                return null;
            }

            if (responseDTOList.getSpiks() == null) {
                return null;
            }
            spiks = new ArrayList<>(responseDTOList.getSpiks().size());

            for (SpikResponseDTO spikResponseDTO:
                 responseDTOList.getSpiks()) {
                Spik spik = new Spik(spikResponseDTO);
                spiks.add(spik);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return spiks;
    }

    @Override
    public List<Hashtag> getAllHashtags() {
        return null;
    }

    @Override
    public Spik getSpikById(long id) {
        return null;
    }

    @Override
    public List<Spik> addSpiks(List<Spik> spikList) {
        return null;
    }

    @Override
    public Spik addSpik(Spik spik) {
        Call<SpikResponseDTO> spikCall = networkAuthService.getSpikopService().addSpik(spik.getContent());
        Response<SpikResponseDTO> spikResponseDTO = null;
        try {
            spikResponseDTO = spikCall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (spikResponseDTO == null) {
            return null;
        }

        SpikResponseDTO responseDTO = spikResponseDTO.body();

        if (responseDTO == null) {
            return null;
        }

        return new Spik(responseDTO);
    }
}
