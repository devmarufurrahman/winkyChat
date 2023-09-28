package com.example.mrnchatbd;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mrnchatbd.adapter.ChatViewRecyclerAdapter;
import com.example.mrnchatbd.adapter.SearchRecyclerAdapter;
import com.example.mrnchatbd.model.ChatMessageModel;
import com.example.mrnchatbd.model.ChatroomModel;
import com.example.mrnchatbd.utils.AndroidUtils;
import com.example.mrnchatbd.utils.FirebaseUtils;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    TextView chatUserName;
    ImageButton sendSmsBtn,backBtn;
    EditText chatInput;
    SearchActivity.UserModel userModel;
    RecyclerView chatRecyclerView;
    String chatroomId;
    ChatroomModel chatroomModel;
    ChatViewRecyclerAdapter chatAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        //id define
        chatUserName = findViewById(R.id.chatUserName);
        backBtn = findViewById(R.id.backBtn);
        sendSmsBtn = findViewById(R.id.sendSmsBtn);
        chatInput = findViewById(R.id.chatSmsInput);
        chatRecyclerView = findViewById(R.id.chatRecyclerView);



        // intent model data
        userModel = AndroidUtils.getUserModelIntent(getIntent());
        chatroomId = FirebaseUtils.getChatroomId(FirebaseUtils.currentUserId(),userModel.getUserId());



        chatUserName.setText(userModel.getUserName());

        // back on activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        // send btn activity
        sendSmsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = chatInput.getText().toString().trim();
                if (message.isEmpty())
                    return;
                sendMessageToUser(message);
            }
        });


        // chat room create
        getOrCreateChatroomModel();

        // adapter setup chat message
        setupChatRecycler();
    }

    private void setupChatRecycler() {
        Query query = FirebaseUtils.getChatroomMessageReference(chatroomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query, ChatMessageModel.class).build();


        chatAdapter = new ChatViewRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        chatRecyclerView.setLayoutManager(manager);
        chatRecyclerView.setAdapter(chatAdapter);
        chatAdapter.startListening();

        chatAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                chatRecyclerView.smoothScrollToPosition(0);
            }
        });

    }

    private void sendMessageToUser(String message) {
        chatroomModel.setLastMessageTimeStamp(Timestamp.now());
        chatroomModel.setLastMessageSenderId(FirebaseUtils.currentUserId());
        FirebaseUtils.getChatroomReference(chatroomId).set(chatroomModel);


        ChatMessageModel chatMessageModel = new ChatMessageModel(message,FirebaseUtils.currentUserId(),Timestamp.now());
        FirebaseUtils.getChatroomMessageReference(chatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            chatInput.setText("");
                        }
                    }
                });

    }

    private void getOrCreateChatroomModel() {
        FirebaseUtils.getChatroomReference(chatroomId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    chatroomModel = task.getResult().toObject(ChatroomModel.class);
                    if (chatroomModel == null){
                        // first time chat
                        chatroomModel = new ChatroomModel(
                                chatroomId, Arrays.asList(FirebaseUtils.currentUserId(),userModel.getUserId()),
                                Timestamp.now(),""
                        );
                        FirebaseUtils.getChatroomReference(chatroomId).set(chatroomModel);

                    }
                }
            }
        });
    }
}