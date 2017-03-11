package com.arjunalabs.android.spikop.data.remote;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 21/02/17.
 */

public class SpikListResponseDTO {

    private List<SpikResponseDTO> spiks;

    public List<SpikResponseDTO> getSpiks() {
        return spiks;
    }

    public void setSpiks(List<SpikResponseDTO> spiks) {
        this.spiks = spiks;
    }
}
