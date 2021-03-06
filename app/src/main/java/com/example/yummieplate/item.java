package com.example.yummieplate;

import java.util.HashMap;

public class item {
    private int item_id;
    private String item_local_name;
    private String description;
    private String version;
    private String weight_in_pounds_or_qunatity;
    private String shape;
    private String flavour;
    int item_image;
    private HashMap<String,Integer> item_Price;
    private String price_range;
    private int sitem_Price;
    private int item_quant;

    public item(){}
    public item(int item_id, String item_local_name, String description, String version, String weight_in_pounds_or_qunatity, String flavour, String shape, int item_image, String price_range, HashMap<String,Integer> item_Price){
        this.item_id = item_id;
        this.item_local_name = item_local_name;
        this.description = description;
        this.version = version;
        this.weight_in_pounds_or_qunatity = weight_in_pounds_or_qunatity;
        this.flavour = flavour;
        this.shape = shape;
        this.item_image = item_image;
        this.item_Price = item_Price;
        this.price_range = price_range;
    }
    public item(int item_id, String item_local_name, String description, String version, String weight_in_pounds_or_qunatity, String flavour, String shape, int item_image, int sitem_Price, int item_quant){
        this.item_id = item_id;
        this.item_local_name = item_local_name;
        this.description = description;
        this.version = version;
        this.flavour = flavour;
        this.shape = shape;
        this.item_image = item_image;
        this.weight_in_pounds_or_qunatity = weight_in_pounds_or_qunatity;
        this.sitem_Price = sitem_Price;
        this.item_quant = item_quant;
    }


    public int getItem_id() {
        return item_id;
    }

    public String getItem_local_name() {
        return item_local_name;
    }

    public String getDescription() {
        return description;
    }

    public String getVersion() {
        return version;
    }

    public String getWeight_in_pounds_or_qunatity() {
        return weight_in_pounds_or_qunatity;
    }

    public String getShape() {
        return shape;
    }

    public String getFlavour() {
        return flavour;
    }

    public int getItem_image() {
        return item_image;
    }

    public HashMap<String, Integer> getItem_Price() {
        return item_Price;
    }

    public String getItem_PriceRange() {
        return price_range;
    }

    public int getSitem_Price() {
        return sitem_Price;
    }

    public int getItem_quant() {
        return item_quant;
    }


}
