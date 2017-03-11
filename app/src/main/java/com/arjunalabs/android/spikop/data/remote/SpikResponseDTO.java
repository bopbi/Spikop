package com.arjunalabs.android.spikop.data.remote;

import java.util.Date;

/**
 * Created by bobbyadiprabowo on 19/02/17.
 */

public class SpikResponseDTO {

    private int id;
    private String content;
    private Date created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
