package com.arjunalabs.android.spikop.data;

import com.arjunalabs.android.spikop.data.remote.TagResponseDTO;

/**
 * Created by bobbyadiprabowo on 19/02/17.
 */

public class Hashtag {

    private int id;
    private String name;
    private long created_at;

    public Hashtag() {

    }

    public Hashtag(TagResponseDTO tagResponseDTO) {
        this.id = tagResponseDTO.getId();
        this.name = "#"+tagResponseDTO.getName();
        this.created_at = tagResponseDTO.getCreated_at().getTime();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }
}
