package com.hfad.volume.FragmentsFolder;

import android.content.Context;
import android.database.Cursor;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hfad.volume.DataBase;
import com.hfad.volume.R;

//launched if user is not signed up...adds user to local database and firebase
public class SignUpFragment extends Fragment {


    private AudioManager audioManager;
    private int maxVolume;
    Button signUp;
    EditText myPhone,password;
    private String myPhoneString,passwordString;
    DataBase db;
    FirebaseDatabase database;
    DatabaseReference myRef;
    private String Volume="Volume";
    private String Permission="Permission";
    private String Camera="Camera";
    private String MaxVolume="Max Volume";
    private String Password="Password";
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_sign_up, container, false);

        password=view.findViewById(R.id.Password);
        db=new DataBase(getContext());
        database=FirebaseDatabase.getInstance();
        myRef=database.getReference("User");
        signUp=view.findViewById(R.id.signUp);
        myPhone=view.findViewById(R.id.myPhone);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });



        return view;
    }

    //method to add new user to local database and firebase and then launch FindNumberFragment after sign up by calling launchFindNumberFragment
    private void addUser()
    {

        audioManager=(AudioManager)getActivity().getSystemService(Context.AUDIO_SERVICE);
        maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_RING);
        myPhoneString = myPhone.getText().toString();
        passwordString=password.getText().toString();
        boolean result = db.insertData(myPhoneString, passwordString,"reference");
        if (result) Log.e("Data inserted", "TRUE");
        else Log.e("Data Inserted", "FALSE");
        myRef.child(myPhoneString).child(Volume).setValue(0);
        myRef.child(myPhoneString).child(Camera).setValue("");
        myRef.child(myPhoneString).child(Permission).setValue(false);
        myRef.child(myPhoneString).child(MaxVolume).setValue(maxVolume);
        myRef.child(myPhoneString).child(Password).setValue(passwordString);
        launchFindNumberFragment();
    }

    private void launchFindNumberFragment()
    {
        FindNumberFragment findNumberFragment=new FindNumberFragment();
        FragmentTransaction transaction=getActivity().getSupportFragmentManager().beginTransaction();//getActivity() is necessary when calling fragment from a fragment
        transaction.replace(R.id.linearLayoutAuthentication,findNumberFragment);
        transaction.commit();
    }


}