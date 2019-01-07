package com.example.rk.foodorder;

/**
 * Created by Rk on 16-04-2018.
 */

public class FoodDetails {
    String Id,Name,Price,Category_id;

    public FoodDetails(String id, String name, String price, String category_id) {
        Id = id;
        Name = name;
        Price = price;
        Category_id = category_id;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getCategory_id() {
        return Category_id;
    }

    public void setCategory_id(String category_id) {
        Category_id = category_id;
    }
}
