/*
Samantha Durr
CS-260
Project 3, Option 1: Inventory App
 */

package com.example.samanthadurrinventoryproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InventoryDetails extends Fragment {

    //These are static variables that take in parameter arguments. The default is set to param1
    //and param2.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //These are the strings that are connected to the parameters.
    private String mParam1;
    private String mParam2;

    //Empty public constructor for the inventory.
    public InventoryDetails() {
        // Required empty public constructor
    }

    //Creates a new instance of the InventoryDetails variable that takes in the information set
    //in the string parameters param1 and param2. IT then creates a new inventorydetail that
    //creates the InventoryDetails fragment window to display the information found in that
    //Inventory item.
    public static InventoryDetails newInstance(String param1, String param2) {
        InventoryDetails fragment = new InventoryDetails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If the arguments are not null, then set mParam1 and mParam2 to the information found
        //in the static variables.
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Creates a new inventory details fragment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory_details, container, false);
    }
}