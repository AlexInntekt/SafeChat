package com.fils.safechat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fils.safechat.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    ListView mMessageRecyclerView;
    FirebaseDatabase database;
    DatabaseReference myRefToDatabase;
    ArrayList<String> messagesList = new ArrayList<String>();
    JSONObject messages = null;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("running onCreate");


        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, messagesList);
        mMessageRecyclerView.setAdapter(adapter);

        mMessageRecyclerView = findViewById(R.id.messageListView);
        database = FirebaseDatabase.getInstance();
        myRefToDatabase = database.getReference("messages");
        myRefToDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    Gson gson = new Gson();
                    String gsonString = gson.toJson(dataSnapshot.getValue());
                    messagesList.clear();
                    try {
                        messages = new JSONObject(gsonString);
                        Iterator<String> iterator = messages.keys();
                        while (iterator.hasNext()) {
                            String key = iterator.next();
                            try {
                                JSONObject message = new JSONObject(messages.get(key).toString());
                                messagesList.add(message.get("text").toString());
                            } catch (JSONException e) {
                                // Something went wrong!
                            }
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

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
