package com.hfad.volume.FragmentsFolder;

import android.app.ActivityManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.volume.AuthenticationActivity;
import com.hfad.volume.DataBase;
import com.hfad.volume.R;
import com.hfad.volume.ServicesFolder.DiscoverableService;


public class FindNumberFragment extends Fragment {

    FirebaseDatabase database;
    DatabaseReference myRef;
    Button makeDiscoverable,getControl;
    EditText findPhone,lostPassword;
    DataBase db;
    Cursor res;
    Intent intent;
    int state;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_find_number, container, false);
        makeDiscoverable=view.findViewById(R.id.makeDiscoverable);
        getControl=view.findViewById(R.id.getControl);
        findPhone=view.findViewById(R.id.findPhone);
        lostPassword=view.findViewById(R.id.lostPassword);
        AuthenticationActivity a=new AuthenticationActivity();
        db=new DataBase(getContext());
        res= db.getData();
        res.moveToLast();
        state=res.getInt(2);

        intent=new Intent(getActivity(),DiscoverableService.class);

        if(isMyServiceRunning()!=0){makeDiscoverable.setText("Stop Discoverability");}
        else {makeDiscoverable.setText("Start Discoverability");}

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("User");



        makeDiscoverable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(state!=0){
                    Log.v("State:",Integer.toString(res.getInt(2)));
                    Discoverable();
                }
                else {
                    Log.v("State:",Integer.toString(res.getInt(2)));
                    notDiscoverable();
                }


            }
        });

        getControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String findPhoneString=findPhone.getText().toString();
                String lostPasswordString=lostPassword.getText().toString();
                myRef.child(findPhoneString).child("Password").setValue(lostPasswordString);

            }
        });

        //method to delete local database
        //getContext().deleteDatabase("PhoneNumbers");

        return view;
    }

    public int isMyServiceRunning()
    {
        res.moveToLast();
        int state=res.getInt(2);
        return state;
    }

    private void Discoverable()
    {
        makeDiscoverable.setText("Start Discoverability");
        getContext().stopService(intent);
        state=0;
    }

    private void notDiscoverable()
    {
        makeDiscoverable.setText("Stop Discoverability");
        getContext().startService(intent);
        state=1;
    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        db.updateServiceState(state);
    }






}