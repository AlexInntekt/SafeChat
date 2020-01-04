package com.fils.safechat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.fils.safechat.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    ListView mMessageRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("running onCreate");

        mMessageRecyclerView = findViewById(R.id.messageListView);
        ArrayList<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, a);
        mMessageRecyclerView.setAdapter(adapter);

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
