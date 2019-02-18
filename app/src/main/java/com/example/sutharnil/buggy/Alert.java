package com.example.sutharnil.buggy;

import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;


public class Alert extends Fragment {

    private RecyclerView recyclerView;
    private Get_Req_Adapter getReqAdapter;
    private List<Send_request> send_requestList=new ArrayList<>();
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private SharedPreferences sharedPreferences1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alert, container, false);
        recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview);

        sharedPreferences1 = getContext().getSharedPreferences("switch",Context.MODE_PRIVATE);
        String On=sharedPreferences1.getString("on","n").toString();

        if (On.equals("on")){
            Get_Request();

        }else if (On.equals("off")){
            AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setIcon(R.drawable.ic_priority_high_black_24dp);
            builder.setCancelable(true);
            builder.setMessage("Please Active Your Availability..");
            AlertDialog alertDialog=builder.create();
            alertDialog.show();
        }
        getReqAdapter=new Get_Req_Adapter(send_requestList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        getReqAdapter.setOnclickAccept(new Get_Req_Adapter.OnclickAccept() {


            @Override
            public void onclickAccept(int position) {
                Toast.makeText(getContext(), ""+send_requestList.get(position).getPessanger(), Toast.LENGTH_SHORT).show();
            }
        });

        getReqAdapter.setOnclickDecline(new Get_Req_Adapter.OnclickDecline() {


            @Override
            public void onclickDecline(int position) {
                Toast.makeText(getContext(), ""+send_requestList.get(position).getPessanger(), Toast.LENGTH_SHORT).show();
            }
        });
        SnapHelper startSnapHelper = new StartSnapHelper();
        startSnapHelper.attachToRecyclerView(recyclerView);



        return view;
    }

    private void Get_Request() {


            send_requestList.clear();
        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("Requests");
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    Send_request sendRequest=new Send_request();
                    for(DataSnapshot s1 : dataSnapshot.getChildren()) {
                        sendRequest = dataSnapshot.getValue(Send_request.class);
                    }
                    send_requestList.add(sendRequest);

                    recyclerView.setAdapter(getReqAdapter);
                    getReqAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


}


