/*
Samantha Durr
CS-260
Project 3, Option 1: Inventory App
 */
package com.example.samanthadurrinventoryproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class newItem extends Fragment {

    //These are static variables that take in parameter arguments. The default is set to param1
    //and param2.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //These are the strings that are connected to the parameters.
    private String mParam1;
    private String mParam2;

    //A base constructor to createa a new item.
    public newItem() {
    }

    //Uses the arguments to create a fragment window that allows for adding a new item to the
    //list.
    public static newItem newInstance(String param1, String param2) {
        newItem fragment = new newItem();
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
        return inflater.inflate(R.layout.fragment_new_item, container, false);
    }
}