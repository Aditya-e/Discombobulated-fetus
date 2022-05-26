package com.hfad.volume;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hfad.volume.FragmentsFolder.VolumeFragment;
//Activity opened up after lost phone's number and password is entered

public class MainActivity extends AppCompatActivity {
    TextView insultUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insultUser=findViewById(R.id.insultUser);

        //Setting the color of toolbar and title
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#009FFF"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(R.string.AppTitle);

        Intent intent=getIntent();
        String targetPhone=intent.getStringExtra("targetPhone");


        //Buttons to open volume fragments
        Button volume=findViewById(R.id.Volume);

        //'Get Volume' button listener
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insultUser.setText("");
                Bundle bundle = new Bundle();
                bundle.putString("targetPhone", targetPhone);
                VolumeFragment volumeFragment=new VolumeFragment();
                volumeFragment.setArguments(bundle);
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentLinearLayout,volumeFragment);
                transaction.commit();
            }
        });

    }
}