package com.hfad.volume.ServicesFolder;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


public class DiscoverableService extends IntentService {


    public DiscoverableService() {
        super("DiscoverableService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    Log.e("Service state:","started");

    }
}