package com.hfad.volume.FragmentsFolder;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.hfad.volume.R;


public class VolumeFragment extends Fragment {
    private SeekBar seekBar;
    private AudioManager audioManager;
        public VolumeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_volume, container, false);
        seekBar=view.findViewById(R.id.seekBar);
        audioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE); //getSystemService needs to be called on context hence getActivity()

        seekBar.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_RING));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int volume, boolean b) {




                audioManager.setStreamVolume(AudioManager.STREAM_RING,volume,0);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });




        return view;
    }
}