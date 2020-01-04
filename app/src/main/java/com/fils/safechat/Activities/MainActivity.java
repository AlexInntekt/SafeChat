package com.fils.safechat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.fils.safechat.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("running onCreate");

//        logoutButton

        Button logoutButton = (Button) findViewById(R.id.logoutButton);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }

    protected void logout()
    {
        System.out.println("maia hi maia hu");
        FirebaseAuth.getInstance().signOut();
        Intent nextActivity = new Intent(getBaseContext(), SplashActivity.class);
        nextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(nextActivity);
        finishAffinity();
    }
}
