package com.hfad.volume.FragmentsFolder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.volume.R;


public class FindNumberFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_find_number, container, false);

        //method to delete local database
//        getContext().deleteDatabase("PhoneNumbers");

        return view;
    }
}