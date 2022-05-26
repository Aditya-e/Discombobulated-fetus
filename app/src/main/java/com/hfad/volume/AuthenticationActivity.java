package com.hfad.volume;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.hfad.volume.FragmentsFolder.FindNumberFragment;
import com.hfad.volume.FragmentsFolder.SignUpFragment;

public class AuthenticationActivity extends AppCompatActivity {

    public DataBase db;//Local SQLite database initialisation
    Cursor res;

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

        

        Toast.makeText(getApplicationContext(),R.string.Permission,Toast.LENGTH_LONG);
        NotificationManager notificationManager =
                (NotificationManager) this.getSystemService(this.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && !notificationManager.isNotificationPolicyAccessGranted()) {



            Intent intent = new Intent(
                    android.provider.Settings
                            .ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS);

            startActivity(intent);
        }



        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, 183);





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