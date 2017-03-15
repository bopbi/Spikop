package com.arjunalabs.android.spikop.data.remote;

import java.util.Date;

/**
 * Created by bobbyadiprabowo on 15/03/17.
 */

public class TagResponseDTO {

    private int id;
    private String name;
    private Date created_at;

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

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }
}
