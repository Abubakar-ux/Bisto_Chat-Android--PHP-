package com.abubakar.i180449_i180564;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class MessageRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    ArrayList<Message> list;
    String rid;
    String sid;
    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;
    public static final int MESSAGE_TYPE_OUT_IMAGE = 3;
    public static final int MESSAGE_TYPE_IN_IMAGE = 4;
    public static final int MESSAGE_TYPE_OUT_IMAGE_ONLY = 5;
    public static final int MESSAGE_TYPE_IN_IMAGE_ONLY = 6;

    public MessageRVAdapter(Context context, ArrayList<Message> list,String rid,String sid) { // you can pass other parameters in constructor
        this.context = context;
        this.sid=sid;
        for(int i=0;i<list.size();i++){
            Log.d("messages",list.get(i).text);
        }
        this.list=list;

        this.rid=rid;
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {
        TextView dateTime;
        TextView messageTV;
        MessageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.text_received);
            dateTime = itemView.findViewById(R.id.time_date_text_received);
        }
        void bind(int position) {
            Message message = list.get(position);
            messageTV.setText(message.text);


            Date resultDate = new Date(message.getTimestamp()*1000);
            dateTime.setText(sdf.format(resultDate));
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {
        TextView dateTime;
        TextView messageTV;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.text_sent);
            dateTime = itemView.findViewById(R.id.time_date_text_sent);
        }
        void bind(int position) {
            Message message = list.get(position);
            messageTV.setText(message.text);
            Date resultDate = new Date(message.getTimestamp()*1000);
            dateTime.setText(sdf.format(resultDate));

            /*
            messageTV.setOnKeyListener(new View.OnKeyListener() {
                public boolean onKey(View view, int keyCode, KeyEvent keyevent) {
                    //If the keyevent is a key-down event on the "enter" button
                    if ((keyevent.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                        String newText = messageTV.getText().toString();

                        message.setText(newText);



                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query query = ref.child("Chats").orderByChild("text").equalTo(message.getText());
                        list.remove(position);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot appleSnapshot: snapshot.getChildren()) {
                                    appleSnapshot.getRef().child("text").setValue(newText);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        return true;
                    }
                    return false;
                }
            });


            messageTV.setOnLongClickListener((View.OnLongClickListener) v -> {
                Toast.makeText(context,"Message Deleted",Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                Query applesQuery = ref.child("Chats").orderByChild("text").equalTo(message.getText());
                list.remove(position);
                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("0", "onCancelled", databaseError.toException());
                    }
                });
                return false;
            });

             */

        }
    }
    /*

    void deleteMessage(Message message){
        RequestQueue MyRequestQueue = Volley.newRequestQueue(context);
        String url = Id.getIp()+"deleteMessage.php";
        StringRequest MyStringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("reponse",response);
                getChat();
                clearImage();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("message error",error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> MyData;
                MyData = new HashMap<String, String>();
                if(encodedImage==null){
                    MyData.put("senderId", message.getSenderId());
                    MyData.put("receiverId", message.getReceiverId());
                    MyData.put("text", message.getText());
                    MyData.put("timestamp", Long.toString(message.getTimestamp()));
                    clearImage();
                }
                else{
                    MyData.put("senderId", message.getSenderId());
                    MyData.put("receiverId", message.getReceiverId());
                    MyData.put("text", message.getText());
                    MyData.put("timestamp", Long.toString(message.getTimestamp()));
                    MyData.put("image", encodedImage);
                }

                return MyData;
            }
        };

        MyRequestQueue.add(MyStringRequest);
    }

    */

    private class MessageImageInViewHolder extends RecyclerView.ViewHolder {
        TextView dateTime;
        TextView messageTV;
        ImageView image;
        MessageImageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.text_received_image);
            image=itemView.findViewById(R.id.image_received);
            dateTime=itemView.findViewById(R.id.time_date_text_received_image);

        }
        void bind(int position) {
            Message message = list.get(position);
            messageTV.setText(message.text);
            if(!message.getImgSrc().isEmpty()){
                Picasso.get().load(Id.getIp()+message.getImgSrc()).fit().centerCrop().into(image);
            }

            Date resultDate = new Date(message.getTimestamp()*1000);
            dateTime.setText(sdf.format(resultDate));

        }
    }

    private class MessageImageOutViewHolder extends RecyclerView.ViewHolder {
        TextView dateTime;
        TextView messageTV;
        ImageView image;
        MessageImageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.text_sent_image);
            image=itemView.findViewById(R.id.image_sent);
            dateTime = itemView.findViewById(R.id.time_date_text_sent_image);

        }
        void bind(int position) {
            Message message = list.get(position);
            messageTV.setText(message.text);
            if(!message.getImgSrc().isEmpty()){
                Picasso.get().load(Id.getIp()+message.getImgSrc()).fit().centerCrop().into(image);
            }
            Date resultDate = new Date(message.getTimestamp()*1000);
            dateTime.setText(sdf.format(resultDate));

        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.message_rec, parent, false));
        }
        else if(viewType == MESSAGE_TYPE_OUT){
            return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.message_sent, parent, false));
        }
        else if(viewType == MESSAGE_TYPE_OUT_IMAGE){
            return new MessageImageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.message_sent_img, parent, false));
        }
        else{
            return new MessageImageInViewHolder(LayoutInflater.from(context).inflate(R.layout.message_rec_img, parent, false));
        }


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (list.get(position).senderId.equals(rid)  && list.get(position).getImgSrc().equals("Not Available")) {
            ((MessageInViewHolder) holder).bind(position);
        }
        else if(list.get(position).senderId.equals(rid)  && !list.get(position).getImgSrc().equals("Not Available")){
            ((MessageImageInViewHolder) holder).bind(position);
        }
        else if(list.get(position).senderId.equals(sid) && !list.get(position).getImgSrc().equals("Not Available")){
            ((MessageImageOutViewHolder) holder).bind(position);
        }
        else{
            ((MessageOutViewHolder) holder).bind(position);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).senderId.equals(rid)  && list.get(position).getImgSrc().equals("Not Available")) {
            return 1;
        }
        else if(list.get(position).senderId.equals(rid) && !list.get(position).getImgSrc().equals("Not Available")){
            return 4;
        }
        else if(list.get(position).senderId.equals(sid)&& list.get(position).getImgSrc().equals("Not Available")){
            return 2;
        }
        else{
            return 3;
        }


    }
}
