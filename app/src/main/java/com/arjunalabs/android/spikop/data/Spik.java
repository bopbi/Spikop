package com.arjunalabs.android.spikop.data;

import com.arjunalabs.android.spikop.data.remote.SpikResponseDTO;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class Spik {

    private long id;
    private long remoteId;
    private String content;
    private long createdAt;

    public Spik() {

    }

    public Spik(SpikResponseDTO spikResponseDTO) {
        remoteId = spikResponseDTO.getId();
        content = spikResponseDTO.getContent();
        createdAt = spikResponseDTO.getCreated_at().getTime();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRemoteId() {
        return remoteId;
    }

    public void setRemoteId(long remoteId) {
        this.remoteId = remoteId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}
