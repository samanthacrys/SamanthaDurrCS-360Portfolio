/*
Samantha Durr
CS-260
Project 3, Option 1: Inventory App
 */
package com.example.samanthadurrinventoryproject;

import android.view.View;

public class Item {
    //Supplies an integer variable called id.
    int id;
    //Provides a quantity and a unit to the items.
    String qty;
    String unit;

    //Sets an item to the item number, location, and quantity.
    public Item(View itemNumber, View location, String qty) {

        super();
    }

    //This sets the id of the item and the quantity to what is found in ii.
    public Item(int ii, String quantity) {
        super();
        this.id = ii;
        this.qty = quantity;
    }

    // constructor
    public Item(String quantity) {
        this.qty = quantity;
    }

    //Gets the id and returns it.
    public int getId() {

        return id;
    }
    //Sets the id to itself.
    public void setId(int id) {

        this.id = id;
    }

    //gets the quantity of the item.
    public String getQty() {

        return qty;
    }

    //Sets the item's quantity.
    public void setQty(String qty) {

        this.qty = qty;
    }

}

