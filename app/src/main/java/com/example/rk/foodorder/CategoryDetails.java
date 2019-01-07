package com.example.rk.foodorder;

/**
 * Created by Rk on 10-04-2018.
 */

public class CategoryDetails {
    private String name,id;

    public CategoryDetails(String id, String name) {
        this.setName(name);
        this.setId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
