package com.example.sutharnil.buggy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;

import java.util.Map;

public class Account_user extends AppCompatActivity {

    private TextView u_id,u_f_name,u_l_name,u_mob,u_address,u_adhar,u_gender;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference1;
    private String a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_user);

        u_id =(TextView)findViewById(R.id.u_id);
        u_f_name =(TextView)findViewById(R.id.u_f_name);
        u_l_name =(TextView)findViewById(R.id.u_l_name);
        u_mob =(TextView)findViewById(R.id.u_mob);
        u_address =(TextView)findViewById(R.id.u_address);
        u_adhar =(TextView)findViewById(R.id.u_adhar);
        u_gender =(TextView)findViewById(R.id.u_gender);


        firebaseAuth=FirebaseAuth.getInstance();
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        databaseReference1= FirebaseDatabase.getInstance().getReference("UserInfo");
        a=firebaseUser.getEmail().toString();
    }

    @Override
    public void onStart() {
        super.onStart();


        b=a.substring(0,a.length()-4);
        databaseReference1.child(b).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Map<String, Object> data =(Map<String, Object>) dataSnapshot.getValue();

                u_id.setText(""+a);
                u_f_name.setText(""+data.get("u_f_name").toString());
                u_l_name.setText(""+data.get("u_l_name").toString());
                u_mob.setText(""+data.get("u_mob").toString());
                u_address.setText(""+data.get("u_address").toString());
                u_adhar.setText(""+data.get("u_adhar").toString());
                u_gender.setText(""+data.get("u_gender").toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
