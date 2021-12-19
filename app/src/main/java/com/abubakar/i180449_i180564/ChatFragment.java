package com.abubakar.i180449_i180564;
/*
import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mhassanakbar.i180564_i180449.placeholder.PlaceholderContent;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


 * A fragment representing a list of Items.

public class ChatFragment extends Fragment {

    private static final String ARG_COLUMN_COUNT = "column-count";
    RecyclerView rv;
    Button nContact;
    List<Profile> contactList;
    List<Profile> mutualContacts;
    ArrayList<String> arrayList;
    ContactRVAdapter adapter;
    private final int ADD_CONTACT = 0;
    FirebaseDatabase database;
    DatabaseReference reference;
    private FirebaseAuth mAuth;
    EditText searchView;
    CharSequence search ="";
    CircleImageView profileImage;
    String senderId;
    Context context;
    TextView name;

     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).

    public ChatFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ChatFragment newInstance(int columnCount) {
        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat_list, container, false);
        rv = view.findViewById(R.id.list);
        arrayList=new ArrayList<>();
        contactList=new ArrayList<>();
        mutualContacts=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        senderId =  mAuth.getCurrentUser().getUid();
        name = view.findViewById(R.id.name);

        // Set the adapter
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            // requesting to the user for permission.
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 100);

        } else {
            //if app already has permission this block will execute.
            readContacts();
        }

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("ProfilesTable");

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Profile x=dataSnapshot.getValue(Profile.class);

                if(arrayList.contains(x.getPhoneNo()) && !x.getId().equals(mAuth.getCurrentUser().getUid())){
                    mutualContacts.add(x);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Profile x=dataSnapshot.getValue(Profile.class);
                if(x.getId().equals(senderId)){
                    Picasso.get().load(x.getDp()).fit().centerCrop().into(profileImage);
                    name.setText(x.getName());
                }
                if(arrayList.contains(x.getPhoneNo()) && !x.getId().equals(mAuth.getCurrentUser().getUid())){
                    mutualContacts.add(x);
                }
                adapter.notifyDataSetChanged();
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
        RecyclerView.LayoutManager lm= new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter=new ContactRVAdapter(mutualContacts, getContext());
        rv.setAdapter(adapter);
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readContacts();
    }

    @SuppressLint("Range")
    private void readContacts() {
        ContentResolver contentResolver=getContext().getContentResolver();
        Cursor phones=contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
        String number;
        while (phones.moveToNext())
        {
            number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace("+92","0");
            arrayList.add(number);
//                Log.d("Hello",phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
        }

        phones.close();
    }
}
*/