package com.arjunalabs.android.spikop.data;

/**
 * Created by bobbyadiprabowo on 19/02/17.
 */

public class Hashtag {

    private int id;
    private String name;
    private int created_at;

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

    public int getCreated_at() {
        return created_at;
    }

    public void setCreated_at(int created_at) {
        this.created_at = created_at;
    }
}
