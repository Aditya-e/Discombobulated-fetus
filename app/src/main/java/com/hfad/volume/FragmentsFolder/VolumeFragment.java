package com.hfad.volume.FragmentsFolder;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.volume.R;

//this fragment controls volume of other device
public class VolumeFragment extends Fragment {
    private Button increaseVolume,decreaseVolume;
    private AudioManager audioManager;
    String targetPhone;
    int volume=1;

    FirebaseDatabase database;
    DatabaseReference myRef;

    FindNumberFragment findNumberFragment;

        public VolumeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_volume, container, false);
        increaseVolume=view.findViewById(R.id.increaseVolume);
        decreaseVolume=view.findViewById(R.id.decreaseVolume);

        findNumberFragment=new FindNumberFragment();
        targetPhone=findNumberFragment.sendTargetPhone();

        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("User");

        //AudioManger class is used to control the volume
//        audioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE); //getSystemService needs to be called on context hence getActivity()
//        audioManager.setStreamVolume(AudioManager.STREAM_RING,0,0);

        increaseVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(targetPhone).child("Volume").setValue(++volume);
            }
        });

        decreaseVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myRef.child(targetPhone).child("Volume").setValue(--volume);
            }
        });

        return view;
    }
}