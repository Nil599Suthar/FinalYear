package com.example.sutharnil.buggy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.support.v7.widget.AppCompatDrawableManager.get;

public class RequestBuggy extends AppCompatActivity {

    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference, databaseReference1;
    private Spinner pickup, drop;
    private TextView seek;
    private String s1, s2, s3, s4;
    private String a,b,n;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_buggy);

        Button req_buggy = (Button) findViewById(R.id.req_buggy);
        final SeekBar seekbar = findViewById(R.id.seekBar);
        Button plus = (Button) findViewById(R.id.btnplus);
        Button minus = (Button) findViewById(R.id.btnminus);
        seek = (TextView) findViewById(R.id.seek);
        pickup = (Spinner) findViewById(R.id.pickup);
        drop = (Spinner) findViewById(R.id.drop);
        progressDialog = new ProgressDialog(RequestBuggy.this);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Requests");
        databaseReference1= FirebaseDatabase.getInstance().getReference("UserInfo");
        a=firebaseUser.getEmail().toString();
        getname();

        seekbar.setProgress(1);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                seek.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seekbar.setProgress(seekbar.getProgress() + 1);

            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                seekbar.setProgress(seekbar.getProgress() - 1);

            }
        });

        req_buggy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s1 = pickup.getSelectedItem().toString();
                s2 = drop.getSelectedItem().toString();
                s3 = seek.getText().toString();
                s4 = firebaseUser.getEmail().toString();

                final Intent intent = new Intent(RequestBuggy.this, BuggiesOnMap.class);

                progressDialog.setMessage("Your request for a buggy is being processed. \n" +
                        "Please wait for some time....");
                progressDialog.setIcon(R.drawable.ic_logopit_1534081965403);
                progressDialog.setCancelable(false);

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            Thread.sleep(3000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                        if (!s3.equals("0")) {

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RequestBuggy.this, "Please Add Number Of Passangers", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }).start();
                progressDialog.show();
                addData(s4, s1, s2, s3);


            }
        });
    }


    private void addData(String s4, String s1, String s2, String s3) {


        if (s3.equals("0")) {
            Toast.makeText(this, "Please Add Number Of Passangers", Toast.LENGTH_SHORT).show();

        } else {
            String key = databaseReference.push().getKey();
            Send_request sendRequest = new Send_request(key,s4,n, s1, s2, s3);
            databaseReference.child(key).setValue(sendRequest);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_for_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.logout) {

            Singout();
        } else if (item.getItemId() == R.id.account) {
            Intent i = new Intent(this, Account_user.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    private void Singout() {

        firebaseAuth.signOut();

        firebaseUser = firebaseAuth.getCurrentUser();


        if (firebaseUser == null) {
            Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(RequestBuggy.this, loginActivity.class);
            startActivity(i);
            finish();

        }
    }


    public void getname() {


        b=a.substring(0,a.length()-4);
        databaseReference1.child(b).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> data =(Map<String, Object>) dataSnapshot.getValue();

                String n1=data.get("u_f_name").toString();
                String n2=data.get("u_l_name").toString();
                n = n1+" "+n2;
//                Toast.makeText(RequestBuggy.this, n, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
