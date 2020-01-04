package com.fils.safechat.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.fils.safechat.Models.Message;
import com.fils.safechat.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {

    ListView mMessageRecyclerView;
    Button button;
    EditText editText;

    FirebaseDatabase database;
    DatabaseReference myRefToDatabase;
    FirebaseAuth mAuth;
    FirebaseUser user;

    ArrayList<Message> messagesList = new ArrayList<Message>();
    JSONObject messages = null;
    ArrayAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.sendButton);
        button.setEnabled(false);

        editText = findViewById(R.id.messageEditText);
        editText.addTextChangedListener((new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0)
                    button.setEnabled(true);
                else
                    button.setEnabled(false);
            }
        }));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRefToDatabase = database.getReference("messages");
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Message message = new Message(user.getEmail(), editText.getText().toString(), Long.toString(timestamp.getTime()));

                myRefToDatabase.push().setValue(message)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                editText.setText("");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
            }
        });

        mMessageRecyclerView = findViewById(R.id.messageListView);
        adapter = new ArrayAdapter<Message>(this, android.R.layout.simple_list_item_1, messagesList);
        mMessageRecyclerView.setAdapter(adapter);

        database = FirebaseDatabase.getInstance();
        myRefToDatabase = database.getReference("messages");
        myRefToDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("dataSnapshot:"+dataSnapshot);
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
                                JSONObject messageJson = new JSONObject(messages.get(key).toString());
                                Message message = new Message(messageJson.get("author").toString(),
                                                                messageJson.get("text").toString(),
                                                                messageJson.get("createdAt").toString());
                                messagesList.add(message);
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
        System.out.println("delogare iesire din cont...");
        FirebaseAuth.getInstance().signOut();
        Intent nextActivity = new Intent(getBaseContext(), SplashActivity.class);
        nextActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(nextActivity);
        finishAffinity();
    }
}
