package com.hfad.volume;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.volume.FragmentsFolder.VolumeFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        FirebaseDatabase database=FirebaseDatabase.getInstance();
//        DatabaseReference rootRef=database.getReference("message");

        Button volume=findViewById(R.id.Volume);
        Button camera=findViewById(R.id.Camera);

        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VolumeFragment volumeFragment=new VolumeFragment();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLayout,volumeFragment);
                transaction.commit();
            }
        });

    }
}