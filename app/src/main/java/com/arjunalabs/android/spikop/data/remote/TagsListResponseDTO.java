package com.arjunalabs.android.spikop.data.remote;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 13/03/17.
 */

public class TagsListResponseDTO {

    private List<TagResponseDTO> hashtags;

    public List<TagResponseDTO> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<TagResponseDTO> hashtags) {
        this.hashtags = hashtags;
    }
}
