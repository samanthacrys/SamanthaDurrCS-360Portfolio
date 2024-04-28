/*
Samantha Durr
CS-260
Project 3, Option 1: Inventory App
 */
package com.example.samanthadurrinventoryproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.atomic.AtomicReference;

public class addItem extends AppCompatActivity {
    /*
    itemNumber: is a variable in the view class that displays the item number.

    location: displays the location information from the view for where it is located in the
    warehouse.

    QtyHolder: This is a string that stores the item's quantity information.

    IncreaseQty and DecreaseQty: Both of these variables contain images buttons for increasing
    and decreasing the quantity.

    ItemDescValue, ItemQtyValue, and ItemUnitValue: These store information found from the edit
    text boxes on the item's number of units, quantity, and description.

    CancelButton and AddItemButton: these are buttons that either cancel the addition or add
    the item to the database.

    EmptyHolder: This is a boolean value that is used to know whether a field is filled or not.

    database: this is the database that stores all of the item information.

    DescHolder: This is a string that holds the descriptions.
     */
    View itemNumber;
    View location;
    String QtyHolder;
    ImageButton IncreaseQty, DecreaseQty;
    EditText ItemDescValue, ItemQtyValue, ItemUnitValue;
    Button CancelButton, AddItemButton;
    Boolean EmptyHolder;
    ItemsSQLiteHandler database;
    private String DescHolder;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_new_item);

        //These initiate the variables for itemNumber, location, QtyHolder, and the database
        //for adding the information needed from the screen.
        itemNumber = findViewById(R.id.ItemNumber);
        location = findViewById(R.id.Location);
        QtyHolder = String.valueOf(findViewById(R.id.Quantity));
        database = new ItemsSQLiteHandler();

        AtomicReference<Intent> intent = new AtomicReference<>(getIntent());

        //This sets a listener that receives item quantity input from the add item window.
        //As long as the value is not empty, then the information returned is entered into the
        //item's quantity.
        IncreaseQty.setOnClickListener(view -> {
            int input = 0, total;

            String val = ItemQtyValue.getText().toString().trim();

            if (!val.isEmpty()) {
                input = Integer.parseInt(val);
            }

            total = input + 1;
            ItemQtyValue.setText(String.valueOf(total));
        });

        //This sets a listener to see if the decrease button as been pressed and therefore
        //decreases the total as long as it is not 0.
        DecreaseQty.setOnClickListener(view -> {
            int input, total;

            String qty = ItemQtyValue.getText().toString().trim();

            if (qty.equals("0")) {
                Toast.makeText(this, "Item Quantity is Zero", Toast.LENGTH_LONG).show();
            } else {
                input = Integer.parseInt(qty);
                total = input - 1;
                ItemQtyValue.setText(String.valueOf(total));
            }
        });

        //This sets a listener onto the cancel button that cancels the action of creating a new
        //item to add to the database.
        CancelButton.setOnClickListener(view -> {
            // Going back to ItemsListActivity after cancel adding item
            Intent add = new Intent();
            setResult(0, add);
            this.finish();
        });

        //This is a listener that sets the add button to add the item to the database.
        AddItemButton.setOnClickListener(view -> InsertItemIntoDatabase());
    }

    //This receives information from the add listener to add the item into the database as long
    //as the item has all of the fields filled in and not empty. As long as this is the case,
    //then the item is added to the database with the information present.
    public void InsertItemIntoDatabase() {
        String message = CheckEditTextNotEmpty();

        //If the EmptyHolder boolean is not set to false, then take the value stored in qut
        //and put it in the qtyholder variable and create a new item in the database with the
        //information in the fields. Otherwise, let people know that it's not there.
        if (!EmptyHolder) {
            String qty = QtyHolder;

            Item item = new Item(itemNumber, location, qty);
            database.createItem(item);

            Toast.makeText(this,"Item Added Successfully", Toast.LENGTH_LONG).show();

            Intent add = new Intent();
            setResult(RESULT_OK, add);
            this.finish();
        } else {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    //Checks that there is no empty text in any of the fields that could cause errors when
    //creating a new entry in the database.
    public String CheckEditTextNotEmpty() {
        String message = "";
        DescHolder = ItemDescValue.getText().toString().trim();
        String UnitHolder = ItemUnitValue.getText().toString().trim();
        QtyHolder = ItemQtyValue.getText().toString().trim();

        //If the DescHolder is empty, then set EmptyHolder to true and let the user know that
        //the description field is empty. Then check if the UnitHolder is empty and let the user
        //know that it is when the field is set to true. Otherwise, set EmptyHolder to false and
        //send the correct message to the user.
        if (DescHolder.isEmpty()) {
            ItemDescValue.requestFocus();
            EmptyHolder = true;
            message = "Item Description is Empty";
        } else if (UnitHolder.isEmpty()){
            ItemUnitValue.requestFocus();
            EmptyHolder = true;
            message = "Item Unit is Empty";
        } else {
            EmptyHolder = false;
        }
        return message;
    }
}

