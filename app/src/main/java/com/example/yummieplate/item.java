package com.example.yummieplate;

public class item {
    private int item_id;
    private String item_local_name;
    private String description;
    private int version;
    private int weight_in_pounds;
    private String shape;
    private String flavour;
    int item_image;
    private int item_Price;
    private int item_quant;

    public item(){}
    public item(int item_id, String item_local_name, String description, int version, int weight_in_pounds, String shape, String flavour, int item_image, int item_Price){
        this.item_id = item_id;
        this.item_local_name = item_local_name;
        this.description = description;
        this.version = version;
        this.weight_in_pounds = weight_in_pounds;
        this.shape = shape;
        this.flavour = flavour;
        this.item_image = item_image;
        this.item_Price = item_Price;
    }
    public item(int item_id, String item_local_name, String description, int version, int weight_in_pounds, String shape, String flavour, int item_image, int item_Price, int item_quant){
        this.item_id = item_id;
        this.item_local_name = item_local_name;
        this.description = description;
        this.version = version;
        this.shape = shape;
        this.flavour = flavour;
        this.item_image = item_image;
        this.weight_in_pounds = weight_in_pounds;
        this.item_Price = item_Price;
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

    public int getVersion() {
        return version;
    }

    public int getWeight_in_pounds() {
        return weight_in_pounds;
    }

    public String getShape() {
        return shape;
    }

    public int getItem_image() {
        return item_image;
    }

    public int getItem_Price() {
        return item_Price;
    }

    public int getItem_quant() {
        return item_quant;
    }
}
