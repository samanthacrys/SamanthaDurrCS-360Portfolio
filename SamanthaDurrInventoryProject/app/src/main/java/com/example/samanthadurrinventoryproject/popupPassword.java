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

public class popupPassword extends Fragment {

    //Sets static string variables that work with param1 and param2.
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //Points to the Param1 and Param2 arguments.
    private String mParam1;
    private String mParam2;

    //Default constructor.
    public popupPassword() {
    }

    //Creates a new instance of the forgot password window.
    public static popupPassword newInstance(String param1, String param2) {
        popupPassword fragment = new popupPassword();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //If the arguments are not null, then assign mParam1 and mParam2 to the static strings.
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //Open the popup window.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_popup_password, container, false);
    }
}