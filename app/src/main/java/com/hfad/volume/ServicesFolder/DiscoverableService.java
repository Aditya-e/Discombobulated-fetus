package com.hfad.volume.ServicesFolder;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.Camera;
import android.media.AudioManager;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hfad.volume.DataBase;

import android.os.Handler;
import java.util.logging.LogRecord;

//I DO NOT UNDERSTAND HOW SERVICE WORKS,THERE IS A REALLY WEIRD BEHAVIOUR THAT I CAN'T EXPLAIN

/*
* Problems:The service is not stopping with stopService() method or with selfStop()
*           If the controller has entered password correctly once and goes back to enter wrong password,controller is still able to control it*/
public class DiscoverableService extends IntentService {

    DataBase db;
    Cursor res;
    String password;
    String phoneNumber;
    boolean access;

    FirebaseDatabase database;
    DatabaseReference myRef;

    AudioManager audioManager;

    String permission;


    public DiscoverableService() {
        super("DiscoverableService");
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {

        return super.onStartCommand(intent,flags,startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        
            Log.e("Service state:", "started");

            db = new DataBase(getApplicationContext());
            res = db.getData();
            res.moveToLast();
            password = res.getString(1);
            phoneNumber = res.getString(0);

            database = FirebaseDatabase.getInstance();
            myRef = database.getReference("User").child(phoneNumber).child("Password");

        audioManager=(AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        //audioManager.setStreamVolume(AudioManager.STREAM_RING,0,0);


        database.getReference("User").child(phoneNumber).child("Permission").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                permission=snapshot.getValue(String.class);
                if(permission.equals("true"))  //supposed to check from firebase whether permission is "true" or "false"
                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String passwordEntered = snapshot.getValue(String.class);
                            //supposed to check if password entered is correct
                            if (password.equals(passwordEntered)) {
                                Log.e("password:",passwordEntered);
                                access = true;

                                if(access){database.getReference("User").child(phoneNumber).child("Volume").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        int volume = snapshot.getValue(Integer.class);
                                        audioManager.setStreamVolume(AudioManager.STREAM_RING, volume, 0);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });}
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//            myRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    String passwordEntered = snapshot.getValue(String.class);
//                    if (password.equals(passwordEntered)) {
//                        access = true;
//                        //maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
//                        database.getReference("User").child(phoneNumber).child("Volume").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                int volume = snapshot.getValue(Integer.class);
//                                audioManager.setStreamVolume(AudioManager.STREAM_RING, volume, 0);
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        }
    }
