package com.hfad.volume;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.hfad.volume.FragmentsFolder.FindNumberFragment;
import com.hfad.volume.FragmentsFolder.SignUpFragment;

public class AuthenticationActivity extends AppCompatActivity {

    DataBase db;//Local SQLite database initialisation

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        //Setting the color of toolbar and title
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#009FFF"));
        actionBar.setBackgroundDrawable(colorDrawable);
        actionBar.setTitle(R.string.AppTitle);

        db=new DataBase(this);

        //sees if user is already signed up,if not opens SignUpFragment else opens FindNumberFragment
        Cursor res = db.getData();
        if(res.getCount()==0)launchSignUpFragment();
        else launchFindNumberFragment();




    }

    //method to launch SignUpFragment
    private void launchSignUpFragment()
    {
        SignUpFragment signUpFragment=new SignUpFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayoutAuthentication,signUpFragment);
        transaction.commit();
    }

    //method to launch FindNumberFragment
    private void launchFindNumberFragment()
    {
        FindNumberFragment findNumberFragment=new FindNumberFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.linearLayoutAuthentication,findNumberFragment);
        transaction.commit();
    }
}