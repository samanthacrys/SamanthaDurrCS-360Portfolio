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

public class RequestInformation extends Fragment {

    //Static variables for param1 and param2
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Strings that take in argument information.
    private String mParam1;
    private String mParam2;

    //Default constructor.
    public RequestInformation() {
    }

    //Creates a new instance of requesting information for the SMS.
    public static RequestInformation newInstance(String param1, String param2) {
        RequestInformation fragment = new RequestInformation();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If the arguments are not null, then set the params to the static variables.
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Open the request information fagrment.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_request_information, container, false);
    }
}