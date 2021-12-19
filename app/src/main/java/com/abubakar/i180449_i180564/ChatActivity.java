package com.abubakar.i180449_i180564;
/*
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ChatActivity extends AppCompatActivity {

    private ScreenShotContentObserver screenShotContentObserver;
    RecyclerView recyclerView;
    ImageButton selectImage,clearImage;
    Button send;
    View imagePreviewBackground;
    EditText messageContent;
    Uri selectedImage=null;
    ImageButton backButton;
    TextView appbar_heading;
    ImageView receiverImage,imagePreview;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setStatusBarColor(ContextCompat.getColor(ChatActivity.this,R.color.black));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Intent data=getIntent();
        String id=data.getStringExtra("id");
        String receiverName=data.getStringExtra("profileName");
        String receiverImageUri = data.getStringExtra("image");
        // Populate dummy messages in List, you can implement your code here
        send=findViewById(R.id.send_button);
        selectImage=findViewById(R.id.select_image);
        messageContent=findViewById(R.id.message_content);
        backButton = findViewById(R.id.back_button);
        imagePreview = findViewById(R.id.image_preview);
        clearImage = findViewById(R.id.clear_image);
        imagePreviewBackground = findViewById(R.id.image_preview_bg);
        appbar_heading = findViewById(R.id.appbarHeading);
        receiverImage = findViewById(R.id.recImg);


        HandlerThread handlerThread = new HandlerThread("content_observer");
        handlerThread.start();
        final Handler handler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(android.os.Message msg) {
                super.handleMessage(msg);
            }
        };

        screenShotContentObserver = new ScreenShotContentObserver(handler, this) {
            @Override
            protected void onScreenShot(String path, String fileName) {
                File file = new File(path); //this is the file of screenshot image
                Uri screenshot = Uri.fromFile(file);

                Toast.makeText(ChatActivity.this,"Screenshot detected. "+path,Toast.LENGTH_SHORT).show();

                StorageReference st= FirebaseStorage.getInstance().getReference();
                st=st.child("messages/" + screenshot.getLastPathSegment() + ".jpg");
                st.putFile(screenshot)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> task= taskSnapshot.getStorage().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String dp=uri.toString();
                                        reference2.push().setValue(new Message(senderId,id,"Taken Screenshot",dp,true));
                                        messageContent.setText(null);
                                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                                        clearImage();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });


            }
        };


        adapter = new MessageRVAdapter(this, messagesList,id,senderId);
        recyclerView = findViewById(R.id.rv);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);

        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,200);
            }
        });

        backButton.setOnClickListener(v -> {
            finish();
        });

        send.setOnClickListener(view -> {
            if(selectedImage!=null){
                StorageReference st= FirebaseStorage.getInstance().getReference();
                st=st.child("messages/" + selectedImage.getLastPathSegment() + ".jpg");
                st.putFile(selectedImage)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Task<Uri> task= taskSnapshot.getStorage().getDownloadUrl();
                                task.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String dp=uri.toString();
                                        reference2.push().setValue(new Message(senderId,id,messageContent.getText().toString(),dp,true));
                                        messageContent.setText(null);
                                        recyclerView.smoothScrollToPosition(adapter.getItemCount());
                                        clearImage();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(ChatActivity.this,"Image uploading failed",Toast.LENGTH_LONG).show();
                                        clearImage();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ChatActivity.this,"Image uploading failed",Toast.LENGTH_LONG).show();
                        clearImage();
                    }
                });
            }
            else{
                reference2.push().setValue(new Message(senderId,id,messageContent.getText().toString(),"",false));
                messageContent.setText(null);
                clearImage();
                recyclerView.smoothScrollToPosition(adapter.getItemCount());
            }

        });

        clearImage.setOnClickListener(v -> {
            clearImage();
        });

    }

    public void clearImage(){
        selectedImage= null;
        imagePreview.setImageURI(null);
        imagePreview.setVisibility(View.GONE);
        clearImage.setVisibility(View.GONE);
        imagePreviewBackground.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();

        getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                screenShotContentObserver
        );
    }

    @Override
    public void onPause() {
        super.onPause();

        try {
            getContentResolver().unregisterContentObserver(screenShotContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getContentResolver().unregisterContentObserver(screenShotContentObserver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==200 && resultCode==RESULT_OK){
            selectedImage=data.getData();
            imagePreview.setImageURI(selectedImage);
            imagePreview.setVisibility(View.VISIBLE);
            clearImage.setVisibility(View.VISIBLE);
            imagePreviewBackground.setVisibility(View.VISIBLE);

        }
    }
}


 */