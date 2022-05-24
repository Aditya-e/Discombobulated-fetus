package com.hfad.volume.ServicesFolder;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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


public class DiscoverableService extends IntentService {

    DataBase db;
    Cursor res;
    String password;
    String phoneNumber;
    boolean access;
    private Handler handler;

    FirebaseDatabase database;
    DatabaseReference myRef;

    AudioManager audioManager;

    private int maxVolume;


    public DiscoverableService() {
        super("DiscoverableService");
    }

    @Override
    public int onStartCommand(Intent intent,int flags,int startId)
    {
        handler=new Handler();
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
        audioManager.setStreamVolume(AudioManager.STREAM_RING,0,0);

            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String passwordEntered = snapshot.getValue(String.class);
                    if (password.equals(passwordEntered)) {
                        access = true;
                        Log.e("Service:", "Running here too");
                        Log.e("access", Boolean.toString(access));
                        //maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            if(access) {
                database.getReference("User").child(phoneNumber).child("Volume").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        int volume=snapshot.getValue(Integer.class);
                        audioManager.setStreamVolume(AudioManager.STREAM_RING,volume,0);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }


    }

    private void showText()
    {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (access) {
                    Toast.makeText(getApplicationContext(), "Access", Toast.LENGTH_SHORT);

                }
            }
        });
    }
}