package com.arjunalabs.android.spikop.data;

/**
 * Created by bobbyadiprabowo on 06/02/17.
 */

public class Timeline {

    private int id;
    private String content;
    private int createdAt;

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

    public int getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(int createdAt) {
        this.createdAt = createdAt;
    }
}
