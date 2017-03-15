package com.arjunalabs.android.spikop.data.remote;

import java.util.List;

/**
 * Created by bobbyadiprabowo on 13/03/17.
 */

public class TagsListResponseDTO {

    private List<String> hashtags;

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
}
